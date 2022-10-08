package net.nawaman.lambdacalculusj.examples;

import static net.nawaman.lambdacalculusj.LambdaCalculus.$$;
import static net.nawaman.lambdacalculusj.LambdaCalculus.$;
import static net.nawaman.lambdacalculusj.LambdaCalculus.lambda;
import static net.nawaman.lambdacalculusj.TestHelper.assertAsString;

import org.junit.jupiter.api.Test;

import net.nawaman.lambdacalculusj.Lambda;

public class BooleanExamples {
    
    private final Lambda a = lambda("a", x -> x);
    private final Lambda b = lambda("b", x -> x);
    
    private final Lambda TRUE  = lambda("TRUE",  x -> y -> x);
    private final Lambda FALSE = lambda("FALSE", x -> y -> y);
    
    @Test
    void testLiterals() {
        assertAsString("TRUE",  TRUE);
        assertAsString("FALSE", FALSE);
        
        assertAsString("TRUE(a)(b)", $$(TRUE,  a, b));
        assertAsString("FALSE(a)(b)", $$(FALSE, a, b));
        
        assertAsString("a", $(TRUE,  a, b));
        assertAsString("b", $(FALSE, a, b));
    }
    
    @Test
    void testNot() {
        var not = lambda("not", bool -> $$(bool, FALSE, TRUE));
        
        assertAsString("not(TRUE)",  $$(not, TRUE));
        assertAsString("not(FALSE)", $$(not, FALSE));
        
        assertAsString("FALSE", $(not, TRUE));
        assertAsString("TRUE",  $(not, FALSE));
    }
    
    @Test
    void testAnd() {
        var and = lambda("and", p -> q -> $$(p, q, p));
        
        assertAsString("and(TRUE)(TRUE)",   $$(and, TRUE,  TRUE));
        assertAsString("and(TRUE)(FALSE)",  $$(and, TRUE,  FALSE));
        assertAsString("and(FALSE)(TRUE)",  $$(and, FALSE, TRUE));
        assertAsString("and(FALSE)(FALSE)", $$(and, FALSE, FALSE));
        
        assertAsString("TRUE",  $(and, TRUE,  TRUE ));
        assertAsString("FALSE", $(and, TRUE,  FALSE));
        assertAsString("FALSE", $(and, FALSE, TRUE ));
        assertAsString("FALSE", $(and, FALSE, FALSE));
    }
    
    @Test
    void testOr() {
        var or = lambda("or", p -> q -> $$(p, p, q));
        
        assertAsString("or(TRUE)(TRUE)",   $$(or, TRUE,  TRUE));
        assertAsString("or(TRUE)(FALSE)",  $$(or, TRUE,  FALSE));
        assertAsString("or(FALSE)(TRUE)",  $$(or, FALSE, TRUE));
        assertAsString("or(FALSE)(FALSE)", $$(or, FALSE, FALSE));
        
        assertAsString("TRUE",  $(or, TRUE,  TRUE ));
        assertAsString("TRUE",  $(or, TRUE,  FALSE));
        assertAsString("TRUE",  $(or, FALSE, TRUE ));
        assertAsString("FALSE", $(or, FALSE, FALSE));
    }
    
    @Test
    void testEquals_straightforward() {
        var booleanEqual = lambda("booleanEqual", p -> q -> $$(p, $$(q, TRUE, FALSE), $$(q, FALSE, TRUE)));
        
        assertAsString("booleanEqual(TRUE)(TRUE)",   $$(booleanEqual, TRUE,  TRUE));
        assertAsString("booleanEqual(TRUE)(FALSE)",  $$(booleanEqual, TRUE,  FALSE));
        assertAsString("booleanEqual(FALSE)(TRUE)",  $$(booleanEqual, FALSE, TRUE));
        assertAsString("booleanEqual(FALSE)(FALSE)", $$(booleanEqual, FALSE, FALSE));
        
        assertAsString("TRUE",  $(booleanEqual, TRUE,  TRUE ));
        assertAsString("FALSE", $(booleanEqual, TRUE,  FALSE));
        assertAsString("FALSE", $(booleanEqual, FALSE, TRUE ));
        assertAsString("TRUE",  $(booleanEqual, FALSE, FALSE));
    }
    
    @Test
    void testEquals_short() {
        var not          = lambda("not",          bool -> $$(bool, FALSE, TRUE));
        var booleanEqual = lambda("booleanEqual", p -> q -> $$(p, q, $$(not, q)));
        
        assertAsString("booleanEqual(TRUE)(TRUE)",   $$(booleanEqual, TRUE,  TRUE));
        assertAsString("booleanEqual(TRUE)(FALSE)",  $$(booleanEqual, TRUE,  FALSE));
        assertAsString("booleanEqual(FALSE)(TRUE)",  $$(booleanEqual, FALSE, TRUE));
        assertAsString("booleanEqual(FALSE)(FALSE)", $$(booleanEqual, FALSE, FALSE));
        
        assertAsString("TRUE",  $(booleanEqual, TRUE,  TRUE ));
        assertAsString("FALSE", $(booleanEqual, TRUE,  FALSE));
        assertAsString("FALSE", $(booleanEqual, FALSE, TRUE ));
        assertAsString("TRUE",  $(booleanEqual, FALSE, FALSE));
    }
    
}
