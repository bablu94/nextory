package com.nextory.techtest.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nextory.techtest.models.Author;
import com.nextory.techtest.models.Book;


@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

	Page<Book> findAll(Pageable pageable);
	
	@Query("SELECT b FROM Book b WHERE b.author = :author AND b.id != :id")
	List<Book> findOtherBooksByAuthor(@Param("author") Author author, @Param("id") Long id);
    
}
