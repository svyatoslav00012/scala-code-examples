package main.textAnalytics

import scala.io.{BufferedSource, Source}

object TextAnalyser {

  def main(args: Array[String]): Unit = {
    val tinyWiki = "tinywiki.xml"
    val text = withSource(tinyWiki)(_.take(1*1024*1024).mkString)
    println(s"Total words: ${text.words.length}")
    text.wordsStats(List("idid"))
      .map{case (word, cnt, perc) => s"$word  $cnt  $perc%"}
      .foreach(println)
  }

  def withSource[T](fileName: String)(p: BufferedSource => T): T = {
    val source = Source.fromFile(fileName)
    val res = p(source)
    source.close()
    res
  }

  implicit class SmartMap[A, B](m: Map[A, B]){
    def mapVals[C](p: B => C): Map[A, C] = m.map{
      case (key, value) => key -> p(value)
    }
  }

  implicit class SmartText(text: String){
    def analyze(text: String): Unit = {
    }

    def cntLines: Long = text.count(_ == '\n')

    def search(query: String): List[(Int, String)] = {
      val lowerCaseQuery = query.toLowerCase
      text
        .split("\n")
        .zipWithIndex
        .filter(_._1.toLowerCase.contains(lowerCaseQuery))
        .map{case (row, ind) => (ind, row)}
        .toList
    }

    def wordsStats(ignoreWords: List[String] = Nil): List[(String, Int, Double)] = {
      val allWords = words
      val filteredWords = allWords.filter(s => ignoreWords.map(_.toLowerCase).contains(s.toLowerCase))
      filteredWords
        .groupBy(w => w)
        .values
        .map(words => (words.head, words.length, words.length * 100.0 / allWords.length))
        .toList
        .sortBy(-_._2)
    }

    def words: List[String] = text
      .collect{
        case char if char.isSpaceChar || char.isLetter => char.toLower
      }
      .split("\\s+")
      .filter(_.nonEmpty)
      .toList
  }

}
