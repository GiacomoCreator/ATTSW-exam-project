package com.spring.morganti.giacomo.attsw.app.e2e.steps;

import static org.assertj.core.api.Assertions.assertThat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;

import com.spring.morganti.giacomo.attsw.app.repositories.SupermarketRepository;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, properties = "spring.main.allow-bean-definition-overriding=true")
public class SupermarketsWebAppSteps {
	
	@Autowired
	SupermarketRepository supermarketRepository;
	
	private WebDriver driver;
	
	@LocalServerPort
	private int port;
	
	private static String baseUrl;
	
	@Before
	public void setup() {
		supermarketRepository.deleteAll();
		baseUrl = "http://localhost:" + port;
		driver = new ChromeDriver();
	}
	
	@After
	public void teardown() {
		driver.quit();
	}
	
	@Given("The user starts from the home page")
	public void the_user_starts_from_the_home_page() {
		
		driver.get(baseUrl);
		
		assertThat(driver.getTitle()).isEqualTo("Home Page");
	}
	
	@When("The user clicks on the {string} link")
	public void the_user_clicks_on_the_link(String linkName) {
		
		 driver.findElement(By.linkText(linkName)).click(); 
	}

	@When("Inserts a supermarket with name {string} and address {string}")
	public void inserts_a_supermarket_with_name_and_address(String supermarketName, String supermaketAddress) {
		
	    assertThat(driver.getTitle()).isEqualTo("Edit");
		
		driver.findElement(By.name("name")).clear();
		driver.findElement(By.name("name")).sendKeys(supermarketName);
		driver.findElement(By.name("address")).clear();
		driver.findElement(By.name("address")).sendKeys(supermaketAddress);	

	}
	
	@And("Clicks on the {string} button")
	public void cicks_on_the_button(String buttonName) {
		
		driver.findElement(By.name(buttonName)).click();
	}

	@Then("The {string} table contains a supermarket with name {string} and address {string}")
	public void the_table_contains_a_supermarket_with_name_and_address(
			String tableId, String supermarketName, String supermarketAddress) {
	    
		assertThat(driver.findElement(By.id(tableId)).getText()).
			contains(supermarketName, supermarketAddress);
	}
	
	@Then("The {string} table does not contain a supermarket with name {string} and address {string}")
	public void the_table_does_not_contain_a_supermarket_with_name_and_address(
			String tableId, String supermarketName, String supermarketAddress) {
	    
		assertThat(driver.findElement(By.id("supermarkets_table")).getText()).
			doesNotContain(supermarketName, supermarketAddress);
	}

	@When("Updates the address to {string}")
	public void updates_the_address_to(String UpdatedSupermarketAddress) {
		
	    assertThat(driver.getTitle()).isEqualTo("Edit");
	    
		driver.findElement(By.name("address")).clear();
		driver.findElement(By.name("address")).sendKeys(UpdatedSupermarketAddress);
		
		driver.findElement(By.name("save_button")).click();
		
		assertThat(driver.getTitle()).isEqualTo("Home Page");
		
	}

	@Given("The dabatase contains a supermarket with name {string} and address {string}")
	public void the_dabatase_contains_a_supermarket_with_name_and_address(
			String supermarketName, String supermarketAddress) {
		
		driver.findElement(By.linkText("Add new supermarket")).click(); 
		
		driver.findElement(By.name("name")).clear();
		driver.findElement(By.name("name")).sendKeys(supermarketName);
		driver.findElement(By.name("address")).clear();
		driver.findElement(By.name("address")).sendKeys(supermarketAddress);	
		
		driver.findElement(By.name("save_button")).click();
		
	    assertThat(driver.getTitle()).isEqualTo("Home Page");
	    
		assertThat(driver.findElement(By.id("supermarkets_table")).getText()).
			contains(supermarketName, supermarketAddress);
		
	}

	@Then("The message {string} is displayed")
	public void the_message_is_displayed(String message) {
		
		assertThat(driver.getPageSource()).contains(message);
	}

	@When("The user enters {string} in the search bar")
	public void the_user_enters_in_the_search_bar(String supermarketNameToSearch){
		
		driver.findElement(By.name("supermarket_search_form"));
		
		driver.findElement(By.name("name_to_search")).clear();
	    driver.findElement(By.name("name_to_search")).sendKeys(supermarketNameToSearch);
	  
	}
	
	

}