package cn._2dland.lab.utils;

import java.io.IOException;
import java.io.InputStream;

/**
 * 
 * @author deadblue
 */
public class DataUtils {

	/**
	 * 以Little Endian方式读取一个16位整型数字
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static int readInt16LE(InputStream in) throws IOException {
		int n = 0;
		for(int i = 0; i < 2; i++) {
			int b = in.read();
			if(b < 0) break;
			n |= (b & 0xff) << (i * 8);
		}
		return n;
	}
	/**
	 * 以Little Endian方式读取一个16位整型数字
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static int readInt16LE(byte[] b, int offset) {
		int n = 0;
		for(int i = 0; i < 2; i++) {
			if(offset + i >= b.length) break;
			n |= (b[offset + i] & 0xff) << (i * 8);
		}
		return n;
	}

	/**
	 * 以Little Endian方式读取一个32位整型数字
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static int readInt32LE(InputStream in) throws IOException {
		int n = 0;
		for(int i = 0; i < 4; i++) {
			int b = in.read();
			if(b < 0) break;
			n |= (b & 0xff) << (i * 8);
		}
		return n;
	}
	/**
	 * 以Little Endian方式读取一个32位整型数字
	 * @param b
	 * @param offset
	 * @return
	 */
	public static int readInt32LE(byte[] b, int offset) {
		int n = 0;
		for(int i = 0; i < 4; i++) {
			if(offset + i >= b.length) break;
			n |= (b[offset + i] & 0xff) << (i * 8);
		}
		return n;
	}

}
