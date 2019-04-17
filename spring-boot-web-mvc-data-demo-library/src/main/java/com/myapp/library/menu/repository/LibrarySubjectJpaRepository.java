package com.myapp.library.menu.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.myapp.library.entity.Subject;

@Repository
public interface LibrarySubjectJpaRepository extends JpaRepository<Subject, Long> {

	Optional<Subject> findBySubtitle(String subtitle);
	
	@Query(value = "from Subject s where durationInHrs BETWEEN :start AND :end")
	List<Subject> findSubjectBetweenDuration(@Param("start") int startVal, @Param("end") int endVal);

}
