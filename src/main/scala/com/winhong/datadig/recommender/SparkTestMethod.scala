package com.winhong.datadig.recommender

import java.text.SimpleDateFormat
import java.util.{Calendar, Date}

//import com.winhong.gzszyy.commons.redis.RedisReader
//import com.winhong.gzszyy.core.search.recommendation.service.RecommendService
//import com.winhong.gzszyy.core.search.recommendation.service.impl.RecommendServiceImpl
import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.recommendation.Rating
import org.apache.spark.{SparkContext, SparkConf}

/**
  * Created by root on 12/3/15.
  */
object SparkTestMethod {
  val keywordMap = scala.collection.mutable.Map("word" -> "00")
  val idWordMap = scala.collection.mutable.Map("00" -> "word")
  val startDate = "2015"

  def getNowDate():String={
    var now:Date = new Date()
    var dateFormat:SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd")
    var dateStr = dateFormat.format( now )
    dateStr
  }

  def getBefore30Date():String= {
    var dateFormat: SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd")
    var cal: Calendar = Calendar.getInstance()
    cal.add(Calendar.DATE, -30)
    var dateStr = dateFormat.format(cal.getTime())
    dateStr
  }

  def timeFormat(time:String):String={
    val sdf:SimpleDateFormat = new SimpleDateFormat("HH:mm:ss")
    val date:String = sdf.format(new Date((time.toLong*1000l)))
    date
  }

  def main(args: Array[String]): Unit = {

    var now:Date = new Date()
    var dateFormat:SimpleDateFormat = new SimpleDateFormat("HH:mm:ss")
    var dateStr = dateFormat.format( now )
    println(dateStr)
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

    val conf = new SparkConf
    //conf.setAppName("MovieLensALS").setMaster("local[5]")
    conf.setAppName("UserSearchALS").setMaster("local[5]")

    val sc = new SparkContext(conf)

    println("----------装载all user search数据，其中最后一列时间戳除 10 的余数作为 key ， Rating 为值；--------")

    val keywordSet = List(1,2,3,4,5)
    val reduceSample = sc.parallelize(keywordSet)

    reduceSample.foreach(println)


    sc.stop()
  }

}
