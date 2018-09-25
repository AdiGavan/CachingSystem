package observerPattern.classes;

import java.util.ArrayList;

import observerPattern.interfaces.CacheListener;

/**
 * The BroadcastListener broadcasts cache events to other listeners that have been added to it.
 */
public class BroadcastListener<K, V> implements CacheListener<K, V> {

    private ArrayList<CacheListener<K, V>> listeners = new ArrayList<CacheListener<K, V>>();

    /**
     * Add a listener to the broadcast list.
     *
     * @param listener the listener
     */
    public void addListener(CacheListener<K, V> listener) {
        listeners.add(listener);
    }

    /**
     * Notifica ascultatorii in caz de cacheHit.
     *
     * @param key
     */
    public void onHit(final K key) {
        for (int i = 0; i < listeners.size(); i++) {
            listeners.get(i).onHit(key);
        }
    }

    /**
     * Notifica ascultatorii in caz de cacheMiss.
     *
     * @param key
     */
    public void onMiss(final K key) {
        for (int i = 0; i < listeners.size(); i++) {
            listeners.get(i).onMiss(key);
        }
    }

    /**
     * Notifica ascultatorii in caz de cachePut.
     *
     * @param key, value
     */
    public void onPut(final K key, final V value) {
        for (int i = 0; i < listeners.size(); i++) {
            listeners.get(i).onPut(key, value);
        }
    }

}
