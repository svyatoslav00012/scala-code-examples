package main.bankDbImmutable

import scala.annotation.tailrec
import scala.io.StdIn.readLine
import scala.util.Try

object IOHelpers {

  //only non-pure: (main, processInput, readInput)
  @tailrec
  def processInput(db: DB): Unit = {
    val input = readInput()
    val (output, newDB) = process(input, db)
    println(output)
    processInput(newDB)
  }

  def readInput(): (String, String, String) = {
    println("Enter from:")
    val from = readLine()
    println("Enter to:")
    val to = readLine()
    println("Enter amount:")
    val amount = readLine()
    (from, to, amount)
  }

  def process(input: (String, String, String), db: DB): (String, DB) =
    parseInput(input)
      .map((db.tryMakeTransfer _).tupled) match {
      case Right(updatedDb) => (updatedDb.toString, updatedDb)
      case Left(error) => (error, db)
    }

  def parseInput(input: (String, String, String)): Either[String, (String, String, Int)] =
    input match {
      case (from, to, amount) if from.isBlank || to.isBlank || amount.isBlank => Left("Wrong input, one of values is empty")
      case (_, _, amount) if Try(amount.toInt).isFailure => Left("Wrong input, amount is not integer")
      case (from, to, amount) => Right(from, to, amount.toInt)
    }

  def tab(multilineString: String): String = multilineString.split("\n").map("  " + _).mkString("\n")

}
