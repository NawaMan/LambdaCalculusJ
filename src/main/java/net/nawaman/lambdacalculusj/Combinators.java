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
    public static final Lambda pair     = lambda("pair",     a -> b -> c ->  $(c, a, b));
    public static final Lambda repeat   = lambda("repeat",   a ->            $(a, a));       // const
    
    public static final Lambda bluebird          = compose;
    public static final Lambda baldEagle         = lambda("baldEagle",         a -> b -> c -> d -> e -> f -> g->  $(a, $(b, c, d), $(e, f, g)));
    public static final Lambda becard            = lambda("becard",            f -> a -> b -> c ->                $(f, $(a, $(b, c))));
    public static final Lambda blackbird         = lambda("blackbird",         f -> a -> b -> c ->                $(f, $(a, b, c)));     // ...
    public static final Lambda doubleMockingbird = lambda("doubleMockingbird", a -> b ->                          $(a, b, $(a, b)));
    public static final Lambda bunting           = lambda("bunting",           f -> a -> b -> c -> d ->           $(f, $(a, b, c, d)));
    public static final Lambda cardinal          = flip;
    public static final Lambda dove              = lambda("dove",        a -> b -> c -> d ->       $(a, b, $(c, d)));
    public static final Lambda dickcissel        = lambda("dickcissel",  a -> b -> c -> d -> e ->  $(a, b, c, $(d, e)));
    public static final Lambda dovekies          = lambda("dovekies",    a -> b -> c -> d -> e ->  $(a, $(b, c), $(d, e)));
    public static final Lambda eagle             = lambda("eagle",       a -> b -> c -> d -> e ->  $(a, b, $(c, d, e)));
    public static final Lambda finch             = lambda("finch",       a -> b -> c ->            $(c, b, a));
    public static final Lambda goldfinch         = lambda("goldfinch",   a -> b -> c -> d ->       $(a, d, $(b, c)));
    public static final Lambda hummingbird       = lambda("hummingbird", a -> b -> c      ->       $(a, b, c, b));
    public static final Lambda idiot             = identity;
    public static final Lambda jay               = lambda("jay", a -> b -> c -> d ->  $(a, b, $(a, d, c)));
    public static final Lambda kestrel           = constant;
    public static final Lambda kite              = lambda("kite",     a -> b ->            b);
    public static final Lambda lark              = lambda("lark",     a -> b ->            $(a, $(b, b)));
    public static final Lambda mockingbird       = repeat;
    public static final Lambda owl               = lambda("owl",       a -> b ->            $(b, $(a, b)));
    public static final Lambda phoenix           = lambda("phoenix",   a -> b -> c -> d ->  $(a, $(b, d), $(c, d)));
    public static final Lambda queer             = lambda("queer",     a -> b -> c ->       $(b, $(a, c)));
    public static final Lambda quixotic          = lambda("quixotic",  a -> b -> c ->       $(a, $(c, b)));
    public static final Lambda quizzical         = lambda("quizzical", a -> b -> c ->       $(b, $(c, a)));
    public static final Lambda quirky            = lambda("quirky",    a -> b -> c ->       $(c, $(a, b)));
    public static final Lambda quacky            = lambda("quacky",    a -> b -> c ->       $(c, $(b, a)));
    public static final Lambda robin             = lambda("robin",     a -> b -> c ->       $(b, c, a));
    public static final Lambda starling          = lambda("starling",  a -> b -> c ->       $(a, c, $(b, c)));  // <*>
    public static final Lambda thrush            = lambda("thrush",    a -> b ->            $(b, a));
    public static final Lambda turing            = lambda("turing",    a -> b ->            $(b, $(a, a, b)));
    public static final Lambda vireo             = pair;
    public static final Lambda warbler           = lambda("warbler",   a -> b ->            $(a, b, b));
    
    public static final Lambda iota  = lambda("iota",   f ->                 $(f, $(a -> b -> c -> $(a,c, $(b,c))), $(x -> y -> x)));
    public static final Lambda omega = lambda("omega",  a ->                 $($(a, a), $(a, a)));
    public static final Lambda psi   = lambda("psi",    a -> b -> c -> d ->  $(a, $(b, c), $(b, d)));

    public static final Lambda I = iota;
    public static final Lambda Ψ = psi;
    public static final Lambda Ω = omega;
    
    public static final Lambda B  = bluebird;
    public static final Lambda B1 = blackbird;
    public static final Lambda B2 = bunting;
    public static final Lambda B3 = becard;
    public static final Lambda C  = flip;
    public static final Lambda D  = dove;
    public static final Lambda D1 = dickcissel;
    public static final Lambda E  = eagle;
    public static final Lambda E2 = baldEagle;
    public static final Lambda F  = finch;
    public static final Lambda G  = goldfinch;
    public static final Lambda H  = hummingbird;
    public static final Lambda Id = identity;
    public static final Lambda J  = jay;
    public static final Lambda K  = constant;
    public static final Lambda KI = kite;
    public static final Lambda L  = lark;
    public static final Lambda M  = repeat;
    public static final Lambda M2 = doubleMockingbird;
    public static final Lambda O  = owl;
    public static final Lambda Q  = queer;
    public static final Lambda Q1 = quixotic;
    public static final Lambda Q2 = quizzical;
    public static final Lambda Q3 = quirky;
    public static final Lambda Q4 = quacky;
    public static final Lambda R  = robin;
    public static final Lambda S  = starling;
    public static final Lambda S_ = lambda("S_", a -> b -> c ->       $(a, $(b, c), c));           // <*>
    public static final Lambda S2 = lambda("S2", a -> b -> c -> d ->  $(a, $($(b, d), $(c, d))));  // <*>
    public static final Lambda T  = thrush;
    public static final Lambda U  = turing;
    public static final Lambda V  = vireo;
    public static final Lambda W  = warbler;
    public static final Lambda Y = lambda("Y", g ->  lazy(lambda(x -> lazy(g, lazy(x, x))),
                                                          lambda(x -> lazy(g, lazy(x, x)))));
    
    //== N-combinator -- Nightingale (or Nawa :-p)
    
    public static final Lambda N  = lambda("N",  a -> b ->                           $(b, a));
    public static final Lambda N2 = lambda("N2", a -> b -> c ->                      $(c, a, $(N, a, b)));
    public static final Lambda N3 = lambda("N3", a -> b -> c -> d ->                 $(d, a, $(N, a, b), $(N2, a, b, c)));
    public static final Lambda N4 = lambda("N4", a -> b -> c -> d -> e ->            $(e, a, $(N, a, b), $(N2, a, b, c), $(N3, a, b, c, d)));
    public static final Lambda N5 = lambda("N5", a -> b -> c -> d -> e -> f ->       $(f, a, $(N, a, b), $(N2, a, b, c), $(N3, a, b, c, d), $(N4, a, b, c, d, e)));
    public static final Lambda N6 = lambda("N6", a -> b -> c -> d -> e -> f -> g ->  $(g, a, $(N, a, b), $(N2, a, b, c), $(N3, a, b, c, d), $(N4, a, b, c, d, e), $(N5, a, b, c, d, e, f)));
    
}
