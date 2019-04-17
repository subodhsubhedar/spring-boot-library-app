package com.myapp.library.menu.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.myapp.library.controller.ErrorController;
import com.myapp.library.controller.LibraryBookController;
import com.myapp.library.controller.validation.BookValidator;
import com.myapp.library.controller.validation.SubjectSearchCriteria;
import com.myapp.library.entity.Book;
import com.myapp.library.entity.Subject;
import com.myapp.library.exception.LibraryServiceException;
import com.myapp.library.menu.service.LibraryService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = { LibraryBookController.class, BookValidator.class })
@WithMockUser(username = "subodh", password = "suyog")
public class LibraryBookControllerTestManager {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@MockBean
	private LibraryService serviceMock;
	
	private Set<Subject> subjectDs;

	private Set<Book> bookDs;

	@Before
	public void setUp() {

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		subjectDs = new HashSet<Subject>();
		Subject s1 = new Subject(1L, "JUNIT", 56, null);
		subjectDs.add(s1);
		Subject s2 = new Subject(2L, "Spring Boot", 48, null);
		subjectDs.add(s2);
		Subject s3 = new Subject(3L, "Maven", 78, null);
		subjectDs.add(s3);
		Subject s4 = new Subject(4L, "Jenkins", 28, null);
		subjectDs.add(s4);

		Subject s5 = new Subject(6L, "AWS", 92, null);
		subjectDs.add(s5);

		bookDs = new LinkedHashSet<Book>();

		Book b1 = new Book(1L, "Junit with Spring", 1215, 1, LocalDate.now(), s1);
		bookDs.add(b1);
		Book b2 = new Book(2L, "Spring Boot Fundamentals", 1218, 2, LocalDate.now(), s2);
		bookDs.add(b2);
		Book b3 = new Book(3L, "Spring Boot Workshop", 2000, 1, LocalDate.now(), s2);
		bookDs.add(b3);
		Book b4 = new Book(4L, "Learning Maven", 2001, 1, LocalDate.now(), s3);
		bookDs.add(b4);
		Book b5 = new Book(5L, "Building projects with Maven", 1800, 1, LocalDate.now(), s3);
		bookDs.add(b5);
		Book b6 = new Book(6L, "Jenkins in a nutshell", 1789, 1, LocalDate.now(), s4);
		bookDs.add(b6);

		s1.addReferences(b1);

		s2.addReferences(b2);
		s2.addReferences(b3);

		s3.addReferences(b4);
		s3.addReferences(b5);

		s4.addReferences(b6);

	}

	@Test
	public void testGetAllBooks_shouldReturnCorrectView() throws Exception {

		when(serviceMock.findAllBooks()).thenReturn(bookDs);

		this.mockMvc.perform(get("/menu/all-books"))
				.andExpect(model().attribute("allBooksList", bookDs))
				.andExpect(status().isOk())
				.andExpect(view().name("all-books-view"));

	}

	
	@Test
	public void testGetAllBooksNotAvailable_shouldReturnCorrectView() throws Exception {

		when(serviceMock.findAllBooks()).thenReturn(null);
		
		this.mockMvc.perform(get("/menu/all-books"))
			.andExpect(status().isOk())
			.andExpect(view().name("all-books-view"));
	}
	
	@Test
	public void testSearchBook_shouldReturnCorrectBook() throws Exception {

		
		Book b = bookDs.stream().filter(book -> book.getTitle().equals("Junit with Spring")).findFirst().get();
		
		when(serviceMock.getBook("Junit with Spring")).thenReturn(b);

		this.mockMvc.perform(get("/menu/search-book")
				.param("searchBookTitle", "Junit with Spring")
				)
				.andExpect(status().isOk())
				.andExpect(model().attribute("book", b))
						.andExpect(view().name("search-book-view"));
	}
	
	
	@Test
	public void testSearchBook_shouldThrowServiceException() throws Exception {

		when(serviceMock.getBook("JUNIT")).thenThrow(new LibraryServiceException("DB Connection error"));

		this.mockMvc.perform(get("/menu/search-book")
				.param("searchBookTitle", "Junit with Spring")
				)
				.andExpect(status().isOk())
						.andExpect(view().name("search-book-view"));
	}
	
	
	@Test
	public void testAddBook_shouldReturnCorrectSearchView() throws Exception {

		this.mockMvc.perform(get("/menu/add-new-book")
				.flashAttr("book", new Book())
				)
				.andExpect(status().isOk())
					.andExpect(view().name("add-book-view"));
	}
	
	@Test
	public void testPostAddBook_shouldThrowValidationError() throws Exception {
		
		this.mockMvc.perform(post("/menu/add-new-book")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.flashAttr("book", new Book())
				)
					.andExpect(status().isOk())
					.andExpect(model().attributeHasFieldErrors("book", "title"))
					.andExpect(model().attributeHasFieldErrors("book", "price"))
					.andExpect(model().attributeHasFieldErrors("book", "volume"))
					.andExpect(model().attributeHasFieldErrors("book", "publishDate"))
					.andExpect(model().attributeHasFieldErrors("book", "subject"))
					.andExpect(view().name("add-book-view"));
	}

	@Test
	public void testPostAddBookFuturePublishDt_shouldThrowValidationError() throws Exception {
		
		Book b = new Book(0, "Spring Boot with Cloud", 1200, 1, LocalDate.parse("2019-12-15"), 
				subjectDs.stream().filter(subj -> subj.getSubtitle().equals("Spring Boot")).findFirst().get());
		
		this.mockMvc.perform(post("/menu/add-new-book")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.flashAttr("book", new Book())
				)
					.andExpect(status().isOk())
					.andExpect(model().attributeHasFieldErrors("book", "publishDate"))
					.andExpect(view().name("add-book-view"));
	}
	
	
	@Test
	public void testPostAddBook_shouldAddSuccessfully() throws Exception {


		Book b = new Book(0, "Spring Boot with Cloud", 1200, 1, LocalDate.now(), 
				subjectDs.stream().filter(subj -> subj.getSubtitle().equals("Spring Boot")).findFirst().get());

		when(serviceMock.createBook(b)).thenReturn(b);
		
		when(serviceMock.findAllSubjects()).thenReturn(subjectDs);
		
		this.mockMvc.perform(post("/menu/add-new-book")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.flashAttr("book", b)
				)
					.andExpect(status().isOk())
					.andExpect(model().attribute("availableSubjectList", (subjectDs.stream().collect(Collectors.toList()))))
					.andExpect(model().attribute("addedBookTitle", "Spring Boot with Cloud"))
					.andExpect(view().name("add-book-view"));
	}
	
	@Test
	public void testPostAddBook_shouldThrowException() throws Exception {


		Book b = new Book(0, "Spring Boot with Cloud", 1200, 1, LocalDate.now(), 
				subjectDs.stream().filter(subj -> subj.getSubtitle().equals("Spring Boot")).findFirst().get());

		when(serviceMock.createBook(b)).thenReturn(b);
		
		when(serviceMock.findAllSubjects()).thenThrow(new LibraryServiceException("DB exception..."));
		
		this.mockMvc.perform(post("/menu/add-new-book")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.flashAttr("book", b)
				)
					.andExpect(status().isOk())
					.andExpect(view().name("add-book-view"));
	}
	
	
	
	@Test
	public void testDeleteBook_shouldReturnCorrectView() throws Exception {
		
		
		Book b = bookDs.stream().filter(book -> book.getTitle().equals("Jenkins in a nutshell")).findFirst().get();
		
		when(serviceMock.getBook("Jenkins in a nutshell")).thenReturn(b);
		
		this.mockMvc.perform(get("/menu/delete-book")
						.param("deleteBookTitle", "Jenkins in a nutshell")
				)
					.andExpect(status().isOk())
					.andExpect(model().attribute("book", b ))
					.andExpect(view().name("delete-book-view"));
	}	
	
	@Test
	public void testPostDeleteBook_shouldDeleteSuccessfully() throws Exception {

		Book b = bookDs.stream().filter(book -> book.getTitle().equals("Jenkins in a nutshell")).findFirst().get();
		
		this.mockMvc.perform(post("/menu/delete-book")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.flashAttr("book", b)
				)
					.andExpect(status().isOk())
					.andExpect(model().attribute("deletedBookTitle", "Jenkins in a nutshell"))
					.andExpect(view().name("delete-book-view"));
	}

	
}