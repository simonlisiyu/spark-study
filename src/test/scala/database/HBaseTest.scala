/*
package database

import org.apache.log4j.{Level, Logger}
import org.apache.spark._


/**
  * Created by root on 11/26/15.
  */
object HBaseTest {
  val tableName = HbaseConstantVariables.HTABLE_SPARK_DATA_DIG
  val infoFamily = HbaseConstantVariables.HTABLE_INFO_FAMILY
  val relFamily = HbaseConstantVariables.HTABLE_RELATION_FAMILY
  def main(args: Array[String]) {
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)
    val conf = new SparkConf
    //conf.setAppName("App").setMaster("spark://Slave-3:7077")
    conf.setAppName("App").setMaster("local[2]")
    val sc = new SparkContext(conf)

    HBaseAccessor.deleteTable(tableName)
    HBaseAccessor.create(tableName,infoFamily,relFamily)

    val list = Array("1","2","3")
//    val rowId = Tools.randomM5String("a")
    val rowId = HBaseUtils.generate
    HBaseAccessor.addData(rowId,tableName,infoFamily,relFamily,list,list,list,list)


    val rs = HBaseAccessor.getResultScan(tableName)
//    for (r:Result <- rs){
//      for(kv:KeyValue <- r){
//        println("row: "+kv.getRow)
//      }
//    }
    println("success")

    var array = Array("1")
    array = array.:+("2")
    array.foreach(println)
  }
}
*/
