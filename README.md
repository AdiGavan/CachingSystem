"Copyright [2017] Gavan Adrian-George, 324CA"

Nume, prenume: Gavan, Adrian-George

Grupa, seria: 324CA

Tema 3 - Caching System

Prezentarea implementarii
=========================

- Consider ca pattern-ul Observer a fost corect implementat prin utilizarea metodelor
onPut, onHit si onMiss si prin implementarea Observerilor (BroadcastListener,
KeyStatsListener, StatsListener si CacheListenerul din FileCache).

A. FileCache
1. Metoda createCacheListener:
- Metoda creaza un listener care in caz de cache Miss incarca fisierul in memorie.
Interfata CacheListener contine 3 metode, dar cum acest listener trebuie sa incarce
fisierul in memorie doar daca apare un cache Miss, trebuie implementata doar metoda
onMiss. Aceasta citeste intr-un "String" datele din fisier si le adauga in cache.

B. ObservableCache
- Contine 2 variabile, un CacheStalePolicy care retine variabila stalePolicy si o lista
de tipul CacheListener in care se retin listenerii cache-ului.
1. Metoda setStalePolicy atribuie variabilei stalePolicy policy-ul corespunzator.
2. Metoda setCacheListener adauga un cacheListener in lista de listeneri a cache-ului.
3. Metoda clearStaleEntries verifica (folosind metoda shouldRemoveEldestEntry a
stalePolicy) daca trebuie scos un element din cache. Daca trebuie scos, se apeleaza 
functia remove a cache-ului pentru elementul corespunzator (elementul este dat de
functia getEldestEntry).

C. ObservableFIFOCache
- Contine o variabila de tipul FIFOCache ("container") in care se retin datele din cache.
1. Metoda get:
- Se verifica daca cheia se gaseste deja in cache. Daca nu se gaseste, listenerii
cache-ului sunt informati ca a avut loc un miss (se apeleaza metoda onMiss a fiecarui
listener). Un lucru important este ca in acest moment se va incarca fisierul in memorie,
iar cand se va executa din nou bucla "do while" (clasa FileCache, metoda getFileContents),
de aceasta data se va gasi fisierul in memorie.
- Daca fisierul se gaseste in memorie, listenerii cache-ului sunt informati ca a avut loc
un cacheHit (se apeleaza metoda onHit a fiecarui listener).
2. Metoda put:
- Se adauga perechea de tipul cheie-valoare.
- Ascultatorii sunt notificati ca a avut loc un update (se apeleaza metoda onPut a fiecarui
ascultator).
- Se apeleaza metoda clearStaleEntries (daca capacitatea maxima a fost atinsa, se elimina
cel mai vechi element din cache).
3. Metoda size apeleaza metoda size a container-ului si se returneaza dimensiunea cacheului.
4. Metoda isEmpty returneaza true daca dimensiunea cacheului este 0, false altfel.
5. Metoda remove apeleaza metoda remove a container-ului si elimina o anume cheie din cache.
6. Metoda clearAll apeleaza metoda clearAll a conatinerului si goleste cashe-ul.
7. Metoda getEldestEntry apeleaza metoda container-ului si intoarce cel mai vechi element.

D. LRUCache:
- Pentru implementarea LRUCache se foloseste si o lista dublu inlantuita. Voi detalia
mai intai modul de implementare al listei.
a) DLLNode<K, V> este o clasa ce reprezinta un nod al liste. Contine cheia, valoarea, 
o referinta catre nodul urmator si o referinta catre nodul anterior (si getteri si setteri
pentru fiecare + metoda equals care compara cheia si valoarea nodurilor).
b) DoublyLinkedList<K, V>:
- Este o lista cu elemente DLLNode implementata generic. Contine 3 variabile: un intreg "size"
(pentru dimensiune), un DLLNode first si un DLLNode last (referinte catre primul si ultimul nod).
1. Metoda "size" returneaza dimensiunea listei, metoda "isEmpty" returneaza true daca dimensiunea
listei este 0, false altfel.
2. Metodele getFirst si getLast returneaza referinte catre primul, respectiv ultimul nod al listei.
3. Metoda addFirst adauga un element la inceputul listei si trateaza 2 cazuri: cand lista este goala
si cand lista contine deja elemente.
4. Metodele removeLast si removeFirst elimina ultimul, respectiv primul element din lista. Sunt asema-
natoare si trateaza 3 cazuri: cand lista este goala, cand este un singur element in lista si cand
sunt mai multe noduri in lista).
5. Metoda removeDLLNode elimina un anumit nod din lista. Daca lista este goala returneaza null. Daca
nodul este chiar primul nod se apeleaza metoda removeFirst. Daca nodul este chiar ultimul nod, se 
apeleaza metoda removeLast. Daca este un alt nod, se seteaza nodul anterior sa arate catre nodul
urmator (next va fi o referinta catre urmatorul nod), iar nodul urmator se seteaza sa arate catre
nodul anterior (previous va fi o referinta catre nodul anterior).
6. Metoda moveDLLNodeFirst muta un anumit nod la inceputul liste. Se elimina nodul din lista cu metoda
removeDLLNode si apoi se adauga nodul la inceputul listei cu metoda addFirst.
c) LRUCache:
- Contine 3 variabile, o lista dublu inlantuita "cache", un HashMap ce retine perechi de tipul
cheie - DLLNode aferent listei "cacheMap" si un Pair "eldestEntry" in care se va retine cel mai vechi
element din cache.
1. Metoda get:
- Verifica daca cheia se gaseste in cache (verificare simpla cu ajutorul HashMap-ului).
- Daca nu se gaseste, se notifica toti ascultatorii cache-ului ca a avut loc un cacheMiss.
- Daca se gaseste, se notifica toti ascultatorii cache-ului ca a avut loc un cacheHit si se muta
nodul corespunzator cheii pe prima pozitie in lista.
2. Metoda put:
- Sunt 2 cazuri. Cheia se gaseste in cache si se face doar update sau nu se gaseste in cache.
- Daca cheia sa gaseste, se retine vechiul nod din lista (cu ajutorul cacheMap), se actualizeaza
valoarea cheii din cacheMap (se face put de cheie si nodul cu noua valoare), se sterge vechiul nod
din lista "cache" si se adauga noul nod la inceputul listei. Se notifica toti ascultatorii ca a avut
loc un cachePut.
- Daca cheia nu se gaseste, se retine cel mai vechi nod (ultimul nod din lista), se adauga noul nod 
in cacheMap si in lista "cache" ca primul element. Se notifica toti ascultatorii ca a avut loc un
cachePut. Se apeleaza metoda clearStaleEntries pentru a sterge cel mai putin recent utilizat element
din cache, in caz ca cache-ul este plin.
3. Metoda size returneaza dimensiunea cache-ului (apeleaza metoda de size a HashMap-ului) si metoda
isEmpty returneaza true daca size este 0, false altfel.
4. Metoda remove elimina o anumita cheie din cache. Se apeleaza metoda removeDLLNode a listei si 
metoda remove a HashMap-ului.
5. Metoda clearALL goleste cache-ul. Pentru a goli HashMap-ul se apeleaza metoda clear(), iar pentru
a elimina elementele din lista se apeleaza removeLast cat timp dimensiunea listei este mai mare sau
egala cu 0.
6. Metoda getEldestEntry intoarce cea mai putin recent utilizata intrare. Se ia ultimul element din 
lista si se returneaza un Pair format din cheia si valoarea nodului.
7. Metoda clearStaleEntries verifica daca trebuie sters un element din cache (foloseste metoda
shouldRemoveEldestEntry a stalePolicy). Daca trebuie sters, se sterge ultimul element din lista
si cheia corespunzatoare din HashMap.

E. TimeAwareCache ofera aceeasi functionalitate ca si LRUCache, doar ca retine si timestamp-ul
fiecarui element. Se extinde clasa LRUCache, se suprascrie metoda get si setExpirePolicy.

F. BroadcastListener
- Acest listener trimite prin broadcast evenimentele cache-ului catre toti ascultatorii.
1. Metoda addListener adauga un listener la lista cu ascultatori (se apeleaza metoda add a listei).
2. Metoda onHit apeleaza metoda onHit a fiecarui ascultator din lista.
3. Metoda onMiss apeleaza metoda onMiss a fiecarui ascultator din lista.
4. Metoda onPut apeleaza metoda onPut a fiecarui ascultator din lista.

G. KeyStatsListener
- Utilizeaza 3 variabile de tip TreeMap<K, Integer>, cate unul pentru onHit, onMiss, onPut. Fiecare
retine de cate ori a avut loc un eveniment pentru un anumit fisier.
1. Metoda getKeyHits apeleaza metoda get(key) a TreeMap-ului onhit.
2. Metoda getKeyMisses apeleaza metoda get(key) a TreeMap-ului onmiss.
3. Metoda getKeyUpdates apeleaza metoda get(key) a TreeMap-ului onput.
4. Metodele getTopHitKeys, getTopMissedKeys si getTopUpdatedKeys au acelasi algoritm, diferind doar
variabila TreeMap asupra careia se efectueaza operatiile.
- Se creeaza o lista auxiliara in care se pun toate entry-urile din TreeMap.
- Se sorteaza lista auxiliara folosind un comparator custom (se sorteaza descrescator).
- Se iau primele "top" chei din lista auxiliara si se pun in lista ce va fi returnata.
5. Metodele onHit, onMiss si onPut au acelasi algoritm, difera doar TreeMap-ul folosit.
- Se verifica daca cheia exista deja in TreeMap sau nu.
- Daca cheia nu exista, se adauga cheia cu valoarea 1 (se apeleaza put(key, 1)).
- Daca cheia exista, se ia vechea valoare din TreeMap (get(key)), se incrementeaza valoarea si se
pune in TreeMap cheia cu noua valoare (put(key, value) - fiind TreeMap => nu exista chei duplicate,
deci va suprascrie vechea valoare).

H. StatsListener
- Utilizeaza 3 variabile in care se retin numarul total de miss-uri, update-uri si hit-uri.
1. Metodele getHits, getMisses si getUpdates doar returneaza una din cele 3 variabile, in functie de
metoda.
2. Metodele onMiss, onHit si onPut incrementeaza una din variabile, in functie de metoda.

