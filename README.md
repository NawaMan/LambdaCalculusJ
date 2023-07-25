# LambdaCalculusJ
Play round with Lambda Calculus in Java.

You can write something like:

```
    var TRUE  = lambda("TRUE",  x -> y -> x);
    var FALSE = lambda("FALSE", x -> y -> y);
    var and   = lambda("and", p -> q -> $(p, q, p));
    show("TRUE and FALSE", $(and, TRUE, FALSE));
```

## Play Around

- REPLIT: [`link`](https://replit.com/@NawaMan/TryLambdaCalculusJ#src/main/java/MainExamples.java)

## Main features

- [`Lambda`](https://github.com/NawaMan/LambdaCalculusJ/blob/main/src/main/java/net/nawaman/lambdacalculusj/Lambda.java) type with
  - one parameter only
  - automatically create valid lambda for boolean
  - automatically create valid lambda for whole number
- [`LambdaCalculus`](https://github.com/NawaMan/LambdaCalculusJ/blob/main/src/main/java/net/nawaman/lambdacalculusj/LambdaCalculus.java) utility class
  - Create a Lambda quickly with [`λ(...)`](https://github.com/NawaMan/LambdaCalculusJ/blob/main/src/main/java/net/nawaman/lambdacalculusj/LambdaCalculus.java#L22) or with [`lambda(...)`](https://github.com/NawaMan/LambdaCalculusJ/blob/main/src/main/java/net/nawaman/lambdacalculusj/LambdaCalculus.java#L53) method.
  - Create a Lambda quickly for whole number function -- [`wholeNumber(...)`](https://github.com/NawaMan/LambdaCalculusJ/blob/main/src/main/java/net/nawaman/lambdacalculusj/LambdaCalculus.java#L86).
  - Create a Lambda with printable name ([here](https://github.com/NawaMan/LambdaCalculusJ/blob/main/src/main/java/net/nawaman/lambdacalculusj/LambdaCalculus.java#L32) and [here](https://github.com/NawaMan/LambdaCalculusJ/blob/main/src/main/java/net/nawaman/lambdacalculusj/LambdaCalculus.java#L63)).
  - Eager application with [`$(...)`](https://github.com/NawaMan/LambdaCalculusJ/blob/main/src/main/java/net/nawaman/lambdacalculusj/LambdaCalculus.java#L114).
  - Lazy application with [`lazy`](https://github.com/NawaMan/LambdaCalculusJ/blob/main/src/main/java/net/nawaman/lambdacalculusj/LambdaCalculus.java#L125).
  - [`format(...)`](https://github.com/NawaMan/LambdaCalculusJ/blob/main/src/main/java/net/nawaman/lambdacalculusj/LambdaCalculus.java#L173), [`show(...)`](https://github.com/NawaMan/LambdaCalculusJ/blob/main/src/main/java/net/nawaman/lambdacalculusj/LambdaCalculus.java#L236) and [`displayValue(...)`](https://github.com/NawaMan/LambdaCalculusJ/blob/main/src/main/java/net/nawaman/lambdacalculusj/LambdaCalculus.java#L183) methods to easily inspect Lambda.
- Shortcut abstract for whole number making arithmetic operation faster (see [here](https://github.com/NawaMan/LambdaCalculusJ/blob/main/src/main/java/net/nawaman/lambdacalculusj/WholeNumbers.java)).
- Predefined common combinators (see [here](https://github.com/NawaMan/LambdaCalculusJ/blob/main/src/main/java/net/nawaman/lambdacalculusj/Combinators.java)).


## More Examples

- [Boolean examples](https://github.com/NawaMan/LambdaCalculusJ/blob/main/src/test/java/net/nawaman/lambdacalculusj/examples/BooleanExamples.java)
- [Whole number arithmetic examples](https://github.com/NawaMan/LambdaCalculusJ/blob/main/src/test/java/net/nawaman/lambdacalculusj/examples/WholeNumberArithmeticExamples.java)
- [Whole number arithmetic shortcut examples](https://github.com/NawaMan/LambdaCalculusJ/blob/main/src/test/java/net/nawaman/lambdacalculusj/examples/WholeNumberArithmeticShortcutExamples.java)
- [Data structure examples](https://github.com/NawaMan/LambdaCalculusJ/blob/main/src/test/java/net/nawaman/lambdacalculusj/examples/DataStructureExamples.java)
- [Loop examples](https://github.com/NawaMan/LambdaCalculusJ/blob/main/src/test/java/net/nawaman/lambdacalculusj/examples/LoopExamples.java)
- [Recursive examples](https://github.com/NawaMan/LambdaCalculusJ/blob/main/src/test/java/net/nawaman/lambdacalculusj/examples/RecursiveExamples.java)

## Need help?

Post question [here](https://github.com/NawaMan/LambdaCalculusJ/issues).

## Buy Me A Coffee

Like this? Consider [buy me a coffee](https://buymeacoffee.com/NawaMan).

