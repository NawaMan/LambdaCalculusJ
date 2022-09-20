package net.nawaman.lambdacalculusj;

import static net.nawaman.lambdacalculusj.TestHelper.assertAsString;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

class LazyLambdaTest {
    
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
    
    // A lambda to be used as parameter.
    private Lambda parameter1 = new LambdaWrapper("parameter1", x -> {
        logs.add("parameter1(" + x+ ")");
        return x;
    });
    private Lambda parameter2 = new LambdaWrapper("parameter2", x -> {
        logs.add("parameter1(" + x+ ")");
        return x;
    });
    
    // The lambda under test.
    private Lambda lazyLambda = new LazyLambda("theLambda", innerLambda, parameter1);
    
    @Test
    void testToString() {
        assertAsString("theLambda(parameter1)", lazyLambda);
    }
    
    @Test
    void testApply() {
        assertAsString("[]", logs);
        
        assertAsString("parameter2", lazyLambda.apply(parameter2));
        assertAsString("[apply(parameter2)]", logs);
    }
    
    @Test
    void testEvaluate() {
        assertAsString("[]", logs);
        
        // Evaluate a lazy lambda expression will simply apply the parameter.
        assertAsString("parameter1", lazyLambda.evaluate());
        assertAsString("[evaluate(): 0, apply(parameter1)]", logs);
    }
    
    @Test
    void testIntValue() {
        // The lazy lambda delegate the intValue to innerLambda which is delegated further to the parameter1.
        var parameter1 = new LambdaWrapper("parameter1", x -> {
            logs.add("parameter1(" + x+ ")");
            return x;
        }) {
            @Override
            public Integer intValue() {
                return 42;
            }
            
        };
        var lazyLambda = new LazyLambda("theLambda", innerLambda, parameter1);
        assertAsString("42", lazyLambda.intValue());
    }
    
}
