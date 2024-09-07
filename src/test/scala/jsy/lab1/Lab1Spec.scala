package jsy.student

import jsy.lab1._
import jsy.tester.JavascriptyTester
import org.scalatest.flatspec._
import org.scalatest.Suites

/*
 * We use a library called ScalaTest that makes it easy to write
 * and run tests.
 *
 * For Lab1, the Lab1Spec test file (this file) is the same as
 * the test file for grading.
 * 
 * In future assignments, this will change.
 */

class Lab1Spec(lab1: Lab1Like) extends AnyFlatSpec {
  import lab1._

  /*
   * Example: Test-driven development of plus
   *
   * Test-driven development means that you first write a test before
   * you write the code. This process makes it easier to guide you
   * to implement just what you need correctly.
   *
   * Understandably, this feels overkill for this plus example and
   * even for the Lab 1 assignment. The complexity of implementations
   * in this course increases quickly, so practicing this process now
   * will definitely help later.
   */

  // Define a single test case. The first string categorizes the test.
  // By convention, we are using the function name. The second string
  // gives an explanation for a test. For more information, go to
  // http://scalatest.org/.

  "plus" should "add two integers" in {
    // Specify assertions here.

    // A simple assert is fine.
    assert(plus(1, 1) === 2)

    // It is convenient to distinguish the expected result from the
    // code that you're testing, which affects the error messages
    // when the test fails.
    assertResult(2) {
      plus(1, 1)
    }
  }

  /* Exercises */

  // Check that abs works for negative, positive and zero values

  "abs" should "evaluate to the absolute value of the argument" in {
     assert(abs(2) === 2)
     assert(abs(-2) === 2)
     assert(abs(0) === 0)
  }

  // Check that xor works for all four combinations of boolean values

  "xor" should "evaluate to the exclusive or of the arguments" in {
    assert(!xor(true, true))
    assert(xor(true, false))
    assert(xor(false, true))
    assert(!xor(false, false))
  }

  // Check that repeat works

  "repeat" should "evaluate to repeated concatenation of a string" in {
    assert(repeat("a", 3) === "aaa")
    assert(repeat("abc", 3) === "abcabcabc")
    assert(repeat("a", 1) === "a")
    assert(repeat("abc", 1) === "abc")
    assert(repeat("abc", 4) === "abcabcabcabc")
    assert(repeat("", 5) === "")
  }

  // it says make this test in the same category as the last one
  it should "evaluate to an empty string when repeated zero times" in {
    assert(repeat("abc", 0) === "")
    assert(repeat("", 0) === "")
  }

  // Check that repeat requires a non-negative repetition amount

  it should "throw an exception when a negative number of repetitions is expected" in {
    intercept[java.lang.IllegalArgumentException] {
      repeat("abc", -3)
    }
  }

  // Check that sqrt works.  This requires steps for sqrtStep, sqrtN, and sqrtErr.

  "sqrtStep" should "evaluate to one iteration of Newton's method" in {
    assert(sqrtStep(4, 1) === 2.5)
    assert(sqrtStep(1, 1) === 1.0)
    assert(sqrtStep(5, 8) === 4.3125)
  }

  "sqrtN" should "perform several iterations of sqrtStep" in {
    assert(sqrtN(4,1,2) === 2.05)
    assert(sqrtN(4,1,6) === 2.0)
    assert(sqrtN(4,20,2) === 5.248019801980198)
    assert(sqrtN(4,20,6) === 2.0000105791285385)
  }

  it should "evaluate to x0 if n is zero" in {
    assert(sqrtN(4,1,0) === 1.0)
    assert(sqrtN(4,20,0) === 20.0)
  }

  "sqrtErr" should "perform iterations until the error is within epsilon" in {
    assert(sqrtErr(4, 1, 0.1) === 2.000609756097561)
    assert(sqrtErr(4, 1, 0.0001) === 2.0000000929222947)
    assert(sqrtErr(4, 1, 0.00000001) === 2.000000000000002)
  }


  // Make sure that the functions have the correct requires

  "sqrtN" should "throw an exception when n is negative" in {
    intercept[java.lang.IllegalArgumentException] {
      sqrtN(4, 1, -10)
    }
  }

  "sqrtErr" should "throw an exception when using a non-positive epsilon" in {
    intercept[java.lang.IllegalArgumentException] {
      sqrtErr(4, 1, -0.01)
    }
    intercept[java.lang.IllegalArgumentException] {
      sqrtErr(4, 1, 0.0)
    }
  }

  // Have a few trees pre-made to test against, both valid and invalid

  val t1 = Node(Empty, 2, Empty)
  val t2 = Node(t1, 4, Empty)
  val t3 = Node(Empty, 4, t2)
  val t4 = Node(t1, 4, Node(Empty, 4, Node(Empty, 5, Empty)))
  val t5 = Node(Empty, 2, t1)
  val t6 = Node(Empty, 4, Empty)
  val t7 = Node(Empty, 2, Node(Empty, 4, Empty))
  val t8 = Node(Node(Empty, 1, Empty), 2, Node(Empty, 3, Empty))
  val t9 = Node(Empty, 2, Node(Empty, 3, Empty))
  val t10 = Node(Node(Empty, 1, Empty), 2, Empty)
  val t11 = Node(Node(Empty, 1, Empty), 3, Empty)

  // repOk

  "repOk" should "ensure that a SearchTree is properly ordered" in {
    assert(repOk(Empty))
    assert(repOk(t1))
    assert(repOk(t2))
    assert(!repOk(t3))
    assert(repOk(t4))
    assert(repOk(t5))
  }

  // insertion

  "insert" should "insert numbers as leaves in SearchTrees at the proper position" in {
    assert(insert(Empty, 2) === t1)
    assert(insert(t1, 2) === t5)
    assert(insert(t6, 2) === t2)
    assert(insert(t1, 4) === t7)
  }

  // deleteMin

  "deleteMin" should "remove the smallest value from a tree, and provide the resulting tree" in {
    assert(deleteMin(t7) === (t6, 2))
    assert(deleteMin(t1) === (Empty, 2))
    assert(deleteMin(t5) === (t1, 2))
    assert(deleteMin(t4) === (Node(Empty, 4, Node(Empty, 4, Node(Empty, 5, Empty))), 2))
  }

  // delete

  "delete" should "remove a given value from a tree, if present" in {
    assert(delete(t1, 2) === Empty)
    assert(delete(t5, 2) === t1)
    assert(delete(t8, 1) === t9)
    assert(delete(t8, 3) === t10)
    assert(delete(t8, 2) === t11)
    assert(delete(t8, 4) === t8)
  }

  // Some more testing code that uses the Scala List libray.  The function 'treeFromList'
  // inserts all the elements in the list into the tree.  It is likely the code
  // will look quite mysterious now.  We hope break it down eventually in the
  // course, but you can try to figure it out by reading Scala documentation yourself.
  // To get started, /: is an operator to corresponds to the 'foldLeft' method on
  // lists.

  // Regardless, you can use this function to help test your code.
  def treeFromList(l: List[Int]): SearchTree = ((Empty: SearchTree) /: l)(insert)

  "insert" should "produce trees that satisfy repOk" in {
    assert(repOk(treeFromList(List(3, 4, 7, 2, 1, 10))))
    assert(repOk(treeFromList(List(1, 2, 3, 4, 5))))
    assert(repOk(treeFromList(List(9, 8, 7, 6, 5))))
    assert(repOk(treeFromList(List(1, 1, 1, 1))))
  }

  "insert-delete" should "produce trees that satisfy repOk" in {
    val ins = (n: Int) => (t: SearchTree) => insert(t,n)
    val del = (n: Int) => (t: SearchTree) => delete(t,n)
    ((Empty: SearchTree) /: List(ins(2), ins(6), ins(10), ins(22), del(4), del(6), ins(4), del(10), del(4)))(
        (t, f) => {
          val t1 = f(t)
          assert(repOk(t1))
          t1
        }
    )
  }

  // Eval

  "eval+" should "perform addition" in {
    assert(eval("1 + 1") === 2)
  }

  "eval-" should "perform subtraction" in {
    assert(eval("4 - 2") === 2)
  }

  "eval*" should "perform multiplication" in {
    assert(eval("4 * 2") === 8)
  }

  "eval/" should "perform division" in {
    assert(eval("4 / 2") === 2)
  }

  "eval/0" should "division by 0 should yield Infinity" in {
    assert(eval("4 / 0") === Double.PositiveInfinity)
    assert(eval("-4 / 0") === Double.NegativeInfinity)
  }

  it should "0/0 should yield NaN" in {
    assert(eval("0 / 0").isNaN)
  }

}

// An adapter class to pass in your Lab1 object.
class Lab1SpecRunner extends Lab1Spec(jsy.student.Lab1)

// The next bit of code runs a test for each .jsy file in src/test/resources/lab1.
// The test expects a corresponding .ans file with the expected result.
class Lab1JsyTests extends JavascriptyTester(None, "lab1", jsy.student.Lab1)

class Lab1Suite extends Suites(
  new Lab1SpecRunner,
  new Lab1JsyTests
)
