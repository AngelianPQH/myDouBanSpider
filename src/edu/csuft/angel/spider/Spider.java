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
 * ��������
 * @author acer
 *
 */
public class Spider implements Runnable{
	/**
	 * ���췽��
	 * @param url��վ��·��
	 * @param films ���صĵ�Ӱ�б�
	 */
	String url;
	
	private List<Film> films;
	
	public Spider(String url, List<Film> films) {
		this.url = url;
		this.films = films;
	}
	
	public void run() {
		try {
			//����jsoup����ҳ��ȡһ��document// ��� item Ԫ��
			org.jsoup.nodes.Document doc=Jsoup.connect(url).get();
			//elements ���Ϸ��ذ�����������Ԫ�ص�����
			Elements es=doc.select(".grid_view .item");
			// ����һҳ
			for(Element e:es) {
				Film film=new Film();
				film.id=Integer.parseInt(e.select("em").get(0).text());
				film.title=e.select(".info .title").get(0).text();
				film.poster=e.select(".pic img").get(0).attr("src");
				film.star=Double.parseDouble(e.select(".star .rating_num").get(0).text());
				String ratingString[]=e.select(".star span").get(3).text().split("��");
				film.rating=ratingString[0];
				film.quote=e.select(".quote .inq").text(); 
				
				String rest=e.select(".info .bd p").get(0).text();
				//�ָ����ݺ�����
				String getDirectorReg="\\s����";
				String []directors=rest.split(getDirectorReg);
				film.director=directors[0];
				System.out.println(film.director);
				//ȡ����ӳʱ��
				String getTimeReg="\\d{4}";
				Pattern pattern=Pattern.compile(getTimeReg);
				if(directors.length>1) {
					Matcher matcher = pattern.matcher(directors[1]); 
				    if(matcher.find()) film.time=Integer.parseInt(matcher.group());
				//ͨ��ʱ��ָ�������
					String getActReg="\\s\\d{4}";
					String []actors=directors[1].split(getActReg);
					film.actor="����"+actors[0];
				//�ָ������Һ;���
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
				    //ͨ��ʱ��ָ�������
					String getActReg="\\s\\d{4}";
					String []actors=directors[0].split(getActReg);
					//������ʱ����Ҫ���»�ȡ
					film.director=actors[0];
					//û��directors˵��û����
					film.actor="������";
				//�ָ������Һ;���
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