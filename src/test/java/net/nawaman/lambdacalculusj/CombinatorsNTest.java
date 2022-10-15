package net.nawaman.lambdacalculusj;

import static net.nawaman.lambdacalculusj.Combinators.N;
import static net.nawaman.lambdacalculusj.LambdaCalculus.$;
import static net.nawaman.lambdacalculusj.LambdaCalculus.lambda;
import static net.nawaman.lambdacalculusj.LambdaCalculus.wholeNumber;
import static net.nawaman.lambdacalculusj.TestHelper.assertAsString;
import static net.nawaman.lambdacalculusj.WholeNumbers.add;
import static net.nawaman.lambdacalculusj.WholeNumbers.power;

import org.junit.jupiter.api.Test;

class CombinatorsNTest {
    
    @Test
    void test() {
        // The result of the first become the value to the second which used it twice.
        var func = lambda(x -> $(N,
                $(add, x, wholeNumber(1)),
                x_1 -> $(add, $(add, $(power, x_1, wholeNumber(2)), x_1), wholeNumber(1))
        ));
        assertAsString("3",  $(func, wholeNumber(0)));
        assertAsString("7",  $(func, wholeNumber(1)));
        assertAsString("13", $(func, wholeNumber(2)));
        assertAsString("21", $(func, wholeNumber(3)));
    }
    
}
