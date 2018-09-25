package cachingSystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import cachingSystem.classes.LRUCache;
import cachingSystem.classes.ObservableCache;
import cachingSystem.classes.ObservableFIFOCache;
import cachingSystem.classes.TimeAwareCache;
import cachingSystem.interfaces.CacheStalePolicy;
import dataStructures.classes.Pair;
import observerPattern.classes.BroadcastListener;
import observerPattern.interfaces.CacheListener;

public final class FileCache {

    public enum Strategy {
        FIFO,
        LRU,
    }

    public static cachingSystem.FileCache createCacheWithCapacity(
            cachingSystem.FileCache.Strategy strategy, int capacity) {
        ObservableCache<String, String> dataCache;

        switch (strategy) {

            case FIFO:
                dataCache = new ObservableFIFOCache<>();
                break;
            case LRU:
                dataCache = new LRUCache<>();
                break;
            default:
                throw new IllegalArgumentException("Unsupported cache strategy: " + strategy);
        }

        dataCache.setStalePolicy(new CacheStalePolicy<String, String>() {
            @Override
            public boolean shouldRemoveEldestEntry(Pair<String, String> entry) {
                return dataCache.size() > capacity;
            }
        });

        return new cachingSystem.FileCache(dataCache);
    }

    public static cachingSystem.FileCache createCacheWithExpiration(long millisToExpire) {
        TimeAwareCache<String, String> dataCache = new TimeAwareCache<>();

        dataCache.setExpirePolicy(millisToExpire);

        return new cachingSystem.FileCache(dataCache);
    }

    private FileCache(ObservableCache<String, String> dataCache) {
        this.dataCache = dataCache;
        this.broadcastListener = new BroadcastListener<>();

        this.dataCache.setCacheListener(broadcastListener);

        broadcastListener.addListener(createCacheListener());
    }

    /**
     * Metoda createCacheListener creeaza un listener care incarca un
     * fisier in memorie cand apare un cacheMiss.
     *
     * Cum doar in cazul unui cacheMiss se incarca fisierul in memorie,
     * metodele onHit si onPut nu fac nimic in acest caz.
     *
     * @return cacheListener
     */

    private CacheListener<String, String> createCacheListener() {
        return new CacheListener<String, String>() {
            @Override
            public void onHit(final String key) {
            }

            public void onPut(final String key, final String value) {
            }

            /*
             * Se citesc datele intr-un string si apoi se apeleaza metoda
             * "put" a cache-ului.
             * @see observerPattern.interfaces.CacheListener#onMiss(java.lang.Object)
             */

            public void onMiss(final String key) {
                File file = new File(key);
                String value = "";
                try {
                    Scanner scan = new Scanner(file);
                    while (scan.hasNextLine()) {
                        value += scan.nextLine();
                    }
                    scan.close();
                    dataCache.put(key, value);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public String getFileContents(String path) {
        String fileContents;

        do {
            fileContents = dataCache.get(path);
        } while (fileContents == null);

        return fileContents;
    }

    public void putFileContents(String path, String contents) {
        dataCache.put(path, contents);
    }

    public void addListener(CacheListener<String, String> listener) {
        broadcastListener.addListener(listener);
    }

    private ObservableCache<String, String> dataCache;
    private BroadcastListener<String, String> broadcastListener;
}
