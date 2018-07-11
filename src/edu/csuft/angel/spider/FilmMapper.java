package edu.csuft.angel.spider;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * FilmMapper�ӿ�ʵ�����ݲ������ݿ���
 * @author acer
 *
 */
@Mapper
public interface FilmMapper {
	//insert into film() values()
	//void run(Film film);
	
	@Insert("insert into film(id,title,poster,star,rating,quote,director,actor,time,country,type) values(#{id},#{title},#{poster},#{star},#{rating},#{quote},#{director},#{actor},#{time},#{country},#{type})")
	void insert(Film f);
	
	@Select("select * from film")
	List<Film> findAll();
	
}
