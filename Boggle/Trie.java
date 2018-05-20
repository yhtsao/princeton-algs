import edu.princeton.cs.algs4.StdOut;

public class Trie<Value> {
    private static final int R = 26;        // extended ASCII

    public TrieNode root;      // root of trie
    private int n;          // number of keys in trie

    /**
     * Initializes an empty string symbol table.
     */
    public Trie() {
    }

    /**
     * Returns the value associated with the given key.
     *
     * @param key the key
     * @return the value associated with the given key if the key is in the symbol table
     * and {@code null} if the key is not in the symbol table
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Value get(String key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        TrieNode x = get(root, key, 0);
        if (x == null) return null;
        return (Value) x.val;
    }

    /**
     * Does this symbol table contain the given key?
     *
     * @param key the key
     * @return {@code true} if this symbol table contains {@code key} and
     * {@code false} otherwise
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public boolean contains(String key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    public TrieNode get(TrieNode x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) return x;
        char c = key.charAt(d);
        int position = c - 'A';
        return get(x.next[position], key, d + 1);
    }

    public Value getNonRecursive(String key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");

        TrieNode x = getNonRecursive(root, key, 0);
        if (x == null) return null;
        return (Value) x.val;
    }

    public TrieNode getNonRecursive(TrieNode x, String key, int d) {
        if (x == null) return null;
        TrieNode nextNode = x;
        for (; d < key.length(); d++) {
            char c = key.charAt(d);
            int position = c - 'A';
            if (nextNode.next[position] == null) {
                nextNode = null;
                break;
            }
            nextNode = nextNode.next[position];
        }
        return nextNode;
    }

    /**
     * Inserts the key-value pair into the symbol table, overwriting the old value
     * with the new value if the key is already in the symbol table.
     * If the value is {@code null}, this effectively deletes the key from the symbol table.
     *
     * @param key the key
     * @param val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(String key, Value val) {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");
        if (val == null) delete(key);
        else root = put(root, key, val, 0);
    }

    private TrieNode put(TrieNode x, String key, Value val, int d) {
        if (x == null) x = new TrieNode();
        if (d == key.length()) {
            if (x.val == null) n++;
            x.val = val;
            return x;
        }
        char c = key.charAt(d);
        int position = c - 'A';
        x.next[position] = put(x.next[position], key, val, d + 1);
        return x;
    }

    /**
     * Returns the number of key-value pairs in this symbol table.
     *
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return n;
    }

    /**
     * Is this symbol table empty?
     *
     * @return {@code true} if this symbol table is empty and {@code false} otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    public TrieNode keyNodeWithPrefix(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException("calls keysWithPrefix() with null argument");
        }
        TrieNode x = get(root, prefix, 0);
        return x;
    }

    /**
     * Removes the key from the set if the key is present.
     *
     * @param key the key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void delete(String key) {
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");
        root = delete(root, key, 0);
    }

    private TrieNode delete(TrieNode x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) {
            if (x.val != null) n--;
            x.val = null;
        } else {
            char c = key.charAt(d);
            int position = c - 'A';
            x.next[position] = delete(x.next[position], key, d + 1);
        }

        // remove subtrie rooted at x if it is completely empty
        if (x.val != null) return x;
        for (int c = 0; c < R; c++) {
            //int position = c - 'A';
            if (x.next[c] != null)
                return x;
        }
        return null;
    }
}