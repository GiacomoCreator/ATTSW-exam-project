package com.spring.morganti.giacomo.attsw.app.controllers;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.morganti.giacomo.attsw.app.model.Supermarket;
import com.spring.morganti.giacomo.attsw.app.model.SupermarketDTO;
import com.spring.morganti.giacomo.attsw.app.services.SupermarketService;

@Controller
public class SupermarketWebController {
	
	private static final String MESSAGE_ATTRIBUTE = "message";
	private static final String SUPERMARKETS_ATTRIBUTE = "supermarkets";
	private static final String SUPERMARKET_ATTRIBUTE = "supermarket";
	private static final String REDIRECT_HOME = "redirect:/";
	
	@Autowired
	private SupermarketService supermarketService;

	@GetMapping("/")
	public String index(Model model) {
		List<Supermarket> allSupermarkets = supermarketService.getAllSupermarkets();
		model.addAttribute(SUPERMARKETS_ATTRIBUTE, allSupermarkets);
		model.addAttribute(MESSAGE_ATTRIBUTE, allSupermarkets.isEmpty() ? "No supermarket is present" :  "");
		return "index";
	}
		
	@GetMapping("/edit/{id}")
	public String editSupermarket(@PathVariable BigInteger id, Model model) {
		Supermarket supermarketById = supermarketService.getSupermarketById(id);
		model.addAttribute(SUPERMARKET_ATTRIBUTE, supermarketById);
		model.addAttribute(MESSAGE_ATTRIBUTE, 
				supermarketById == null ? "Error: supermarket with id " + id + " not found"  : "");
		return "edit";
	}
	
	@GetMapping("/new")
	public String newSupermarket(Model model) {
		model.addAttribute(SUPERMARKET_ATTRIBUTE, new Supermarket());
		model.addAttribute(MESSAGE_ATTRIBUTE, "");
		
		return "edit";
	}
	
	@PostMapping("/save")
	public String saveSupermarket(SupermarketDTO supermarketDTO) {
		
		Supermarket supermarket = new Supermarket(
				supermarketDTO.getId(), supermarketDTO.getName(), supermarketDTO.getAddress());
		
		final BigInteger id = supermarket.getId();
		if (id == null) {
			supermarketService.insertNewSupermarket(supermarket);
		} else {
			supermarketService.updateSupermarketById(id, supermarket);
		}
		return REDIRECT_HOME;
	}
	
	@GetMapping("/search")
	public String searchSupermarket (@RequestParam("name_to_search") String supermarketName, Model model) {

		List<Supermarket> supermarkets = supermarketService.getSupermarketsByName(supermarketName);
		model.addAttribute(SUPERMARKETS_ATTRIBUTE, supermarkets);
		model.addAttribute(MESSAGE_ATTRIBUTE,  
				supermarkets.isEmpty() ? "Error: supermarket with this name not found"  : "");
		
		return "search";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteSupermarket(@PathVariable BigInteger id) {
		Supermarket supermarketToDelete = supermarketService.getSupermarketById(id);
		supermarketService.delete(supermarketToDelete);
		
		return REDIRECT_HOME;
	}

	@GetMapping("/drop")
	public String deleteAll() {
		supermarketService.deleteAll();
	
		return REDIRECT_HOME;
		
	}
}
