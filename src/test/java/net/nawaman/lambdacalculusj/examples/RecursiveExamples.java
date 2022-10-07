package net.nawaman.lambdacalculusj.examples;

import static net.nawaman.lambdacalculusj.LambdaCalculus.$;
import static net.nawaman.lambdacalculusj.LambdaCalculus.format;
import static net.nawaman.lambdacalculusj.LambdaCalculus.lambda;
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
    
    private final Lambda successor   = lambda("successor", n -> lambda(f -> a -> $(f, $(n, f, a)), () -> n.intValue() + 1));
    private final Lambda multiply    = lambda("mul", m -> n -> $(m, $(n, successor), zero));
    private final Lambda falseOrElse = $(lambda(a -> b -> a), FALSE);
    private final Lambda isZero      = lambda("isZero", n -> $(n, falseOrElse, TRUE));
    
    private final Lambda pair        = lambda("pair",   a -> b -> lambda(format("Pair[%s,%s]", a, b), f -> $(f,a,b)));
    private final Lambda first       = lambda("first",  p -> $(p, lambda(a -> b -> a)));
    private final Lambda second      = lambda("second", p -> $(p, lambda(a -> b -> b)));
    private final Lambda transform   = lambda("trans",  p -> $(pair,  $(second, p), $(successor, $(second, p))));
    private final Lambda predecessor = lambda("pred",   n -> $(first, $($(n, transform), $(pair, zero, zero))));
    
    private final Lambda add         = lambda("add",       n -> m -> $($(n, successor), m));
    private final Lambda subtract    = lambda("subtract",    n -> k -> $($(k, predecessor), n));
    private final Lambda lessOrEqual = lambda("lessOrEqual", n -> m -> $(isZero, $(subtract, n, m)));
    
    @Test
    void testFactorial() {
        var almostFactorial = lambda("almostFactorial", f -> n -> {
            return $($(isZero, n), one, $(multiply, n, $(f, f, $(predecessor, n))));
        });
        var factorial = $(almostFactorial, almostFactorial);
        assertAsString("1",  $(factorial, zero) .evaluate());
        assertAsString("1",  $(factorial, one)  .evaluate());
        assertAsString("2",  $(factorial, two)  .evaluate());
        assertAsString("6",  $(factorial, three).evaluate());
        assertAsString("24", $(factorial, four) .evaluate());
    }
    
    @Test
    void testFactorial_yCombinator() {
        var yCombinator = lambda("Y", g -> $(lambda("Y'", x -> $(g, $(x, x))), lambda("Y''", x -> $(g, $(x, x)))));
        var factorial   = $(yCombinator, r -> n -> $($(isZero, n), one, $(multiply, n, $(r, $(predecessor, n)))));
        assertAsString("1",  $(factorial, zero) .evaluate());
        assertAsString("1",  $(factorial, one)  .evaluate());
        assertAsString("2",  $(factorial, two)  .evaluate());
        assertAsString("6",  $(factorial, three).evaluate());
        assertAsString("24", $(factorial, four) .evaluate());
    }
    
    @Test
    void testFibonacci() {
        var yCombinator = lambda("Y", g -> $(lambda("Y'", x -> $(g, $(x, x))), lambda("Y''", x -> $(g, $(x, x)))));
        var fibonacci   = $(yCombinator, lambda(r -> n -> $($(lessOrEqual, n, two), one, $(add, $(r, $(predecessor, n)), $(r, $(predecessor, $(predecessor, n)))))));
        assertAsString("1",  $(fibonacci, one)  .evaluate());
        assertAsString("1",  $(fibonacci, two)  .evaluate());
        assertAsString("2",  $(fibonacci, three).evaluate());
        assertAsString("3",  $(fibonacci, four) .evaluate());
        assertAsString("5",  $(fibonacci, five) .evaluate());
        assertAsString("8",  $(fibonacci, lambda(6)).evaluate());
        assertAsString("13", $(fibonacci, lambda(7)).evaluate());
        assertAsString("21", $(fibonacci, lambda(8)).evaluate());
    }
    
}
