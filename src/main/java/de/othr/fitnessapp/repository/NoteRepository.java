package de.othr.fitnessapp.repository;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.othr.fitnessapp.model.Note;


@Repository
public interface NoteRepository extends  JpaRepository<Note, Long>{

	List<Note> findByTitleContainingIgnoreCase (String title);
	Page<Note>  findAll(Pageable pageable);
	Page <Note> findByTitleContainingIgnoreCase (String title, Pageable pageable);
}
