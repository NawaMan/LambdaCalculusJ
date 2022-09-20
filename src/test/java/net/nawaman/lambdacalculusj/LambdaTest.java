package net.nawaman.lambdacalculusj;

import static net.nawaman.lambdacalculusj.TestHelper.assertAsString;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

class LambdaTest {
    
    // The lambda under test.
    private Lambda theLambda = (Lambda)(x -> x);
    
    // A lambda to be used as parameter.
    private Lambda parameter = (Lambda)(x -> x);
    
    @Test
    void testToString() {
        assertAsString("\\E.*\\Q$$Lambda$\\E[0-9]+\\Q/0x\\E[0-9a-f]+@[0-9a-f]+\\Q", theLambda);
    }
    
    @Test
    void testIntValue() {
        assertAsString(null, theLambda.intValue());
    }
    
    @Test
    void testEvaluate() {
        assertEquals(theLambda, theLambda.evaluate());
    }
    
    @Test
    void testApply() {
        var counter   = new AtomicInteger(0);
        var theLambda = (Lambda)(x -> {
            counter.getAndIncrement();
            return x;
        });
        assertEquals(0, counter.get());
        
        assertEquals(parameter, theLambda.apply(parameter));
        assertEquals(1, counter.get());
        
        assertEquals(parameter, theLambda.apply(parameter));
        assertEquals(2, counter.get());
    }
    
}
