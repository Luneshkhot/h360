package com.hashthreesixty.main

object ScalaMain extends App {


  //
  (an:Int) => an * an

  def simpleFunction (x : Int) = x * x

  def squareFunction(x : Int) : String = {
    "SQUARED_OUTPUT"
  }

  squareFunction(3)

}


class MyDataInvariantStructure[T] {
  def append(myElement:T) : T = {
    myElement
  }
}


abstract class MyDataStructure[-T, +U] {
  def append(myElement:T) : U
}







