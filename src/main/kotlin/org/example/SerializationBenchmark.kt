/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.example

import akka.util.ByteString
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.lightbend.lagom.javadsl.jackson.JacksonSerializerFactory
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.openjdk.jmh.annotations.*
import org.taymyr.lagom.javadsl.api.KotlinJsonSerializer
import org.taymyr.lagom.javadsl.api.transport.MessageProtocols
import java.util.concurrent.TimeUnit
import kotlin.random.Random
import kotlin.text.Charsets.UTF_8

@Serializable
data class TestMessage(
    val age: Int,
    val name: String,
    val id: Long,
    val wired: Boolean,
    val simpleList: List<Int>,
    val nullableType: Double?,
    val list: List<TestMessage2>,
    val list2: List<TestMessage2>
)

@Serializable
data class TestMessage2(
    val field1: String,
    val field2: Int,
    val field3: Long
)

@BenchmarkMode(value = [Mode.AverageTime])
//@BenchmarkMode(value = [Mode.Throughput])
@Warmup(iterations = 2)
@Measurement(iterations = 3)
@Fork(value = 3)
//@OutputTimeUnit(value = TimeUnit.SECONDS)
@OutputTimeUnit(value = TimeUnit.MILLISECONDS)
open class SerializationBenchmark {

    @State(Scope.Benchmark)
    open class SerializationConfig {
        val kotlinJsonSerializer = KotlinJsonSerializer<TestMessage>(Json, TestMessage::class.java)
        val jacksonSerializer = JacksonSerializerFactory(ObjectMapper().registerKotlinModule()).messageSerializerFor<TestMessage>(TestMessage::class.java)
        val json = "{\"age\":-2108119354,\"name\":\"1358827507\",\"id\":8472297894804733568,\"wired\":true,\"simpleList\":[-1189871939,-264705116,-1647795938,-1086802910,-1150835309,-19061124,45677974,-573073391,87419864,1076415504,1910764920,1905725198,380325179,-1861430261,550494915,-1733733919,-1693670083,-707098341,52402311,-896767778,-466839805,692222514,-2123006738,1809446655,1376145811,1112946453,1408593393,1544374350,326160297,-2090385438],\"nullableType\":null,\"list\":[{\"field1\":\"7550713992877952377\",\"field2\":917254564,\"field3\":7830337685999407853},{\"field1\":\"-1101906032607374406\",\"field2\":1417619597,\"field3\":6092545039947774315},{\"field1\":\"-1995464772829067832\",\"field2\":-1752398407,\"field3\":-2452520568201455953},{\"field1\":\"-3451782208524423553\",\"field2\":1438405247,\"field3\":6328331217444190702},{\"field1\":\"3654433141038330024\",\"field2\":1885445428,\"field3\":3034699976407910943},{\"field1\":\"-602277853966398895\",\"field2\":-351657198,\"field3\":654691287042792762},{\"field1\":\"8763590343568321955\",\"field2\":279661636,\"field3\":-3090779666943387853},{\"field1\":\"-956611650015559926\",\"field2\":544992541,\"field3\":-8096120598290972937},{\"field1\":\"1204808880500103478\",\"field2\":1655817599,\"field3\":-2776464425647292651},{\"field1\":\"6596749679576471650\",\"field2\":-273738182,\"field3\":-4919084411773895664}],\"list2\":[{\"field1\":\"8859125320337385096\",\"field2\":1527379131,\"field3\":-6184527630580793401},{\"field1\":\"-4005039268668412599\",\"field2\":-159625435,\"field3\":5067887164773216866},{\"field1\":\"3891158407883073909\",\"field2\":1467813181,\"field3\":4273776700295994949},{\"field1\":\"3596024061558652966\",\"field2\":1024196447,\"field3\":3799289695295644976},{\"field1\":\"-8150226113476282560\",\"field2\":1028830189,\"field3\":-7407133965392743190},{\"field1\":\"213674631475672041\",\"field2\":-2106959131,\"field3\":-6155049686966155470},{\"field1\":\"2067534300179367531\",\"field2\":2058927197,\"field3\":2548091072032515312},{\"field1\":\"8367493905870518\",\"field2\":1319461835,\"field3\":6964667028634285619},{\"field1\":\"3962362380374563808\",\"field2\":-980189629,\"field3\":-8976613945877701009},{\"field1\":\"-2195710802253138629\",\"field2\":-656056337,\"field3\":7565171782092938169},{\"field1\":\"1958696229036054809\",\"field2\":553305710,\"field3\":7245486577826786770},{\"field1\":\"-5590717926450073618\",\"field2\":1638418726,\"field3\":8670848722827217391},{\"field1\":\"7584048178591325570\",\"field2\":1273757175,\"field3\":3075063413183583873},{\"field1\":\"2170318212845801288\",\"field2\":-530982631,\"field3\":3025362248615028616},{\"field1\":\"-4307408175758889704\",\"field2\":1767092495,\"field3\":-3081587671388574095}]}"
        val wire = ByteString.fromString(json, UTF_8)
        val message = TestMessage(
            age = Random.nextInt(),
            name = Random.nextInt().toString(),
            id = Random.nextLong(),
            wired = Random.nextBoolean(),
            simpleList = List(500) { Random.nextInt() },
            nullableType = null,
            list = List(6000) { TestMessage2(Random.nextLong().toString(), Random.nextInt(), Random.nextLong()) },
            list2 = List(6000) { TestMessage2(Random.nextLong().toString(), Random.nextInt(), Random.nextLong()) }
        )
    }


    @Benchmark
    fun kotlinxSerialize(config: SerializationConfig) {
        config.kotlinJsonSerializer.serializerForRequest().serialize(config.message)
    }

    @Benchmark
    fun jaksonSerialize(config: SerializationConfig) {
        config.jacksonSerializer.serializerForRequest().serialize(config.message)
    }

    @Benchmark
    fun kotlinxDeserialize(config: SerializationConfig) {
        config.kotlinJsonSerializer.deserializer(MessageProtocols.JSON).deserialize(config.wire)
    }

    @Benchmark
    fun jaksonDeserialize(config: SerializationConfig) {
        config.jacksonSerializer.deserializer(MessageProtocols.JSON).deserialize(config.wire)
    }
}
