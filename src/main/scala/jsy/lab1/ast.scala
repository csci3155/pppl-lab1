package jsy.lab1

import scala.util.parsing.input.Positional

/**
 * @author Bor-Yuh Evan Chang
 */
object ast {
  sealed abstract class Expr extends Positional
  
  /* Literals and Values*/
  case class N(n: Double) extends Expr
  
  /* Unary and Binary Operators */
  case class Unary(uop: Uop, e1: Expr) extends Expr
  case class Binary(bop: Bop, e1: Expr, e2: Expr) extends Expr

  sealed abstract class Uop
  
  case object Neg extends Uop /* - */

  sealed abstract class Bop
  
  case object Plus extends Bop /* + */
  case object Minus extends Bop /* - */
  case object Times extends Bop /* * */
  case object Div extends Bop /* / */

  /* Define values. */
  def isValue(e: Expr): Boolean = e match {
    case N(_) => true
    case _ => false
  }
  
  /*
   * Pretty-print values.
   * 
   * We do not override the toString method so that the abstract syntax can be printed
   * as is.
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