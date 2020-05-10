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
	
	@RequestMapping(path="filmById.do", method=RequestMethod.GET)
	public ModelAndView viewFilmByIDPage() throws SQLException {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("WEB-INF/views/filmbyid.jsp");

		return mv;
	}
	@RequestMapping(path="filmById.do", params="id", method=RequestMethod.GET)
	public ModelAndView filmByID(int id) throws SQLException { // int id here has to match params="id"
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("WEB-INF/views/filmbyidresults.jsp");
		mv.addObject("film", filmDao.findFilmById(id));
		mv.addObject("language", filmDao.languageFromId(id));
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
		if (film != null && film.getTitle() != "" && film.getDescription() != "" && film.getRating() != "") {
		Film newFilm = filmDao.createFilm(film);
			System.out.println("new film is: " + newFilm);
			System.out.println("film is" + film);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("WEB-INF/views/results.jsp");
		mv.addObject("film", newFilm);
		mv.addObject("language", filmDao.languageFromId(newFilm.getId()));
		return mv;
		}
		else {
			ModelAndView mv = new ModelAndView();
			mv.setViewName("WEB-INF/views/error.jsp");
			return mv;
		}
	}
	
	@RequestMapping(path="deleteFilm.do", params="filmId", method=RequestMethod.POST)
	public ModelAndView deletefilmByID(int filmId) throws SQLException { 
		if (filmId > 1000) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("WEB-INF/views/filmdeleted.jsp");
		mv.addObject("deleted", filmDao.deleteFilm(filmId));
		return mv;
		}
		else {
			ModelAndView mv = new ModelAndView();
			mv.setViewName("WEB-INF/views/errordeletingfilm.jsp");
			return mv;
		}
	}


	public void setFilmDao(FilmDAO filmDao) {
		this.filmDao = filmDao;
	}
}
