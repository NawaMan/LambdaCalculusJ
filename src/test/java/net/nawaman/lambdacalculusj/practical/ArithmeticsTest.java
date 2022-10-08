package net.nawaman.lambdacalculusj.practical;

import static net.nawaman.lambdacalculusj.LambdaCalculus.$$;
import static net.nawaman.lambdacalculusj.LambdaCalculus.lambda;
import static net.nawaman.lambdacalculusj.TestHelper.assertAsString;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static net.nawaman.lambdacalculusj.LambdaCalculus.*;

class ArithmeticsTest implements Arithmetics {
    
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
    
}
