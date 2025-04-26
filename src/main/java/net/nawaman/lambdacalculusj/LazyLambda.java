package net.nawaman.lambdacalculusj;

import static java.lang.String.format;

import java.util.function.Supplier;

public class LazyLambda implements Lambda {
    
    private final Supplier<String> toString;
    private final Lambda lambda;
    private final Lambda input;
    
    public LazyLambda(Lambda lambda, Lambda input) {
        this(() -> lambda.toString(), lambda, input);
    }
    
    public LazyLambda(String toString, Lambda lambda, Lambda input) {
        this(() -> toString, lambda, input);
    }
    
    public LazyLambda(Supplier<String> toString, Lambda lambda, Lambda input) {
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
        try {
            return lambda
                    .evaluate()
                    .apply(input)
                    .evaluate();
        } catch (StackOverflowError e) {
            throw new LazyEvaluatingLambdaException(lambda, input, e);
        }
    }
    
    @Override
    public Integer intValue() {
        return evaluate()
                .intValue();
    }
    
    @Override
    public String toString() {
        return format("%s(%s)", toString.get(), input.toString());
    }
}