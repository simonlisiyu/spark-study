package com.winhong.datadig.datadig

import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.feature.{IDF, HashingTF}
import org.apache.spark.mllib.linalg
import org.apache.spark.mllib.linalg.SparseVector
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkContext, SparkConf}

/**
  * Created by root on 11/12/15.
  */
object TF {

  def main(args: Array[String]) {
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

    val conf = new SparkConf
    //conf.setAppName("App").setMaster("spark://Slave-3:7077")
    conf.setAppName("App").setMaster("local[5]")
    val sc = new SparkContext(conf)
    //sc.addJar("/root/IdeaProjects/datadig/out/artifacts/datadig/datadig.jar")

//    val hadoopRdd = sc.textFile("hdfs://localhost:9000/spark-in/1.txt")
    val hadoopRdd = sc.textFile("hdfs://localhost:9000/spark-in/aaa")

    //    val hadoopRdd = new HadoopRDD(sc,conf, classOf[SequenceFileInputFormat[Text,Text]], classOf[Text], classOf[Text],1)

    // Load documents (one per line).
    val documents: RDD[Seq[String]] = hadoopRdd.map(_.split(" ").toSeq)
    println("documents:"+documents)

    val hashingTF = new HashingTF()
    val tf: RDD[linalg.Vector] = hashingTF.transform(documents)
    tf.foreach(println)
    println()

    tf.cache()
    val idf = new IDF().fit(tf)
//    val idf = new IDF(minDocFreq = 2).fit(tf)
    val tfidf: RDD[linalg.Vector] = idf.transform(tf)

    tfidf.foreach(println)
    println()

//    tfidf.foreach(_.toSparse.indices.foreach(println))

//    val mapFromRdd = tfidf.map(line => (line.toSparse.indices -> line.toSparse.values))
//    for((k,v) <- mapFromRdd){
//      k.foreach(println)
//      v.foreach(println)
//    }
//    tfidf.foreach(_.toSparse.indices.toList.foreach(println))
//    val indices = tfidf.foreach(_.toSparse.indices.copyToArray(indices))
//    tfidf.foreach(_.toSparse.values.foreach(println))
//    val values = tfidf.foreach(_.toSparse.values)
//    println(indices)


    val idfTransformed = tfidf.zip(documents)map(t => {
      (t._2,t._1)
    })
//    idfTransformed.foreach(println)





//    val wcRdd = hadoopRdd.map(x => (x, 1))
//    val result = wcRdd.reduceByKey(_+_)
//    result.foreach(print)
//
//    val sorted = result.map{
//      case (key,value) => (value,key)
//    }.sortByKey(true,1)
//    val topN = sorted.top(3).map{s => (s._2,s._1)}
//    topN.foreach(println)


    sc.stop()
  }

}
