package com.attornatuschallenge.personapi;

import com.attornatuschallenge.personapi.entities.*;
import com.attornatuschallenge.personapi.repositories.TestH2PersonRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

	@Test
	public void testGetPeople() {
		List<PersonResponseData> response = restTemplate.getForObject(baseUrl, List.class);
		assertEquals(personH2Repository.findAll().size(), response.size());
	}

	@Test
	public void testGetPersonById() throws JsonProcessingException {
		String response = restTemplate.getForObject(baseUrl + "/{id}", String.class,1);

		ObjectMapper objectMapper = new ObjectMapper();
		PersonResponseData data = objectMapper.readValue(response, PersonResponseData.class);

		//Response should be the person1 added on TestConfig
		assertEquals("Fulano", data.getName());
		assertEquals("19/02/1996", data.getBirthDate());
		assertEquals(1, data.getId());
		assertEquals("Rua tal", data.getAddresses().stream().findFirst().get().getLogradouro());
		assertEquals("60730012", data.getAddresses().stream().findFirst().get().getCep());
		assertEquals("1432", data.getAddresses().stream().findFirst().get().getHouseNumber());
		assertEquals("Fortaleza", data.getAddresses().stream().findFirst().get().getCity());
	}

	@Test
	public void testGetPersonById_Fail() throws HttpClientErrorException {
		try{
			restTemplate.getForObject(baseUrl + "/{id}", Person.class,999);
		}catch (HttpClientErrorException e) {
			int statusCode = e.getStatusCode().value();
			assertEquals(404, statusCode);
		}
	}

	@Test
	public void testGetPersonAddressById() throws JsonProcessingException {
		String response = restTemplate.getForObject(baseUrl + "/{id}/address", String.class,1);

		ObjectMapper objectMapper = new ObjectMapper();
		Address[] data = objectMapper.readValue(response, Address[].class);

		//Response should be the person1's address info added on TestConfig
		assertEquals("Rua tal", Arrays.stream(data).findFirst().get().getLogradouro());
		assertEquals("60730012", Arrays.stream(data).findFirst().get().getCEP());
		assertEquals("1432", Arrays.stream(data).findFirst().get().getHouseNumber());
		assertEquals("Fortaleza", Arrays.stream(data).findFirst().get().getCity());
		assertEquals(false, Arrays.stream(data).findFirst().get().getMain());
	}

	@Test
	public void testGetPersonAddressById_Fail() throws HttpClientErrorException {
		try{
			restTemplate.getForObject(baseUrl + "/{id}/address", Address.class,998);
		}catch (HttpClientErrorException e) {
			int statusCode = e.getStatusCode().value();
			assertEquals(404, statusCode);
		}
	}

	@Test
	public void testGetPersonMainAddress() throws JsonProcessingException {
		String response = restTemplate.getForObject(baseUrl + "/{id}/address/main", String.class,3);

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		Person data = objectMapper.readValue(response, Person.class);

		//Response should be the person3's address info added on TestConfig
		assertEquals("Fulano de Tal", data.getName());
		assertEquals("20/02/1994", data.getBirthDate());
		assertEquals("Rua distante de tudo", data.getAddresses().get(0).getLogradouro());
		assertEquals("61535111", data.getAddresses().get(0).getCEP());
		assertEquals("567", data.getAddresses().get(0).getHouseNumber());
		assertEquals("Nowhere", data.getAddresses().get(0).getCity());
		assertEquals(true, data.getAddresses().get(0).getMain());
	}

	@Test
	public void testGetPersonMainAddress_Fail() throws HttpClientErrorException {
		try{
			restTemplate.getForObject(baseUrl + "/{id}/address/main", Person.class,1);
		}catch (HttpClientErrorException e) {
			int statusCode = e.getStatusCode().value();
			assertEquals(404, statusCode);
		}
	}

	@Test
	public void testGetPersonByName() throws JsonProcessingException {
		String response = restTemplate.getForObject(baseUrl + "/name?name={name}", String.class, "Fulano");

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		Person[] data = objectMapper.readValue(response, Person[].class);

		//Should contain person1 and person3
		assertEquals(2, data.length);
		assertEquals("Fulano", data[0].getName());
		assertEquals("Fulano de Tal", data[1].getName());
	}

	@Test
	public void testUpdatePersonInfo() throws JsonProcessingException {
		//First let's check person2 current info.
		String response = restTemplate.getForObject(baseUrl + "/{id}", String.class,2);

		ObjectMapper objectMapper = new ObjectMapper();
		PersonResponseData person2CurrentData = objectMapper.readValue(response, PersonResponseData.class);

		assertEquals("Cicrano", person2CurrentData.getName());
		assertEquals("13/12/1995", person2CurrentData.getBirthDate());

		//Now lets update his info;

		PersonRequestData newPerson2  = new PersonRequestData("Cicrano Atualizado");

		restTemplate.put(baseUrl + "/{id}", newPerson2, 2);

		String updateResponse = restTemplate.getForObject(baseUrl + "/{id}", String.class,2);

		PersonResponseData person2UpdatedData = objectMapper.readValue(updateResponse, PersonResponseData.class);

		assertEquals("Cicrano Atualizado", person2UpdatedData.getName());
		assertEquals("13/12/1995", person2UpdatedData.getBirthDate());
	}
}
