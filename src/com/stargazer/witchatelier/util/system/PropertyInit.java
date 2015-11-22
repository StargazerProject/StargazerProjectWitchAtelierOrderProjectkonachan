package com.stargazer.witchatelier.util.system;

public class PropertyInit {
	public PropertyInit() {
		/**System Property**/
		System.setProperty("ControlMessageStatus", "true");
		System.setProperty("CenterControlMessageStatus", "true");
		System.setProperty("webStatus", "konachan");
		System.setProperty("cellsFile", "StartBigCGCellsServerkonachan.jar");
		/**Cells Server Property**/
		System.setProperty("BigcgSystemCellsName", "PisualCellsSystemBigCGSystemCellskonachan");
		System.setProperty("BigcgSystemCellsIP", "10.0.1.25");
		System.setProperty("BigcgSystemCellsPort", "10740");
		/**Pisual YandeCG Property**/
		System.setProperty("MongodbIP", "10.0.1.25");
		System.setProperty("MongodbPort", "27017");
		System.setProperty("MongodbDataBase", "PisualkonachanBigFile");
		System.setProperty("MongodbDataBaseCollection", "konachanBigFile");
		System.setProperty("gettestFromWeb", "185.66.140.115");
		System.setProperty("getNewNumFromWeb", "http://konachan.com/post.xml?limit=1");
		System.setProperty("getYandeCGPages", "http://konachan.com/post.xml?limit=100&page=");
		/**Pisual YandeCG GridFS Property**/
		System.setProperty("MongodbYandeCGGridFSCollection", "konachanGridFSBigFile");
		System.setProperty("MongodbYandeCGGridFSTempPath", "f://kic/");//从网站下载及上传的临时文件夹
		System.setProperty("MongodbYandeCGGridFSDownloadTempPath", "../home/GridFSDownLoadTemp/");
		/**Order Cache Property**/
		System.setProperty("OrderCacheMongodbIP", "10.0.1.25");
		System.setProperty("OrderCacheMongodbPort", "27017");
		System.setProperty("OrderCacheMongodbDataBase", "PisualOrderCache");
		System.setProperty("OrderCacheMongodbDataBaseCollection", "OrderCache");
		/**Order BackUP Cache Property**/
		System.setProperty("OrderBackUPCacheMongodbIP", "10.0.1.25");
		System.setProperty("OrderBackUPCacheMongodbPort", "27017");
		System.setProperty("OrderBackUPCacheMongodbDataBase", "PisualOrderBackUPCache");
		System.setProperty("OrderBackUPCacheMongodbDataBaseCollection", "OrderBackUPCache");
		/**Message disruptor Property**/
		System.setProperty("CenterControlMessageServiceMessageDisruptorStatus", "true");
		System.setProperty("NullQueueWaitTime", "1000");
		/**log Property**/
		/**Summus Densitate Segmentation Service Transaction Level Task disruptor Property**/
		System.setProperty("SummusDensitateSegmentationServiceDisruptorStatus", "true");
		System.setProperty("NullQueueWaitTime", "1000");
		/**Summus Densitate Segmentation Service System Level Task disruptor Property**/
		System.setProperty("SystemServiceDisruptorStatus", "true");
		System.setProperty("NullQueueWaitTime", "1000");
		/**Summus Densitate Segmentation Service**/
		System.setProperty("SummusDensitateSegmentationServiceListenStatus", "true");
	}
}
