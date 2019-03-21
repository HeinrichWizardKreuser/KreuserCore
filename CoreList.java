/*******************************************************************************
 *
 *  CoreList is a custom implimentation of an ArrayList-like iterable Collection/List.
 *
 *  The library offers some methods that ArrayList doesn't:
 *      - max() returns the maximum in the list
 *      - min() returns the minimum in the list
 *      - getRandom() returns a random Key in the list
 *      - removeKey and removeIndex are seperate to avoid the case of an <Integer> list
 *      - ConcurrentModificationException fix:
 *          You are able to remove elements during for-each enhanced forloop iterations
 *          without experiencing errors
 *      - otherThanKey() returns an iterable list of all keys except the specified
 *      - sorted() returns a sorted version of the list in the order specified ("min" or "max")
 *
 *  @author Heinrich Kreuser
 *  Date: 21 March 2019
 *
 *
 * MIT License
 *
 * Copyright (c) [year] [fullname]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *******************************************************************************/
import java.util.Iterator;
import java.util.Arrays;

public class CoreList<K extends Comparable<K>> implements Iterable<K> {
    // The array of the Keys (will alwats be a multiple of 10)
    private K[] list;

    // The current size of the defined elements in the array
    private int N;

    // Constructor using a pre-existing array
    @SuppressWarnings("unchecked")
    public CoreList(K[] arr) {
        this.N = arr.length;
        this.list = (K[])new Comparable[10*(N/10) + 10];
        for (int i = 0; i < N; i++) {
            list[i] = arr[i];
        }
    }

    // Standard constructor
    public CoreList() {
        clear();
    }

    /**
     * @return the size of the list
     */
    public int size() {
        return N;
    }

    // Clears the list, might as well construct a new list
    @SuppressWarnings("unchecked")
    public void clear() {
        this.list = (K[])new Comparable[10];
        this.N = 0;
    }

    /**
     * @param k is a given key added to the list
     */
    @SuppressWarnings("unchecked")
    public void add(K k) {
        if (this.N == list.length) {
            K[] newList = (K[])new Comparable[N+10];
            for (int i = 0; i < N; i++) {
                newList[i] = list[i];
            }
            list = newList;
        }
        list[N] = k;
        N++;
    }

    /**
     * @return the given key k
     * @param k is removed from the list if the list contains it
     */
    public K removeKey(K k) {
        if (k == null) return null;
        for (int i = 0; i < N; i++) {
            if (list[i] == k) {
                for (; i < N-1; i++) {
                    list[i] = list[i+1];
                }
                list[N-1] = null;
                N--;
                return k;
            }
        }
        throw new IllegalArgumentException("List doesn't contain element ");
    }

    /**
     * @return the key at the specifeid index after removing it
     * @param i is the index removed from the list if it is within bounds
     */
    public K removeIndex(int i) {
        if (i < 0 || N <= i) throw new IndexOutOfBoundsException(i + " for size " +N);
        return removeKey(list[i]);
    }

    /**
     * @param k is the given key for which we will be searching the index for
     * @return the index of the given key if it is contained in the list
     */
    public int indexOf(K k) {
        for (int i = 0; i < N; i++) {
            if (list[i] == k) {
                return i;
            }
        }
        throw new IllegalArgumentException("List doesn't contain element");
    }

    /**
     * @param i is the index of the key we need to retrieve
     * @return the key at the specified index i
     */
    public K get(int i) {
        if (i < 0 || N <= i) throw new IndexOutOfBoundsException(i + " for size " +N);
        return list[i];
    }

    /**
     * @param k is the key we are searching the list for
     * @return true if k is contained within the list
     */
    public boolean contains(K k) {
        for (int i = 0; i < N; i++) {
            if (list[i] == k) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return an array version of the list
     */
    @SuppressWarnings("unchecked")
    public K[] toArray() {
        K[] arr = (K[])new Comparable[N];
        for (int i = 0; i < N; i++) {
            arr[i] = list[i];
        }
        return arr;
    }

    /**
     * @return a random element from the list
     */
    public K getRandom() {
        int i = (int)(Math.random()*N);
        return list[i];
    }

    /**
     * @return a sorted version of the list in ascending order
     */
    public CoreList<K> sorted() {
        return sorted("min");
    }

    /**
     * @param order is the specified order in which to sort the list. "min" and "max"
     *  will sort the list in asceninding and descending order respectively
     * @return a sorted version of the list in the specified order
     */
    public CoreList<K> sorted(String order) {
        K[] sorted = this.toArray();
        Arrays.sort(sorted);
        if (order.equals("max")) {
            for (int i = 0; i < N/2; i++) {
                K temp = sorted[i];
                sorted[i] = sorted[N-1-i];
                sorted[N-1-i] = temp;
            }
        }
        return new CoreList<>(sorted);
    }

    /**
     * @return the key with the largest value in the list
     */
    public K max() {
        K max = list[0];
        for (int i = 1; i < N; i++) {
            max = list[i].compareTo(max) > 0 ? list[i] : max;
        }
        return max;
    }

    /**
     * @return the key with the smallest value in the list
     */
    public K min() {
        K min = list[0];
        for (int i = 1; i < N; i++) {
            min = list[i].compareTo(min) < 0 ? list[i] : min;
        }
        return min;
    }

    /**
     * @return a copy of the list that avoids pointer errors with it's predecessor
     */
    public CoreList<K> copy() {
        CoreList<K> copy = new CoreList<>();
        for (int i = 0; i < N; i++) {
            copy.add(list[i]);
        }
        return copy;
    }

    /**
     * The iterator for the use of for-each enhanced forloop iteration.
     * It avoids ConcurrentModificationException that is thrown when elements are removed from
     * the list durind iteration without any extra work required from the user (like directly)
     * using the class' Iterator)
     * @return the iterator of the list
     */
    @Override
    public Iterator<K> iterator() {
        return new Iterator<K>() {

            private int j = 0;
            private int last = 0;
            private K lastElement = null;

            @Override
            public boolean hasNext() {
                return j < N;
            }

            @Override
            public K next() {
                // assert that the last index and last element are equal
                if (lastElement == null || list[last] == lastElement) {
                    last = j;
                    lastElement = list[last];
                    return list[j++];
                }
                // if they are not, then return the list[last]
                lastElement = list[last];
                j = last;
                return list[last];
            }

            @Override
            public void remove() {
                // do nothing
            }
        };
    }

    /**
     * The iterator for the use of for-each enhanced forloop iteration, but skips
     * the first appearance of the specified ke. For use when we want to compare an element
     * to all other elements and thus reduces the need for the user to add an extra if
     * statement during the iteration.
     * It avoids ConcurrentModificationException that is thrown when elements are removed from
     * the list durind iteration without any extra work required from the user (like directly)
     * using the class' Iterator)
     * @return the iterator of the list discluding the first appearance of the specified key
     */
    public Iterable<K> otherThanKey(K k) {
        return new OtherThanKey(k);
    }
    private class OtherThanKey implements Iterable<K> {
        private K skipKey;
        public OtherThanKey(K k) {
            this.skipKey = k;
        }
        @Override
        public Iterator<K> iterator() {
            return new Iterator<K>() {

                private int j = 0;
                private int last = 0;
                private K lastElement = null;
                private int skip = indexOf(skipKey);

                @Override
                public boolean hasNext() {
                    if (j == skip) j++;
                    return j < N;
                }

                @Override
                public K next() {
                    // assert that the last index and last element are equal
                    if (lastElement == null || list[last] == lastElement) {
                        last = j;
                        lastElement = list[last];
                        return list[j++];
                    }
                    // if they are not, then return the list[last]
                    lastElement = list[last];
                    j = last;
                    return list[last];
                }

                @Override
                public void remove() {
                    // do nothing
                }
            };
        }
    }
}
