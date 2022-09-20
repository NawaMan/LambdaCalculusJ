package net.nawaman.lambdacalculusj;

import static net.nawaman.lambdacalculusj.LambdaCalculus.$;
import static net.nawaman.lambdacalculusj.LambdaCalculus.lambda;

import java.util.function.Supplier;

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
