/*
 * CSCI 3155: Lab 1 Worksheet
 *
 * This worksheet demonstrates how you could experiment
 * interactively with your implementations in Lab1.scala.
 */

/*
 * Example: Test-driven development of plus
 */

// Here we can write expressions to experiment with how we might implement
// something. The expression is evaluated interactively.
1 + 1
val n = 1 + 1
n + 3

// The worksheet is built with all of the project files, so we can call
// a function from your jsy.student.Lab1 object (in Lab1.scala).
jsy.student.Lab1.plus(3, 4)

// We can imports all of the functions from jsy.student.Lab1
import jsy.student.Lab1._
plus(3, 4)

// We can check the implementation here, though it better to write tests
// in Lab1Spec.scala.
assert(plus(1, 1) == 2)

// Braces {} can be used wherever parentheses () can be (but not the other
// way around). Braces {} introduce scope, while () do not.
assert {
  val two = 2
  plus(1, 1) == two
}

/* Exercises */

// Call jsy.student.Lab1.abs
//abs(-3) // Will fail until implemented in Lab1.scala.

// Call the JavaScripty parser (from the provided library) on a string
jsy.lab1.Parser.parse("-4")

// We can import the parse function from jsy.lab1.Parser to experiment
// with the provided parser.
import jsy.lab1.Parser.parse
val negFourAST = parse("-4")

// We also want to import the AST nodes for convenience.
import jsy.lab1.ast._
assert {
  negFourAST match {
    case Unary(_, _) => true
    case _ => false
  }
}

// Evaluate that JavaScripty expression.
//eval(negFourAST)

// For convenience, we also have an eval function that takes a string,
// which calls the parser and then delegates to your eval function.
//eval("1 + 1")
