/*
 * EZJavaTextParser
 * EZJavaTextParserTester.java
 * Purpose: Allows users to test the parser
 *
 * @author Jayden Navarro
 * @version 1.0 12/20/13
 */

public class EZJavaTextParserTester {
    public static void main(String[] args) {
        //Only accepts a word followed by ": " and then a double
        EZJavaTextParser ez1 = new EZJavaTextParser("word: double");

        //Accepts a word followed by ":\t" and then a double anywhere within a string
        EZJavaTextParser ez2 = new EZJavaTextParser("word: double", true, true);

        boolean doesItMatch = ez1.matches("hello: 34.5");
        //Prints out true because hello is a word. It is followed by ": " and then a double
        System.out.println(doesItMatch);

        doesItMatch = ez1.matches("hello: 34");
        //Prints out false because hello is a word. It is followed by ": " and then an integer. It only accepts a double.
        System.out.println(doesItMatch);

        doesItMatch = ez1.matches("hello: 34.6 hi");
        //Prints out false because hello is a word. It is followed by ": " and then an double. But because of the space
        //and then the word "hi" at the end it is false.
        System.out.println(doesItMatch);

        doesItMatch = ez2.matches("hello: 34.6 hi");
        //Prints out true because hello is a word. It is followed by ": " and then an double. Because of the true
        //parameters in the constructor this means that "word: double" can have trailing junk or junk before.
        System.out.println(doesItMatch);


        ez1.setPattern("integer:\tdouble"); //allows you to reset the pattern


        Integer[] ints = ez2.getIntegers(); //doubles are stored in this array as well. 56.7 would be stored as 56 followed by 7.
        String[] words = ez2.getWords(); //this is where the words are stored


        String[] pattern = ez2.getPattern(); //use this to check that your pattern is stored correctly

        for (int i = 0; i < ints.length; i++) {
            System.out.println("Integers: " + ints[i]); //Prints out integers (and doubles)
        }

        for (int i = 0; i < words.length; i++) {
            System.out.println("Words: " + words[i]); //Prints out words
        }

        for (int i = 0; i < pattern.length; i++) {
            System.out.println("Pattern: " + pattern[i]); //Prints out the pattern.
        }
    }
}
