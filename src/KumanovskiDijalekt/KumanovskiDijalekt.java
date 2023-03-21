package KumanovskiDijalekt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;

class MapEntry<K extends Comparable<K>,E> implements Comparable<K> {

    // Each KumanovskiDijalekt.MapEntry object is a pair consisting of a key (a Comparable
    // object) and a value (an arbitrary object).
    K key;
    E value;

    public MapEntry (K key, E val) {
        this.key = key;
        this.value = val;
    }

    public int compareTo (K that) {
        // Compare this map entry to that map entry.
        @SuppressWarnings("unchecked")
        MapEntry<K,E> other = (MapEntry<K,E>) that;
        return this.key.compareTo(other.key);
    }

    public String toString () {
        return "<" + key + "," + value + ">";
    }
}

class SLLNode<E> {
    protected E element;
    protected SLLNode<E> succ;

    public SLLNode(E elem, SLLNode<E> succ) {
        this.element = elem;
        this.succ = succ;
    }

    @Override
    public String toString() {
        return element.toString();
    }
}

class CBHT<K extends Comparable<K>, E> {

    // An object of class KumanovskiDijalekt.CBHT is a closed-bucket hash table, containing
    // entries of class KumanovskiDijalekt.MapEntry.
    private SLLNode<MapEntry<K,E>>[] buckets;

    @SuppressWarnings("unchecked")
    public CBHT(int m) {
        // Construct an empty KumanovskiDijalekt.CBHT with m buckets.
        buckets = (SLLNode<MapEntry<K,E>>[]) new SLLNode[m];
    }

    private int hash(K key) {
        return Math.abs(key.hashCode()) % buckets.length;
    }

    public SLLNode<MapEntry<K,E>> search(K targetKey) {
        // Find which if any node of this KumanovskiDijalekt.CBHT contains an entry whose key is
        // equal
        // to targetKey. Return a link to that node (or null if there is none).
        int b = hash(targetKey);
        for (SLLNode<MapEntry<K,E>> curr = buckets[b]; curr != null; curr = curr.succ) {
            if (targetKey.equals(((MapEntry<K, E>) curr.element).key))
                return curr;
        }
        return null;
    }

    public void insert(K key, E val) {		// Insert the entry <key, val> into this KumanovskiDijalekt.CBHT.
        MapEntry<K, E> newEntry = new MapEntry<K, E>(key, val);
        int b = hash(key);
        for (SLLNode<MapEntry<K,E>> curr = buckets[b]; curr != null; curr = curr.succ) {
            if (key.equals(((MapEntry<K, E>) curr.element).key)) {
                // Make newEntry replace the existing entry ...
                curr.element = newEntry;
                return;
            }
        }
        // Insert newEntry at the front of the 1WLL in bucket b ...
        buckets[b] = new SLLNode<MapEntry<K,E>>(newEntry, buckets[b]);
    }

    public void delete(K key) {
        // Delete the entry (if any) whose key is equal to key from this KumanovskiDijalekt.CBHT.
        int b = hash(key);
        for (SLLNode<MapEntry<K,E>> pred = null, curr = buckets[b]; curr != null; pred = curr, curr = curr.succ) {
            if (key.equals(((MapEntry<K,E>) curr.element).key)) {
                if (pred == null)
                    buckets[b] = curr.succ;
                else
                    pred.succ = curr.succ;
                return;
            }
        }
    }

    public String toString() {
        String temp = "";
        for (int i = 0; i < buckets.length; i++) {
            temp += i + ":";
            for (SLLNode<MapEntry<K,E>> curr = buckets[i]; curr != null; curr = curr.succ) {
                temp += curr.element.toString() + " ";
            }
            temp += "\n";
        }
        return temp;
    }

}

public class KumanovskiDijalekt {

    static int hash(String str, int length)
    {
        char[] arr = str.toLowerCase().toCharArray();
        return (int)arr[0] + (int)arr[1] + (int)arr[2] % length;
    }

    public static void main (String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(
                System.in));
        int N = Integer.parseInt(br.readLine());

        String rechnik[] = new String[N];
        for (int i = 0; i < N; i++) {
            rechnik[i] = br.readLine();
        }
        String tekst = br.readLine();

        if(N == 0)
        {
            System.out.println(tekst);
            return ;
        }
        CBHT<String, String> hashTable = new CBHT<String, String>(rechnik.length);

        for (int i = 0; i < rechnik.length; i++) {
            String zborNaKumanovski = rechnik[i].split(" ")[0].toLowerCase();
            String zborNaMakedonski = rechnik[i].split(" ")[1].toLowerCase();

            hashTable.insert(zborNaKumanovski, zborNaMakedonski);
        }

        String[] splittedTextArray = tekst.split(" ");

        HashMap<Integer, Character> table = new HashMap<>();

        for(int i = 0; i < splittedTextArray.length; i++)
        {
            if(splittedTextArray[i].contains(","))
            {
                splittedTextArray[i] = splittedTextArray[i].replace(",", "");
                table.put(i, ',');
            }
            else if(splittedTextArray[i].contains("."))
            {
                splittedTextArray[i] = splittedTextArray[i].replace(".", "");
                table.put(i, '.');
            }
            else if(splittedTextArray[i].contains("!"))
            {
                splittedTextArray[i] = splittedTextArray[i].replace("!", "");
                table.put(i, '!');
            }
            else if(splittedTextArray[i].contains("?"))
            {
                splittedTextArray[i] = splittedTextArray[i].replace("?", "");
                table.put(i, '?');
            }

        }


        StringBuilder str = new StringBuilder();
        for (int i = 0; i < splittedTextArray.length; i++)
        {
            boolean golemaPrvaBukva = false;
            if(splittedTextArray[i].charAt(0) == splittedTextArray[i].toUpperCase().charAt(0))
            {
                golemaPrvaBukva = true;
            }

            if(hashTable.search(splittedTextArray[i].toLowerCase()) != null)
            {
                String theNewWordWithCap = "";
                if(golemaPrvaBukva == true)
                {
                    String theWord1 = hashTable.search(splittedTextArray[i].toLowerCase()).element.value.substring(0,1).toUpperCase();
                    String theWord2= hashTable.search(splittedTextArray[i].toLowerCase()).element.value.substring(1);
                    theNewWordWithCap = theWord1 + theWord2;
                }
                else
                {
//                    System.out.println(hashTable.search(splittedTextArray[i].toLowerCase()).element);
                    theNewWordWithCap = hashTable.search(splittedTextArray[i].toLowerCase()).element.value;
                }
                str.append(theNewWordWithCap + " ");
            }
            else
            {
                str.append(splittedTextArray[i]+ " ");
            }

        }

        String[] spllitedFinalSentence = str.toString().split(" ");

        for(int i = 0; i < spllitedFinalSentence.length; i++){
            if(table.get(i) != null)
            {
                String newStr = spllitedFinalSentence[i] + table.get(i);
                spllitedFinalSentence[i] = newStr;
            }
        }


        StringBuilder finalString = new StringBuilder();
        for(int i = 0; i < spllitedFinalSentence.length; i++)
        {
         finalString.append(spllitedFinalSentence[i] + " ");
        }


        System.out.println(finalString);
    }
}
