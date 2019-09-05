package gdx.kapotopia;

import java.util.HashMap;

/**
 * A helper class to help pass variables between screens using an HashMap kind of system.
 * When a value stored is getting accessed, it is automatically removed
 */
public class ValueGateway {

    private HashMap<String,Object> store;

    ValueGateway() {
        store = new HashMap<String, Object>();
    }

    public void addToTheStore(String key, Object o) {
        store.put(key, o);
    }

    public Object removeFromTheStore(String key) {
        return store.remove(key);
    }
}
