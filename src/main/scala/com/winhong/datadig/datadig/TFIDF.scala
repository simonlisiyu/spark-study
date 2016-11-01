package com.winhong.datadig.datadig

import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.feature.{IDF, HashingTF}
import org.apache.spark.mllib.linalg
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkContext, SparkConf}
import java.sql.{Connection, DriverManager, ResultSet}

/**
  * Created by root on 11/12/15.
  */
object TFIDF {
  val keywordMap = scala.collection.mutable.Map(1 -> "name")

  def nonNegativeMod(x: Int, mod: Int): Int = {
    val rawMod = x % mod
    rawMod + (if (rawMod < 0) mod else 0)
  }

  def main(args: Array[String]) {
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

    val conf = new SparkConf
    conf.setAppName("App").setMaster("local")
    val sc = new SparkContext(conf)
//    val hadoopRdd = sc.textFile("hdfs://localhost:9000/spark-in/1.txt")
  val hadoopRdd = sc.textFile("hdfs://localhost:9000/spark-in/aaa")

    // Load documents (one per line).
    val documents: RDD[Seq[String]] = hadoopRdd.map(_.split(" ").toSeq)
    documents.foreach(w => {
      for(v <- w){
        keywordMap += (nonNegativeMod(v.##, 1 << 20) -> v)
//        keywordMap.foreach(println)
      }
    })

    val hashingTF = new HashingTF()
    val tf: RDD[linalg.Vector] = hashingTF.transform(documents)
    tf.cache()
    val idf = new IDF().fit(tf)
    val tfidf: RDD[linalg.Vector] = idf.transform(tf)
    val tfidfOfName = tfidf.partitions

    val mapFromRdd = tfidf.map(line => (line.toSparse.indices -> line.toSparse.values))
//    mapFromRdd.foreach(
//       n => println(n._1.toList +"\t" + n._2.toList)
//    )

    val customerMapRDD = mapFromRdd.map(
     line => {
       val changeToName = (x: Int) => keywordMap.getOrElse(x,0)
       val kList = line._1.map(changeToName)
       val nameMap = kList.toList.zip(line._2.toList)
       nameMap
     }

    )


    println("-----------------")
//    customerMapRDD.collect.foreach(println)
    customerMapRDD.saveAsTextFile("/root/abc")

    sc.stop()
  }

}
