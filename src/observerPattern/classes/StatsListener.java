package observerPattern.classes;

import observerPattern.interfaces.CacheListener;

/**
 * The StatsListener collects hit / miss / update stats for a cache.
 *
 * @param <K>
 * @param <V>
 */
public class StatsListener<K, V> implements CacheListener<K, V> {

    private int misses = 0;
    private int updates = 0;
    private int hits = 0;

    /**
     * Get the number of hits for the cache.
     *
     * @return number of hits
     */
    public int getHits() {
        return hits;
    }

    /**
     * Get the number of misses for the cache.
     *
     * @return number of misses
     */
    public int getMisses() {
        return misses;
    }

    /**
     * Get the number of updates (put operations) for the cache.
     *
     * @return number of updates
     */
    public int getUpdates() {
        return updates;
    }

    /**
     * In caz de cacheHit, se incrementeaza numarul total de hit-uri.
     *
     * @param key
     */
    public void onHit(final K key) {
        hits++;
    }

    /**
     * In caz de cacheMiss, se incrementeaza numarul total de miss-uri.
     *
     * @param key
     */
    public void onMiss(final K key) {
        misses++;
    }

    /**
     * In caz de cachePut, se incrementeaza numarul total de put-uri.
     *
     * @param key
     */
    public void onPut(final K key, final V value) {
        updates++;
    }
}
