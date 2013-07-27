package cn._2dland.lab.swf;

import cn._2dland.lab.swf.tags.DefineBinaryTag;
import cn._2dland.lab.swf.tags.EndTag;
import cn._2dland.lab.swf.tags.UnparsedTag;

public class TagFactory {

	public static SWFTag createTag(int code, int length, byte[] raw) {
		SWFTag result = null;
		switch(code) {
		case 0:
			result = new EndTag();
			break;
		case 87:
			result = new DefineBinaryTag(length, raw);
			break;
		default:
			UnparsedTag tag = new UnparsedTag();
			tag.setCode(code);
			tag.setLength(length);
			tag.setRaw(raw);
			result = tag;
			break;
		}
		return result;
	}

}
