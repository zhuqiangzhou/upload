package com.nstc.index;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JsIndexAction extends HttpServlet{
	//加载下拉选
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		String conf = getServletContext().getInitParameter("conf");
		File file = new File(conf);
		File[] listFiles = file.listFiles();
		for (File f : listFiles) {
			// 读取文件内容
			String encoding = "GBK";
			try {
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file + File.separator + f.getName()),
						encoding);// 考虑到编码
				BufferedReader br = new BufferedReader(read);
				String str;
				Map<String, String> data = new HashMap<String, String>();
				while ((str = br.readLine()) != null) {
					String[] arrays = str.split("=");
					if (arrays.length > 1) {
						String key = arrays[0].trim();
						String value = arrays[1].trim();
						data.put(key, value);
					}
				}
				if (!data.isEmpty()) {
					list.add(data);
				}
				read.close();
				br.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		request.setAttribute("kehulist", list);
		try {
			request.getRequestDispatcher("/WEB-INF/jsUpload.jsp").forward(
					request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request,HttpServletResponse response) {
		doGet(request, response);
	}
}
