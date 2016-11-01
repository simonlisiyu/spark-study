package com.dtspark.test

/**
  * Created by root on 11/13/15.
  */
object OOPInAction {
  def main(args: Array[String]) {
//    val p = new Teacher
//    p.name = "Spark"
//
//    p.sayHello

//    val p = new Teacher("Spark",5)
    val p = new Teacher("Spark",5,"male")
    println(" : "+p.age)


  }

}

//class Teacher {
//  var name : String = _
//  private var age = 27
//  private[this] val gender = "male"
//
//  def this(name:String){
//    this
//    this.name = name
//  }
//
//  def sayHello(): Unit ={
//    println(this.name+":"+this.age+":"+this.gender)
//  }
//}

//class Teacher(val name : String,val age : Int) {
//  println("This is the primary constructor!!!")
//  var gender: String = _
//  println(gender)
//
//  def this(name: String, age: Int, gender: String) {
//    this(name, age)
//
//    this.gender = gender
//  }
//}

class Teacher private (val name : String,val age : Int) {
  println("This is the primary constructor!!!")
  var gender: String = _
  println(gender)

  def this(name: String, age: Int, gender: String) {
    this(name, age)

    this.gender = gender
  }

  println("gender:"+gender)
}



