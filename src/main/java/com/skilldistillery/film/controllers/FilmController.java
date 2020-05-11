package com.skilldistillery.film.controllers;

import java.sql.SQLException;
import java.util.List;

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
	@RequestMapping(path="filmById.do", params="id", method= {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView filmByID(int id) throws SQLException { // int id here has to match params="id"
		Film film = filmDao.findFilmById(id);
		//List<Actor> actors
		ModelAndView mv = new ModelAndView();
		if(film != null) {
		mv.setViewName("WEB-INF/views/filmbyidresults.jsp");
		mv.addObject("film", filmDao.findFilmById(id));
		mv.addObject("actors", filmDao.findActorsByFilmId(id));
		mv.addObject("languageString", filmDao.languageFromId(film.getLanguageId()));
		mv.addObject("category", filmDao.findCategoriesByFilmId(id));
		return mv;
		} else {
			mv.setViewName("WEB-INF/views/error.jsp");
		return mv;
		}
	}
	
	@RequestMapping(path="filmByKeyword.do", method= RequestMethod.GET)
	public ModelAndView filmByKeyword() throws SQLException { 
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("WEB-INF/views/filmbykeyword.jsp");
		return mv;
	}
	
	@RequestMapping(path="filmByKeyword.do", params= "keyword", method= RequestMethod.GET)
	public ModelAndView filmByKeyword(String keyword) throws SQLException { 
		
		List<Film> films = filmDao.findFilmByKeyword(keyword);
		ModelAndView mv = new ModelAndView();
		if(!films.isEmpty()) {
		
		for (Film film : films) {
			film.setActors(filmDao.findActorsByFilmId(film.getId()));
			film.setLanguageString(filmDao.languageFromId(film.getLanguageId()));
			film.setCategory(filmDao.findCategoriesByFilmId(film.getId()));
//		mv.addObject("languageString", filmDao.languageFromId(film.getLanguageId()));
//		mv.addObject("actors", filmDao.findActorsByFilmId(film.getId()));
		}
		mv.setViewName("WEB-INF/views/keywordresults.jsp");
		mv.addObject("films", films);
		return mv;
		} else {
			mv.setViewName("WEB-INF/views/error.jsp");
			return mv;
		}

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
		mv.addObject("languageString", filmDao.languageFromId(1));
		return mv;
		}
		else {
			ModelAndView mv = new ModelAndView();
			mv.setViewName("WEB-INF/views/error.jsp");
			return mv;
		}
	}
	@RequestMapping(path="updateFilm.do", params="title", method=RequestMethod.POST)
	public ModelAndView updateFilm(Film film) throws SQLException {
		if (film != null && film.getTitle() != "" && film.getDescription() != "" && film.getRating() != "") {
			System.out.println("********: " + film);
			Film updatedFilm = filmDao.updateFilm(film);
//			System.out.println("new film is: " + updatedFilm);
//			System.out.println("film is" + film);
			ModelAndView mv = new ModelAndView();
			mv.setViewName("WEB-INF/views/filmbyidresults.jsp");
			mv.addObject("film", updatedFilm);
			mv.addObject("languageString", filmDao.languageFromId(1));
			mv.addObject("category", filmDao.findCategoriesByFilmId(film.getId()));
			mv.addObject("actors", filmDao.findActorsByFilmId(film.getId()));
			
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
//		if (filmId > 1000) {
		boolean isdeleted = filmDao.deleteFilm(filmId);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("WEB-INF/views/filmdeleted.jsp");
		mv.addObject("deleted", filmDao.deleteFilm(filmId));
		mv.addObject("isdeleted", isdeleted);
		return mv;
		}
//		else {
//			ModelAndView mv = new ModelAndView();
//			mv.setViewName("WEB-INF/views/errordeletingfilm.jsp");
//			return mv;
//		}
//	}


	public void setFilmDao(FilmDAO filmDao) {
		this.filmDao = filmDao;
	}
}
