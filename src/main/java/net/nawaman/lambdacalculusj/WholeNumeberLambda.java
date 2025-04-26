package net.nawaman.lambdacalculusj;

import static net.nawaman.lambdacalculusj.LambdaCalculus.lazy;
import static net.nawaman.lambdacalculusj.LambdaCalculus.lambda;

import java.util.function.Supplier;

/**
 * This implementation of Lambda aims to represent a whole lambda.
 */
public class WholeNumeberLambda extends LambdaWrapper {
    
    private final Supplier<Integer> numberValue;
    
    public WholeNumeberLambda(int numberValue) {
        this(f -> {
            return lambda(numberValue + "(" + f + ")", a -> {
                var each = a;
                for (int i = 0; i < numberValue; i++) {
                    each = lazy(f, each);
                }
                return each;
            });
        }, () -> numberValue);
        
        if (numberValue < 0) {
            throw new IllegalArgumentException("Number for the WholeNumericLambda cannot be negative: " + numberValue);
        }
    }
    
    public WholeNumeberLambda(Lambda lambda, Supplier<Integer> numberValue) {
        super(null, lambda);
        this.numberValue = numberValue;
    }
    
    @Override
    public Integer intValue() {
        try {
            return numberValue.get();
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
