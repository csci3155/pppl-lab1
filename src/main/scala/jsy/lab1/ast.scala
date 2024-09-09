package jsy.lab1

import scala.util.parsing.input.Positional

/**
 * @author Bor-Yuh Evan Chang
 */
object ast {
  sealed trait Expr extends Positional                         // e ::=

  /* Literals */
  case class N(n: Double) extends Expr                         //   n

  /* Unary and Binary Operators */
  case class Unary(uop: Uop, e1: Expr) extends Expr            // | uop e1
  case class Binary(bop: Bop, e1: Expr, e2: Expr) extends Expr // | e1 bop e2

  sealed trait Uop              // uop ::=
  case object Neg extends Uop   //   -

  sealed trait Bop              // bop ::=
  case object Plus extends Bop  //   +
  case object Minus extends Bop // | -
  case object Times extends Bop // | *
  case object Div extends Bop   // | /

  /* Define values. */
  def isValue(e: Expr): Boolean = e match {
    case N(_) => true
    case _ => false
  }
  
  /*
   * Pretty-print values.
   * 
   * We do not override the toString method so that the abstract syntax can be printed
   * as-is.
   */
  def pretty(v: Expr): String = {
    require(isValue(v))
    (v: @unchecked) match {
      case N(n) => prettyNumber(n)
    }
  }

  def prettyNumber(n: Double): String =
    if (n.isWhole) "%.0f" format n else n.toString
}