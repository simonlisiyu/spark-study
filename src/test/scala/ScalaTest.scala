/**
  * Created by root on 11/17/15.
  */
object ScalaTest {

  def main(args: Array[String]) {
//    val nums = List(2.0,2.0,3.0)
//    val resultLeftReduce = nums.reduceLeft(math.pow)  // = pow( pow(2.0,2.0) , 3.0) = 64.0
//    val resultRightReduce = nums.reduceRight(math.pow) // = pow(2.0, pow(2.0,3.0)) = 256.0
//    val a = pow(2.0,3.0)
//    println("resultLeftReduce:"+resultLeftReduce)
//    println("resultRightReduce:"+resultRightReduce)
//    println("a:"+a)

//    println(sqrt(pow(2,2)+pow(2,2)))
/*
    val array1 = Array(List(1,2,3),List(2,3,4))
    val array2 = Array(List(3,4,5),List(5,6,7))
    val array3 = array1++array2
    array1.foreach(println)
    println("array1")
    array2.foreach(println)
    println("array2")
    array3.foreach(println)
    println("array3")*/
    /*Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

    val conf = new SparkConf
    //conf.setAppName("App").setMaster("spark://Slave-3:7077")
    conf.setAppName("App").setMaster("local")
    val sc = new SparkContext(conf)
    val mmm = List(("1",2),("3",4),("3",6))
    val k = sc.parallelize(mmm)

    k.foreach(println)

    val l = k.reduceByKey((x,y) => x + y).collect
    l.foreach{
      m=> println(m._1 +"\t " + m._2)
    }

    val aaa = "1.0"
    if(aaa.length > 3 )println(">>>>>>>>>>>>>>>>>>")
    else println("elseelseelseelseelseelse")*/

//    val writer = new PrintWriter(new File("/root/test.txt" ))
//
//    writer.write("Hello Scala"+"\n")
//    writer.write("Second Scala")
//    writer.close()

    /*val list1:Array[(String,Double)] = Array{("1"->1.0)};
    val list2:Array[(String,Double)] = Array{("2"->2.0)};
    (list1 ++ list2).foreach(println)

    val line = "1.0,2.0,3.0".split(",")
    val down1Value = pow(line(1).toDouble,2)+pow(line(2).toDouble,2)
    println(down1Value)*/

    println("1243")
  }
}
