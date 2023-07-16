package com.tyrone.booksapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tyrone.booksapi.models.Book;
import com.tyrone.booksapi.services.BookService;

import jakarta.validation.Valid;

@Controller
public class BooksController {
	@Autowired
	BookService bookService;

	@GetMapping("/")
	public String home() {
		return "redirect:/books";
	}

	@GetMapping("/books")
	public String index(Model model) {

		List<Book> books = bookService.allBooks();
		model.addAttribute("books", books);
		return "index.jsp";
	}

	@GetMapping("/books/new")
	public String newBook(@ModelAttribute("book") Book book) {
		return "new.jsp";
	}

	@PostMapping("/books")
	public String create(@Valid @ModelAttribute("book") Book book, BindingResult result) {
		if (result.hasErrors()) {
			return "new.jsp";
		} else {
			bookService.createBook(book);
			return "redirect:/books";
		}
	}

	// other methods removed for brevity
	@RequestMapping("/books/{id}/edit")
	public String edit(@PathVariable("id") Long id, Model model) {
		Book book = bookService.findBook(id);
		model.addAttribute("book", book);
		return "/books/edit.jsp";
	}

	@RequestMapping(value = "/books/{id}", method = RequestMethod.PUT)
	public String update(@Valid @ModelAttribute("book") Book book, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("book", book);
			return "/books/edit.jsp";
		} else {
			bookService.upDateBook(book);
			return "redirect:/books";
		}
	}

	// other methods removed for brevity
	@RequestMapping("/books/delete/{id}")
	public String deleteBook(@PathVariable("id") Long id) {		
		bookService.deleteBook(id);
		return "redirect:/books";
	}
}
