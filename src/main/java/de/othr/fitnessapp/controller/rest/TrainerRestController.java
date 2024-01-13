package de.othr.fitnessapp.controller.rest;

import java.util.ArrayList;
import java.util.List;


import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.othr.fitnessapp.model.Role;
import de.othr.fitnessapp.model.Trainer;
import de.othr.fitnessapp.service.RoleServiceI;
import de.othr.fitnessapp.service.TrainerServiceI;


@RestController
public class TrainerRestController {

	
	private final TrainerServiceI trainerService;
	private RoleServiceI roleService;

	TrainerRestController(TrainerServiceI trainerService, RoleServiceI roleService) {
		this.trainerService =trainerService;
		this.roleService=roleService;

	}
	
	
	@PostMapping("/api/trainers/")
	 public ResponseEntity<EntityModel<Trainer>> post(@RequestBody Trainer trainerFromRequest) {
		
		List<Role> roles =trainerFromRequest.getRoles();
        roles.add(roleService.findRoleByDescription("TRAINER"));
        trainerFromRequest.setRoles(roles);
        
        Trainer trainer = trainerService.saveTrainer(trainerFromRequest);
		EntityModel<Trainer> entityModel = EntityModel.of(trainer);
		Link trainerLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TrainerRestController.class)
				  .getTrainerByID(trainer.getId())).withSelfRel();
		
		
		entityModel.add(trainerLink);
		
		return new ResponseEntity<>(entityModel, HttpStatus.CREATED);
	    
	  }
	
	@PutMapping("/api/trainers/{id}")
	 public ResponseEntity<EntityModel<Trainer>> put(@PathVariable long id, @RequestBody Trainer trainerFromRequest) {
		
		Trainer trainer = trainerService.updateTrainerAndAddress(id, trainerFromRequest, trainerFromRequest.getAddress());
		EntityModel<Trainer> entityModel = EntityModel.of(trainer);
		Link trainerLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TrainerRestController.class)
				  .getTrainerByID(trainer.getId())).withSelfRel();
		
		
		entityModel.add(trainerLink);
		
		return new ResponseEntity<>(entityModel, HttpStatus.OK);
	    
	  }
	
	
	@GetMapping("/api/trainers/{id}")
	ResponseEntity<EntityModel<Trainer>> getTrainerByID(@PathVariable long id) {

		Trainer trainer = trainerService.getTrainerById(id);
		
		if (trainer==null) {
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		EntityModel<Trainer> entityModel = EntityModel.of(trainer);
		
		Link trainerLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TrainerRestController.class)
				  .getTrainerByID(trainer.getId())).withSelfRel();
	
		entityModel.add(trainerLink);
		
		return new ResponseEntity<>(entityModel, HttpStatus.OK);
		
	}
	
	
	@GetMapping("/api/trainers/")
	public ResponseEntity <CollectionModel<EntityModel<Trainer>>> all() {

		 List <Trainer> trainers = trainerService.getAllTrainers();
		 
		 if (trainers.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
				 
		 List<EntityModel<Trainer>> trainerModels = new ArrayList();
		 
		 for (Trainer trainer : trainers) {
			 
			 EntityModel<Trainer> entityModel = EntityModel.of(trainer);
			 
			 Link trainerLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TrainerRestController.class)
					  .getTrainerByID(trainer.getId())).withSelfRel();
			 
			 entityModel.add(trainerLink);
			 trainerModels.add(entityModel);
			 			 
			 
		 }
		 
		 Link link= WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TrainerRestController.class)
			      .all()).withSelfRel();
		 
		 return new ResponseEntity<>( CollectionModel.of(trainerModels, link), HttpStatus.OK);
		 
		
	}
	
	@DeleteMapping("/api/trainers/{id}")
	ResponseEntity<?> deleteTrainer(@PathVariable Long id) {

		Trainer trainer = trainerService.getTrainerById(id);
		
		if (trainer==null) {
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	
		trainerService.delete(trainer);

	  return ResponseEntity.noContent().build();
	  
	}
}