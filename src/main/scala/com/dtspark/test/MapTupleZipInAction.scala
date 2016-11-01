package com.dtspark.test

/**
  * Created by root on 11/13/15.
  */
object MapTupleZipInAction {
  def main(args: Array[String]) {
    val map = Map("book" -> 10,"gun"->18,"ipad"->1000)
    for((k,v) <- map) yield (k,v * 0.9)

    val scores = scala.collection.mutable.Map("Scala" -> 7,"Hadoop" -> 8,"Spark" -> 10)
    val hadoopScore = scores.getOrElse("Hadoop",0)  //success get the key's value or fail return 0
    scores += ("R" -> 9)  //insert new k,v to mutable.Map
    scores -= "Hadoop"  //del one k,v from mutable.Map

    val sortedScore = scala.collection.immutable.SortedMap("Scala" -> 7,"Hadoop" -> 8,"Spark" -> 10)    //Map sort by key

    val tuple = (1,2,3.14,"Rocky","Spark")
    val thirdv = tuple._3
    val (first,second,third,fourth,fifth) = tuple
    val(f,s,_,_,_) = tuple

    "Rocky Spark".partition(_.isUpper)    //partition apart word, isUpper get word like "R"

//    val symbols = Array("[","-","]")
//      val counts = Array(2,5,2)
//      val pairs = symbols.zip(counts)   //join
//      val pairss = pairs.unzip
//    for((y,x) <- pairs) Console.print(y*x)    //print x, y times.
//    println()
//    println(pairs)
//    println(pairss)


    val a = List(3,6,5)
    val b = a.+:(2)
    val c = b.:+(7)
    println(c)

    val symbols = Array(3,6,5)
    val counts = Array(2,5,2)
    val pairs = symbols.zip(counts)   //join
    val pairss = pairs.unzip
    for((y,x) <- pairs) Console.print(y*x)    //print x, y times.
    println()
    println(pairs)
    println(pairss)

  }

}
