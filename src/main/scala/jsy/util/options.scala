package jsy.util

object options {
  sealed abstract class Spec
  case class SetBool(setter: Boolean => Unit, default: Option[Boolean => Boolean]) extends Spec
  case class SetInt(setter: Option[Int] => Unit, default: Option[Boolean => Option[String]]) extends Spec

  class Options[T](program: String, specs: List[(String, Spec, String)], anon: (String, PartialFunction[List[String], T], String)) {
    val opts: Map[String, List[String] => Option[List[String]]] =
      (Map[String, List[String] => Option[List[String]]]() /: specs)((acc, spec) => spec match {
        case (name, SetBool(setter, _), _) =>
          acc +
          (("--" + name) -> { (t: List[String]) => setter(true); Some(t) }) +
          (("--no-" + name) -> { (t: List[String]) => setter(false); Some(t) })
        case (name, SetInt(setter, _), _) =>
          acc +
          (("--" + name) -> { (t: List[String]) => t match {
            case arg :: t =>
              val argp: Int = try arg.toInt catch { case _: Throwable => usageErr() }
              setter(Some(argp)); Some(t)
            case _ => usageErr()
          } }) +
          (("--no-" + name) -> { (t: List[String]) => setter(None); Some(t) })
      })
      
    val (anonName, anonDo, anonDesc) = anon
      
    val nameWidth: Int = opts.foldLeft(anonName.length){ case (acc, (n, _)) => acc max (n.length + 5) }
    
    def padRight(s: String, w: Int): String = {
      val nspaces = (w - s.length) max 0
      s + (" " * nspaces)
    }
    
    def optline(name: String, text: String): String = {
      "%-2s".format("") + padRight(name, nameWidth) + "  %s%n".format(text)
    }
      
    val descriptions: String = {
      (specs :\ "")((spec, acc) => spec match {
        case (name, SetBool(_, default), desc) => {
          def defaultStr(b: Boolean): String =
            default map (f => if (f(b)) " (default)" else "") getOrElse("") 
          optline("--" + name, "turn on %s".format(desc) + defaultStr(true)) +
          optline("--no-" + name, "turn off %s".format(desc) + defaultStr(false)) +
          acc
        }
        case (name, SetInt(_, default), desc) => {
          def defaultStr(b: Boolean): String = {
            val opt =
              for {
                f <- default
                s <- f(b)
              } yield " (default%s)".format(s)
            opt.getOrElse("")
          }
          optline("--" + name + " <int>", "set %s".format(desc) + defaultStr(true)) +
          optline("--no-" + name, "unset %s".format(desc) + defaultStr(false)) +
          acc
        }
      })
    }
    
    val header =
      """
Usage: %s [options] %s
        
""".format(program, anonName) +
optline(anonName, anonDesc) + """
Options:
"""

    private var currentArgs: List[String] = List()

    def usageErr(): Nothing = {
      val unprocessed = currentArgs match {
        case Nil => ""
        case _ => """
Unprocessed Arguments:
  """ + currentArgs.reduceRight({ _ + " " + _ }) + "%n".format()
      }
      print(header + descriptions + unprocessed)
      sys.exit(1)
    }
    
    def process(args: Array[String]): T = {
      def loop(l: List[String]): List[String] = {
        currentArgs = l
        l match {
          case Nil => Nil
          case h :: t => opts.get(h) match {
            case None => l
            case Some(doit) => { val tp = doit(t).getOrElse(t); loop(tp) }
          }
        }
      }
      val err: PartialFunction[List[String], T] = { case _ => usageErr() }
      (anonDo orElse err)(loop(args.toList))
    }
  }
}