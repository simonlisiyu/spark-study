package com.winhong.datadig.datadig

import org.apache.hadoop.io.Text
import org.apache.hadoop.mapred.SequenceFileInputFormat
import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.feature.{IDF, HashingTF}
import org.apache.spark.mllib.linalg
import org.apache.spark.rdd.{RDD, HadoopRDD}
import org.apache.spark.{SparkContext, SparkConf}

/**
  * Created by root on 11/12/15.
  */
object App {
  val articleMap = scala.collection.mutable.Map("n a m e" -> "name")
  val tfMap = scala.collection.mutable.Map("a m e n" -> "n a m e")
  val keywordMap = scala.collection.mutable.Map(1 -> "name")

  def changeToName = (x: Int) => keywordMap.getOrElse(x, "0")

  def nonNegativeMod(x: Int, mod: Int): Int = {
    val rawMod = x % mod
    rawMod + (if (rawMod < 0) mod else 0)
  }

  def main(args: Array[String]) {
    println("Hello World!")
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

    val conf = new SparkConf
    //conf.setAppName("App").setMaster("spark://Slave-3:7077")
    conf.setAppName("App").setMaster("local[2]")
    val sc = new SparkContext(conf)
    //sc.addJar("/root/IdeaProjects/datadig/out/artifacts/datadig/datadig.jar")

    val hadoopRdd = sc.textFile("hdfs://localhost:9000/sqoop/input/article/part-m-00000")
//    val hadoopRdd = sc.textFile("hdfs://localhost:9000/sqoop/input/article/bbb")
    /*.map(x => {
//      println(x)
//      i = i+1
//      println(i)
      val a = "id="+x.split(",",2)(0)+"id="
      val b = x.split(",",2)(1).replaceAll(", "," ")
      (a+" "+b)
    })*/

//    val hadoopRdd = sc.textFile("hdfs://localhost:9000/spark-in/aaa")

    hadoopRdd.foreach(println)
//    val documents: RDD[Seq[String]] = hadoopRdd.map(_.split(" ").toSeq)
//    documents.foreach(w => {
//      for (v <- w) {
//        keywordMap += (nonNegativeMod(v.##, 1 << 20) -> v)
//      }
//    })
//
//    for(c <-  hadoopRdd.map(x => {x}).toArray()){
//      val aRdd = sc.parallelize(List(c))
//      val documents: RDD[Seq[String]] = aRdd.map(_.split(" ").toSeq)
//      val hashingTF = new HashingTF()
//      val tf: RDD[linalg.Vector] = hashingTF.transform(documents)
//      val indices = tf.first().toSparse.indices.map(changeToName)
//      val k = indices.mkString(" ")
//      tfMap += (k -> c)
//    }
//
//
//
//    tfMap.foreach(println)
//    println("-------------------")
//    articleMap.foreach(println)


//    val tfMapFromRdd = tf.map(line => (line.toSparse.indices -> line.toSparse.values))
//    val tfMapRDD = tfMapFromRdd.map(
//      line => {
//        val changeToName = (x: Int) => keywordMap.getOrElse(x, "0")
//        val kList:Array[String] = line._1.map(changeToName)
//        val nameMap = kList.toList.zip(line._2.toList)
//        nameMap
//      }
//    )
//
//    tfMapRDD.foreach(println)

//    println(articleMap.get("太阳 病 发热 汗 出 恶风 脉 缓 者 名为 中风"))

//    hadoopRdd.map(x => {
//      val docRdd = sc.parallelize(x._1.split(" "))

//
//    })


//    val wcRdd = hadoopRdd.map(x => (x, 1))
//    val result = wcRdd.reduceByKey(_+_)
//    result.foreach(print)
//
//    val sorted = result.map{
//      case (key,value) => (value,key)
//    }.sortByKey(true,1)
//    val topN = sorted.top(2)
//    topN.foreach(println)


    sc.stop()
  }

}
