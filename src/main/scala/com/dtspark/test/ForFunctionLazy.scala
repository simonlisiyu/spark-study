package com.dtspark.test

import scala.io.Source

/**
  * Created by root on 11/13/15.
  */
object ForFunctionLazy {
  def main(args: Array[String]) {
    //i <- 1 to 2 continue give (1 to 2) to i
//    for(i <- 1 to 2; j <- 1 to 2) print ((100*i+j)+" ")
//    println

    //if decide print
//    for(i <- 1 to 2; j <- 1 to 2 if i != j) print ((100*i+j)+" ")
//    println

    //def is function, could have name(addA), or not have name (x : Int) => x +200
//    def addA(x : Int) = x + 100
//    val add = (x : Int) => x +200
//    println("The result from a function is : "+addA(2))
//    println("The result from a vla is : " + add(2))

    //recusive, must have return type
//    def fac(n:Int):Int = if(n <= 0) 1 else n*fac(n-1)
//    println("The result from a fac is : "+fac(10))

    //def function have three args
//    def combine(content:String, left:String = "[", right: String = "]") = left + content + right
//    println("The result from a combine is : "+combine("I love Spark!"))
//    println("The result from a combine is : "+combine("I love Spark!", "<<"))

    //def function have changely args
//    def connected(args: Int*) = {
//      var result = 0
//      for(arg <- args) result += arg
//      result
//    }
//    println("The result from a connected is : "+connected(1,2,3,4,5))


    //lazy will be run while val or var be used
    lazy val file = Source.fromFile("/root/Desktop/aaa1")

    println("Scala")
    for(line <- file.getLines) println(line)
  }

}
