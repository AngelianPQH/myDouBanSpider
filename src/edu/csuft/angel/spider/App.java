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
 * 爬虫app
 * @author acer
 *
 */
public class App {
	
	public static void main(String[] args) {
		List<Film> films = Collections.synchronizedList(new LinkedList<>());
		String url = "https://movie.douban.com/top250";
		ExecutorService pool = Executors.newFixedThreadPool(4);
		pool.execute(new Spider(url, films));
		
		//爬虫遍历十页
		for (int i = 1; i < 10; i++) {
			url = String.format("https://movie.douban.com/top250?start=%d&filter=", 25 * i);
			pool.execute(new Spider(url, films));
		}
		pool.shutdown();
		
		//等待,当线程池的任务全部执行完之后,isTerminated返回true
		while (true) {
			if (pool.isTerminated()) {
				try {
					
					//保存到数据库
					//获得会话工厂(数据库的连接池)
					SqlSessionFactory factory;
					factory = new SqlSessionFactoryBuilder().build(new FileReader("config.xml"));
					//从会话工场获取一个连接
					SqlSession session = factory.openSession();
					//获得一个mapper(反射:黑魔法)
					FilmMapper mapper=session.getMapper(FilmMapper.class);
					
					//存入数据库
					for(Film f:films) {
						System.out.println(f.toString());
						mapper.insert(f);
					}
					session.commit();//会话提交
					session.close();
					System.out.println("存储成功");
					DownImage(films);
					System.out.println("图片保存成功");
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
	 *传入要下载的电影海报列表
	 * @param films
	 */
	private static void DownImage(List<Film> films) {
		ExecutorService pool = Executors.newFixedThreadPool(8);
		for (Film film : films) {
			//规格化海报名字
			String imgName= String.format("%03d_%s", film.getId(), film.getTitle().split(" ")[0]);
			pool.execute(new ImgDownload(film.poster,imgName,"D:\\2018kcsj\\Spider_filmImg"));
		}
		pool.shutdown();
	}

	
}
 