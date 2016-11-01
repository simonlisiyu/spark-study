package spark.study.java.sql.commons;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import scala.Tuple2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 4/6/16.
 */
public class JDBCDataSource {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf()
                .setAppName("JDBCDataSource")
                .setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);
        SQLContext sqlContext = new SQLContext(sc);

        Map<String, String> options = new HashMap<>();
        options.put("url", "jdbc:mysql://192.168.190.132:3306/testdb");
        options.put("dbtable", "student_infos");
        DataFrame studentsInfosDF = sqlContext.read().format("jdbc").options(options).load();

        options.put("dbtable", "student_scores");
        DataFrame studentsScoresDF = sqlContext.read().format("jdbc").options(options).load();

        JavaPairRDD<String, Tuple2<Integer, Integer>> studentRDD =
                studentsInfosDF.javaRDD().mapToPair(new PairFunction<Row, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(Row row) throws Exception {
                return new Tuple2<String, Integer>(row.getString(0),Integer.valueOf(String.valueOf(row.get(1))));
            }
        }).join(studentsScoresDF.javaRDD().mapToPair(new PairFunction<Row, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(Row row) throws Exception {
                return new Tuple2<String, Integer>(row.getString(0),Integer.valueOf(String.valueOf(row.get(1))));
            }
        }));

        JavaRDD<Row> studentRowRDD = studentRDD.map(new Function<Tuple2<String, Tuple2<Integer, Integer>>, Row>() {
            @Override
            public Row call(Tuple2<String, Tuple2<Integer, Integer>> tuple) throws Exception {
                return RowFactory.create(tuple._1(), tuple._2()._1(), tuple._2()._2());
            }
        });

        JavaRDD<Row> filterRDD = studentRowRDD.filter(new Function<Row, Boolean>() {
            @Override
            public Boolean call(Row row) throws Exception {
                return row.getInt(2) > 80;
            }
        });

        List<StructField> structFields = new ArrayList<>();
        structFields.add(DataTypes.createStructField("name", DataTypes.StringType, true));
        structFields.add(DataTypes.createStructField("age", DataTypes.IntegerType, true));
        structFields.add(DataTypes.createStructField("score", DataTypes.IntegerType, true));
        StructType structType = DataTypes.createStructType(structFields);

        DataFrame studentsDF = sqlContext.createDataFrame(filterRDD, structType);

        for(Row row: studentsDF.collect()){
            System.out.println(row);
        }

        studentsDF.javaRDD().foreach(new VoidFunction<Row>() {
            @Override
            public void call(Row row) throws Exception {
                String sql = "insert into good_student_infos values ("
                        + "'" + row.getString(0) + "',"
                        + row.getInt(1) + ","
                        + row.getInt(2) + ")";
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = null;
                Statement stmt = null;
                try{
                    conn = DriverManager.getConnection("jdbc:mysql://192.168.190.132:3306/testdb", "", "");
                    stmt = conn.createStatement();
                    stmt.executeUpdate(sql);
                } catch (Exception e){
                    e.printStackTrace();
                } finally {
                    if(stmt != null){
                        stmt.close();
                    }
                    if(conn != null){
                        conn.close();
                    }
                }
            }
        });

        sc.close();


    }
}
