package com.pisual.www.cells.yande.util;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;

import com.pisual.witchatelier.model.YandeCGGridFS;


/**
 * Pisual IO 工具包
 * 
 * @author felixerio
 * **/
public class PisualIOUtil {
	/** 日志初始化 **/
	static Logger logger = Logger.getLogger(PisualIOUtil.class.getName());
	/** 获取指定目录下所有文件 自动命名ID **/
	public List<YandeCGGridFS> getPathFileList(String Url,List<YandeCGGridFS> YandeCGGridFSList) {
		File file = new File(Url);
		File[] tempList = file.listFiles();

		for (int i = 0; i < tempList.length; i++) {
			if (tempList[i].isFile()) {
				YandeCGGridFS yandeCGGridFS = new YandeCGGridFS();
				yandeCGGridFS.setFileMenuList(tempList[i].getAbsolutePath());
				yandeCGGridFS.setId(i + "");
				YandeCGGridFSList.add(yandeCGGridFS);
				System.out.println("文     件：" + tempList[i].getAbsolutePath());
			}
		}
		return null;
	}

	/**
	 * PisualCells智能文件特征分析模块<Smart feature analysis module>
	 * 获取指定文件夹下所有数据，智能命名ID及其他特征 例如： konachan 196327 black_eyes black_hair blush
	 * brown_eyes brown_hair flowers h.i.t_(59-18-45) kamijou_touma male
	 * misaka_mikoto ribbons seifuku short_hair snow to_aru_majutsu_no_index.jpg
	 * 获取完成后为 ID：196327konachanjpg yande 312953 animal_ears ass cleavage maid
	 * pantyhose tail thighhighs tsubaki_kureha utsunomiya_hetaru
	 * yuri_kuma_arashi yurigasaki_ruru yurishiro_ginko.png
	 * 获取完成后为ID：312953yandepng
	 * 
	 * 注意： 分析将排除一定的无效干扰文件 无效干扰文件列表： 1..DS_Store 2.Thumbs.db
	 * **/
	public List<YandeCGGridFS> getPathFileListSmartID(String Url,List<YandeCGGridFS> YandeCGGridFSList) {
		File file = new File(Url);
		File[] tempList = file.listFiles();
		for (int i = 0; i < tempList.length; i++) {
			if (tempList[i].isFile()) {
				if (tempList[i].getAbsolutePath().indexOf(".DS_Store") > 0 || tempList[i].getAbsolutePath().indexOf("Thumbs.db") > 0) {
					continue;
				}
				int indexNum = tempList[i].getName().lastIndexOf('.');
				if ((indexNum > -1)&& (indexNum < (tempList[i].getName().length() - 1))) {
					/**特异化部分**/
					if(tempList[i].getName().indexOf("Yande")!=-1||tempList[i].getName().indexOf("yande")!=-1||(tempList[i].getName().indexOf("konachan")!=-1))
							{	
						String filename[] = tempList[i].getName().split(" ");
						YandeCGGridFS yandeCGGridFS = new YandeCGGridFS();
					    yandeCGGridFS.setFileMenuList(tempList[i].getAbsolutePath());
					    yandeCGGridFS.setId(filename[1]);
					    	yandeCGGridFS.set_id(filename[1]);
					    YandeCGGridFSList.add(yandeCGGridFS);
							}
				}
				else
				{
					String filename[] = tempList[i].getName().split(" ");
					YandeCGGridFS yandeCGGridFS = new YandeCGGridFS();
					yandeCGGridFS.setFileMenuList(tempList[i].getAbsolutePath());
					yandeCGGridFS.setId(filename[1]);
					YandeCGGridFSList.add(yandeCGGridFS);
				}
			}
		}
		return null;
	}
	/**获取文件类型**/
	public static String getFileType(String fileName)
	{
		int indexNum = fileName.lastIndexOf('.');
		if ((indexNum > -1)&& (indexNum < (fileName.length() - 1))) {
			System.out.println(fileName.substring(indexNum + 1));
			return fileName.substring(indexNum + 1);
		}
		else
		{
			return "";
		}
	}
}
