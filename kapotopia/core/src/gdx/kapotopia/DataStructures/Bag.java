package gdx.kapotopia.DataStructures;

import java.util.Iterator;

public class Bag<Item> implements Iterable<Item>{

    private final static int INITIAL_CAPACITY = 32;
    // On assume que bagSize <= l.length
    private int bagSize;
    private Item[] l;

    public Bag() {
        bagSize = 0;
        l = (Item[]) new Object[INITIAL_CAPACITY];
    }

    public void add(Item item) {
        resize(bagSize+1);
        l[bagSize] = item;
        bagSize++;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return bagSize;
    }

    @Override
    public Iterator<Item> iterator() {
        return new DenseBagIterator();
    }

    private void resize(int minCapacity){
        if(l.length < minCapacity) {
            Item[] biggerArray;
            biggerArray=(Item[]) new Object[2*minCapacity];
            System.arraycopy(l, 0, biggerArray, 0, bagSize);
            l=biggerArray;
        }
    }

    private class DenseBagIterator implements Iterator<Item> {
        int pos = 0;
        @Override
        public boolean hasNext() {
            return pos < bagSize;
        }

        @Override
        public Item next() {
            return l[pos++];
        }
    }
}
