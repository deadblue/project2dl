package cn._2dland.lab.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.BitSet;

import cn._2dland.lab.swf.struct.Rectangle;

/**
 * 读取各种Adobe定义的数据结构
 * @author deadblue
 */
public class AdobeUtils {

	/**
	 * 读取8位整数
	 * @param in
	 * @return
	 */
	public static int readUI8(InputStream in) {
		int n = 0;
		try {
			n = in.read();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return n;
	}
	/**
	 * 读取16位整数
	 * @param in
	 * @return
	 */
	public static int readUI16(InputStream in) {
		int n = 0;
		try {
			byte[] buf = new byte[2];
			in.read(buf);
			n = (buf[0] & 0xff); 
			n |= (buf[1] & 0xff) << 8;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return n;
	}
	/**
	 * 读取32位整数
	 * @param in
	 * @return
	 */
	public static int readUI32(InputStream in) {
		int n = 0;
		try {
			byte[] buf = new byte[4];
			in.read(buf);
			n = (buf[0] & 0xff); 
			n |= (buf[1] & 0xff) << 8;
			n |= (buf[2] & 0xff) << 16;
			n |= (buf[3] & 0xff) << 24;
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return n;
	}

	public static float readFixed8(InputStream in) throws IOException {
		int low = in.read();
		int high = in.read();
		float result = high;
		if(low != 0) {
			int digit = (int) Math.floor(Math.log10(low));
			int divider = (int) Math.pow(10, digit + 1);
			result += (float)low / divider;
		}
		return result;
	}

	public static Rectangle readRectangle(InputStream in) throws IOException {
		Rectangle rect = new Rectangle();
		// 读取bitCount
		int b = in.read();
		int bitCount = b >> 3;
		rect.setBitCount(bitCount);
		// 计算需要读取的字节数，并读取
		int byteLen = (int)Math.ceil((double)(bitCount * 4 + 5) / 8);
		byte[] buf = new byte[byteLen];
		buf[0] = (byte)b;
		in.read(buf, 1, byteLen - 1);
		// 将字节转换为位集
		BitSet bits = new BitSet(byteLen * 8);
		for(int i=0; i < byteLen; i++) {
			for(int j = 0; j < 8; j++) {
				// 每字节按从高到低读取
				int bit = (buf[i] >> (7 - j)) & 0x01;
				bits.set((i * 8 + j), bit != 0);
			}
		}
		// 依次读取四个属性值
		int xMin = bitsToInt(bits, 5, bitCount);
		rect.setxMin(xMin);
		int xMax = bitsToInt(bits, 5 + bitCount, bitCount);
		rect.setxMax(xMax);
		int yMin = bitsToInt(bits, 5 + bitCount * 2, bitCount);
		rect.setyMin(yMin);
		int yMax = bitsToInt(bits, 5 + bitCount * 3, bitCount);
		rect.setyMax(yMax);
		return rect;
	}
	private static int bitsToInt(BitSet bits, int offset, int count) {
		int result = 0;
		bits = bits.get(offset, offset + count);
		for(int i = 0; i < count; i++) {
			result <<= 1;
			result |= bits.get(i) ? 1 : 0;
		}
		return result;
	}

}
