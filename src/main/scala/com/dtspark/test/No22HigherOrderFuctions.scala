package com.dtspark.test

import breeze.numerics.{sqrt, ceil}


/**
  * Created by root on 12/4/15.
  */
object No22HigherOrderFuctions {

  def main(args: Array[String]) {
    (1 to 9).map("*" * _).foreach(println _)
    (1 to 9).filter(_ % 2 == 0).foreach(println)
    println((1 to 9).reduceLeft(_ * _))
    "Spark is the most exciting thing happening in big data today".split(" ").
      sortWith(_.length < _.length).foreach(println)

//    val fun = ceil _
    val num = 3.14
//    fun(num)
//    Array(3.14, 1.42, 2.0).map(fun)

    def high_order_functions(f: (Double) => Double) = f(0.25)
    println(high_order_functions(sqrt(_)))
    println(high_order_functions(ceil.apply(_)))

    def mulBy(factor: Double) = (x: Double) => factor * x
    val quintuple = mulBy(5)
    println(quintuple(20))

  }

}
