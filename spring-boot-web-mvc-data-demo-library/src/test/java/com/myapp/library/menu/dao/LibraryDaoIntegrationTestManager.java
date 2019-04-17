package com.myapp.library.menu.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.myapp.library.entity.Book;
import com.myapp.library.entity.Subject;
import com.myapp.library.exception.LibraryServiceException;
import com.myapp.library.menu.repository.LibraryBookJpaRepository;
import com.myapp.library.menu.repository.LibrarySubjectJpaRepository;

@RunWith(SpringRunner.class)
@DataJpaTest(showSql = true)
public class LibraryDaoIntegrationTestManager {

	@Autowired
	private TestEntityManager entityMngr;

	@Autowired
	private LibraryBookJpaRepository bookRepository;

	@Autowired
	private LibrarySubjectJpaRepository subjectRepository;

	@Before
	public void setUp() {
		Subject s1 = new Subject(0, "JUNIT", 56, null);
		Subject s2 = new Subject(0, "Spring Boot", 48, null);
		Subject s3 = new Subject(0, "Maven", 78, null);
		Subject s4 = new Subject(0, "Jenkins", 28, null);
		Subject s5 = new Subject(0, "AWS", 92, null);
 
		entityMngr.persist(s1);
		entityMngr.persist(s2);
		entityMngr.persist(s3);
		entityMngr.persist(s4);
		entityMngr.persist(s5);

		entityMngr.flush();

		Book b1 = new Book(1L, "Junit with Spring", 1215, 1, LocalDate.now(), s1);
		Book b2 = new Book(2L, "Spring Boot Fundamentals", 1218, 2, LocalDate.now(), s2);
		Book b3 = new Book(3L, "Spring Boot Workshop", 2000, 1, LocalDate.now(), s2);
		Book b4 = new Book(4L, "Learning Maven", 2001, 1, LocalDate.now(), s3);
		Book b5 = new Book(5L, "Building projects with Maven", 1800, 1, LocalDate.now(), s3);
		Book b6 = new Book(6L, "Jenkins in a nutshell", 1789, 1, LocalDate.now(), s4);

		entityMngr.persist(b1);
		entityMngr.persist(b2);
		entityMngr.persist(b3);
		entityMngr.persist(b4);
		entityMngr.persist(b5);
		entityMngr.persist(b6);

		entityMngr.flush();
	}

	@Test
	public void testFindAllSubjects_shouldReturnCorrectCount() throws LibraryServiceException {
		assertTrue(Integer.valueOf(5).equals(subjectRepository.findAll().size()));
	}

	@Test
	public void testFindAllBooks_shouldReturnCorrectCount() throws LibraryServiceException {
		assertTrue(Integer.valueOf(6).equals(bookRepository.findAll().size()));
	}

	@Test
	public void testFindSubjectByTitle_shouldReturnCorrectTitle() throws LibraryServiceException {

		assertTrue("Spring Boot".equals(subjectRepository.findBySubtitle("Spring Boot").get().getSubtitle()));
	}

	@Test
	public void testFindSubjectByTitle_shouldReturnCorrectDuration() throws LibraryServiceException {
		assertTrue(
				Integer.valueOf(48).equals(subjectRepository.findBySubtitle("Spring Boot").get().getDurationInHrs()));
	}

	@Test
	public void testFindBookByTitle_shouldReturnCorrectTitle() throws LibraryServiceException {
		assertTrue("Spring Boot Workshop".equals(bookRepository.findByTitle("Spring Boot Workshop").get().getTitle()));
	}

	@Test
	public void testFindBookByTitle_shouldReturnCorrectPrice() throws LibraryServiceException {
		assertTrue(Double.valueOf(2000).equals(bookRepository.findByTitle("Spring Boot Workshop").get().getPrice()));
	}

	@Test
	public void testFindBookByTitle_shouldReturnCorrectSubject() throws LibraryServiceException {
		assertTrue("Spring Boot"
				.equals(bookRepository.findByTitle("Spring Boot Workshop").get().getSubject().getSubtitle()));
	}
 
	@Test
	public void testFindBookByTitle_shouldReturnCorrectPublishDate() throws LibraryServiceException {
		assertTrue(LocalDate.now().equals(bookRepository.findByTitle("Spring Boot Workshop").get().getPublishDate()));
	}

	@Test
	public void testFindSubjectByDuration_shouldReturnCorrectResults() throws LibraryServiceException {
		assertTrue(Integer.valueOf(2).equals((subjectRepository.findSubjectBetweenDuration(25, 50)).size()));
	}

	@Test
	@DirtiesContext
	public void testCreateSubject_shouldCreateNewSubject() throws LibraryServiceException {
		Subject s = new Subject(5L, "Dockers", 78, null);

		assertTrue("Dockers".equals((subjectRepository.save(s)).getSubtitle()));
	}

	@Test
	@DirtiesContext
	public void testCreateBookWithoutSubject_shouldFail() throws LibraryServiceException {
		Book b = new Book(0, "Understanding Dockers", 4000, 1, LocalDate.now(), null);
		bookRepository.save(b);

		assertNull((bookRepository.findAll().stream().filter((book) -> book.getTitle().equals("Understanding Dockers"))
				.findFirst().get().getSubject()));
	}

	@Test
	@DirtiesContext
	public void testCreateBookWithSubject_shouldReturnCorrectBook() throws LibraryServiceException {

		Subject s = subjectRepository.findBySubtitle("Jenkins").get();

		Book b = new Book(0, "Understanding Jenkins", 4800, 1, LocalDate.now(), s);

		bookRepository.save(b);

		assertTrue(
				"Understanding Jenkins".equals(bookRepository.findByTitle("Understanding Jenkins").get().getTitle()));

	}

	@Test
	@DirtiesContext
	public void testCreateBookWithSubject_shouldReturnCorrectSubject() throws LibraryServiceException {
		Subject s = subjectRepository.findBySubtitle("Jenkins").get();

		Book b = new Book(0, "Understanding Jenkins", 4800, 1, LocalDate.now(), s);

		bookRepository.save(b);

		assertTrue(
				"Jenkins".equals(bookRepository.findByTitle("Understanding Jenkins").get().getSubject().getSubtitle()));

	}

	@Test
	@DirtiesContext
	public void testDeleteSubjectHavingBooks_shouldThrowException() throws LibraryServiceException {

		Subject s = subjectRepository.findBySubtitle("Spring Boot").get();
		subjectRepository.delete(s);
	}

	@Test
	@DirtiesContext
	public void testDeleteSubjectHavingNoBooks_shouldDelete() throws LibraryServiceException {
		Subject s = subjectRepository.findBySubtitle("AWS").get();
		subjectRepository.delete(s);

		assertFalse(subjectRepository.findBySubtitle("AWS").isPresent());
	}

	@Test
	@DirtiesContext
	public void testDeleteBook_shouldDelete() throws LibraryServiceException {
		Book b = bookRepository.findByTitle("Building projects with Maven").get();

		bookRepository.delete(b);

		assertFalse(bookRepository.findByTitle("Building projects with Maven").isPresent());
	}

}
