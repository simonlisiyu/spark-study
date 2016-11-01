import org.apache.spark.SparkConf
import org.apache.spark.sql.Row
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * Created by elier on 2016/4/12.
 */
object Test {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("top3").setMaster("local[2")
    val ssc = new StreamingContext(conf, Seconds(5))

    val productClickLogsDStream = ssc.socketTextStream("192.168.190.132",9999)
    val categoryProductPairsDStream = productClickLogsDStream.map(log => (log.split(" ")(2)+log.split(" ")(1), 1))
    val categoryProductCountDStream = categoryProductPairsDStream.reduceByKeyAndWindow((v1:Int, v2:Int) => v1+v2, Seconds(60), Seconds(10))
    categoryProductCountDStream.foreachRDD(rdd => {
      val rowRDD = rdd.map(tuple => {
        val category = tuple._1.split(" ")(0)
        val product = tuple._1.split(" ")(1)
        val click_count = tuple._2
        Row(category, product, click_count)
      })

      val structType = StructType(Array(StructField("category",StringType,true),
        StructField("product",StringType,true),
        StructField("click_count",IntegerType,true)))

      val hiveContext = new HiveContext(rdd.context)
      val productDF = hiveContext.createDataFrame(rowRDD, structType)
      productDF.registerTempTable("product_click_log")
      val top3DF = hiveContext.sql("SELECT category,product,click_count FROM (" +
        "SELECT category,product,click_count,row_number() OVER (PARTITION BY category ORDER BY click_count DESC) rank FROM product_click_log) tmp " +
        "WHERE rank <= 3")
      top3DF.show()

    })

    ssc.start()
    ssc.awaitTermination()
  }

}
