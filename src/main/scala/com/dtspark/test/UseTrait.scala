package com.dtspark.test

/**
  * Created by root on 11/15/15.
  */
class UseTrait {

}

trait Logger {
  def log (msg : String)
//  def log (msg : String){}
}

class ConcreteLogger extends Logger with Cloneable{
  override def log(msg: String) = println("Log : " + msg)

  def concreteLog{
    log("It's me !!!")
  }
}

trait TraitLogger extends Logger{
  override def log (msg : String): Unit ={
    println("TraitLogger Log content is : "+msg)
  }
}
trait TraitLoggered{
  def loged(msg : String)
}


class Human{
  println("Human")
}
trait TTeacher extends Human{
  println("TTeacher")
  def teach
}
trait PianoPlayer extends Human{
  println("PianoPlayer")
  def playPiano = {println("I'm playing piano ")}
}
class PianoTeacher extends Human with TTeacher with PianoPlayer {
  override def teach = {println("I'm tarining students.")}
}

//AOP
trait Action{
  def doAction
}
trait TBeforeAfter extends Action {
  abstract override def doAction: Unit ={
    println("Initialization")
    super.doAction
    println("Destroyed")
  }
}

class Work extends Action {
  override def doAction = println("Working...")
}

object UseTrait extends App{
//  val logger = new ConcreteLogger
  val logger = new ConcreteLogger with TraitLogger
  logger.concreteLog

//  val t1 = new PianoTeacher
//  t1.playPiano
//  t1.teach

  val t2 = new Human with TTeacher with PianoPlayer{
    def teach = {println("I'm teaching students.")}
  }
  t2.playPiano
  t2.teach

  val work = new Work with TBeforeAfter
  work.doAction

}
