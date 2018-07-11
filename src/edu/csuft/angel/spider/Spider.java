package edu.csuft.angel.spider;

import java.awt.event.ItemEvent;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * 网络爬虫
 * @author acer
 *
 */
public class Spider implements Runnable{
	/**
	 * 构造方法
	 * @param url网站的路径
	 * @param films 下载的电影列表
	 */
	String url;
	
	private List<Film> films;
	
	public Spider(String url, List<Film> films) {
		this.url = url;
		this.films = films;
	}
	
	public void run() {
		try {
			//利用jsoup打开网页获取一个document// 获得 item 元素
			org.jsoup.nodes.Document doc=Jsoup.connect(url).get();
			//elements 集合返回包含表单中所有元素的数组
			Elements es=doc.select(".grid_view .item");
			// 遍历一页
			for(Element e:es) {
				Film film=new Film();
				film.id=Integer.parseInt(e.select("em").get(0).text());
				film.title=e.select(".info .title").get(0).text();
				film.poster=e.select(".pic img").get(0).attr("src");
				film.star=Double.parseDouble(e.select(".star .rating_num").get(0).text());
				String ratingString[]=e.select(".star span").get(3).text().split("人");
				film.rating=ratingString[0];
				film.quote=e.select(".quote .inq").text(); 
				
				String rest=e.select(".info .bd p").get(0).text();
				//分隔导演和主演
				String getDirectorReg="\\s主演";
				String []directors=rest.split(getDirectorReg);
				film.director=directors[0];
				System.out.println(film.director);
				//取出上映时间
				String getTimeReg="\\d{4}";
				Pattern pattern=Pattern.compile(getTimeReg);
				if(directors.length>1) {
					Matcher matcher = pattern.matcher(directors[1]); 
				    if(matcher.find()) film.time=Integer.parseInt(matcher.group());
				//通过时间分隔出主演
					String getActReg="\\s\\d{4}";
					String []actors=directors[1].split(getActReg);
					film.actor="主演"+actors[0];
				//分隔出国家和剧情
					if(actors.length>1) {
						String getCountryReg="/\\s";
						String []last=actors[1].split(getCountryReg);
						film.country=last[1];
						film.type=last[2];
					}
					
				}
				else {
					Matcher matcher = pattern.matcher(directors[0]); 
				    if(matcher.find()) film.time=Integer.parseInt(matcher.group());
				    //通过时间分隔出主演
					String getActReg="\\s\\d{4}";
					String []actors=directors[0].split(getActReg);
					//无主演时导演要重新获取
					film.director=actors[0];
					//没有directors说明没主演
					film.actor="无主演";
				//分隔出国家和剧情
					if(actors.length>1) {
						String getCountryReg="/\\s";
						String []last=actors[1].split(getCountryReg);

						film.country=last[1];
						film.type=last[2];
					}
				}
				films.add(film);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}