package net.nawaman.lambdacalculusj;

/**
 * This implementation of Lambda contains `toString` which is eaiser to inspect.
 */
public class LambdaWrapper implements Lambda {
    
    private final Lambda lambda;
    private final String toString;
    
    public LambdaWrapper(String toString, Lambda lambda) {
        this.lambda   = lambda;
        this.toString = toString;
    }
    
    @Override
    public Lambda apply(Lambda input) {
        try {
            return lambda.apply(input);
        } catch (StackOverflowError e) {
            throw new LazyEvaluatingLambdaException(lambda, input, e);
        }
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