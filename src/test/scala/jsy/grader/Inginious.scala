package jsy.grader

import java.io._
import org.scalatest._
import org.scalatest.events._

class Inginious extends Reporter {
  val debug:Boolean = false
  var total:Double = 0
  var succeeded:Double = 0
  var tempFileName: String = "FAIL"

  def writeGrade(grade: Double) {
    if (debug) {
      println(f"Number of Tests: $total\nNumber Correct: $succeeded\nGrade: $grade%.1f")
    }
    val pw = new PrintWriter(new File(tempFileName))
    pw.write(f"$grade%.1f\n")
    pw.close()
  }

  // The test runner calls apply for each test
  def apply(event: Event): Unit = {
    event match {
      case RunStarting(_, _, configMap, _, _, _, _, _) => {
        println("RunStarting")
        // By passing a custom fileName for test output,
        // students can't cheat (hopefully)
        tempFileName = configMap.getRequired[String]("tempFileName")
      }
      case RunStopped(_, _, _, _, _, _, _, _) => {
        println("RunStopped")
        writeGrade(0)
      }
      case RunAborted(_, _, _, _, _, _, _, _, _, _) => {
        println("RunAborted")
        writeGrade(0)
      }
      case RunCompleted(_, _, _, _, _, _, _, _) => {
        writeGrade(100 * succeeded / total)
        println("RunCompleted")
      }
      case TestStarting(_, _, _, _, _, _, _, _, _, _, _, _) => {
        total += 1
        if (debug) println("TestStarting")
      }
      case TestSucceeded(_, _, _, _, _, _, _, _, _, _, _, _, _, _) => {
        succeeded += 1
        if (debug) println("TestSucceeded")
      }

      // There are many other events, but they are mostly irrelevant to us
      // I've just kept them here for completeness
      case DiscoveryStarting(_, _, _, _) => if (debug) println("DiscoveryStarting")
      case DiscoveryCompleted(_, _, _, _) => if (debug) println("DiscoveryCompleted")
      case ScopeOpened(_, _, _, _, _, _, _, _) => if (debug) println("ScopeOpened")
      case ScopeClosed(_, _, _, _, _, _, _, _) => if (debug) println("ScopeClosed")
      case ScopePending(_, _, _, _, _, _, _, _) => if (debug) println("ScopePending")
      case TestFailed(_, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _,_) => if (debug) println("TestFailed")
      case TestCanceled(_, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _) => if (debug) println("TestCanceled")
      case TestIgnored(_, _, _, _, _, _, _, _, _, _, _) => if (debug) println("TestIgnored")
      case TestPending(_, _, _, _, _, _, _, _, _, _, _, _, _) => if (debug) println("TestPending")
      case SuiteStarting(_, _, _, _, _, _, _, _, _, _) => if (debug) println("SuiteStarting")
      case SuiteCompleted(_, _, _, _, _, _, _, _, _, _, _) => if (debug) println("SuiteCompleted")
      case SuiteAborted(_, _, _, _, _, _, _, _, _, _, _, _, _) => if (debug) println("SuiteAborted")
      case InfoProvided(_, _, _, _, _, _, _, _, _) => if (debug) println("InfoProvided")
      case MarkupProvided(_, _, _, _, _, _, _, _) => if (debug) println("MarkupProvided")
      case AlertProvided(_, _, _, _, _, _, _, _, _) => if (debug) println("AlertProvided")
      case NoteProvided(_, _, _, _, _, _, _, _, _) => if (debug) println("NoteProvided")
      case _ => println("Some other thing happened!")
    }
  }
}
