package net.nawaman.lambdacalculusj.examples;

import static net.nawaman.lambdacalculusj.LambdaCalculus.lazy;
import static net.nawaman.lambdacalculusj.LambdaCalculus.$;
import static net.nawaman.lambdacalculusj.LambdaCalculus.format;
import static net.nawaman.lambdacalculusj.LambdaCalculus.lambda;
import static net.nawaman.lambdacalculusj.LambdaCalculus.wholeNumber;
import static net.nawaman.lambdacalculusj.TestHelper.assertAsString;

import org.junit.jupiter.api.Test;

import net.nawaman.lambdacalculusj.Lambda;

class DataStructureExamples {
    
    private final Lambda a = lambda("a", x -> x);
    private final Lambda b = lambda("b", x -> x);
    
    @Test
    void testValue() {
        var makeValue = lambda("value", x     -> lambda(format("Value[%s]", x), f -> $(f, x)));
        var readValue = lambda("read",  value -> $(value, x -> x));
        
        var value = lazy(makeValue, a);
        assertAsString("value(a)",        value);
        assertAsString("read(value(a))", lazy(readValue, value));
        assertAsString("a",              $(readValue, value));
    }
    
    @Test
    void testValue_map() {
        var TRUE  = lambda("TRUE",  x -> y -> x);
        var FALSE = lambda("FALSE", x -> y -> y);
        var not   = lambda("not", bool -> $(bool, FALSE, TRUE));
        
        var makeValue = lambda("value", x     -> lambda(format("Value[%s]", x), f -> $(f, x)));
        var readValue = lambda("read",  value -> $(value, x -> x));
        var mapValue  = lambda("map",   value -> f -> $(makeValue, $(f, $(readValue, value))));
        
        var value = lazy(makeValue, TRUE);
        assertAsString("value(TRUE)",  value);
        assertAsString("Value[FALSE]", $(mapValue, value, not));
    }
    
    @Test
    void testPair() {
        var newPair  = lambda("newPair",     a -> b -> lambda(format("Pair[%s,%s]", a, b), f -> $(f,a,b)));
        var firstOf  = lambda("firstOf",  p -> $(p, lambda(a -> b -> a)));
        var secondOf = lambda("secondOf", p -> $(p, lambda(a -> b -> b)));
        
        var pair = lazy(newPair, a, b);
        assertAsString("newPair(a)(b)", pair);
        assertAsString("Pair[a,b]",     pair.evaluate());
        assertAsString("a",             $(firstOf,  pair));
        assertAsString("b",             $(secondOf, pair));
        
        var setFirst  = lambda("setFirst",  p -> x -> $(newPair, x, $(secondOf, p)));
        var setSecond = lambda("setSecond", p -> x -> $(newPair, $(firstOf, p), x));
        
        assertAsString("Pair[b,b]", $(setFirst,  pair, b));
        assertAsString("Pair[a,a]", $(setSecond, pair, a));
    }
    
    @Test
    void testList() {
        var TRUE    = lambda("TRUE",    x -> y -> x);
        var FALSE   = lambda("FALSE",   x -> y -> y);
        var newPair = lambda("newPair", a -> b -> lambda(format("Pair[%s,%s]", a, b), f -> $(f,a,b)));
        var NIL     = lambda("NIL",     x -> TRUE);
        
        assertAsString("NIL", NIL.evaluate());
        
        var newList = lambda("newList", element -> $(newPair, element, NIL));
        var list1   = $(newList, wholeNumber(1));
        assertAsString("Pair[1,NIL]", list1.evaluate());
        
        var concat = lambda("concat",   value -> list -> $(newPair, value, list));
        var list2  = $(concat, wholeNumber(2), list1);
        assertAsString("Pair[2,Pair[1,NIL]]", list2.evaluate());
        
        var list3  = $(concat, wholeNumber(5), list2);
        assertAsString("Pair[5,Pair[2,Pair[1,NIL]]]", list3.evaluate());
        
        var headOf = lambda("headOf",   list -> $(list, lambda(a -> b -> a)));
        var tailOf = lambda("tailOf",   list -> $(list, lambda(a -> b -> b)));
        assertAsString("5",                   $(headOf, list3));
        assertAsString("Pair[2,Pair[1,NIL]]", $(tailOf, list3));
        
        assertAsString("2",           $(headOf, list2));
        assertAsString("Pair[1,NIL]", $(tailOf, list2));
        
        assertAsString("1",   $(headOf, list1));
        assertAsString("NIL", $(tailOf, list1));
        
        var isEmpty  = lambda("isEmpty",  list -> $(list, x -> y -> FALSE));
        assertAsString("TRUE",  $(isEmpty, NIL));
        assertAsString("FALSE", $(isEmpty, list1));
        assertAsString("FALSE", $(isEmpty, list2));
        assertAsString("FALSE", $(isEmpty, list3));
        
    }
    
}
