package jsy.lab1

/*
 * Search Tree
 */
object tree {
  sealed abstract class Tree
  case object Empty extends Tree
  case class Node(l: Tree, d: Int, r: Tree) extends Tree
}
