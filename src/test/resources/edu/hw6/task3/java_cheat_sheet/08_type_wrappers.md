# Java, autoboxing

____

* Wrapper types are immutable?

## Type wrappers

There is a type wrapper for each primitive type.

```java
/* Boolean(char c) is deprecated since JDK 9. To obtain a Boolean
 * object - use valueOf() instead.  */
static Boolean valueOf(boolean boolValue)
/* boolString is interpreted as true is it containts "true". Otherwise
 * it will be false.  */
static Boolean valueOf(String boolString)

/* Returns a boolean contained in Boolean object.  */
char booleanValue()
```

Same for `Character`, but it has no `valueOf(String)`.

All numeric type type wrappers inherit the abstract class `Number`, that declares methods that return the values of an object in each of the diffirent number formats:

```java
byte byteValue()
double doubleValue()
float floatValue()
int intValue()
long longValue()
short shortValue()
```

All of the numeric type wrappers implement these methods. Each numeric type wrapper has two `valueOf()` methods. Example for `Integer`:

```java
static Integer valueOf(int val)
static Integer valueOf(String valStr) throws NumberFormatException
```

All of the type wrappers override `toString()`.

____

## Autoboxing

Autoboxing is a process by which a primitive type is automatically encapsulated (*boxed*) into its equivalent type wrapper whenever an object of that type is needed. There is no need to excplicitly construct an object.

```java
Integer iOb = 100;  // Autobox an int.
int i = iOb;        // Auto-unbox.
```

### methods

Autoboxing/unboxing might occur when an argument is passed to a method, or when a value is returned by a method.

### expressions

```java
Integer iOb_1 = 100;
/* iOb_1 gets automatically unboxed, incremented and reboxed.  */
++iOb_1;

/* iOb_1 gets automatically unboxed, the expression gets evaluated,
 * and the result is reboxed and assigned to iOb_2.  */
Integer iOb_2 = iOb_1 + (iOb_1 / 3);

/* Same, but without reboxing.  */
int i = iOb_1 + (iOb_1 / 3);
```

Autoboxing/unboxing allows to mix diffirent types of numberic objects in an expression.

* Auto-unboxing allows to use `Integer` numeric objects to control a `switch` statement.

* Auto-unboxing allows to use `Boolean` objects to control any of Java's loop statements and `if`.

* *See also*: autoboxing/unboxing helps prevent errors (*Shildt, p. 293*).

* ***Warning***: each autobox/unbox adds overhead.

____

## Object pool

[According to Ivan Ponomarev]([МФТИ Core Java 2020 Лекция 5 - YouTube](https://youtu.be/GQLzO5lNEvE?t=960)), type wrappers are immutable. Just like `String`'s type wrapper values are cached:

```java
/* Both five1 and five 2 most likely (but not necesserely) reference 
 * the same value '5' in Integer object pool - use valueOf().  */
Integer five1 = Integer.valueOf(5);
Integer five2 = Integer.valueOf(5);

/* Object pool will not be used when creating
 * a type wrapper object using operator new.  */
Integer five3 = new Integer(5);
```

By default JVM checks if a value to wrap belongs to segment [-128, 127] of integer numbers and if it does JVM uses Integer object cache. If not - it creates a new object. This upper bound can be increased (lower bound can not):

```cmd
-XX:AutoBoxCacheMax=<size>
```

It is acceptable if there are proofs that such action can solve some performance issues.

### Caching bounds

`Byte`,`Short`, `Long`: -128..127;

`Character`: 0..127;

`Boolean`: `Boolean.TRUE` and `Boolean.FALSE`

`Float` and `Double` are not cached, but JavaDoc says that `valueOf()` can cache frequently used values.

* *See*: pattern object pool.
