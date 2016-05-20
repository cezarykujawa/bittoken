package org.bittoken;

import java.nio.ByteBuffer;

public class ByteUtil {

	public static byte[] toBytes(long l) {
		ByteBuffer bb = ByteBuffer.allocate(Long.BYTES);
		bb.putLong(l);
		return bb.array();
	}

	public static long longFromBytes(byte[] array) {
		if (array.length != Long.BYTES) {
			throw new IllegalArgumentException(String.format("Invalid array lenght:%d", array.length));
		}
		ByteBuffer bb = ByteBuffer.allocate(Long.BYTES);

		bb.put(array);

		bb.flip();

		return bb.getLong();
	}

	public static String toBinaryString(byte b) {
		return String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
	}

	public static String to6BitString(byte b) {
		return String.format("%6s", Integer.toBinaryString(b & 0x3F)).replace(' ', '0');
	}

	public static String to6BitString(byte[] array) {
		StringBuffer sb = new StringBuffer();
		for (byte b : array) {
			sb.append(to6BitString(b));
			sb.append(" ");
		}

		return sb.toString();
	}

	public static String toBinaryString(byte[] array) {
		StringBuffer sb = new StringBuffer();
		for (byte b : array) {
			sb.append(toBinaryString(b));
			sb.append(" ");
		}

		return sb.toString();
	}

}
