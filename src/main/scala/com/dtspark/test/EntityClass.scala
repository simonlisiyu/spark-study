package com.dtspark.test

/**
  * Created by root on 11/13/15.
  */
object EntityClass {
  def main(args: Array[String]) {
    val person = new Person()
    person.increment()
    println(person.current)

    val student = new Student
    student.age = 10
//    println(student.score)
    println(student.name)
    println(student.age)
  }


}

class Person {
  private var age = 0
  def increment() {age += 1}
  def current = age
}

class Student {
  var age = 0
  private[this] var score = 0
  val name = "Scala"
//  def score = score
//  def lessScore(other: Student) = score < other.score
}


