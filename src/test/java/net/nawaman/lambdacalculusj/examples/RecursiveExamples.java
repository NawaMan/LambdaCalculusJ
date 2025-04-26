package net.nawaman.lambdacalculusj.examples;

import static net.nawaman.lambdacalculusj.LambdaCalculus.$;
import static net.nawaman.lambdacalculusj.LambdaCalculus.format;
import static net.nawaman.lambdacalculusj.LambdaCalculus.lambda;
import static net.nawaman.lambdacalculusj.LambdaCalculus.lazy;
import static net.nawaman.lambdacalculusj.LambdaCalculus.wholeNumber;
import static net.nawaman.lambdacalculusj.TestHelper.assertAsString;

import org.junit.jupiter.api.Test;

import net.nawaman.lambdacalculusj.Lambda;

class RecursiveExamples {
    
    private final Lambda zero  = lambda("0", lambda(f -> a -> a,                               () -> 0));
    private final Lambda one   = lambda("1", lambda(f -> a -> $(f, a),                         () -> 1));
    private final Lambda two   = lambda("2", lambda(f -> a -> $(f, $(f, a)),                   () -> 2));
    private final Lambda three = lambda("3", lambda(f -> a -> $(f, $(f, $(f, a))),             () -> 3));
    private final Lambda four  = lambda("4", lambda(f -> a -> $(f, $(f, $(f, $(f, a)))),       () -> 4));
    private final Lambda five  = lambda("5", lambda(f -> a -> $(f, $(f, $(f, $(f, $(f, a))))), () -> 5));
    
    private final Lambda TRUE  = lambda("TRUE",  x -> y -> x);
    private final Lambda FALSE = lambda("FALSE", x -> y -> y);
    
    private final Lambda successor   = lambda("successor", n ->      lambda(f -> a -> $(f, $(n, f, a)), () -> n.intValue() + 1));
    private final Lambda multiply    = lambda("multiply",  m -> n -> $(m, $(n, successor), zero));
    private final Lambda isZero      = lambda("isZero",    n ->      $(n, $(lambda(a -> b -> a), FALSE), TRUE));
    
    private final Lambda newPair     = lambda("newPair",     a -> b -> lambda(format("Pair[%s,%s]", a, b), f -> $(f,a,b)));
    private final Lambda firstOf     = lambda("firstOf",     p ->      $(p, lambda(a -> b -> a)));
    private final Lambda secondOf    = lambda("secondOf",    p ->      $(p, lambda(a -> b -> b)));
    private final Lambda transform   = lambda("transform",   p ->      $(newPair, $(secondOf, p), $(successor, $(secondOf, p))));
    private final Lambda predecessor = lambda("predecessor", n ->      $(firstOf, $($(n, transform), $(newPair, zero, zero))));
    
    private final Lambda add         = lambda("add",         n -> m -> $($(n, successor), m));
    private final Lambda subtract    = lambda("subtract",    n -> k -> $($(k, predecessor), n));
    private final Lambda lessOrEqual = lambda("lessOrEqual", n -> m -> $(isZero, $(subtract, n, m)));
    
    
    @Test
    void testFactorial() {
        var almostFactorial = lambda("almostFactorial", f -> n -> {
            return lazy($(isZero, n), one, lazy(multiply, n, lazy(f, f, lazy(predecessor, n))));
        });
        var factorial = lazy(almostFactorial, almostFactorial);
        assertAsString("1",  $(factorial, zero));
        assertAsString("1",  $(factorial, one));
        assertAsString("2",  $(factorial, two));
        assertAsString("6",  $(factorial, three));
        assertAsString("24", $(factorial, four));
    }
    
    @Test
    void testFactorial_yCombinator() {
        var yCombinator = lambda("Y", g -> lazy(lambda("Y'", x -> lazy(g, lazy(x, x))), lambda("Y''", x -> lazy(g, lazy(x, x)))));
        var factorial   = lazy(yCombinator, r -> n -> lazy($(isZero, n), one, lazy(multiply, n, lazy(r, lazy(predecessor, n)))));
        assertAsString("1",  $(factorial, zero));
        assertAsString("1",  $(factorial, one));
        assertAsString("2",  $(factorial, two));
        assertAsString("6",  $(factorial, three));
        assertAsString("24", $(factorial, four));
    }
    
    @Test
    void testFibonacci() {
        var yCombinator = lambda("Y", g -> lazy(lambda("Y'", x -> lazy(g, lazy(x, x))), lambda("Y''", x -> lazy(g, lazy(x, x)))));
        var fibonacci   = lazy(yCombinator, lambda(r -> n -> lazy($(lessOrEqual, n, two), one, lazy(add, lazy(r, lazy(predecessor, n)), lazy(r, lazy(predecessor, lazy(predecessor, n)))))));
        assertAsString("1",  $(fibonacci, one));
        assertAsString("1",  $(fibonacci, two));
        assertAsString("2",  $(fibonacci, three));
        assertAsString("3",  $(fibonacci, four));
        assertAsString("5",  $(fibonacci, five));
        assertAsString("8",  $(fibonacci, wholeNumber(6)));
        assertAsString("13", $(fibonacci, wholeNumber(7)));
    }
    
}
