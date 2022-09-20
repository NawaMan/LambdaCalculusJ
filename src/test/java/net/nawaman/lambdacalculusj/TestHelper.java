package net.nawaman.lambdacalculusj;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Objects;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

public class TestHelper {
    
    /**
     * This assert changes the actual value to string and match it with the expected string value.
     * 
     * The string value of actual must match the expected exactly.
     * If inexact matching is needed, start it with '\\E' and ended it with '\\Q'.
     **/
    public static void assertAsString(String expected, Object actual) {
        var expectedRegEx  = "^\\Q" + expected + "\\E$";
        var actualAsString = Objects.toString(actual);
        
        if (actualAsString.matches(expectedRegEx))
            return;
        
        assertEquals(expected, actualAsString);
    }
    
    /**
     * This assert changes the actual value to string and match it with the expected string value.
     * 
     * The string value of actual must match the expected exactly.
     * If inexact matching is needed, start it with '\\E' and ended it with '\\Q'.
     **/
    public static void assertAsString(String failureMessage, String expected, Object actual) {
        var expectedRegEx  = "^\\Q" + expected + "\\E$";
        var actualAsString = Objects.toString(actual);
        
        if (actualAsString.matches(expectedRegEx))
            return;
        
        assertEquals(expected, actualAsString, failureMessage);
    }
    
    /**
     * This assert changes the actual value to string and match it with the expected string value.
     * 
     * The string value of actual must match the expected exactly.
     * If inexact matching is needed, start it with '\\E' and ended it with '\\Q'.
     **/
    public static void assertAsString(Supplier<String> failureMessage, String expected, Object actual) {
        var expectedRegEx  = "^\\Q" + expected + "\\E$";
        var actualAsString = Objects.toString(actual);
        
        if (actualAsString.matches(expectedRegEx))
            return;
        if (Objects.equals(expected, actualAsString))
            return;
        
        var message = failureMessage.get();
        assertEquals(expected, actualAsString, message);
    }
    
    //== Tests for the above ==
    
    @Test
    public void testExact() {
        assertAsString(
                "Should match exacty", 
                "[One, 2, 3.0]", 
                asList("One", 2, 3.0));
    }
    
    @Test
    public void testExact_unmatch() {
        var failure = assertThrows(AssertionFailedError.class, () -> {
            assertAsString(
                    "Should not match", 
                    "[One, Two, 3.0]",
                    asList("One", 2, 3.0));
        });
        assertAsString(
                "org.opentest4j.AssertionFailedError: "
                + "Should not match ==> "
                + "expected: <[One, Two, 3.0]> but was: <[One, 2, 3.0]>",
                failure);
    }
    
    @Test
    public void testExact_regExLiked() {
        assertAsString(
                "When not use RegEx, any text that look like RegEx should still match exactly.", 
                "[One, [0-9]+, 3.0]", 
                asList("One", "[0-9]+", 3.0));
    }
    
    @Test
    public void testRegEx() {
        assertAsString(
                "Match as RegEx.", 
                "[One, \\E[0-9]+\\Q, 3.0]", 
                asList("One", 2, 3.0));
    }
    
    @Test
    public void testRegEx_unmatch() {
        var failure = assertThrows(AssertionFailedError.class, () -> {
            assertAsString(
                    "RegEx but not match.", 
                    "[One, \\E[0-9]+\\Q, 3.0]", 
                    asList("One", "Two", 3.0));
        });
        assertAsString(
                "org.opentest4j.AssertionFailedError: "
                + "RegEx but not match. ==> "
                + "expected: <[One, \\E[0-9]+\\Q, 3.0]> but was: <[One, Two, 3.0]>",
                failure);
    }
    
}