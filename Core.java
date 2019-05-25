import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
// for sorting
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import static java.util.stream.Collectors.toMap;
import static java.util.Map.Entry.comparingByValue;

/******************************************************************************
 * Welcome to the KreuserCore Engine v5. It provides us with some shortcuts to
 * widely used methods for efficiency.
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
 ******************************************************************************/
public class Core {

  /***************************************************************************
   *                            DEBUGGING
   ***************************************************************************/
  /** Defines whether methods that print will print when called. */
  private static boolean active = true;

  /** Turns active on/ sets it to true */
  public static void on() {
    active = true;
  }

  /** Turns active off/ sets it to false */
  public static void off() {
    active = false;
  }

  /**
   * Displays the given message with the current time to the terminal
   * @param message is the message it will display
   */
  public static void log(String message) {
    if (active) {
      System.out.println(now() + " " + message);
    }
  }

  /**
   * Displays the given message, waits for 2 seconds then kills the program
   * @param message is what it displays along with the current time and "ERROR"
   */
  public static void exit(String message) {
    System.out.println(now() + " ERROR: " + message);
    wait(2000);
    System.exit(0);
  }

  /** Closes the program */
  public static void exit() {
    System.exit(0);
  }

  /** Prints the current stack trace */
  public static void trace() {
    System.out.println(Thread.currentThread().getStackTrace());
  }

  /**
   * Returns false after printing the specified message. For use when returning
   * false from a method, but also wanting to print a statement as it returns
   * @param message is the message to display
   * @return false irrelevant
   */
  public static boolean returnFalse(String message) {
    if (active) {
      System.out.println(now() + " " + message);
    }
    return false;
  }

  /**
   * Returns true after printing the specified message. For use when returning
   * true from a method, but also wanting to print a statement as it returns
   * @param message is the message to display
   * @return true irrelevant
   */
  public static boolean returnTrue(String message) {
    if (active) {
      System.out.println(now() + " " + message);
    }
    return true;
  }

  /**
   * Shortcut for System.out.println()
   * @param message is the message to print to the terminal
   */
  public static void println(String message) {
    System.out.println(message);
  }

  /**
   * The format in which now() returns the current time when called
   */
  private static final String DATE = "[HH:mm:ss.SSS]";

  /**
   * Gets the current time in the format of date (above)
   * @return the current time in the format in date (above)
   */
  public static String now() {
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat(DATE);
    return dateFormat.format(calendar.getTime());
  }

  /** Freezes the program until enter is pressed */
  public static void freeze() {
    if (!active) {
      return;
    }
    System.out.println("Froze program, press enter to continue");
    (new Scanner(System.in)).nextLine();
  }

  /**
   * Displays the given message, then waits for enter to be pressed
   * @param message is the specified message displayed when called
   */
  public static void freeze(String message) {
    if (!active) {
      return;
    }
    log("Frozen: " + message + " (Press enter to continue)");
    (new Scanner(System.in)).nextLine();
  }

  /**
   * Waits for the specified amount of milliseconds
   * @param mil is the amount of milliseconds to wait
   */
  public static void wait(int mil) {
    long end = time() + mil;
    while (time() < end) {
      int i = 0; // do nothing
    }
  }

  /**
   * Checks whether check is equal to answer and prints out answer
   *
   * @param check the value to check whether is right
   * @param correct the correct value check must be
   */
  public static void test(double check, double correct) {
    if (!active){
      return;
    }
    if (check != correct) {
      println("FAILED TEST: " + check + " != answer " + check);
    }
  }

  /**
   * Checks whether the boolean outcome is true and if it isn't, reports the
   * given message to the terminal
   *
   * @param outcome the boolean to assert is true
   * @param msg the message to be printed to the terminal
   */
  public static void test(boolean outcome, String msg) {
    if (!active){
      return;
    }
    if (!outcome) {
      println("FAILED TEST: " + msg);
    }
  }

  /***************************************************************************
   *                            SORTING
   ***************************************************************************/
  /**
   * Given a hashmap of keys and values (where the values are doubles), returns
   * an arraylist of the keys sorted based on the values in the order specified
   *
   * @param <T> is the generic type of the hashmap
   * @param toSort is a given hashamp with any Key data type and Value as double
   * @param order specifies sorting order (max = descending, min = ascending)
   * @return an arraylist of the keys sorted by their original mapped value in
   *         the hashmap
   */
  public static <T> ArrayList<T> sort(HashMap<T, Double> toSort,
      String order) {
    if (order.equals("max")) {
      HashMap<T, Double> sorted = toSort
        .entrySet()
        .stream()
        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
        .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
          LinkedHashMap::new));
      ArrayList<T> toReturn = new ArrayList<>(0);
      for (T t : sorted.keySet()) {
        toReturn.add(t);
      }
      return toReturn;
    } else if (!order.equals("min")) {
      throw new IllegalArgumentException("illegal order specification '"
        + order
        + "'! (only 'min' or 'max' accepted)");
    }
    HashMap<T, Double> sorted = toSort
      .entrySet()
      .stream()
      .sorted(comparingByValue())
      .collect(toMap(e -> e.getKey(),  e -> e.getValue(),
        (e1, e2) -> e2, LinkedHashMap::new));
    ArrayList<T> toReturn = new ArrayList<>(0);
    for (T t : sorted.keySet()) {
      toReturn.add(t);
    }
    return toReturn;
  }

  /***************************************************************************
   *                           INPUT/OUTPUT
   ***************************************************************************/
  /**
   * Reads from the given file stores the contents into an string arraylist
   *
   * @param filename is the file to read from
   * @return a list filled with the contents of that file
   */
  public static ArrayList<String> readFrom(String filename) {
    ArrayList<String> readFrom = new ArrayList<>(0);
    Scanner reader = null;
    try {
      reader = new Scanner(new File(filename));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      exit("Could not find file " + filename + "!");
    }
    while (reader.hasNextLine()) {
      readFrom.add(reader.nextLine());
    }
    reader.close();
    return readFrom;
  }

  /**
   * Reads from the terminal until done and returns the lines in a list
   *
   * @return a list filled with the contents of what was inputted into the
   *         terminal
   */
  public static ArrayList<String> readTerminal() {
    ArrayList<String> readTerminal = new ArrayList<>(0);
    Scanner terminal = new Scanner(System.in);
    while (terminal.hasNextLine()) {
      readTerminal.add(terminal.nextLine());
    }
    terminal.close();
    return readTerminal;
  }

  /**
   * One instance of terminal reading
   *
   * @return the latest thing typed into the terminal
   */
  public static String read() {
    Scanner sc = new Scanner(System.in);
    String read = sc.nextLine();
    sc.close();
    return read;
  }

  /**
   * Gets all files from a given folder in the form of a hashmap with keys being
   * the name of the files and the value mapped to being the entries of the file
   * in the form of an string arraylist
   *
   * @param folder is the folder to read files from
   * @return the filenames mapped to string arraylist housing their contents
   */
  public static HashMap<String, ArrayList<String>> allFiles(String folder) {
    HashMap<String, ArrayList<String>> allFiles = new HashMap<>();
    File[] filesList = (new File(folder)).listFiles();
    if (!folder.endsWith("/")) {
      folder += "/";
    }
    for (File file : filesList) {
      if (file.isFile()) {
        String name = file.getName();
        allFiles.put(name, readFrom(folder + name));
      }
    }
    return allFiles;
  }

  /**
   * Retrieves a list of all the file names in the given folder
   *
   * @param folder is the folder to get the file names from
   * @return an string arraylist containing the name of all files in the folder
   */
  public static ArrayList<String> listFiles(String folder) {
    ArrayList<String> listFiles = new ArrayList<>(0);
    File[] filesList = (new File(folder)).listFiles();
    for (File file : filesList) {
      listFiles.add(file.getName());
    }
    return listFiles;
  }

  /**
   * Writes the given contents to the given filename
   *
   * @param filename is the file to write the contents to
   * @param contents is an string arraylist containin the contents to write
   */
  public static void writeTo(String filename, ArrayList<String> contents) {
    PrintWriter writer = null;
    try {
      writer = new PrintWriter(filename);
    } catch (FileNotFoundException e) {
    }
    for (String line : contents) {
      writer.println(line);
    }
    writer.close();
  }

  /***************************************************************************
   *                        SOME USEFUL METHODS
   ***************************************************************************/
  /**
   * Creates a .java file containing the standard main(String[] args) method
   * @param args needs to contain the specified name for the .java file
   */
  public static void main(String[] args) {
    if (args.length == 0) {
      System.out.println("Please enter a filename!");
      return;
    }
    String fileName = args[0] + ".java";
    PrintWriter writer = null;
    try {
      writer = new PrintWriter(fileName);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    writer.println("public class " + args[0] + " {\n");
    writer.println("\tpublic static void main(String[] args) {\n\t\t\n\t}\n}");
    writer.close();
  }

  /**
   * Shortcut for System.currentTimeMillis()
   *
   * @return the current time in milliseconds
   */
  public static long time() {
    return System.currentTimeMillis();
  }

  /**
   * Tells us how much time has elapsed since the given time
   *
   * @param time is the time we've been waiting since
   * @return the time elapsed since time
   */
  public static long timeElapsed(long time) {
    return time() - time;
  }

  /**
   * Gets us the subtring that lies between the given pattern, example:
   * let string s = "<import>../constants/dna/[time].dna". By calling
   * capture(s, "<", ">") returns us "import".
   *
   * @param s is the string to capture something from
   * @param start is the prefix to sart cutting from
   * @param end is the suffix to stop cutting from
   * @return the substring that lies between braces[0] and braces[1] disclusive
   */
  public static String capture(String s, String start, String end) {
    if (start.equals("")) {
      return s.substring(0, s.indexOf(end));
    } else if (end.equals("")) {
      return s.substring(s.indexOf(start) + start.length());
    }
    return s.substring(s.indexOf(start) + start.length(), s.indexOf(end));
  }

  /**
   * Confirms whether the given array contains the given value
   *
   * @param ts the array to check whether it contains t
   * @param t is the value to check whether it is in the list
   * @return true if the array contains the given value
   */
  public static <T> boolean contains(T[] ts, T t) {
    for (T i : ts) {
      if (i == t) {
        return true;
      }
    }
    return false;
  }
}
