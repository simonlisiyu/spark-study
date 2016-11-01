package com.dtspark.test

import java.io.{File, PrintWriter}

import scala.io.Source

/**
  * Created by root on 11/13/15.
  */
object FileOps {

  def main(args: Array[String]) {
//    val file = Source.fromFile("/root/Desktop/aaa")
//
//    for(line <- file.getLines()){
//      println(line)
//    }
    //
    //    file.close

//    val file = Source.fromURL("http://spark.apache.org/")
//    file.foreach(print)
//
//    file.close

//    val writer = new PrintWriter(new File("scalaFile.txt"))
//    for(i <- 1 to 100) writer.println(i)
//    writer.close

    print("Please enter your input : ")
//    val line = Console.readLine
    val line = readLine
    println("Thanks, you just typed : "+line)


  }
}
