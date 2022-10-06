package net.nawaman.lambdacalculusj.examples;

import static net.nawaman.lambdacalculusj.LambdaCalculus.$;
import static net.nawaman.lambdacalculusj.LambdaCalculus.format;
import static net.nawaman.lambdacalculusj.LambdaCalculus.lambda;
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
        
        var value = $(makeValue, a);
        assertAsString("value(a)",        value);
        assertAsString("read(value(a))", $(readValue, value));
        assertAsString("a",              $(readValue, value).evaluate());
    }
    
    @Test
    void testValue_map() {
        var TRUE  = lambda("TRUE",  x -> y -> x);
        var FALSE = lambda("FALSE", x -> y -> y);
        var not   = lambda("not", bool -> $(bool, FALSE, TRUE));
        
        var makeValue = lambda("value", x     -> lambda(format("Value[%s]", x), f -> $(f, x)));
        var readValue = lambda("read",  value -> $(value, x -> x));
        var mapValue  = lambda("map",   value -> f -> $(makeValue, $(f, $(readValue, value))));
        
        var value = $(makeValue, TRUE);
        assertAsString("value(TRUE)",  value);
        assertAsString("Value[FALSE]", $(mapValue, value, not).evaluate());
    }
    
    @Test
    void testPair() {
        var makePair = lambda("pair",     a -> b -> lambda(format("Pair[%s,%s]", a, b), f -> $(f,a,b)));
        var firstOf  = lambda("firstOf",  p -> $(p, lambda(a -> b -> a)));
        var secondOf = lambda("secondOf", p -> $(p, lambda(a -> b -> b)));
        
        var pair = $(makePair, a, b);
        assertAsString("pair(a)(b)", pair);
        assertAsString("Pair[a,b]",  pair.evaluate());
        assertAsString("a",          $(firstOf,  pair).evaluate());
        assertAsString("b",          $(secondOf, pair).evaluate());
        
        var setFirst  = lambda("setFirst",  p -> x -> $(makePair, x, $(secondOf, p)));
        var setSecond = lambda("setSecond", p -> x -> $(makePair, $(firstOf, p), x));
        
        assertAsString("Pair[b,b]", $(setFirst,  pair, b).evaluate());
        assertAsString("Pair[a,a]", $(setSecond, pair, a).evaluate());
    }
    
    @Test
    void testList() {
        var TRUE     = lambda("TRUE",  x -> y -> x);
        var FALSE    = lambda("FALSE", x -> y -> y);
        var makePair = lambda("pair",  a -> b -> lambda(format("Pair[%s,%s]", a, b), f -> $(f,a,b)));
        var NIL      = lambda("NIL",   x -> TRUE);
        
        assertAsString("NIL", NIL.evaluate());
        
        var makeList = lambda("makeList", value -> $(makePair, value, NIL));
        var list1    = $(makeList, lambda(1));
        assertAsString("Pair[1,NIL]", list1.evaluate());
        
        var concat = lambda("concat",   value -> list -> $(makePair, value, list));
        var list2  = $(concat, lambda(2), list1);
        assertAsString("Pair[2,Pair[1,NIL]]", list2.evaluate());
        
        var list3  = $(concat, lambda(5), list2);
        assertAsString("Pair[5,Pair[2,Pair[1,NIL]]]", list3.evaluate());
        
        var headOf = lambda("headOf",   list -> $(list, lambda(a -> b -> a)));
        var tailOf = lambda("tailOf",   list -> $(list, lambda(a -> b -> b)));
        assertAsString("5",                   $(headOf, list3).evaluate());
        assertAsString("Pair[2,Pair[1,NIL]]", $(tailOf, list3).evaluate());
        
        assertAsString("2",           $(headOf, list2).evaluate());
        assertAsString("Pair[1,NIL]", $(tailOf, list2).evaluate());
        
        assertAsString("1",   $(headOf, list1).evaluate());
        assertAsString("NIL", $(tailOf, list1).evaluate());
        
        var isEmpty  = lambda("isEmpty",  list -> $(list, x -> y -> FALSE));
        assertAsString("TRUE",  $(isEmpty, NIL).evaluate());
        assertAsString("FALSE", $(isEmpty, list1).evaluate());
        assertAsString("FALSE", $(isEmpty, list2).evaluate());
        assertAsString("FALSE", $(isEmpty, list3).evaluate());
        
    }
    
}
