package com.attornatuschallenge.personapi;

import com.attornatuschallenge.personapi.entities.Person;
import com.attornatuschallenge.personapi.entities.PersonRequestData;
import com.attornatuschallenge.personapi.repositories.TestH2PersonRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.DataInput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PersonapiApplicationTests {

	@LocalServerPort
	private int port;

	private String baseUrl = "http://localhost";

	private static RestTemplate restTemplate;

	@Autowired
	private TestH2PersonRepository personH2Repository;

	@BeforeAll
	public static void init() {
		restTemplate = new RestTemplate();
	}

	@BeforeEach
	public void setUp() {
		baseUrl = baseUrl.concat(":").concat(port + "").concat("/people");
	}

	@Test
	public void testAddPerson() throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();

		PersonRequestData person = new PersonRequestData("Gol D. Roger", "11/01/1196");

		String personAsString = objectMapper.writeValueAsString(person);

		Map<String,Object> result = new ObjectMapper().readValue(personAsString, HashMap.class);

		ResponseEntity<String> response = restTemplate.postForEntity(baseUrl + "/add", result, String.class);

		int statusCode = response.getStatusCode().value();


		assertEquals(statusCode ,201 ,"Correct status code returned");
		assertEquals("Gol D. Roger successfully registered!", response.getBody());
		// 3 person are already being added by data seeding
		assertEquals(4, personH2Repository.findAll().size());
	}


}
