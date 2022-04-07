package main.streams

import scala.io.{BufferedSource, Source}
import scala.util.Random

object ScalaStreams extends App {

  def withSource[T](fileName: String)(p: BufferedSource => T): T = {
    val source = Source.fromFile(fileName)
    val res = p(source)
    source.close()
    res
  }

  //not actually streams, but an example of lazy file processing
  def processFile(fileName: String): Unit = withSource(fileName) { source =>
    source.getLines().foreach(line =>
      if (Random.nextDouble() < 0.1)
        println(line)
    )
  }

  //processFile(someFile)

}
