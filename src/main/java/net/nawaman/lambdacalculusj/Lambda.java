package net.nawaman.lambdacalculusj;

/**
 * This class represent a Lambda-Calculus lambda object.
 */
@FunctionalInterface
public interface Lambda {
    
    /**
     * Apply this lambda with the given input.
     * 
     * @param  input  the input.
     * @return        the result lambda.
     */
    Lambda apply(Lambda input);
    
    /**
     * Returns the number that this lambda represent or null if this lambda does not represent a number.
     * 
     * @return  the integer value or null.
     */
    default Integer intValue() {
        return null;
    }
    
    /**
     * Evaluate this lambda.
     * 
     * @return  the result.
     */
    default Lambda evaluate() {
        return this;
    }
    
}
