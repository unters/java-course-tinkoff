# Java, records

`record` is a special-purpose class designed to provide a efficient, easy-to-use way to hold a group of values. One of the central motivations for records is the reduction of the effort required to create a class whose primary purpose is to organize two or more values into a single unit.

___

## record

```java
record recordName(component-list) {
    /* Ooptional body statements.  */
}
```

`component-list` defines the data that a record will hold. Body is optional, because compiler will automatically create corresponding `private` `final` fields to store listed data, read-only `public` getter methods to that data, override `toString()`, `equals()`, and `hashCode()` inherited from `Object`. 

* <mark>TODO</mark>: Another element automatically created by the compiler is the record's *canonical constructor*.

```java
/* Example.  */
record Employee(String name, int id) {}

class RecordDemo {
    public static void main(String[] args) {
        Employee emp = new Employee("Joe Doe", 1047);
        System.out.println("Employee " + emp.name() +
            " with id " + emp.id());
    }
}
```

A `record` can not inherit another class. All records implicitly inherit `java.lang.Record`, which specifies `abstract` overrides of the `equals()`, `hashCode()`, and `toString()` methods, declared by `Object`.

A `record` cannot be extended - all `record` declarations are considered `final`.

A `record` can implement one or more interfaces.

Aside from the fields associated with a `record`'s components, any other fields must be `static`.

A `record` can be generic.

___

## Declaring a canonical constructor

For a canonical constructor , the types and parameter names must be the same as those specified by the `record` declaration. Each component must be fully initialized upon completion of the constructor. A constructor cannot be generic and it cannot include `throws` clause. It cannot invoke another constructor defined for the record. The constructor must be at least as accessible as its `record` declaration.

```java
/* Example.  */
record Employee(String name, int idNum) {
    public Employee(String name, int idNum) {
        /* Remove any leading and trailing spaces.  */
        this.name = name.trim();
        this.idNum = idNum;
    }
}
```

### compact constructor

A *compact constructor* is declared by speciyfing the name of the record, but without parameters. The compact constructor implicitly has parameters that are the same as the record's components, and its components are automatically assigned the values of the arguments passed to the constructor.

```java
/* Compact canonical constructor that removes any leading and
 * trailing spaces from the name string.  */
public Employee {
    name = name.trim();
}
```

___

## Declaring a non-canonical constructor

Any non-canonical constructor must first call another constructor in the records via `this`. The constructor invoked will often be the canonical constructor. Doing this ultimately ensures that all fields are assigned.

```java
record Employee(String name, int id) {
    static int pendingID = -1;

    public Employee {
        name = name.trim();
    }

    public Employee(String name) {
        this(name, pendingID);
    }
}
```

___

## records and exceptions

```java
public Employee {
    name = name.trim();

    /* Check, that name contains only one comma.  */
    int i = name.indexOf(',');
    int j = name.lastIndexOf(',');
    if (i != j)
        throw IllegalArgumentException("Multiple commas found.");

    /* Confirm that comma is present after at least one leading
     * character, and that at least one character follows the comma.  */
    if (i < 1 | name.length() == i + 1)
        throw IllegalArgumentException("Required format: last, first");
}
```

___

## creating record getter methods

It is possible to create your own implementation of a getter method. When a getter is declared, the implicit version is no longer supplied.

A getter must have the same name and return type as the component that it obtains. It must also be explicitly declared `public`. No `throws` clause is allowed in a getter declaration. A getter cannot be generic and/or `static`.

A better alternative to overriding is creating a separate instance method with its own name.

```java
/* An instance method that returns only the last name without
 * the first name.  */
String lastName() {
    return name.substring(0, name.trim().indexOf(','));
}
```
