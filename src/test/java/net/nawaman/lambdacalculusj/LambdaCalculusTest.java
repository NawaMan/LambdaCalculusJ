package net.nawaman.lambdacalculusj;

import static net.nawaman.lambdacalculusj.LambdaCalculus.$;
import static net.nawaman.lambdacalculusj.LambdaCalculus.lazy;
import static net.nawaman.lambdacalculusj.LambdaCalculus.displayValue;
import static net.nawaman.lambdacalculusj.LambdaCalculus.intValue;
import static net.nawaman.lambdacalculusj.LambdaCalculus.lambda;
import static net.nawaman.lambdacalculusj.LambdaCalculus.show;
import static net.nawaman.lambdacalculusj.LambdaCalculus.wholeNumber;
import static net.nawaman.lambdacalculusj.LambdaCalculus.println;
import static net.nawaman.lambdacalculusj.LambdaCalculus.section;
import static net.nawaman.lambdacalculusj.TestHelper.assertAsString;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

class LambdaCalculusTest {
    Lambda a = new LambdaWrapper("a", x -> x);
    Lambda b = new LambdaWrapper("b", x -> x);
    
    @Test
    void testLambda_noName() {
        var lambda = lambda(x -> y -> x);
        assertAsString("lambda(*)", lambda);
        assertAsString("a",         lambda.apply(a).apply(b));
        
        // Force no parameters
        var lambda2 = lambda(x -> y -> x, new Lambda[0]);
        assertAsString("lambda(*)", lambda2);
        assertAsString("a",         lambda2.apply(a).apply(b));
        
        // Lazy with force no parameters
        var lambda3 = lambda(lazy(a -> a, lambda(b -> b)), new Lambda[0]);
        assertAsString("lambda(*)(lambda(*))", lambda3);
        assertAsString("a",                    lambda3.apply(a));
    }
    
    @Test
    void testLambda_named() {
        var lambda = lambda("True", x -> y -> x);
        assertAsString("True", lambda);
        assertAsString("a",    lambda.apply(a).apply(b));
    }
    
    @Test
    void testInt() {
        assertAsString("42",    wholeNumber(42));
        assertAsString("5(12)", wholeNumber(5).apply(wholeNumber(12)));
    }
    
    @Test
    void testNumeric() {
        var add = lambda(x -> y -> lambda(wholeNumber(x.intValue() + y.intValue()), () -> x.intValue() + y.intValue()));
        var two = wholeNumber(2);
        var ten = wholeNumber(10);
        assertAsString("12", add.apply(two).apply(ten));
    }
    
    @Test
    void testBoolean() {
        var a     = new LambdaWrapper("a", x -> x);
        var b     = new LambdaWrapper("b", x -> x);
        var True  = lambda(true);
        var False = lambda(false);
        assertAsString("a", True.apply(a).apply(b));
        assertAsString("b", False.apply(a).apply(b));
    }
    
    @Test
    void testApply0() {
        var theLambda = lazy(lambda(true));
        assertAsString("true", theLambda);
        assertAsString("true", theLambda.evaluate());
    }
    
    @Test
    void testApply1() {
        var theLambda = lazy(lambda(true), a);
        assertAsString("true(a)", theLambda);
    }
    
    @Test
    void testApply2() {
        var theLambda = lazy(lambda(true), a, b);
        assertAsString("true(a)(b)", theLambda);
        assertAsString("a",          theLambda.evaluate());
    }
    
    @Test
    void testApply3() {
        var theLambda = lazy(lambda(true), a, b, b);
        assertAsString("true(a)(b)(b)", theLambda);
        assertAsString("b",             theLambda.evaluate());
    }
    
    @Test
    void testDisplayValue_basic() {
        assertAsString("true",    displayValue(lambda(true)));
    }
    
    @Test
    void testDisplayValue_incompleteEvaluate() {
        assertAsString("true(a)", displayValue(lazy(lambda(true), a)));
    }
    
    @Test
    void testDisplayValue_evaluate() {
        assertAsString("a", displayValue(lazy(lambda(true), a, b)));
    }
    
    @Test
    void testDisplayValue_infiniteRecursive() {
        var repeat = lambda("repeat", x -> lazy(x, x));
        assertAsString("repeat(repeat)'", displayValue(lazy(repeat, repeat)));
    }
    
    @Test
    void testNumeric_intValue() {
        var zero = lambda(f -> a -> a);
        var two  = lambda(f -> a -> $(f, $(f, a)));
        var five = lambda(f -> a -> $(f, $(f, $(f, $(f, $(f, a))))));
        assertAsString("0", intValue(zero));
        assertAsString("2", intValue(two));
        assertAsString("5", intValue(five));
        
        var notNumber = lambda(f -> a -> f);
        assertAsString("null", intValue(notNumber));
        
        var notNumber2 = lambda(f -> a -> $(f, lambda(true)));
        assertAsString("null", intValue(notNumber2));
    }
    
    @Test
    void testShow() throws IOException {
        var stdOut = captureStdOut(() -> {
            show(lambda(true), a, b);
        });
        assertAsString("a\n", stdOut);
    }
    
    @Test
    void testShow_withDescription() throws IOException {
        var stdOut = captureStdOut(() -> {
            show("(true, a, b)", lambda(true), a, b);
        });
        assertAsString("(true, a, b): a\n", stdOut);
    }
    
    @Test
    void testSection() throws IOException {
        var stdOut = captureStdOut(() -> {
            section("HEAD-1");
        });
        assertAsString("== HEAD-1 ==================\n", stdOut);
    }
    
    @Test
    void testPrintln_empty() throws IOException {
        var stdOut = captureStdOut(() -> {
            println();
        });
        assertAsString("\n", stdOut);
    }
    
    @Test
    void testPrintln() throws IOException {
        var stdOut = captureStdOut(() -> {
            println("Hello");
        });
        assertAsString("Hello\n", stdOut);
    }
    
    String captureStdOut(Runnable runnable) throws IOException {
        var stdOut = System.out;
        try (var buffer = new ByteArrayOutputStream()) {
            System.setOut(new PrintStream(buffer));
            runnable.run();
            return buffer.toString();
        } finally {
            System.setOut(stdOut);
        }
    }
    
}
