package com.hashthreesixty.main

import com.fasterxml.jackson.databind.ObjectMapper
import com.hashthreesixty.common.HASHCONFIG
import org.apache.commons.configuration.ConfigurationFactory.ConfigurationBuilder
import org.apache.spark.streaming.twitter.TwitterUtils
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.elasticsearch.spark.rdd._
import twitter4j.auth.OAuthAuthorization

/**
  * Created by Chinnasamy on 7/3/17.
  */
object SparkTwitterConnector extends App {

  val conf = new SparkConf()
  conf.setAppName(HASHCONFIG.APP_STREAMING_NAME)
  conf.setMaster(HASHCONFIG.MASTER_URL)
  conf.set("es.index.auto.create", "true")
  conf.set("es.nodes", "localhost:9200")


  val sparkContext = new SparkContext(conf)
  sparkContext.setLogLevel("WARN")

  val streamingContext = new StreamingContext(sparkContext, Seconds(10))


  System.setProperty("twitter4j.oauth.consumerKey",       "H7gCcVkw7Hl8C4hbgEo62h6zF")
  System.setProperty("twitter4j.oauth.consumerSecret",    "yGgJ8IT5FKoNcrMWCE31tmQqtkzPiSnhIX9XuXlFP4P54XVdxg")
  System.setProperty("twitter4j.oauth.accessToken",       "837928192170209280-v0p7XGPdehsV0VSTouN6YisQReaEQGt")
  System.setProperty("twitter4j.oauth.accessTokenSecret", "6HnqRgWUyQWPSUfKKpthSP7jXUBFfU7hVop6vlfJxUQpL")


  val stream = TwitterUtils.createStream(streamingContext, None)
  val tags = stream.flatMap { status => status.getHashtagEntities.map(_.getText) }

    val tweets = stream.filter {t =>
      val tags = t.getText.split(" ").map(_.toLowerCase)
      checkForData(tags)
  }

  val mapper = new ObjectMapper();


  //stream.foreachRDD(x => x.foreach(x => println(x.getText())))
  //stream.foreachRDD(x => EsSpark.saveJsonToEs(x, "twittershow/fields"))

  //tweets.print();
  tweets.map(t => mapper.writeValueAsString(t))
        //.print()
        .foreachRDD(x => {println(x); EsSpark.saveJsonToEs(x, "twittershow/fields")})


  streamingContext.start();
  streamingContext.awaitTermination();

  def checkForData(tags : Array[String]) = {


//     tags.contains("paris") || tags.contains("france") || tags.contains("france election") ||
//       tags.contains("france election") || tags.contains("paris election") || tags.contains("Fillon") ||
//       tags.contains("Emmanuel Macron") || tags.contains("Marine Le Pen") || tags.contains("Front National") ||
//       tags.contains("En Marche") || tags.contains("Les Républicains")    || tags.contains("Macron") || tags.contains("Le Pen") ||
//       tags.contains("Marine Le Pen")

    //tags.contains("")

    val returnValue = TwitterCompanies.isThereCompaniesInTweet(tags)

    if (returnValue)
      println(" \n\n\n\n We got a tweat")

    returnValue
  }
}

case class TwitterDTO(data : String)