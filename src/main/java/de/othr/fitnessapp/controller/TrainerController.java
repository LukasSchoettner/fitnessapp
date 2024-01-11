package de.othr.fitnessapp.controller;

import java.util.ArrayList;
import java.util.List;

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

import de.othr.fitnessapp.model.Trainer;
import de.othr.fitnessapp.service.TrainerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;


@RequestMapping(value = "/trainer")
@Controller
public class TrainerController {

	private TrainerService trainerService;
	// tbd private CourseServiceI courseService;
	
	public TrainerController(TrainerService trainerService) {
			// tbd CourseServiceI courseService) {
		super();
		this.trainerService = trainerService;
		// tbd this.courseService= courseService;
	}
	
	@GetMapping("/add")
    public String showSignUpForm(Model model, HttpServletRequest request ) {
		Trainer trainer = new Trainer();
	
		
		model.addAttribute("trainer", trainer);
		request.getSession().setAttribute("trainerSession", trainer);
		
        return "/trainers/trainer-add";
    }
	
	@PostMapping("/add")
    public String addTrainer(@Valid @ModelAttribute Trainer trainer, 
    		BindingResult result, 
    		Model model,
    		RedirectAttributes redirectAttributes) {
        
    	if (result.hasErrors()) {
    		System.out.println(result.getAllErrors().toString());
            return "/trainers/trainer-add";
        }
        
    	
        trainerService.saveTrainer(trainer);
        redirectAttributes.addFlashAttribute("added", "Trainer added!");
        
        return "redirect:/trainer/all";
    }
	
	@GetMapping("/update/{id}")
	public String showUpdateTrainerForm(@PathVariable Long id, 
			Model model,
			HttpServletRequest request) {
    	Trainer trainer = trainerService.getTrainerById(id);
    	model.addAttribute("trainer", trainer);
		model.addAttribute("trainer", trainer);
		request.getSession().setAttribute("trainerSession", trainer);
		
		System.out.println("updating trainer id="+ id);
		return "/trainers/trainer-update";
	}
	
	@PostMapping("/update")
	public String updateTrainer(@Valid @ModelAttribute("trainer") Trainer trainer,
			BindingResult results,
			Model model, 
			RedirectAttributes redirectAttributes) {
		
				
		if (results.hasErrors()){
			
			return "/trainers/trainer-update";
		}
       
		trainerService.updateTrainer(trainer);
        redirectAttributes.addFlashAttribute("updated", "Trainer updated!");
		return "redirect:/trainer/all";
		
	}
	
	@GetMapping("/delete/{id}")
    public String deleteTrainer(@PathVariable("id") long id, Model model, RedirectAttributes redirectAttributes) {
        Trainer trainer = trainerService.getTrainerById(id);               
        trainerService.delete(trainer);
        redirectAttributes.addFlashAttribute("deleted", "Trainer deleted!");
        return "redirect:/trainer/all";
    }
    
    @GetMapping("/all")
    public String showUserList(Model model, @RequestParam(required = false) String keyword,
    	    @RequestParam(required = false, defaultValue = "1") int page, @RequestParam(required = false, defaultValue = "3") int size) {
    	        
    	    	
    	    	
    	    	 try {
    	    	      List<Trainer> trainers = new ArrayList<Trainer>();
    	    	      
    	    	      Pageable paging = PageRequest.of(page - 1, size);

    	    	      Page<Trainer> pageTrainers;
    	    	      
    	    	        pageTrainers = trainerService.getAllTrainers(keyword, paging);
    	    	      
    	    	        model.addAttribute("keyword", keyword);

    	    	        trainers = pageTrainers.getContent();

    	    	      model.addAttribute("trainers", trainers);
    	    	      model.addAttribute("currentPage", pageTrainers.getNumber() + 1);
    	    	      model.addAttribute("totalItems", pageTrainers.getTotalElements());
    	    	      model.addAttribute("totalPages", pageTrainers.getTotalPages());
    	    	      model.addAttribute("pageSize", size);
    	    	      model.addAttribute("entityContext", "trainer/all");
    	    	    } catch (Exception e) {
    	    	      model.addAttribute("message", e.getMessage());
    	    	    }    
    	    	 
        return "/trainers/trainer-all";
    }
	
    
    //tbd Selecting a course
}
