package com.spring.morganti.giacomo.attsw.app;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.spring.morganti.giacomo.attsw.app.model.Supermarket;
import com.spring.morganti.giacomo.attsw.app.repositories.SupermarketRepository;

import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SupermarketWebControllerIT {

	@Autowired
	private SupermarketRepository supermarketRepository;
	
    @Autowired
    private WebApplicationContext webAppContext;

	private MockMvc mvc;
	
	
	@Before
	public void setUp() {
		mvc = MockMvcBuilders.
					webAppContextSetup(webAppContext).build();
		supermarketRepository.deleteAll();
	}
	
	@After
	public void cleanUp() {
		supermarketRepository.deleteAll();
	}

	@Test
	public void test_test_homePage_withSupermarkets() throws Exception {
		
		List<Supermarket> supermarkets = asList(
				supermarketRepository.save(new Supermarket(null, "supermarket", "address")));
		
		mvc.perform(get("/"))
		.andExpect(view().name("index"))
		.andExpect(model().attribute("supermarkets",
			supermarkets))
		.andExpect(model().attribute("message",
				""));
	}
	
	@Test
	public void test_homePage_withoutSupermarkets() throws Exception {

		mvc.perform(get("/"))
			.andExpect(view().name("index"))
			.andExpect(model().attribute("supermarkets",
				Collections.emptyList()))
			.andExpect(model().attribute("message",
				"No supermarket is present"));
	}
		
	@Test
	public void test_editPage_getSupermarketById_isFound() throws Exception {

		Supermarket savedSupermarket = 
				supermarketRepository.save(new Supermarket(BigInteger.valueOf(1), "supermarket", "address"));

		mvc.perform(get("/edit/1"))
			.andExpect(view().name("edit"))
			.andExpect(model().attribute("supermarket", savedSupermarket))
			.andExpect(model().attribute("message", ""));
	
	}
	
	@Test
	public void test_editPage_getSupermarketById_isNotFound() throws Exception {

		mvc.perform(get("/edit/1"))
			.andExpect(view().name("edit"))
			.andExpect(model().attribute("supermarket", nullValue()))
			.andExpect(model().attribute("message", "Error: supermarket with id 1 not found"));
	}

	@Test
	public void test_editPage_newSupermarket() throws Exception {
		
		mvc.perform(get("/new"))
			.andExpect(view().name("edit"))
			.andExpect(model().attribute("supermarket", new Supermarket()))
			.andExpect(model().attribute("message", ""));
	
	}

	@Test
	public void test_postSupermarket_withoutId() throws Exception {
		
		mvc.perform(post("/save")
				.param("name", "supermarket")
				.param("address", "address"))
			.andExpect(view().name("redirect:/"));
		
		assertThat(supermarketRepository.count()).isEqualTo(1);
	}
	
	@Test
	public void test_postSupermarket_withId_ShouldUpdateExistingSupermarket() throws Exception {
		
		supermarketRepository.save(new Supermarket(BigInteger.valueOf(1), "savedSupermarket", "savedAddress"));
		
		mvc.perform(post("/save")
				.param("id", "1")
				.param("name", "updatedSupermarket")
				.param("address", "updatedAddress"))
			.andExpect(view().name("redirect:/"));
		
		Optional<Supermarket> updatedSupermarket = supermarketRepository.findById(BigInteger.valueOf(1));
		
		assertThat(updatedSupermarket.get().getName()).contains("updatedSupermarket");
		assertThat(updatedSupermarket.get().getAddress()).contains("updatedAddress");	
	}
	
	@Test
	public void test_searchPage_searchSupermarketsByName_found() throws Exception {
	 
		List<Supermarket> supermarkets = asList(
				supermarketRepository.save(new Supermarket(null, "supermarketToSearch", "address1")),
				supermarketRepository.save(new Supermarket(null, "supermarketToSearch", "address2"))
				);
		String nameToSearch = "supermarketToSearch";
		 
		mvc.perform(get("/search")
				.param("name_to_search", nameToSearch))
		 		.andExpect(model().attribute("supermarkets", supermarkets))
		 		.andExpect(model().attribute("message", ""))
		 		.andExpect(view().name("search"));
	 }
	
	@Test
	public void test_searchPage_searchSupermarketsByName_notFound() throws Exception {
	 
		supermarketRepository.save(new Supermarket(null, "savedSupermarket", "savedAddress"));		
		String nameToSearch = "supermarket";
		 
		mvc.perform(get("/search")
				.param("name_to_search", nameToSearch))
		 		.andExpect(model().attribute("supermarkets", Collections.emptyList()))
		 		.andExpect(model().attribute("message", "Error: supermarket with this name not found"))
		 		.andExpect(view().name("search"));
	 }
		
	@Test
	public void test_deleteSupermarketById() throws Exception {
		
		 supermarketRepository.save(new Supermarket(BigInteger.valueOf(1), "supermarket", "address"));
		 
		 mvc.perform(get("/delete/1")
				.param("id", "1"))
		 		.andExpect(view().name("redirect:/"));
		 
		 assertFalse(supermarketRepository.findById(BigInteger.valueOf(1)).isPresent());

	}

	@Test
	public void test_deleteAllSupermarkets() throws Exception {
		
		supermarketRepository.save(new Supermarket(null, "supermarketToSearch", "address1"));
		supermarketRepository.save(new Supermarket(null, "supermarketToSearch", "address2"));
		
		mvc.perform(get("/drop"))
			.andExpect(view().name("redirect:/"));
		
		assertThat(supermarketRepository.findAll()).isEmpty();
	}
}
