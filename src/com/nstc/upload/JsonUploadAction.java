package com.nstc.upload;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

public class JsonUploadAction extends HttpServlet {

	private String contentPath;
	private String uploadJsonPath;

	public void init() {
		contentPath = getServletContext().getInitParameter("content");
		uploadJsonPath = getServletContext().getInitParameter("uploadjson");
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		String message = "";
		try {
			// 使用apache文件上传组件处理文件上传步骤
			// 1.创建一个DiskFileItemFactory工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 设置工厂的缓冲区大小，当上传的文件大小超过缓冲区的时，就会生成一个临时文件存放到指定的目录中
			factory.setSizeThreshold(1024 * 100);

			// 2.创建一个文件上传解析器
			ServletFileUpload upload = new ServletFileUpload(factory);
			// 解决上传文件名的乱码
			upload.setHeaderEncoding("utf-8");
			// 3.判断提交上来的数据是否是上传表单的数据
			if (!ServletFileUpload.isMultipartContent(request)) {
				return;
			}

			// 设置上传单个文件的大小的最大值，目前是设置为1024*1024字节，也就是10MB
			upload.setFileSizeMax(1024 * 1024 * 100);
			// 设置上传文件总量的最大值，最大值=同时上传的多个文件的大小的最大值的和，目前设置为10MB
			upload.setSizeMax(1024 * 1024 * 100);

			// 4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
			List<FileItem> list = upload.parseRequest(request);

			Map<String, String> map = new HashMap<String, String>();
			for (FileItem fileItem : list) {
				// 如果fileitem里面封装的是普通输入项的数据
				if (fileItem.isFormField()) {
					String name = fileItem.getFieldName();
					// 解决普通输入项的数据错乱中文乱码问题
					String value = fileItem.getString("utf-8");
					System.out.println(name + "=" + value);
					map.put(name, value);
				} else {// 如果封装的是上传文件
					String filename = fileItem.getName();
					System.out.println(filename);
					if (filename == null || filename.trim().equals("")) {
						continue;
					}
					
					
					// 处理获取到的上传文件的文件名的路径部分，只保留文件名部分

					filename = filename.substring(filename
							.lastIndexOf(File.separator) + 1);

					// 得到上传文件的扩展名
					String fileExtName = filename.substring(filename
							.lastIndexOf("."));
					
					if (!"version_ios.json".equals(filename) && !"version_android.json".equals(filename)) {
						throw new RuntimeException("文件名错误，文件名必须是version_ios.json或者version_android.json");
					}
					
					
					if (!(".json".equals(fileExtName))) {
						throw new RuntimeException("上传失败");
					}
					// 如果需要限制上传的文件类型，那么可以通过文件的扩展名来判断上传的文件类型是否合法
					System.out.println("上传的文件的扩展名是：" + fileExtName);

					// 获取item中的上传文件的输入流
					InputStream in = fileItem.getInputStream();

					// 得到文件保存的名称
					String saveFilename = makeFileName(filename);

					// 得到文件的保存目录
					String realSavePath = makePath(uploadJsonPath, map);

					// 创建一个文件输出流
					FileOutputStream out = new FileOutputStream(realSavePath
							+ File.separator + saveFilename);
					// 创建一个缓冲区
					byte[] buffer = new byte[1024];
					// 判断输入流中的数据是否已经读完的标识(len = is.read(buffer))>0 就表示in里面还有数据
					int len = 0;
					// 循环将输入流读入到缓冲区中，
					while ((len = in.read(buffer)) > 0) {
						// 使用FileOutputStream输出流将缓冲区的数据写入到指定的目录
						out.write(buffer, 0, len);
					}
					// 关闭输入流
					in.close();
					// 关闭输出流
					out.close();
					// 删除处理文件上传时生成的临时文件
					fileItem.delete();

					// 上传完之后复制到最终目标文件夹
					String customer = (String) map.get("customer");

					File contentFile = new File(contentPath + File.separator
							+ customer);
					if (!contentFile.exists()) {
						contentFile.mkdirs();
					}

					String realPath = realSavePath + File.separator+ saveFilename; // E:\\upload\cn\test\2019-06-12\xrapp-test-2019-06-12
											// 18-07-52-783.ipa
					// 上传完之后获取最新的文件并且复制到另个文件夹
					String destPath = readAndCopyFile(realPath, contentPath,customer,filename);
					

					request.setAttribute("realSavePath", realSavePath
							+ File.separator + saveFilename);
					request.setAttribute("contentPath", destPath);
				}
			}
			message = "上传成功";
			request.setAttribute("message", message);
			request.getRequestDispatcher("/WEB-INF/success.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			message = "上传失败";
			request.setAttribute("message", message);
			try {
				request.getRequestDispatcher("/WEB-INF/fail.jsp").forward(
						request, response);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}

	// 文件全名+后缀
	private String makeFileName(String fileName) {
		long time = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd.HHmmss.SSS");
		String TIME = sdf.format(time);
//		String name = fileName.substring(0,fileName.lastIndexOf("."));
		return  fileName + "-"  + TIME  ;
	}
	
	public String readAndCopyFile(String realPath, String contentPath,String customer,String filename) throws IOException {
		File file = new File(realPath);
		if (file.isFile()) {
			String destPath = contentPath + File.separator + customer
					+ File.separator + filename; // 目标文件
			File f1 = new File(destPath);
			if (f1.exists()) {
				f1.delete();
			}
//			copyDocument(sourcepath, destPath);
			FileUtils.copyFile(file,f1);	
			
			return destPath;
		} else {
			throw new RuntimeException("文件不存在");
		}
	}



	
	// 文件上传的真实目录
	private String makePath(String savePath, Map<String,String> map) {

		long time = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String TIME = sdf.format(time);

		String customer = (String) map.get("customer");
		// 构造新的保存目录
		String dir = savePath + File.separator + customer + File.separator + TIME;
		// File既可以代表文件也可以代表目录
		File file = new File(dir);
		// 如果目录不存在
		if (!file.exists()) {
			// 创建目录
			file.mkdirs();
		}
		return dir;
	}
	
	
	
	

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request, response);
	}

}
