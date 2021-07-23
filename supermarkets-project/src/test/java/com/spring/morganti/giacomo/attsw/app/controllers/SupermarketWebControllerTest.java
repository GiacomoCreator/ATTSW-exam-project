package com.spring.morganti.giacomo.attsw.app.controllers;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;

import com.spring.morganti.giacomo.attsw.app.model.Supermarket;
import com.spring.morganti.giacomo.attsw.app.services.SupermarketService;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = SupermarketWebController.class)
public class SupermarketWebControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private SupermarketService supermarketService;
	
	@Test
	public void test_statusCode200() throws Exception {
		mvc.perform(get("/"))
			.andExpect(status().is2xxSuccessful());
	}
	
	@Test
	public void test_backToHomeView() throws Exception {
		ModelAndViewAssert.assertViewName(mvc.perform(get("/"))
			.andReturn()
			.getModelAndView(), "index");
	}

	@Test
	public void test_homeView_withSupermarkets() throws Exception {
		List<Supermarket> supermarkets = asList(new Supermarket(1L, "supermarket", "address"));

		when(supermarketService.getAllSupermarkets())
			.thenReturn(supermarkets);
		
		mvc.perform(get("/"))
		.andExpect(view().name("index"))
		.andExpect(model().attribute("supermarkets",
			supermarkets))
		.andExpect(model().attribute("message",
				""));
		
		verify(supermarketService).getAllSupermarkets();
		verifyNoMoreInteractions(supermarketService);
	}
	
	@Test
	public void test_homeView_withNoSupermarkets() throws Exception {
		when(supermarketService.getAllSupermarkets())
			.thenReturn(Collections.emptyList());

		mvc.perform(get("/"))
			.andExpect(view().name("index"))
			.andExpect(model().attribute("supermarkets",
				Collections.emptyList()))
			.andExpect(model().attribute("message",
				"No supermarket is present"));
		
		 verify(supermarketService).getAllSupermarkets();
		 verifyNoMoreInteractions(supermarketService);
	}
	
	@Test
	public void test_getSupermarketById_isFound() throws Exception {
		Supermarket supermarket = new Supermarket(1L, "supermarket", "address");

		when(supermarketService.getSupermarketById(1L)).thenReturn(supermarket);

		mvc.perform(get("/edit/1"))
			.andExpect(view().name("edit"))
			.andExpect(model().attribute("supermarket", supermarket))
			.andExpect(model().attribute("message", ""));
		
		 verify(supermarketService).getSupermarketById(1L);
		 verifyNoMoreInteractions(supermarketService);
	}

	@Test
	public void test_getSupermarketById_isNotFound() throws Exception {
		when(supermarketService.getSupermarketById(1L)).thenReturn(null);

		mvc.perform(get("/edit/1"))
			.andExpect(view().name("edit"))
			.andExpect(model().attribute("supermarket", nullValue()))
			.andExpect(model().attribute("message", "Error: supermarket with id 1 not found"));
		
		 verify(supermarketService).getSupermarketById(1L);
		 verifyNoMoreInteractions(supermarketService);
	}

	@Test
	public void test_newSupermarket() throws Exception {
		mvc.perform(get("/new"))
			.andExpect(view().name("edit"))
			.andExpect(model().attribute("supermarket", new Supermarket()))
			.andExpect(model().attribute("message", ""));
		
		verifyNoInteractions(supermarketService);
	}
	
	@Test
	public void test_postSupermarket_withoutId() throws Exception {
		mvc.perform(post("/save")
				.param("name", "supermarket")
				.param("address", "address"))
			.andExpect(view().name("redirect:/"));
		
		verify(supermarketService)
			.insertNewSupermarket(new Supermarket(null, "supermarket", "address"));
		verifyNoMoreInteractions(supermarketService);
	}
	
	@Test
	public void test_postSupermarket_withId_ShouldUpdateExistingSupermarket() throws Exception {
		mvc.perform(post("/save")
				.param("id", "1")
				.param("name", "supermarket")
				.param("address", "address"))
			.andExpect(view().name("redirect:/"));
		
		verify(supermarketService)
			.updateSupermarketById(1L, new Supermarket(1L, "supermarket", "address"));
		verifyNoMoreInteractions(supermarketService);
	}
	
	@Test
	public void test_searchSupermarketsByName() throws Exception {
	 
		Supermarket testSupermarket1 = new Supermarket(null, "supermarket", "address1");
		Supermarket testSupermarket2 = new Supermarket(null, "supermarket", "address2");
		List<Supermarket> supermarkets = asList(testSupermarket1, testSupermarket2);
		String supermarketName = "supermarket";
		 
		when(supermarketService.getSupermarketsByName(supermarketName)).thenReturn(supermarkets);
		 
		mvc.perform(get("/search")
				.param("name_to_search", supermarketName))
		 		.andExpect(model().attribute("supermarkets", supermarkets))
		 		.andExpect(model().attribute("message", ""))
		 		.andExpect(view().name("search"));
		 
		 verify(supermarketService).getSupermarketsByName(supermarketName);
		 verifyNoMoreInteractions(supermarketService);
	 }
	
	@Test
	public void test_searchSupermarketsByName_notFound() throws Exception {
	 
		String supermarketName = "supermarket";
		 
		when(supermarketService.getSupermarketsByName(supermarketName))
				.thenReturn(Collections.emptyList());
		 
		mvc.perform(get("/search")
				.param("name_to_search", supermarketName))
		 		.andExpect(model().attribute("supermarkets", Collections.emptyList()))
		 		.andExpect(model().attribute("message", "Error: supermarket with this name not found"))
		 		.andExpect(view().name("search"));
		 
		 verify(supermarketService).getSupermarketsByName(supermarketName);
		 verifyNoMoreInteractions(supermarketService);
	 }
	
	@Test
	public void test_deleteSupermarketById() throws Exception {
		
		 Supermarket supermarketToDelete  = new Supermarket(1L, "supermarket", "address");
		 
		 when(supermarketService.getSupermarketById(1L)).thenReturn(supermarketToDelete);
		 
		 mvc.perform(get("/delete/1")
				.param("id", "1"))
		 		.andExpect(view().name("redirect:/"));
		 
		 verify(supermarketService).getSupermarketById(1L);
		 verify(supermarketService).delete(supermarketToDelete);
	}


	@Test
	public void test_deleteAllSupermarkets() throws Exception {
		mvc.perform(get("/drop"))
			.andExpect(view().name("redirect:/"));
		
		verify(supermarketService).deleteAllSupermarkets();
		verifyNoMoreInteractions(supermarketService);
	}
	
	
}