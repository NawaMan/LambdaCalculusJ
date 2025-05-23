package net.nawaman.lambdacalculusj;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * This class contains useful methods to create and manipulate lambda.
 */
public class LambdaCalculus {
    
    private LambdaCalculus() {}
    
    //== Creating ==
    
    /**
     * Create a lambda. This can be used to create Lambda Calculus lambda from Java lambda expression.
     * 
     * @param  lambda  the input lambda.
     * @return         the lambda.
     */
    public static Lambda λ(Lambda lambda) {
        return lambda(lambda);
    }
    
    /**
     * Create a lambda. This is used to convert Java lambda expression to Lambda Calculus lambda.
     * 
     * @param  name    the name of the lambda.
     * @param  lambda  the input lambda.
     * @return         the lambda.
     */
    public static Lambda λ(String name, Lambda lambda) {
        return lambda(name, lambda);
    }
    
    /**
     * Create a lambda that has integer value.
     * 
     * @param  lambda    the input lambda.
     * @param  intValue  the supplier for the integer value.
     * @return           the lambda.
     */
    public static Lambda λ(Lambda lambda, Supplier<Integer> intValue) {
        return lambda(lambda, intValue);
    }
    
    /**
     * Create a lambda. This can be used to create Lambda Calculus lambda from Java lambda expression.
     * 
     * @param  lambda  the input lambda.
     * @return         the lambda.
     */
    public static Lambda lambda(Lambda lambda) {
        return new LambdaWrapper(null, lambda);
    }
    
    /**
     * Create a lambda. This is used to convert Java lambda expression to Lambda Calculus lambda.
     * 
     * @param  name    the name of the lambda.
     * @param  lambda  the input lambda.
     * @return         the lambda.
     */
    public static Lambda lambda(String name, Lambda lambda) {
        return new LambdaWrapper(name, lambda);
    }
    
    /**
     * Create a lambda that has integer value.
     * 
     * @param  lambda    the input lambda.
     * @param  intValue  the supplier for the integer value.
     * @return           the lambda.
     */
    public static Lambda lambda(Lambda lambda, Supplier<Integer> intValue) {
        return new WholeNumeberLambda(lambda, intValue);
    }
    
    //== Number ==
    
    /**
     * Create a lambda for the whole-number value.
     * 
     * @param  wholeNumberValue  the integer value.
     * @return           the lambda.
     */
    public static Lambda wholeNumber(int wholeNumberValue) {
        return new WholeNumeberLambda(wholeNumberValue);
    }
    
    //== Boolean ==
    
    private static final Lambda TRUE  = lambda("true",  x -> y -> x);
    private static final Lambda FALSE = lambda("false", x -> y -> y);
    
    /**
     * Create a lambda for the boolean value.
     * 
     * @param  booleanValue  the boolean value.
     * @return               the lambda.
     */
    public static Lambda lambda(boolean booleanValue) {
        return booleanValue ? TRUE : FALSE;
    }
    
    //== Applying ==
    
    /**
     * Create a lambda by applying the inputs to the given lambda eagerly.
     * 
     * @param  lambda  the lambda.
     * @param  inputs  the inputs.
     * @return         the result lambda.
     */
    public static Lambda $(Lambda lambda, Lambda ... inputs) {
        return lambda(lambda, inputs).evaluate();
    }
    
    /**
     * Create a lambda by applying the inputs to the given lambda lazily.
     * 
     * @param  lambda  the lambda.
     * @param  inputs  the inputs.
     * @return         the result lambda.
     */
    public static Lambda lazy(Lambda lambda, Lambda ... inputs) {
        if (!(lambda instanceof LambdaWrapper)
         && !(lambda instanceof LazyLambda)) {
            lambda = lambda(lambda);
        }
        
        return lambda(lambda, inputs);
    }
    
    /**
     * Create a lambda by applying the inputs to the given lambda lazily.
     * 
     * @param lambda  the lambda.
     * @param inputs  the inputs.
     * @return        the result lambda.
     */
    public static Lambda lambda(Lambda lambda, Lambda ... inputs) {
        if (inputs.length == 0) {
            if ((lambda instanceof LambdaWrapper)
             || (lambda instanceof LazyLambda)) {
                return lambda;
            }
            
            return new LambdaWrapper(null, lambda);
        }
        
        if (inputs.length == 1) {
            var input = inputs[0];
            return new LazyLambda(lambda, input);
        }
        
        var firstInput = inputs[0];
        var restInputs = new Lambda[inputs.length - 1];
        System.arraycopy(inputs, 1, restInputs, 0, restInputs.length);
        
        var newLambda = new LazyLambda(lambda, firstInput);
        return lazy(newLambda, restInputs);
    }
    
    //== Displaying ==
    
    /**
     * Create a string using the template and the values.
     * 
     * @param  template  the string template.
     * @param  values    the argument values.
     * @return           the result string.
     */
    public static String format(String template, Lambda ... values) {
        return String.format(template, Stream.of(values).map(LambdaCalculus::displayValue).toArray());
    }
    
    /**
     * Create a human friendly string representation of the lambda value.
     * 
     * @param  lambda  the lambda
     * @return         the string representation.
     */
    public static String displayValue(Lambda lambda) {
        var valueString = (String)null;
        try {
            valueString = lambda.evaluate().toString();
            if (valueString.matches("[a-zA-Z0-9$_.]+\\$\\$Lambda(\\$[0-9]+)?/0x[0-9a-fA-F]+@[0-9a-fA-F]+"))  {
                valueString = lambda.toString();
            }
            
        } catch (LazyEvaluatingLambdaException error) {
            valueString = lambda.toString() + "'";
        }
        return valueString;
    }
    
    /**
     * Returns the integer value representation of the given lambda or <code>null</code> if the lambda does not represent an integer.
     * 
     * @param  lambda  the input lambda.
     * @return         the integer representation or <code>null</code>.
     */
    public static Integer intValue(Lambda lambda) {
        var intValue = lambda.intValue();
        if (intValue != null) {
            return intValue;
        }
        
        var counter = new AtomicReference<Integer>(0);
        var input   = lambda("input", a -> a);
        var func    = lambda("func",  a -> {
            if (a != input) {
                counter.set(null);
                return a;
            }
            
            var value = counter.get();
            if (value instanceof Integer) {
                counter.set(value + 1);
            }
            
            return a;
        });
        
        var result = $(lambda, func, input);
        return (input == result) ? counter.get() : null;
    }
    
    /**
     * Print out the string value of the result of applying the lambda with the inputs.
     * 
     * @param  lambda  the lambda.
     * @param  inputs  the input values.
     * @return         the application result (evaluated).
     */
    public static Lambda show(Lambda lambda, Lambda ... inputs) {
        return show(null, lambda, inputs);
    }
    
    /**
     * Print out with a description prefix the string value of the result of applying the lambda with the inputs.
     * 
     * @param  description  the description.
     * @param  lambda       the lambda.
     * @param  inputs       the input values.
     * @return              the application result (evaluated).
     */
    public static Lambda show(String description, Lambda lambda, Lambda ... inputs) {
        var value = lazy(lambda, inputs);
        
        var valueString = displayValue(value);
        
        if (description == null) {
            println(valueString);
        } else {
            println(description + ": " + valueString);
        }
        return value;
    }
    
    //== MISC ==
    
    /**
     * A convenient method to print out a section title.
     * 
     * @param  section  the section name.
     */
    public static void section(String section) {
        println("== " + section + " ==================");
    }
    
    /**
     * A convenient method to print out a new line.
     */
    public static void println() {
        println("");
    }
    
    /**
     * A convenient method to print out the object.
     * 
     * @param  object  the given object.
     */
    public static void println(Object object) {
        System.out.println(object);
    }
    
}