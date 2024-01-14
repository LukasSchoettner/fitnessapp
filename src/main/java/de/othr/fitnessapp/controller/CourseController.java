package de.othr.fitnessapp.controller;

import de.othr.fitnessapp.model.Course;
import de.othr.fitnessapp.service.CertServiceI;
import de.othr.fitnessapp.service.CourseServiceI;
import de.othr.fitnessapp.service.EmailServiceI;
import de.othr.fitnessapp.service.WeatherServiceI;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Log4j2
@RequestMapping(value = "/course")
@AllArgsConstructor
public class CourseController {
    private CourseServiceI courseService;
    private CertServiceI certService;
    private WeatherServiceI weatherService;
    private EmailServiceI emailService;
    //private TrainerServiceI trainerService;
    //private UserServiceI UserService;
    //private GymServiceI GymService;

    @GetMapping(value = "/add")
    public String showCourseAddForm(@RequestParam(required = false) String lang, @RequestParam(required = false) String forecastDate, Model model) {

        Course course = new Course();
        model.addAttribute("course", course);
        model.addAttribute("EN", "/course/add");
        model.addAttribute("DE", "/course/add?lang=de");

        if (forecastDate != null) {
            model.addAttribute("forecast", weatherService.getTempForecast(forecastDate));
        }
        if (lang != null) {
            model.addAttribute("de", "de");
        }
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
        //TODO: Add savedCourse to Trainer/Gym and save back
        log.info("Saved Course with ID and name: {} {}", savedCourse.getId(), savedCourse.getName());
        redirectAttributes.addFlashAttribute("added", "Course added!");

        try {
            emailService.sendEmail("To", "Message", "E-Mail");
            log.info("Mail about course creation successfully sent: {}", savedCourse.getName());
            redirectAttributes.addFlashAttribute("send", "Mail sent succesfully!");
        } catch (MailException e) {
            redirectAttributes.addFlashAttribute("error", "Mail could not be sent!");
            log.error("Send Mail failed!");
        }
        return "redirect:/course/all";
    }

    @GetMapping(value = "/update/{id}")
    public String showCourseUpdateForm(@PathVariable Long id, @RequestParam(required = false) String lang, Model model) {

        Course course = courseService.getCourseById(id);
        log.info("Updating Course with ID and name: {} {}", course.getId(), course.getName());
        model.addAttribute("course", course);
        model.addAttribute("EN", "/course/update/"+id);
        model.addAttribute("DE", "/course/update/"+id+"?lang=de");
        if (lang != null) {
            model.addAttribute("de", "de");
        }
        return "/course/course-update-form";
    }

    @PostMapping(value = "/update")
    public String processCourseUpdateForm(@ModelAttribute @Valid Course course, BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            log.error("Error Count:" + result.getErrorCount());
            log.error("All Errors: \n" + result.getAllErrors());
            return "/course/course-update-form";
        }

        //Course existingCourse = courseService.getCourseById(course.getId());
        //existingCourse.setName(course.getName());
        //existingCourse.setDate(course.getDate());
        //existingCourse.setTrainer(course.getTrainer());

        Course updatedCourse = courseService.updateCourse(course);
        log.info("Updated Course with ID and name: {} {}", updatedCourse.getId(), updatedCourse.getName());
        redirectAttributes.addFlashAttribute("updated", "Course updated!");
        return "redirect:/course/all";
    }

    @GetMapping(value = "/delete/{id}")
    public String processCourseDeleteForm(@PathVariable Long id, RedirectAttributes redirectAttributes) {


        Course course = courseService.getCourseById(id);
        courseService.deleteCourseById(course.getId());
        //TODO: Remove the course from Trainer/Gym/Customer and save back
        log.info("Deleted Course with ID and name: {} {}", course.getId(), course.getName());
        redirectAttributes.addFlashAttribute("deleted", "Course deleted!");
        return "redirect:/course/all";
    }

    @GetMapping(value = "/all")
    public String showCourseList(Model model, @RequestParam(required = false) String keyword,
                                                @RequestParam(required = false, defaultValue = "1") int page,
                                                @RequestParam(required = false, defaultValue = "3") int size,
                                                @RequestParam(required = false) String lang) {

            Pageable paging = PageRequest.of(page - 1, size);
            //TODO: Change to the personal courses
            Page<Course> pageCourses = courseService.getAllCourses(paging);

            //model.addAttribute("keyword", keyword);

            fillPaginationView(model, size, pageCourses);
            model.addAttribute("entityContext", "course/all");
            model.addAttribute("EN", "/course/all");
            model.addAttribute("DE", "/course/all?lang=de");

        if (lang != null) {
            model.addAttribute("de", "de");
        }
        return "/course/course-all";
    }



    @GetMapping(value = "/history")
    public String showWorkoutHistoryList(Model model, @RequestParam(required = false) String keyword,
                                                        @RequestParam(required = false, defaultValue = "1") int page,
                                                        @RequestParam(required = false, defaultValue = "3") int size,
                                                        @RequestParam(required = false) String lang) {

            Pageable paging = PageRequest.of(page - 1, size);
            //TODO: Change to the personal courses
            Page<Course> pageCourses = courseService.getPastCourses(paging);

            //model.addAttribute("keyword", keyword);

            fillPaginationView(model, size, pageCourses);
            model.addAttribute("entityContext", "course/history");
            model.addAttribute("EN", "/course/history");
            model.addAttribute("DE", "/course/history?lang=de");

        if (lang != null) {
            model.addAttribute("de", "de");
        }
        return "/course/course-history";
    }

    @GetMapping(value = "/history/cert/{id}")
    public ResponseEntity<byte[]> processCourseCertForm(@PathVariable Long id) {
        Course course = courseService.getCourseById(id);
        log.info("Downloaded Certificate for Course with ID and name: {} {}", course.getId(), course.getName());
        //TODO: Add parameter name
        return certService.getCourseCert(course);
    }

    //TODO: list Workout on Courses
    //TODO: delete Workout from Course
    //TODO: add Workout to Course ...

    private void fillPaginationView(Model model, int size, Page<Course> pageCourses) {
        model.addAttribute("courses", pageCourses.getContent());
        model.addAttribute("currentPage", pageCourses.getNumber() + 1);
        model.addAttribute("totalItems", pageCourses.getTotalElements());
        model.addAttribute("totalPages", pageCourses.getTotalPages());
        model.addAttribute("pageSize", size);
    }
}
