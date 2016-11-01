package com.winhong.datadig.datadig

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkContext, SparkConf}


/**
  * Created by root on 11/14/15.
  */
object MLlibTest {

  def nonNegativeMod(x: Int, mod: Int): Int = {
    val rawMod = x % mod
    rawMod + (if (rawMod < 0) mod else 0)
  }

//  def main(args: Array[String]) {
//
//    println(nonNegativeMod("ls".##, 1 << 20))
//    println(1 << 20)
//  }

  def main(args: Array[String]) {
    val conf = new SparkConf
    //conf.setAppName("App").setMaster("spark://Slave-3:7077")
    conf.setAppName("App").setMaster("local")
    val sc = new SparkContext(conf)
    //sc.addJar("/root/IdeaProjects/datadig/out/artifacts/datadig/datadig.jar")

//    val hadoopRdd = sc.textFile("hdfs://localhost:9000/spark-in/aaa")
    val hadoopRdd = sc.textFile("hdfs://localhost:9000/spark-in/1.txt")

    //    val hadoopRdd = new HadoopRDD(sc,conf, classOf[SequenceFileInputFormat[Text,Text]], classOf[Text], classOf[Text],1)

    // Load documents (one per line).
    val documents: RDD[Seq[String]] = hadoopRdd.map(_.split(" ").toSeq)
    documents.foreach(w => {
      for(v <- w){
        print(v)
        println(nonNegativeMod(v.##, 1 << 20))
      }

    })

//    documents.foreach(w => {
//      w.foreach(println)
//    })

    sc.stop()
  }
}
