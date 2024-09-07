/*
 * CSCI 3155: Lab 1 Worksheet
 *
 * This worksheet demonstrates how you could experiment
 * interactively with your implementations in Lab1.scala.
 */

/*
 * Example: Test-driven development of `plus`
 */

/* Here we can write expressions to experiment with how we might implement
 * something. The expression is evaluated interactively.
 */
1 + 1
val n = 1 + 1
n + 3

/* The worksheet is built with all of the project files, so we can call
 * a function from your `jsy.lab1.Lab1` object (in Lab1.scala).
 */
//jsy.lab1.Lab1.plus(3, 4)

/* We can imports all of the functions from your `jsy.lab1.Lab1` object. */
import jsy.lab1.Lab1._
//plus(3, 4)

/* We can check the implementation here, though it better to write tests
 * in Lab1Spec.scala.
 */
//assert(plus(1, 1) == 2)

/* Braces {} can be used wherever parentheses () can be (but not the other
 * way around). Braces {} introduce scope, while () do not.
 */
// assert {
//   val two = 2
//   plus(1, 1) == two
// }

/* Exercises */

//assert(repeat("a", 3) == "aaa")

/* We import jsy.lab.ast._ to use the AST nodes. */
import jsy.lab1.ast._

/* A parser defines how to translate concrete syntax (i.e., strings a programmer types)
 * into an abstract syntax tree (AST) that we use to evaluate the program. A parser
 * is given to you in jsy.lab1.Parser. You can use the parser to parse a String into
 * an Expr.
 *
 * Here are some shortcuts to that parser for your reference.
* */
import jsy.lab1.Parser
def parse(s: String): Expr = Parser.parse(s)
def parseFile(filename: String): Expr = Parser.parseFile(filename)

/* Call the parser (from the provided library) on a string */
parse("-4")

val negFourAST = parse("-4")
assert {
  negFourAST match {
    case Unary(_, _) => true
    case _           => false
  }
}

/* Evaluate that `negFourAST` expression. */
//eval(negFourAST)

/* For convenience, we also have an overloaded `jsy.lab1.Lab1.eval` function that takes
 * a String, calls the parser, and then delegates to your `eval` function.
 */
//eval("1 + 1")
