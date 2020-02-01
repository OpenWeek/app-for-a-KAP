package gdx.kapotopia.DataStructures;

public interface Tree<Key extends Comparable<Key>, Value> extends Iterable<Key> {

    int size();
    Value get(Key key);
    void put(Key key, Value val);
    Key min();
    Key max();
    Key floor(Key key);
    Key select(int k);
    int rank(Key key);
    void deleteMin();
    void deleteMax();
    void delete(Key key);
}
