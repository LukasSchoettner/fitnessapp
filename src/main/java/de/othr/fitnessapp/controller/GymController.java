package de.othr.fitnessapp.controller;


import de.othr.fitnessapp.service.CourseServiceI;
import de.othr.fitnessapp.service.RoleServiceI;
import de.othr.fitnessapp.service.TrainerServiceI;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import de.othr.fitnessapp.model.Gym;
import de.othr.fitnessapp.service.GymServiceI;
import jakarta.validation.Valid;

@RequestMapping(value = "/gym")
@Controller
@Log4j2
public class GymController {
    private GymServiceI gymService;
    private CourseServiceI courseService;
    private RoleServiceI roleService;
    private TrainerServiceI trainerService;


    public GymController() {
    }

    @Autowired

    public GymController(GymServiceI gymService, CourseServiceI courseService, RoleServiceI roleService, TrainerServiceI trainerService) {
        this.gymService = gymService;
        this.courseService = courseService;
        this.roleService = roleService;
        this.trainerService = trainerService;
    }

    @GetMapping("/add")
    public String showGymAddForm(Model model) {
        Gym gym = new Gym();
        gym.setId((long) -1);
        model.addAttribute("gym", gym);
        return "/gyms/gyms-add";
    }

    @PostMapping("/add")
    public String addGym(@Valid Gym gym,
                             BindingResult result,
                             Model model,
                             RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            log.error("Validation error. Please check the input.");
            return "/gym/gym-add";
        }

        gymService.saveGym(gym);
        redirectAttributes.addFlashAttribute("added", "Gym added!");

        return "redirect:/gym/all";
    }


    @GetMapping("/update/{id}")
    public String showUpdateGymForm(@PathVariable Long id, Model model) {
        model.addAttribute("gym", gymService.getGym(id));
        System.out.println("updating gym id="+ id);
        return "/gym/gym-update";
    }


    @PostMapping("/update")
    public String updateGym(@Valid @ModelAttribute("GYM") Gym gym,
                                BindingResult results,
                                Model model,
                                RedirectAttributes redirectAttributes) {

        // get Gym from database by id
        Gym existingGym = gymService.getGym(gym.getId());

        existingGym.setEmail(gym.getEmail());
        existingGym.setName(gym.getName());

        if (results.hasErrors()){

            return "/gym/gym-update";
        }

        gymService.updateGym(existingGym);

        redirectAttributes.addFlashAttribute("updated", "Gym updated!");

        return "redirect:/gym/all";

    }


    @GetMapping("/delete/{id}")
    public String deleteGym(@PathVariable("id") long id, Model model, RedirectAttributes redirectAttributes) {
        Gym gym = gymService.getGym(id);
        gymService.deleteGym(id);
        redirectAttributes.addFlashAttribute("deleted", "Gym deleted!");
        return "redirect:/gym/all";
    }


}
