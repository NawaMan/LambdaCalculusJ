package net.nawaman.lambdacalculusj;

public class LambdaWrapper implements Lambda {
    
    private final Lambda lambda;
    private final String toString;
    
    public LambdaWrapper(String toString, Lambda lambda) {
        this.lambda   = lambda;
        this.toString = toString;
    }
    
    @Override
    public Lambda apply(Lambda input) {
        return lambda.apply(input);
    }
    
    @Override
    public Integer intValue() {
        return lambda.intValue();
    }
    
    @Override
    public String toString() {
        return (toString == null) ? "lambda(*)" : toString;
    }
    
}