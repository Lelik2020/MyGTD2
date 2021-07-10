package ru.kau.mygtd2.trees.abstr;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public abstract class AllTree <T extends AllTree<T>> implements Iterable<T>{

    private int mySize = 1;
    public final T Parent;
    public final int Level;
    private volatile List<T> mySubtrees;

    protected AllTree() {
        this(null);
    }

    protected AllTree(T parent) {
        this(parent, -1);
    }

    protected AllTree(T parent, int position) {
        if (position == -1) {
            position = parent == null ? 0 : parent.subtrees().size();
        }
        if (parent != null && (position < 0 || position > parent.subtrees().size())) {
            throw new IndexOutOfBoundsException("`position` value equals " + position + " but must be in range [0; " + parent.subtrees().size() + "]");
        }
        Parent = parent;
        if (parent != null) {
            Level = parent.Level + 1;
            parent.addSubtree((T)this, position);
        } else {
            Level = 0;
        }
    }

    public List<T> subtrees() {
        if (mySubtrees == null) {
            return Collections.emptyList();
        }
        synchronized (mySubtrees) {
            return new ArrayList<T>(mySubtrees);
        }
    }

    @NonNull
    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public void forEach(Consumer<? super T> action) {

    }

    @Override
    public Spliterator<T> spliterator() {
        return null;
    }

    public final boolean hasChildren() {
        return mySubtrees != null && !mySubtrees.isEmpty();
    }

    synchronized final void addSubtree(T subtree, int position) {
        if (mySubtrees == null) {
            mySubtrees = Collections.synchronizedList(new ArrayList<T>());
        }
        final int subtreeSize = subtree.getSize();
        synchronized (mySubtrees) {
            final int thisSubtreesSize = mySubtrees.size();
            while (position < thisSubtreesSize) {
                subtree = mySubtrees.set(position++, subtree);
            }
            mySubtrees.add(subtree);
            for (AllTree<?> parent = this; parent != null; parent = parent.Parent) {
                parent.mySize += subtreeSize;
            }
        }
    }

    public final int getSize() {
        return mySize;
    }

}
