package de.othr.fitnessapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.othr.fitnessapp.model.Course;
import de.othr.fitnessapp.service.CourseServiceI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import de.othr.fitnessapp.model.Message;
import de.othr.fitnessapp.model.Note;
import de.othr.fitnessapp.model.Trainer;
import de.othr.fitnessapp.service.NoteServiceI;
import de.othr.fitnessapp.service.TrainerServiceI;
import de.othr.fitnessapp.service.impl.MailServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;


@RequestMapping(value = "/note")
@Controller
public class NoteController {
	
	private NoteServiceI noteService;
	private TrainerServiceI trainerService;
	private CourseServiceI courseService;
	
	public NoteController(NoteServiceI noteService,TrainerServiceI trainerService, CourseServiceI courseService) {
		super();
		this.noteService = noteService;
		this.trainerService = trainerService;
		this.courseService= courseService;
	}
	
	@GetMapping("/add")
    public String showSignUpForm(Model model, HttpServletRequest request ) {
		Note note = new Note();
	
		
		model.addAttribute("note", note);
		request.getSession().setAttribute("noteSession", note);
		
        return "/notes/note-add";
    }
	
	@PostMapping("/add")
    public String addNote(@Valid @ModelAttribute Note note, 
    		BindingResult result, 
    		Model model,
    		RedirectAttributes redirectAttributes) {
        
    	if (result.hasErrors()) {
    		System.out.println(result.getAllErrors().toString());
            return "/notes/note-add";
        }
        
    	
    	noteService.saveNote(note);
        redirectAttributes.addFlashAttribute("added", "Note added!");
        
        return "redirect:/note/all";
    }
	
	@GetMapping("/update/{id}")
	public String showUpdateNoteForm(@PathVariable Long id, 
			Model model,
			HttpServletRequest request) {
		Note note = noteService.getNoteById(id);
    	model.addAttribute("note", note);
		model.addAttribute("note", note);
		request.getSession().setAttribute("noteSession", note);

		return "/notes/note-update";
	}
	
	@PostMapping("/update")
	public String updateNote(@Valid @ModelAttribute("note") Note note,
			BindingResult results,
			Model model, 
			RedirectAttributes redirectAttributes) {
		
				
		if (results.hasErrors()){
			
			return "/notes/note-update";
		}
       
		noteService.updateNote(note);
        redirectAttributes.addFlashAttribute("updated", "Note updated!");
		return "redirect:/note/all";
		
	}
	
	@GetMapping("/delete/{id}")
    public String deleteNote(@PathVariable("id") long id, Model model, RedirectAttributes redirectAttributes) {
		Note note = noteService.getNoteById(id);               
        noteService.delete(note);
        redirectAttributes.addFlashAttribute("deleted", "Note deleted!");
        return "redirect:/note/all";
    }
    
    @GetMapping("/all")
    public String showUserList(Model model, @RequestParam(required = false) String keyword,
    	    @RequestParam(required = false, defaultValue = "1") int page, @RequestParam(required = false, defaultValue = "3") int size) {
    	        
    	    	
    	    	
    	    	 try {
    	    	      List<Note> notes = new ArrayList<Note>();
    	    	      
    	    	      Pageable paging = PageRequest.of(page - 1, size);

    	    	      Page<Note> pageNotes;
    	    	      
    	    	        pageNotes = noteService.getAllNotes(keyword, paging);
    	    	      
    	    	        model.addAttribute("keyword", keyword);

    	    	        notes = pageNotes.getContent();

    	    	      model.addAttribute("notes", notes);
    	    	      model.addAttribute("currentPage", pageNotes.getNumber() + 1);
    	    	      model.addAttribute("totalItems", pageNotes.getTotalElements());
    	    	      model.addAttribute("totalPages", pageNotes.getTotalPages());
    	    	      model.addAttribute("pageSize", size);
    	    	      model.addAttribute("entityContext", "note/all");
    	    	    } catch (Exception e) {
    	    	      model.addAttribute("message", e.getMessage());
    	    	    }    
    	    	 
        return "/notes/note-all";
    }
    
   

	@GetMapping(value= "/trainer/select")
	public String showSelectTrainerByID(Model model) {
		
		Trainer trainer = new Trainer();
		trainer.setId((long) -1);
		List <Trainer> trainers = new ArrayList<Trainer>();
		model.addAttribute("trainers", trainers);
		model.addAttribute("trainer", trainer);
				
		return "/notes/note-select-trainer";
		
	}
	
	@PostMapping(value= "/trainer/select")
	public String showResultsSelectTrainerByID(Model model, @ModelAttribute Trainer trainer) {
						
		model.addAttribute("trainers", trainerService.findTrainersBylastName(trainer.getLastName()));
						
		return "/notes/note-select-trainer";
		
	}
	
	
	@RequestMapping(value = "/trainer/select/{id}")
	public String selectAcademicEventProcess(@PathVariable("id") int id, Model model, HttpServletRequest request) {
		
				
		Note noteSession = (Note) request.getSession().getAttribute("noteSession");
		
		Trainer trainer  = trainerService.getTrainerById((long) id);
				
		noteSession.setTrainer(trainer);
									
		request.getSession().setAttribute("noteSession", noteSession);
		
		model.addAttribute("note", noteSession);
		
		if (noteSession.getId()>0) {
			return  "/notes/note-update";
		}
				
		return  "/notes/note-add";
	}

	@GetMapping(value= "/course/select")
	public String showSelectCourseByID(Model model) {

		Course course = new Course();
		course.setId((long) -1);
		List <Course> courses = new ArrayList<Course>();
		model.addAttribute("courses", courses);
		model.addAttribute("course", course);

		return "/notes/note-select-course";

	}

	@PostMapping(value= "/course/select")
	public String showResultsSelectCourseByID(Model model, @ModelAttribute Course course) {

		model.addAttribute("courses", courseService.findCoursesByName(course.getName()));

		return "/notes/note-select-course";

	}


	@RequestMapping(value = "/course/select/{id}")
	public String selectCourseEventProcess(@PathVariable("id") int id, Model model, HttpServletRequest request) {


		Note noteSession = (Note) request.getSession().getAttribute("noteSession");

		Course course  = courseService.getCourseById((long) id);

		noteSession.setCourse(course);

		request.getSession().setAttribute("noteSession", noteSession);

		model.addAttribute("note", noteSession);

		if (noteSession.getId()>0) {
			return  "/notes/note-update";
		}

		return  "/notes/note-add";
	}
}
