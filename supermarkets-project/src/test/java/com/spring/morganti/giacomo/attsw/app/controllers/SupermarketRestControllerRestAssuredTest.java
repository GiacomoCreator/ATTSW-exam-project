package com.spring.morganti.giacomo.attsw.app.controllers;

import static java.util.Arrays.asList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.http.MediaType;

import com.spring.morganti.giacomo.attsw.app.model.Supermarket;
import com.spring.morganti.giacomo.attsw.app.services.SupermarketService;

import static org.hamcrest.Matchers.*;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

@RunWith(MockitoJUnitRunner.class)
public class SupermarketRestControllerRestAssuredTest {
	
	@Mock
	private SupermarketService supermarketService;

	@InjectMocks
	private SupermarketRestController supermarketRestController;

	@Before
	public void setup() {
		RestAssuredMockMvc
			.standaloneSetup(supermarketRestController);	
	}

	@Test
	public void test_findById_withExistingSupermarket() throws Exception {
		when(supermarketService.getSupermarketById(1L)).
			thenReturn(new Supermarket(1L, "supermarket", "address"));
		
		given().
		when().
			get("/api/supermarkets/1").
		then().
			statusCode(200).
			assertThat().
			body(
				"id", equalTo(1),
				"name", equalTo("supermarket"),
				"address", equalTo("address")
			);
		
		verify(supermarketService).getSupermarketById(1L);
		verifyNoMoreInteractions(supermarketService);
	}

	@Test
	public void test_findById_withNonExistingSupermarket() throws Exception {
		when(supermarketService.getSupermarketById(1L)).
		thenReturn(null);
		
		given().
		when().
		    get("/api/supermarkets/1").
		then().statusCode(200).
		    contentType(emptyOrNullString());
		
		verify(supermarketService).getSupermarketById(1L);
		verifyNoMoreInteractions(supermarketService);
	}
	
	@Test
	public void test_getAllSupermarkets() {
		Supermarket testSupermarket1 = new Supermarket(1L, "supermarket1", "address1");
		Supermarket testSupermarket2 = new Supermarket(2L, "supermarket2", "address2");
		when(supermarketService.getAllSupermarkets()).thenReturn(asList(testSupermarket1, testSupermarket2));
		
		given().
		when().
			get("/api/supermarkets").
		then().
			statusCode(200).
			assertThat().
				body("id[0]", equalTo(1),
					 "name[0]", equalTo("supermarket1"),
					 "address[0]", equalTo("address1"),
					 "id[1]", equalTo(2),
					 "name[1]", equalTo("supermarket2"),
					 "address[1]", equalTo("address2")
				);
		
		verify(supermarketService).getAllSupermarkets();
		verifyNoMoreInteractions(supermarketService);
	}

	@Test
	public void test_postSupermarket() throws Exception {
		Supermarket newSupermarket = new Supermarket(null, "supermarket", "");
		when(supermarketService.insertNewSupermarket(newSupermarket)).
			thenReturn(new Supermarket(1L, "supermarket", ""));
      		
		given().
			contentType(MediaType.APPLICATION_JSON_VALUE).
			body(newSupermarket).
		when().
			post("/api/supermarkets/new").
		then().
			statusCode(200).
			body(
					"id", equalTo(1),
					"name", equalTo("supermarket"),
					"address", equalTo("")
				);
		
		verify(supermarketService).insertNewSupermarket(newSupermarket);
		verifyNoMoreInteractions(supermarketService);
	}
	
	@Test
	public void test_updateSupermarket() throws Exception {
		Supermarket updatedSupermarket = new Supermarket(null, "supermarket", "");
		when(supermarketService.updateSupermarketById(1L, updatedSupermarket)).
			thenReturn(new Supermarket(1L, "supermarket", ""));

		given().
			contentType(MediaType.APPLICATION_JSON_VALUE).
			body(updatedSupermarket).
		when().
			put("/api/supermarkets/update/1").
		then().
			statusCode(200).
			body(
				"id", equalTo(1),
				"name", equalTo("supermarket"),
				"address", equalTo("")
			);
		
		verify(supermarketService).updateSupermarketById(1L, updatedSupermarket);
		verifyNoMoreInteractions(supermarketService);
	}
	
	@Test
	public void test_deleteSupermarketById() throws Exception {
		Supermarket supermarketToDelete = new Supermarket(1L, "supermarket", "address");
		when(supermarketService.getSupermarketById(1L)).
		    thenReturn(supermarketToDelete);
		
		given().
	    when().
		    delete("/api/supermarkets/delete/1").
	    then().
		    statusCode(200);
		
		verify(supermarketService).getSupermarketById(1L);
		verify(supermarketService).delete(supermarketToDelete);
		verifyNoMoreInteractions(supermarketService);
	}
	
	@Test
	public void test_deleteAllSupermarkets() throws Exception {
		
		given().
		when().
			delete("api/supermarkets/drop").
		then().
			statusCode(200);
		
		verify(supermarketService).deleteAllSupermarkets();
		verifyNoMoreInteractions(supermarketService);
	}

}
