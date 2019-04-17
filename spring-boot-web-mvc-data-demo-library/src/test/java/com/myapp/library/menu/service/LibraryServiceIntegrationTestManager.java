package com.myapp.library.menu.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.myapp.library.entity.Book;
import com.myapp.library.entity.Subject;
import com.myapp.library.exception.LibraryServiceException;
import com.myapp.library.menu.repository.LibraryBookJpaRepository;
import com.myapp.library.menu.repository.LibrarySubjectJpaRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LibraryServiceIntegrationTestManager {

	@Autowired
	private LibraryService service;

	@MockBean
	private LibrarySubjectJpaRepository subjectRepositoryMock;

	@MockBean
	private LibraryBookJpaRepository bookRepositoryMock;

	private List<Subject> subjectDs;

	private List<Book> bookDs;

	@Test
	public void runSmokeTest() {
		assertNotNull(service);
	}

	@Before 
	public void setUpDS() {

		subjectDs = new ArrayList<Subject>();
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

		bookDs = new ArrayList<Book>();

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
	public void testFindAllSubjects_shouldReturnCorrectCount() throws LibraryServiceException {

		when(subjectRepositoryMock.findAll()).thenReturn(subjectDs);

		assertTrue(Integer.valueOf(5).equals(service.findAllSubjects().size()));

	}

	@Test
	public void testFindAllBooks_shouldReturnCorrectCount() throws LibraryServiceException {

		when(bookRepositoryMock.findAll()).thenReturn(bookDs);

		assertTrue(Integer.valueOf(6).equals(service.findAllBooks().size()));

	}

	@Test
	public void testFindSubjectByTitle_shouldReturnCorrectTitle() throws LibraryServiceException {

		when(subjectRepositoryMock.findBySubtitle("Spring Boot")).thenReturn(Optional.of(subjectDs.get(1)));

		assertTrue("Spring Boot".equals((service.getSubject("Spring Boot")).getSubtitle()));
	}

	@Test
	public void testFindSubjectByTitle_shouldReturnCorrectDuration() throws LibraryServiceException {

		when(subjectRepositoryMock.findBySubtitle("Spring Boot")).thenReturn(Optional.of(subjectDs.get(1)));

		assertTrue(Integer.valueOf(48).equals((service.getSubject("Spring Boot")).getDurationInHrs()));
	}

	@Test
	public void testFindBookByTitle_shouldReturnCorrectTitle() throws LibraryServiceException {

		when(bookRepositoryMock.findByTitle("Junit with Spring")).thenReturn(Optional.of(bookDs.get(0)));

		assertTrue("Junit with Spring".equals((service.getBook("Junit with Spring")).getTitle()));

	}

	@Test
	public void testFindBookByTitle_shouldReturnCorrectPrice() throws LibraryServiceException {

		when(bookRepositoryMock.findByTitle("Junit with Spring")).thenReturn(Optional.of(bookDs.get(0)));

		assertTrue(Double.valueOf(1215).equals((service.getBook("Junit with Spring")).getPrice()));
	}

	@Test
	public void testFindBookByTitle_shouldReturnCorrectSubject() throws LibraryServiceException {

		when(bookRepositoryMock.findByTitle("Learning Maven")).thenReturn(Optional.of(bookDs.get(3)));

		assertTrue(
				subjectDs.get(2).getSubtitle().equals((service.getBook("Learning Maven")).getSubject().getSubtitle()));
	}

	@Test
	public void testFindBookByTitle_shouldReturnCorrectPublishDate() throws LibraryServiceException {

		when(bookRepositoryMock.findByTitle("Learning Maven")).thenReturn(Optional.of(bookDs.get(2)));

		assertTrue(LocalDate.now().equals((service.getBook("Learning Maven")).getPublishDate()));
	}

	@Test
	public void testFindSubjectByDuration_shouldReturnCorrectResults() throws LibraryServiceException {

		List<Subject> subList = new ArrayList<Subject>();
		subList.add(subjectDs.get(1));
		subList.add(subjectDs.get(3));

		when(subjectRepositoryMock.findSubjectBetweenDuration(25, 50)).thenReturn(subList);

		assertTrue(
				"Spring Boot".equals((service.findSubjectByDuration(25, 50)).stream().findFirst().get().getSubtitle()));
	}

	@Test
	public void testCreateSubject_shouldCreateNewSubject() throws LibraryServiceException {

		Subject s = new Subject(5L, "Dockers", 78, null);

		when(subjectRepositoryMock.save(s)).thenReturn(s);
		assertTrue("Dockers".equals(service.createSubject(s).getSubtitle()));
	}

	@Test
	public void testCreateBookWithoutSubject_shouldFail() throws LibraryServiceException {

		Book b = new Book(7L, "Understanding Dockers", 4000, 1, LocalDate.now(), null);

		when(bookRepositoryMock.save(b)).thenReturn(b);

		assertNull((service.createBook(b).getSubject()));
	}

	@Test
	public void testCreateBookWithSubject_shouldReturnCorrectBook() throws LibraryServiceException {

		Subject s = new Subject(5L, "Dockers", 78, null);
		Book b = new Book(7L, "Understanding Dockers", 4000, 1, LocalDate.now(), s);

		when(bookRepositoryMock.save(b)).thenReturn(b);

		assertTrue("Understanding Dockers".equals(service.createBook(b).getTitle()));
	}

	@Test
	public void testCreateBookWithSubject_shouldReturnCorrectSubject() throws LibraryServiceException {

		Subject s = new Subject(5L, "Dockers", 78, null);
		Book b = new Book(7L, "Understanding Dockers", 4000, 1, LocalDate.now(), s);

		when(bookRepositoryMock.save(b)).thenReturn(b);

		assertTrue("Dockers".equals(service.createBook(b).getSubject().getSubtitle()));
	}

	@Test(expected = LibraryServiceException.class)
	public void testDeleteSubjectHavingBooks_shouldThrowException() throws LibraryServiceException {

		when(subjectRepositoryMock.findBySubtitle("Spring Boot")).thenReturn(Optional.of(subjectDs.get(1)));

		service.deleteSubject(subjectDs.get(1).getSubtitle());
		// verify(subjectRepositoryMock).delete(subjectDs.get(1));
	}

	@Test
	public void testDeleteSubjectHavingNoBooks_shouldDelete() throws LibraryServiceException {

		when(subjectRepositoryMock.findBySubtitle("AWS")).thenReturn(Optional.of(subjectDs.get(4)));

		service.deleteSubject(subjectDs.get(4).getSubtitle());
		verify(subjectRepositoryMock).delete(subjectDs.get(4));
	}

	@Test
	public void testDeleteBook_shouldDelete() throws LibraryServiceException {

		when(bookRepositoryMock.findByTitle("Jenkins in a nutshell")).thenReturn(Optional.of(bookDs.get(5)));

		service.deleteBook(bookDs.get(5).getTitle());

		verify(bookRepositoryMock).delete(bookDs.get(5));
	}
}
