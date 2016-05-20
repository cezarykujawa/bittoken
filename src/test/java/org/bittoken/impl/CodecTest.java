package org.bittoken.impl;

import static org.bittoken.ByteUtil.*;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CodecTest {

	final static Logger logger = LoggerFactory.getLogger(CodecTest.class);

	private Codec codec = new Codec();
	private Random random = new Random();

	@Before
	public void setup() {
	}

	@Test
	public void smokeTest() {

		long id = 12034l;

		byte[] idAsBytes = toBytes(id);

		byte[] idAs6BitArray = codec.to6Bit(idAsBytes);

		String token = new String(codec.toBitTokenChars(idAs6BitArray), StandardCharsets.US_ASCII);

		byte[] decoded = codec.decode(token.getBytes(StandardCharsets.US_ASCII));

		assertArrayEquals(idAsBytes, decoded);

	}

	@Test
	public void randomTest() {

		for (int i = 0; i < 1000000; i = (i + 1) * 10) {
			byte[] data = new byte[i];
			random.nextBytes(data);

			byte[] encodedData = codec.encode(data);
			byte[] decodedData = codec.decode(encodedData);
			Assert.assertArrayEquals(data, decodedData);
			
		}

	}

	@Test
	public void regressiveTest() throws IOException, URISyntaxException {

		byte[] input = FileUtils.readFileToByteArray(new File(CodecTest.class.getResource("/randomBytes").toURI()));
		String encoded = new String (codec.encode(input));
		String expectedEncoded = FileUtils.readFileToString(new File(CodecTest.class.getResource("/encoded").toURI()), Charset.defaultCharset());
		assertEquals(expectedEncoded, encoded);
		byte[] decoded = codec.decode(encoded.getBytes());
		assertArrayEquals(input, decoded);
	}

	

}