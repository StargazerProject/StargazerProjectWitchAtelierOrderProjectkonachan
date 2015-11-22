package com.stargazer.witchatelier.util.socketnio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import com.stargazer.witchatelier.util.system.PropertiesUtil;

public class NIOServer {
	public static <T> T receiveData(SocketChannel socketChannel)
			throws IOException {
		T t = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ByteBuffer buffer = ByteBuffer.allocate(10240);

		try {
			byte[] bytes;
			int size = 0;
			while ((size = socketChannel.read(buffer)) >= 0) {
				buffer.flip();
				bytes = new byte[size];
				buffer.get(bytes);
				baos.write(bytes);
				buffer.clear();
			}
			bytes = baos.toByteArray();
			Object obj = SerializableUtil.toObject(bytes);
			t = (T) obj;
		} finally {
			try {
				baos.close();
			} catch (Exception ex) {
				System.out.println("Object Message: "+ex.getMessage());
			}
		}
		return t;
	}

	public static void sendData(SocketChannel socketChannel,Object object) throws IOException {
		byte[] bytes = SerializableUtil.toBytes(object);
		ByteBuffer buffer = ByteBuffer.wrap(bytes);
		socketChannel.write(buffer);
	}
}