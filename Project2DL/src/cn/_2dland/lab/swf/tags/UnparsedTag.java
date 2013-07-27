package cn._2dland.lab.swf.tags;

import cn._2dland.lab.swf.SWFTag;

/**
 * 未解析Tag
 * 懒得解析的都先用这个~
 * @author deadblue
 */
public class UnparsedTag extends SWFTag {

	/** 原始数据 */
	private byte[] raw;

	public byte[] getRaw() {
		return raw;
	}
	public void setRaw(byte[] raw) {
		this.raw = raw;
	}


}
