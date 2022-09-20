package net.nawaman.lambdacalculusj;

import static net.nawaman.lambdacalculusj.TestHelper.assertAsString;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

class NumericLambdaTest {
    
    private Lambda five = new NumericLambda(5);
    
    @Test
    void testIntValue() {
        assertEquals(5, five.intValue());
    }
    
    @Test
    void testToString() {
        assertAsString("5", five);
    }
    
    @Test
    void testApply() {
        var logs    = new ArrayList<String>();
        var counter = new AtomicInteger(0);
        var log     = new LambdaWrapper("f", x -> {
            logs.add("#" + counter.getAndIncrement() + "-x:" + x);
            return x;
        });
        // Number give out a lazy lambda of the function and the parameter
        var fortyTwo = new NumericLambda(42);
        assertAsString("f(f(f(f(f(42)))))", five.apply(log).apply(fortyTwo));
        assertAsString("[]", logs);
        
        // Once evaluated, we get the result.
        assertAsString("42", five.apply(log).apply(fortyTwo).evaluate());
        assertAsString("["
                + "#0-x:f(f(f(f(42)))), "
                + "#1-x:f(f(f(42))), "
                + "#2-x:f(f(42)), "
                + "#3-x:f(42), "
                + "#4-x:42"
                + "]", logs);
    }
    
    @Test
    void testNAN() {
        var nan = new NumericLambda(f -> a -> { return null; }, () -> null);
        assertAsString("NAN", nan);
    }
    
    @Test
    void testNAN_NullPointerException() {
        var nan = new NumericLambda(f -> a -> { return null; }, () -> { throw new NullPointerException(); });
        assertAsString("NAN", nan);
    }
    
}
