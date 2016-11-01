package com.dtspark.test

/**
  * Created by root on 11/14/15.
  */
class ApplyClass {

  def apply() = println("I am into Spark so much!!!")

  def haveATry{
    println("Have a try on apply!")
  }
}

object ApplyClass{
  def apply() = {
    println("I am into Scala so much!!!")
    new ApplyClass
  }
}

object ApplyOpertion {
//  def main(args: Array[String]) {
//    val array = Array(1,2,3,4,5)
//    val a = ApplyClass()
//    a.haveATry
//  }

  def main(args: Array[String]) {
    val a = new ApplyClass
    a.haveATry
    println(a())    //a() use create method
  }
}
