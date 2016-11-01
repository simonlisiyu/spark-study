package com.dtspark.test

/**
  * Created by root on 11/12/15.
  */
object TupleArrayMap {
  def main(args: Array[String]) {
    val pari = (100,"Scala","Spark")
    println(pari._1)
    println(pari._2)

    val ages = Map("Rocky" -> 27,"Spark" -> 5)
    for((k,v) <- ages){
      println("Key is "+k+",Value is "+v)    }
    for((k,_) <- ages){
      println("Key is "+k)
    }



  }

}
