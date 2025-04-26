package net.nawaman.lambdacalculusj;

public class LazyEvaluatingLambdaException extends RuntimeException {
    
    private static final long serialVersionUID = -4163105489758063665L;
    
    public LazyEvaluatingLambdaException(Lambda lambda, Lambda input, Throwable cause) {
        super("StackOverflow while applying lambda=[" + lambda + "] with input=[" + input + "].", cause);
    }
    
    public LazyEvaluatingLambdaException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
