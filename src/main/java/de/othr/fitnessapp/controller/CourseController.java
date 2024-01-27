package de.othr.fitnessapp.controller;

import de.othr.fitnessapp.config.MyUserDetails;
import de.othr.fitnessapp.model.Course;
import de.othr.fitnessapp.model.Customer;
import de.othr.fitnessapp.service.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Set;
import java.util.stream.Collectors;

@Controller
@Log4j2
@RequestMapping(value = "/course")
@AllArgsConstructor
public class CourseController {
    private CourseServiceI courseService;
    private CertServiceI certService;
    private WeatherServiceI weatherService;
    private EmailServiceI emailService;
    private TrainerServiceI trainerService;
    private CustomerServiceI customerService;


    @GetMapping(value = "/add")
    public String showCourseAddForm(@RequestParam(required = false) String lang,
                                    @RequestParam(required = false) String forecastDate,
                                    Model model,
                                    @AuthenticationPrincipal MyUserDetails user) {

        Course course = new Course();
        model.addAttribute("course", course);
        model.addAttribute("EN", "/course/add");
        model.addAttribute("DE", "/course/add?lang=de");

        if (forecastDate != null) {
            log.info("Trainer " + user.getUsername() + " has requested a weather forecast!");
            model.addAttribute("forecast", weatherService.getTempForecast(forecastDate));
        }
        if (lang != null) {
            model.addAttribute("de", "de");
        }
        return "/course/course-add-form";
    }

    @PostMapping(value = "/add")
    public String processCourseAddForm(@ModelAttribute @Valid Course course,
                                       BindingResult result,
                                       RedirectAttributes redirectAttributes,
                                       @AuthenticationPrincipal MyUserDetails user) {

        if (result.hasErrors()) {
            log.error("Error Count:" + result.getErrorCount());
            log.error("All Errors: \n" + result.getAllErrors());
            return "/course/course-add-form";
        }

        course.setTrainer(trainerService.getTrainerById(user.getId()));
        Course savedCourse = courseService.saveCourse(course);

        log.info("Saved Course with ID and name: {} {}", savedCourse.getId(), savedCourse.getName());
        redirectAttributes.addFlashAttribute("added", "Course added!");

        try {
            emailService.sendEmail("To", "Message", "E-Mail");
            log.info("Mail about course creation successfully sent: {}", savedCourse.getName());
            redirectAttributes.addFlashAttribute("sent", "Mail sent succesfully!");
        } catch (MailException e) {
            log.error("Send Mail failed!");
            redirectAttributes.addFlashAttribute("error", "Mail could not be sent!");
        }
        return "redirect:/course/all";
    }

    @GetMapping(value = "/update/{id}")
    public String showCourseUpdateForm(@PathVariable Long id,
                                       @RequestParam(required = false) String lang,
                                       Model model,
                                       RedirectAttributes redirectAttributes,
                                       @AuthenticationPrincipal MyUserDetails user) {

        if (!courseService.trainerOwnsCourse(id, user.getId())) {
            redirectAttributes.addFlashAttribute("error", "This course doesnt exist or you are not the owner!");
            return "redirect:/course/all";
        }

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
    public String processCourseUpdateForm(@ModelAttribute @Valid Course course,
                                          BindingResult result,
                                          RedirectAttributes redirectAttributes,
                                          @AuthenticationPrincipal MyUserDetails user) {

        if (!courseService.trainerOwnsCourse(course.getId(), user.getId())) {
            redirectAttributes.addFlashAttribute("error", "This course doesnt exist or you are not the owner!");
            return "redirect:/course/all";
        }

        if (result.hasErrors()) {
            log.error("Error Count:" + result.getErrorCount());
            log.error("All Errors: \n" + result.getAllErrors());
            return "/course/course-update-form";
        }

        Course existingCourse = courseService.getCourseById(course.getId());
        existingCourse.setName(course.getName());
        existingCourse.setDate(course.getDate());
        Course updatedCourse = courseService.updateCourse(existingCourse);
        log.info("Updated Course with ID and name: {} {}", updatedCourse.getId(), updatedCourse.getName());
        redirectAttributes.addFlashAttribute("updated", "Course updated!");
        return "redirect:/course/all";
    }

    @GetMapping(value = "/delete/{id}")
    public String processCourseDeleteForm(@PathVariable Long id,
                                          RedirectAttributes redirectAttributes,
                                          @AuthenticationPrincipal MyUserDetails user) {

        if (!courseService.trainerOwnsCourse(id, user.getId())) {
            redirectAttributes.addFlashAttribute("error", "This course doesnt exist or you are not the owner!");
            return "redirect:/course/all";
        }

        Course course = courseService.getCourseById(id);
        courseService.deleteCourseById(course.getId());
        log.info("Deleted Course with ID and name: {} {}", course.getId(), course.getName());
        redirectAttributes.addFlashAttribute("deleted", "Course deleted!");
        return "redirect:/course/all";
    }

    @GetMapping(value = "/all")
    public String showCourseList(Model model,
                                 @RequestParam(required = false) String keyword,
                                 @RequestParam(required = false, defaultValue = "1") int page,
                                 @RequestParam(required = false, defaultValue = "3") int size,
                                 @RequestParam(required = false) String lang,
                                 @AuthenticationPrincipal MyUserDetails user) {

        Set<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        Pageable paging = PageRequest.of(page - 1, size);
        Page<Course> pageCourses;

        if (roles.contains("TRAINER")) {
            pageCourses = courseService.getAllCoursesByTrainerId(paging, user.getId());
        } else if (roles.contains("CUSTOMER")) {
            pageCourses = courseService.getCoursesWhereCustomerIsNotRegistered(paging, customerService.findCustomerById(user.getId()));
        } else {
            return "redirect:/home";
        }

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

    @GetMapping(value = "/registered")
    public String showRegisteredCourseList(Model model,
                                           @RequestParam(required = false) String keyword,
                                           @RequestParam(required = false, defaultValue = "1") int page,
                                           @RequestParam(required = false, defaultValue = "3") int size,
                                           @RequestParam(required = false) String lang,
                                           @AuthenticationPrincipal MyUserDetails user) {

        Pageable paging = PageRequest.of(page - 1, size);

        Page<Course> pageCourses = courseService.getCoursesWhereCustomerIsRegistered(paging, customerService.findCustomerById(user.getId()));;

        //model.addAttribute("keyword", keyword);
        fillPaginationView(model, size, pageCourses);
        model.addAttribute("entityContext", "course/all");
        model.addAttribute("EN", "/course/all");
        model.addAttribute("DE", "/course/all?lang=de");

        if (lang != null) {
            model.addAttribute("de", "de");
        }
        return "/course/course-registered";
    }

    @GetMapping(value = "/details/{courseId}")
    public String showCourseDetails(@PathVariable Long courseId,
                                    Model model,
                                    RedirectAttributes redirectAttributes,
                                    @RequestParam(required = false) String keyword,
                                    @RequestParam(required = false) String lang,
                                    @AuthenticationPrincipal MyUserDetails user) {

        Set<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        if (roles.contains("TRAINER") && courseService.trainerOwnsCourse(courseId, user.getId())) {
            Course course = courseService.getCourseById(courseId);
            model.addAttribute("course", course);
            model.addAttribute("participants", courseService.getParticipantsofCourse(courseId));
            model.addAttribute("customers", customerService.getCustomersNotInCourse(course));
        } else if (roles.contains("CUSTOMER") && !courseService.customerIsParticipant(courseId, user.getId())) {
            model.addAttribute("course", courseService.getCourseById(courseId));
        } else {
            redirectAttributes.addFlashAttribute("error", "You are not allowed to see the course details!");
            return "redirect:/home";
        }

        if (lang != null) {
            model.addAttribute("de", "de");
        }
        return "/course/course-details";
    }

    @GetMapping(value = "/register/{courseId}")
    public String processCourseRegisterForm(@PathVariable Long courseId,
                                            @RequestParam(required = false) Long customerId,
                                            RedirectAttributes redirectAttributes,
                                            @AuthenticationPrincipal MyUserDetails user) {

        Set<String> roles = user.getAuthorities().stream()
                                                    .map(GrantedAuthority::getAuthority)
                                                    .collect(Collectors.toSet());

        if (roles.contains("TRAINER") && !courseService.customerIsParticipant(courseId, customerId)
                                        && courseService.trainerOwnsCourse(courseId, user.getId())) {
            courseService.registerParticipant(courseId, customerId);
            log.info("Registered customer in course with ID: {}", courseId);
            redirectAttributes.addFlashAttribute("added", "Successfully registered!");
            return "redirect:/course/details/" + courseId;
        } else if (roles.contains("CUSTOMER") && !courseService.customerIsParticipant(courseId, user.getId())) {
            courseService.registerParticipant(courseId, user.getId());
            log.info("Registered customer in course with ID: {}", courseId);
            redirectAttributes.addFlashAttribute("added", "Successfully registered!");
            return "redirect:/course/registered";
        } else {
            redirectAttributes.addFlashAttribute("error", "You are already registered in this course!");
            return "redirect:/home";
        }
    }

    @GetMapping(value = "/deregister/{courseId}")
    public String processCourseDeregisterForm(@PathVariable Long courseId,
                                              @RequestParam(required = false) Long customerId,
                                              RedirectAttributes redirectAttributes,
                                              @AuthenticationPrincipal MyUserDetails user) {

        Set<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        if (roles.contains("TRAINER") && courseService.customerIsParticipant(courseId, customerId)
                                        && courseService.trainerOwnsCourse(courseId, user.getId())) {
            courseService.deregisterParticipant(courseId, customerId);
            log.info("Deregistered customer in course with ID: {}", courseId);
            redirectAttributes.addFlashAttribute("deleted", "Successfully deregistered!");
            return "redirect:/course/details/" + courseId;
        } else if (roles.contains("CUSTOMER") && courseService.customerIsParticipant(courseId, user.getId())) {
            courseService.deregisterParticipant(courseId, user.getId());
            log.info("Deregistered customer in course with ID: {}", courseId);
            redirectAttributes.addFlashAttribute("deleted", "Successfully deregistered!");
            return "redirect:/course/all";
        } else {
            redirectAttributes.addFlashAttribute("error", "You are not registered in this course!");
            return "redirect:/home";
        }
    }



    @GetMapping(value = "/history")
    public String showCourseHistoryList(Model model,
                                        @RequestParam(required = false) String keyword,
                                        @RequestParam(required = false, defaultValue = "1") int page,
                                        @RequestParam(required = false, defaultValue = "3") int size,
                                        @RequestParam(required = false) String lang,
                                        @AuthenticationPrincipal MyUserDetails user) {

        Set<String> roles = user.getAuthorities().stream()
                                                    .map(GrantedAuthority::getAuthority)
                                                    .collect(Collectors.toSet());

        Pageable paging = PageRequest.of(page - 1, size);
        Page<Course> pageCourses;

        if (roles.contains("TRAINER")) {
            pageCourses = courseService.getPastCoursesByTrainerId(paging, user.getId());
        } else if (roles.contains("CUSTOMER")) {
            pageCourses = courseService.getPastCoursesWhereCustomerIsRegistered(paging, customerService.findCustomerById(user.getId()));
        } else {
            return "redirect:/home";
        }

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
    public ResponseEntity<byte[]> processCourseCertForm(@PathVariable Long id,
                                                        @AuthenticationPrincipal MyUserDetails user) {

        if (!courseService.customerIsParticipant(id, user.getId())) {
            return ResponseEntity.badRequest().build();
        }

        Customer customer = customerService.findCustomerById(user.getId());
        Course course = courseService.getCourseById(id);
        log.info("{} downloaded Certificate for Course with ID and name: {} {}", customer.getLast_name(), course.getId(), course.getName());
        return certService.getCourseCert(customer, course);
    }

    private void fillPaginationView(Model model, int size, Page<Course> pageCourses) {
        model.addAttribute("courses", pageCourses.getContent());
        model.addAttribute("currentPage", pageCourses.getNumber() + 1);
        model.addAttribute("totalItems", pageCourses.getTotalElements());
        model.addAttribute("totalPages", pageCourses.getTotalPages());
        model.addAttribute("pageSize", size);
    }
}
