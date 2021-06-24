package com.spring.morganti.giacomo.attsw.app.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.spring.morganti.giacomo.attsw.app.model.Supermarket;

@Repository
public class SupermarketRepository {

	private static final String TEMPORARY_IMPLEMENTATION = "Temporary implementation";

	public  List<Supermarket> findAll() {
		throw new UnsupportedOperationException(TEMPORARY_IMPLEMENTATION);
	}

	public Optional<Supermarket> findById(long id) {
		throw new UnsupportedOperationException(TEMPORARY_IMPLEMENTATION);
		}

	public Supermarket save(Supermarket supermarket) {
		throw new UnsupportedOperationException(TEMPORARY_IMPLEMENTATION);
	}

	public void delete(Supermarket supermarketToDelete) {
		throw new UnsupportedOperationException(TEMPORARY_IMPLEMENTATION);		
	}

	public void deleteAll() {
		throw new UnsupportedOperationException(TEMPORARY_IMPLEMENTATION);
	}

}
