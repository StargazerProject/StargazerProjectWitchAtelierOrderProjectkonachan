package com.pisual.www.cells.yande.util;
import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.nio.ch.FileChannelImpl;
import org.apache.commons.codec.digest.*;   
import org.apache.commons.io.IOUtils; 
  
/** 
 * 计算文件的MD5 
 */  
public class FileMD5 {  
    protected static char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};  
    protected static MessageDigest messageDigest = null;  
    static{  
        try{  
            messageDigest = MessageDigest.getInstance("MD5");  
        }catch (NoSuchAlgorithmException e) {  
            System.err.println(FileMD5.class.getName()+"初始化失败，MessageDigest不支持MD5Util.");  
            e.printStackTrace();  
        }  
    }  
      
    /** 
     * 计算文件的MD5 
     * @param fileName 文件的绝对路径 
     * @return 
     * @throws IOException 
     * @throws InvocationTargetException 
     * @throws IllegalArgumentException 
     * @throws IllegalAccessException 
     * @throws SecurityException 
     * @throws NoSuchMethodException 
     */  
    public static String getFileMD5String(String fileName) throws IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{  
        File f = new File(fileName);
        return getFileMD5String(f);  
    }  
      
    /** 
     * 计算文件的MD5，重载方法
     * 添加了卸载文件句柄的方法 修正了在Windows下文件无法删除的问题
     * @param file 文件对象 
     * @return 
     * @throws IOException 
     * @throws SecurityException 
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalArgumentException 
     * @throws IllegalAccessException 
     */  
    public static String getFileMD5String(File file) throws IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{  
        FileInputStream in = new FileInputStream(file);  
        FileChannel ch = in.getChannel();  
        MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());  
        messageDigest.update(byteBuffer);
        String md5 = bufferToHex(messageDigest.digest());
        Method m = FileChannelImpl.class.getDeclaredMethod("unmap", MappedByteBuffer.class);  
        m.setAccessible(true);  
        m.invoke(FileChannelImpl.class, byteBuffer);
//        
//        Method getCleanerMethod = byteBuffer.getClass().getMethod("cleaner", new Class[0]);  
//        getCleanerMethod.setAccessible(true);  
//        sun.misc.Cleaner cleaner = (sun.misc.Cleaner)   
//        getCleanerMethod.invoke(byteBuffer, new Object[0]);  
//        cleaner.clean();  
//        
        ch.close();
        in.close();
//       	 FileInputStream fis= new FileInputStream(file.getAbsolutePath());    
//         String md5 = DigestUtils.md5Hex(IOUtils.toByteArray(fis));    
//         IOUtils.closeQuietly(fis);    
//         fis.close();
        return md5;  
    }  
      
    private static String bufferToHex(byte bytes[]) {  
       return bufferToHex(bytes, 0, bytes.length);  
    }  
      
    private static String bufferToHex(byte bytes[], int m, int n) {  
       StringBuffer stringbuffer = new StringBuffer(2 * n);  
       int k = m + n;  
       for (int l = m; l < k; l++) {  
        appendHexPair(bytes[l], stringbuffer);  
       }  
       return stringbuffer.toString();  
    }  
      
    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {  
       char c0 = hexDigits[(bt & 0xf0) >> 4];  
       char c1 = hexDigits[bt & 0xf];  
       stringbuffer.append(c0);  
       stringbuffer.append(c1);  
    }  
}  