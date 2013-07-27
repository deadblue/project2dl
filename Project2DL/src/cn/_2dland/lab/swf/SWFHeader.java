package cn._2dland.lab.swf;

import cn._2dland.lab.swf.struct.Rectangle;

/**
 * SWF文件头
 * @author deadblue
 */
public class SWFHeader {

	/** 压缩方式 */
	private CompressMode compress;
	/** 版本 */
	private int version;
	/** 文件大小 */
	private int fileLength;
	/** 帧大小 */
	private Rectangle frameRect;
	/** 帧速率 */
	private float frameRate;
	/** 帧数量 */
	private int frameCount;

	public CompressMode getCompress() {
		return compress;
	}
	public void setCompress(CompressMode compress) {
		this.compress = compress;
	}

	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}

	public int getFileLength() {
		return fileLength;
	}
	public void setFileLength(int fileLength) {
		this.fileLength = fileLength;
	}

	public Rectangle getFrameRect() {
		return frameRect;
	}
	public void setFrameRect(Rectangle frameRect) {
		this.frameRect = frameRect;
	}

	public float getFrameRate() {
		return frameRate;
	}
	public void setFrameRate(float frameRate) {
		this.frameRate = frameRate;
	}

	public int getFrameCount() {
		return frameCount;
	}
	public void setFrameCount(int frameCount) {
		this.frameCount = frameCount;
	}

}
