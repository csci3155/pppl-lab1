package jsy.lab1

object Lab1 {
  /*
   * CSCI 3155: Lab 1
   * <Your Name>
   *
   * Partner: <Your Partner's Name>
   * Collaborators: <Any Collaborators>
   */

  /*
   * Fill in the appropriate portions above by replacing things delimited
   * by '<'... '>'.
   *
   * Replace the '???' expression with your code in each function. The
   * '???' expression is a Scala expression that throws a NotImplementedError
   * exception.
   *
   * Your lab will not be graded if it does not compile.
   *
   * This template compiles without error. Before you submit comment out any
   * code that does not compile or causes a failing assert.  Simply put in a
   * '???' as needed to get something that compiles without error.
   */

  /*
   * Example: Test-driven development of `plus`
   *
   * A convenient, quick-and-dirty way to experiment, especially with small code
   * fragments, is to use the interactive Scala interpreter. The simplest way
   * to use the interactive Scala interpreter is through a worksheet, such as
   * Lab1.worksheet.sc. A Scala Worksheet is code evaluated in the context of
   * the project with results for each expression shown inline (somewhat like a
   * Jupyter notebook).
   *
   * Step 0: Sketch an implementation in Lab1.scala using ??? for unimplemented things.
   * Step 1: Do some experimentation in Lab1.worksheet.sc.
   * Step 2: Write a test in Lab1Spec.scala, which should initially fail because of the ???.
   * Step 3: Fill in the ??? here to finish the implementation to make your test pass.
   */

  //def plus(x: Int, y: Int): Int = ???
  def plus(x: Int, y: Int): Int = 0
  // def plus(x: Int, y: Int): Int = x + y

  /* Exercises */

  def repeat(s: String, n: Int): String = ???

  def sqrtStep(c: Double, xn: Double): Double = ???

  def sqrtN(c: Double, x0: Double, n: Int): Double = ???

  def sqrtErr(c: Double, x0: Double, epsilon: Double): Double = ???

  def sqrt(c: Double): Double = {
    require(c >= 0)
    if (c == 0) 0 else sqrtErr(c, 1.0, 0.0001)
  }

  /* Search Tree */

  sealed abstract class Tree
  case object Empty extends Tree
  case class Node(l: Tree, d: Int, r: Tree) extends Tree

  def repOk(t: Tree): Boolean = {
    def check(t: Tree, min: Int, max: Int): Boolean = t match {
      case Empty         => true
      case Node(l, d, r) => ???
    }
    check(t, Int.MinValue, Int.MaxValue)
  }

  def insert(t: Tree, n: Int): Tree = ???

  def deleteMin(t: Tree): (Tree, Int) = {
    require(t != Empty)
    (t: @unchecked) match {
      case Node(Empty, d, r) => (r, d)
      case Node(l, d, r) =>
        val (l1, m) = deleteMin(l)
        ???
    }
  }

  def delete(t: Tree, n: Int): Tree = ???

  /* Evaluate the JavaScripty Calculator Language */

  /* We import `jsy.lab1.ast._` so that we can make use of the Expr type defined
   * there. Take a look at ast.scala to see what the Expr type looks like, as well
   * some helper functions for defining values (`isValue`) and pretty-printing
   * (`pretty`) that you are welcome to use.
   */
  import jsy.lab1.ast._

  def eval(e: Expr): Double = e match {
    case N(n) => ???
    case _    => ???
  }

  /* We also overload `eval` to take a String and parse it into an Expr and then
   * evaluate it  using your `eval` function defined above.
   */
  def eval(e: String): Double = eval(Parser.parse(e))

}
