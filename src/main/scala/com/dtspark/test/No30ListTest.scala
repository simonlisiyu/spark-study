package com.dtspark.test

/**
  * Created by root on 12/8/15.
  */
object No30ListTest {
  def main(args: Array[String]) {
    val bigData = List("Hadoop" , "Spark")
    val data = List(1, 2, 3)

    val bigData_Core = "Hadoop" :: ("Spark" :: Nil)
    val data_Int = 1::2::3::Nil

    println(data.isEmpty)
    println(data.head)
    println(data.tail)
    println(data.tail.head)

    val List(a,b) = bigData
    println("a: "+a+"=== b: "+b)
    val x::y::rest = data
    println("x: "+x+",y: "+y+",rest: "+rest)

    val suffledData = List(6,4,5,6,2,9,1)
    println(sortList(suffledData))

    def sortList(list: List[Int]): List[Int] = list match {
      case List() => List()
      case head::tail => compute (head, sortList(tail))
    }

    def compute(data: Int, dataSet: List[Int]) : List[Int] = dataSet match {
      case List() => List(data)
      case head::tail => if(data <= head) data::dataSet
        else head::compute(data,tail)
    }

  }

}
