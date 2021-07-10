package ru.kau.mygtd2.utils.sys;

public class Zips {

    public static ZipArchiveInputStream buildZipArchiveInputStream(String file) {
        return new ZipArchiveInputStream(file);
    }

}
