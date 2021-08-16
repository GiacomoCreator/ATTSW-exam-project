Feature: Supermarket Web Application
2 			 Specifications of Editing operations in the Supermarket Web Application

	Scenario: Add a new supermarket
		Given The user starts from the home page
		When The user clicks on the "Add new supermarket" link
		And Inserts a supermarket with name "SupermarketName" and address "SupermarketAddress" 
		And Clicks on the "save_button" button
		Then The "supermarkets_table" table contains a supermarket with name "SupermarketName" and address "SupermarketAddress"

	Scenario: Update the address of a supermarket
		Given The user starts from the home page
		And The dabatase contains a supermarket with name "SupermarketName" and address "SupermarketAddress"
		When The user clicks on the "Edit" link
		And Updates the address to "UpdatedSupermarketAddress"
		Then The "supermarkets_table" table contains a supermarket with name "SupermarketName" and address "UpdatedSupermarketAddress"

	Scenario: Delete a supermarket
		Given The user starts from the home page
		And The dabatase contains a supermarket with name "SupermarketName" and address "SupermarketAddress"
		When The user clicks on the "Delete" link
		Then The message "No supermarket is present" is displayed
	
  Scenario: Reset the database
		Given The user starts from the home page
		And The dabatase contains a supermarket with name "SupermarketName1" and address "SupermarketAddress1"
		And The dabatase contains a supermarket with name "SupermarketName2" and address "SupermarketAddress2" 
		When The user clicks on the "Reset" link
		Then The message "No supermarket is present" is displayed
		
	