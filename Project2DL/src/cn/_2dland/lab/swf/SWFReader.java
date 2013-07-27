package cn._2dland.lab.swf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.InflaterInputStream;

import cn._2dland.lab.utils.AdobeUtils;
import cn._2dland.lab.utils.DataUtils;

public class SWFReader {

	/** 输入流 */
	private InputStream in = null;
	/** 头信息 */
	private SWFHeader header = null;

	public SWFReader(InputStream in) {
		this.in = in;
		prepare();
	}

	/**
	 * 预处理，读取SWF头信息
	 */
	private void prepare() {
		header = new SWFHeader();
		try {
			// 处理第一字节
			int magic = in.read();
			switch(magic) {
			case 'F':
				header.setCompress(CompressMode.NONE);
				break;
			case 'C':
				header.setCompress(CompressMode.ZLIB);
				break;
			case 'Z':
				header.setCompress(CompressMode.LZMA);
				break;
			}
			// 后两个字节应为"WS"，这里不做判断
			in.skip(2);
			// 读取swf文件版本（1字节）
			header.setVersion(in.read());
			// 读取swf文件大小（4字节）
			header.setFileLength(DataUtils.readInt32LE(in));
			// 若swf被压缩，则进行解压
			if(header.getCompress() == CompressMode.ZLIB) {
				in = new InflaterInputStream(in);
			} else if(header.getCompress() == CompressMode.LZMA) {
				// 暂不支持（懒得弄~）
			}
			// 读取帧信息
			header.setFrameRect(AdobeUtils.readRectangle(in));
			header.setFrameRate(AdobeUtils.readFixed8(in));
			header.setFrameCount(DataUtils.readInt16LE(in));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取文件头
	 * @return
	 */
	public SWFHeader getHeader() {
		return header;
	}

	/**
	 * 读取tag
	 * @return
	 */
	public SWFTag readTag() {
		SWFTag tag = null;
		try {
			// 读取tag头
			int head = DataUtils.readInt16LE(in);
			// 读取tag代码
			int code = head >> 6;
			// 读取数据长度
			int length = head & 0x3f;
			if(length == 0x3f) {
				length = DataUtils.readInt32LE(in);
			}
			// 读取数据
			ByteArrayOutputStream buf = new ByteArrayOutputStream();
			for(int i = 0; i < length; i++) {
				buf.write(in.read());
			}
			buf.close();
			// 构造tag对象
			tag = TagFactory.createTag(code, length, buf.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tag;
	}

	/**
	 * 关闭读取器，释放资源
	 */
	public void close() {
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
