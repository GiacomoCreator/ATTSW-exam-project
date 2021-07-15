package com.spring.morganti.giacomo.attsw.app.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.spring.morganti.giacomo.attsw.app.model.Supermarket;

@Service
public class SupermarketService {

	private static final String TEMPORARY_IMPLEMENTATION = "Temporary implementation";
	
	public Supermarket getSupermarketById(long l) {
		throw new UnsupportedOperationException(TEMPORARY_IMPLEMENTATION);
	}

	public List<Supermarket> getAllSupermarkets() {
		throw new UnsupportedOperationException(TEMPORARY_IMPLEMENTATION);
	}

	public Supermarket insertNewSupermarket(Supermarket newSupermarket) {
		throw new UnsupportedOperationException(TEMPORARY_IMPLEMENTATION);
	}

	public Supermarket updateSupermarketById(long id, Supermarket updatedSupermarket) {
		throw new UnsupportedOperationException(TEMPORARY_IMPLEMENTATION);
	}

	public void delete(Supermarket supermarketToDelete) {
		throw new UnsupportedOperationException(TEMPORARY_IMPLEMENTATION);
		
	}

	public void deleteAllSupermarkets() {
		throw new UnsupportedOperationException(TEMPORARY_IMPLEMENTATION);
	}

	public List<Supermarket> getSupermarketsByName(String supermarketName) {
		throw new UnsupportedOperationException(TEMPORARY_IMPLEMENTATION);
	}

}
