package net.nawaman.lambdacalculusj.examples;

import static net.nawaman.lambdacalculusj.LambdaCalculus.$;
import static net.nawaman.lambdacalculusj.LambdaCalculus.lambda;
import static net.nawaman.lambdacalculusj.TestHelper.assertAsString;

import org.junit.jupiter.api.Test;

import net.nawaman.lambdacalculusj.Arithmetics;

class ArithmeticsTest implements Arithmetics {
    
    @Test
    void testNumber() {
        assertAsString("0",    lambda(0));
        assertAsString("1",    lambda(1));
        assertAsString("42",   lambda(42));
        assertAsString("4096", lambda(4096));
        
        try {
            lambda(-1);
        } catch (Exception e) {
            assertAsString("java.lang.IllegalArgumentException: Number for the NumericLambda cannot be negative: -1", e);
        }
    }
    
    @Test
    void testSuccessor() {
        assertAsString("1",    $(successor, lambda(0)));
        assertAsString("2",    $(successor, lambda(1)));
        assertAsString("43",   $(successor, lambda(42)));
        assertAsString("4097", $(successor, lambda(4096)));
    }
    
    @Test
    void testPredecessor() {
        assertAsString("0",    $(predecessor, lambda(0)));
        assertAsString("0",    $(predecessor, lambda(1)));
        assertAsString("1",    $(predecessor, lambda(2)));
        assertAsString("41",   $(predecessor, lambda(42)));
        assertAsString("4095", $(predecessor, lambda(4096)));
    }
    
    @Test
    void testAdd() {
        assertAsString("42",   $(add, lambda(0),    lambda(42)));
        assertAsString("43",   $(add, lambda(1),    lambda(42)));
        assertAsString("44",   $(add, lambda(2),    lambda(42)));
        assertAsString("84",   $(add, lambda(42),   lambda(42)));
        assertAsString("4138", $(add, lambda(4096), lambda(42)));
        assertAsString("8192", $(add, lambda(4096), lambda(4096)));
    }
    
    @Test
    void testSubtract() {
        assertAsString("0",    $(subtract, lambda(0),    lambda(42)));
        assertAsString("0",    $(subtract, lambda(1),    lambda(42)));
        assertAsString("0",    $(subtract, lambda(2),    lambda(42)));
        assertAsString("0",    $(subtract, lambda(42),   lambda(42)));
        assertAsString("42",   $(subtract, lambda(84),   lambda(42)));
        assertAsString("4054", $(subtract, lambda(4096), lambda(42)));
        assertAsString("0",    $(subtract, lambda(4096), lambda(4096)));
    }
    
    @Test
    void testMultiple() {
        assertAsString("0",        $(multiply, lambda(0),    lambda(42)));
        assertAsString("42",       $(multiply, lambda(1),    lambda(42)));
        assertAsString("84",       $(multiply, lambda(2),    lambda(42)));
        assertAsString("1764",     $(multiply, lambda(42),   lambda(42)));
        assertAsString("3528",     $(multiply, lambda(84),   lambda(42)));
        assertAsString("172032",   $(multiply, lambda(4096), lambda(42)));
        assertAsString("16777216", $(multiply, lambda(4096), lambda(4096)));
    }
    
    @Test
    void testDivide() {
        assertAsString("0",  $(divide, lambda(0),    lambda(42)));
        assertAsString("0",  $(divide, lambda(1),    lambda(42)));
        assertAsString("0",  $(divide, lambda(2),    lambda(42)));
        assertAsString("1",  $(divide, lambda(42),   lambda(42)));
        assertAsString("1",  $(divide, lambda(58),   lambda(42)));
        assertAsString("2",  $(divide, lambda(84),   lambda(42)));
        assertAsString("97", $(divide, lambda(4096), lambda(42)));
        assertAsString("1",  $(divide, lambda(4096), lambda(4096)));
        
        try {
            $(divide, lambda(58), lambda(0));
        } catch (Exception e) {
            assertAsString("java.lang.ArithmeticException: / by zero", e);
        }
    }
    
    @Test
    void testModuro() {
        assertAsString("0",  $(moduro, lambda(0),    lambda(42)));
        assertAsString("1",  $(moduro, lambda(1),    lambda(42)));
        assertAsString("2",  $(moduro, lambda(2),    lambda(42)));
        assertAsString("0",  $(moduro, lambda(42),   lambda(42)));
        assertAsString("16", $(moduro, lambda(58),   lambda(42)));
        assertAsString("0",  $(moduro, lambda(84),   lambda(42)));
        assertAsString("22", $(moduro, lambda(4096), lambda(42)));
        assertAsString("0",  $(moduro, lambda(4096), lambda(4096)));
        
        try {
            $(moduro, lambda(58), lambda(0));
        } catch (Exception e) {
            assertAsString("java.lang.ArithmeticException: / by zero", e);
        }
    }
    
    @Test
    void testPower() {
        assertAsString("0",          $(power, lambda(0),    lambda(3)));
        assertAsString("1",          $(power, lambda(1),    lambda(3)));
        assertAsString("8",          $(power, lambda(2),    lambda(3)));
        assertAsString("74088",      $(power, lambda(42),   lambda(3)));
        assertAsString("592704",     $(power, lambda(84),   lambda(3)));
        assertAsString("2147483647", $(power, lambda(4096), lambda(3)));
    }
    
    @Test
    void testNumberComparison() {
        assertAsString("false", $(equal, lambda(5), lambda(1)));
        assertAsString("true",  $(equal, lambda(5), lambda(5)));
        assertAsString("false", $(equal, lambda(5), lambda(7)));
        
        assertAsString("false", $(lessOrEqual, lambda(5), lambda(1)));
        assertAsString("true",  $(lessOrEqual, lambda(5), lambda(5)));
        assertAsString("true",  $(lessOrEqual, lambda(5), lambda(7)));
        
        assertAsString("true",  $(greaterOrEqual, lambda(5), lambda(1)));
        assertAsString("true",  $(greaterOrEqual, lambda(5), lambda(5)));
        assertAsString("false", $(greaterOrEqual, lambda(5), lambda(7)));
        
        assertAsString("false", $(lessThan, lambda(5), lambda(1)));
        assertAsString("false", $(lessThan, lambda(5), lambda(5)));
        assertAsString("true",  $(lessThan, lambda(5), lambda(7)));
        
        assertAsString("true",  $(greaterThan, lambda(5), lambda(1)));
        assertAsString("false", $(greaterThan, lambda(5), lambda(5)));
        assertAsString("false", $(greaterThan, lambda(5), lambda(7)));
    }
    
}
