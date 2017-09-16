package com.hashthreesixty.main

/**
  * Created by dharshekthvel on 5/7/17.
  */
object TwitterCompanies {

  val positiveCompanies = Set("BlackRock",
                              "J.P. Morgan",
                              "Bank of America",
                              "Wells Fargo",
                              "Citigroup",
                              "Goldman Sachs",
                              "Morgan Stanley",
                              "Deutsche Bank",
                              "Federal Reserve",
                              "EXIM Bank",
                              "CNN Money",
                              "Wall Street Journal",
                              "Thomson Reuters",
                              "Forbes",
                              "Financial Times",
                              "Fortune");

  val negativeCompanies = Set("Lehman Brothers",
                              "unitedbreweries",
                              "Pacific Gas & Electric Co",
                              "Thornburg Mortgage",
                              "Chrysler",
                              "MF Global",
                              "Conseco",
                              "Enron",
                              "CIT Group",
                              "General Motors",
                              "WorldCom",
                              "Washington Mutual");

  def retrieveMePositiveCompanies() : Set[String] = {
    positiveCompanies.map(_.toLowerCase()).toSet
  }

  def retrieveMeNegativeCompanies() : Set[String] = {
    negativeCompanies.map(_.toLowerCase()).toSet
  }

  def isThereCompaniesInTweet(tweets : Array[String]) : Boolean = {

    import util.control.Breaks._

    var tweetContainsCompanies = false;

    breakable {
      for (i <- positiveCompanies) {

        if (tweets.contains(i.toLowerCase())) {
          tweetContainsCompanies = true
          break
        }

      }

      for (i <- negativeCompanies) {

        if (tweets.contains(i.toLowerCase())) {
          tweetContainsCompanies = true
          break
        }

      }
    }

    tweetContainsCompanies
  }

  def main(args : Array[String]) = {

    println("GOD")




    val arrayss = Array("CNN hMoney".toLowerCase(), "Reuters")

    println(isThereCompaniesInTweet(arrayss))


    println("GOD")
  }
}
