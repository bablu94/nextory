package com.nextory.techtest.controllers;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nextory.techtest.models.Author;
import com.nextory.techtest.models.Book;
import com.nextory.techtest.models.Comment;
import com.nextory.techtest.services.BookService;
import com.nextory.techtest.services.CommentService;

import jakarta.validation.Valid;

@RestController
public class BookController {

	@Autowired
	BookService bookService;

	@Autowired
	CommentService commentService;

	@GetMapping("/api/book")
	public ResponseEntity<Page<Book>> getAllBooks(
			@RequestParam(required = false, defaultValue = "0") Integer pageNumber,
			@RequestParam(required = false, defaultValue = "25") Integer pageSize,
			@RequestParam(required = false, defaultValue = "title") String sortFieldName,
			@RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction) {
		Pageable pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(direction, sortFieldName));
		return (ResponseEntity.ok(bookService.getAllBooks(pageRequest)));
	}

	@GetMapping("/api/book/{id}")
	public ResponseEntity<Book> getBookById(@PathVariable("id") Long _id) {
		Book book = bookService.getBookById(_id);
		if (book == null) {
			return (ResponseEntity.notFound().build());
		} else {
			return (ResponseEntity.ok(book));
		}
	}

	@PostMapping("/api/book/{id}/comments")
	public ResponseEntity<Comment> addComment(@PathVariable("id") Long id, @Valid Comment comment,
			BindingResult result, @RequestParam("rating") Integer rating) {
		if (result.hasErrors()) {
			return ResponseEntity.badRequest().body(comment);
		}
		Book book = bookService.getBookById(id);
		comment.setBook(book);
		comment.setRating(rating);
		commentService.saveComment(comment);
		return ResponseEntity.ok(comment);
	}

	@GetMapping("/api/book/{id}/comments")
	public ResponseEntity<List<Comment>> getAllComments(@PathVariable("id") Long id) {
		Book book = bookService.getBookById(id);
		List<Comment> comments = book.getComments();
		comments.sort(Comparator.comparing(Comment::getRating).reversed());
		return ResponseEntity.ok(comments);
	}

	@GetMapping("/books/{id}")
	public ResponseEntity<Map<String, Object>> getBookDetails(@PathVariable("id") Long id) {
		Book book = bookService.getBookById(id);
		Author author = book.getAuthor();
		Map<String, Object> response = new HashMap<>();
		response.put("book", book);
		response.put("author", author);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/books/{id}/suggested")
	public ResponseEntity<List<Book>> getSuggestedBooks(@PathVariable("id") Long id) {
		Book book = bookService.getBookById(id);
		Author author = book.getAuthor();
		List<Book> suggestedBooks = bookService.getSuggestedBooks(author, id);
		return ResponseEntity.ok(suggestedBooks);
	}

}
