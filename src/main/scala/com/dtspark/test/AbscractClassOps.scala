package com.dtspark.test

/**
  * Created by root on 11/15/15.
  */
class AbscractClassOps {

  var id : Int = _
}

abstract class SuperTeacher(val name : String){
  var id : Int
  var age : Int
  def teach
}

class TeacherForMaths(name : String) extends SuperTeacher(name){
  override var id: Int = name.hashCode()

//  var id: Int = name.##

  override def teach: Unit ={
    println("Teaching!!!")
  }

  override var age: Int = 29
}

object AbstractClassOps{
  def main(args: Array[String]) {
    val teacher = new TeacherForMaths("Sparker")
    teacher.teach

    println("teacher.id : "+teacher.id)
    println("teacher.name : "+teacher.name)
  }
}