package com.dtspark.test

/**
  * Created by root on 11/16/15.
  */
object RegExpressOps {
  def main(args: Array[String]) {
    val regex = """([0-9]+) ([a-z]+)""".r     //three " not need such as \" something
    val numPattern = "[0-9]+".r
    val numberPattern = """\s+[0-9]+\s""".r

    // findAllIn method return all match regex things
    for(matchString <- numPattern.findAllIn("x99345y Scala, 2d2298 Spark")) println(matchString)

    // findFirstIn method return first one match regex
    println(numberPattern.findFirstIn("99ss java, 222 hadoop"))

    val numitemPattern = """([0-9]+) ([a-z]+)""".r
    val numitemPattern(num, item) = "99 hadoop"

//    val line = "93459 spark"
    val line = "dd8dd spark"
    line match{
      case numitemPattern(num, blog) => println(num+"\t"+blog)
      case _ => println("Oops...")
    }


  }

}
