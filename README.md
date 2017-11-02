# StreamCompat for Android

Use Streams everywhere just like you wish you always could!

* **Works on every Android Device**: StreamCompat can be used on 100% of all active Android Devices.
* **Efficient and Performant**: Regardless of how many filter, map or flatMap statements you use every item in source collection will be evaluated **only once**. That means high performance that scales very well!
* **Optimized for Mobile Devices**: This isn't just a straight backport of the Stream API! It uses an Iterator based implementation which avoids autoboxing wherever possible and defaults to memory efficient Collections to ensure that mobile developers can use it without having to worry about anything! 

```java
final List<ViewModel> viewModels = StreamCompat.of(models)
        .filter(ExampleModel::isVisible)
        .mapToInt(ExampleModel::getTextResId)
        .mapToObj(context::getString)
        .map(ViewModel::new)
        .collect(Collectors.toList());
```

# How do I add it to my project?

To use StreamCompat just add this to the dependencies closure in your build.gradle:

```
compile 'com.github.wrdlbrnft:stream-compat:0.3.0.12'
```

You can use Java 8 features like lambda expressions and method references by using at least version `26.0.0` of the Android Build Tools.

# Features

StreamCompat is not meant to be a full backport of the Stream API. The most important features are included based on an `Iterator` based implementation. There is the basic `Stream` for dealing with objects, as well as special versions for primitive types. The full list is:

* `Stream`: For streaming objects
* `IntStream`: For streaming `int` values.
* `LongStream`: For streaming `long` values.
* `FloatStream`: For streaming `float` values.
* `DoubleStream`: For streaming `double` values.
* `CharacterStream`: For streaming `char` values.
* `ByteStream`: For streaming `byte` values.

Each of those implementations supports almost all features of Javas Stream API, for example:

* `filter`: To filter elements.
* `map`: To map values to other values.
* `flatMap`: To map other streams into one long stream.
* `reduce`: To combine elements from a stream into one result.
* `distinct`: To remove duplicate elements.
* `collect`: To collect elements into a container.
* `toArray`: to collect elements into an array.

As well as many other useful operations like `limit`, `skip`, `count` etc. Anyone who already knows the Stream API from Java is going to have no problem to get started with StreamCompat, but there are a few differences and optimizations for mobile devices which means that some things are going to work a little bit differently. Notable differences are:

* Parallel streaming is not supported. Streams will only be evaluated serially and in the order of the source collection.
* StreamCompat by default only uses Android specific memory efficient collections internally.
* There are many new `Collector` implementations for Android specific collections, and lots of helper methods to help dealing with them.
* StreamCompat includes many more primtive stream implementations to make the life of mobile developers easier and to avoid autoboxing as much as possible.
