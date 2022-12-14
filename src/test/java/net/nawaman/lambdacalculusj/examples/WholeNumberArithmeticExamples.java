package net.nawaman.lambdacalculusj.examples;

import static net.nawaman.lambdacalculusj.LambdaCalculus.lazy;
import static net.nawaman.lambdacalculusj.LambdaCalculus.$;
import static net.nawaman.lambdacalculusj.LambdaCalculus.format;
import static net.nawaman.lambdacalculusj.LambdaCalculus.lambda;
import static net.nawaman.lambdacalculusj.LambdaCalculus.wholeNumber;
import static net.nawaman.lambdacalculusj.TestHelper.assertAsString;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import net.nawaman.lambdacalculusj.Lambda;

public class WholeNumberArithmeticExamples {
    
    private final Lambda a = lambda("a", x -> x);
    
    private final List<String> logs = new ArrayList<>();
    private final Lambda func = lambda("func", x -> {
        logs.add("func(" + x + ")");
        return x;
    });
    
    @Test
    void testLiterals_zero() {
        var zero = lambda("0", lambda(f -> a -> a, () -> 0));
        assertAsString("0", zero);
        assertAsString("a", zero.apply(func).apply(a));
        assertAsString("a", zero.apply(func).apply(a).evaluate());
        assertAsString("[]", logs);
    }
    
    @Test
    void testLiterals_one() {
        var one = lambda("1", lambda(f -> a -> lazy(f, a), () -> 1));
        assertAsString("1", one);
        assertAsString("func(a)", one.apply(func).apply(a));
        assertAsString("a",       one.apply(func).apply(a).evaluate());
        assertAsString("["
                        + "func(a)"
                        + "]", logs);
    }
    
    @Test
    void testLiterals_two() {
        var two = lambda("2", lambda(f -> a -> lazy(f, lazy(f, a)), () -> 2));
        assertAsString("2", two);
        assertAsString("func(func(a))", two.apply(func).apply(a));
        assertAsString("a",             two.apply(func).apply(a).evaluate());
        assertAsString("["
                        + "func(func(a)), "
                        + "func(a)"
                        + "]", logs);
    }
    
    @Test
    void testLiterals_five() {
        var five = lambda("5", lambda(f -> a -> lazy(f, lazy(f, lazy(f, lazy(f, lazy(f, a))))), () -> 5));
        assertAsString("5", five);
        assertAsString("func(func(func(func(func(a)))))", five.apply(func).apply(a));
        assertAsString("a",                               five.apply(func).apply(a).evaluate());
        assertAsString("["
                        + "func(func(func(func(func(a))))), "
                        + "func(func(func(func(a)))), "
                        + "func(func(func(a))), "
                        + "func(func(a)), "
                        + "func(a)"
                        + "]", logs);
    }
    
    @Test
    void testEatFiveCake() {
        var cookie = lambda("cookie", x -> x);
        var eat    = lambda("eat", x -> {
            logs.add("eat(" + x + ")");
            return x;
        });
        
        var five = lambda("5", lambda(f -> a -> lazy(f, lazy(f, lazy(f, lazy(f, lazy(f, a))))), () -> 5));
        assertAsString("eat(eat(eat(eat(eat(cookie)))))", five.apply(eat).apply(cookie));
        
        five.apply(eat).apply(cookie).evaluate();
        assertAsString("["
                        + "eat(eat(eat(eat(eat(cookie))))), "
                        + "eat(eat(eat(eat(cookie)))), "
                        + "eat(eat(eat(cookie))), "
                        + "eat(eat(cookie)), "
                        + "eat(cookie)"
                        + "]", logs);
    }
    
    @Test
    void testSyntacticSugar() {
        var cookie = lambda("cookie", x -> x);
        var eat    = lambda("eat", x -> {
            logs.add("eat(" + x + ")");
            return x;
        });
        
        //== Zero ==
        
        var zeroDirect = lambda("0", lambda(f -> a -> a, () -> 0));
        var zeroShort  = wholeNumber(0);
        
        logs.clear();
        zeroDirect.apply(eat).apply(cookie).evaluate();
        assertAsString("[]", logs);
        
        logs.clear();
        zeroShort.apply(eat).apply(cookie).evaluate();
        assertAsString("[]", logs);
        
        //== Two ==
        
        var twoDirect = lambda("2", lambda(f -> a -> lazy(f, lazy(f, a)), () -> 2));
        var twoShort  = wholeNumber(2);
        
        logs.clear();
        twoDirect.apply(eat).apply(cookie).evaluate();
        assertAsString("["
                        + "eat(eat(cookie)), "
                        + "eat(cookie)"
                        + "]", logs);
        
        logs.clear();
        twoShort.apply(eat).apply(cookie).evaluate();
        assertAsString("["
                        + "eat(eat(cookie)), "
                        + "eat(cookie)"
                        + "]", logs);
        
        //== Five ==
        
        var fiveDirect = lambda("5", lambda(f -> a -> lazy(f, lazy(f, lazy(f, lazy(f, lazy(f, a))))), () -> 5));
        var fiveShort  = wholeNumber(5);
        
        logs.clear();
        fiveDirect.apply(eat).apply(cookie).evaluate();
        assertAsString("["
                        + "eat(eat(eat(eat(eat(cookie))))), "
                        + "eat(eat(eat(eat(cookie)))), "
                        + "eat(eat(eat(cookie))), "
                        + "eat(eat(cookie)), "
                        + "eat(cookie)"
                        + "]", logs);
        
        logs.clear();
        fiveShort.apply(eat).apply(cookie).evaluate();
        assertAsString("["
                        + "eat(eat(eat(eat(eat(cookie))))), "
                        + "eat(eat(eat(eat(cookie)))), "
                        + "eat(eat(eat(cookie))), "
                        + "eat(eat(cookie)), "
                        + "eat(cookie)"
                        + "]", logs);
    }
    
    @Test
    void testEvenOdd() {
        var zero  = wholeNumber(0);
        var one   = wholeNumber(1);
        var two   = wholeNumber(2);
        var three = wholeNumber(3);
        var four  = wholeNumber(4);
        var five  = wholeNumber(5);
        
        var TRUE  = lambda("TRUE",  x -> y -> x);
        var FALSE = lambda("FALSE", x -> y -> y);
        var not   = lambda("not",   bool -> $(bool, FALSE, TRUE));
        var isOdd = lambda("isOdd", n -> n.apply(not).apply(FALSE).evaluate());
        
        assertAsString("zero is even (not an odd).", "FALSE", isOdd.apply(zero));
        assertAsString("one is odd.",                "TRUE",  isOdd.apply(one));
        assertAsString("two is even (not an odd).",  "FALSE", isOdd.apply(two));
        assertAsString("three is odd.",              "TRUE",  isOdd.apply(three));
        assertAsString("four is even (not an odd).", "FALSE", isOdd.apply(four));
        assertAsString("five is odd.",               "TRUE",  isOdd.apply(five));
    }
    
    @Test
    void testSuccessor() {
        var successor = lambda("successor", n -> lambda(f -> a -> lazy(f, lazy(n, f, a)), () -> n.intValue() + 1));
        
        var cookie = lambda("cookie", x -> x);
        var eat    = lambda("eat", x -> {
            logs.add("eat(" + x + ")");
            return x;
        });
        
        logs.clear();
        var zero = lambda(f -> a -> a, () -> 0);
        assertAsString("0", zero);
        assertAsString("0", zero.intValue());
        assertAsString("0(eat)(cookie)", lazy(zero, eat, cookie));
        zero.apply(eat).apply(cookie).evaluate();
        assertAsString("[]", logs);
        
        logs.clear();
        var one = lazy(successor, zero);
        assertAsString("successor(0)",              one);
        assertAsString("1",                         one.intValue());
        assertAsString("successor(0)(eat)(cookie)", lazy(one, eat, cookie));
        lazy(one, eat, cookie).evaluate();
        assertAsString("["
                        + "eat(0(eat)(cookie))"
                        + "]", logs);
        
        logs.clear();
        var two = lazy(successor, one);
        assertAsString("successor(successor(0))",              two);
        assertAsString("2",                                    two.intValue());
        assertAsString("successor(successor(0))(eat)(cookie)", lazy(two, eat, cookie));
        lazy(two, eat, cookie).evaluate();
        assertAsString("["
                        + "eat(successor(0)(eat)(cookie)), "
                        + "eat(0(eat)(cookie))"
                        + "]", logs);
        
        logs.clear();
        var three = lazy(successor, two);
        assertAsString("successor(successor(successor(0)))",              three);
        assertAsString("3",                                               three.intValue());
        assertAsString("successor(successor(successor(0)))(eat)(cookie)", lazy(three, eat, cookie));
        lazy(three, eat, cookie).evaluate();
        assertAsString("["
                        + "eat(successor(successor(0))(eat)(cookie)), "
                        + "eat(successor(0)(eat)(cookie)), "
                        + "eat(0(eat)(cookie))"
                        + "]", logs);
        
        logs.clear();
        var five = lazy(successor, lazy(successor, lazy(successor, lazy(successor, lazy(successor, zero)))));
        assertAsString("successor(successor(successor(successor(successor(0)))))",              five);
        assertAsString("5",                                            five.intValue());
        assertAsString("successor(successor(successor(successor(successor(0)))))(eat)(cookie)", lazy(five, eat, cookie));
        lazy(five, eat, cookie).evaluate();
        assertAsString("["
                        + "eat(successor(successor(successor(successor(0))))(eat)(cookie)), "
                        + "eat(successor(successor(successor(0)))(eat)(cookie)), "
                        + "eat(successor(successor(0))(eat)(cookie)), "
                        + "eat(successor(0)(eat)(cookie)), "
                        + "eat(0(eat)(cookie))"
                        + "]", logs);
    }
    
    @Test
    void testSuccessor_another() {
        var successor = lambda("successor", n -> lambda(f -> a -> lazy(f, lazy(n, f, a)), () -> n.intValue() + 1));
        
        var cookie = lambda("cookie", x -> x);
        var eat    = lambda("eat", x -> {
            logs.add("eat(" + x + ")");
            return x;
        });
        
        logs.clear();
        var zero = lambda(f -> a -> a, () -> 0);
        var two  = lazy(successor, lazy(successor, zero));
        
        // == Use $(...) ==
        var eatTwoCookies$ = lazy(two, eat, cookie);
        logs.clear();
        assertAsString("successor(successor(0))(eat)(cookie)", eatTwoCookies$);
        assertAsString("[]", logs);
        
        logs.clear();
        eatTwoCookies$.evaluate();
        assertAsString("["
                + "eat(successor(0)(eat)(cookie)), "
                + "eat(0(eat)(cookie))"
                + "]", logs);
        
        // == Use apply ==
        var eatTwoCookiesApply = two.apply(eat).apply(cookie);
        logs.clear();
        assertAsString("eat(successor(0)(eat)(cookie))", eatTwoCookiesApply);
        assertAsString("[]", logs);
        
        logs.clear();
        eatTwoCookiesApply.evaluate();
        assertAsString("["
                + "eat(successor(0)(eat)(cookie)), "
                + "eat(0(eat)(cookie))"
                + "]", logs);
    }
    
    @Test
    void testAdd() {
        var successor = lambda("successor", n -> lambda(f -> a -> $(f, $(n, f, a)), () -> n.intValue() + 1));
        var add       = lambda("add",       n -> m -> $($(n, successor), m));
        
        assertAsString("add(2)(3)", lazy(add, wholeNumber(2), wholeNumber(3)));
        assertAsString("5",         $(add, wholeNumber(2), wholeNumber(3)));
        
        var five = lazy(add, wholeNumber(2), wholeNumber(3));
        assertAsString("add(3)(add(2)(3))", lazy(add, wholeNumber(3), five));
        assertAsString("8",                 $(add, wholeNumber(3), five));
    }
    
    @Test
    void testMultiply() {
        var zero      = wholeNumber(0);
        var successor = lambda("successor", n -> lambda(f -> a -> $(f, $(n, f, a)), () -> n.intValue() + 1));
        var multiply  = lambda("multiply",  n -> m -> $(m, $(n, successor), zero));
        
        var two   = wholeNumber(2);
        var three = wholeNumber(3);
        assertAsString("multiply(2)(3)", lazy(multiply, two, three));
        assertAsString("6",              $(multiply, two, three));
        
        var six = lazy(multiply, two, three);
        assertAsString("multiply(3)(multiply(2)(3))", lazy(multiply, three, six));
        assertAsString("18",                          $(multiply, three, six));
    }
    
    @Test
    void testPower() {
        var zero      = wholeNumber(0);
        var one       = wholeNumber(1);
        var successor = lambda("successor", n -> lambda(f -> a -> $(f, $(n, f, a)), () -> n.intValue() + 1));
        var multiply  = lambda("multiply",  n -> m -> $(m, $(n, successor), zero));
        var power     = lambda("power",     n -> p -> $($(p, $(multiply, n), one)));
        
        assertAsString("power(2)(3)", lazy(power, wholeNumber(2), wholeNumber(3)));
        assertAsString("8",           $(power,  wholeNumber(2), wholeNumber(3)));
        
        var eight = lazy(power, wholeNumber(2), wholeNumber(3));
        assertAsString("power(2)(power(2)(3))", lazy(power, wholeNumber(2), eight));
        assertAsString("256",                   $(power, wholeNumber(2), eight));
    }
    
    @Test
    void testIsZero() {
        var TRUE  = lambda("TRUE",  x -> y -> x);
        var FALSE = lambda("FALSE", x -> y -> y);
        
        var falseOrElse = $(lambda(a -> b -> a), FALSE);
        var isZero      = lambda("isZero", n -> $(n, falseOrElse, TRUE));
        assertAsString("TRUE",  $(isZero, wholeNumber(0)));
        assertAsString("FALSE", $(isZero, wholeNumber(1)));
        assertAsString("FALSE", $(isZero, wholeNumber(5)));
    }
    
    @Test
    void testPredecessor() {
        var zero        = lambda("0",           lambda(f -> a -> a, () -> 0));
        var successor   = lambda("successor",   n -> lambda(f -> a -> $(f, $(n, f, a)), () -> n.intValue() + 1));
        var newPair     = lambda("newPair",     a -> b -> lambda(format("Pair[%s,%s]", a, b), f -> $(f,a,b)));
        var firstOf     = lambda("firstOf",     p -> $(p, lambda(a -> b -> a)));
        var secondOf    = lambda("secondOf",    p -> $(p, lambda(a -> b -> b)));
        var transform   = lambda("transform",   p -> $(newPair,  $(secondOf, p), $(successor, $(secondOf, p))));
        var predecessor = lambda("predecessor", n -> $(firstOf, $($(n, transform), $(newPair, zero, zero))));
        
        assertAsString("0", $(predecessor, zero));
        assertAsString("0", $(predecessor, wholeNumber(1)));
        assertAsString("4", $(predecessor, wholeNumber(5)));
    }
    
    @Test
    void testSubtract() {
        var zero        = lambda("0",           lambda(f -> a -> a, () -> 0));
        var succ        = lambda("succ",        n -> lambda(f -> a -> $(f, $(n, f, a)), () -> n.intValue() + 1));
        var pairOf      = lambda("pairOf",      a -> b -> lambda(format("Pair[%s,%s]", a, b), f -> $(f,a,b)));
        var first       = lambda("first",       p -> $(p, lambda(a -> b -> a)));
        var second      = lambda("second",      p -> $(p, lambda(a -> b -> b)));
        var transform   = lambda("transform",   p -> $(pairOf, $(second, p), $(succ, $(second, p))));
        var predecessor = lambda("predecessor", n -> $(first, $($(n, transform), $(pairOf, zero, zero))));
        var subtract    = lambda("subtract",    n -> k -> $($(k, predecessor), n));
        
        assertAsString("4", $(subtract, wholeNumber(5), wholeNumber(1)));
        assertAsString("1", $(subtract, wholeNumber(3), wholeNumber(2)));
    }
    
    @Test
    void testNumberComparison() {
        var TRUE  = lambda("TRUE",  x -> y -> x);
        var FALSE = lambda("FALSE", x -> y -> y);
        var not   = lambda("not",   bool -> $(bool, FALSE, TRUE));
        var and   = lambda("and",   p -> q -> $(p, q, p));
        var or    = lambda("or",    p -> q -> $(p, p, q));
        
        var falseOrElse = $(lambda(a -> b -> a), FALSE);
        var isZero      = lambda("isZero", n -> $(n, falseOrElse, TRUE));
        
        var zero        = lambda("0",           lambda(f -> a -> a, () -> 0));
        var succ        = lambda("succ",        n -> lambda(f -> a -> $(f, $(n, f, a)), () -> n.intValue() + 1));
        var pairOf      = lambda("pairOf",      a -> b -> lambda(format("Pair[%s,%s]", a, b), f -> $(f,a,b)));
        var first       = lambda("first",       p -> $(p, lambda(a -> b -> a)));
        var second      = lambda("second",      p -> $(p, lambda(a -> b -> b)));
        var transform   = lambda("transform",   p -> $(pairOf, $(second, p), $(succ, $(second, p))));
        var predecessor = lambda("predecessor", n -> $(first, $($(n, transform), $(pairOf, zero, zero))));
        var subtract    = lambda("subtract",    n -> k -> $($(k, predecessor), n));
        
        var lessOrEqual = lambda("lessOrEqual", n -> m -> $(isZero, $(subtract, n, m)));
        assertAsString("FALSE", $(lessOrEqual, wholeNumber(5), wholeNumber(1)));
        assertAsString("TRUE",  $(lessOrEqual, wholeNumber(5), wholeNumber(5)));
        assertAsString("TRUE",  $(lessOrEqual, wholeNumber(5), wholeNumber(7)));
        
        var greaterThan = lambda("greaterThan", n -> m -> $(not, $(lessOrEqual, n, m)));
        assertAsString("TRUE",  $(greaterThan, wholeNumber(5), wholeNumber(1)));
        assertAsString("FALSE", $(greaterThan, wholeNumber(5), wholeNumber(5)));
        assertAsString("FALSE", $(greaterThan, wholeNumber(5), wholeNumber(7)));
        
        var equal = lambda("equal", n -> m -> $(and, $(lessOrEqual, n, m), $(lessOrEqual, m, n)));
        assertAsString("FALSE", $(equal, wholeNumber(5), wholeNumber(1)));
        assertAsString("TRUE",  $(equal, wholeNumber(5), wholeNumber(5)));
        assertAsString("FALSE", $(equal, wholeNumber(5), wholeNumber(7)));
        
        var lessThan = lambda("lessThan", n -> m -> $(and, $(lessOrEqual, n, m), $(not, $(lessOrEqual, m, n))));
        assertAsString("FALSE", $(lessThan, wholeNumber(5), wholeNumber(1)));
        assertAsString("FALSE", $(lessThan, wholeNumber(5), wholeNumber(5)));
        assertAsString("TRUE",  $(lessThan, wholeNumber(5), wholeNumber(7)));
        
        var greaterOrEqual = lambda("greaterOrEqual", n -> m -> $(or, $(greaterThan, n, m), $(equal, n, m)));
        assertAsString("TRUE",  $(greaterOrEqual, wholeNumber(5), wholeNumber(1)));
        assertAsString("TRUE",  $(greaterOrEqual, wholeNumber(5), wholeNumber(5)));
        assertAsString("FALSE", $(greaterOrEqual, wholeNumber(5), wholeNumber(7)));
    }
    
}
//