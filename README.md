#Records

Persistent, type safe, heterogeneous maps.

Depends on [https://github.com/krukow/clj-ds](https://github.com/krukow/clj-ds) for persistent collections.

##Usage

```java
// Specifying a datastructure
interface Thing {
  Key<String> name = new Key<>("name");
  Key<Double> price = new Key<>("price");
}

// Constructing a record
Rec<Thing> thing = Rec.create(name, "stuff", price, 14d);
Rec<Thing> sameThing = new Rec<Thing>().with(name, "stuff").with(price, 14d);

// Accessing data
Double myPrice = thing.get(price);

// toString, equals and hashCode just works
System.out.println(thing); // Prints '{name "stuff", price 14.0}'
System.out.println(thing.equals(sameThing)); // Prints 'true'
```

For more examples see RecordTest.

##Motivation

This library is an attempt to make working with data structures in Java easier. This style of programming is heavily influenced by Clojure. It encourages the separation of data structures and functions that operate on them, and treats immutability as the default.

####Code reuse
Normally, every class has to define constructors, accessors, hashCode, equals, toString, etc. This doesn't just require you to implement and maintain lots of code, but it leaves room for mistakes and differences in behaviour. By defining the way objects are constructed, printed, compared and accessed *once*, all records behave the same.

Records implement java.util.Map, so every function that work on maps, also work on records. In addition, they allow you to either stream or iterate over their map entries. Records are functions of their keys, and a keys are functions of records.

####Immutability
Although Java allows marking variables as final, it doesn't solve the problem of composite immutable objects. Records use Clojure's persistent data structures (via krukow's port clj-ds) as a backing data structure to achieve fast and immutable composite objects. 

If you for some reason need a mutable record, you can convert records back and forth via the asMutable() and asImmutable() methods.

##Runtime type safety
At the moment, records provide no runtime guarantees for its contents. Just like any of the java collections, if you cast it to its raw type you can insert anything and it will only fail with a ClassCastException when you try to get data out of it expecting a certain type. 