package com.A6.moviecrud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.A6.moviecrud.entity.Movie;
import com.A6.moviecrud.repository.MovieRepository;

@Controller
public class MovieController {

	@Autowired
	MovieRepository repository;

	@GetMapping("/")
	public String loadMain() {
		return "main.html";
	}

	@GetMapping("/insert")
	public String loadInsertForm() {
		return "insert.html";
	}

	@PostMapping("/insert")
	public String saveRecord(Movie movie, ModelMap map) {
		repository.save(movie);
		map.put("message", "Movie Added Success");
		return "main.html";
	}

	@GetMapping("/fetch")
	public String fetch(ModelMap map) {
		List<Movie> movies = repository.findAll();
		if (movies.isEmpty()) {
			map.put("message", "No Records Present");
			return "main.html";
		}
		map.put("movies", movies);
		return "fetch.html";
	}	
	
	@GetMapping("/delete")
	public String deleteRecord(Long id, ModelMap map) {
	    if (repository.existsById(id)) {
	        repository.deleteById(id);
	        map.put("message", "Movie Deleted Successfully");
	    } else {
	        map.put("message", "Movie with ID " + id + " not found");
	    }
	    List<Movie> movies = repository.findAll();
	    map.put("movies", movies);
	    return "fetch";  // reload fetch page with updated list
	}
	
//	@GetMapping("/delete")
//	public String removebyId(@RequestParam Long id,ModelMap map ) {
//		repository.deleteById(id);
//		map.put("message", "record deleted");
//		return "fetch.html";
//	}
//	
	
	
	@GetMapping("/edit")
	public String loadEditForm(Long id, ModelMap map) {
	    Movie movie = repository.findById(id).orElse(null);
	    if (movie == null) {
	        map.put("message", "Movie with ID " + id + " not found");
	        return "fetch";
	    }
	    map.put("movie", movie);
	    return "edit";  // loads edit.html
	}
	
	@PostMapping("/update")
	public String updateRecord(Movie movie, ModelMap map) {
	    if (repository.existsById(movie.getId())) {
	        repository.save(movie);  // save() works as insert + update
	        map.put("message", "Movie Updated Successfully");
	    } else {
	        map.put("message", "Movie with ID " + movie.getId() + " not found");
	    }
	    map.put("movies", repository.findAll());
	    return "fetch";  // go back to list after update
	}

	
}
