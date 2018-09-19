package com.erik.learn.secureapi.web.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.erik.learn.secureapi.persistent.model.Person;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/person")
public class PersonController {

	@GetMapping
	public List<Person> getPersons() {

		return Arrays.asList(
				Person.builder()
						.id(1L)
						.name("erik")
						.address("bandung")
						.build(),
				Person.builder()
						.id(2L)
						.name("jake")
						.address("jakarta")
						.build());
	}

	@PostMapping
	public Person savePersons(@RequestBody Person person) {

		person.setId(new Random().nextLong());
		return person;
	}
}
