package org.bittoken.impl;

import java.util.HashMap;
import java.util.Map;

import org.bittoken.BitToken;
import org.bittoken.Decoder;
import org.bittoken.Encoder;

public class Codec implements Encoder, Decoder {

	private static final byte[] MASKS = new byte[] { 0b00000000, 0b00000001, 0b00000011, 0b00000111, 0b00001111,
			0b00011111, 0b00111111, 0b01111111, (byte) 0b11111111 };

	public static final Map<Byte, Byte> REVERSE_BITTOKEN_MAP = new HashMap<Byte, Byte>();

	static {
		for (byte i = 0; i < BitToken.BITTOKEN_CHARS.length; i++) {
			REVERSE_BITTOKEN_MAP.put((byte) BitToken.BITTOKEN_CHARS[i], i);
		}
	}

	public byte[] encode(byte[] source) {
		byte[] sixBitsArray = to6Bit(source);

		return toBitTokenChars(sixBitsArray);
	}

	public byte[] toBitTokenChars(byte[] sixBitsArray) {
		byte[] dest = new byte[sixBitsArray.length];

		for (int i = 0; i < dest.length; i++) {
			dest[i] = (byte) BitToken.BITTOKEN_CHARS[sixBitsArray[i]];
		}

		return dest;
	}

	public byte[] to6Bit(byte[] source) {
		int sourceLen = source.length;
		int destLen = sourceLen * 8 / 6 + (((sourceLen * 8) % 6) > 0 ? 1 : 0);

		byte[] dest = new byte[destLen];

		for (int i = 0; i < sourceLen; i++) {
			int c1Index = i * 8 / 6;
			int bitoffset = (i * 8) % 6;

			dest[c1Index] = (byte) (dest[c1Index] + (source[i] >> (bitoffset + 2) & MASKS[6 - bitoffset]));

			dest[c1Index + 1] = (byte) (dest[c1Index + 1] + (source[i] & MASKS[2 + bitoffset]) << (4 - bitoffset));

		}
		return dest;
	}

	public byte[] decode(byte[] source) {
		byte[] sixBitsArray = fromBittokenChars(source);
		return from6Bit(sixBitsArray);

	}

	private byte[] fromBittokenChars(byte[] source) {
		byte[] dest = new byte[source.length];

		for (int i = 0; i < dest.length; i++) {
			dest[i] = REVERSE_BITTOKEN_MAP.get(source[i]);
		}

		return dest;
	}

	private byte[] from6Bit(byte[] source) {
		int sourceLen = source.length;
		int destLen = sourceLen * 6 / 8;

		byte[] dest = new byte[destLen];

		for (int i = 0; i < destLen; i++) {
			int tIndex = i * 8 / 6;
			int bitoffset = (i * 8 % 6);

			dest[i] = (byte) (dest[i] + (source[tIndex] & MASKS[6 - bitoffset]) << bitoffset + 2);

			dest[i] = (byte) (dest[i] + (source[tIndex + 1] >> (4 - bitoffset) & MASKS[bitoffset + 4]));

		}
		return dest;
	}

}
