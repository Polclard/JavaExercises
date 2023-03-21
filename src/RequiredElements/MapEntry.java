package RequiredElements;

public class MapEntry<K extends Comparable<K>,E> implements Comparable<K> {

    // Each MapEntry object is a pair consisting of a key (a Comparable
    // object) and a value (an arbitrary object).
    K key;
    E value;

    @SuppressWarnings("unchecked")
    public MapEntry (K key, E val)
    {
        this.key = key;
        this.value = val;
    }

    public int compareTo (K that) {
    // Compare this map entry to that map entry.4
        @SuppressWarnings("unchecked")
		MapEntry<K,E> other = (MapEntry<K,E>) that;
        return this.key.compareTo(other.key);
    }

    public String toString () {
        return "<" + key + "," + value + ">";
    }

    public E getValue() {
        return value;
    }

    public K getKey() {
        return key;
    }
}
