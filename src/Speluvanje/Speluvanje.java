package Speluvanje;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

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


class OBHT<K extends Comparable<K>,E> {

    private MapEntry<K,E>[] buckets;
    static final int NONE = -1; // ... distinct from any bucket index.
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static final MapEntry former = new MapEntry(null, null);
    private int occupancy = 0;

    @SuppressWarnings("unchecked")
    public OBHT (int m) {
        buckets = (MapEntry<K,E>[]) new MapEntry[m];
    }

    private int hash (K key) {
        return Math.abs(key.hashCode()) % buckets.length;
    }

    public MapEntry<K,E> getBucket(int i){
        return buckets[i];
    }

    public int search (K targetKey) {
        int b = hash(targetKey); int n_search=0;
        for (;;) {
            MapEntry<K,E> oldEntry = buckets[b];
            if (oldEntry == null)
                return NONE;
            else if (targetKey.equals(oldEntry.key))
                return b;
            else{
                b = (b + 1) % buckets.length;
                n_search++;
                if(n_search==buckets.length)
                    return NONE;
            }
        }
    }

    public void insert (K key, E val) {
        MapEntry<K,E> newEntry = new MapEntry<K,E>(key, val);
        int b = hash(key); int n_search=0;

        for (;;) {
            MapEntry<K,E> oldEntry = buckets[b];
            if (oldEntry == null) {
                if (++occupancy == buckets.length) {
                    System.out.println("Hash tabelata e polna!!!");
                }
                buckets[b] = newEntry;
                return;
            } else if (oldEntry == former
                    || key.equals(oldEntry.key)) {
                buckets[b] = newEntry;
                return;
            } else{
                b = (b + 1) % buckets.length;
                n_search++;
                if(n_search==buckets.length)
                    return;

            }
        }
    }

    @SuppressWarnings("unchecked")
    public void delete (K key) {
        int b = hash(key); int n_search=0;
        for (;;) {
            MapEntry<K,E> oldEntry = buckets[b];

            if (oldEntry == null)
                return;
            else if (key.equals(oldEntry.key)) {
                buckets[b] = former;
                return;
            } else{
                b = (b + 1) % buckets.length;
                n_search++;
                if(n_search==buckets.length)
                    return;

            }
        }
    }

    public String toString () {
        String temp = "";
        for (int i = 0; i < buckets.length; i++) {
            temp += i + ":";
            if (buckets[i] == null)
                temp += "\n";
            else if (buckets[i] == former)
                temp += "former\n";
            else
                temp += buckets[i] + "\n";
        }
        return temp;
    }
}


class Zbor implements Comparable<Zbor>{
    String zbor;

    public Zbor(String zbor) {
        this.zbor = zbor;
    }
    @Override
    public boolean equals(Object obj) {
        Zbor pom = (Zbor) obj;
        return this.zbor.equals(pom.zbor);
    }
    @Override
    public int hashCode() {
        return Math.abs(zbor.hashCode()) % 20;
    }
    @Override
    public String toString() {
        return zbor;
    }
    @Override
    public int compareTo(Zbor arg0) {
        return zbor.compareTo(arg0.zbor);
    }
}

public class Speluvanje {
    public static void main(String[] args) throws IOException {
        OBHT<Zbor, String> tabela;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        //---Vie odluchete za goleminata na hesh tabelata----
        tabela = new OBHT<Zbor,String>(N+2);

        ArrayList<String> listOfWords = new ArrayList<String>();
        for(int i = 0; i < N; i++)
        {
            String word = br.readLine();
            listOfWords.add(word);
            Zbor newZbor = new Zbor(word);
            tabela.insert(newZbor, word);
        }

        String text = br.readLine();
        String [] splittedString = text.split(" ");


        OBHT<Integer, Character> interpunction = new OBHT<Integer, Character>(N+2);
        for(int i = 0; i < splittedString.length; i++)
        {
            if(splittedString[i].contains("."))
            {
                splittedString[i] = splittedString[i].replace(".", "");
                interpunction.insert(i, '.');
            }
            else if(splittedString[i].contains(","))
            {
                splittedString[i] = splittedString[i].replace(",", "");
                interpunction.insert(i, ',');
            }
            else if(splittedString[i].contains("!"))
            {
                splittedString[i] = splittedString[i].replace("!", "");
                interpunction.insert(i, '!');
            }
            else if(splittedString[i].contains("?"))
            {
                splittedString[i] = splittedString[i].replace("?", "");
                interpunction.insert(i, '?');
            }
        }


        ArrayList<Zbor> wrongWords = new ArrayList<Zbor>();

        for(int i = 0; i < splittedString.length; i++)
        {
            Zbor wordToSearch = new Zbor(splittedString[i].toLowerCase());
            if(tabela.search(wordToSearch) == OBHT.NONE && wordToSearch.compareTo(new Zbor("")) != 0)
            {
                wrongWords.add(wordToSearch);
            }
        }

        if(wrongWords.size() == 0)
        {
            System.out.println("Bravo");
        }
        else{
            for(int i = 0; i < wrongWords.size(); i++)
            {
                System.out.println(wrongWords.get(i).zbor);
            }
        }

//        System.out.println(wrongWords.size());
//        System.out.println(Arrays.toString(wrongWords.toArray()));
//        System.out.println( wrongWords.get(0).compareTo(new Zbor("")) == 0);
//        System.out.println(Arrays.toString(splittedString));
//        System.out.println(Arrays.toString(listOfWords.toArray()));
//        System.out.println(text);
//        System.out.println(tabela.toString());
//        System.out.println(interpunction.toString());


        /*
                166
a
abacus
abdomen
academy
accelerate
accelerator
accent
adaptable
adapter
administrator
adventure
aeolian
aerodrome
affidavit
after
again
age
agency
agile
ago
agony
ahead
aid
air
alabaster
alarm
albino
album
alcohol
also
am
an
and
angle
animal
animals
ant
august
avenue
aviation
bear
bird
birds
birds
bought
bull
bus
but
by
cages
came
came
camel
cat
chicken
cow
crocodile
days
deer
different
dog
donkey
duck
ducks
ebony
eight
elephant
elephants
entered
etc
experience
fast
father
find
fish
five
four
frog
gate
giraffes
goat
going
green
grow
happy
have
hen
home
horse
i
in
interesting
is
it
it
kangaroos
kinds
lampshade
lawyer
like
lion
lizard
lost
lot
maybe
monkey
monkeys
mouse
my
nine
nitrogen
now
of
one
out
parrot
pig
ponds
puma
rabbit
returned
rhinoceros
rhinos
rooster
round
sad
sat
saw
seen
seven
shark
shell
six
snake
stags
swimming
ten
the
then
there
three
tickets
tiger
tigers
tired
to
today
too
tree
turtle
two
under
up
very
visit
want
was
we
went
were
when
will
worm
you
zebra
zoo
.


         */
    }
}
