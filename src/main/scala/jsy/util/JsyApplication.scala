package jsy.util

import java.io.{ByteArrayOutputStream, File}
import java.nio.file.Files

trait JsyApplication {
  import jsy.util.options
  
  var debug = false /* set to false to disable debugging output */
  var keepGoing = false /* set to true to keep going after exceptions */
  var maxSteps: Option[Int] = None /* set to a number to bound the number of execution steps */
  
  var anonOption = ("input",
    { case filename :: Nil => new File(filename) }: PartialFunction[List[String], File],
    "A file containing a JavaScripty program or a directory with .jsy files.")
    
  var flagOptions = List(
    ("debug", options.SetBool(b => debug = b, Some(b => debug == b)), "debugging"),
    ("keep-going", options.SetBool(b => keepGoing = b, Some(b => keepGoing == b)), "keep going after exceptions"),
    ("bound-steps", options.SetInt(
        { i => maxSteps = i },
        Some({ 
          case true => maxSteps map { i => ": %d".format(i) }
          case false => if (maxSteps == None) Some("") else None
        })),
     "bound for maximum number of execution steps before aborting")
  )
  
  def handle[T](default: T)(e: => T): T =
    if (!keepGoing) e
    else try e catch {
      case exn: Throwable => println(exn.toString); default
    }
    
  def processFile(file: File): Unit

  def testJsy(file: File)(k: (File, File, Unit => (Boolean, String)) => Unit) {
    val jsyext = """[.]jsy$""".r
    val ans: File = new File(jsyext.replaceAllIn(file.getPath, ".ans"))
    val assertion: Unit => (Boolean, String) = { _ =>
      if (!ans.exists()) {
        (false, s"Expected output file ${ans} does not exist.")
      }
      else {
        val outstream = new ByteArrayOutputStream()
        Console.withOut(outstream)(processFile(file))

        val encoding = java.nio.charset.StandardCharsets.UTF_8
        val ansstring = new String(Files.readAllBytes(ans.toPath), encoding)

        outstream.flush()
        val outstring = new String(outstream.toString(encoding.toString))
        outstream.close()

        (ansstring == outstring, s"Computed output does not match expected output.\nComputed:\n${outstring}\nExpected:\n${ansstring}")
      }
    }
    k(file, ans, assertion)
  }
  
  def isJsy(file: File): Boolean = {
    val jsyext = """[.]jsy$""".r
    jsyext findFirstIn file.getName match {
      case Some(_) => true
      case None => false
    }
  }

  def doFile(doit: File => Unit, file: File) = {
    def loop(file: File) {
      if (file.isFile) {
        doit(file)
      }
      else if (file.isDirectory) {
        file.listFiles filter { f => f.isDirectory || isJsy(f) } foreach loop
      }
      else {
        throw new IllegalArgumentException("File %s does not exist.".format(file))
      }
    }
    loop(file)
  }

  def test(fileordir: File)(k: (File, File, Unit => (Boolean, String)) => Unit) {
    doFile({ f => testJsy(f)(k) }, fileordir)
  }

  def main(args: Array[String]) {
    val opts = new options.Options("jsy", flagOptions, anonOption)
    val file: File = opts.process(args)
    doFile(processFile, file)
  }

}