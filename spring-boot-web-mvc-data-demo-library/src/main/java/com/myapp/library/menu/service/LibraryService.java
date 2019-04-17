package com.myapp.library.menu.service;

import java.util.Set;

import com.myapp.library.entity.Book;
import com.myapp.library.entity.Subject;
import com.myapp.library.exception.LibraryServiceException;

/**
 * 
 * @author Admin
 *
 */
public interface LibraryService {

	Set<Subject> findAllSubjects() throws LibraryServiceException;

	Set<Book> findAllBooks() throws LibraryServiceException;

	Book getBook(String bookTitle) throws LibraryServiceException;

	Subject getSubject(String subjTitle) throws LibraryServiceException;

	Book createBook(Book book) throws LibraryServiceException;

	Subject createSubject(Subject subject) throws LibraryServiceException;

	Book deleteBook(String bookTitle) throws LibraryServiceException;

	Subject deleteSubject(String subjTitle) throws LibraryServiceException;

	Set<Subject> findSubjectByDuration(int startValue, int endValue) throws LibraryServiceException;

}
