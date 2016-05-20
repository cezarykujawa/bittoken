package org.bittoken;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class BittokenUtil {
	private Decoder decoder;
	private Encoder encoder;

	public BittokenUtil(Decoder decoder, Encoder encoder) {
		super();
		this.decoder = decoder;
		this.encoder = encoder;
	}

	public String generateToken(byte... bytes) {
		return new String(encoder.encode(bytes), StandardCharsets.US_ASCII);
	}

	public byte[] interlaceBytes(long... longs) {
		ByteBuffer resultBuffer = ByteBuffer.allocate(Long.BYTES * longs.length);

		ByteBuffer longBuffers[] = new ByteBuffer[longs.length];
		for (int i = 0; i < longs.length; i++) {
			longBuffers[i] = ByteBuffer.allocate(Long.BYTES);
			longBuffers[i].putLong(longs[i]);
		}
		for (int j = 0; j < Long.BYTES; j++) {
			for (int i = 0; i < longs.length; i++) {
				resultBuffer.put(longBuffers[i].get(j));
			}
		}

		return resultBuffer.array();

	}

	public String generateToken(long... longs) {
		ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES * longs.length);

		for (long l : longs) {
			buffer.putLong(l);
		}

		return generateToken((encoder.encode(buffer.array())));
	}

}
