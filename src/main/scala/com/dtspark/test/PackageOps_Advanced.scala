package com.critest.scala

/**
  * Created by root on 11/16/15.
  */
class PackageOps_Advanced {
  import PackageOps_Advanced.power

  private def canMakeItTrue = power > 10001

}

object PackageOps_Advanced{
  private def power = 10000
  def makeItTrue(p : PackageOps_Advanced): Boolean = {
  val result = p.canMakeItTrue
  result
  }
}

package spark{
  package navigation{
    private[spark] class Navigators{              //package.spark private
      protected[navigation] def useStarChart() {}       //package.spark.navigation protected
      class LegOfJourney{
        private[Navigators] val distance = 100          //Navigator private, different with [this]
      }
      private[this] var speed = 200           //this.object private
    }
  }

  package launch{
    import navigation._
    object Vehicle{
      private[launch] val guide = new Navigators
    }
  }
}
