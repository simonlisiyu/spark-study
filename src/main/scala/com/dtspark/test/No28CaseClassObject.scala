/*
package com.dtspark.test

/**
  * Created by root on 12/7/15.
  */
abstract class Person
case class Students(age: Int) extends Person
case class Workers(age: Int, salary: Double) extends Person
case object Shared extends Person

abstract class Item
case class Book(description: String, price: Double) extends Item
case class Bundle(description: String, price: Double, items: Item*) extends Item

object No28CaseClassObject {
  def main(args: Array[String]) {
    def caseOps(person: Person) = person match {
      case Students(age) => println("i am "+age+"years old student")
      case Workers(_,salary) => println("i am a work, every month get "+salary)
      case Shared => println("No property")
    }

    val student = Students(19)
    caseOps(Students(19))
    caseOps(Shared)

    val worker = Workers(33, 10000.1)
    val worker2 = worker.copy(salary = 19.95)
    val worker3 = worker.copy(age = 66)

    caseOps(worker2)

    def caseclass_nested(person: Item) = person match {
      case Bundle(_, _, thebook @ Book(_, _), rest @ _*) => println("The thebook descr is : "+thebook.description)
//      case Bundle(_, _, Book(descr, _), _*) => println("The first descr is : "+descr)
      case _ => println("Oops!")
    }

    caseclass_nested(Bundle("1111 special's",30.0,
    Book("scala for the spark developer", 69.96),
    Bundle("hadoop",40.0,
    Book("hive",79.96),
    Book("hbase",32.95)
    )))

    caseclass_nested(Bundle("1212 special's",30.0,
      Book("spark for the impatient", 69.96)))
  }
}


*/
