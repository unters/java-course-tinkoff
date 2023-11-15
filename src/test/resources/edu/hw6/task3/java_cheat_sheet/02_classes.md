# Java, OOP

___

## Inheritance

It is permitted to specify only one superclass for each subclass being created.

```java
class A {
    ...
}

class B extends A {
    ...
}
```

A superclass variable can be used to reference any object derived from that class.

It is the type of the reference variable - not the type of the object that it refers to - that determines what members can be accessed.

### super

```java
super(arg-list);    /* Call a constructor, defined by a superclass.  */
```

`super()` must always be the first statement executed inside a subclass' constructor.

`super()` always refers to the superclass immidiately above the calling class.

In a class hierarchy, constructors complete their execution in order of derivation (from superclass to subclass).

If `super()` is not used, then the default or parameterless constructor of each superclass will be executed.

```java
super.member;    /* Here member is a method or an instance variable.  */
```

### final classes

`final` keyword (with a `class`) prevents a class from being inherited.

All methods of a `final` class are implicitly declared `final` too.

* It is illegal to declare a class `abstract` and `final` at the same time.

### var and inheritance

The inferred type is based upon the type of the variable, even if it references an object of a derived class (*Shildt, p. 195*).

___

## instanceof

```java
objref instanceof type
```

Operator `instanceof`:

```java
@interface I1 {...}
class A { ... }
class B extends A implements I1 { ... }
class X { ... }

A a; B b; X x; I1 i1;
a instanceof B   // true or false - depends on type of instance referenced by c1.
i1 instanceof B  // true or false - same
b instanceof A   // true
x instanceof B   // compile error - always false, logic error

/* t == null  */
t instanceof X   // false

/* Any object can be cast to class Object.  */
a instanceof Object    // true
```

Avoiding `ClassCastException`s:

```java
Person p = ...;
if (p instanceof Student) {
    Student s = (Student) p;
}
```

### Pattern matching

Pattern matching (JDK 17+):

```java
Person p = ...;
if (p instanceof Student s) {
    // variable Student s is available here ...
} else {
    // ... and here it is not.
}

// This compiles.
if (obj instanceof String s && s.length() > 5) {
    ...
    s.contains(...)
}
```

- *TODO*: [capture](https://youtu.be/UKV2gwgajDk?t=3822)

---

## Access modifiers

When no access modifier is used, then by default the member of a class is `public` within its own package, but it cannot be accessed outside of its package (this is called ***default access***).

| is accessible in (the)         | private | no modifier | protected | public  |
| ------------------------------ | ------- | ----------- | --------- | ------- |
| same class                     | **Yes** | **Yes**     | **Yes**   | **Yes** |
| same package subclass          | No      | **Yes**     | **Yes**   | **Yes** |
| same package non-subclass      | No      | **Yes**     | **Yes**   | **Yes** |
| diffirent package subclass     | No      | No          | **Yes**   | **Yes** |
| diffirent package non-subclass | No      | No          | No        | **Yes** |

* A non-nested class has only two possible access levels: ***default*** and ***public***.
* ***default access*** is also called ***package-private***.

### nested classes

It is possible to define a class within another class. The scope of a nested class is bounded by the scope of its enclosing class.

A nested class has access to all members, including `private`, of the class in which it is nested (the reverse is not true).

- Is is also possible to declare a nested class that is local to a block.

Nested classes can be `static` and non-`static` (***inner***) (*Shildt, p. 157*).

*Note*: according to Shildt, nested classes are particularly helpful when handling events.

---

## Methods

* Return types **do not** play a role in overload resolutions.

Argument passing:

1. ***call-by-value***;

2. ***call-by-refference***.
* A large number of recursive calls to a method could cause a stack overrun.

### constructors

A class *must* have a constructor.

Access modifiers can be used with class constructors.

Default constructor will not be automatically created if at least one constructor is defined.

If there is no argumentless constuctor in base class it will not be automatically created for the derived class.

`this(args)` can be used to call a matching constructor. It must be the first statement in the constructor. You cannot use any instance variable of the constructor's class in a call to `this()`. You cannot use `super()` and `this()` in the same constructor because each must be the first statement in the constructor.

* *Shildt, p. 344*: Constructors that call `this()` will execute a bit slower than those that contain all the initialization code inline (overhead is added because of the call and return mechanism used when the second constructor is invoked).

### static methods restrictions

`static` methods:

1. can only directly call other `static` methods of their class;

2. can only directly access `static` variables of their class;

3. cannot refer to `this` or `super` in any way.

### varargs

```java
void varargsMethod(int ... v) {
    for (int x : v)
        System.out.print(x + " ");
}
```

* varargs variable has attribute `length` (because it is actually an array).

"Normal" parameters of the method must be declared before varargs parameter.

```java
int doSmth(int a, byte b, int ... vals) { ... }
```

* There must be only one varargs parameter.

Varargs methods can be overloaded:

```java
void varargsMethod(int ... v) { ... }
void varargsMethod(boolean ... v) { ... }
void varargsMethod(String msg, int ... v) { ... }
```

But:

```java
class VarargsTest1 {
    static void varargsMethod(int ... v) { ... }

    static void varargsMethod(boolean ... v) { ... }

    public static void main(String[] args) {
        varargsMethod(1, 2, 3);        // Ok.
        varargsMethod(true, false);    // Ok. 
        varargsMethod();    // Ambigious - will not compile.
    }
}
```

```java
class VarargsTest2 {
    static void varargsMethod(int ... v) { ... }

    static void varargsMethod(int a, int ... v) { ... }

    public static void main(String[] args) { 
        /* Ambigious - will not compile.  */    
        varargsMethod(1);
    }
}
```

### method overriding

Method ***overriding*** occurs only when the names and the type signatures of the two methods are identical. If they are not, then the two methods are simply ***overloaded***. 

* Overriden methods in Java are similar to virtual functions in C++.

### dynamic method dispatch

*Shildt, p. 189.*

### abstract methods and classes

`abstract` type modifier requires certain methods to be overriden by subclasses (this methods are sometimes reffered to as *subclasser responsibility*).

Any class that contains one ore more `abstract` methods must also be declared `abstract`.

There can be no objects of an `abstract` class.

It is forbidden to declare `abstract` constructors or `abstract static` methods.

Any subclass of an `abstract` class must either implement all of the `abstract` methods in the superclass, or be declared `abstract` itself.

* `abstract` classes can have as much implementation as they see fit.

* Obviously, it is possible to create a reference variable of an `abstract` type.

### final methods

`final` keyword disallows a method from being overriden.

* Methods declared as `final` can sometimes provide a performance enhancement: the compiler is free to "inline" calls to them because the compiler "knows" they will not be overriden by a subclass.

*TODO*: late binding (dynamic resolution of method calls), early binding (call to a method can be resolved at compile time).

___

## Variables

### initialization section

Code of the *initialization section* will be executed each time before the execution of the constructor.

If there is a need to do a computation in order to initialize `static` variables, it is possible to declare a `static` block that gets executed only once, when the class is first loaded.

```java
class Employee {
    private static int nextId;
    private int id;

    // static initialization block
    static {
        nextId = ThreadLocalRandom.current.nextInt(10000);
    }

    // object initialization block
    {
        id = nextId;
        ++nextId;
    }
}
```

### final variables

`final` variables cannot be modified - they are essentially constants.

### transient variables

When a variable id declared as `transient` , its value need not persist when an object is stored.

```java
/* Here, if an object of type T is written to a persistent storage area,
 * the contents of a would not be saved, but the contents of b would.  */
class T {
    transient int a;    // will not persist
    int b;              // will persist
}
```

### volatile variables

The `volatile` modifier tells the compiler that the variable modified by `volatile` can be changed unexpectedly by other parts of the program. 

In a multithreaded program, sometimes two or more threads share the same variable. For efficiency considerations, each thread can keep its own, private copy of such a shared variable. The real (the *master*) copy of the variable is updated at various times, such as when a `synchronised` method is entered. While this approach works fine, it may be inefficient at times. In some cases, all that really matters is that the master copy of a variable always reflects its current state. To ensure this, a variable can be specified as `volatile`, which tells the compiler that it must always use the master copy of a volatile variable (or, at least, always keep any private copies up-to-date with the master copy, and vice versa). Also, accesses to the shared variable must be executed in the precise order indicated by the program.

____

## Object class

Any class in Java is implicitly inherited from class `Object`.

```java
public boolean equals(Object other)
```

* [*Expired abstraction*]([МФТИ Core Java 2020 Лекция 3 - YouTube](https://youtu.be/UKV2gwgajDk?t=4350))

Formal contract `equals()` is reflexive, symmetric, transitive, constistent (if objects have not changed since last call of `equals()` the next call of `equals()` will return the same result as the previous). No objects equals `null`.

```java
public int hashCode()
```

Formal contract `hashCode()`: 1. is consistent; 2. always returns the same value for equal objects (compared using `equals()`); 3. but the reverse might not be true.

There is a special lib `EqualsVerifier` to verify overriden `equals()` and `hashCode()`.

* Most IDEs have plugins to automatically override `equals()` and `hashCode()` for a given class.

* *See also*: `Lombok` lib to automatically generate  `equals()` and `hashCode()` (`Lombok` takes part in byte code generation).
  
  ```java
  import lombok.EqualsAndHashCode;
  
  @EqualsAndHashCode
  public class Person {
      private int age;
      private String name;
  }
  ```

```java
public String toString()
```

* `toString()` can also be generated (overriden) using IDE plugins and `Lombok`.

### finalize

Do not attempt to override `finalize`.

____

## Anonymous classes

```java
class Demo {
    void show() {
        System.out.println("superclass");
    }
}

class FlavorDemo {
    public static void main(String[] args) {
        Demo d = new Demo() {
            void show() {
                super.show();
                System.out.println("subclass");
            }
        }
        d.show();
    }
}
```

```java
button.onMouseClick(new EventListener(new EventListener() {
    void onClick(Event e) {
        /* All outer fields and effectively final variables
         * are available here.  */
    }
});
```

____

## Other

### Strings (and string literals)

Strings in Java are implemented as classes. Strings and string literals:

1. are objects of type (class) `String`;
2. are immutable.

There is an array inside `String`. 'til Java 9 is was `char[]`. Since Java 9 it is `byte[]` and `byte coder` (just an encoding flag). Strings use *Latin1* encoding if *Latin1* is enough, or, if there are hieroglyphs, cyrillics and so on, *UTF-16* encoding.

* About Latin1 / UTF-16 Strings - *The Lord of the Strings: Two Scourds, Alexey Shipilev*.

#### string constants pool

As soon as `String` objects are immutable, there is no need to store the same string literal multiple times for `String` objects that "are equal".

if `String` object is created using operator `new`, than the corresponding string literal doesnt get into the *string constants pool*.

#### string internation

```java
String str = new String("John Doe"); // "John Doe" not in string constant pool.
str = str.intern();    // After intern() "John Doe" is in string constant pool.
```

* [Ivan Ponomarev about pool of string constants]([МФТИ Core Java 2020 Лекция 4 - YouTube](https://youtu.be/VTFuqbVg5qc?t=4306)).

```java
if (a == "John Doe")         // Wrong - diffirent objects, same state.
if (a.equals("John Doe"))    // Bad - NPE if a == null.
if ("John Doe".equals(a))    // Thats it.

if ("John Doe".equalsIgnoreCase(a))
if (str != null && !str.trim().isEmpty())
```

* Do not concat string using `+=` in loop (obvious reason). Use `StringBuilder` instead.

* *<mark>See</mark>*: UTF code points.

* Ivan Ponmarev's harmful `String` methods: `indexOf`, `lastIndexOf`, `replace`, `split` - use finite-state machines and regular expressions instead.

___

### Arrays

Arrays in Java are implemented as classes.

Any array in Java has `length` attribute.

Arrays are passed by reference.

* Array bounds are checked at runtime, but sometimes JVM can make no checks if JVM is sure that index is not out of bounds.

```java
luckyNumbers = Arrays.copyOf(luckyNumbers, 2 * luckyNumbers.sizeOf());
```

* <mark>What is</mark> this `luckyNumbers.sizeOf()`???

Arrays are covariant:

```java
String[] a = new String[1];
/* This will compile, because String is derived from Object.  */
Object[] b = a;

/* This will compile, but ArrayStoreException will occur.  */
b[0] = 5; 
```

#### Array default values

Elements in the array allocated by `new` will automaticaly be initialized to:

1. `0` (for numeric types);

2. `false` (for booleans);

3. `null` (for reference types).

____

- Although commonly reffered to as a dot operator, the formal specification for Java categorizes the `.` as a separator.

____

```java
public class Point {
    private final int x;
    private final int y;

    /* Java will ask to create a constructor (because x and y are
     * unitialized private fields). Depending on the purpose of the
     * class a lot of boiler-plate code might also be needed: getX(),
     * getY(), equals(), hashCode(), toString().  */

    public double distance(Point other) { ... }
}
```

* boiler-plate code can be avoided using `Lombok`.
