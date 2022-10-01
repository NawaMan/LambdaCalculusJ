package net.nawaman.lambdacalculusj;

import static java.lang.String.format;

public class LazyLambda implements Lambda {
    
    private final String toString;
    private final Lambda lambda;
    private final Lambda input;
    
    public LazyLambda(String toString, Lambda lambda, Lambda input) {
        this.toString = toString;
        this.lambda   = lambda;
        this.input    = input;
    }
    
    @Override
    public Lambda apply(Lambda subInput) {
        return evaluate()
                .apply(subInput);
    }
    
    @Override
    public Lambda evaluate() {
        return lambda
                .evaluate()
                .apply(input)
                .evaluate();
    }
    
    @Override
    public Integer intValue() {
        return evaluate()
                .intValue();
    }
    
    @Override
    public String toString() {
        return format("%s(%s)", toString , input.toString());
    }
}