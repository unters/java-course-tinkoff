# Java, String handling

___

`String` objects are immutable.  For those cases in which a modifiable string is desired, Java provides two options: `StringBuffer` and `StringBuilder`. All 3 classes are defined in `java.lang`. All 3 are declared `final`. All three implement the `CharSequence` interface.

___

## String

```java
String()
String(char[] chars)
String(char[] chars, int startIndex, int numChars)
String(String strObj)
```

* `char` type uses 16 bits to represent the basic Unicode character set.

```java
/* For ASCII strings.  */
String(byte[] chars)
String(byte[] chars, int startIndex, int numChars)
```

```java
String(StringBuffer strBufObj)
String(StringBuilder strBuildObj)
```

* There are also constructors than let to specify the **charset**.

To obtain the length of the string call its `int length()` method.

### String literals, concatination

```java
/* Using string literals to initialize a string.  */
String s = "abc";

/* Any string literal can be used as a string object.  */
System.out.println("abc".length());
```

```java
/* '+' is the only operator that can be applied to strings.  */
String age = "9";
String s = "He is " + age + " years old.";

/* Same result as above.  */
int age = 9;
String s = "He is " + age + " years old.";

/* However.  */
String s = "four: " + 2 + 2;       /* four: 22  */
/* This can be avoided by using parantheses.  */
String s = "four: " + (2 + 2);     /* four: 4  */
```

### String conversion

`valueOf()` allows to convert data into its string representation. Is is overloaded for all the primitive types and for type `Object`. For primitive types it reterns corresponding contents in string form. For `Object`'s `valueOf()` call the `toString()` method (which every class implements and can override) on the object.

* There is a special version of `valueOf()` that allows to specify a subset of a `char` array:
  
  ```java
  static String valueOf(char[] chars, int startIndex, int numChars)
  ```

### Main String Methods

```java
/* To obtain the length of the string. */
int length()

/* Get a single character by its index.  */
char charAt(int index)

/* Here sourceEnd i the index past the end of the desired substring.
 * target must be large enough to hold the specified number of 
 * characters.  */
void getChars(int sourceStart, int sourceEnd,
    char[] target, int targetStart)

/* Convert all the characters in a String into a character array.  */
char[] toCharArray()

/* Compare two strings by contents.  */
boolean equals(Object str)
boolean equalsIgnoreCase(String str)

/* Method is specified by the Comparable<T> interface, which String
 * implements. Return a value less than zero if the invoking string is
 * less then str, greater than zero - invoking greater than str, and 
 * equal to zero if two string are equal.  */
int compareTo(String str)
int compareToIgnoreCase(String str)

int indexOf()
int lastIndexOf()

/* Create a new String object.  */
String substring(int startIndex, int endIndex)
String concant(String str)
String replace(CharSequence original, CharSequence replacement)

/* Remove all leading and trailing spaces.  */
String trim()
/* Remove all leading and trailing whitespaces
 * (spaces, tabs and other).  */
String strip()

static String join(CharSequence delim, CharSequence ... strs)
```

* `==` operator returns true when both operands refer to the same object (not two diffirent objects with same contents).

___

## StringBuffer

Default constructor reserves room for 16 characters without reallocation. The second versions allows to explicitly set the size of the initial buffer. The third and forth versions allocate room for 16 extra characters (in addition to str and chars).

```java
StringBuffer()
StringBuffer(int size)
StringBuffer(String str)
StringBuffer(CharSequence chars)
```

```java
int length()
int capacity()
void ensureCapacity(int minCapacity)
void setLength(int len)

char charAt(int index)
void setCharAt(int where, char ch)

/* Concatenate the string representation of any other type of data to
 * the end of the invoking StringBuffer object.  */
StringBuffer append(String str)
StringBuffer append(int num)
StringBuffer append(Object obj)
/* Instead of (String str) can be (char ch) and (Object obj).  */
StringBuffer insert(int index, String str)
StringBuffer reverse()
/* Cut characters by index startIndex through endIndex. The invoking
 * StringBuffer is returned.   */
StringBuffer delete(int startIndex, int endIndex)
StringBuffer replace(int startIndex, int endIndex, Stirng str)
String substring(int startIndex, int endIndex)
```

___

## StringBuilder

`StringBuilder` is similar to `StringBuffer` except for it is not synchronised, which means that it is not thread-safe. `StringBuilder` has a faster performance.
