package edu.csuft.angel.spider;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * ͼƬ���أ�ImgDownload��Ķ���
 * @author acer
 *
 */
public class ImgDownload implements Runnable{
	
	
	String httpurl;/*** ͼƬ�������� ��ַ*/

	String imgFileName;/*** ͼƬ������ļ���*/
	
	String imgDownloadPath;/*** ͼƬ�ļ����ش����·��*/
	
	/**
	 * ���췽��
	 * @param ������ַ
	 * @param ͼƬ����
	 * @param ���ش��·��
	 */
	public ImgDownload(String url, String name, String path) {
		this.httpurl = url;
		this.imgFileName = name;
		this.imgDownloadPath = path;
	}

	/**
	 * �����ļ�
	 */
	public void run() {
		try {
			URL url = new URL(httpurl);
			// ������Դ
			HttpURLConnection conn;
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5 * 1000);// ���ó�ʱ��Ϊ5��
			
			// ��ֹ���γ���ץȡ������403����
			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			//���ȿɱ���ڴ�����
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			// �����Ӷ����ȡ������
			try(InputStream inputStream = conn.getInputStream()){
				byte[] buffer = new byte[1024*8];
				int len;
				while ((len = inputStream.read(buffer)) != -1) {
					bos.write(buffer, 0, len);
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			//תΪ�ַ�����д�뻺��
			byte []data=bos.toByteArray();
			bos.close();
			
			// ����ļ�����·���Ƿ���ڣ��������ڣ��򴴽�
			File saveDir = new File(imgDownloadPath);
			if (!saveDir.exists()) 
					saveDir.mkdir();
			File file = new File(imgDownloadPath + "/" + imgFileName + ".jpg");
			
			try(FileOutputStream out=new FileOutputStream(file)){
				out.write(data);
				out.close();
				System.out.println("���سɹ�:"+imgDownloadPath+imgFileName);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}		
}
		