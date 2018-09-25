package observerPattern.classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import observerPattern.interfaces.CacheListener;




/**
 * The KeyStatsListener collects key-level stats for cache operations.
 *
 * @param <K>
 * @param <V>
 */
public class KeyStatsListener<K, V> implements CacheListener<K, V> {

    /*
     * onmiss retine de cate ori a fost un cacheMiss pe un fisier.
     */
    private Map<K, Integer> onmiss = new TreeMap<K, Integer>();
    /*
     * onput retine de cate ori a fost un cachePut pe un fisier.
     */
    private Map<K, Integer> onput = new TreeMap<K, Integer>();
    /*
     * onhit retine de cate ori a fost un cacheHit pe un fisier.
     */
    private Map<K, Integer> onhit = new TreeMap<K, Integer>();

    /**
     * Get the number of hits for a key.
     *
     * @param key the key
     * @return number of hits
     */
    public int getKeyHits(K key) {
        return onhit.get(key);
    }

    /**
     * Get the number of misses for a key.
     *
     * @param key the key
     * @return number of misses
     */
    public int getKeyMisses(K key) {
        return onmiss.get(key);
    }

    /**
     * Get the number of updates for a key.
     *
     * @param key the key
     * @return number of updates
     */
    public int getKeyUpdates(K key) {
        return onput.get(key);
    }

    /**
     * Get the @top most hit keys.
     *
     * @param top number of top keys
     * @return the list of keys
     */
    public List<K> getTopHitKeys(int top) {

        ArrayList<Map.Entry<K, Integer>> tophitsAux = new
                ArrayList<Map.Entry<K, Integer>>();
        LinkedList<K> tophits = new LinkedList<K>();

        /*
         * Se pun toate intrarile din onhit intr-o lista auxiliara.
         */
        for (Map.Entry<K, Integer> entry : onhit.entrySet()) {
            tophitsAux.add(entry);
        }

        /*
         * Se sorteaza lista auxiliara descrescator dupa numarul de hit-uri.
         */
        Collections.sort(tophitsAux, new Comparator<Map.Entry<K, Integer>>() {
            @Override
            public int compare(final Map.Entry<K, Integer> s1,
                         final Map.Entry<K, Integer> s2) {
                if (s1.getValue() < s2.getValue()) {
                    return 1;
                } else if (s1.getValue() > s2.getValue()) {
                    return -1;
                } else {
                    return 0;
                }
            }
         });

         /*
          * In lista ce va fi returnata se pun doar primele top key.
          */
         for (int i = 0; i < top; i++) {
             tophits.add(tophitsAux.get(i).getKey());
         }

         return tophits;
    }

    /**
     * Get the @top most missed keys.
     *
     * @param top number of top keys
     * @return the list of keys
     */
    public List<K> getTopMissedKeys(int top) {

        ArrayList<Map.Entry<K, Integer>> topmissAux = new
                ArrayList<Map.Entry<K, Integer>>();
        LinkedList<K> topmiss = new LinkedList<K>();

        /*
         * Se pun toate intrarile din onmiss intr-o lista auxiliara.
         */
        for (Map.Entry<K, Integer> entry : onmiss.entrySet()) {
            topmissAux.add(entry);
        }

        /*
         * Se sorteaza lista auxiliara descrescator dupa numarul de miss-uri.
         */
        Collections.sort(topmissAux, new Comparator<Map.Entry<K, Integer>>() {
            @Override
            public int compare(final Map.Entry<K, Integer> s1,
                                       final Map.Entry<K, Integer> s2) {
                if (s1.getValue() < s2.getValue()) {
                    return 1;
                } else if (s1.getValue() > s2.getValue()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });

        /*
         * In lista ce va fi returnata se pun doar primele top key.
         */
        for (int i = 0; i < top; i++) {
            topmiss.add(topmissAux.get(i).getKey());
        }

        return topmiss;
    }

    /**
     * Get the @top most updated keys.
     *
     * @param top number of top keys
     * @return the list of keys
     */
    public List<K> getTopUpdatedKeys(int top) {

        ArrayList<Map.Entry<K, Integer>> topupdatesaux = new ArrayList<Map.Entry<K, Integer>>();
        LinkedList<K> topupdates = new LinkedList<K>();

        /*
         * Se pun toate intrarile din onput intr-o lista auxiliara.
         */
        for (Map.Entry<K, Integer> entry : onput.entrySet()) {
            topupdatesaux.add(entry);
        }

        /*
         * Se sorteaza lista auxiliara descrescator dupa numarul de put-uri.
         */
        Collections.sort(topupdatesaux, new Comparator<Map.Entry<K, Integer>>() {
            @Override
            public int compare(final Map.Entry<K, Integer> s1, final Map.Entry<K, Integer> s2) {
                if (s1.getValue() < s2.getValue()) {
                    return 1;
                } else if (s1.getValue() > s2.getValue()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });

        /*
         * In lista ce va fi returnata se pun doar primele top key.
         */
        for (int i = 0; i < top; i++) {
            topupdates.add(topupdatesaux.get(i).getKey());
        }

        return topupdates;

    }

    /**
     * In cazul unui cacheHit, se incrementeaza numarul de hit-uri al cheii.
     *
     * @param key
     */
    public void onHit(final K key) {
        if (onhit.get(key) == null) {
            onhit.put(key, 1);
        } else {
            int oldValue = onhit.get(key);
            oldValue++;
            onhit.put(key, oldValue);
        }
    }

    /**
     * In cazul unui cacheMiss, se incrementeaza numarul de miss-uri al cheii.
     *
     * @param key
     */
    public void onMiss(final K key) {
        if (onmiss.get(key) == null) {
            onmiss.put(key, 1);
        } else {
            int oldValue = onmiss.get(key);
            oldValue++;
            onmiss.put(key, oldValue);
        }
    }

    /**
     * In cazul unui cachePut, se incrementeaza numarul de put-uri al cheii.
     *
     * @param key
     */
    public void onPut(final K key, final V value) {
        if (onput.get(key) == null) {
            onput.put(key, 1);
        } else {
            int oldValue = onput.get(key);
            oldValue++;
            onput.put(key, oldValue);
        }
    }
}
