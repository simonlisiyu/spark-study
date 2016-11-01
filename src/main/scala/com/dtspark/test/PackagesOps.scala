package com.critest.scala

/**
  * Created by root on 11/15/15.
  */


import scala._
import java.awt.{Color,Font}
import java.util.{HashMap=>JavaHashMap}
import scala.{StringBuilder=>_}


class PackagesOps {

}

//package object, val and var could be used directly in the package(same name package)
package object people {
  val defaultName = "Scala"
}
package people{
  class people {
    var name = defaultName
  }
}

package spark.navigation{
  abstract class Navigator{
    def act
  }
  package tests{
    //in the spark.navigation.tests package
    class NavigatorSuite
  }

  package impls {
    //
    class Action extends Navigator{
      override def act: Unit = {
        println("This is an Action's Implement of abstract class Navigator!")
      }
    }
  }
}

package hadoop{
  package navigation {
    class Navigator
  }

  package launch{

  import com.critest.scala.hadoop.navigation.Navigator

  class Booster{
      val nav = new Navigator
    }
  }


}
