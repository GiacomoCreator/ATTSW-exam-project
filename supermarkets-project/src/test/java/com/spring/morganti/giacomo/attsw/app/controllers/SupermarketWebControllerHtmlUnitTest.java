package com.spring.morganti.giacomo.attsw.app.controllers;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.spring.morganti.giacomo.attsw.app.model.Supermarket;
import com.spring.morganti.giacomo.attsw.app.services.SupermarketService;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = SupermarketWebController.class)
public class SupermarketWebControllerHtmlUnitTest {

	@Autowired
	private WebClient webClient;
	
	@MockBean
	private SupermarketService supermarketService;
	
	@Test
	public void test_homePageTitle() throws Exception {
		HtmlPage page = webClient.getPage("/");
		assertThat(page.getTitleText()).isEqualTo("Home Page");
	}
	
	@Test
	public void test_homePage_withoutSupermarkets() throws Exception {
		when(supermarketService.getAllSupermarkets())
			.thenReturn(Collections.emptyList());

		HtmlPage page = this.webClient.getPage("/");

		assertThat(page.getBody()
			.getTextContent()).contains("No supermarket");
	}

	@Test
	public void test_homePage_withSupermarkets() throws Exception {
		when(supermarketService.getAllSupermarkets())
			.thenReturn(
				asList(
					new Supermarket(1L, "supermarket1", "address1"),
					new Supermarket(2L, "supermarket2", "address2")));

		HtmlPage page = this.webClient.getPage("/");

		assertThat(page.getBody().getTextContent())
			.doesNotContain("No supermarket");
		
		HtmlTable table = page.getHtmlElementById("supermarkets_table");
		
		assertThat(removeWindowsCR(table.asText()))
			.isEqualTo(
				"Supermarkets\n" +
				"Name	Address\n" +
				"supermarket1	address1	Edit	Delete\n" +
				"supermarket2	address2	Edit	Delete"
			);
		page.getAnchorByHref("/edit/1");
		page.getAnchorByHref("/edit/2");
		page.getAnchorByHref("/delete/1");
		page.getAnchorByHref("/delete/2");
	}
	
	private String removeWindowsCR(String s) {
		return s.replace("\r", "");
	}

	@Test
	public void test_homePage_shouldProvideALinkForCreatingANewSupermarket() throws Exception {
		HtmlPage page = this.webClient.getPage("/");
		assertThat(
			page
				.getAnchorByText("Add new supermarket")
				.getHrefAttribute()
		).isEqualTo("/new");
	}
	
	@Test
	public void test_newPageTitle() throws Exception {
		HtmlPage page = webClient.getPage("/new");
		assertThat(page.getTitleText()).isEqualTo("Edit");
	}
	
	@Test
	public void test_editPageTitle() throws Exception {
		Supermarket supermarket = new Supermarket(1L, "supermarket", "address");

		when(supermarketService.getSupermarketById(1L)).thenReturn(supermarket);
		
		HtmlPage page = webClient.getPage("/edit/1");
		assertThat(page.getTitleText()).isEqualTo("Edit");
	}
	
	@Test
	public void test_editNonExistingSupermarket() throws Exception {
		when(supermarketService.getSupermarketById(1L))
			.thenReturn(null);

		HtmlPage page = this.webClient.getPage("/edit/1");

		assertThat(page.getBody().getTextContent())
			.contains("Error: supermarket with id 1 not found");
	}

	@Test
	public void test_editExistingSupermarket() throws Exception {
		when(supermarketService.getSupermarketById(1))
			.thenReturn(new Supermarket(1L, "oldSupermarket", "oldAddress"));

		HtmlPage page = this.webClient.getPage("/edit/1");

		final HtmlForm form = page.getFormByName("supermarket_form");

		form.getInputByValue("oldSupermarket").setValueAttribute("newSupermarket");
		form.getInputByValue("oldAddress").setValueAttribute("newAddress");
		form.getButtonByName("save_button").click();

		verify(supermarketService)
			.updateSupermarketById(1L, new Supermarket(1L, "newSupermarket", "newAddress"));
	}

	@Test
	public void test_editNewSupermarket() throws Exception {
		HtmlPage page = this.webClient.getPage("/new");

		final HtmlForm form = page.getFormByName("supermarket_form");

		form.getInputByName("name").setValueAttribute("supermarket");
		form.getInputByName("address").setValueAttribute("address");
		form.getButtonByName("save_button").click();

		verify(supermarketService)
			.insertNewSupermarket(new Supermarket(null, "supermarket", "address"));
	}
	
	@Test
	public void test_searchPageTitle() throws Exception {
		
		Supermarket supermarket = new Supermarket(1L, "supermarketName", "address");
		String supermarketName = "supermarketName";
		
		when(supermarketService.getSupermarketsByName(supermarketName)).thenReturn(asList(supermarket));
		
		HtmlPage homePage = webClient.getPage("/");
		
		final HtmlForm searchForm = homePage.getFormByName("supermarket_search_form");
		
		searchForm.getInputByName("name_to_search").setValueAttribute(supermarketName);
		
		HtmlPage searchPage = searchForm.getButtonByName("search_button").click();
		
		assertThat(searchPage.getTitleText()).isEqualTo("Search");
	}
	
	@Test
	public void test_searchPage_withoutSupermarkets() throws Exception {
		
		
		String supermarketName = "nonExsistingSupermarket";
		
		when(supermarketService.getSupermarketsByName(supermarketName))
			.thenReturn(Collections.emptyList());

		HtmlPage homePage = webClient.getPage("/");
		
		final HtmlForm searchForm = homePage.getFormByName("supermarket_search_form");
		
		searchForm.getInputByName("name_to_search").setValueAttribute(supermarketName);
		
		HtmlPage searchPage = searchForm.getButtonByName("search_button").click();

		assertThat(searchPage.getBody()
			.getTextContent()).contains("Error: supermarket with this name not found");
	}
	
	
	@Test
	public void test_searchPage_withSupermarkets() throws Exception {
		 
		Supermarket testSupermarket1 = new Supermarket(1L, "existingSupermarket", "address1");
		Supermarket testSupermarket2 = new Supermarket(2L, "existingSupermarket", "address2");
		List<Supermarket> supermarkets = asList(testSupermarket1, testSupermarket2);
		String supermarketName = "existingSupermarket";
			 
		when(supermarketService.getSupermarketsByName(supermarketName)).thenReturn(supermarkets);
		
		HtmlPage homePage = webClient.getPage("/");
		
		final HtmlForm searchForm = homePage.getFormByName("supermarket_search_form");
		
		searchForm.getInputByName("name_to_search").setValueAttribute(supermarketName);
		
		HtmlPage searchPage = searchForm.getButtonByName("search_button").click();
		
		assertThat(searchPage.getBody().getTextContent())
			.doesNotContain("Error: supermarket with name supermarket not found");
		HtmlTable table = searchPage.getHtmlElementById("supermarkets_name_table");
		assertThat(removeWindowsCR(table.asText()))
			.isEqualTo(
				"Supermarkets\n" +
				"Name	Address\n" +
				"existingSupermarket	address1	Edit	Delete\n" +
				"existingSupermarket	address2	Edit	Delete"
				);
		searchPage.getAnchorByHref("/edit/1");
		searchPage.getAnchorByHref("/edit/2");
		searchPage.getAnchorByHref("/delete/1");
		searchPage.getAnchorByHref("/delete/2");
	}
	
}
