package edu.csuft.angel.spider;
/**
 * 电影Film类的定义
 * @author acer
 *
 */
public class Film {
	
	int id;/*** 排名*/
	
	String title;/*** 标题 */
	
	String poster;/*** 海报路径*/
	
	double star;/*** 评分*/
	
	String rating;/*** 投票 */
	
	String quote;/** * 引语 */
	
	String director;/*** 导演*/
	
	String actor;/*** 主演*/
	
	String country;/*** 国家*/
	
	int time;/*** 上映时间 */
	
	String type;/*** 电影类型*/
	
	public Film() {
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPoster() {
		return poster;
	}
	public void setPoster(String poster) {
		this.poster = poster;
	}
	public double getStar() {
		return star;
	}
	public void setStar(double star) {
		this.star = star;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getQuote() {
		return quote;
	}
	public void setQuote(String quote) {
		this.quote = quote;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getActor() {
		return actor;
	}
	public void setActor(String actor) {
		this.actor = actor;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "Film [id=" + id + ", title=" + title + ", poster=" + poster + ", star=" + star + ", rating=" + rating
				+ ", quote=" + quote + ", director=" + director + ", actor=" + actor + ", country=" + country
				+ ", time=" + time + ", type=" + type + "]";
	}
	
	
}
