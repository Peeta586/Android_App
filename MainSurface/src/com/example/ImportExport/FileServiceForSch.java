package com.example.ImportExport;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import android.content.Context;
import android.os.Environment;

/**
 * @author LSHM
 *导入与导出文件类
 */
public class FileServiceForSch {
	private Context context;
	private File path1;

	public FileServiceForSch(Context context) {
		this.context = context;
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			// 创建一个文件夹对象，赋值为外部存储器的目录
			File sdcardDir = Environment.getExternalStorageDirectory();
			// 得到一个路径，内容是sdcard的文件夹路径和名字
			String path = sdcardDir.getPath() + "/mingli";
			// 若不存在，创建目录，可以在应用启动的时候创建
			path1 = new File(path);
			if (!path1.exists())
				path1.mkdirs();
		}
	}

	public FileServiceForSch() {
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			// 创建一个文件夹对象，赋值为外部存储器的目录
			File sdcardDir = Environment.getExternalStorageDirectory();
			// 得到一个路径，内容是sdcard的文件夹路径和名字
			String path = sdcardDir.getPath() + "/mingli";
			// 若不存在，创建目录，可以在应用启动的时候创建
			path1 = new File(path);
			if (!path1.exists())
				path1.mkdirs();
		}
	}

	/**
	 * @param filename:文件名
	 * @param content：文件内容
	 * @return
	 */
	public boolean saveSchduleToSD(String filename, String content) {
		boolean flag = false;
		FileOutputStream fileOutputStream = null;

		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			File file = new File(path1, filename);

			try {
				fileOutputStream = new FileOutputStream(file);
				fileOutputStream.write(content.getBytes());
				flag = true;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (fileOutputStream != null) {
					try {
						fileOutputStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

		return flag;
	}

	/**
	 * @param fileName
	 * @return：String类型的JsonObject对象
	 */
	public String getFileFromSDcard(String fileName) {
		FileInputStream inputStream = null;
		// 缓存的流，和磁盘无关，不需要关闭
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		File file = new File(path1, fileName);
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			try {
				inputStream = new FileInputStream(file);
				int len = 0;
				byte[] data = new byte[1024];
				while ((len = inputStream.read(data)) != -1) {
					outputStream.write(data, 0, len);
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return new String(outputStream.toByteArray());
	}
}
