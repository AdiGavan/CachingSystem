package cachingSystem.classes;

import java.util.HashMap;
import java.sql.Timestamp;

import dataStructures.classes.Pair;

/**
 * Aceasta clasa reprezinta un nod al listei dublu inlantuite.
 * Contine o cheie, o valoare si alte 2 noduri.
 * Un nod arata catre urmatorul nod din lista, iar celalalt este o
 * referinta catre nodul anterior din lista.
 *
 * @author Gavan Adrian
 *
 * @param <K>
 * @param <V>
 */
class DLLNode<K, V> {
    private K key;
    private V value;
    private DLLNode<K, V> previous;
    private DLLNode<K, V>  next;
    private Timestamp timestamp;

    DLLNode(final K key, final V value) {
        this.key = key;
        this.value = value;
        timestamp = new Timestamp(System.currentTimeMillis());
    }

    public K getKey() {
        return key;
    }

    public void setKey(final K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(final V value) {
        this.value = value;
    }

    public DLLNode<K, V>  getPre() {
        return previous;
    }

    public void setPre(final DLLNode<K, V>  pre) {
        this.previous = pre;
    }

    public DLLNode<K, V>  getNext() {
        return next;
    }

    public void setNext(final DLLNode<K, V>  next) {
        this.next = next;
    }

    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        int aux1 = 0;
        int aux2 = 0;
        if (key != null) {
            aux1 = key.hashCode();
        }
        if (value != null) {
            aux2 = value.hashCode();
        }
        result = prime * result + aux1;
        result = prime * result + aux2;
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this.key.equals(((DLLNode<K, V>) obj).getKey())
                && this.value.equals(((DLLNode<K, V>) obj).getValue())) {
            return true;
        } else {
            return false;
        }
    }
}

/**
 * Aceasta clasa reprezinta o lista dublu inlantuita.
 * Utilizeaza 3 variabile, una pentru dimensiune si 2 noduri.
 * Un nod va fi o referinta catre primul nod al listei, iar
 * celalalt va fi o referinta catre ultimul nod din lista.
 *
 * @author Gavan Adrian
 *
 * @param <K>
 * @param <V>
 */
class DoublyLinkedList<K, V> {
    private int size;
    private DLLNode<K, V> first;
    private DLLNode<K, V> last;

    DoublyLinkedList() {
        first = null;
        last = null;
        size = 0;
    }

    /**
     * Verifica daca lista este goala.
     *
     * @return true sau false
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Returneaza dimensiunea listei.
     *
     * @return dimensiune
     */
    public int size() {
        return size;
    }

    /**
     * Returneaza primul nod din lista.
     *
     * @return primul nod
     */
    public DLLNode<K, V> getFirst() {
        return first;
    }

    /**
     * Returneaza ultimul nod din lista.
     *
     * @return ultimul nod
     */
    public DLLNode<K, V> getLast() {
        return last;
    }

    /**
     * Adauga un nod nou la inceputul listei.
     *
     * @param node
     */
    public void addFirst(final DLLNode<K, V> node) {
        /*
         * Cazul cand lista este goala.
         */
        if (first == null) {
            node.setPre(null);
            node.setNext(null);
            first = node;
            last = node;
        /*
         * Cazul cand lista nu este goala.
         */
        } else {
            node.setNext(first);
            node.setPre(null);
            first.setPre(node);
            first = node;
        }
        size++;
    }

    /**
     * Sterge ultimul nod din lista.
     *
     * @return valoare nod
     */
    public V removeLast() {
        V aux = null;
        /*
         * Cazul cand lista este goala.
         */
        if (first == null) {
            return null;
        }
        /*
         * Cazul cand este doar un nod in lista.
         */
        if (first == last) {
            aux = last.getValue();
            first = null;
            last = null;
            size--;
            return aux;
        /*
         * Cazul cand sunt mai multe noduri in lista.
         */
        } else {
            aux = last.getValue();
            last = last.getPre();
            last.setNext(null);
            size--;
            return aux;
        }
    }

    /**
     * Sterge primul nod din lista.
     *
     * @return valoare nod
     */
    public V removeFirst() {
        V aux = null;
        /*
         * Cazul cand lista este goala.
         */
        if (first == null) {
            return null;
        }
        /*
         * Cazul cand este doar un nod in lista.
         */
        if (first == last) {
            aux = first.getValue();
            first = null;
            last = null;
            size--;
            return aux;
        /*
         * Cazul cand sunt mai multe noduri in lista.
         */
        } else {
            aux = first.getValue();
            first = first.getNext();
            first.setPre(null);
            size--;
            return aux;
        }
    }

    /**
     * Sterge un anume nod din lista.
     *
     * @return valoare nod
     */
    public V removeDLLNode(final DLLNode<K, V> node) {
        V aux = null;
        /*
         * Cazul cand lista este goala.
         */
        if (first == null) {
            return null;
        }
        /*
         * Cazul cand nodul ce trebuie scos este primul nod.
         */
        if (node.equals(first)) {
            aux = removeFirst();
            return aux;
        }
        /*
         * Cazul cand nodul ce trebuie scos este ultimul.
         */
        if (node.equals(last)) {
            aux = removeLast();
            return aux;
        }
        /*
         *  Cazul cand nu e nici primul nici ultimul.
         */
        node.getPre().setNext(node.getNext());
        node.getNext().setPre(node.getPre());

        return node.getValue();
    }

    /**
     * Functia muta un anumit nod de pe pozitia lui pe prima pozitie din lista.
     *
     * @param node
     */
    public void moveDLLNodeFirst(final DLLNode<K, V> node) {
        this.removeDLLNode(node);
        this.addFirst(node);
    }
}

/**
 * This cache is very similar to the FIFOCache, but guarantees O(1) complexity for the get, put and
 * remove operations.
 */
public class LRUCache<K, V> extends ObservableCache<K, V> {

    protected DoublyLinkedList<K, V> cache = new DoublyLinkedList<K, V>();
    protected HashMap<K, DLLNode<K, V>> cacheMap = new
                                                 HashMap<K, DLLNode<K, V>>();
    private Pair<K, V> eldestEntry;

    /**
     * Intoarce valoarea asociata unei chei sau null daca cheia nu exista in
     * cache.
     *
     * @param key
     * @return valoarea
     */
    public V get(final K key) {
        DLLNode<K, V> aux2 = null;
        aux2 = cacheMap.get(key);
        V aux = null;
        if (aux2 == null) {
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
            aux = cacheMap.get(key).getValue();
            cache.moveDLLNodeFirst(cacheMap.get(key));
        }
        return aux;
    }

    /**
     * Insereaza o pereche cheie-valoare in cache.
     *
     * @param key, value
     */
    public void put(final K key, final V value) {
        if (cacheMap.containsKey(key)) {
            DLLNode<K, V> aux = new DLLNode<K, V>(key, value);
            DLLNode<K, V> aux2 = cacheMap.get(key);
            cacheMap.put(key, aux);
            cache.removeDLLNode(aux2);
            cache.addFirst(cacheMap.get(key));
            /*
             * Ascultatorii sunt notificati ca a avut loc un put.
             */
            for (int i = 0; i < listeners.size(); i++) {
                listeners.get(i).onPut(key, value);
            }
        } else {
            DLLNode<K, V> aux = new DLLNode<K, V>(key, value);
            eldestEntry = getEldestEntry();
            cacheMap.put(key, aux);
            cache.addFirst(cacheMap.get(key));
            /*
             * Ascultatorii sunt notificati ca a avut loc un put.
             */
            for (int i = 0; i < listeners.size(); i++) {
                listeners.get(i).onPut(key, value);
            }

            this.clearStaleEntries();

        }
    }

    /**
     * Returneaza dimensiunea cache-ului.
     *
     * @return dimensiune cache.
     */
    public int size() {
        return cacheMap.size();
    }

    /**
     * Returneaza true daca cache-ul este gol, false altfel.
     *
     * @return true sau false
     */
    public boolean isEmpty() {
        return (cacheMap.size() == 0);
    }

    /**
     * Sterge o anumita cheie din cache si returneaza valoarea acesteia.
     *
     * @param key
     * @return value
     */
    public V remove(final K key) {
        V aux;
        aux = cache.removeDLLNode(cacheMap.get(key));
        aux = cacheMap.remove(key).getValue();
        return aux;
    }

    /**
     * Goleste cache-ul.
     */
    public void clearAll() {
        cacheMap.clear();
        while (cache.size() >= 0) {
            cache.removeLast();
        }
    }

    /**
     * Returneaza ce mai veche pereche cheie-valoare din cache.
     *
     * @return cea mai veche pereche
     */
    public Pair<K, V> getEldestEntry() {
        if (this.isEmpty()) {
            return null;
        }
        DLLNode<K, V> aux = cache.getLast();
        Pair<K, V> aux2 = new Pair<K, V>(aux.getKey(), aux.getValue());
        return aux2;
    }

    /**
     * Sterge cea mai veche pereche cheie-valoare din cache.
     */
    public void clearStaleEntries() {
        if (stalePolicy.shouldRemoveEldestEntry(getEldestEntry())) {
            cache.removeLast();
            cacheMap.remove(eldestEntry.getKey());
        }
    }

}
