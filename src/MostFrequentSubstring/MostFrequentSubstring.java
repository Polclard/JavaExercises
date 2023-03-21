package MostFrequentSubstring;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
;

class MapEntry<K extends Comparable<K>,E> implements Comparable<K> {

    K key;
    E value;

    public MapEntry (K key, E val) {
        this.key = key;
        this.value = val;
    }

    public int compareTo (K that) {
        @SuppressWarnings("unchecked")
        MapEntry<K,E> other = (MapEntry<K,E>) that;
        return this.key.compareTo(other.key);
    }

    public String toString () {
        return "(" + key + "," + value + ")";
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

    private SLLNode<MapEntry<K,E>>[] buckets;

    @SuppressWarnings("unchecked")
    public CBHT(int m) {
        buckets = (SLLNode<MapEntry<K,E>>[]) new SLLNode[m];
    }

    private int hash(K key) {
        return Math.abs(key.hashCode()) % buckets.length;
    }

    public SLLNode<MapEntry<K,E>> search(K targetKey) {
        int b = hash(targetKey);
        for (SLLNode<MapEntry<K,E>> curr = buckets[b]; curr != null; curr = curr.succ) {
            if (targetKey.equals(((MapEntry<K, E>) curr.element).key))
                return curr;
        }
        return null;
    }

    public void insert(K key, E val) {		// Insert the entry <key, val> into this CBHT.
        MapEntry<K, E> newEntry = new MapEntry<K, E>(key, val);
        int b = hash(key);
        for (SLLNode<MapEntry<K,E>> curr = buckets[b]; curr != null; curr = curr.succ) {
            if (key.equals(((MapEntry<K, E>) curr.element).key)) {
                curr.element = newEntry;
                return;
            }
        }
        buckets[b] = new SLLNode<MapEntry<K,E>>(newEntry, buckets[b]);
    }

    public void delete(K key) {
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

class Word {
    String word;
    int occurences;

    public Word(String word, int occurences)
    {
        this.occurences = occurences;
        this.word = word;
    }

    public int getOccurences() {
        return occurences;
    }

    public String getWord() {
        return word;
    }

    public void setOccurences(int occurences) {
        this.occurences = occurences;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public String toString() {
        return word + ":" + occurences;
    }
}

public class MostFrequentSubstring {

    static boolean isItIn(String element, ArrayList<Word>arr)
    {
        for(int i = 0; i < arr.size(); i++)
        {
            if(arr.get(i).word.compareTo(element) == 0)
                return true;
        }
        return false;
    }

    public static void main (String[] args) throws IOException{
        CBHT<String,Integer> tabela = new CBHT<String,Integer>(300);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String word = br.readLine().trim();


        int number = 0;

        ArrayList<String> listOfWords = new ArrayList<String>();

        for(int i = 0; i < word.length()+1; i++)
        {
            for(int j = i+1; j < word.length()+1; j++)
            {
                number++;
//                System.out.println("=> " + word.substring(i, j));
                String currentWord = word.substring(i, j);
                listOfWords.add(currentWord);
            }
        }
        CBHT<String, ArrayList<Word>> hashTable = new CBHT<String, ArrayList<Word>>(number);

        ArrayList<Word> words = new ArrayList<Word>();

        for(int i = 0; i < listOfWords.size(); i++)
        {
            int counter = 0;
           for(int j = 0; j < listOfWords.size(); j++)
           {
               if(listOfWords.get(i).compareTo(listOfWords.get(j)) == 0)
               {
                   counter++;
               }
           }
            Word wrd = new Word(listOfWords.get(i),counter);
            if(!isItIn(wrd.word, words))
            {
                words.add(wrd);
            }
        }

        int maxOccurences = words.get(0).occurences;
        String mostFrequentWord = words.get(0).word;
        Word wrd = words.get(0);
        for(int i = 0; i < words.size(); i++)
        {
            if(words.get(i).occurences == maxOccurences && words.get(i).word.length() == mostFrequentWord.length())
            {
                if(words.get(i).word.compareTo(mostFrequentWord) < 0)
                {
                    wrd = words.get(i);
                    maxOccurences = words.get(i).occurences;
                    mostFrequentWord = words.get(i).word;
                }
            }
            else if(words.get(i).occurences >= maxOccurences && words.get(i).word.length() >= mostFrequentWord.length())
            {
                if(words.get(i).word.compareTo(mostFrequentWord) < 0)
                {

                }else
                {
                    wrd = words.get(i);
                    maxOccurences = words.get(i).occurences;
                    mostFrequentWord = words.get(i).word;
                }
//                wrd = words.get(i);
//                maxOccurences = words.get(i).occurences;
//                mostFrequentWord = words.get(i).word;
            }
        }

//        System.out.println(Arrays.toString(words.toArray()));
//        System.out.println("Length: " + words.size());

//        System.out.println(wrd.word +  wrd.occurences);
        System.out.println(wrd.word);

//        System.out.println(hashTable.toString());
//        System.out.println(Arrays.toString(listOfWords.toArray()));
//        System.out.println("number: " + number);
    }
}