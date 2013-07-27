package cn._2dland.lab.swf.tags;

import cn._2dland.lab.swf.SWFTag;
import cn._2dland.lab.utils.DataUtils;

public class DefineBinaryTag extends SWFTag {

	/** 标识 */
	private int id;
	/** 保留字 */
	private int reserved;
	/** 二进制数据 */
	private byte[] binary;

	public DefineBinaryTag(int length, byte[] raw) {
		this.code = 87;
		this.length = length;
		parse(raw);
	}
	// 解析原始数据
	private void parse(byte[] raw) {
		id = DataUtils.readInt16LE(raw, 0);
		reserved = 0;
		// 拷贝二进制数据
		binary = new byte[length - 6];
		System.arraycopy(raw, 6, binary, 0, length - 6);
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public int getReserved() {
		return reserved;
	}
	public void setReserved(int reserved) {
		this.reserved = reserved;
	}

	public byte[] getBinary() {
		return binary;
	}
	public void setBinary(byte[] binary) {
		this.binary = binary;
	}

}
