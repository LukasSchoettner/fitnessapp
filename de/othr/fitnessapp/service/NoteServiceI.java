package de.othr.fitnessapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import de.othr.fitnessapp.model.Note;


public interface NoteServiceI {
	
	Page<Note> getAllNotes(String title, Pageable pageable);
	
	List<Note> findNotesByTitle(String title);
	
	Note saveNote(Note note);
	
	Note getNoteById(Long id);
	
	Note updateNote(Note note);
	
	void delete(Note note);
}
