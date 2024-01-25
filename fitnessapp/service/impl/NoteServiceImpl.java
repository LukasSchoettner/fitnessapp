package de.othr.fitnessapp.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import de.othr.fitnessapp.model.Note;
import de.othr.fitnessapp.repository.NoteRepository;
import de.othr.fitnessapp.repository.TrainerRepository;
import de.othr.fitnessapp.service.NoteServiceI;




@Service
public class NoteServiceImpl implements NoteServiceI{

	private NoteRepository  noteRepository;
	private TrainerRepository  trainerRepository;
	// tbd private CourseRepositoryI  courseRepository;
	
	public NoteServiceImpl(NoteRepository noteRepository,TrainerRepository  trainerRepository /* tbd , CourseRepositoryI  courseRepository*/) {
		super();
		this.noteRepository = noteRepository;
		this.trainerRepository = trainerRepository;
		// tbd this.courseRepository = courseRepository;
	} 
	
	@Override
	public Page<Note> getAllNotes(String title, Pageable pageable) {
		Page <Note> pageNotes;
		if (title == null) {
	        pageNotes = noteRepository.findAll(pageable);
	      } else {
	        pageNotes = noteRepository.findByTitleContainingIgnoreCase(title, pageable);
	        
	      }				
		return  pageNotes;
	}

	@Override
	public Note saveNote(Note note) {
		
		return noteRepository.save(note);
	}

	@Override
	public Note getNoteById(Long id) {
		
		return noteRepository.findById(id).get();
	}

	@Override
	public Note updateNote(Note note) {
		
		return noteRepository.save(note);
	}

	@Override
	public void delete(Note note) {
		
		noteRepository.delete(note);	
	}

	@Override
	public List<Note> findNotesByTitle(String title) {
		
		return noteRepository.findByTitleContainingIgnoreCase(title);
	}
}
