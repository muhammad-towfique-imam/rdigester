/**
 * Copyright 2015 TASER International, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.evidence.tools.digest

import java.util.UUID

import com.evidence.tools.digest.RDigester.Algorithm
import com.typesafe.scalalogging.LazyLogging
import org.apache.commons.codec.digest.DigestUtils
import org.scalatest.BeforeAndAfterAll
import org.scalatest.FlatSpec
import org.scalatest.Matchers

/**
 * Created by pmahendra on 11/25/15.
 */

class RDigesterSpec extends FlatSpec with Matchers with LazyLogging with BeforeAndAfterAll {
  override def beforeAll(): Unit = {
  }

  it should "compute md5 digest" in {
    val data = UUID.randomUUID().toString

    val expected = DigestUtils.md5Hex(data)
    val calculated = RDigester.md5Hex(data)

    logger.info(s"calculated $calculated == expected $expected")
    assert(calculated == expected)
  }

  it should "compute sha1 digest" in {
    val data = UUID.randomUUID().toString

    val expected = DigestUtils.sha1Hex(data)
    val calculated = RDigester.sha1Hex(data)

    logger.info(s"calculated $calculated == expected $expected")
    assert(calculated == expected)
  }

  it should "compute sha256 digest" in {
    val data = UUID.randomUUID().toString

    val expected = DigestUtils.sha256Hex(data)
    val calculated = RDigester.sha256Hex(data)

    logger.info(s"calculated $calculated == expected $expected")
    assert(calculated == expected)
  }

  it should "compute sha512 digest" in {
    val data = UUID.randomUUID().toString

    val expected = DigestUtils.sha512Hex(data)
    val calculated = RDigester.sha512Hex(data)

    logger.info(s"calculated $calculated == expected $expected")
    assert(calculated == expected)
  }

  it should "compute md5 digest given a series of serialized ctx" in {
    val data = UUID.randomUUID().toString
    val expected = DigestUtils.md5Hex(data)

    var ctx = new RDigester(Algorithm.Md5).getCtx()
    assert(ctx != null)

    data.foreach(char => {
      val digester = new RDigester(Algorithm.Md5, ctx)
      digester.update(Array(char.toByte), 1)

      ctx = digester.getCtx
    })

    val calculated = new RDigester(Algorithm.Md5, ctx).finalChecksumHex()

    logger.info(s"[resumable] calculated $calculated == expected $expected")
    assert(calculated == expected)
  }

  it should "compute sha1 digest given a series of serialized ctx" in {
    val data = UUID.randomUUID().toString
    val expected = DigestUtils.sha1Hex(data)

    var ctx = new RDigester(Algorithm.Sha1).getCtx()
    assert(ctx != null)

    data.foreach(char => {
      val digester = new RDigester(Algorithm.Sha1, ctx)
      digester.update(Array(char.toByte), 1)

      ctx = digester.getCtx
    })

    val calculated = new RDigester(Algorithm.Sha1, ctx).finalChecksumHex()

    logger.info(s"[resumable] calculated $calculated == expected $expected")
    assert(calculated == expected)
  }

  it should "compute sha256 digest given a series of serialized ctx" in {
    val data = UUID.randomUUID().toString
    val expected = DigestUtils.sha256Hex(data)

    var ctx = new RDigester(Algorithm.Sha256).getCtx()
    assert(ctx != null)

    data.foreach(char => {
      val digester = new RDigester(Algorithm.Sha256, ctx)
      digester.update(Array(char.toByte), 1)

      ctx = digester.getCtx
    })

    val calculated = new RDigester(Algorithm.Sha256, ctx).finalChecksumHex()

    logger.info(s"[resumable] calculated $calculated == expected $expected")
    assert(calculated == expected)
  }

  it should "compute sha512 digest given a series of serialized ctx" in {
    val expected = DigestUtils.sha256Hex("Hello World")

    var digester = new RDigester(Algorithm.Sha256)
    val ctx = digester.getCtx()

    digester = new RDigester(Algorithm.Sha256, ctx)
    digester.update("Hello World".getBytes, 11)

    val finalCheckSum = digester.finalChecksumHex()

    logger.info(s"[resumable] calculated $finalCheckSum == expected $expected")
    assert(finalCheckSum == expected)
  }
}

