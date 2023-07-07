package com.nextory.techtest.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.nextory.techtest.models.Author;
import com.nextory.techtest.models.Book;
import com.nextory.techtest.repositories.BookRepository;
import com.nextory.techtest.services.BookService;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

	@Mock
	private BookRepository bookRepository;

	@InjectMocks
	private BookService bookService;

	@Test
	public void testGetAllBooks() {
		List<Book> books = new ArrayList<>();
		when(bookRepository.findAll()).thenReturn(books);

		List<Book> result = bookService.getAllBooks();

		assertEquals(books, result);
	}

	@Test
	public void testGetBookById() {
		Book book = new Book();
		Mockito.when(bookRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(book));
		Book result = bookService.getBookById(1L);
		assertEquals(book, result);
	}

	@Test
	public void testGetAllBooksPageable() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<Book> bookPage = new PageImpl<>(Collections.singletonList(new Book()));
		Mockito.when(bookRepository.findAll(pageable)).thenReturn(bookPage);
		Page<Book> result = bookService.getAllBooks(pageable);
		assertEquals(bookPage, result);
	}

	@Test
	public void testGetSuggestedBooks() {
		Author author = new Author();
		List<Book> books = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			books.add(new Book());
		}
		Mockito.when(bookRepository.findOtherBooksByAuthor(Mockito.any(Author.class), Mockito.anyLong()))
				.thenReturn(books);
		List<Book> result = bookService.getSuggestedBooks(author, 5L);
		Assertions.assertEquals(5, result.size());
	}
}
