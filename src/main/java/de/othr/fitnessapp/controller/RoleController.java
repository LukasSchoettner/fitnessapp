package de.othr.fitnessapp.controller;

import de.othr.fitnessapp.config.MyUserDetails;
import de.othr.fitnessapp.model.Baseuser;
import de.othr.fitnessapp.model.Role;
import de.othr.fitnessapp.service.BaseuserService;
import de.othr.fitnessapp.service.RoleServiceI;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@RequestMapping(value = "/role")
@Controller
public class RoleController {

    private BaseuserService baseuserService;
    private RoleServiceI roleService;

    public RoleController(BaseuserService baseuserService, RoleServiceI roleService) {

        super();
        this.baseuserService = baseuserService;
        this.roleService = roleService;
    }

    @GetMapping("/add/{id}")
    public String showaddRoleForm(Model model, HttpServletRequest request,@PathVariable Long id) {
        Baseuser baseuser = baseuserService.getBaseuserById(id);
        List<Role> roles = roleService.findAll();
        Role roleselect=new Role();
        model.addAttribute("baseuser", baseuser);
        model.addAttribute("roles", roles);
        model.addAttribute("roleselect", roleselect);
        request.getSession().setAttribute("baseuserSession", baseuser);

        return "/roles/role-add";
    }

    @PostMapping("/add")
    public String addRole(@ModelAttribute("baseuser") Baseuser baseuser, @ModelAttribute Role roleselect, BindingResult result, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        baseuser= (Baseuser) request.getSession().getAttribute("baseuserSession");
        Baseuser processeduser =baseuserService.getBaseuserById(baseuser.getId());
        if (processeduser.getId()<1 || roleselect.getId()<1) {
            System.out.println(result.getAllErrors().toString());
            return "/roles/role-add";
        }

        List<Role> roles = processeduser.getRoles();
        roles.add(roleselect);
        processeduser.setRoles(roles);
        baseuserService.saveBaseuser(processeduser);
        redirectAttributes.addFlashAttribute("added", "Role added!");

        return "redirect:/role/all";
    }

    @GetMapping("/delete")
    public String deleteRole(@RequestParam() long userid, @RequestParam() long roleid, Model model, RedirectAttributes redirectAttributes) {
        Baseuser baseuser = baseuserService.getBaseuserById(userid);
        Role role=roleService.getRoleById(roleid);
        List<Role> userroles = baseuser.getRoles();
        userroles.remove(role);
        baseuser.setRoles(userroles);
        baseuserService.saveBaseuser(baseuser);
        redirectAttributes.addFlashAttribute("deleted", "Role deleted!");
        return "redirect:/role/all";
    }

    @GetMapping("/all")
    public String showRoleList(HttpServletRequest request, Model model, @RequestParam(required = false) String keyword,
                               @RequestParam(required = false, defaultValue = "1") int page, @RequestParam(required = false, defaultValue = "3") int size) {


        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        Long userid;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        if (principal instanceof MyUserDetails) {
            userid = ((MyUserDetails) principal).getId();
        } else {
            userid = null;
        }
        request.getSession().setAttribute("userid", userid);
        request.getSession().setAttribute("login", username);


        try {
            List<Baseuser> baseusers = new ArrayList<Baseuser>();

            Pageable paging = PageRequest.of(page - 1, size);

            Page<Baseuser> pageBaseusers;

            pageBaseusers = baseuserService.getAllBaseusers(keyword, paging);

            model.addAttribute("keyword", keyword);

            baseusers = pageBaseusers.getContent();

            model.addAttribute("baseusers", baseusers);
            model.addAttribute("currentPage", pageBaseusers.getNumber() + 1);
            model.addAttribute("totalItems", pageBaseusers.getTotalElements());
            model.addAttribute("totalPages", pageBaseusers.getTotalPages());
            model.addAttribute("pageSize", size);
            model.addAttribute("entityContext", "role/all");
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }

        return "/roles/role-all";
    }

}