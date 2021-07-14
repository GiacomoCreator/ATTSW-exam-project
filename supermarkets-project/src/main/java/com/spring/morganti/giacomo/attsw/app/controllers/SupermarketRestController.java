package com.spring.morganti.giacomo.attsw.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.morganti.giacomo.attsw.app.model.Supermarket;
import com.spring.morganti.giacomo.attsw.app.services.SupermarketService;

@RestController
@RequestMapping("/api/supermarkets")
public class SupermarketRestController {

	@Autowired
	private SupermarketService supermarketService;
	
	@GetMapping
	public List<Supermarket> allSupermarkets() {
	return supermarketService.getAllSupermarkets();
	}
	
	@GetMapping("/{id}")
	public Supermarket oneSupermarket(@PathVariable long id) {
	return supermarketService.getSupermarketById(id);
	}
	
	@PostMapping("/new")
	public Supermarket newSupermarket(@RequestBody Supermarket supermarket) {
		return supermarketService.insertNewSupermarket(supermarket);
	}
	
	@PutMapping("/update/{id}")
	public Supermarket updateSupermarket(@PathVariable long id, @RequestBody Supermarket updatedSupermarket) {
		return supermarketService.updateSupermarketById(id, updatedSupermarket);
	}
	
	@DeleteMapping("/delete/{id}")
	public void deleteSupermarketById(@PathVariable long id) {
		Supermarket supermarketToDelete = supermarketService.getSupermarketById(id);
		supermarketService.delete(supermarketToDelete);
	}
	
	@DeleteMapping("/drop")
	public void deleteAllSupermarkets() {
		supermarketService.deleteAllSupermarkets();
	}

}
