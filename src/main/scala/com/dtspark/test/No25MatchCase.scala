package com.dtspark.test

/**
  * Created by root on 12/7/15.
  */
object No25MatchCase {
  def main(args: Array[String]) {
    val data = 2
    data match {
      case 1 => println("first")
      case 2 => println("Second")
      case _ => println("not known number")
    }

    val result = data match {
      case i if i == 1 => "the first"
      case number if number == 2 => "the second"
      case _ => "not not known number"
    }
    println(result)

    "Spark !" foreach {
      c => println (
      c match {
        case ' ' => "space"
        case ch => "Char: "+ch
      }
      )
    }

    "Spark !".foreach {
      c => println (
        c match {
          case ' ' => "space"
          case ch => "Char: "+ch
        }
      )
    }

    def match_type(t : Any) = t match {
      case p:Int => println("Integer")
      case p:String => println("String")
      case m:Map[_,_] => m.foreach(println)
      case _ => println("unknown type!")
    }

    match_type(2)
    match_type(Map("scala" -> "spark"))

    def match_array(arr:Any) = arr match {
      case Array(0) => println("Array"+"0")
      case Array(x,y) => println("Array"+x+" "+y)
      case Array(0,_*) => println("Array"+"0 ...")
      case _ => println("something else")
    }

    match_array(Array(0))
    match_array(Array(0,1))
    match_array(Array(0,1,2,3,4,5))

    def match_list(lst : Any) = lst match {
      case 0 :: Nil => println("List: "+"0")
      case x :: y :: Nil => println("List: "+x+" "+y)
      case 0 :: tail => println("List: "+"0 ...")
      case head :: 1 :: tail => println("List: "+"*,1,*")
      case _ => println("something else")
    }

    match_list(List(0))
    match_list(List(0,1))
    match_list(List(0,1,2,3,4,5))
    match_list(List(1,1,3))

    def match_tuple(tuple:Any) = tuple match {
      case (0,_) => println("Tuple:" + "0")
      case (x,0) => println("Tuple:"+x)
      case _ => println("something else")
    }

    match_tuple((0,"Scala"))
    match_tuple((2,0))
    match_tuple((0,1,2,3,4,5))

  }



}
