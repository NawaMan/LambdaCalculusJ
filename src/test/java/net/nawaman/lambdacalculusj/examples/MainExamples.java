package net.nawaman.lambdacalculusj.examples;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static net.nawaman.lambdacalculusj.LambdaCalculus.$;
import static net.nawaman.lambdacalculusj.LambdaCalculus.format;
import static net.nawaman.lambdacalculusj.LambdaCalculus.lambda;
import static net.nawaman.lambdacalculusj.LambdaCalculus.lazy;
import static net.nawaman.lambdacalculusj.LambdaCalculus.section;
import static net.nawaman.lambdacalculusj.TestHelper.assertAsString;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import net.nawaman.lambdacalculusj.Lambda;

public class MainExamples {
    
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        section("Lambda basic ...");
        
        validate("Annonymouse lambda of lambda(a -> a)",
                lambda(a -> a),
                "lambda(*)");
        
        validate("Labeled lambda of lambda(\"MyLambda\", a -> a)",
                lambda("MyLambda", a -> a),
                "MyLambda");
        
        // Just a lambda to be used ... nothing interesting...
        var A = lambda("A", a -> a);
        var B = lambda("B", a -> a);
        
        
        
        section("Basic combinator ...");
        
        var identity = lambda("identity", a -> a);
        validate("`identity` of a thing returns that thing, try `A`",
                $(identity, A),
                "A");
        validate("Now, try `B`",
                $(identity, B),
                "B");
        
        
        
        section("Eager and Lazy application ...");
        
        validate("Try apply `identity` with `A` -- eagerly: ",
                $(identity, A),
                "A");
        validate("Try apply `identity` with `A` -- lazily: ",
                lazy(identity, A),
                "identity(A)");
        validate("For eager application, the Lambda's method `apply` can also be used: ",
                identity.apply(A),
                "A");
        
        
        
        section("Multiple parmeter lambda ...");
        
        var apply1with2 = lambda("apply", a -> b -> lazy(a, b));
        validate("`apply1with2` lambda apply the first parameter with the second parameter -- `apply1with2.apply(A).apply(B)`: ",
                apply1with2.apply(A).apply(B),
                "A(B)");
        var apply1Only = apply1with2.apply(A);
        validate("If only one is apply, we will see Java lambda: ",
                apply1Only,
                "" + MainExamples.class.getCanonicalName() + "$$Lambda$\\E[0-9]+/0x[0-9a-f]+@[0-9a-f]+\\Q");
        validate("But once all parameters are applied, we get the result: ",
                apply1Only.apply(B),
                "A(B)");
        
        
        
        section("Multiple application and precedence ...");
        
        var logs = new ArrayList<String>();
        var do1 = lambda("Do-1", a -> {
            logs.add("Do-1 of " + a);
            return a;
        });
        var do2 = lambda("Do-2", a -> {
            logs.add("Do-2 of " + a);
            return a;
        });
        var do3 = lambda("Do-3", a -> {
            logs.add("Do-3 of " + a);
            return a;
        });
        
        logs.clear();
        validate("Apply is left-precedence: `$(do1, do2, do3, A)`: ",
                $(do1, do2, do3, A), "A",
                logs, asList("Do-1 of Do-2",
                       "Do-2 of Do-3",
                       "Do-3 of A"));
        
        logs.clear();
        validate("Use parenthesis to force the precedence: `$(do1, $(do2, $(do3, A)))`: ",
                $(do1, $(do2, $(do3, A))), "A",
                logs, asList("Do-3 of A",
                       "Do-2 of A",
                       "Do-1 of A"));
        
        
        // Church's Encoding....
        
        section("Boolean IF, TRUE and FALSE ...");
        
        var IF = lambda("if", condition -> choiceTrue -> choiceFalse -> $(condition, choiceTrue, choiceFalse));
        
        var TRUE  = lambda("true",  a -> b -> a);
        var FALSE = lambda("false", a -> b -> b);
        validate("If true then A else B: ",
                $(IF, TRUE, A, B),
                "A");
        
        validate("If false then A else B: ",
                $(IF, FALSE, A, B),
                "B");
        
        
        section("Boolean NOT ...");
        
        var not = lambda("not", bool -> $(bool, FALSE, TRUE));
        validate("not(true): ",
                $(not, TRUE),
                "false");
        
        validate("not(false): ",
                $(not, FALSE),
                "true");
        
        
        section("Boolean AND ...");
        
        var and = lambda("and", p -> q -> $(p, q, p));
        validate("and(true, true): ",
                $(and, TRUE, TRUE),
                "true");
        
        validate("and(true, false): ",
                $(and, TRUE, FALSE),
                "false");
        
        validate("and(false, true): ",
                $(and, FALSE, TRUE),
                "false");
        
        validate("and(false, false): ",
                $(and, FALSE, FALSE),
                "false");
        
        
        section("Boolean OR ...");
        
        var or = lambda("or", p -> q -> $(p, p, q));
        validate("or(true, true): ",
                $(or, TRUE, TRUE),
                "true");
        
        validate("or(true, false): ",
                $(or, TRUE, FALSE),
                "true");
        
        validate("or(false, true): ",
                $(or, FALSE, TRUE),
                "true");
        
        validate("or(false, false): ",
                $(or, FALSE, FALSE),
                "false");
        
        
        section("Boolean Equals ...");
        
        var booleanEqual = lambda("booleanEqual", p -> q -> $(p, $(q, TRUE, FALSE), $(q, FALSE, TRUE)));
        validate("booleanEqual(true, true): ",
                $(booleanEqual, TRUE, TRUE),
                "true");
        
        validate("booleanEqual(true, false): ",
                $(booleanEqual, TRUE, FALSE),
                "false");
        
        validate("booleanEqual(false, true): ",
                $(booleanEqual, FALSE, TRUE),
                "false");
        
        validate("booleanEqual(false, false): ",
                $(booleanEqual, FALSE, FALSE),
                "true");
        
        
        section("Boolean Equals short ...");
        
        var booleanEqualShort = lambda("booleanEqual", p -> q -> $(p, q, $(not, q)));
        validate("booleanEqual(true, true): ",
                $(booleanEqualShort, TRUE, TRUE),
                "true");
        
        validate("booleanEqual(true, false): ",
                $(booleanEqualShort, TRUE, FALSE),
                "false");
        
        validate("booleanEqual(false, true): ",
                $(booleanEqualShort, FALSE, TRUE),
                "false");
        
        validate("booleanEqual(false, false): ",
                $(booleanEqualShort, FALSE, FALSE),
                "true");
        
        
        section("Numbers ...");
        
        var zero  = lambda("zero",  lambda(f -> a -> a));
        var one   = lambda("one",   lambda(f -> a -> $(f, a)));
        var two   = lambda("two",   lambda(f -> a -> $(f, $(f, a))));
        var three = lambda("three", lambda(f -> a -> $(f, $(f, $(f, a)))));
        
        var func = lambda("func", x -> {
            logs.add("do-func(" + x + ")");
            return x;
        });
        
        logs.clear();
        validate("zero(func, A): ",
                $(zero, func, A), "A",
                logs, asList());
        
        logs.clear();
        validate("one(func, A): ",
                $(one, func, A), "A",
                logs, asList("do-func(A)"));
        
        logs.clear();
        validate("two(func, A): ",
                $(two, func, A), "A",
                logs, asList("do-func(A)",
                             "do-func(A)"));
        
        logs.clear();
        validate("three(func, A): ",
                $(three, func, A), "A",
                logs, asList("do-func(A)",
                             "do-func(A)",
                             "do-func(A)"));
        
        
        section("isOdd ...");
        
        var isOdd = lambda("isOdd", n -> $(n, not, FALSE));
        
        validate("isOdd(zero): ",
                $(isOdd, zero),
                "false");
        
        validate("isOdd(one): ",
                $(isOdd, one),
                "true");
        
        validate("isOdd(two): ",
                $(isOdd, two),
                "false");
        
        validate("isOdd(three): ",
                $(isOdd, three),
                "true");
        
        
        section("Successor ...");
        
        var successor = lambda("successor", n -> lambda(f -> a -> $(f, $(n, f, a))));
        
        logs.clear();
        var ZERO = zero;
        validate("zero(func, A): ",
                $(zero, func, A), "A",
                logs, asList());
        
        logs.clear();
        var ONE = $(successor, zero);
        validate("successor(zero)(func, A): ",
                $(ONE, func, A), "A",
                logs, asList("do-func(A)"));
        
        logs.clear();
        var TWO = $(successor, $(successor, zero));
        validate("successor(successor(zero))(func, A): ",
                $(TWO, func, A), "A",
                logs, asList("do-func(A)",
                             "do-func(A)"));
        
        logs.clear();
        var THREE = $(successor, $(successor, $(successor, zero)));
        validate("successor(successor(successor(zero)))(func, A): ",
                $(THREE, func, A), "A",
                logs, asList("do-func(A)",
                             "do-func(A)",
                             "do-func(A)"));
        
        
        section("Wholenumber with shortcut ...");
        
        var counter = new AtomicInteger(0);
        var count   = lambda("count", x -> {
            counter.incrementAndGet();
            return x;
        });
        
        counter.set(0);
        $(ZERO, count, A);
        validate("ZERO: ", counter.get(), "0");
        
        counter.set(0);
        $(ONE, count, A);
        validate("successor(ZERO): ", counter.get(), "1");
        
        counter.set(0);
        $(TWO, count, A);
        validate("successor(successor(ZERO)): ", counter.get(), "2");
        
        counter.set(0);
        $(THREE, count, A);
        validate("successor(successor(successor(ZERO))): ", counter.get(), "3");
        
        var FOUR = $(successor, $(successor, $(successor, $(successor, zero))));
        counter.set(0);
        $(FOUR, count, A);
        validate("successor(successor(successor(successor(ZERO)))): ", counter.get(), "4");
        
        
        section("Add ...");
        var add = lambda("add", n -> m -> $($(n, successor), m));
        
        counter.set(0);
        $($(add, TWO, THREE), count, A);
        validate("add(2, 3): ", counter.get(), "5");
        
        counter.set(0);
        $($(add, FOUR, $(add, TWO, THREE)), count, A);
        validate("add(4, add(2, 3)): ", counter.get(), "9");
        
        
        section("Mul ...");
        var mul = lambda("mul", n -> m -> $(m, $(n, successor), ZERO));
        
        counter.set(0);
        $($(mul, TWO, THREE), count, A);
        validate("mul(2, 3): ", counter.get(), "6");
        
        counter.set(0);
        $($(mul, FOUR, $(mul, TWO, THREE)), count, A);
        validate("mul(4, mul(2, 3)): ", counter.get(), "24");
        
        
        section("Power ...");
        var pow = lambda("pow", n -> p -> $($(p, $(mul, n), one)));
        
        counter.set(0);
        $($(pow, TWO, THREE), count, A);
        validate("pow(2, 3): ", counter.get(), "8");
        
        counter.set(0);
        $($(pow, TWO, $(pow, TWO, THREE)), count, A);
        validate("pow(2, pow(2, 3)): ", counter.get(), "256");
        
        
        section("Predecessor ...");
        var newPair     = lambda("newPair",     a -> b -> lambda(format("Pair[%s,%s]", a, b), f -> $(f,a,b)));
        var firstOf     = lambda("firstOf",     p -> $(p, lambda(a -> b -> a)));
        var secondOf    = lambda("secondOf",    p -> $(p, lambda(a -> b -> b)));
        var transform   = lambda("transform",   p -> $(newPair,  $(secondOf, p), $(successor, $(secondOf, p))));
        var predecessor = lambda("predecessor", n -> $(firstOf, $($(n, transform), $(newPair, zero, zero))));
        
        counter.set(0);
        $($(predecessor, THREE), count, A);
        validate("predecessor(3): ", counter.get(), "2");
        
        counter.set(0);
        $($(predecessor, ONE), count, A);
        validate("predecessor(1): ", counter.get(), "0");
        
        counter.set(0);
        $($(predecessor, ZERO), count, A);
        validate("predecessor(0): ", counter.get(), "0");
        
        
        section("Subtract ...");
        var subtract = lambda("subtract", n -> k -> $($(k, predecessor), n));
        
        counter.set(0);
        $($(subtract, FOUR,  ONE), count, A);
        validate("subtract(4, 1): ", counter.get(), "3");
        
        counter.set(0);
        $($(subtract, THREE,  TWO), count, A);
        validate("subtract(3, 2): ", counter.get(), "1");
        
        counter.set(0);
        $($(subtract, ONE,  THREE), count, A);
        validate("subtract(1, 3): ", counter.get(), "0");
        
        
        section("isZero ...");
        
        var falseOrElse = $(lambda(a -> b -> a), FALSE);
        var isZero      = lambda("isZero", n -> $(n, falseOrElse, TRUE));
        
        validate("isZero(zero): ",
                $(isZero, zero),
                "true");
        
        validate("isZero(one): ",
                $(isZero, one),
                "false");
        
        validate("isZero(two): ",
                $(isZero, two),
                "false");
        
        
        section("lessOrEqual ...");
        
        var lessOrEqual = lambda("lessOrEqual", n -> m -> $(isZero, $(subtract, n, m)));
        
        validate("lessOrEqual(four, one): ",
                $(lessOrEqual, FOUR, ONE),
                "false");
        
        validate("lessOrEqual(four, four): ",
                $(lessOrEqual, FOUR, FOUR),
                "true");
        
        validate("lessOrEqual(one, four): ",
                $(lessOrEqual, ONE, FOUR),
                "true");
        
        
        section("greaterThan ...");
        
        var greaterThan = lambda("greaterThan", n -> m -> $(not, $(lessOrEqual, n, m)));
        
        validate("greaterThan(four, one): ",
                $(greaterThan, FOUR, ONE),
                "true");
        
        validate("greaterThan(four, four): ",
                $(greaterThan, FOUR, FOUR),
                "false");
        
        validate("greaterThan(one, four): ",
                $(greaterThan, ONE, FOUR),
                "false");
        
        
        section("equal ...");
        
        var equal = lambda("equal", n -> m -> $(and, $(lessOrEqual, n, m), $(lessOrEqual, m, n)));
        
        validate("equal(four, one): ",
                $(equal, FOUR, ONE),
                "false");
        
        validate("equal(four, four): ",
                $(equal, FOUR, FOUR),
                "true");
        
        validate("equal(one, four): ",
                $(equal, ONE, FOUR),
                "false");
        
        
        section("lessThan ...");
        
        var lessThan = lambda("lessThan", n -> m -> $(and, $(lessOrEqual, n, m), $(not, $(lessOrEqual, m, n))));
        
        validate("lessThan(four, one): ",
                $(lessThan, FOUR, ONE),
                "false");
        
        validate("lessThan(four, four): ",
                $(lessThan, FOUR, FOUR),
                "false");
        
        validate("lessThan(one, four): ",
                $(lessThan, ONE, FOUR),
                "true");
        
        
        section("greaterOrEqual ...");
        
        var greaterOrEqual = lambda("greaterOrEqual", n -> m -> $(or, $(greaterThan, n, m), $(equal, n, m)));
        
        validate("greaterOrEqual(four, one): ",
                $(greaterOrEqual, FOUR, ONE),
                "true");
        
        validate("greaterOrEqual(four, four): ",
                $(greaterOrEqual, FOUR, FOUR),
                "true");
        
        validate("greaterOrEqual(one, four): ",
                $(greaterOrEqual, ONE, FOUR),
                "false");
    }
    
    //== Internal utilities ==
    
    static void validate(String description, Lambda actual, String expect) {
        validate(description, actual, expect, null, null);
    }
    static void validate(String description, Lambda actual, String expect, List<String> logActual, List<String> logExpected) {
        assertAsString(expect, actual);
        System.out.println(description + "\n    = `" + actual + "`");
        if (logActual != null) {
            assertAsString(logExpected.stream().collect(joining("\n")),
                           logActual  .stream().collect(joining("\n")));
            System.out.println("    logs: \n" + logActual.stream().map("        "::concat).collect(joining("\n")));
        }
        System.out.println();
    }
    static void validate(String description, Object actual, String expect) {
        assertAsString(expect, actual);
        System.out.println(description + "\n    = `" + actual + "`");
        System.out.println();
    }
    
    static void pause() {
        System.out.println();
        System.out.println("\033[34;1mEnter to continue ...\033[0m");
        scanner.nextLine();
    }
    
}
