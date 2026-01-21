import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class WordNet {

    private final SAP sap;
    private final HashMap<Integer, String> idToSynset;
    private final HashMap<String, HashSet<Integer>> wordToIds;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        In in = new In(synsets);
        idToSynset = new HashMap<>();
        wordToIds = new HashMap<>();

        String line = in.readLine();
        while (line != null) {
            String[] parts = line.split(",");
            idToSynset.put(Integer.parseInt(parts[0]), parts[1]);
            for (String word : parts[1].split(" ")) {
                wordToIds.computeIfAbsent(word, k -> new HashSet<>())
                         .add(Integer.parseInt(parts[0]));
            }
            line = in.readLine();
        }

        Digraph G = new Digraph(idToSynset.size());

        In in2 = new In(hypernyms);
        String line2 = in2.readLine();
        while (line2 != null) {
            String[] parts = line2.split(",");
            for (int i = 1; i < parts.length; ++i) {
                G.addEdge(Integer.parseInt(parts[0]), Integer.parseInt(parts[i]));
            }
            line2 = in2.readLine();
        }

        findRoot(G);
        DirectedCycle finder = new DirectedCycle(G);
        if (finder.hasCycle()) throw new IllegalArgumentException("graph has a cycle");

        sap = new SAP(G);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return wordToIds.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) throw new IllegalArgumentException("word is null");
        return wordToIds.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        List<HashSet<Integer>> ids = getIds(nounA, nounB);
        return sap.length(ids.get(0), ids.get(1));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        List<HashSet<Integer>> ids = getIds(nounA, nounB);
        return idToSynset.get(sap.ancestor(ids.get(0), ids.get(1)));
    }

    private List<HashSet<Integer>> getIds(String wordA, String wordB) {
        HashSet<Integer> idsA = wordToIds.get(wordA);
        HashSet<Integer> idsB = wordToIds.get(wordB);

        if (idsA == null || idsA.isEmpty()) {
            throw new IllegalArgumentException(wordA + " is not a noun");
        }
        if (idsB == null || idsB.isEmpty()) {
            throw new IllegalArgumentException(wordB + " is not a noun");
        }

        return List.of(idsA, idsB);
    }

    private int findRoot(Digraph graph) {
        int root = -1;

        for (int v = 0; v < graph.V(); v++) {
            if (graph.outdegree(v) == 0) {
                if (root != -1) {
                    throw new IllegalArgumentException("Graph has more than one root");
                }
                root = v;
            }
        }

        if (root == -1) {
            throw new IllegalArgumentException("Graph has no root");
        }

        return root;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wn = new WordNet(args[0], args[1]);
        while (!StdIn.isEmpty()) {
            String v = StdIn.readString();
            String w = StdIn.readString();
            int distance = wn.distance(v, w);
            String sap = wn.sap(v, w);
            StdOut.printf("distance = %d, sap = %s\n", distance, sap);
        }
    }
}
