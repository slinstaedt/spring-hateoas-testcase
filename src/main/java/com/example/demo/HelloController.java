package com.example.demo;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class HelloController {

	private static final String WORLD = "World";

	@RequestMapping(path = "/hello", produces = "application/hal+json")
	public ResponseEntity<EntityModel<Greeting>> hello(@RequestParam String name) {
		Greeting greeting = new Greeting("Hello " + name);
		if (WORLD.equalsIgnoreCase(name)) {
			return ResponseEntity.ok(EntityModel.of(greeting));
		} else {
			Link world = linkTo(methodOn(HelloController.class).hello(WORLD)).withRel("a:world");
			return ResponseEntity.ok(EntityModel.of(greeting).add(world));
		}
	}
}
