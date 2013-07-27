package cn._2dland.lab.tools;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.InflaterInputStream;

import cn._2dland.lab.utils.DataUtils;

/**
 * DoSWF解密模块
 * @author deadblue
 */
public class DoSWFDecrypt {

	/** 区块大小修正值 */
	private int blockFix;
	/** 密钥修正值 */
	private int keyFix;
	/** 跳过长度修正值 */
	private int skipFix;
	/** 数据总大小修正值 */
	private int sizeFix;
	/** 加密字节跳过步数 */
	private int step;

	public DoSWFDecrypt(int blockFix, int keyFix, int skipFix, int sizeFix, int step) {
		// 这五项数据推测是DoSWF随机生成或定制的，因此未写死在程序中
		this.blockFix = blockFix;
		this.keyFix = keyFix;
		this.skipFix = skipFix;
		this.sizeFix = sizeFix;
		this.step = step;
	}

	public byte[] decrypt(byte[] data) {
		// 读取标记变量
		int block = data[0] + blockFix;
		byte key = (byte)((data[1] & 0xff) + keyFix);
		int skip = DataUtils.readInt32LE(data, 2) + skipFix;
		int size = DataUtils.readInt32LE(data, 6) + sizeFix;
		// 取出加密部分数据
		byte[] encrypt = new byte[size];
		System.arraycopy(data, data.length - size, encrypt, 0, size);
		// 数据解密
		int index = 0;
		while(true) {
			int i = 0;
			while(i < block) {
				encrypt[index] ^= key;
				if(++index > size) break;
				i += step;
			}
			index += skip;
			if(index > size) break;
		}
		// 使用zlib算法解压缩
		InflaterInputStream iis = new InflaterInputStream(new ByteArrayInputStream(encrypt));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			// 读取第一字节，判断是否要做垃圾回收（？）
			int garbage = iis.read();
			// 存在该标识时，需要跳过2个整型（8字节）
			if(garbage != 0) {
				// 跳过一个整型（与解密无关）
				iis.skip(4);
				// 读取数据长度并跳过（与解密无关）
				int tmp = DataUtils.readInt32LE(iis);
				iis.skip(tmp);
			}
			// 跳过一个无用字节（无任何用途）
			iis.skip(1);
			// 读取垃圾数据长度，并跳过
			// 并非完全的垃圾数据，是一个空白的swf
			// DoSWF还会先加载这个swf，但是似乎无任何意义
			int len = DataUtils.readInt32LE(iis);
			iis.skip(len);
			// 这里开始是目标SWF的数据，将其读出并返回
			for(int b; (b = iis.read()) >= 0;) {
				baos.write(b);
			}
			baos.close();
			iis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return baos.toByteArray();
	}

}
