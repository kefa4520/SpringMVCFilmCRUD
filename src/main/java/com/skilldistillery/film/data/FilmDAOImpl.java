package com.skilldistillery.film.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.film.entities.Actor;
import com.skilldistillery.film.entities.Film;

public class FilmDAOImpl implements FilmDAO {

	private String user = "student";
	private String pass = "student";

	private String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=MST";

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Film findFilmById(int filmId) throws SQLException {
		Film film = null;

		Connection conn = DriverManager.getConnection(URL, user, pass);

		String sql = "SELECT * FROM film WHERE id = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, filmId);

		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			film = new Film();
			film.setId(rs.getInt("id"));
			film.setTitle(rs.getString("title"));
			film.setDescription(rs.getString("description"));
			film.setReleaseYear(rs.getInt("release_year"));
			film.setLanguageId(rs.getInt("language_id"));
			film.setRentalDuration(rs.getInt("rental_duration"));
			film.setRentalRate(rs.getDouble("rental_rate"));
			film.setLength(rs.getInt("length"));
			film.setReplacementCost(rs.getDouble("replacement_cost"));
			film.setRating(rs.getString("rating"));
			film.setSpecialFeatures(rs.getString("special_features"));
			film.setActors(findActorsByFilmId(filmId));
			film.setLanguageString(languageFromId(filmId));

		}
		rs.close();
		ps.close();
		conn.close();

		return film;

	}

	@Override
	public Actor findActorById(int actorId) throws SQLException {
		Actor actor = null;

		Connection conn = DriverManager.getConnection(URL, user, pass);
		String sql = "SELECT id, first_name, last_name FROM actor WHERE id = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, actorId);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			actor = new Actor();
			actor.setId(rs.getInt(1));
			actor.setFirstName(rs.getString(2));
			actor.setLastName(rs.getString(3));
//		    actor.setFilms(findFilmsByActorId(actorId)); // An Actor has Films
		}
		rs.close();
		ps.close();
		conn.close();

		return actor;

	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) throws SQLException {
		List<Actor> actors = new ArrayList<>();

		Connection conn = DriverManager.getConnection(URL, user, pass);
		String sql = "SELECT actor.* " + "FROM actor JOIN film_actor ON film_actor.actor_id = actor.id "
				+ "JOIN film ON film.id = film_actor.film_id WHERE film_id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, filmId);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			int actorId = rs.getInt("id");
			String actorFirstName = rs.getString("first_name");
			String actorLastName = rs.getString("last_name");
			Actor actor = new Actor(actorId, actorFirstName, actorLastName);
			actors.add(actor);

		}
		rs.close();
		stmt.close();
		conn.close();

		return actors;
	}

	@Override
	public List<Film> findFilmByKeyword(String keyword) throws SQLException {
		List<Film> films = new ArrayList<>();
		Film film = null;

		Connection conn = DriverManager.getConnection(URL, user, pass);

		String sql = "SELECT * FROM film WHERE title LIKE ? OR description like ?";

		PreparedStatement ps = conn.prepareStatement(sql);

		ps.setString(1, "%" + keyword + "%");
		ps.setString(2, "%" + keyword + "%");

		ResultSet rs = ps.executeQuery();

		while (rs.next()) {

			film = new Film();
			film.setId(rs.getInt("id"));
			film.setTitle(rs.getString("title"));
			film.setDescription(rs.getString("description"));
			film.setReleaseYear(rs.getInt("release_year"));
			film.setLanguageId(rs.getInt("language_id"));
			film.setRentalDuration(rs.getInt("rental_duration"));
			film.setRentalRate(rs.getDouble("rental_rate"));
			film.setLength(rs.getInt("length"));
			film.setReplacementCost(rs.getDouble("replacement_cost"));
			film.setRating(rs.getString("rating"));
			film.setSpecialFeatures(rs.getString("special_features"));
			film.setActors(findActorsByFilmId(rs.getInt("id")));
			film.setLanguageString(languageFromId(rs.getInt("id")));
			films.add(film);
		}
		rs.close();
		ps.close();
		conn.close();
		return films;
	}

	@Override
	public String languageFromId(int filmId) throws SQLException {
		String language = null;

		Connection conn = DriverManager.getConnection(URL, user, pass);

		String sql = "SELECT language.name FROM language"
				+ " JOIN film ON film.language_id = language.id WHERE film.id = ?";

		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, filmId);

		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			language = rs.getString("language.name");
		}
		rs.close();
		ps.close();
		conn.close();
		return language;
	}

	@Override
	public Film createFilm(Film film) throws SQLException {
		System.out.println(film);
		Film newFilm = null;
		int newFilmId = 0;
//		int languageId = 4;
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(URL, user, pass);
			conn.setAutoCommit(false); // START TRANSACTION
			String sql = "INSERT INTO film (title, description, release_year, language_id, rating) VALUES (?,?,?,4,?)";
			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, film.getTitle());
			stmt.setString(2, film.getDescription());
			stmt.setInt(3, film.getReleaseYear());
//			stmt.setInt(4, film.getLanguageId(languageId));
			stmt.setString(4, film.getRating());
			int updateCount = stmt.executeUpdate();
//			System.out.println(updateCount); // make sure its not 0
			if (updateCount == 1) {
				ResultSet keys = stmt.getGeneratedKeys();
				System.out.println("Keys are: " + keys);
				while (keys.next()) {

					newFilmId = keys.getInt(1);
					
					System.out.println("In new film id: " + newFilmId);
					film.setId(newFilmId);

				}
			} else {
				
				film = null;
				
			}

			conn.commit(); // COMMIT TRANSACTION

		} catch (Exception sqle) {
			sqle.printStackTrace();
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException sqle2) {
					System.err.println("Error trying to rollback");
				}
			}
//			throw new RuntimeException("Error inserting film");
		}
//		newFilm = findFilmById(newFilmId);

		return film;
	}

	@Override
	public boolean deleteFilm(int filmId) throws SQLException {
		
		Connection conn = null;
		  try {
		    conn = DriverManager.getConnection(URL, user, pass);
		    conn.setAutoCommit(false); // START TRANSACTION
		    String sql = "DELETE FROM film WHERE id = ?";
		    PreparedStatement stmt = conn.prepareStatement(sql);
		    stmt.setInt(1, filmId);
		    
		    stmt.executeUpdate();

		    conn.commit();             // COMMIT TRANSACTION
		    
		  }
		  catch (SQLException sqle) {
		    sqle.printStackTrace();
		    if (conn != null) {
		      try { conn.rollback(); }
		      catch (SQLException sqle2) {
		        System.err.println("Error trying to rollback");
		      }
		    }
		    return false;
		  }
		  return true;
		
	}

}
