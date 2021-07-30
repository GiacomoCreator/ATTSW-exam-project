package com.spring.morganti.giacomo.attsw.app;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.emptyOrNullString;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import com.spring.morganti.giacomo.attsw.app.model.Supermarket;
import com.spring.morganti.giacomo.attsw.app.repositories.SupermarketRepository;

import io.restassured.response.Response;
import io.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SupermarketRestControllerIT {

	@Autowired
	private SupermarketRepository supermarketRepository;
	
	@LocalServerPort
	private int port;
	
	@Before
	public void setUp() {
		RestAssured.port = port;
		supermarketRepository.deleteAll();		
	}
	
	@After
	public void cleanUp() {
		supermarketRepository.deleteAll();
	}

	@Test
	public void test_getSupermarketById()  throws Exception {
		
		Supermarket supermarketToGet = supermarketRepository.save(new Supermarket(null, "supermarket", "address"));
		
		Response response = 
						given().
						when().
							get("/api/supermarkets/"+supermarketToGet.getId());

			Supermarket result = response.getBody().as(Supermarket.class);

			assertThat(supermarketRepository.findById(result.getId()))
				.contains(supermarketToGet);
		}
	
	@Test
	public void test_getSupermarketsByName()  throws Exception {
		
		supermarketRepository.save(new Supermarket(null, "supermarketName", "address1"));
		supermarketRepository.save(new Supermarket(null, "supermarketName", "address2"));
		
		given().
		when().
			get("/api/supermarkets/name/supermarketName").
		then().
			statusCode(200).
			assertThat().
			body(
				 "name[0]", equalTo("supermarketName"),
				 "address[0]", equalTo("address1"),
				 "name[1]", equalTo("supermarketName"),
				 "address[1]", equalTo("address2")
					);
		}
	
	@Test
	public void test_getAllSupermarkets_withEmptyRepository()  throws Exception {
		
		given().
		when().
			get("/api/supermarkets").
		then().
			statusCode(200).
			assertThat().
				body(is("[]"));
		
	}

	@Test
	public void test_getAllSupermarkets_WithNotEmptyRepository() throws Exception {
		
		supermarketRepository.save(new Supermarket(null, "supermarket1", "address1"));
		supermarketRepository.save(new Supermarket(null, "supermarket2", "address2"));
		
		given().
		when().
			get("/api/supermarkets").
		then().
			statusCode(200).
				assertThat().
				body(
					 "name[0]", equalTo("supermarket1"),
					 "address[0]", equalTo("address1"),
					 "name[1]", equalTo("supermarket2"),
					 "address[1]", equalTo("address2")
				);
		
	}
	
	@Test
	public void test_newSupermarket() throws Exception {

		Response response = 
					given().
						contentType(MediaType.APPLICATION_JSON_VALUE).
						body(
								new Supermarket(null, "newSupermarket", "newAddress")).
					when().
						post("/api/supermarkets/new");

		Supermarket savedSupermarket = response.getBody().as(Supermarket.class);

		assertThat(supermarketRepository.findById(savedSupermarket.getId()))
			.contains(savedSupermarket);
	}

	@Test
	public void test_updateSupermarket() throws Exception {

		Supermarket savedSupermarket = supermarketRepository
			.save(new Supermarket(null, "originalSupermarket", "originalAddress"));

		given().
			contentType(MediaType.APPLICATION_JSON_VALUE).
			body(
					new Supermarket(null, "modifiedSupermarket", "modifiedAddress")).
		when().
			put("/api/supermarkets/update/" + savedSupermarket.getId()).
		then().
			statusCode(200).
			body(
				"id", equalTo(savedSupermarket.getId()),
				"name", equalTo("modifiedSupermarket"),
				"address", equalTo("modifiedAddress")
			);
	}
	
	@Test
	public void test_deleteSupermarket() throws Exception {
		
		Supermarket supermarketToDelete = 
				supermarketRepository.save(new Supermarket(null, "supermarket", "address"));
		
		given().
		when().
			delete("/api/supermarkets/delete/" + supermarketToDelete.getId()).
		then().
			statusCode(200).
		    contentType(emptyOrNullString());
		
		assertThat(
				supermarketRepository.findById(supermarketToDelete.getId())).isNotPresent();
	}
	
	@Test
	public void test_deleteAllSupermarkets() throws Exception {
		
		supermarketRepository.save(new Supermarket(null, "supermarket1", "address1"));
		supermarketRepository.save(new Supermarket(null, "supermarket2", "address2"));
		
		given().
		when().
			delete("/api/supermarkets/drop").
		then().
			statusCode(200).
		    contentType(emptyOrNullString());
		
		assertThat(supermarketRepository.findAll()).isEmpty();	
		
	}

}
