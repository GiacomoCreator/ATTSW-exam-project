package com.spring.morganti.giacomo.attsw.app.controllers;

import java.math.BigInteger;
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
import com.spring.morganti.giacomo.attsw.app.model.SupermarketDTO;
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
	public Supermarket oneSupermarket(@PathVariable BigInteger  id) {
	return supermarketService.getSupermarketById(id);
	}
	
	@GetMapping("/name/{supermarketName}")
	public List<Supermarket> getSupermarketsByName(@PathVariable String supermarketName) {
	return supermarketService.getSupermarketsByName(supermarketName);
	}
	
	@PostMapping("/new")
	public Supermarket newSupermarket(@RequestBody SupermarketDTO supermarketDTO) {
		Supermarket supermarket = new Supermarket(
				supermarketDTO.getId(), supermarketDTO.getName(), supermarketDTO.getAddress());
		return supermarketService.insertNewSupermarket(supermarket);
	}
	
	@PutMapping("/update/{id}")
	public Supermarket updateSupermarket(@PathVariable BigInteger  id, @RequestBody SupermarketDTO updatedSupermarketDTO) {
		Supermarket updatedSupermarket = new Supermarket(
				updatedSupermarketDTO.getId(), updatedSupermarketDTO.getName(), updatedSupermarketDTO.getAddress());
		return supermarketService.updateSupermarketById(id, updatedSupermarket);
	}
	
	@DeleteMapping("/delete/{id}")
	public void deleteSupermarketById(@PathVariable BigInteger  id) {
		Supermarket supermarketToDelete = supermarketService.getSupermarketById(id);
		supermarketService.delete(supermarketToDelete);
	}
	
	@DeleteMapping("/drop")
	public void deleteAllSupermarkets() {
		supermarketService.deleteAll();
	}

}
