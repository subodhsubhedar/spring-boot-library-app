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

import com.myapp.library.controller.LibrarySubjectController;
import com.myapp.library.controller.validation.SearchSubjectValidator;
import com.myapp.library.controller.validation.SubjectSearchCriteria;
import com.myapp.library.controller.validation.SubjectValidator;
import com.myapp.library.entity.Book;
import com.myapp.library.entity.Subject;
import com.myapp.library.exception.LibraryServiceException;
import com.myapp.library.menu.service.LibraryService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = { LibrarySubjectController.class, SubjectValidator.class, SearchSubjectValidator.class })
@WithMockUser(username = "subodh", password = "suyog")
public class LibrarySubjectControllerTestManager {

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
	public void testGetAllSubjects_shouldReturnCorrectView() throws Exception {

		when(serviceMock.findAllSubjects()).thenReturn(subjectDs);

		this.mockMvc.perform(get("/menu/all-subjects"))
				.andExpect(model().attribute("allSubjectsList", subjectDs))
				.andExpect(status().isOk())
				.andExpect(view().name("all-subjects-view"));

	}

	@Test
	public void testGetAllSubjectsNotAvailable_shouldReturnCorrectView() throws Exception {

		when(serviceMock.findAllSubjects()).thenReturn(null);

		this.mockMvc.perform(get("/menu/all-subjects"))
				.andExpect(status().isOk())
				.andExpect(view().name("all-subjects-view"));

	}
	
	@Test
	public void testSearchSubject_shouldReturnCorrectSubject() throws Exception {

		when(serviceMock.getSubject("JUNIT")).thenReturn(subjectDs.stream().findFirst().get());

		this.mockMvc.perform(get("/menu/search-subject")
				.param("searchSubjectTitle", "JUNIT")
				)
				.andExpect(status().isOk())
				.andExpect(model().attribute("subject", subjectDs.stream().findFirst().get()))
						.andExpect(view().name("search-subject-view"));
	}
	
	
	@Test
	public void testSearchSubject_shouldThrowServiceException() throws Exception {

		when(serviceMock.getSubject("JUNIT")).thenThrow(new LibraryServiceException("DB Connection error"));

		this.mockMvc.perform(get("/menu/search-subject")
				.param("searchSubjectTitle", "JUNIT")
				)
				.andExpect(status().isOk())
						.andExpect(view().name("search-subject-view"));
	}
	
	
	@Test
	public void testSearchSubjectByDuration_shouldReturnCorrectSearchView() throws Exception {

		this.mockMvc.perform(get("/menu/search-subject-by-duration")
				.flashAttr("searchSubjectCriteria", new SubjectSearchCriteria())
				)
				.andExpect(status().isOk())
					.andExpect(view().name("search-subject-by-duration-view"));
	}
	
	
	@Test
	public void testPostSearchSubjectByDuration_shouldThrowValidationError() throws Exception {

		this.mockMvc.perform(post("/menu/search-subject-by-duration")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("durationStart", String.valueOf(0))
				.param("durationEnd", String.valueOf(0))
				)
					.andExpect(status().isOk())
					.andExpect(model().attributeHasFieldErrors("searchSubjectCriteria", "durationStart"))
					.andExpect(model().attributeHasFieldErrors("searchSubjectCriteria", "durationEnd"))
					.andExpect(view().name("search-subject-by-duration-view"));
	}
	
	@Test
	public void testPostSearchSubjectByDuration_shouldReturnCorrectSubjects() throws Exception {

		Set<Subject> subjectsSet =  ((subjectDs.stream())
				.filter( subj -> (subj.getDurationInHrs()<50  && subj.getDurationInHrs()>25) 
						))
						.collect(Collectors.toSet());
		
		when(serviceMock.findSubjectByDuration(25, 50)).thenReturn(subjectsSet);
		
		this.mockMvc.perform(post("/menu/search-subject-by-duration")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("durationStart", String.valueOf(25))
				.param("durationEnd", String.valueOf(50))
				)
					.andExpect(status().isOk())
					.andExpect(model().attribute("allSubjectsList", subjectsSet))
					.andExpect(view().name("all-subjects-view"));
	}
	
	@Test
	public void testAddSubject_shouldReturnCorrectSearchView() throws Exception {

		this.mockMvc.perform(get("/menu/add-new-subject")
				.flashAttr("Subject", new Subject())
				)
				.andExpect(status().isOk())
					.andExpect(view().name("add-subject-view"));
	}
	
	@Test
	public void testPostAddSubject_shouldThrowValidationError() throws Exception {

		this.mockMvc.perform(post("/menu/add-new-subject")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("subtitle", "")
				.param("durationInHrs", String.valueOf(0))
				)
					.andExpect(status().isOk())
					.andExpect(model().attributeHasFieldErrors("subject", "subtitle"))
					.andExpect(model().attributeHasFieldErrors("subject", "durationInHrs"))
					.andExpect(view().name("add-subject-view"));
	}

	
	@Test
	public void testPostAddSubject_shouldAddSuccessfully() throws Exception {

		Subject s6 = new Subject(8L, "Spring Unit Testing", 128, null);
		
		this.mockMvc.perform(post("/menu/add-new-subject")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.flashAttr("subject", s6)
				)
					.andExpect(status().isOk())
					.andExpect(model().attribute("addedSubjectTitle", "Spring Unit Testing"))
					.andExpect(view().name("add-subject-view"));
	}
	
	
	
	@Test
	public void testPostDeleteSubject_shouldDeleteSuccessfully() throws Exception {

		Subject s2 = new Subject(2L, "Spring Boot", 48, null);
		
		this.mockMvc.perform(post("/menu/delete-subject")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.flashAttr("subject", s2)
				)
					.andExpect(status().isOk())
					.andExpect(model().attribute("deletedSubjectTitle", "Spring Boot"))
					.andExpect(view().name("delete-subject-view"));
	}
	
	@Test
	public void testDeleteSubjectWithBooks_shouldReturnCorrectView() throws Exception {
		
		
		Subject s = subjectDs.stream().filter(subj -> subj.getSubtitle().equals("JUNIT")).findFirst().get();
		
		when(serviceMock.getSubject("JUNIT")).thenReturn(s);
		
		this.mockMvc.perform(get("/menu/delete-subject")
						.param("deleteSubjectTitle", "JUNIT")
				)
					.andExpect(status().isOk())
					.andExpect(model().attribute("subject", s ))
					.andExpect(model().attribute("noBooks", false))
					.andExpect(view().name("delete-subject-view"));
	}
	
	
	@Test
	public void testDeleteSubjectWithNoBooks_shouldReturnCorrectView() throws Exception {
		
		
		Subject s = subjectDs.stream().filter(subj -> subj.getSubtitle().equals("AWS")).findFirst().get();
		
		when(serviceMock.getSubject("AWS")).thenReturn(s);
		
		this.mockMvc.perform(get("/menu/delete-subject")
						.param("deleteSubjectTitle", "AWS")
				)
					.andExpect(status().isOk())
					.andExpect(model().attribute("subject", s ))
					.andExpect(model().attribute("noBooks", true))
					.andExpect(view().name("delete-subject-view"));
	}
		
	
}