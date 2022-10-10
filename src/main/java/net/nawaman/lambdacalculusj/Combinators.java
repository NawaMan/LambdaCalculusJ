package net.nawaman.lambdacalculusj;

import static net.nawaman.lambdacalculusj.LambdaCalculus.lambda;
import static net.nawaman.lambdacalculusj.LambdaCalculus.lazy;
import static net.nawaman.lambdacalculusj.LambdaCalculus.$;

/**
 * This class contains a set of combinator.
 */
public interface Combinators {
    
    // See more : https://github.com/loophp/combinator
    
    public static final Lambda apply    = lambda("apply",    f -> a ->       $(f, a));       // $
    public static final Lambda constant = lambda("constant", a -> b ->       a);
    public static final Lambda compose  = lambda("compose",  f -> g -> a ->  $(f, $(g, a))); // .
    public static final Lambda flip     = lambda("flip",     f -> a -> b ->  $(f, b, a));
    public static final Lambda identity = lambda("identity", a ->            a);             // id
    public static final Lambda repeat   = lambda("repeat",   a ->            $(a, a));       // const
    
    public static final Lambda bluebird    = compose;
    public static final Lambda blackbird   = lambda("blackbird", f -> a -> b -> c->  $(f, $(a, b, c)));  // ...
    public static final Lambda cardinal    = flip;
    public static final Lambda dove        = lambda("dove",        a -> b -> c -> d ->       $(a, b, $(c, d)));
    public static final Lambda eagle       = lambda("eagle",       a -> b -> c -> d -> e ->  $(a, b, $(c, d, e)));
    public static final Lambda finch       = lambda("finch",       a -> b -> c ->            $(c, b, a));
    public static final Lambda goldfinch   = lambda("goldfinch",   a -> b -> c -> d ->       $(a, d, $(b, c)));
    public static final Lambda hummingbird = lambda("hummingbird", a -> b -> c      ->       $(a, b, c, b));
    public static final Lambda mockingbird = repeat;
    public static final Lambda idiot       = identity;
    public static final Lambda jay         = lambda("jay", a -> b -> c -> d ->  $(a, b, $(a, d, c)));
    public static final Lambda kestrel     = constant;
    public static final Lambda kite        = lambda("kite",     a -> b ->            b);
    public static final Lambda lark        = lambda("lark",     a -> b ->            $(a, $(b, b)));
    public static final Lambda owl         = lambda("owl",      a -> b ->            $(b, $(a, b)));
    public static final Lambda phoenix     = lambda("phoenix",  a -> b -> c -> d ->  $(a, $(b, d), $(c, d)));
    public static final Lambda queer       = lambda("queer",    a -> b -> c ->       $(b, $(a, c)));
    public static final Lambda robin       = lambda("robin",    a -> b -> c ->       $(b, c, a));
    public static final Lambda starling    = lambda("starling", a -> b -> c ->       $(a, c, $(b, c)));  // <*>
    public static final Lambda thrush      = lambda("thrush",   a -> b ->            $(b, a));
    public static final Lambda turing      = lambda("turing",   a -> b ->            $(b, $(a, a, b)));
    public static final Lambda vireo       = lambda("vireo",    a -> b -> c ->       $(c, a, b));
    public static final Lambda warbler     = lambda("warbler",  a -> b ->            $(a, b, b));
    
    public static final Lambda iota  = lambda("iota",   f ->                 $(f, $(a -> b -> c -> $(a,c, $(b,c))), $(x -> y -> x)));
    public static final Lambda omega = lambda("omega",  a ->                 $($(a, a), $(a, a)));
    public static final Lambda psi   = lambda("psi",    a -> b -> c -> d ->  $(a, $(b, c), $(b, d)));

    public static final Lambda I = iota;
    public static final Lambda Ψ = psi;
    public static final Lambda Ω = omega;
    
    public static final Lambda B  = bluebird;
    public static final Lambda C  = flip;
    public static final Lambda Id = identity;
    public static final Lambda K  = constant;
    public static final Lambda KI = kite;
    public static final Lambda M  = repeat;
    public static final Lambda L  = lark;
    public static final Lambda O  = owl;
    public static final Lambda Q  = queer;
    public static final Lambda S  = starling;
    public static final Lambda S_ = lambda("S_", a -> b -> c ->       $(a, $(b, c), c));           // <*>
    public static final Lambda S2 = lambda("S2", a -> b -> c -> d ->  $(a, $($(b, d), $(c, d))));  // <*>
    public static final Lambda T  = thrush;
    public static final Lambda U  = turing;
    public static final Lambda V  = vireo;
    public static final Lambda W  = warbler;
    public static final Lambda Y = lambda("Y", g ->  lazy(lambda(x -> lazy(g, lazy(x, x))),
                                                          lambda(x -> lazy(g, lazy(x, x)))));
    
}
