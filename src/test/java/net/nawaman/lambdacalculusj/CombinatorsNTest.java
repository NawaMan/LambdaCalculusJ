package net.nawaman.lambdacalculusj;

import static net.nawaman.lambdacalculusj.Combinators.N;
import static net.nawaman.lambdacalculusj.Combinators.N2;
import static net.nawaman.lambdacalculusj.Combinators.N3;
import static net.nawaman.lambdacalculusj.Combinators.N4;
import static net.nawaman.lambdacalculusj.LambdaCalculus.$;
import static net.nawaman.lambdacalculusj.LambdaCalculus.lambda;
import static net.nawaman.lambdacalculusj.LambdaCalculus.wholeNumber;
import static net.nawaman.lambdacalculusj.TestHelper.assertAsString;
import static net.nawaman.lambdacalculusj.WholeNumbers.add;
import static net.nawaman.lambdacalculusj.WholeNumbers.power;

import org.junit.jupiter.api.Test;

class CombinatorsNTest {
    
    static interface F1 {
        int apply(int x);
    }
    
    @Test
    void testN1() {
        // The result of the first become the value to the second which used it twice.
        //             (x + 1)**2 + (x + 1) + 1
        var func = lambda(x -> $(N,
                /* X = */ $(add, x, wholeNumber(1)),
                X -> $(add,
                        $(add,
                            $(power, X, wholeNumber(2)),
                            X
                        ),
                        wholeNumber(1)
                    )
                )
        );
        
        var f = (F1)((x) -> ((x + 1)*(x + 1) + (x + 1) + 1));
        assertAsString("" + f.apply(0), $(func, wholeNumber(0)));
        assertAsString("" + f.apply(1), $(func, wholeNumber(1)));
        assertAsString("" + f.apply(2), $(func, wholeNumber(2)));
        assertAsString("" + f.apply(3), $(func, wholeNumber(3)));
    }
    
    static interface F2 {
        int apply(int x, int y);
    }
    
    @Test
    void testN2() {
        // The result of the first become the value to the second which used it twice.
        //             (x + 1)**2 + (y + 2)**2 + 1
        var func = lambda(x -> y -> $(N2,
                /* X = */      $(add, x, wholeNumber(1)),
                /* Y = */ X -> $(add, y, wholeNumber(2)),
                //             X**2 + Y**2 + 1
                X -> Y      -> $(add,
                                    $(add,
                                        $(power, X, wholeNumber(2)),
                                        $(power, Y, wholeNumber(2))
                                    ),
                                    wholeNumber(1)
                                )
                )
        );
        
        var f = (F2)((x, y) -> ((x + 1)*(x + 1) + (y + 2)*(y + 2) + 1));
        assertAsString("" + f.apply(0, 0),  $(func, wholeNumber(0), wholeNumber(0)));
        assertAsString("" + f.apply(1, 1), $(func, wholeNumber(1), wholeNumber(1)));
        assertAsString("" + f.apply(2, 2), $(func, wholeNumber(2), wholeNumber(2)));
        assertAsString("" + f.apply(3, 3), $(func, wholeNumber(3), wholeNumber(3)));
    }
    
    static interface F3 {
        int apply(int x, int y, int z);
    }
    
    @Test
    void testN3() {
        // The result of the first become the value to the second which used it twice.
        // (x + 1)**2 + (y + 2)**2 + (z + 3)**2 + 1
        var func = lambda(x -> y -> z -> $(N3,
                /* X = */           $(add, x, wholeNumber(1)),
                /* Y = */ X ->      $(add, y, wholeNumber(2)),
                /* Z = */ X -> Y -> $(add, z, wholeNumber(3)),
                //                   X**2 + Y**2 + Z**2 + 1
                X -> Y -> Z      -> $(add,
                                        $(add,
                                            $(add,
                                                $(power, X, wholeNumber(2)),
                                                $(power, Y, wholeNumber(2))
                                            ),
                                            $(power, Z, wholeNumber(2))
                                         ),
                                        wholeNumber(1)
                                    )
                )
        );
        
        var f = (F3)((x, y, z) -> ((x + 1)*(x + 1) + (y + 2)*(y + 2) + (z + 3)*(z + 3) + 1));
        assertAsString("" + f.apply(0, 0, 0), $(func, wholeNumber(0), wholeNumber(0), wholeNumber(0)));
        assertAsString("" + f.apply(1, 1, 1), $(func, wholeNumber(1), wholeNumber(1), wholeNumber(1)));
        assertAsString("" + f.apply(2, 2, 2), $(func, wholeNumber(2), wholeNumber(2), wholeNumber(2)));
        assertAsString("" + f.apply(3, 3, 3), $(func, wholeNumber(3), wholeNumber(3), wholeNumber(3)));
    }
    
    static interface F4 {
        int apply(int x, int y, int z, int w);
    }
    
    @Test
    void testN4() {
        // The result of the first become the value to the second which used it twice.
        // (x + 1)**2 + (y + 2)**2 + (z + 3)**2 + (w + 4)**2 + 1
        var func = lambda(x -> y -> z -> w -> $(N4,
                /* X = */                $(add, x, wholeNumber(1)),
                /* Y = */ X ->           $(add, y, wholeNumber(2)),
                /* Z = */ X -> Y ->      $(add, z, wholeNumber(3)),
                /* W = */ X -> Y -> Z -> $(add, w, wholeNumber(4)),
                //                   X**2 + Y**2 + Z**2 + W**2 + 1
                X -> Y -> Z -> W ->
                    $(add,
                        $(add,
                            $(add,
                                $(add,
                                    $(power, X, wholeNumber(2)),
                                    $(power, Y, wholeNumber(2))
                                ),
                                $(power, Z, wholeNumber(2))
                            ),
                            $(power, W, wholeNumber(2))
                        ),
                        wholeNumber(1)
                    )
                )
        );
        
        var f = (F4)((x, y, z, w) -> ((x + 1)*(x + 1) + (y + 2)*(y + 2) + (z + 3)*(z + 3) + (w + 4)*(w + 4) + 1));
        assertAsString("" + f.apply(0, 0, 0, 0), $(func, wholeNumber(0), wholeNumber(0), wholeNumber(0), wholeNumber(0)));
        assertAsString("" + f.apply(1, 1, 1, 1), $(func, wholeNumber(1), wholeNumber(1), wholeNumber(1), wholeNumber(1)));
        assertAsString("" + f.apply(2, 2, 2, 2), $(func, wholeNumber(2), wholeNumber(2), wholeNumber(2), wholeNumber(2)));
        assertAsString("" + f.apply(3, 3, 3, 3), $(func, wholeNumber(3), wholeNumber(3), wholeNumber(3), wholeNumber(3)));
    }
    
}
