package com.spring.morganti.giacomo.attsw.app.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.spring.morganti.giacomo.attsw.app.model.Supermarket;
import com.spring.morganti.giacomo.attsw.app.repositories.SupermarketRepository;

@Service
public class SupermarketService {

	private SupermarketRepository supermarketRepository;
	
	public SupermarketService(SupermarketRepository supermarketRepository) {
		this.supermarketRepository = supermarketRepository;
	}

	public List<Supermarket> getAllSupermarkets() {
	
		return supermarketRepository.findAll();
	}

	public Supermarket getSupermarketById(long id) {
		return supermarketRepository.findById(id)
		.orElse(null);
	}

	public Supermarket insertNewSupermarket(Supermarket newSupermarket) {
		newSupermarket.setId(null);
		return supermarketRepository.save(newSupermarket);
	}

	public Supermarket updateSupermarketById(long id, Supermarket replacementSupermarket) {
		replacementSupermarket.setId(id);
		return supermarketRepository.save(replacementSupermarket);
	}

	public void delete(Supermarket supermarketToDelete) {
		supermarketRepository.delete(supermarketToDelete);
	}

	public void deleteAll() {
		supermarketRepository.deleteAll();		
	}

}
