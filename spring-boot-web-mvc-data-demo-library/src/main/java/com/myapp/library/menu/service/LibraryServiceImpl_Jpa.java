package com.myapp.library.menu.service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myapp.library.entity.Book;
import com.myapp.library.entity.Subject;
import com.myapp.library.exception.LibraryServiceException;
import com.myapp.library.menu.repository.LibraryBookJpaRepository;
import com.myapp.library.menu.repository.LibrarySubjectJpaRepository;

/**
 * ss
 * 
 * @author Admin
 *
 */
@Service
public class LibraryServiceImpl_Jpa implements LibraryService {

	@Autowired
	private LibrarySubjectJpaRepository subjectRepository;

	@Autowired
	private LibraryBookJpaRepository bookRepository;

	@Override
	@Transactional
	public Set<Subject> findAllSubjects() throws LibraryServiceException {
		try {

			return new LinkedHashSet<Subject>(subjectRepository.findAll());

		} catch (Exception e) {
			throw new LibraryServiceException("Exception Occured while retrieving all subjects.", e);
		}
	}

	@Override
	@Transactional
	public Set<Book> findAllBooks() throws LibraryServiceException {
		try {
			return new LinkedHashSet<Book>(bookRepository.findAll());
		} catch (Exception e) {
			throw new LibraryServiceException("Exception Occured while retrieving all books.", e);
		}

	}

	@Override
	@Transactional
	public Book getBook(String bookTitle) throws LibraryServiceException {
		Book book = null;
		try {

			Optional<Book> optional = bookRepository.findByTitle(bookTitle);

			if (optional.isPresent()) {
				book = optional.get();
			}
			return book;
		} catch (Exception e) {
			throw new LibraryServiceException("Exception Occured while retrieving BOOK :" + bookTitle, e);
		}
	}

	@Override
	@Transactional
	public Subject getSubject(String subjTitle) throws LibraryServiceException {
		Subject subject = null;
		try {

			Optional<Subject> optional = subjectRepository.findBySubtitle(subjTitle);

			if (optional.isPresent()) {
				subject = optional.get();
			}
			return subject;
		} catch (Exception e) {
			throw new LibraryServiceException("Exception Occured while retrieving subject :" + subjTitle, e);
		}
	}

	@Override
	@Transactional
	public Book createBook(Book book) throws LibraryServiceException {
		try {
			return bookRepository.save(book);
		} catch (Exception e) {
			throw new LibraryServiceException("Exception Occured while creating book :" + book, e);
		}

	}

	@Override
	@Transactional
	public Subject createSubject(Subject subject) throws LibraryServiceException {
		try {
			return subjectRepository.save(subject);
		} catch (Exception e) {
			throw new LibraryServiceException("Exception Occured while creating subject :" + subject, e);
		}

	}

	@Override
	@Transactional
	public Book deleteBook(String bookTitle) throws LibraryServiceException {
		Book deletedBook;
		try {
			Book book = getBook(bookTitle);
			deletedBook = book;

			bookRepository.delete(deletedBook);

			return deletedBook;
		} catch (Exception e) {
			throw new LibraryServiceException("Exception Occured while deleting book :" + bookTitle, e);
		}

	}

	@Override
	@Transactional
	public Subject deleteSubject(String subjTitle) throws LibraryServiceException {
		Subject deletedSubject;
		try {

			Subject subject = this.getSubject(subjTitle);
			deletedSubject = subject;

			if (subject.getReferences() == null || subject.getReferences().isEmpty()) {

				subjectRepository.delete(subject);
				return deletedSubject;
			} else {
				throw new LibraryServiceException(
						"Could not delete subject as there are one or more books associated with it: " + subjTitle);
			}

		} catch (Exception e) {
			throw new LibraryServiceException("Exception Occured while deleting subject: " + subjTitle, e);
		}

	}


	@Override
	public Set<Subject> findSubjectByDuration(int startValue, int endValue) throws LibraryServiceException {
		List<Subject> subjectList;

		try {

			subjectList = subjectRepository.findSubjectBetweenDuration(startValue, endValue);

		} catch (Exception e) {
			throw new LibraryServiceException("Exception Occured while retrieving all subjects.", e);
		}

		return new LinkedHashSet<Subject>(subjectList);
	}

}
