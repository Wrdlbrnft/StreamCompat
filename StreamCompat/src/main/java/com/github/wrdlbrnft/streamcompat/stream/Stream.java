package com.github.wrdlbrnft.streamcompat.stream;

import com.github.wrdlbrnft.streamcompat.bytestream.ByteStream;
import com.github.wrdlbrnft.streamcompat.characterstream.CharacterStream;
import com.github.wrdlbrnft.streamcompat.collections.ArraySet;
import com.github.wrdlbrnft.streamcompat.doublestream.DoubleStream;
import com.github.wrdlbrnft.streamcompat.floatstream.FloatStream;
import com.github.wrdlbrnft.streamcompat.function.BiConsumer;
import com.github.wrdlbrnft.streamcompat.function.BinaryOperator;
import com.github.wrdlbrnft.streamcompat.function.Consumer;
import com.github.wrdlbrnft.streamcompat.function.Function;
import com.github.wrdlbrnft.streamcompat.function.IntFunction;
import com.github.wrdlbrnft.streamcompat.function.Predicate;
import com.github.wrdlbrnft.streamcompat.function.Supplier;
import com.github.wrdlbrnft.streamcompat.function.ToByteFunction;
import com.github.wrdlbrnft.streamcompat.function.ToCharFunction;
import com.github.wrdlbrnft.streamcompat.function.ToDoubleFunction;
import com.github.wrdlbrnft.streamcompat.function.ToFloatFunction;
import com.github.wrdlbrnft.streamcompat.function.ToIntFunction;
import com.github.wrdlbrnft.streamcompat.function.ToLongFunction;
import com.github.wrdlbrnft.streamcompat.intstream.IntStream;
import com.github.wrdlbrnft.streamcompat.longstream.LongStream;
import com.github.wrdlbrnft.streamcompat.optionals.Optional;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * A {@link Stream} contains a sequence of elements which can be filtered and transformed in a
 * number of ways.
 *
 * @param <T> Type of the elements in the {@link Stream}.
 */
public interface Stream<T> extends Iterable<T> {

    /**
     * Filters the elements of the {@link Stream} according to a {@link Predicate}.
     *
     * @param predicate The {@link Predicate} used to test the elements in the Stream
     * @return A new {@link Stream} which only contains the elements which match the {@link Predicate}.
     */
    Stream<T> filter(Predicate<T> predicate);

    /**
     * Maps the elements in the {@link Stream} according to a mapping function.
     * <p>In simpler terms transforms the elements based on the supplied mapping function.
     * As an example:</p>
     * <pre>
     * final List<String> strings = StreamCompat.of("Android", "Google", "Alphabet")
     *         .map(text -> text.substring(0, 2))
     *         .collect(Collectors.toList());
     * </pre>
     * The map operation the above {@link Stream} will call substring(0, 2) on every {@link String} in the {@link Stream}
     * and replace the original {@link String} in the {@link Stream} with the result of the substring(0, 2) call.
     * The final list after collecting then contains the following entries:
     * <p>
     * <p>"An", "Go", "Al"</p>
     *
     * @param mapper A {@link Function} which map the elements in the {@link Stream} to some other element
     * @param <R>    The type of the elements after transformation.
     * @return A new {@link Stream} which now contains all the transformed elements.
     */
    <R> Stream<R> map(Function<T, ? extends R> mapper);

    /**
     * Similar to {@link Stream#map(Function)}. This method maps every element in the {@link Stream}
     * to a primitive {@code int} value. For example:
     * <pre>
     * final int[] hashCodes = StreamCompat.of("Android", "Google", "Alphabet")
     *         .mapToInt(String::hashCode)
     *         .toArray();
     * </pre>
     *
     * @param mapper A {@link ToIntFunction} which maps the elements in the {@link Stream} to an {@code int}.
     * @return Returns an {@link IntStream} which contains all the mapped int values.
     */
    IntStream mapToInt(ToIntFunction<? super T> mapper);

    /**
     * Similar to {@link Stream#mapToInt(ToIntFunction)}. This method maps every element in the {@link Stream}
     * to a primitive {@code long} value.
     *
     * @param mapper A {@link ToLongFunction} which maps the elements in the {@link Stream} to a {@code long}.
     * @return Returns a {@link LongStream} which contains all the mapped {@code long} values.
     */
    LongStream mapToLong(ToLongFunction<? super T> mapper);

    /**
     * Similar to {@link Stream#mapToInt(ToIntFunction)}. This method maps every element in the {@link Stream}
     * to a primitive {@code double} value.
     *
     * @param mapper A {@link ToDoubleFunction} which maps the elements in the {@link Stream} to a {@code double}.
     * @return Returns a {@link DoubleStream} which contains all the mapped {@code double} values.
     */
    DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper);

    /**
     * Similar to {@link Stream#mapToInt(ToIntFunction)}. This method maps every element in the {@link Stream}
     * to a primitive {@code float} value.
     *
     * @param mapper A {@link ToFloatFunction} which maps the elements in the {@link Stream} to a {@code float}.
     * @return Returns a {@link FloatStream} which contains all the mapped {@code float} values.
     */
    FloatStream mapToFloat(ToFloatFunction<? super T> mapper);

    /**
     * Similar to {@link Stream#mapToInt(ToIntFunction)}. This method maps every element in the {@link Stream}
     * to a primitive {@code char} value.
     *
     * @param mapper A {@link ToCharFunction} which maps the elements in the {@link Stream} to a {@code char}.
     * @return Returns a {@link CharacterStream} which contains all the mapped {@code char} values.
     */
    CharacterStream mapToChar(ToCharFunction<? super T> mapper);

    /**
     * Similar to {@link Stream#mapToInt(ToIntFunction)}. This method maps every element in the {@link Stream}
     * to a primitive {@code byte} value.
     *
     * @param mapper A {@link ToByteFunction} which maps the elements in the {@link Stream} to a {@code byte}.
     * @return Returns a {@link ByteStream} which contains all the mapped {@code byte} values.
     */
    ByteStream mapToByte(ToByteFunction<? super T> mapper);

    /**
     * Similar to {@link Stream#map(Function)}, but maps every element in the {@link Stream}
     * to a new {@link Stream} and concatenates all these {@link Stream Streams} together to create
     * a new {@link Stream} which then contains all the elements of the mapped {@link Stream Streams}.
     *
     * @param mapper A {@link Function} which maps each element to a {@link Stream}
     * @param <R>    The type of the elements in the mapped {@link Stream Streams}.
     * @return Returns a new {@link Stream} which contains all the elements of the mapped
     * {@link Stream Streams}.
     */
    <R> Stream<R> flatMap(Function<T, ? extends Stream<? extends R>> mapper);

    /**
     * Similar to {@link Stream#flatMap(Function)} but maps every element in the {@link Stream} to
     * an {@link IntStream}. The returned {@link IntStream} contains all the {@code int} values of
     * all the mapped {@link IntStream IntStreams}.
     *
     * @param mapper A {@link Function} which maps every element to an {@link IntStream}.
     * @return A new {@link IntStream} which contains all the {@code int} values of the mapped
     * {@link IntStream IntStreams}.
     */
    IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper);

    /**
     * Similar to {@link Stream#flatMapToInt(Function)} but maps every element in the {@link Stream} to
     * an {@link LongStream}. The returned {@link LongStream} contains all the {@code long} values of
     * all the mapped {@link LongStream LongStreams}.
     *
     * @param mapper A {@link Function} which maps every element to an {@link LongStream}.
     * @return A new {@link LongStream} which contains all the {@code long} values of the mapped
     * {@link LongStream LongStreams}.
     */
    LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper);

    /**
     * Similar to {@link Stream#flatMapToInt(Function)} but maps every element in the {@link Stream} to
     * an {@link FloatStream}. The returned {@link FloatStream} contains all the {@code float} values of
     * all the mapped {@link FloatStream FloatStreams}.
     *
     * @param mapper A {@link Function} which maps every element to an {@link FloatStream}.
     * @return A new {@link FloatStream} which contains all the {@code float} values of the mapped
     * {@link FloatStream FloatStreams}.
     */
    FloatStream flatMapToFloat(Function<? super T, ? extends FloatStream> mapper);

    /**
     * Similar to {@link Stream#flatMapToInt(Function)} but maps every element in the {@link Stream} to
     * an {@link DoubleStream}. The returned {@link DoubleStream} contains all the {@code double} values of
     * all the mapped {@link DoubleStream DoubleStreams}.
     *
     * @param mapper A {@link Function} which maps every element to an {@link DoubleStream}.
     * @return A new {@link DoubleStream} which contains all the {@code double} values of the mapped
     * {@link DoubleStream DoubleStreams}.
     */
    DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper);

    /**
     * Similar to {@link Stream#flatMapToInt(Function)} but maps every element in the {@link Stream} to
     * an {@link CharacterStream}. The returned {@link CharacterStream} contains all the {@code char} values of
     * all the mapped {@link CharacterStream CharacterStreams}.
     *
     * @param mapper A {@link Function} which maps every element to an {@link CharacterStream}.
     * @return A new {@link CharacterStream} which contains all the {@code char} values of the mapped
     * {@link CharacterStream CharacterStreams}.
     */
    CharacterStream flatMapToChar(Function<? super T, ? extends CharacterStream> mapper);

    /**
     * Similar to {@link Stream#flatMapToInt(Function)} but maps every element in the {@link Stream} to
     * a {@link ByteStream}. The returned {@link ByteStream} contains all the {@code byte} values of
     * all the mapped {@link ByteStream ByteStreams}.
     *
     * @param mapper A {@link Function} which maps every element to a {@link ByteStream}.
     * @return A new {@link ByteStream} which contains all the {@code byte} values of the mapped
     * {@link ByteStream ByteStreams}.
     */
    ByteStream flatMapToByte(Function<? super T, ? extends ByteStream> mapper);

    /**
     * Returns a {@link Stream} which contains only distinct elements of this {@link Stream}.
     *
     * @param setSupplier A {@link Supplier} which creates a new empty {@link Set} which will be used
     *                    to filter out duplicate elements in this {@link Stream}.
     * @return Returns a new {@link Stream} which contains only distinct elements.
     */
    Stream<T> distinct(Supplier<Set<T>> setSupplier);

    /**
     * Returns a {@link Stream} which contains only distinct elements of this {@link Stream}.
     * <p>
     * For the sake of memory efficiency this operation uses an {@link ArraySet}
     * internally to check for duplicate elements. This is the preferred option if this {@link Stream}
     * contains up to one or two thousand elements. For a large number of elements {@link Stream#distinct(Supplier)}
     * can be used to supply a different type of {@link Set} which prioritizes fast lookup times instead of
     * memory efficiency like for example a {@link HashSet}.
     * </p>
     *
     * @return Returns a new {@link Stream} which contains only distinct elements.
     */
    Stream<T> distinct();

    /**
     * Collects all the elements in the {@link Stream} based on the supplied {@link Collector}.
     * <p>
     * <p>The {@link Collectors} class contains many predefined {@link Collector Collectors}.</p>
     *
     * @param collector The {@link Collector} used to collect the elements in the {@link Stream}.
     * @param <R>       Type of the returned Object
     * @return Returns the result of the collection.
     */
    <R, A> R collect(Collector<? super T, A, R> collector);

    /**
     * Collects all the elements in the {@link Stream} based on the supplied {@link BiConsumer} into
     * a result container supplied by the {@link Supplier}.
     *
     * @param supplier    Creates the result container used for the collection.
     * @param accumulator A {@link BiConsumer} which adds an element to the result container.
     * @param <R>         Type of the result container.
     * @return Returns the result container after all elements have been added by the {@link BiConsumer}.
     */
    <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator);

    /**
     * Limits how many elements will be evaluated in the {@link Stream}. If the current {@link Stream}
     * contains more elements then the supplied limit then all remaining elements will be ignored.
     *
     * @param maxSize Maximum number of elements which should be evaluated in the current {@link Stream}.
     * @return Returns a new {@link Stream} which contains all elements up to the supplied limit.
     */
    Stream<T> limit(long maxSize);

    /**
     * Skips the supplied number of elements at the start of the {@link Stream}. If the number of
     * elements in the {@link Stream} is lower than the skipped count an empty {@link Stream} will
     * be returned.
     *
     * @param count How many elements should be skipped
     * @return Returns a new {@link Stream} which does not contain any of the skipped elements.
     */
    Stream<T> skip(long count);

    /**
     * Performs a reduction operation on all the elements in the {@link Stream} with the
     * supplied {@link BinaryOperator} and them returns the reduced value as an {@link Optional}.
     * <p>
     * <p>This is a terminal operation.</p>
     *
     * @param accumulator A {@link BinaryOperator} used to combine elements in the {@link Stream} into just one.
     * @return Returns the reduced value as an {@link Optional}
     */
    Optional<T> reduce(BinaryOperator<T> accumulator);

    /**
     * Performs a reduction operation on all elements in the {@link Stream} with the supplied
     * {@link BinaryOperator}.
     * <p>
     * <p>This is a terminal operation.</p>
     *
     * @param identity    A base value used for the reduction.
     * @param accumulator A {@link BinaryOperator} used to combine elements in the {@link Stream} into just one.
     * @return Returns the reduced value.
     */
    T reduce(T identity, BinaryOperator<T> accumulator);

    /**
     * Performs a mapping and reduction operation on all elements in the {@link Stream} based on the
     * supplied mapper {@link Function} and {@link BinaryOperator}.
     * <p>
     * <p>This is a terminal operation.</p>
     *
     * @param identity    A base value used for the reduction.
     * @param mapper      A mapper {@link Function} which transforms elements in the {@link Stream} before the reduction.
     * @param accumulator A {@link BinaryOperator} used to combine elements in the {@link Stream} into just one.
     * @param <U>         The type of the elements after applying the mapping function.
     * @return Returns the reduced value.
     */
    <U> U reduce(U identity, Function<? super T, ? extends U> mapper, BinaryOperator<U> accumulator);

    /**
     * Returns the minimal element according to the provided {@link Comparator}.
     * <p>
     * <p>This is a terminal operation.</p>
     *
     * @param comparator The {@link Comparator} used to compare the elements in the {@link Stream}.
     * @return The minimal element as an {@link Optional}.
     */
    Optional<T> min(Comparator<? super T> comparator);

    /**
     * Returns the maximal element according to the provided {@link Comparator}.
     * <p>
     * <p>This is a terminal operation.</p>
     *
     * @param comparator The {@link Comparator} used to compare the elements in the {@link Stream}.
     * @return The maximal element as an {@link Optional}.
     */
    Optional<T> max(Comparator<? super T> comparator);

    /**
     * Returns how many elements are contained in the {@link Stream}.
     * <p>
     * <p>This is a terminal operation.</p>
     *
     * @return Returns how many elements are contained in the {@link Stream}.
     */
    long count();

    /**
     * Checks if at least one of the elements in the {@link Stream} matches the {@link Predicate}.
     * <p>
     * <p>This is a terminal operation.</p>
     *
     * @param predicate The {@link Predicate} used to test the elements in the {@link Stream}.
     * @return Returns {@code true} when at least one element matches the {@link Predicate}, otherwise {@code false}.
     */
    boolean anyMatch(Predicate<? super T> predicate);

    /**
     * Checks if all of the elements in the {@link Stream} match the {@link Predicate}.
     * <p>
     * <p>This is a terminal operation.</p>
     *
     * @param predicate The {@link Predicate} used to test the elements in the {@link Stream}.
     * @return Returns {@code true} when all elements match the {@link Predicate}, otherwise {@code false}.
     */
    boolean allMatch(Predicate<? super T> predicate);

    /**
     * Checks if none of the elements in the {@link Stream} match the {@link Predicate}.
     * <p>
     * <p>This is a terminal operation.</p>
     *
     * @param predicate The {@link Predicate} used to test the elements in the {@link Stream}.
     * @return Returns {@code true} when no elements match the {@link Predicate}, otherwise {@code false}.
     */
    boolean noneMatch(Predicate<? super T> predicate);

    /**
     * Collects all the elements in the {@link Stream} into an array. You can just use it like this:
     * <pre>
     * final String[] strings = StreamCompat.of("Android", "Google", "Alphabet")
     *         .map(text -> text.substring(0, 2))
     *         .toArray(String[]::new);
     * </pre>
     * <p>
     * This is a terminal operation.
     *
     * @param generator An {@link IntFunction} which creates a new array with the supplied length
     * @return An array which contains all the elements in the {@link Stream}
     */
    T[] toArray(IntFunction<T[]> generator);

    /**
     * Returns the first element in the {@link Stream} as an {@link Optional}.
     * <p>
     * The returned {@link Optional} is guaranteed to contain a valid element as long as the
     * {@link Stream} is not empty.
     * </p>
     * <p>
     * This is a terminal operation.
     *
     * @return An {@link Optional} which contains the first element of the {@link Stream} as long as
     * one exists.
     */
    Optional<T> findFirst();

    /**
     * Returns an {@link Iterator} which iterates over all elements in the {@link Stream}.
     *
     * @return Returns an {@link Iterator} which iterates over all elements in the {@link Stream}.
     */
    Iterator<T> iterator();

    /**
     * Performs the supplied action on each element in the {@link Stream}.
     * <p>
     * <p>This is a terminal operation.</p>
     *
     * @param action An action as a {@link Consumer}.
     */
    void forEach(Consumer<? super T> action);
}
