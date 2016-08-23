package jsy.util

/*
 * DoWithContext is a data structure that holds a function that returns a result of
 * type R with a context C.
 *
 * Aside: This is also known as the Reader monad.
 */
sealed class DoWithContext[-C,+R] private (doer: C => R) {
  def apply(c: C) = doer(c)

  def map[B](f: R => B): DoWithContext[C,B] = new DoWithContext[C,B]({
    (c: C) => {
      val r = doer(c)
      f(r)
    }
  })

  def flatMap[D <: C,B](f: R => DoWithContext[D,B]): DoWithContext[D,B] = new DoWithContext[D,B]({
    (d: D) => {
      val r = doer(d)
      f(r)(d)
    }
  })
}

object DoWithContext {
  def doget[C]: DoWithContext[C, C] = new DoWithContext[C,C]({ c => c })
  def doreturn[C,R](r: R): DoWithContext[C, R] = new DoWithContext[C,R]({ _ => r })
}

