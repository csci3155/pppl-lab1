import jsy.lab5.ast._
import jsy.lab5.Parser.parse

import jsy.util.Visitor

val oldheight: Visitor[Expr,Int] = Visitor(rec => {
  case N(_) => 1
  case Binary(_, e1, e2) => 1 + (rec(e1) max rec(e2))
})


val olde: Expr = parse("1 + (1 + 2)")
val oldh = oldheight(olde)


case class Foo(e: Expr) extends Expr
val newe: Expr = Binary(Plus, Foo(olde), N(3))

val newheight: Visitor[Expr,Int] = oldheight extendWith ((rec: Expr => Int) => {
  case Foo(e1) => 1 + rec(e1)
})

val newh = newheight(newe)

