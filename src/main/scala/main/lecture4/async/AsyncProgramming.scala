package main.lecture4.async

import cats.effect.{IO, IOApp}
import cats.implicits.catsSyntaxParallelSequence1

object AsyncProgramming extends IOApp.Simple {

  def hardwork(x: Long): IO[Long] = IO {
    def fib(n: Long): Long =
      n match {
        case 0 => 1
        case 1 => 1
        case _ => fib(n - 1) + fib(n - 2)
      }

    fib(x)
  }

  implicit class SmartIO[T](x: IO[T]){
    def debug: IO[T] = x.map { r =>
      println(s"$r (${Thread.currentThread().getId})")
      r
    }
  }

  override def run: IO[Unit] = {
    List(43, 41, 45, 46, 42).map(_.toLong)
      .map(n => hardwork(n).map(n -> _))
      .map(_.debug)
      .parSequence
      .debug
      .void
  }
}
