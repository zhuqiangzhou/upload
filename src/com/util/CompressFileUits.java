package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
//解压
public class CompressFileUits {

	//sourceFile: 本地目录+文件
	//contentPath:本地目录
	public static void unPack(String sourceFile,String destFile) {
		File srcFile = new File(sourceFile);
//		ZipFile zfile = new ZipFile(sourceFile, GBK);// 连接待解压文件 "utf-8"会乱码

		ZipFile zipFile = null;
//		String targetFile = contentPath;
		File credirectory = new File(destFile); // 指定文件目录
		if (!credirectory.exists()) {
			credirectory.mkdirs();
		}
		InputStream in = null;
		FileOutputStream out = null;
//		String savefilename = saveFilename.substring(0,saveFilename.lastIndexOf("."));
		try {
			zipFile = new ZipFile(srcFile,Charset.forName("gbk"));
			Enumeration<?> entries = zipFile.entries();
			while (entries.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) entries.nextElement();
				String entryName = entry.getName();
				System.out.println("解压" + entry.getName());
				
				if(entryName.contains("_MACOSX")|| entryName.contains(".DS_Store")) {
					continue;
				}
				if (entryName.contains("www_android_uat") ||
						entryName.contains("www_android_test") || 
						entryName.contains("www_ios_uat") || 
						entryName.contains("www_ios_test")) {
					
					
					if (entry.isDirectory()) {
						String name = entry.getName();
						name = name.substring(0, name.length() - 1);
						File createDirectory = new File(destFile+File.separator+name);
						createDirectory.mkdirs();
					} else {
						int index = entryName.lastIndexOf(File.separator);
						if (index != -1) {
							File createDirectory = new File(destFile
									+ File.separator
									+ entryName.substring(0, index));
							createDirectory.mkdirs();
						}
						index = entryName.lastIndexOf("/");
						if (index != -1) {
							File createDirectory = new File(destFile+File.separator
									+ entryName.substring(0, index));
							createDirectory.mkdirs();
						}
						File unpackfile = new File(destFile+File.separator
								+ entry.getName());
						in = zipFile.getInputStream(entry);
						out = new FileOutputStream(unpackfile);
						int c;
						byte[] by = new byte[1024];
						while ((c = in.read(by)) != -1) {
							out.write(by, 0, c);
						}
						out.flush();
						in.close();
						out.close();
					}
				} else {
					throw new RuntimeException("上传文件有误");
				}
				
			}
			System.out.println("******************解压完毕********************");

		} catch (Exception e) {
			throw new RuntimeException("unzip error from ZipUtils", e);
		} finally {

			if (zipFile != null) {

				try {

					zipFile.close();

				} catch (IOException e) {

					e.printStackTrace();

				}

			}
		}
		
	}
	// 复制文件
	public static void copyDocumentToTarget(String sourcepath,String targetFile) throws IOException {
		// 创建字节缓冲流对象
		
        long startTime = System.currentTimeMillis();
		FileInputStream fileInputStream = new FileInputStream(new File(sourcepath));
        FileOutputStream fileOutputStream = new FileOutputStream(targetFile);
 
        //获取通道
        FileChannel inChannel = fileInputStream.getChannel();
        FileChannel outChanel = fileOutputStream.getChannel();
 
        //分配缓冲区的大小
        ByteBuffer buf = ByteBuffer.allocate(1024);
 
        //将通道中的数据存入缓冲区
        while (inChannel.read(buf) != -1) {
            buf.flip();//切换读取数据的模式
            outChanel.write(buf);
            buf.clear();
        }
        outChanel.close();
        inChannel.close();
        fileInputStream.close();
        fileOutputStream.close();
        long end = System.currentTimeMillis();
        System.out.println("nioCopyTest1耗费时间:"+(end-startTime));

	}


		
}
