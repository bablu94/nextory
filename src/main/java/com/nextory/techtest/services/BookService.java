package com.nextory.techtest.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.nextory.techtest.models.Author;
import com.nextory.techtest.models.Book;
import com.nextory.techtest.repositories.BookRepository;

@Service
public class BookService {

	@Autowired
	BookRepository bookRepository;

	public List<Book> getAllBooks() {
		List<Book> books = new ArrayList<Book>();
		bookRepository.findAll().forEach(book -> books.add(book));

		return (books);
	}

	public Book getBookById(Long bookId) {
		return (bookRepository.findById(bookId).get());
	}

	public Page<Book> getAllBooks(Pageable pageable) {
		Page<Book> bookPage = bookRepository.findAll(pageable);
		bookPage.getContent().stream().forEach(book -> {
			if (book.getAuthor() != null) {
				String authorName = book.getAuthor().getFirstName().substring(0, 1).toUpperCase() + ". "
						+ book.getAuthor().getLastName().toUpperCase();
				book.setAuthorName(authorName);
			}
		});

		return bookPage;
	}

	public List<Book> getSuggestedBooks(Author author, Long id) {
		List<Book> books = bookRepository.findOtherBooksByAuthor(author, id);
		if (books.size() < 5) {
			Pageable pageRequest = PageRequest.of(0, 5 - books.size(), Sort.by(Sort.Direction.DESC, "boostScore"));
			List<Book> topBooks = bookRepository.findAll(pageRequest).getContent();
			books.addAll(topBooks);
		}
		return books;
	}
}
