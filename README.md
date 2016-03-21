# StreamCompat for Android

Use Streams everywhere just like you know them!

* **Works on API level 7 and above**: With the new Jack Compiler you can use all the things that make Java 8s Stream API so great. Method references and lambda expressions on API level 7 and above! That's **100%** of all Android devices.
* **Efficient and Performant**: Regarless of how many filter, map or flatMap statements you use every item in source collection will be evaluated **only once**. That means high performance that scales really well!

```java
final List<ViewModel> viewModels = StreamCompat.of(models)
        .filter(ExampleModel::isVisible)
        .map(ExampleModel::getTextResId)
        .map(this::getString)
        .map(ViewModel::new)
        .collect(Collectors.toList());
```

# Table of Contents

* [Installation](#installation)
  * [Stream Compat](#streamcompat)
  * [JACK Compiler](#jack-compiler)
* [Examples](#examples)
* [Features](#features)
  * [Overview](#overview)
    * [Fully Supported Features](#fully-supported-features)
    * [Changed Features](#changed-features)
  * [Android-Specific Changes](#android-specific-changes)
    * [Collector Interface](#collector-interface)
    * [Predefined Collectors](#predefined-collectors)

# Installation

## StreamCompat

To use StreamCompat just add this to the dependencies closure in your build.gradle:

```
compile 'com.github.wrdlbrnft:stream-compat:0.1.0.15'
```

To take advantage of Java 8 features you also need to enable JACK which is explained in the next chapter.

## JACK Compiler
To use method references and lambda expressions you need to use the new JACK Compiler!

To activate it you need to modify three things in your build.gradle:
 1. Set your `buildToolsVersion` to `24.0.0 rc1` or higher
 2. Enable JACK in the `jackOptions` closure
 3. Set your language level to Java 8 in the `compileOptions` closure

```
android {
    ...
    buildToolsVersion "24.0.0 rc1"

    defaultConfig {
        ...
        jackOptions {
            enabled = true
        }
    }
    ...
    compileOptions {
        targetCompatibility 1.8
        sourceCompatibility 1.8
    }
}
```

# Features

## Overview

StreamCompat is not meant to be a full backport of the Stream API. The most important features are included based on an `Iterator` based implementation.

### Fully Supported Features

These calls work exactly like in Java 8

* `filter()`
* `map()`
* `flatMap()`

### Changed Features

These calls have been modified to better fit Android

* `collect()`

## Android-Specific Changes

### Collector Interface

For simplicity reasons the functionality of the `Collector` interface was changed. However this may be fixed in future versions of StreamCompat. If that happens changing the `Collector` interface would be a breaking change only for the `create()` factory method in `Collectors`.

The exact difference to the Java 8 implementation is that the methods `combiner()` and  `characteristics()` have been removed. All pre defined collectors are just based on the `supplier()`, `accumulator()` and `finisher()` methods. 

```java
public interface Collector<T, A, R> {

    Supplier<A> supplier();

    BiConsumer<A, T> accumulator();

    Function<A, R> finisher();
}
```

Almost all `Collector` implementations are still possible with just those three methods. 

### Predefined Collectors

Just like in the Java 8 implementation the `Collectors` class contains many usefull pre defined `Collector` implementations.

Aside from basic `toList()` and `toSet()` collectors there are also `Collector` implementations for special Android `Collections`, for example: 

* `toSparseArray()`
* `toLongSparseArray()`

Additionally the default `Collection`s used in the `Collectors` class are memory efficient Android specific versions, for example `toMap()` defaults to using the `ArrayMap` implementation from the support library. 

