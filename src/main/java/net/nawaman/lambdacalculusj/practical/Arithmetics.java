package net.nawaman.lambdacalculusj.practical;

import static java.lang.Math.max;
import static java.lang.Math.pow;
import static java.util.Objects.nonNull;
import static net.nawaman.lambdacalculusj.LambdaCalculus.lambda;

import net.nawaman.lambdacalculusj.Lambda;

public interface Arithmetics {
    
    public static final Lambda successor   = lambda("successor",   n -> lambda(n.intValue() + 1));
    public static final Lambda predecessor = lambda("predecessor", n -> lambda(max(0, n.intValue() - 1)));
    
    public static final Lambda add      = lambda("add",       n -> k -> lambda(n.intValue() + k.intValue()));
    public static final Lambda subtract = lambda("subtract",  n -> k -> lambda(max(0, n.intValue() - k.intValue())));
    public static final Lambda multiply = lambda("multiply",  n -> k -> lambda(n.intValue() * k.intValue()));
    public static final Lambda divide   = lambda("divide",    n -> k -> lambda(n.intValue() / k.intValue()));
    public static final Lambda moduro   = lambda("moduro",    n -> k -> lambda(n.intValue() % k.intValue()));
    public static final Lambda power    = lambda("power",     n -> k -> lambda((int)pow(n.intValue(), k.intValue())));
    
    public static final Lambda isZero         = lambda("isZero",         n ->      lambda(n.intValue() == 0));
    public static final Lambda equal          = lambda("equal",          n -> k -> lambda(nonNull(n.intValue()) && (n.intValue() == k.intValue())));
    public static final Lambda lessOrEqual    = lambda("lessOrEqual",    n -> k -> lambda(n.intValue() <= k.intValue()));
    public static final Lambda greaterOrEqual = lambda("greaterOrEqual", n -> k -> lambda(n.intValue() >= k.intValue()));
    public static final Lambda lessThan       = lambda("lessThan",       n -> k -> lambda(n.intValue() <  k.intValue()));
    public static final Lambda greaterThan    = lambda("greaterThan",    n -> k -> lambda(n.intValue() >  k.intValue()));
}
