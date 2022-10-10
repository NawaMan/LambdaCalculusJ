package net.nawaman.lambdacalculusj.examples;

import static net.nawaman.lambdacalculusj.LambdaCalculus.$;
import static net.nawaman.lambdacalculusj.LambdaCalculus.lambda;
import static net.nawaman.lambdacalculusj.LambdaCalculus.wholeNumber;
import static net.nawaman.lambdacalculusj.TestHelper.assertAsString;

import org.junit.jupiter.api.Test;

import net.nawaman.lambdacalculusj.WholeNumbers;

class WholeNumbersTest implements WholeNumbers {
    
    @Test
    void testNumber() {
        assertAsString("0",    wholeNumber(0));
        assertAsString("1",    wholeNumber(1));
        assertAsString("42",   wholeNumber(42));
        assertAsString("4096", wholeNumber(4096));
        
        try {
            wholeNumber(-1);
        } catch (Exception e) {
            assertAsString("java.lang.IllegalArgumentException: Number for the WholeNumericLambda cannot be negative: -1", e);
        }
    }
    
    @Test
    void testSuccessor() {
        assertAsString("1",    $(successor, wholeNumber(0)));
        assertAsString("2",    $(successor, wholeNumber(1)));
        assertAsString("43",   $(successor, wholeNumber(42)));
        assertAsString("4097", $(successor, wholeNumber(4096)));
    }
    
    @Test
    void testPredecessor() {
        assertAsString("0",    $(predecessor, wholeNumber(0)));
        assertAsString("0",    $(predecessor, wholeNumber(1)));
        assertAsString("1",    $(predecessor, wholeNumber(2)));
        assertAsString("41",   $(predecessor, wholeNumber(42)));
        assertAsString("4095", $(predecessor, wholeNumber(4096)));
    }
    
    @Test
    void testAdd() {
        assertAsString("42",   $(add, wholeNumber(0),    wholeNumber(42)));
        assertAsString("43",   $(add, wholeNumber(1),    wholeNumber(42)));
        assertAsString("44",   $(add, wholeNumber(2),    wholeNumber(42)));
        assertAsString("84",   $(add, wholeNumber(42),   wholeNumber(42)));
        assertAsString("4138", $(add, wholeNumber(4096), wholeNumber(42)));
        assertAsString("8192", $(add, wholeNumber(4096), wholeNumber(4096)));
    }
    
    @Test
    void testSubtract() {
        assertAsString("0",    $(subtract, wholeNumber(0),    wholeNumber(42)));
        assertAsString("0",    $(subtract, wholeNumber(1),    wholeNumber(42)));
        assertAsString("0",    $(subtract, wholeNumber(2),    wholeNumber(42)));
        assertAsString("0",    $(subtract, wholeNumber(42),   wholeNumber(42)));
        assertAsString("42",   $(subtract, wholeNumber(84),   wholeNumber(42)));
        assertAsString("4054", $(subtract, wholeNumber(4096), wholeNumber(42)));
        assertAsString("0",    $(subtract, wholeNumber(4096), wholeNumber(4096)));
    }
    
    @Test
    void testMultiple() {
        assertAsString("0",        $(multiply, wholeNumber(0),    wholeNumber(42)));
        assertAsString("42",       $(multiply, wholeNumber(1),    wholeNumber(42)));
        assertAsString("84",       $(multiply, wholeNumber(2),    wholeNumber(42)));
        assertAsString("1764",     $(multiply, wholeNumber(42),   wholeNumber(42)));
        assertAsString("3528",     $(multiply, wholeNumber(84),   wholeNumber(42)));
        assertAsString("172032",   $(multiply, wholeNumber(4096), wholeNumber(42)));
        assertAsString("16777216", $(multiply, wholeNumber(4096), wholeNumber(4096)));
    }
    
    @Test
    void testDivide() {
        assertAsString("0",  $(divide, wholeNumber(0),    wholeNumber(42)));
        assertAsString("0",  $(divide, wholeNumber(1),    wholeNumber(42)));
        assertAsString("0",  $(divide, wholeNumber(2),    wholeNumber(42)));
        assertAsString("1",  $(divide, wholeNumber(42),   wholeNumber(42)));
        assertAsString("1",  $(divide, wholeNumber(58),   wholeNumber(42)));
        assertAsString("2",  $(divide, wholeNumber(84),   wholeNumber(42)));
        assertAsString("97", $(divide, wholeNumber(4096), wholeNumber(42)));
        assertAsString("1",  $(divide, wholeNumber(4096), wholeNumber(4096)));
        
        try {
            $(divide, wholeNumber(58), wholeNumber(0));
        } catch (Exception e) {
            assertAsString("java.lang.ArithmeticException: / by zero", e);
        }
    }
    
    @Test
    void testModuro() {
        assertAsString("0",  $(moduro, wholeNumber(0),    wholeNumber(42)));
        assertAsString("1",  $(moduro, wholeNumber(1),    wholeNumber(42)));
        assertAsString("2",  $(moduro, wholeNumber(2),    wholeNumber(42)));
        assertAsString("0",  $(moduro, wholeNumber(42),   wholeNumber(42)));
        assertAsString("16", $(moduro, wholeNumber(58),   wholeNumber(42)));
        assertAsString("0",  $(moduro, wholeNumber(84),   wholeNumber(42)));
        assertAsString("22", $(moduro, wholeNumber(4096), wholeNumber(42)));
        assertAsString("0",  $(moduro, wholeNumber(4096), wholeNumber(4096)));
        
        try {
            $(moduro, wholeNumber(58), wholeNumber(0));
        } catch (Exception e) {
            assertAsString("java.lang.ArithmeticException: / by zero", e);
        }
    }
    
    @Test
    void testPower() {
        assertAsString("0",          $(power, wholeNumber(0),    wholeNumber(3)));
        assertAsString("1",          $(power, wholeNumber(1),    wholeNumber(3)));
        assertAsString("8",          $(power, wholeNumber(2),    wholeNumber(3)));
        assertAsString("74088",      $(power, wholeNumber(42),   wholeNumber(3)));
        assertAsString("592704",     $(power, wholeNumber(84),   wholeNumber(3)));
        assertAsString("2147483647", $(power, wholeNumber(4096), wholeNumber(3)));
    }
    
    @Test
    void testIsZero() {
        assertAsString("true",  $(isZero, wholeNumber(0)));
        assertAsString("false", $(isZero, wholeNumber(1)));
        
        assertAsString("true",  $(isZero, lambda(f -> a -> a)));
        assertAsString("false", $(isZero, lambda(f -> a -> $(f, a))));
    }
    
    @Test
    void testNumberComparison() {
        assertAsString("false", $(equal, wholeNumber(5), wholeNumber(1)));
        assertAsString("true",  $(equal, wholeNumber(5), wholeNumber(5)));
        assertAsString("false", $(equal, wholeNumber(5), wholeNumber(7)));
        
        assertAsString("false", $(lessOrEqual, wholeNumber(5), wholeNumber(1)));
        assertAsString("true",  $(lessOrEqual, wholeNumber(5), wholeNumber(5)));
        assertAsString("true",  $(lessOrEqual, wholeNumber(5), wholeNumber(7)));
        
        assertAsString("true",  $(greaterOrEqual, wholeNumber(5), wholeNumber(1)));
        assertAsString("true",  $(greaterOrEqual, wholeNumber(5), wholeNumber(5)));
        assertAsString("false", $(greaterOrEqual, wholeNumber(5), wholeNumber(7)));
        
        assertAsString("false", $(lessThan, wholeNumber(5), wholeNumber(1)));
        assertAsString("false", $(lessThan, wholeNumber(5), wholeNumber(5)));
        assertAsString("true",  $(lessThan, wholeNumber(5), wholeNumber(7)));
        
        assertAsString("true",  $(greaterThan, wholeNumber(5), wholeNumber(1)));
        assertAsString("false", $(greaterThan, wholeNumber(5), wholeNumber(5)));
        assertAsString("false", $(greaterThan, wholeNumber(5), wholeNumber(7)));
    }
    
    @Test
    void testNumberComparison_encoding() {
        assertAsString("true", $(equal, wholeNumber(1), lambda(f -> a -> $(f, a))));
    }
    
    @Test
    void testNumberComparison_notNumber() {
        assertAsString("false", $(equal, wholeNumber(1), lambda(f -> a -> lambda(true))));
    }
    @Test
    void testIsNumber() {
        assertAsString("true",  $(isNumber, wholeNumber(1)));
        assertAsString("false", $(isNumber, lambda(f -> a -> lambda(true))));
    }
    
}
