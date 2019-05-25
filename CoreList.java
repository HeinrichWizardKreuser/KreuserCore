import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Predicate;
import java.util.stream.Stream;

/*******************************************************************************
 *
 * CoreList is a custom implementation of an ArrayList-like iterable
 * Collection/List.
 *
 * The library offers some methods that ArrayList doesn't:
 *  * max() returns the maximum in the list.
 *  * min() returns the minimum in the list.
 *  * getRandom() returns a random Key in the list.
 *  * removeKey and removeIndex are separate to avoid the case of an list.
 *  * ConcurrentModificationException fix: You are able to remove elements
 *    during for-each enhanced forloop iterations without experiencing errors.
 *  * otherThanKey() returns an iterable list of all keys except the specified.
 *  * sorted() returns a sorted version of the list in the order specified
 *    ("min" or "max").
 *
 * @param <K> the data type of the Keys
 *
 * @author Heinrich Kreuser
 *
 * Date: 25 May 2019
 *
 *         MIT License
 *
 *         Copyright (c) [2019] [Heinrich Kreuser]
 *
 *         Permission is hereby granted, free of charge, to any person obtaining
 *         a copy of this software and associated documentation files (the
 *         "Software"), to deal in the Software without restriction, including
 *         without limitation the rights to use, copy, modify, merge, publish,
 *         distribute, sublicense, and/or sell copies of the Software, and to
 *         permit persons to whom the Software is furnished to do so, subject to
 *         the following conditions:
 *
 *         The above copyright notice and this permission notice shall be
 *         included in all copies or substantial portions of the Software.
 *
 *         THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *         EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *         MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *         NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 *         BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 *         ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 *         CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *         SOFTWARE.
 *****************************************************************************/
public class CoreList<K extends Comparable<K>> implements Iterable<K> {

  /** The array of the Keys (will always be a multiple of 10) */
  private K[] list;

  /** The current size of the defined elements in the array */
  private int n;

  /**
   * Constructor using a pre-existing array.
   *
   * @param arr the array to construct the list from
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  public CoreList(K[] arr) {
    this.n = arr.length;
    this.list = (K[]) new Comparable[10 * (n / 10) + 10];
    for (int i = 0; i < n; i++) {
      list[i] = arr[i];
    }
  }

  /** Standard constructor */
  public CoreList() {
    clear();
  }

  /**
   * Constructor for when accidently including an integer is added a parameter.
   * @param i does nothing
   */
  public CoreList(int i) {
    clear();
  }

  /**
   * Retrieves the amount of elements in the list.
   *
   * @return the size of the list
   */
  public int size() {
    return n;
  }

  /**
   * Checks whether the list contains zero elements.
   *
   * @return true if the list is empty
   */
  public boolean isEmpty() {
    return n == 0;
  }

  /**
   * Clears the list, might as well construct a new list.
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  public void clear() {
    this.list = (K[]) new Comparable[10];
    this.n = 0;
  }

  /**
   * Adds the given key to the list.
   *
   * @param k a given key added to the list
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
   public void add(K k) {
    if (this.n == list.length) {
      K[] newList = (K[]) new Comparable[n + 10];
      for (int i = 0; i < n; i++) {
        newList[i] = list[i];
      }
      list = newList;
    }
    list[n] = k;
    n++;
  }

  /**
   * Adds the given key at the given index and moves everything else up.
   *
   * @param k a given key added to the list
   * @param index a given index to add the key at
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  public void addAt(K k, int index) {
    if (this.n == list.length) {
      K[] newList = (K[]) new Comparable[n + 10];
      for (int i = 0; i < n; i++) {
        newList[i] = list[i];
      }
      list = newList;
    }
    // now move it downwards
    for (int i = n; i > index; i--) {
      list[i] = list[i - 1];
    }
    list[index] = k;
    n++;
  }

  /**
   * Removes the first appearance of the specified key from the list.
   *
   * @param k the key to be removed from the list if the list contains it
   */
  public void removeKey(K k) {
    if (k == null) {
      return;
    }
    for (int i = 0; i < n; i++) {
      if (list[i] == k) {
        for (; i < n - 1; i++) {
          list[i] = list[i + 1];
        }
        list[n - 1] = null;
        n--;
        return;
      }
    }
  }

  /**
   * Removes the element at the given index and returns the key at that index.
   *
   * @param i the index removed from the list if it is within bounds
   * @return the key at the specified index after removing it
   */
  public K removeIndex(int i) {
    if (i < 0 || n <= i) {
      throw new IndexOutOfBoundsException(i + " for size " + n);
    }
    K k = list[i];
    removeKey(k);
    return k;
  }

  /**
   * retrieves the index of the specified key.
   *
   * @param k the given key for which we will be searching the index for
   * @return the index of the given key if it is contained in the list
   */
  public int indexOf(K k) {
    for (int i = 0; i < n; i++) {
      if (list[i] == k) {
        return i;
      }
    }
    throw new IllegalArgumentException("List doesn't contain element");
  }

  /**
   * Gets the element with the specified index.
   *
   * @param i the index of the key we need to retrieve
   * @return the key at the specified index i
   */
  public K get(int i) {
    if (i < 0 || n <= i) {
      throw new IndexOutOfBoundsException(i + " for size " + n);
    }
    return list[i];
  }

  /**
   * Checks whether the list contains the given key.
   *
   * @param k the key we are searching the list for
   * @return true if k is contained within the list
   */
  public boolean contains(K k) {
    for (int i = 0; i < n; i++) {
      if (list[i] == k) {
        return true;
      }
    }
    return false;
  }

  /**
   * Retrieves an array version of the list.
   *
   * @return an array version of the list
   */
   @SuppressWarnings({"unchecked", "rawtypes"})
  public K[] toArray() {
    K[] arr = (K[]) new Comparable[n];
    for (int i = 0; i < n; i++) {
      arr[i] = list[i];
    }
    return arr;
  }

  /**
   * Gets a random element from the list.
   *
   * @return a random element from the list
   */
  public K getRandom() {
    int i = randomIndex();
    return list[i];
  }

  /**
   * Gets a number between 0 and the list size.
   *
   * @return a random index from the list
   */
  public int randomIndex() {
    return (int) (Math.random() * n);
  }

  /**
   * Gets a random element from the list and then removes it.
   *
   * @return a random element from the list
   */
  public K removeRandom() {
    int i = randomIndex();
    K k = list[i];
    removeIndex(i);
    return k;
  }

  /**
   * Gets a random element that is not the given key.
   *
   * @param k the key that may not be chosen
   * @return a random element that is not the given key
   */
  public K getRandomOtherThan(K k) {
    int indexOfK = 0;
    try {
      indexOfK = indexOf(k);
    } catch (IllegalArgumentException e) {
      return list[randomIndex()];
    }
    int i = randomIndex();
    while (i == indexOfK) {
      i = randomIndex();
    }
    return list[i];
  }

  /**
   * Gets a sorted version of the list in ascending order.
   *
   * @return a sorted version of the list in ascending order
   */
  public CoreList<K> sorted() {
    return sorted("min");
  }

  /**
   * Retrieves a sorted version of the list in the order specified.
   *
   * @param order the specified order in which to sort the list. "min" and
   *        "max" will sort the list in asceninding and descending order
   *        respectively
   * @return a sorted version of the list in the specified order
   */
  public CoreList<K> sorted(String order) {
    K[] sorted = this.toArray();
    Arrays.sort(sorted);
    if (order.equals("max")) {
      for (int i = 0; i < n / 2; i++) {
        K temp = sorted[i];
        sorted[i] = sorted[n - 1 - i];
        sorted[n - 1 - i] = temp;
      }
    }
    return new CoreList<>(sorted);
  }

  /**
   * Gets the first element in the list.
   *
   * @return the first element in the list
   */
  public K first() {
    return list[0];
  }

  /**
   * Gets the last element in the list.
   *
   * @return the last element in the list
   */
  public K last() {
    return list[n - 1];
  }

  /**
   * Retrieves the size-1.
   *
   * @return the index of the last element in the array
   */
  public int lastIndex() {
    return n - 1;
  }

  /**
   * Retrieves the largest element in the list.
   *
   * @return the key with the largest value in the list
   */
  public K max() {
    K max = list[0];
    for (int i = 1; i < n; i++) {
      if (list[i].compareTo(max) > 0) {
        max = list[i];
      }
    }
    return max;
  }

  /**
   * Retrieves the smallest element in the list.
   *
   * @return the key with the smallest value in the list
   */
  public K min() {
    K min = list[0];
    for (int i = 1; i < n; i++) {
      if (list[i].compareTo(min) < 0) {
        min = list[i];
      }
    }
    return min;
  }

  /**
   * Gets a clone of the list that avoids pointer errors with it's predecessor.
   *
   * @return a copy of the list
   */
  public CoreList<K> copy() {
    CoreList<K> copy = new CoreList<>();
    for (int i = 0; i < n; i++) {
      copy.add(list[i]);
    }
    return copy;
  }

  /**
   * stream() method for use with lambda expressions such as forEach
   *
   * @return the contents of the list as a stream
   */
  public Stream<K> stream() {
    return Arrays.stream(Arrays.copyOf(list, n));
  }

  /**
   * Returns a filtered list where all elements fulfill the conditions of the
   * given predicate
   *
   * @param predicate is the Predicate Lambda epression that will return a
   *        boolean stating whether an element fulfills a condition
   * @return a corelist of which the elements all meet the predicate requirement
   */
  public CoreList<K> filter(Predicate<K> predicate) {
    CoreList<K> filter = new CoreList<>();
    for (K k : this) {
      if (predicate.test(k)) {
        filter.add(k);
      }
    }
    return filter;
  }

  /**
   * The iterator for the use of for-each enhanced forloop iteration. It avoids
   * ConcurrentModificationException that is thrown when elements are removed
   * from the list during iteration without any extra work required from the
   * user (like directly) using the class' Iterator.
   *
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
        return j < n;
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
        int i = 0; // do nothing
      }
    };
  }

  /**
   * The iterator for the use of for-each enhanced forloop iteration, but skips
   * the first appearance of the specified key. For use when we want to compare
   * an element to all other elements and thus reduces the need for the user to
   * add an extra if statement during the iteration. It avoids
   * ConcurrentModificationException that is thrown when elements are removed
   * from the list durind iteration without any extra work required from the
   * user (like directly) using the class' Iterator).
   * @param k the key to skip during iteration
   * @return the iterator of the list not including the first appearance of the
   *         specified key
   */
  public Iterable<K> otherThanKey(K k) {
    CoreList<K> copy = copy();
    copy.removeKey(k);
    return copy;
  }
}
