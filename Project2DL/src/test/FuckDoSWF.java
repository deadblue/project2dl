package test;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import cn._2dland.lab.swf.SWFReader;
import cn._2dland.lab.swf.SWFTag;
import cn._2dland.lab.swf.tags.DefineBinaryTag;
import cn._2dland.lab.tools.DoSWFDecryptor;

public class FuckDoSWF {

	public static void main(String[] args) {
		String srcFile = "/Users/deadblue/downloads/bokePlayer20130726_V4_1_42_23.swf";
		String destFile = "/Users/deadblue/downloads/decrypt.swf";
		try {
			// 读取swf文件
			FileInputStream fis = new FileInputStream(srcFile);
			SWFReader reader = new SWFReader(fis);
			// 遍历tag，搜寻被加密的数据块，并解密
			byte[] swfData = null;
			SWFTag tag = null;
			do {
				tag = reader.readTag();
				if(tag instanceof DefineBinaryTag) {
					DefineBinaryTag dbTag = (DefineBinaryTag) tag;
					DoSWFDecryptor decryptor = new DoSWFDecryptor(-1, -5, -7, -3, 7);
					swfData = decryptor.decrypt(dbTag.getBinary());
					break;
				}
			} while(tag != null && tag.getCode() != 0);
			reader.close();
			// 若成功解密，将解密后的数据写入文件
			if(swfData != null) {
				FileOutputStream fos = new FileOutputStream(destFile);
				fos.write(swfData);
				fos.close();
				System.out.println("done~");
			} else {
				System.out.println("damn!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
