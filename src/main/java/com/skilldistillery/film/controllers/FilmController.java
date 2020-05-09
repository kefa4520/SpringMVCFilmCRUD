package com.skilldistillery.film.controllers;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.skilldistillery.film.data.FilmDAO;
import com.skilldistillery.film.entities.Film;

@Controller
public class FilmController {
	
	@Autowired
	private FilmDAO filmDao;
	
	
	@RequestMapping("home.do") // test method
	public ModelAndView homeView() throws SQLException {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("WEB-INF/views/home.jsp");
//		mv.addObject("film", filmDao.findFilmById(filmId));
		return mv;
	}
	
	@RequestMapping(path="addFilm.do", method=RequestMethod.GET)
	public ModelAndView goToAddFilmPage() throws SQLException {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("WEB-INF/views/filmform.jsp");
		
		return mv;
	}
	
	@RequestMapping(path="addFilm.do", method=RequestMethod.POST)
	public ModelAndView processFilm(Film film) throws SQLException {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("WEB-INF/views/result.jsp");
		mv.addObject("film", filmDao.createFilm(film));
		return mv;
	}


	public void setFilmDao(FilmDAO filmDao) {
		this.filmDao = filmDao;
	}
}
