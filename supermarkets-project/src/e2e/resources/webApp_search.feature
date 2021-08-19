Feature: Supermarket Web Application
  			 Specifications of Search operations in the Supermarket Web Application

	Scenario: Search a supermarket by name
		Given The user starts from the home page
		And The dabatase contains a supermarket with name "SupermarketName1" and address "SupermarketAddress1"
		And The dabatase contains a supermarket with name "SupermarketName2" and address "SupermarketAddress2"
		When The user enters "SupermarketName1" in the search bar 
		And Clicks on the "search_button" button
		Then The "supermarkets_name_table" table contains a supermarket with name "SupermarketName1" and address "SupermarketAddress1"
	
	Scenario: Search a supermarket by name and update it
		Given The user starts from the home page
		And The dabatase contains a supermarket with name "SupermarketName1" and address "SupermarketAddress1"
		And The dabatase contains a supermarket with name "SupermarketName2" and address "SupermarketAddress2"
		When The user enters "SupermarketName1" in the search bar 
		And Clicks on the "search_button" button
		Then The "supermarkets_name_table" table contains a supermarket with name "SupermarketName1" and address "SupermarketAddress1"
		When The user clicks on the "Edit" link
		And Updates the address to "UpdatedSupermarketAddress1"
		Then The "supermarkets_table" table contains a supermarket with name "SupermarketName1" and address "UpdatedSupermarketAddress1"
	
	Scenario: Search a supermarket by name and delete it
		Given The user starts from the home page
		And The dabatase contains a supermarket with name "SupermarketName1" and address "SupermarketAddress1"
		And The dabatase contains a supermarket with name "SupermarketName2" and address "SupermarketAddress2"
		When The user enters "SupermarketName1" in the search bar 
		And Clicks on the "search_button" button
		Then The "supermarkets_name_table" table contains a supermarket with name "SupermarketName1" and address "SupermarketAddress1"
		When The user clicks on the "Delete" link
		Then The "supermarkets_table" table does not contain a supermarket with name "SupermarketName1" and address "SupermarketAddress1"
	
	Scenario: Search a supermarket by name not found
		Given The user starts from the home page
		And The dabatase contains a supermarket with name "SupermarketName1" and address "SupermarketAddress1"
		And The dabatase contains a supermarket with name "SupermarketName2" and address "SupermarketAddress2"
		When The user enters "SupermarketName3" in the search bar 
		And Clicks on the "search_button" button
	  Then The message "Error: supermarket with this name not found" is displayed