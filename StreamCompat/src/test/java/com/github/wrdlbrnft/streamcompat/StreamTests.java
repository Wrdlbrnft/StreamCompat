package com.github.wrdlbrnft.streamcompat;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class StreamTests {

    @Test
    public void testCaseSimple() {
        final List<String> words = Arrays.asList("ASDF", "EEEE", "vvv", "zr", "wacka", "wacka");
        final String result = StreamCompat.of(words)
                .map(String::toLowerCase)
                .filter(word -> !word.contains("a"))
                .filter(word -> !word.contains("v"))
                .flatMap(word -> StreamCompat.of(word.toCharArray()))
                .filter(Predicates.not(this::isE))
                .map(Character::toUpperCase)
                .collect(Collectors.joining());

        Assert.assertEquals("ZR", result);
    }

    @Test
    public void testOrderingOfStrings() {
        final List<String> words = Arrays.asList("S", "ASDF", "EEEE", "vvv", "zr", "wacka", "wacka");
        final List<String> result = StreamCompat.of(words)
                .collect(Collectors.toOrdereredList());

        Assert.assertEquals(
                Arrays.asList("ASDF", "EEEE", "S", "vvv", "wacka", "wacka", "zr"),
                result
        );
    }

    @Test
    public void testCollectorAppend() {
        final List<String> words = Arrays.asList("ASDF", "EEEE", "vvv", "zr", "wacka", "wacka");
        final List<Character> characters = new ArrayList<>();
        final Collection<Character> result = StreamCompat.of(words)
                .map(String::toLowerCase)
                .filter(word -> !word.contains("a"))
                .filter(word -> !word.contains("v"))
                .flatMap(word -> StreamCompat.of(word.toCharArray()))
                .filter(Predicates.not(this::isE))
                .map(Character::toUpperCase)
                .collect(Collectors.append(characters));

        Assert.assertEquals(characters, result);
        Assert.assertEquals(Arrays.asList('Z', 'R'), result);
    }

    @Test
    public void testFlatMapAndJoin() {
        final Character[] characters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'};
        final String text = StreamCompat.of('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j')
                .filter(c -> c > 'c')
                .filter(c -> c < 'g')
                .filter(Predicates.not(this::isE))
                .flatMap(c -> StreamCompat.of(characters).filter(n -> n != c))
                .map(Character::toUpperCase)
                .collect(Collectors.joining(" "));

        Assert.assertEquals(text, "A B C E F G H I J A B C D E G H I J");
    }

    private boolean isE(char c) {
        return c == 'e' || c == 'E';
    }
}