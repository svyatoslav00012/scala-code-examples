package main.lecture3.streams

import cats.effect._
import fs2.io.file.{Files, Path}
import fs2.{Pipe, Stream, text}

import java.nio.file.Paths
import scala.concurrent.duration.{DurationInt, FiniteDuration}

object StreamProcessing extends IOApp.Simple {

  val tinyWiki = "tinywiki.xml"

  val MB: Long = 1024 * 1024

  override def run: IO[Unit] = {
//    printFile(smallWiki)
    searchInFile(tinyWiki, "ukraine")
//    countLines(smallWiki)
//    copyPartOfFile(smallWiki, tinyWiki, 0, 100*MB)
  }


  //tasks

  def printFile(path: String): IO[Unit] = {
    lines(path)
      .through(printLineByLine(500.millis))
      .compile.drain
  }


  //(1276298894,84318712444)
  def countLines(path: String): IO[Unit] = {
    lines(path)
      .fold((0L, 0L)){case ((cnt, bytes), cur) =>
        if(bytes % (100*MB) < 100)
          println(cnt, bytes / MB)
        (cnt + 1, bytes + cur.length)
      }
      .evalMap(IO.println)
      .compile.drain
  }



  def searchInFile(path: String, searchFor: String): IO[Unit] = {
    lines(path)
      .through(fullTextSearch(searchFor))
      .map{case (str, ind) => s"$ind). $str"}
      .through(printLineByLine(300.millis))
      .compile.drain
  }

  def copyPartOfFile(src: String, dst: String, from: Long, amount: Long): IO[Unit] = {
    streamBytes(src)
      .drop(from)
      .take(amount)
      .through(Files[IO].writeAll(Path.fromNioPath(Paths.get(dst))))
      .compile.drain
  }

//  //helpers
//  val lines = 1000000000
//
//  def write(path: String): IO[Unit] = IO {
//    val p = new java.io.PrintWriter(path)
//    try {
//      (1 to lines).foreach(i => p.println(s"$i I'm a long line number $i"))
//    } finally {
//      p.close()
//    }
//  }
//
//  //transformations
//  def justGoThroughFile(filePath: String): Stream[IO, Unit] =
//      lines(filePath)
//      .map(_.trim.takeWhile(!_.isSpaceChar).toInt)
//      .filter(_ % (lines / 100) == 0)
//      .map(_ / (lines / 100))


  def printLineByLine(freq: FiniteDuration): Pipe[IO, String, Unit] = src =>
      src.evalMap(line => IO.println(line) >> IO.sleep(freq))

  def fullTextSearch(searchFor: String): Pipe[IO, String, (String, Long)] = src =>
    src.zipWithIndex.filter(_._1.toLowerCase.contains(searchFor.toLowerCase))

  def lines(filePath: String): Stream[IO, String] = streamBytes(filePath)
    .through(text.utf8.decode)
    .through(text.lines)

  def streamBytes(filePath: String): Stream[IO, Byte] = Files[IO].readAll(Path.fromNioPath(Paths.get(filePath)))


}
