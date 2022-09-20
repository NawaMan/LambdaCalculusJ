package net.nawaman.lambdacalculusj;

@FunctionalInterface
public interface Lambda {
    
    Lambda apply(Lambda input);
    
    default Integer intValue() {
        return null;
    }
    
    default Lambda evaluate() {
        return this;
    }
    
}
