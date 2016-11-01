package spark.study.java.sql.commons;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;

/**
 * Created by root on 4/5/16.
 */
public class DataFrameOperation {

    public static void main(String[] args) {
        SparkConf conf = new SparkConf()
                .setAppName("DataFrameCreate")
                .setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);
        SQLContext sqlContext = new SQLContext(sc);

        // created DataFrame could be used as a SQL table
        DataFrame df = sqlContext.read().json("hdfs://192.168.190.132:9000/test/students.json");

        // println all datas
        df.show();

        // println data's Schema
        df.printSchema();

        // search a column's all data
        df.select("name").show();

        // search several columns' data, and do compute
        df.select(df.col("name"), df.col("age").plus(1)).show();

        // filter data by a column's value
        df.filter(df.col("age").gt(18)).show();

        // grouping data by a column, then cound number
        df.groupBy(df.col("age")).count().show();

    }


}
