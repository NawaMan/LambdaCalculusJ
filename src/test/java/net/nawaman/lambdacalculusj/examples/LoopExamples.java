package net.nawaman.lambdacalculusj.examples;

import static net.nawaman.lambdacalculusj.LambdaCalculus.$;
import static net.nawaman.lambdacalculusj.LambdaCalculus.format;
import static net.nawaman.lambdacalculusj.LambdaCalculus.lambda;
import static net.nawaman.lambdacalculusj.TestHelper.assertAsString;

import org.junit.jupiter.api.Test;

import net.nawaman.lambdacalculusj.Lambda;

class LoopExamples {
    
    private final Lambda zero  = lambda("0", lambda(f -> a -> a,                               () -> 0));
    private final Lambda one   = lambda("1", lambda(f -> a -> $(f, a),                         () -> 1));
    private final Lambda two   = lambda("2", lambda(f -> a -> $(f, $(f, a)),                   () -> 2));
    private final Lambda three = lambda("3", lambda(f -> a -> $(f, $(f, $(f, a))),             () -> 3));
    private final Lambda four  = lambda("4", lambda(f -> a -> $(f, $(f, $(f, $(f, a)))),       () -> 4));
    private final Lambda five  = lambda("5", lambda(f -> a -> $(f, $(f, $(f, $(f, $(f, a))))), () -> 5));
    
    private final Lambda successor   = lambda("successor", n -> lambda(f -> a -> $(f, $(n, f, a)), () -> n.intValue() + 1));
    private final Lambda multiply    = lambda("multiply",  m -> n -> $(m, $(n, successor), zero));
    
    private final Lambda newPair     = lambda("newPair",  a -> b -> lambda(format("Pair[%s,%s]", a, b), f -> $(f,a,b)));
    private final Lambda firstOf     = lambda("firstOf",  p -> $(p, lambda(a -> b -> a)));
    private final Lambda secondOf    = lambda("secondOf", p -> $(p, lambda(a -> b -> b)));
    
    private final Lambda add = lambda("add", n -> m -> $($(n, successor), m));
    
    
    @Test
    void testFactorial_loop() {
        var start     = $(newPair, lambda(0), lambda(1));
        var next      = lambda(p -> n -> $(newPair, n, $(multiply, n, $(secondOf, p))));
        var each      = lambda(p -> $(next, p, $(successor, $(firstOf, p))));
        var factorial = lambda(n -> $(secondOf, $($(n, each), start)));
        assertAsString("1",  $(factorial, zero));
        assertAsString("1",  $(factorial, one));
        assertAsString("2",  $(factorial, two));
        assertAsString("6",  $(factorial, three));
        assertAsString("24", $(factorial, four));
    }
    
    @Test
    void testFibonacci_loop() {
        // i: 0, 1, 2, 3, 4, 5, 6,  7,  8
        // f: 1, 1, 1, 2, 3, 5, 8, 13, 21
        // 0: 0, 1
        // 1:    1, 1
        // 2:      1, 2
        // 3:         2, 3
        // 4:            3, 5
        // 5:               5, 8
        // 6:                  8, 13
        // 7:                     13, 21
        var start     = $(newPair, zero, one);
        var each      = lambda(p -> $(newPair, $(secondOf, p), $(add, $(firstOf, p), $(secondOf, p))));
        var fibonacci = lambda(n -> $(firstOf, $($(n, each), start)));
        assertAsString("1",  $(fibonacci, one));
        assertAsString("1",  $(fibonacci, two));
        assertAsString("2",  $(fibonacci, three));
        assertAsString("3",  $(fibonacci, four));
        assertAsString("5",  $(fibonacci, five));
        assertAsString("8",  $(fibonacci, lambda(6)));
        assertAsString("13", $(fibonacci, lambda(7)));
        assertAsString("21", $(fibonacci, lambda(8)));
        assertAsString("34", $(fibonacci, lambda(9)));
    }
    
}
