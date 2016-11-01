package com.winhong.datadig.recommender

import java.io.File

import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.recommendation.{ALS, MatrixFactorizationModel, Rating}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkContext, SparkConf}

import scala.io.Source

/**
  * Created by root on 12/4/15.
  */
object MyMovieLensALS {

  def loadRatings(path : String): Seq[Rating] = {
    val lines = Source.fromFile(path).getLines()
    val ratings = lines.map { line =>
      val fields = line.split("\t")
      //println("fields(0) = " + fields(0).toInt +"\t fields(1) = " + fields(1).toInt +"\t fields(2)="+ fields(2).toDouble)
      Rating(fields(0).toInt, fields(1).toInt, fields(2).toDouble)
    }.filter(r => r.rating > 0.0)

    if (ratings.isEmpty) {
      sys.error("No ratings provided.")
    } else {
      ratings.toSeq
    }
  }



  //校验预测数据和实际数据之间的方根误差
  def computeRmse(model: MatrixFactorizationModel, data:RDD[Rating], n:Long) :Double = {
    val predictions: RDD[Rating] = model.predict(data.map(x => (x.user, x.product)))

    val predictionsAndRatings = predictions.map(x => ((x.user, x.product), x.rating))
      .join(data.map(x => ((x.user, x.product), x.rating)))
      .values
    math.sqrt(predictionsAndRatings.map(x => (x._1 - x._2) * (x._1 - x._2)).reduce(_ + _) / n)
  }


  def main(args: Array[String]) {
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

    val conf = new SparkConf
    //conf.setAppName("MovieLensALS").setMaster("local[5]")
    conf.setAppName("MovieLensALS").setMaster("local[5]")

    val sc = new SparkContext(conf)

//    val movieLensHomeDir = "hdfs://localhost:9000/spark-in"
//    val textFile = sc.textFile(new File(movieLensHomeDir, "u1.test").toString)  //uid mid rate time

    println("----------装载电影目录对照表（电影 ID-> 电影标题）；--------")
    //装载电影目录对照表（电影 ID-> 电影标题）
    val moviesRDD = sc.textFile("hdfs://localhost:9000/spark-in/u.item").map{
      line =>
        val fields = line.split("\\|")
        (fields(0).toInt, fields(1))
    }
    println("moviesRDD.count(): "+moviesRDD.count())
    val movies = moviesRDD.collect().toMap
    println("movies: "+movies.size)
    println("----------装载电影目录对照表（电影 ID-> 电影标题）；--------")



    println("----------装载用户评分，该评分有评分器生成--------")
/*    val textFile = sc.textFile("hdfs://localhost:9000/spark-in/u1.test")  //uid mid rate time
    //extract (userid, movieid, rate)dd
    val ratings = textFile.map(_.split("\t") match {
      case Array(uid, mid, rate, time) => Rating(uid.toInt, mid.toInt, rate.toDouble)
    }).filter(r => r.rating > 0.0)*/
    val myRatings = loadRatings("/root/file/ml-100k/u1.my")
    myRatings.foreach(println(_))
    println("---------------------------------")
    val myRatingsRDD = sc.parallelize(myRatings,1)
    myRatingsRDD.foreach(println)
    println("----------装载用户评分，该评分有评分器生成--------")


    /*var m = 1
    ratings.foreach{ r =>
      println("%2d".format(m) + "\t user = " + r.user +"\t product = " + r.product +"\t rating = " +r.rating)
      m+=1
    }
    m+=1
    println("count : "+m)*/


    println("----------装载样本评分数据，其中最后一列时间戳除 10 的余数作为 key ， Rating 为值；--------")
    val textSample = sc.textFile("hdfs://localhost:9000/spark-in/u.data").map(_.split("\t") match {
      case Array(uid, mid, rate, time) => (time.toLong % 10, Rating(uid.toInt, mid.toInt, rate.toDouble))
    })

    val numRatins = textSample.count()
    val numUsers = textSample.map(_._2.user).distinct().count()
    val numMovies = textSample.map(_._2.product).distinct().count()

    //val numTotalUsers = textSample.map(_._2.user).distinct().count()

    println("Got " + numRatins +" ratings from " + numUsers +" users on " + numMovies +" movies")
    println("----------装载样本评分数据，其中最后一列时间戳除 10 的余数作为 key ， Rating 为值；--------")


    println("----------将样本评分表以 key 值切分成 3 个部分，分别用于训练 (60% ，并加入用户评分 ), 校验 (20%), And 测试 (20%)；--------")
    //将样本评分表以 key 值切分成 3 个部分，分别用于训练 (60% ，并加入用户评分 ), 校验 (20%), and 测试 (20%)
    val numPartitions = 4
    val training = textSample.filter(x => x._1 < 6)
      .values
      .union(myRatingsRDD) // 注意 ratings 是 (Int,Rating) ，取 value 即可
      .repartition(numPartitions)
      .cache()
    val validation = textSample.filter(x => x._1 >= 6 && x._1 < 8)
      .values
      .repartition(numPartitions)
      .cache()
    val test = textSample.filter(x => x._1 >= 8).values.cache()


    //calculator each totally counter's value
    val numTraining = training.count()
    val numValidation = validation.count()
    val numTest = test.count()

    println("Training: " + numTraining + ", validation: " + numValidation + ", test: " + numTest)
    println("----------将样本评分表以 key 值切分成 3 个部分，分别用于训练 (60% ，并加入用户评分 ), 校验 (20%), And 测试 (20%)；--------")


    println("----------训练不同参数下的模型，并再校验集中验证，获取最佳参数下的模型----")
    //训练不同参数下的模型，并再校验集中验证，获取最佳参数下的模型
    //------------------------------------validate best model for spark mlib start----------------------------------------
    /*val ranks = List(8, 12)
    val lambdas = List(0.1, 10.0)
    val numIters = List(10, 20)
    */
    val ranks = List(8, 12)
    val lambdas = List(0.1, 10.0)
    val numIters = List(3)
    var bestModel: Option[MatrixFactorizationModel] = None
    var bestValidationRmse = Double.MaxValue
    var bestRank = 0
    var bestLambda = -1.0
    var bestNumIter = -1
    for (rank <- ranks; lambda <- lambdas; numIter <- numIters) { //for 8 loop
    val model = ALS.train(training, rank, numIter, lambda)

      val validationRmse = computeRmse(model, validation, numValidation)
      //print following information as below
      println("RMSE (validation) = " + validationRmse + " for the model trained with rank = "
        + rank + ", lambda = " + lambda + ", and numIter = " + numIter + ".")
      if (validationRmse < bestValidationRmse) {
        bestModel = Some(model)
        bestValidationRmse = validationRmse
        bestRank = rank
        bestLambda = lambda
        bestNumIter = numIter
      }

    }
    println("----------训练不同参数下的模型，并再校验集中验证，获取最佳参数下的模型----")
    //------------------------------------validate best model for spark mlib end----------------------------------------


    println("----------用最佳模型预测测试集的评分，计算和实际评分之间的均方根误差----")
    //，计算和实际评分之间的均方根用最佳模型预测测试集的评分误差
    val testRmse = computeRmse(bestModel.get, test, numTest)

    println("The best model was trained with rank = " + bestRank +" and lamdba = " + bestLambda
      +", and numIter = " + bestNumIter +", and its RMSE on the test set is " + testRmse +".")
    println("----------用最佳模型预测测试集的评分，计算和实际评分之间的均方根误差----")


    //-------------------performance consideration as below----------------
    //create a native baseline and compare it with the best model
    val meanRating = training.union(validation).map(_.rating).mean
    val baselineRmse = math.sqrt(test.map(x => (meanRating - x.rating) * (meanRating - x.rating)).mean)
    val improvement = (baselineRmse - testRmse) / baselineRmse * 100
    println("improvement=>"+improvement)
    println("The best model improves the baseline by " + "%.2f".format(improvement) +"%.")


    println("----------根据用户评分的数据，推荐前十部最感兴趣的电影（注意要剔除用户已经评分的电影）----")
    //根据用户评分的数据，推荐前十部最感兴趣的电影（注意要剔除用户已经评分的电影）
    val myRatedMovieIds = myRatings.map(x=>x.product).toSet
    val candidates = sc.parallelize(movies.keys.filter(!myRatedMovieIds.contains(_)).toSeq)
    val recommendations = bestModel.get
      .predict(candidates.map((99, _)))
      .collect()
      .sortBy(_.rating)
      .take(10)

//    val recommendations = bestModel.get.recommendProducts(1, 10)

//    movies.foreach(println)
 myRatedMovieIds.foreach(println)
    println("-----------------------------------------------------------------------------------")
//    candidates.foreach(println)
    println("-----------------------------------------------------------------------------------")
    recommendations.foreach(println)
    println("----------根据用户评分的数据，推荐前十部最感兴趣的电影（注意要剔除用户已经评分的电影）----")



    //------------------------print all top 10 movies film information as below------------------------------
    var i = 1
    println("Movies recommended for you:")

    recommendations.foreach { r =>
      println("%2d".format(i) + ": " + movies(r.product))
      i += 1
    }


    //结束
    sc.stop()

  }

}
