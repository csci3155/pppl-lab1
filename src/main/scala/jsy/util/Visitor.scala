package jsy.util

object Visitor {
  def apply[E,R](visitant: (E => R) => PartialFunction[E,R]) = new Visitor(visitant)

  def visit[E,R](visitant: (E => R) => PartialFunction[E,R])(e: E): R = {
    def rec(e: E): R = visitant(rec)(e)
    rec(e)
  }
}

/** A visitor of type Visitor[E,R] is an abstraction of a recursive function
  * on E of type E => R where E is an algebraic data type that might be extended.
  *
  * @param visitant
  * @tparam E
  * @tparam R
  */
class Visitor[E,R] private (val visitant: (E => R) => PartialFunction[E,R]) {
  private lazy val rec: E => R = Visitor.visit(visitant)
  def apply(e: E) = rec(e)

  def orElse(that: Visitor[E,R]): Visitor[E,R] = orElse(that.visitant)
  def orElse(that: (E => R) => PartialFunction[E,R]): Visitor[E,R] = new Visitor(
    (v: E => R) => visitant(v) orElse that(v)
  )

  def extendWith(that: Visitor[E,R]): Visitor[E,R] = that orElse this
  def extendWith(that: (E => R) => PartialFunction[E,R]) = new Visitor(that).orElse(this)

  def flatMap[B](op: (E => R) => Visitor[E,B]): Visitor[E,B] = op(rec)
}
