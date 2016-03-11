/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

    package org.apache.spark.examples

import org.apache.spark.{SparkContext, SparkConf}

object JoinBroadcastTest {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("test join broadcast").setMaster("local[4]")
    val sc = new SparkContext(conf)
    val w = sc.parallelize(Array((1, 2), (2, 4), (3, 5)))
    val data = sc.parallelize(Array((1, 6), (2, 6), (3, 7)))
    val bcw = data.sparkContext.joinBroadcast(w)
    data.map{ case (key, v) =>
      (key, v * bcw.getValue(key))
    }.collect().foreach(println)
  }

}
