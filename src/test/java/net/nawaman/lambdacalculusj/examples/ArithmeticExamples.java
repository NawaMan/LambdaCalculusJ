package net.nawaman.lambdacalculusj.examples;

import static net.nawaman.lambdacalculusj.LambdaCalculus.$;
import static net.nawaman.lambdacalculusj.LambdaCalculus.displayValue;
import static net.nawaman.lambdacalculusj.LambdaCalculus.lambda;
import static net.nawaman.lambdacalculusj.TestHelper.assertAsString;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import net.nawaman.lambdacalculusj.Lambda;

public class ArithmeticExamples {
    
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
        var one = lambda("1", lambda(f -> a -> $(f, a), () -> 1));
        assertAsString("1", one);
        assertAsString("func(a)", one.apply(func).apply(a));
        assertAsString("a",       one.apply(func).apply(a).evaluate());
        assertAsString("["
                        + "func(a)"
                        + "]", logs);
    }
    
    @Test
    void testLiterals_two() {
        var two = lambda("2", lambda(f -> a -> $(f, $(f, a)), () -> 2));
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
        var five = lambda("5", lambda(f -> a -> $(f, $(f, $(f, $(f, $(f, a))))), () -> 5));
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
        
        var five = lambda("5", lambda(f -> a -> $(f, $(f, $(f, $(f, $(f, a))))), () -> 5));
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
        var zeroShort  = lambda(0);
        
        logs.clear();
        zeroDirect.apply(eat).apply(cookie).evaluate();
        assertAsString("[]", logs);
        
        logs.clear();
        zeroShort.apply(eat).apply(cookie).evaluate();
        assertAsString("[]", logs);
        
        //== Two ==
        
        var twoDirect = lambda("2", lambda(f -> a -> $(f, $(f, a)), () -> 2));
        var twoShort  = lambda(2);
        
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
        
        var fiveDirect = lambda("5", lambda(f -> a -> $(f, $(f, $(f, $(f, $(f, a))))), () -> 5));
        var fiveShort  = lambda(5);
        
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
        var zero  = lambda(0);
        var one   = lambda(1);
        var two   = lambda(2);
        var three = lambda(3);
        var four  = lambda(4);
        var five  = lambda(5);
        
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
        var successor = lambda("successor", n -> lambda(f -> a -> $(f, $(n, f, a)), () -> n.intValue() + 1));
        
        var cookie = lambda("cookie", x -> x);
        var eat    = lambda("eat", x -> {
            logs.add("eat(" + x + ")");
            return x;
        });
        
        logs.clear();
        var zero = lambda(f -> a -> a, () -> 0);
        assertAsString("0", zero);
        assertAsString("0", zero.intValue());
        assertAsString("0(eat)(cookie)", $(zero, eat, cookie));
        zero.apply(eat).apply(cookie).evaluate();
        assertAsString("[]", logs);
        
        logs.clear();
        var one = $(successor, zero);
        assertAsString("successor(0)",              one);
        assertAsString("1",                         one.intValue());
        assertAsString("successor(0)(eat)(cookie)", $(one, eat, cookie));
        $(one, eat, cookie).evaluate();
        assertAsString("["
                        + "eat(0(eat)(cookie))"
                        + "]", logs);
        
        logs.clear();
        var two = $(successor, one);
        assertAsString("successor(successor(0))",              two);
        assertAsString("2",                                    two.intValue());
        assertAsString("successor(successor(0))(eat)(cookie)", $(two, eat, cookie));
        $(two, eat, cookie).evaluate();
        assertAsString("["
                        + "eat(successor(0)(eat)(cookie)), "
                        + "eat(0(eat)(cookie))"
                        + "]", logs);
        
        logs.clear();
        var three = $(successor, two);
        assertAsString("successor(successor(successor(0)))",              three);
        assertAsString("3",                                               three.intValue());
        assertAsString("successor(successor(successor(0)))(eat)(cookie)", $(three, eat, cookie));
        $(three, eat, cookie).evaluate();
        assertAsString("["
                        + "eat(successor(successor(0))(eat)(cookie)), "
                        + "eat(successor(0)(eat)(cookie)), "
                        + "eat(0(eat)(cookie))"
                        + "]", logs);
        
        logs.clear();
        var five = $(successor, $(successor, $(successor, $(successor, $(successor, zero)))));
        assertAsString("successor(successor(successor(successor(successor(0)))))",              five);
        assertAsString("5",                                            five.intValue());
        assertAsString("successor(successor(successor(successor(successor(0)))))(eat)(cookie)", $(five, eat, cookie));
        $(five, eat, cookie).evaluate();
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
        var successor = lambda("successor", n -> lambda(f -> a -> $(f, $(n, f, a)), () -> n.intValue() + 1));
        
        var cookie = lambda("cookie", x -> x);
        var eat    = lambda("eat", x -> {
            logs.add("eat(" + x + ")");
            return x;
        });
        
        logs.clear();
        var zero = lambda(f -> a -> a, () -> 0);
        var two  = $(successor, $(successor, zero));
        
        // == Use $(...) ==
        var eatTwoCookies$ = $(two, eat, cookie);
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
        
        assertAsString("add(2)(3)", $(add, lambda(2), lambda(3)));
        assertAsString("5",         $(add, lambda(2), lambda(3)).evaluate());
        
        var five = $(add, lambda(2), lambda(3));
        assertAsString("add(3)(add(2)(3))", $(add, lambda(3), five));
        assertAsString("8",                 $(add, lambda(3), five).evaluate());
    }
    
    @Test
    void testMultiply() {
        var zero      = lambda(0);
        var successor = lambda("successor", n -> lambda(f -> a -> $(f, $(n, f, a)), () -> n.intValue() + 1));
        var multiply  = lambda("multiply",  n -> m -> $(m, $(n, successor), zero));
        
        var two   = lambda(2);
        var three = lambda(3);
        assertAsString("multiply(2)(3)", $(multiply, two, three));
        assertAsString("6",              $(multiply, two, three).evaluate());
        
        var six = $(multiply, two, three);
        assertAsString("multiply(3)(multiply(2)(3))", $(multiply, three, six));
        assertAsString("18",                          $(multiply, three, six).evaluate());
    }
    
    @Test
    void testPower() {
        var zero      = lambda(0);
        var one       = lambda(1);
        var successor = lambda("successor", n -> lambda(f -> a -> $(f, $(n, f, a)), () -> n.intValue() + 1));
        var multiply  = lambda("multiply",  n -> m -> $(m, $(n, successor), zero));
        var power     = lambda("power",     n -> p -> $($(p, $(multiply, n), one)));
        
        assertAsString("power(2)(3)", $(power, lambda(2), lambda(3)));
        assertAsString("8",           $(power, lambda(2), lambda(3)).evaluate());
        
        var eight = $(power, lambda(2), lambda(3));
        assertAsString("power(2)(power(2)(3))", $(power, lambda(2), eight));
        assertAsString("256",                   $(power, lambda(2), eight).evaluate());
    }
    
    @Test
    void testIsZero() {
        var TRUE  = lambda("TRUE",  x -> y -> x);
        var FALSE = lambda("FALSE", x -> y -> y);
        
        var falseOrElse = $(lambda(a -> b -> a), FALSE);
        var isZero      = lambda("isZero", n -> $(n, falseOrElse, TRUE));
        assertAsString("TRUE",  $(isZero, lambda(0)).evaluate());
        assertAsString("FALSE", $(isZero, lambda(1)).evaluate());
        assertAsString("FALSE", $(isZero, lambda(5)).evaluate());
    }
    
    @Test
    void testPredecessor() {
        var zero        = lambda("0", lambda(f -> a -> a, () -> 0));
        var successor   = lambda("successor", n -> lambda(f -> a -> $(f, $(n, f, a)), () -> n.intValue() + 1));
        var pair        = lambda("pair",   a -> b -> lambda("Pair[" + displayValue(a) + "," + displayValue(b) + "]", f -> $(f,a,b)));
        var first       = lambda("first",  p -> $(p, lambda(a -> b -> a)));
        var second      = lambda("second", p -> $(p, lambda(a -> b -> b)));
        var transform   = lambda("trans",  p -> $(pair,  $(second, p), $(successor, $(second, p))));
        var predecessor = lambda("pred",   n -> $(first, $($(n, transform), $(pair, zero, zero))));
        
        assertAsString("0", $(predecessor, zero)     .evaluate());
        assertAsString("0", $(predecessor, lambda(1)).evaluate());
        assertAsString("4", $(predecessor, lambda(5)).evaluate());
    }
    
    @Test
    void testSubtract() {
        var zero        = lambda("0",           lambda(f -> a -> a, () -> 0));
        var succ        = lambda("succ",        n -> lambda(f -> a -> $(f, $(n, f, a)), () -> n.intValue() + 1));
        var pairOf      = lambda("pairOf",      a -> b -> lambda("Pair[" + displayValue(a) + "," + displayValue(b) + "]", f -> $(f,a,b)));
        var first       = lambda("first",       p -> $(p, lambda(a -> b -> a)));
        var second      = lambda("second",      p -> $(p, lambda(a -> b -> b)));
        var transform   = lambda("transform",   p -> $(pairOf, $(second, p), $(succ, $(second, p))));
        var predecessor = lambda("predecessor", n -> $(first, $($(n, transform), $(pairOf, zero, zero))));
        var subtract    = lambda("subtract",    n -> k -> $($(k, predecessor), n));
        
        assertAsString("4", $(subtract, lambda(5), lambda(1)).evaluate());
        assertAsString("1", $(subtract, lambda(3), lambda(2)).evaluate());
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
        var pairOf      = lambda("pairOf",      a -> b -> lambda("Pair[" + displayValue(a) + "," + displayValue(b) + "]", f -> $(f,a,b)));
        var first       = lambda("first",       p -> $(p, lambda(a -> b -> a)));
        var second      = lambda("second",      p -> $(p, lambda(a -> b -> b)));
        var transform   = lambda("transform",   p -> $(pairOf, $(second, p), $(succ, $(second, p))));
        var predecessor = lambda("predecessor", n -> $(first, $($(n, transform), $(pairOf, zero, zero))));
        var subtract    = lambda("subtract",    n -> k -> $($(k, predecessor), n));
        
        var lessOrEqual = lambda("lessOrEqual", n -> m -> $(isZero, $(subtract, n, m)));
        assertAsString("FALSE", $(lessOrEqual, lambda(5), lambda(1)).evaluate());
        assertAsString("TRUE",  $(lessOrEqual, lambda(5), lambda(5)).evaluate());
        assertAsString("TRUE",  $(lessOrEqual, lambda(5), lambda(7)).evaluate());
        
        var greaterThan = lambda("greaterThan", n -> m -> $(not, $(lessOrEqual, n, m)));
        assertAsString("TRUE",  $(greaterThan, lambda(5), lambda(1)).evaluate());
        assertAsString("FALSE", $(greaterThan, lambda(5), lambda(5)).evaluate());
        assertAsString("FALSE", $(greaterThan, lambda(5), lambda(7)).evaluate());
        
        var equal = lambda("equal", n -> m -> $(and, $(lessOrEqual, n, m), $(lessOrEqual, m, n)));
        assertAsString("FALSE", $(equal, lambda(5), lambda(1)).evaluate());
        assertAsString("TRUE",  $(equal, lambda(5), lambda(5)).evaluate());
        assertAsString("FALSE", $(equal, lambda(5), lambda(7)).evaluate());
        
        var lessThan = lambda("lessThan", n -> m -> $(and, $(lessOrEqual, n, m), $(not, $(lessOrEqual, m, n))));
        assertAsString("FALSE", $(lessThan, lambda(5), lambda(1)).evaluate());
        assertAsString("FALSE", $(lessThan, lambda(5), lambda(5)).evaluate());
        assertAsString("TRUE",  $(lessThan, lambda(5), lambda(7)).evaluate());
        
        var greaterOrEqual = lambda("greaterOrEqual", n -> m -> $(or, $(greaterThan, n, m), $(equal, n, m)));
        assertAsString("TRUE",  $(greaterOrEqual, lambda(5), lambda(1)).evaluate());
        assertAsString("TRUE",  $(greaterOrEqual, lambda(5), lambda(5)).evaluate());
        assertAsString("FALSE", $(greaterOrEqual, lambda(5), lambda(7)).evaluate());
    }
    
}
//