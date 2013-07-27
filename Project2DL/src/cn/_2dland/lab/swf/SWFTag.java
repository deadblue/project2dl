package cn._2dland.lab.swf;

public abstract class SWFTag {

	/** tag代码 */
	protected int code;
	/** tag长度 */
	protected int length;

	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}

	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}

}
