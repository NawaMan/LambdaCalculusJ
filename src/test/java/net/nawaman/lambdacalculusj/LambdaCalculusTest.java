package net.nawaman.lambdacalculusj;

import static net.nawaman.lambdacalculusj.LambdaCalculus.$;
import static net.nawaman.lambdacalculusj.LambdaCalculus.$$;
import static net.nawaman.lambdacalculusj.LambdaCalculus.displayValue;
import static net.nawaman.lambdacalculusj.LambdaCalculus.intValue;
import static net.nawaman.lambdacalculusj.LambdaCalculus.lambda;
import static net.nawaman.lambdacalculusj.LambdaCalculus.show;
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
    }
    
    @Test
    void testLambda_named() {
        var lambda = lambda("True", x -> y -> x);
        assertAsString("True", lambda);
        assertAsString("a",    lambda.apply(a).apply(b));
    }
    
    @Test
    void testInt() {
        assertAsString("42",    lambda(42));
        assertAsString("5(12)", lambda(5).apply(lambda(12)));
    }
    
    @Test
    void testNumeric() {
        var add = lambda(x -> y -> lambda(lambda(x.intValue() + y.intValue()), () -> x.intValue() + y.intValue()));
        var two = lambda(2);
        var ten = lambda(10);
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
        var theLambda = $$(lambda(true));
        assertAsString("true", theLambda);
        assertAsString("true", theLambda.evaluate());
    }
    
    @Test
    void testApply1() {
        var theLambda = $$(lambda(true), a);
        assertAsString("true(a)", theLambda);
        assertAsString("\\E.*\\Q$$Lambda$\\E[0-9]+\\Q/0x\\E[0-9a-f]+@[0-9a-f]+\\Q", theLambda.evaluate());
    }
    
    @Test
    void testApply2() {
        var theLambda = $$(lambda(true), a, b);
        assertAsString("true(a)(b)", theLambda);
        assertAsString("a",          theLambda.evaluate());
    }
    
    @Test
    void testApply3() {
        var theLambda = $$(lambda(true), a, b, b);
        assertAsString("true(a)(b)(b)", theLambda);
        assertAsString("b",             theLambda.evaluate());
    }
    
    @Test
    void testDisplayValue_basic() {
        assertAsString("true",    displayValue(lambda(true)));
    }
    
    @Test
    void testDisplayValue_incompleteEvaluate() {
        assertAsString("true(a)", displayValue($$(lambda(true), a)));
    }
    
    @Test
    void testDisplayValue_evaluate() {
        assertAsString("a", displayValue($$(lambda(true), a, b)));
    }
    
    @Test
    void testDisplayValue_infiniteRecursive() {
        var repeat = lambda("repeat", x -> $$(x, x));
        assertAsString("repeat(repeat)'", displayValue($$(repeat, repeat)));
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
