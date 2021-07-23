package com.spring.morganti.giacomo.attsw.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.morganti.giacomo.attsw.app.model.Supermarket;
import com.spring.morganti.giacomo.attsw.app.services.SupermarketService;

@Controller
public class SupermarketWebController {
	
	private static final String MESSAGE_ATTRIBUTE = "message";
	private static final String SUPERMARKETS_ATTRIBUTE = "supermarkets";
	private static final String SUPERMARKET_ATTRIBUTE = "supermarket";
	
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
	public String editSupermarket(@PathVariable long id, Model model) {
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
	public String saveSupermarket(Supermarket supermarket) {
		final Long id = supermarket.getId();
		if (id == null) {
			supermarketService.insertNewSupermarket(supermarket);
		} else {
			supermarketService.updateSupermarketById(id, supermarket);
		}
		return "redirect:/";
	}
	
	@GetMapping("/search")
	public String searchSupermarket (@RequestParam("name_to_search") String supermarketName, Model model) {

		List<Supermarket> supermarkets = supermarketService.getSupermarketsByName(supermarketName);
		model.addAttribute(SUPERMARKETS_ATTRIBUTE, supermarkets);
		model.addAttribute(MESSAGE_ATTRIBUTE,  
				supermarkets.isEmpty() ? "Error: supermarket with name " + supermarketName + " not found"  : "");
		
		return "search";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteSupermarket(@PathVariable long id) {
		Supermarket supermarketToDelete = supermarketService.getSupermarketById(id);
		supermarketService.delete(supermarketToDelete);
		
		return "redirect:/";
	}

	@GetMapping("/drop")
	public String deleteAll() {
		supermarketService.deleteAllSupermarkets();
	
		return "redirect:/";
		
	}
}
