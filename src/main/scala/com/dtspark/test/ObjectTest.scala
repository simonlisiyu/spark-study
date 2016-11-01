package com.dtspark.test

/**
  * Created by root on 11/14/15.
  */
object ObjectTest {
  def main(args: Array[String]) {
    println(University.newStudentNo)
    println(University.newStudentNo)


  }
}

object University{
  private var studentNo = 0
  def newStudentNo = {
    studentNo += 1
    studentNo
  }
}

class University{
  val id = University.newStudentNo
  private var number = 0
  def aClass(number:Int){this.number += number}
}


