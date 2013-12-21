import java.util.ArrayList;

/*
 * EZJavaTextParser
 * EZJavaTextParser.java
 * Purpose: Allows users to easily parse strings
 *
 * @author Jayden Navarro
 * @version 1.0 12/20/13
 */

public class EZJavaTextParser {

    private ArrayList<String> pattern;

    private ArrayList<String> words;
    private ArrayList<Integer> integers;

    final private int doubleLoc = 1;
    final private int wordLoc = 0;
    final private int integerLoc = 2;

    final private String[] keys = {"word", "double", "integer"};
    private boolean afterOkay; //flag
    private boolean beforeOkay; //flag


    public EZJavaTextParser() {
        initializeValues();
        afterOkay = false;
        beforeOkay = false;
    }

    public EZJavaTextParser(String args) {
        initializeValues();
        afterOkay = false;
        beforeOkay = false;
        setPattern(args);
    }

    public EZJavaTextParser(String args, boolean after, boolean before) {
        initializeValues();
        afterOkay = after; //Allows following text
        beforeOkay = before; //Allows leading text
        //If both are true then following and leading text are allowed
        setPattern(args);
    }

    private void initializeValues() {
        //Each position in the ArrayList is either a variable placeholder or delimiter expression
        pattern = new ArrayList<String>();
        words = new ArrayList<String>();
        integers = new ArrayList<Integer>();
    }

    public void setPattern(String args) {
        integers.clear();
        words.clear();
        pattern.clear();
        String large = "";
        String small = "";
        int key = -1;
        for (int i = 0; i < args.length(); i++) {
            char c = args.charAt(i);
            int newKey = keyCharMatch(c, small);
            if (newKey == -1) {
                large += small;
                large += c;
                small = "";
            } else if (key == newKey || (small.length() == 0 && newKey != -1)) {
                small += c;
            }
            if (keyStringMatch(small) != -1) {

                if (large.length() > 0)
                    pattern.add(large);
                if (keyStringMatch(small) == doubleLoc) { //doubles stored as integer.integer
                    pattern.add("integer");
                    pattern.add(".");
                    pattern.add("integer");
                } else {
                    pattern.add(small);
                }
                large = "";
                small = "";
            }
            key = newKey;
        }
        if (large.length() > 0)
            pattern.add(large + small);
    }

    public void setPattern(String args, boolean after, boolean before) {
        afterOkay = after;
        beforeOkay = before;
        setPattern(args);
    }

    public boolean matches(String s) {
        boolean match = false;
        int length = s.length();

        if (afterOkay && beforeOkay) {
            for (int i = length; i > 0; i--) {
                for (int j = 0; j < i; j++)
                    if (pMatches(s.substring(j, i))) return true;
            }
            return false;
        }

        if (afterOkay) {
            for (int i = length; i > 0; i--)
                if (pMatches(s.substring(0, i))) return true;
            return false;
        } else if (beforeOkay) {
            for (int i = 0; i < length; i++)
                if (pMatches(s.substring(i, length))) return true;
            return false;
        } else {
            match = pMatches(s);
        }
        return match;
    }

    private boolean pMatches(String s) {
        integers.clear();
        words.clear();
        String builder = "";
        int patternPos = 0;
        int patternKey = keyType(patternPos);
        int key = -1;
        for (int i = 0; i < s.length(); i++) {
            String c = s.substring(i, i+1);
            builder += c;
            key = identify(c);

            if (key != patternKey && builder.length() > 1 && !matchesDelimiter(builder, patternPos)) {
                if (patternKey != -1) {
                    save(builder.substring(0, builder.length()-1));
                } else if (!builder.substring(0, builder.length()-1).equals(pattern.get(patternPos))) {
                    integers.clear();
                    words.clear();
                    return false;
                }
                builder = "";
                builder += c;
                patternPos++;
                patternKey = keyType(patternPos);
                key = identify(builder);
            }

            if ((key == -1 && !matchesDelimiter(builder, patternPos))
                    || (key != patternKey && !matchesDelimiter(builder, patternPos))
                    || (key != -1 && key != patternKey)) {
                integers.clear();
                words.clear();
                return false;
            }

        }
        if (key == patternKey && key != -1) {
            save(builder);
            patternPos++;
        }
        if (patternPos < pattern.size()) {
            integers.clear();
            words.clear();
            return false;
        }
        return true;
    }

    public Integer[] getIntegers() { // ints also contains doubles in two separate array slots. So 56.7 would be stored as "56" and "7".
        Integer[] temp = new Integer[integers.size()];
        integers.toArray(temp);
        return temp;
    }

    public String[] getWords() {
        String[] temp = new String[words.size()];
        words.toArray(temp);
        return temp;
    }


    public String[] getPattern() {
        String[] temp = new String[pattern.size()];
        pattern.toArray(temp);
        return temp;
    }


    private int keyCharMatch(char c, String s) {
        int pos = s.length();
        for (int i = 0; i < keys.length; i++) {
            if (pos < keys[i].length()) {
                if (keys[i].charAt(pos) == c && keys[i].substring(0, pos).equals(s)) return i;
            }
        }
        return -1;
    }

    private int keyStringMatch(String s) {
        for (int i = 0; i < keys.length; i++) {
            if (keys[i].equals(s)) return i;
        }
        return -1;
    }

    private int identify(String s) {
        if (isAlpha(s)) return wordLoc;
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException nfe) {
            return -1;
        }
        return integerLoc;
    }

    private boolean isAlpha(String name) {
        char[] chars = name.toCharArray();

        for (char c : chars) {
            if(!Character.isLetter(c)) {
                return false;
            }
        }

        return true;
    }

    private int keyType(int pos) {
        if (pos >= pattern.size()) return -1;
        for (int i = 0; i < keys.length; i++) {
            if (keys[i].equals(pattern.get(pos))) return i;
        }
        return -1;
    }

    private boolean matchesDelimiter(String s, int pos) {
        if (pos >= pattern.size()) return false;
        String pat = pattern.get(pos);
        if (s.length() > pat.length()) return false;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != pat.charAt(i)) return false;
        }
        return true;
    }

    private void save(String s) {
        if (identify(s) == wordLoc) {
            words.add(s);
        } else {
            integers.add(Integer.parseInt(s));
        }
    }
}
