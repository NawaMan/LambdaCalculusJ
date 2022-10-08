package net.nawaman.lambdacalculusj.examples;

import static net.nawaman.lambdacalculusj.LambdaCalculus.$$;
import static net.nawaman.lambdacalculusj.LambdaCalculus.$;
import static net.nawaman.lambdacalculusj.LambdaCalculus.format;
import static net.nawaman.lambdacalculusj.LambdaCalculus.lambda;
import static net.nawaman.lambdacalculusj.TestHelper.assertAsString;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

class ArithmeticShortcutExamples {
    
    @Test
    void testNotShortcut() throws InterruptedException {
        var zero   = lambda("0",  lambda(f -> a -> a,             () -> 0));
        var two    = lambda("2",  lambda(f -> a -> $(f, $(f, a)), () -> 2));
        var fourty = lambda("40", lambda(f -> a -> $(f, $(f, $(f, $(f, $(f, $(f, $(f, $(f, $(f, $(f, $(f, $(f, $(f, $(f, $(f, $(f, $(f, $(f, $(f, $(f, $(f, $(f, $(f, $(f, $(f, $(f, $(f, $(f, $(f, $(f, $(f, $(f, $(f, $(f, $(f, $(f, $(f, $(f, $(f, $(f, a)))))))))))))))))))))))))))))))))))))))), () -> 40));
        //                                           1    2    3    4    5    6    7    8    9   10   11   12   13   14   15   16   17   18   19   20   21   22   23   24   25   26   27   28   29   30   31   32   33   34   35   36   37   38   39   40
        
        var successor   = lambda("successor",   n -> lambda(f -> a -> $$(f, $$(n, f, a)), () -> n.intValue() + 1));
        var pairOf      = lambda("pairOf",      a -> b -> lambda(format("Pair[%s,%s]", a, b), f -> $(f,a,b)));
        var first       = lambda("first",       p -> $(p, lambda(a -> b -> a)));
        var second      = lambda("second",      p -> $(p, lambda(a -> b -> b)));
        var transform   = lambda("transform",   p -> $(pairOf, $(second, p), $(successor, $(second, p))));
        var predecessor = lambda("predecessor", n -> $(first, $($(n, transform), $(pairOf, zero, zero))));
        var subtract    = lambda("subtract",    n -> k -> $($(k, predecessor), n));
        
        // Check that it take longer than 1 second to compute
        var result = new AtomicInteger(-1);
        var calculate = new Thread(() -> {
            var resultLambda = $(subtract, fourty, two);
            result.set(resultLambda.intValue());
        });
        calculate.start();
        Thread.sleep(100);
        calculate.interrupt();
        
        // The calculation is NOT done before the interrupt
        assertAsString("-1", result.get());
    }
    
    @Test
    void testShortcut() throws InterruptedException {
        var two      = lambda(2);
        var fourty   = lambda(40);
        var subtract = lambda("subtract", n -> k -> lambda(n.intValue() - k.intValue()));
        
        // Check that it take longer than 1 second to compute
        var result = new AtomicInteger(-1);
        var calculate = new Thread(() -> {
            var resultLambda = $(subtract, fourty, two);
            result.set(resultLambda.intValue());
        });
        calculate.start();
        Thread.sleep(100);
        calculate.interrupt();
        
        // The calculation is done before the interrupt
        assertAsString("38", result.get());
    }
    
}
