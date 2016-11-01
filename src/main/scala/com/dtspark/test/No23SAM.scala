package com.dtspark.test

import java.awt.event.{ActionEvent, ActionListener}
import javax.swing.{JButton, JFrame}

/**
  * Created by root on 12/4/15.
  */
object No23SAM {

  var data = 0
  val frame = new JFrame("SAM Testing")
  val jButton = new JButton("Counter")
//  jButton.addActionListener(new ActionListener {
//    override def actionPerformed(e: ActionEvent): Unit ={
//      data += 1
//      println(data)
//    }
//  })

  implicit def convertedAction(action: (ActionEvent) => Unit) =
    new ActionListener {
      override def actionPerformed(event: ActionEvent) {action(event)}
    }

  jButton.addActionListener((event: ActionEvent) => {data += 1; println(data)})

  frame.setContentPane(jButton)
  frame.pack()
  frame.setVisible(true)

}
