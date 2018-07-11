package edu.csuft.angel.spider;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.google.gson.Gson;


/**
 * ����app
 * @author acer
 *
 */
public class App {
	
	public static void main(String[] args) {
		List<Film> films = Collections.synchronizedList(new LinkedList<>());
		String url = "https://movie.douban.com/top250";
		ExecutorService pool = Executors.newFixedThreadPool(4);
		pool.execute(new Spider(url, films));
		
		//�������ʮҳ
		for (int i = 1; i < 10; i++) {
			url = String.format("https://movie.douban.com/top250?start=%d&filter=", 25 * i);
			pool.execute(new Spider(url, films));
		}
		pool.shutdown();
		
		//�ȴ�,���̳߳ص�����ȫ��ִ����֮��,isTerminated����true
		while (true) {
			if (pool.isTerminated()) {
				try {
					
					//���浽���ݿ�
					//��ûỰ����(���ݿ�����ӳ�)
					SqlSessionFactory factory;
					factory = new SqlSessionFactoryBuilder().build(new FileReader("config.xml"));
					//�ӻỰ������ȡһ������
					SqlSession session = factory.openSession();
					//���һ��mapper(����:��ħ��)
					FilmMapper mapper=session.getMapper(FilmMapper.class);
					
					//�������ݿ�
					for(Film f:films) {
						System.out.println(f.toString());
						mapper.insert(f);
					}
					session.commit();//�Ự�ύ
					session.close();
					System.out.println("�洢�ɹ�");
					DownImage(films);
					System.out.println("ͼƬ����ɹ�");
				}catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			} else {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}	

	/**
	 *����Ҫ���صĵ�Ӱ�����б�
	 * @param films
	 */
	private static void DownImage(List<Film> films) {
		ExecutorService pool = Executors.newFixedThreadPool(8);
		for (Film film : films) {
			//��񻯺�������
			String imgName= String.format("%03d_%s", film.getId(), film.getTitle().split(" ")[0]);
			pool.execute(new ImgDownload(film.poster,imgName,"D:\\2018kcsj\\Spider_filmImg"));
		}
		pool.shutdown();
	}

	
}
 