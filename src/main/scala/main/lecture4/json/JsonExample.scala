package main.lecture4.json

import io.circe.generic.auto._
import io.circe.parser
import io.circe.syntax.EncoderOps

object JsonExample extends App {

  //1. string -> json
  val stringJson =
    """{
      |"name": "Svyat",
      |"age": 21,
      |"info": {
      |   "email": "example@email.com",
      |   "skills": ["Scala", "JS", "CI-CD"]
      |}
      |}""".stripMargin
  val maybeJson = parser.parse(stringJson)
  println("parsed json: " + maybeJson)
  //2. json -> case class
  case class Info(email: String, skills: List[String])
  case class Coder(name: String, age: Int, info: Info)

  val coder = maybeJson.flatMap(json => json.as[Coder])

  println("parsed case class: " + coder)

  //3. case class -> json
  val againJson = coder.map(_.copy(age=30).asJson)
  println("case class to json: " + againJson)

  //4. json -> string
  println("json to string (no spaces)" + againJson.map(_.noSpaces))
}
