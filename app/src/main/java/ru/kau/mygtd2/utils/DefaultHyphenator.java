package ru.kau.mygtd2.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import ru.kau.mygtd2.enums.HyphenPattern;

public class DefaultHyphenator {

    private TrieNode trie;
    private int leftMin;
    private int rightMin;

    public HyphenPattern pattern;

    boolean isErrorPattern = false;

    public DefaultHyphenator(HyphenPattern pattern) {
        this.pattern = pattern;
        isErrorPattern = pattern == HyphenPattern.error;
        this.trie = this.createTrie(pattern.patternObject);
        this.leftMin = pattern.leftMin;
        this.rightMin = pattern.rightMin;
    }

    private TrieNode createTrie(Map<Integer, String> patternObject) {

        if (isErrorPattern) {
            return null;
        }

        TrieNode t, tree = new TrieNode();

        for (Map.Entry<Integer, String> entry : patternObject.entrySet()) {
            int key = entry.getKey();
            String value = entry.getValue();

            int numPatterns = value.length() / key;
            for (int i = 0; i < numPatterns; i++) {
                String pattern = value.substring(i * key, (i + 1) * key);
                t = tree;

                for (int c = 0; c < pattern.length(); c++) {
                    char chr = pattern.charAt(c);
                    if (Character.isDigit(chr)) {
                        continue;
                    }
                    int codePoint = pattern.codePointAt(c);
                    if (t.codePoint.get(codePoint) == null) {
                        t.codePoint.put(codePoint, new TrieNode());
                    }
                    t = t.codePoint.get(codePoint);
                }

                List<Integer> list = new ArrayList<Integer>();
                int digitStart = -1;
                for (int p = 0; p < pattern.length(); p++) {
                    if (Character.isDigit(pattern.charAt(p))) {
                        if (digitStart < 0) {
                            digitStart = p;
                        }
                        if (p == pattern.length() - 1) {
                            // last number in the pattern
                            String number = pattern.substring(digitStart, pattern.length());
                            list.add(Integer.valueOf(number));
                        }
                    } else if (digitStart >= 0) {
                        // we reached the end of the current number
                        String number = pattern.substring(digitStart, p);
                        list.add(Integer.valueOf(number));
                        digitStart = -1;
                    } else {
                        list.add(0);
                    }
                }
                t.points = new int[list.size()];
                for (int k = 0; k < list.size(); k++) {
                    t.points[k] = list.get(k);
                }
            }
        }

        return tree;
    }

    public List<String> hyphenate(String word) {
        List<String> result = new ArrayList<String>();
        if (isErrorPattern) {
            result.add(word);
            return result;
        }

        word = "_" + word + "_";
        String lowercase = word.toLowerCase(Locale.US);

        int wordLength = lowercase.length();

        int[] points = new int[wordLength];
        int[] characterPoints = new int[wordLength];
        for (int i = 0; i < wordLength; i++) {
            points[i] = 0;
            characterPoints[i] = lowercase.codePointAt(i);
        }

        TrieNode node, trie = this.trie;
        int[] nodePoints;
        for (int i = 0; i < wordLength; i++) {
            node = trie;
            for (int j = i; j < wordLength; j++) {
                node = node.codePoint.get(characterPoints[j]);
                if (node != null) {
                    nodePoints = node.points;
                    if (nodePoints != null) {
                        for (int k = 0, nodePointsLength = nodePoints.length; k < nodePointsLength; k++) {
                            points[i + k] = Math.max(points[i + k], nodePoints[k]);
                        }
                    }
                } else {
                    break;
                }
            }
        }

        int start = 1;
        for (int i = 1; i < wordLength - 1; i++) {
            if (i > this.leftMin && i < (wordLength - this.rightMin) && points[i] % 2 > 0) {
                result.add(word.substring(start, i));
                start = i;
            }
        }
        if (start < word.length() - 1) {
            result.add(word.substring(start, word.length() - 1));
        }
        return result;
    }

    /**
     * HyphenaPattern.java is an adaptation of Bram Steins hypher.js-Project:
     * https://github.com/bramstein/Hypher
     *
     * Code from this project belongs to the following license: Copyright (c) 2011,
     * Bram Stein All rights reserved.
     *
     * Redistribution and use in source and binary forms, with or without
     * modification, are permitted provided that the following conditions are met:
     * 1. Redistributions of source code must retain the above copyright notice,
     * this list of conditions and the following disclaimer. 2. Redistributions in
     * binary form must reproduce the above copyright notice, this list of
     * conditions and the following disclaimer in the documentation and/or other
     * materials provided with the distribution. 3. The name of the author may not
     * be used to endorse or promote products derived from this software without
     * specific prior written permission.
     *
     * THIS SOFTWARE IS PROVIDED BY THE AUTHOR "AS IS" AND ANY EXPRESS OR IMPLIED
     * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
     * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO
     * EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
     * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
     * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
     * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
     * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
     * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
     * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
     */

    private class TrieNode {
        Map<Integer, TrieNode> codePoint = new HashMap<Integer, TrieNode>();
        int[] points;
    }
}
