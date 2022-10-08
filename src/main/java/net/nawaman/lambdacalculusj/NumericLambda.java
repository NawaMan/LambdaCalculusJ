package net.nawaman.lambdacalculusj;

import static net.nawaman.lambdacalculusj.LambdaCalculus.$;
import static net.nawaman.lambdacalculusj.LambdaCalculus.lambda;

import java.util.function.Supplier;

/**
 * This implementation of Lambda aims to represent an integer.
 */
public class NumericLambda extends LambdaWrapper {
    
    private final Supplier<Integer> intValue;
    
    public NumericLambda(int intValue) {
        this(f -> {
            return lambda(intValue + "(" + f + ")", a -> {
                var each = a;
                for (int i = 0; i < intValue; i++) {
                    each = $(f, each);
                }
                return each;
            });
        }, () -> intValue);
        
        if (intValue < 0) {
            throw new IllegalArgumentException("Number for the NumericLambda cannot be negative: " + intValue);
        }
    }
    
    public NumericLambda(Lambda lambda, Supplier<Integer> intValue) {
        super(null, lambda);
        this.intValue = intValue;
    }
    
    @Override
    public Integer intValue() {
        try {
            return intValue.get();
        } catch (NullPointerException e) {
            return null;
        }
    }
    @Override
    public String toString() {
        var intValue = intValue();
        return (intValue != null) ? ("" + intValue) : "NAN";
    }
    
}
