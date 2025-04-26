package net.nawaman.lambdacalculusj;

import static net.nawaman.lambdacalculusj.TestHelper.assertAsString;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

class LambdaWrapperTest {
    
    private AtomicInteger counter = new AtomicInteger(0);
    private List<String>  logs    = new ArrayList<>();
    
    // The lambda under test.
    private Lambda innerLambda = new Lambda() {
        @Override
        public Lambda apply(Lambda input) {
            logs.add("apply(" + input+ ")");
            return input;
        }
        @Override
        public Integer intValue() {
            return counter.getAndIncrement();
        }
        @Override
        public Lambda evaluate() {
            logs.add("evaluate(): " + counter.get());
            return Lambda.super.evaluate();
        }
    };
    
    // The lambda under test.
    private Lambda wrapperLambda = new LambdaWrapper("theLambda", innerLambda);
    
    // A lambda to be used as parameter.
    private Lambda parameter = new LambdaWrapper("parameter", x -> x);
    
    @Test
    void testToString_named() {
        assertAsString("theLambda", wrapperLambda);
    }
    
    @Test
    void testToString_nullName() {
        var theLambda = new LambdaWrapper(null, x -> x);
        assertAsString("lambda(*)", theLambda);
    }
    
    @Test
    void testIntValue() {
        // Assert that the inner value is called
        
        assertEquals(0, counter.get());
        assertEquals(0, wrapperLambda.intValue());
        
        assertEquals(1, counter.get());
        assertEquals(1, wrapperLambda.intValue());
        
        assertEquals(2, counter.get());
    }
    
    @Test
    void testEvaluate() {
        // Assert that evaluate() which is associated with laziness
        // ... has nothing to do with the inner lambda.
        // and that no call is propagate there.
        assertAsString("[]", logs);
        
        assertEquals(wrapperLambda, wrapperLambda.evaluate());
        assertAsString("[]", logs);
    }
    
    @Test
    void testApply() {
        assertAsString("[]", logs);
        
        assertEquals(parameter, wrapperLambda.apply(parameter));
        assertAsString("[apply(parameter)]", logs);
        
        assertEquals(parameter, wrapperLambda.apply(parameter));
        assertAsString("[apply(parameter), apply(parameter)]", logs);
    }
    
}
