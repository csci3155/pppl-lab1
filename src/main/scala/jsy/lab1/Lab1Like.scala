package jsy.lab1

import jsy.lab1.ast.Expr

sealed abstract class SearchTree
case object Empty extends SearchTree
case class Node(l: SearchTree, d: Int, r: SearchTree) extends SearchTree

trait Lab1Like {
  def plus(x: Int, y: Int): Int
  def abs(n: Double): Double
  def xor(a: Boolean, b: Boolean): Boolean
  def repeat(s: String, n: Int): String
  def sqrtStep(c: Double, xn: Double): Double
  def sqrtN(c: Double, x0: Double, n: Int): Double
  def sqrtErr(c: Double, x0: Double, epsilon: Double): Double
  def sqrt(c: Double): Double

  def repOk(t: SearchTree): Boolean
  def insert(t: SearchTree, n: Int): SearchTree
  def deleteMin(t: SearchTree): (SearchTree, Int)
  def delete(t: SearchTree, n: Int): SearchTree

  def eval(e: Expr): Double
  def eval(s: String): Double
}