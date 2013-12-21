EZJavaTextParser
=============

**Author:** Jayden Navarro

**Email:** jdndeveloper@gmail.com

**Twitter:** [@JDNdeveloper](https://twitter.com/JDNdeveloper) and [@JaydenNavarro](https://twitter.com/JaydenNavarro)

**Google+:** [Jayden Navarro](https://plus.google.com/u/0/112058447436164061508/posts)

## Description:
A one class easy String parser. It allows you to easily set a pattern and then check to see if
Strings match this pattern.

I wrote this so it would be easy for intro/intermediate CS students to use to gather and parse input.
Because of this, some of the language I use may sound like oversimplification to some.

If you want to use this to parse user input, I suggest collecting a whole line of user input
and then passing that into this program using EZObject_EXAMPLE.matches(userInputString);

The same technique can be used for reading from a file.

Enjoy!

**Jayden Navarro**

**P.S.** These classes were built for Java SE 7.

## How To Use:

Put the **EZJavaTextParser.java** into the same folder as your project. Create an object instance of this
class. For instance: **"EZJavaTextParser EZ = new EZJavaTextParser(EXAMPLE_PATTERN);"**.

In the constructor make sure to put your pattern, which I talk about below.

To test to see if a String matches this pattern call **"EZ.matches(EXAMPLE_STRING);"**.
This function will return back to you true if it matches the pattern and false if it does not.

To grab the words or integers from the string call **"EZ.getIntegers()"** and **"EZ.getWords();"*. This 
functions return an integer array and a String array, respectively.

The **EZJavaTextParserTester.java** file I included shows some examples for using this class.

## Pattern Keywords:

* **"word"**: Text that only contains upper and lowercase English alphabet letters. Ex: "Hello"

* **"integer"**: An integer. Ex: "546"

* **"double"**: Two integers seperated by a period. Ex: "452.07"


## Examples of Patterns:

* Ex 1: **"word: integer"**. This would require a word followed by ": " and then an integer.

	**Works:** "Hello: 45", "bob: 8980"

	**Doesn't Work:** "Hello:45", "45: 89"

* Ex 2: **"double.bob.integer"**. This would require a double followed by a period followed by "bob" 
followed by another period and then another double.

	**Works:** "45.67.bob.23.89", "675.2.bob.0.45"

	**Doesn't work:** "456.bob.23.67", "456.r.bob.56", "45.67.BOB.23.89"

## Flags: 

You set these flags in the constructor or using setPattern(String, boolean, boolean).

* **"afterOkay"**: This allows for there to be trailing text after the pattern.

* **"beforeOaky"**: This allows for leading text before the pattern.

Both can be enabled at the same time.

#### Examples: 

**EZJavaTextParser(pattern, true, false);** would allow trailing text but not leading text.

**setPattern(pattern, false, true);** would allow leading text but not trailing text.

## Functions:

**EZJavaTextParser();** This sets both flags to false and intializes other variables 
to defaults.

**EZJavaTextParser(String args);** This sets the pattern to args and sets the flags to false.

**EZJavaTextParser(String args, boolean after, boolean before);** This sets the pattern to args
and the flags to their corresponding values.

**void setPattern(String args);** This sets the pattern to args and resets integers and words lists.

**void setPattern(String args);** boolean after, boolean before); This sets the flags and the pattern.

**boolean matches(String s);** This checks to see if 's' matches the pattern. It also takes into consideration
the flags. It returns true if 's' matches the pattern. It returns false if it does not. It also fills
the integers and words ArrayLists with the words and integers (and doubles) that appeared in 's'.

**Integer[] getIntegers();** This returns an integer array of the integers (and doubles). To those of you 
unfamiliar with 'Integer', you can use it just how you would any 'int' without having to worry about
any casting or conversion.

**String[] getWords();** This returns a String array of the words.

**String[] getPattern();** This returns a String array with the pattern. Every slot in the array is either
a keyword or a delimiter that seperates the keywords.
