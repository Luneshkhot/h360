package com.hashthreesixty.main

import com.hashthreesixty.common.HASHCONFIG
import kafka.serializer.StringDecoder
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}
import org.elasticsearch.spark.rdd.EsSpark
import org.elasticsearch.spark.streaming.sparkDStreamFunctions

import scala.collection.mutable




/**
  * Created by Chinnasamy on 4/3/17.
  */
object HashThreeSixtyMain {

  def main(args: Array[String]) = {

    val conf = new SparkConf()
    conf.setAppName(HASHCONFIG.APP_STREAMING_NAME)
    conf.setMaster(HASHCONFIG.MASTER_URL)
    conf.set("es.index.auto.create", "true")


    val sparkContext = new SparkContext(conf)
    val streamingContext = new StreamingContext(sparkContext, Seconds(1))


    val kafkaParams = Map[String, String]("metadata.broker.list" -> "localhost:9092")
    val messages = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](streamingContext, kafkaParams, Set("HASHER"))

    messages.print

//    val microbatches = mutable.Queue(messages);
//    streamingContext.queueStream(microbatches).saveToEs("hash360index/fields")

    /**
      *  ("custom-index-{date}/customtype")
      *  ("custom-index-{date:{YYYY.mm.dd}}/customtype")
      */
    //messages.foreachRDD((x,y) => EsSpark.saveToEs(x, "hash360index/fields"))

    streamingContext.start()
    streamingContext.awaitTermination()

  }

}
