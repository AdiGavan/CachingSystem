package cachingSystem.classes;

import dataStructures.classes.Pair;

/**
 * Class that adapts the FIFOCache class to the ObservableCache abstract class.
 */
public class ObservableFIFOCache<K, V> extends ObservableCache<K, V> {

    private FIFOCache<K, V> container;

    public ObservableFIFOCache() {
        container = new FIFOCache<>();
    }

    /**
     * Intoarce valoarea asociata unei chei sau null daca cheia nu exista in
     * cache.
     *
     * @param key
     * @return valoarea
     */
    public V get(final K key) {
        V aux = container.get(key);
        if (aux == null) {
            /*
             * Ascultatorii sunt notificati ca a avut loc un miss.
             */
            for (int i = 0; i < listeners.size(); i++) {
                listeners.get(i).onMiss(key);
            }
        } else {
            /*
             * Ascultatorii sunt notificati ca a avut loc un hit.
             */
            for (int i = 0; i < listeners.size(); i++) {
                listeners.get(i).onHit(key);
            }
        }
        return aux;
    }

    /**
     * Insereaza o pereche cheie-valoare in cache.
     *
     * @param key, value
     */
    public void put(final K key, final V value) {
        container.put(key, value);
        /*
         * Ascultatorii sunt notificati ca a avut loc un put.
         */
        for (int i = 0; i < listeners.size(); i++) {
            listeners.get(i).onPut(key, value);
        }

        this.clearStaleEntries();

    }

    /**
     * Intoarce dimensiunea cache-ului.
     *
     * @return dimensiune
     */
    public int size() {
        return container.size();
    }

    /**
     * Intoarce true daca cache-ul este gol, false daca nu.
     *
     * @return adevarat sau fals
     */
    public boolean isEmpty() {
        return container.size() == 0;
    }

    /**
     * Sterge o cheie din cache.
     *
     * @param key
     * @return value
     */
    public V remove(final K key) {
        return container.remove(key);
    }

    /**
     * Goleste cache-ul.
     */
    public void clearAll() {
        container.clearAll();
    }

    /**
     * Se intoarce cea mai veche pereche cheie-valoare.
     *
     * @return pereche veche
     */
    public Pair<K, V> getEldestEntry() {
        return container.getEldestEntry();
    }
}
