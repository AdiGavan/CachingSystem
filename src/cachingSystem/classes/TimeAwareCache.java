package cachingSystem.classes;

import java.sql.Timestamp;

import cachingSystem.interfaces.CacheStalePolicy;
import dataStructures.classes.Pair;


/**
 * The TimeAwareCache offers the same functionality as the LRUCache, but also stores a timestamp for
 * each element. The timestamp is updated after each get / put operation for a key. This
 * functionality allows for time based cache stale policies (e.g. removing entries that are older
 * than 1 second).
 */
public class TimeAwareCache<K, V> extends LRUCache<K, V> {
    
    /**
     * Intoarce valoarea asociata unei chei sau null daca cheia nu exista in
     * cache.
     *
     * @param key
     * @return valoarea
     */
	public V get(K key) {
       clearStaleEntries();

       V value = null;

       if (cacheMap.get(key) == null) {
           /*
            * Ascultatorii sunt notificati ca a avut loc un miss.
            */
    	   for (int i = 0; i < listeners.size(); i++) {
               listeners.get(i).onMiss(key);
           }
       } else {
           V tempVal = cache.removeDLLNode(cacheMap.get(key));
           DLLNode<K, V> first = new DLLNode<K,V>(key, tempVal);
           cache.addFirst(first);
           value = cacheMap.get(key).getValue();
           /*
            * Ascultatorii sunt notificati ca a avut loc un hit.
            */
           for (int i = 0; i < listeners.size(); i++) {
               listeners.get(i).onHit(key);
           }
       }
       return value;
   }

    /**
     * Get the timestamp associated with a key, or null if the key is not stored in the cache.
     *
     * @param key the key
     * @return the timestamp, or null
     */
    public Timestamp getTimestampOfKey(K key) {
    	return cacheMap.get(key).getTimestamp();
    }

    /**
     * Set a cache stale policy that should remove all elements older than @millisToExpire
     * milliseconds.
     *
     * @param millisToExpire the expiration time (milliseconds)
     */
    public void setExpirePolicy(long millisToExpire) {
    	  setStalePolicy(new CacheStalePolicy<K, V>() {
              @Override
              public boolean shouldRemoveEldestEntry(Pair<K, V> entry) {
                  if (entry != null) {
                      Timestamp currentTime = new Timestamp(System.currentTimeMillis());
                      Timestamp maxExpireTime = new Timestamp(currentTime.getTime() - millisToExpire);
                      /* 
                       * Returneaza adevarat daca timestamp-ul nu este dupa timpul maxim. 
                       */
                      return !getTimestampOfKey(entry.getKey()).after(maxExpireTime);
                  }
                  return false;
              }
          });
    }
}
