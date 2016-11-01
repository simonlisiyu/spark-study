package com.dtspark.test

import scala.collection.mutable.ArrayBuffer

/**
  * Created by root on 11/13/15.
  */
object ArrayMatrix {

  def main(args: Array[String]) {
    val nums = new Array[Int](10) //default 0
    val a = new Array[String](10) //default null
    val s = Array("Hello", "World")
    s(0) = "Goodbye"

    val b = ArrayBuffer[Int]()
    b += 1
    b += (1,2,3,5)
    b ++= Array(8,13,21)

    b.trimEnd(5)  //lost last 5 elem
    b.insert(2,6) //at position 2 insert 6
    b.insert(2,7,8,9) //at position 2 insert 7,8,9
    b.remove(2) //remove position 2 elem
    b.remove(2,3) //remove 3 elems from position 2
    b.toArray //new val = Array, Array can not += ...


    val c = Array(2,3,5,7,11)
    val result = for(elem <- c) yield 2*elem  //if without yield return Unit, result is null
    for(elem <- c if elem % 2 == 0) yield 2*elem
    c.filter(_%2 == 0).map(2*_) //same as up commond

    Array(1,7,2,9).sum
    ArrayBuffer("Mary","had","a","little","lamb").max

    val d = ArrayBuffer(1,7,2,9)
    val bSorted = d.sorted

    val e = Array(1,7,2,9)
    scala.util.Sorting.quickSort(e)

    e.mkString(" and ")
    e.mkString("<",",",">")

    val matrix = Array.ofDim[Double](3,4) //3 line 4 column
    matrix(2)(1) = 42
    val triangle = new Array[Array[Int]](10)
      for(i <- 0 until triangle.length)
        triangle(i) = new Array[Int](i+1)



  }
}
