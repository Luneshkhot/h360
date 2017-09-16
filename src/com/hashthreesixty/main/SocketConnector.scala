package com.hashthreesixty.main

import com.hashthreesixty.common.HASHCONFIG
import kafka.serializer.StringDecoder
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Dharshekth Vel on 3/24/17.
  */
object SocketConnector extends App {

  val conf = new SparkConf()
  conf.setAppName(HASHCONFIG.APP_STREAMING_NAME)
  conf.setMaster(HASHCONFIG.MASTER_URL)

  val sparkContext = new SparkContext(conf)



  val streamingContext = new StreamingContext(sparkContext, Seconds(1))


  val data = streamingContext.socketTextStream("localhost", 6789)

  data.print()

  streamingContext.start()
  streamingContext.awaitTermination()







}

class Parent {}

class Child extends Parent{}