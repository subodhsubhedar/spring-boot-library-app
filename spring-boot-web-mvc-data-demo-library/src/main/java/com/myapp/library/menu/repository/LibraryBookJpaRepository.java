package com.myapp.library.menu.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myapp.library.entity.Book;

@Repository
public interface LibraryBookJpaRepository extends JpaRepository<Book, Long> {
	
	Optional<Book> findByTitle(String title);

}
