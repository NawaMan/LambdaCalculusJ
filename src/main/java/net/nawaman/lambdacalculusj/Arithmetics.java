package net.nawaman.lambdacalculusj;

import static java.lang.Math.max;
import static java.lang.Math.pow;
import static net.nawaman.lambdacalculusj.ArithmeticsHelper.checkInt;
import static net.nawaman.lambdacalculusj.ArithmeticsHelper.checkInts;
import static net.nawaman.lambdacalculusj.ArithmeticsHelper.mapInt;
import static net.nawaman.lambdacalculusj.ArithmeticsHelper.mapInts;
import static net.nawaman.lambdacalculusj.LambdaCalculus.intValue;
import static net.nawaman.lambdacalculusj.LambdaCalculus.lambda;

import java.util.function.BiPredicate;
import java.util.function.IntBinaryOperator;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;

class ArithmeticsHelper {
    
    static Integer mapInt(Lambda input, IntUnaryOperator action) {
        var intValue = intValue(input);
        return (intValue == null) ? null : action.applyAsInt(intValue);
    }
    
    static Integer mapInts(Lambda input1, Lambda input2, IntBinaryOperator action) {
        var intValue1 = intValue(input1);
        var intValue2 = intValue(input2);
        return ((intValue1 == null) || (intValue2 == null))
                ? null
                : action.applyAsInt(intValue1, intValue2);
    }
    
    static boolean checkInt(Lambda input, IntPredicate action) {
        var intValue = intValue(input);
        return (intValue == null) ? false : action.test(intValue);
    }
    
    static boolean checkInts(Lambda input1, Lambda input2, BiPredicate<Integer, Integer> action) {
        var intValue1 = intValue(input1);
        var intValue2 = intValue(input2);
        return ((intValue1 == null) || (intValue2 == null))
                ? false
                : action.test(intValue1, intValue2);
    }
    
}

/**
 * Practical arithmetic lambda.
 * 
 * These lambdas take use Java to do the work to speed up the computation.
 * `add` for example, is done using `+` instead of keep calling `successor`.
 */
public interface Arithmetics {
    
    public static final Lambda successor   = lambda("successor",   n -> lambda(mapInt(n, i ->        i + 1)));
    public static final Lambda predecessor = lambda("predecessor", n -> lambda(mapInt(n, i -> max(0, i - 1))));
    
    public static final Lambda add      = lambda("add",      n -> m -> lambda(mapInts(n, m, (i, j) -> i + j)));
    public static final Lambda subtract = lambda("subtract", n -> m -> lambda(mapInts(n, m, (i, j) -> max(0, i - j))));
    public static final Lambda multiply = lambda("multiply", n -> m -> lambda(mapInts(n, m, (i, j) -> i * j)));
    public static final Lambda divide   = lambda("divide",   n -> m -> lambda(mapInts(n, m, (i, j) -> i / j)));
    public static final Lambda moduro   = lambda("moduro",   n -> m -> lambda(mapInts(n, m, (i, j) -> i % j)));
    public static final Lambda power    = lambda("power",    n -> m -> lambda(mapInts(n, m, (i, j) -> (int)pow(i, j))));
    
    public static final Lambda isNumber       = lambda("isNumber",       n ->      lambda(checkInt(n, i -> true)));
    public static final Lambda isZero         = lambda("isZero",         n ->      lambda(checkInt(n, i -> i == 0)));
    public static final Lambda equal          = lambda("equal",          n -> m -> lambda(checkInts(n, m, (i, j) -> i == j)));
    public static final Lambda lessOrEqual    = lambda("lessOrEqual",    n -> m -> lambda(checkInts(n, m, (i, j) -> i <= j)));
    public static final Lambda greaterOrEqual = lambda("greaterOrEqual", n -> m -> lambda(checkInts(n, m, (i, j) -> i >= j)));
    public static final Lambda lessThan       = lambda("lessThan",       n -> m -> lambda(checkInts(n, m, (i, j) -> i <  j)));
    public static final Lambda greaterThan    = lambda("greaterThan",    n -> m -> lambda(checkInts(n, m, (i, j) -> i >  j)));
    
}
