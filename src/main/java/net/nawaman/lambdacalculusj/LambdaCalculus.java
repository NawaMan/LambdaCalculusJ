package net.nawaman.lambdacalculusj;

import java.util.function.Supplier;

public class LambdaCalculus {
    
    private LambdaCalculus() {}
    
    //== Creating ==
    
    public static Lambda lambda(Lambda lambda) {
        return new LambdaWrapper(null, lambda);
    }
    
    public static Lambda lambda(String name, Lambda lambda) {
        return new LambdaWrapper(name, lambda);
    }
    
    public static Lambda lambda(Lambda lambda, Supplier<Integer> intValue) {
        return new NumericLambda(lambda, intValue);
    }
    
    public static Lambda lambda(int intValue) {
        return new NumericLambda(intValue);
    }
    
    private static final Lambda TRUE  = lambda("true",  x -> y -> x);
    private static final Lambda FALSE = lambda("false", x -> y -> y);
    
    public static Lambda lambda(boolean booValue) {
        return booValue ? TRUE : FALSE;
    }
    
    //== Applying ==
    
    public static Lambda $(Lambda lambda, Lambda ... inputs) {
        var stringValue = lambda.toString();
        
        if (inputs.length == 0) {
            return lambda;
        }
        
        if (inputs.length == 1) {
            var input = inputs[0];
            return new LazyLambda(stringValue, lambda, input);
        }
        
        var firstInput = inputs[0];
        var restInputs = new Lambda[inputs.length - 1];
        System.arraycopy(inputs, 1, restInputs, 0, restInputs.length);
        
        var newLambda = new LazyLambda(stringValue, lambda, firstInput);
        return $(newLambda, restInputs);
    }
    
    //== Displaying ==
    
    public static String displayValue(Lambda value) {
        var valueString = (String)null;
        try {
            valueString = value.evaluate().toString();
            if (valueString.matches("[a-zA-Z0-9$_.]+\\$\\$Lambda\\$[0-9]+/0x[0-9a-fA-F]+@[0-9a-fA-F]+"))  {
                valueString = value.toString();
            }
            
        } catch (StackOverflowError error) {
            valueString = value.toString() + "'";
        }
        return valueString;
    }
    
    public static Lambda show(Lambda thing, Lambda ... things) {
        return show(null, thing, things);
    }
    public static Lambda show(String description, Lambda thing, Lambda ... things) {
        var value = $(thing, things);
        
        var valueString = displayValue(value);
        
        if (description == null) {
            println(valueString);
        } else {
            println(description + ": " + valueString);
        }
        return value;
    }
    
    //== MISC ==
    
    public static void section(String section) {
        println("== " + section + " ==================");
    }
    
    public static void println() {
        println("");
    }
    public static void println(Object object) {
        System.out.println(object);
    }
    
}