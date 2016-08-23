/**
 *
 */
package jsy.lab1

import jsy.lab1.ast._
import scala.util.parsing.combinator._
import scala.util.parsing.input.{StreamReader}
import java.io.{InputStreamReader,FileInputStream}
import java.io.InputStream
import java.io.File
import scala.util.parsing.input.Position
import scala.util.parsing.input.NoPosition

trait JSTokens extends token.StdTokens {
  case class FloatLiteral(chars: String) extends Token {
    override def toString = chars
  }
}

class Lexer extends lexical.StdLexical with JSTokens {
  override def token: Parser[Token] =
    decimal ~ opt(exponent) ^^ {
      case dec ~ exp => FloatLiteral(List(Some(dec), exp).flatten.mkString)
    } |
    super.token
    
  def decimal: Parser[String] =
    rep1(digit) ~ opt('.' ~ rep(digit)) ^^ {
      case ws ~ fs =>
        List(Some(ws), fs map { mkList }).flatten.flatten.mkString
    }
  
  def exponent: Parser[String] =
    (accept('e') | accept('E')) ~ opt(accept('+') | accept('-')) ~ rep1(digit) ^^ { 
      case exp ~ sign ~ digits =>
        List(Some(List(exp)), sign map { List(_) }, Some(digits)).flatten.flatten.mkString
    }
}


trait TokenParser extends syntactical.StdTokenParsers {
  type Tokens = JSTokens
  val lexical = new Lexer
  
  import lexical.FloatLiteral
  
  def floatLit: Parser[String] =
    elem("float", _.isInstanceOf[FloatLiteral]) ^^ (_.chars)
}

object Parser extends TokenParser {
  /* Lexer Set Up */
  lexical.delimiters ++= List("(", ")", "-", "+", "*", "/")
  
  def prog: Parser[Expr] = expr

  def expr: Parser[Expr] = binary(0)
    
  val binaryOperators: Vector[List[(String, (Expr, Expr) => Expr)]] = {
    def createBinaryFunction(op: Bop): (Expr, Expr) => Expr =
      { Binary(op, _, _) }
    Vector() ++ List(
      List("+" -> createBinaryFunction(Plus),
           "-" -> createBinaryFunction(Minus)),
      List("*" -> createBinaryFunction(Times),
      	   "/" -> createBinaryFunction(Div))
    )
  }

  def binary(level: Int): Parser[Expr] =
    if (level >= binaryOperators.length)
      unary
    else
      binary(level + 1) * bop(level)

  def bop(level: Int): Parser[(Expr, Expr) => Expr] = {
    def doBop(opf: (String, (Expr, Expr) => Expr)): Parser[(Expr, Expr) => Expr] = {
      val (op, f) = opf
      withpos(op) ^^ { case (pos, _) => ((e1, e2) => f(e1, e2) setPos pos) }
    }
    val bopf0 :: bopfrest = binaryOperators(level)
    (doBop(bopf0) /: bopfrest)((acc, bopf) => acc | doBop(bopf))
  }

  def unary: Parser[Expr] =
    positioned(uop ~ unary ^^ { case op ~ e => op(e) }) |
    term

  def uop: Parser[Expr => Expr] =
    "-" ^^ (_ => (e: Expr) => Unary(Neg, e))

  def term: Parser[Expr] =
    positioned(
      floatLit ^^ (s => N(s.toDouble))
    ) |
    "(" ~> expr <~ ")" |
    failure("expected an expression")
    
  def withpos[T](q: => Parser[T]): Parser[(Position, T)] = Parser { in =>
    q(in) match {
      case Success(t, in1) => Success((in.pos,t), in1)
      case ns: NoSuccess => ns
    }
  }
  
  def withposrep[T](q: => Parser[T]): Parser[List[(Position,T)]] =
    rep(withpos(q))
    
  def withposrep1[T](q: => Parser[T]): Parser[List[(Position,T)]] =
    rep1(withpos(q))
    
  private var parseSource: String = "<source>"
    
  def formatErrorMessage(pos: Position, kind: String, msg: String): String =
    if (pos != NoPosition)
      "%s\n%s:%s:%s: %s\n%s".format(kind, parseSource, pos.line, pos.column, msg, pos.longString)
    else
      "%s\n%s: %s".format(kind, parseSource, msg)
    
  class SyntaxError(msg: String, next: Input) extends Exception {
    override def toString = formatErrorMessage(next.pos, "SyntaxError", msg)
  }
    
  def parseTokens(tokens: lexical.Scanner): Expr = {
    phrase(prog)(tokens) match {
      case Success(e, _) => e
      case NoSuccess(msg, next) => throw new SyntaxError(msg, next)
    }
  }
    
  /*** External Interface ***/
  
  def formatErrorMessage(e: Expr, kind: String, msg: String): String =
    formatErrorMessage(e.pos, kind, msg)
  
  def parse(s: String): Expr = {
    parseTokens(new lexical.Scanner(s))
  }
  
  def parse(in: InputStream): Expr = {
    val reader = StreamReader(new InputStreamReader(in))
    parseTokens(new lexical.Scanner(reader))
  }
  
  def parseFile(filename: String): Expr = {
    parseSource = filename
    parse(new FileInputStream(filename))
  }
  
  def parseFile(file: File): Expr = {
    parseSource = file.getName
    parse(new FileInputStream(file))
  }
}