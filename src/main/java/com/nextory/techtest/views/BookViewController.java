package com.nextory.techtest.views;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.nextory.techtest.models.Book;
import com.nextory.techtest.services.BookService;

@Controller
public class BookViewController {

	@Autowired
	BookService bookService;

	@GetMapping("/book")
	public String getBookPage(Model model) {
		List<Book> books = bookService.getAllBooks();

		books.stream().forEach(book -> {
			String authorName = book.getAuthor().getFirstName().substring(0, 1).toUpperCase() + ". "
					+ book.getAuthor().getLastName().toUpperCase();
			book.setAuthorName(authorName);
		});

		model.addAttribute("books", books);

		return ("books/index");
	}

	@GetMapping("/book/{id}")
	public String getBookDetailPage(@PathVariable("id") Long _id, Model model) {
		Book book = bookService.getBookById(_id);
		model.addAttribute("book", book);

		return ("books/detail");
	}
}
