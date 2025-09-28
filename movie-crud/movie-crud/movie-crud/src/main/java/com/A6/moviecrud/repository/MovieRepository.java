package com.A6.moviecrud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.A6.moviecrud.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

}
