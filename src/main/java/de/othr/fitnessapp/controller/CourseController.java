package de.othr.fitnessapp.controller;

import de.othr.fitnessapp.model.Course;
import de.othr.fitnessapp.model.Workout;
import de.othr.fitnessapp.service.CertServiceI;
import de.othr.fitnessapp.service.CourseServiceI;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Log4j2
@RequestMapping(value = "/course")
@AllArgsConstructor
public class CourseController {
    private CourseServiceI courseService;
    private CertServiceI certService;
    //private TrainerServiceI trainerService;
    //private UserServiceI UserService;
    //private GymServiceI GymService;

    @GetMapping(value = "/add")
    public String showCourseAddForm(Model model) {
        Course course = new Course();
        model.addAttribute("course", course);
        return "/course/course-add-form";
    }

    @PostMapping(value = "/add")
    public String processCourseAddForm(@ModelAttribute @Valid Course course, BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            log.error("Error Count:" + result.getErrorCount());
            log.error("All Errors: \n" + result.getAllErrors());
            return "/course/course-add-form";
        }

        Course savedCourse = courseService.saveCourse(course);
        log.info("Saved Course with ID: {}", savedCourse.getId());
        redirectAttributes.addFlashAttribute("added", "Course added!");
        return "redirect:/course/all";
    }

    @GetMapping(value = "/update/{id}")
    public String showCourseUpdateForm(@PathVariable Long id, Model model) {
        Course course = courseService.getCourseById(id);
        log.info("Updating Course with ID: {}", course.getId());
        model.addAttribute("course", course);
        return "/course/course-update-form";
    }

    @PostMapping(value = "/update")
    public String processCourseUpdateForm(@ModelAttribute @Valid Course course, BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            log.error("Error Count:" + result.getErrorCount());
            log.error("All Errors: \n" + result.getAllErrors());
            return "/course/course-update-form";
        }

        //to update only specific attributes
        //Course existingCourse = courseService.getCourseById(course.getId());
        //existingCourse.setName(course.getName());
        //existingCourse.setDate(course.getDate());
        //existingCourse.setTrainer(course.getTrainer());


        Course updatedCourse = courseService.updateCourse(course);
        log.info("Updated Course with ID: {}", updatedCourse.getId());
        redirectAttributes.addFlashAttribute("updated", "Course updated!");
        return "redirect:/course/all";
    }

    @GetMapping(value = "/delete/{id}")
    public String processCourseDeleteForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Course course = courseService.getCourseById(id);
        courseService.deleteCourseById(course.getId());
        log.info("Deleted Course with ID: {}", id);
        redirectAttributes.addFlashAttribute("deleted", "Course deleted!");
        return "redirect:/course/all";
    }

    @GetMapping(value = "/all")
    public String showCourseList(Model model,@RequestParam(required = false) String keyword,
                                         @RequestParam(required = false, defaultValue = "1") int page,
                                         @RequestParam(required = false, defaultValue = "3") int size) {

        try {

            Pageable paging = PageRequest.of(page - 1, size);

            Page<Course> pageCourses = courseService.getAllCourses(paging);

            //model.addAttribute("keyword", keyword);

            List<Course> courses = pageCourses.getContent();

            model.addAttribute("courses", courses);
            model.addAttribute("currentPage", pageCourses.getNumber() + 1);
            model.addAttribute("totalItems", pageCourses.getTotalElements());
            model.addAttribute("totalPages", pageCourses.getTotalPages());
            model.addAttribute("pageSize", size);
            model.addAttribute("entityContext", "course/all");
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }

        return "/course/course-all";
    }

    @GetMapping(value = "/history")
    public String showWorkoutHistoryList(Model model, @RequestParam(required = false) String keyword,
                                         @RequestParam(required = false, defaultValue = "1") int page,
                                         @RequestParam(required = false, defaultValue = "3") int size) {

        try {
            Pageable paging = PageRequest.of(page - 1, size);

            Page<Course> pageCourses = courseService.getPastCourses(paging);

            //model.addAttribute("keyword", keyword);

            List<Course> courses = pageCourses.getContent();

            model.addAttribute("courses", courses);
            model.addAttribute("currentPage", pageCourses.getNumber() + 1);
            model.addAttribute("totalItems", pageCourses.getTotalElements());
            model.addAttribute("totalPages", pageCourses.getTotalPages());
            model.addAttribute("pageSize", size);
            model.addAttribute("entityContext", "course/history");
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }

        return "/course/course-history";
    }

    @GetMapping(value = "/history/cert/{id}")
    public ResponseEntity<byte[]> processCourseCertForm(@PathVariable Long id) {
        Course course = courseService.getCourseById(id);
        log.info("Downloaded Certificate for Course with ID: {}", course.getId());
        return certService.getCert(course);
    }

    // TODO: Add course to user (Registration?)
    // TODO: Select trainer on course
}
