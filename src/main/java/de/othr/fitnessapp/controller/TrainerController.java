package de.othr.fitnessapp.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

import de.othr.fitnessapp.model.Address;
import de.othr.fitnessapp.model.Message;
import de.othr.fitnessapp.model.Role;
import de.othr.fitnessapp.model.Trainer;
import de.othr.fitnessapp.model.User;
import de.othr.fitnessapp.service.RoleServiceI;
import de.othr.fitnessapp.service.TrainerServiceI;
import de.othr.fitnessapp.service.impl.MailServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;


@RequestMapping(value = "/trainer")
@Controller
public class TrainerController {

	private TrainerServiceI trainerService;
	private RoleServiceI roleService;
	
	public TrainerController(TrainerServiceI trainerService,RoleServiceI roleService) {
			
		super();
		this.trainerService = trainerService;
		this.roleService = roleService;
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
        
    	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        trainer.setPassword("{bcrypt}"+passwordEncoder.encode(trainer.getPassword()));
    
        List<Role> roles =trainer.getRoles();
        roles.add(roleService.findRoleByDescription("TRAINER"));
        trainer.setRoles(roles);
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
		request.getSession().setAttribute("trainerSession", trainer);
		
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
		Address address= trainer.getAddress();
		trainerService.updateTrainerAndAddress(trainer.getId(), trainer, address);
        redirectAttributes.addFlashAttribute("updated", "Trainer updated!");
		return "redirect:/trainer/all";
		
	}
	
	@GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model, RedirectAttributes redirectAttributes) {
        Trainer trainer = trainerService.getTrainerById(id);               
        trainerService.delete(trainer);
        redirectAttributes.addFlashAttribute("deleted", "Trainer deleted!");
        return "redirect:/trainer/all";
    }
    
    @GetMapping("/all")
    public String showUserList(HttpServletRequest request, Model model, @RequestParam(required = false) String keyword,
    	    @RequestParam(required = false, defaultValue = "1") int page, @RequestParam(required = false, defaultValue = "3") int size) {
    	        
    
    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		
		if (principal instanceof UserDetails) {
		   username = ((UserDetails)principal).getUsername();
		} else {
		  username = principal.toString();
		}
		
		request.getSession().setAttribute("login", username);
		
    	    	
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
    
    @GetMapping("/sendmail/{id}")
	public String showSendMail(@PathVariable Long id, 
			Model model,
			HttpServletRequest request) {
    	Trainer trainer = trainerService.getTrainerById(id);
    	Message message = new Message();
    	message.setEmail(trainer.getEmail());
    	model.addAttribute("message", message);
		request.getSession().setAttribute("messageSession", message);
		
		return "/trainers/trainer-write-mail";
	}
    
    @PostMapping("/sendmail")
	public String SendMail(@Valid @ModelAttribute("message") Message message,
			BindingResult results,
			Model model, 
			RedirectAttributes redirectAttributes) {
    	
    	if (results.hasErrors()){
			
			return "/trainers/trainer-write-mail";
		}
		System.out.println("Email:"+message.getEmail()+"Subject:"+message.getSubject()+"Message:"+message.getMessage());
    	MailServiceImpl mailServiceImpl;
		try {
			mailServiceImpl = new MailServiceImpl();
			mailServiceImpl.sendMail(message.getSubject(), message.getMessage(), message.getEmail());
			redirectAttributes.addFlashAttribute("sent", "Mail was sent successfully!");
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("notsent", "Send Mail failed!");
		}
		return "redirect:/trainer/all";
		
	}
	
    
}
