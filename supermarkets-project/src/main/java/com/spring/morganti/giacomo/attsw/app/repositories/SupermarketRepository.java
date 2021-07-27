package com.spring.morganti.giacomo.attsw.app.repositories;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.spring.morganti.giacomo.attsw.app.model.Supermarket;

public interface SupermarketRepository extends MongoRepository<Supermarket, BigInteger>{
	
	Optional<Supermarket> findById(BigInteger id);
	List<Supermarket> findByName(String supermarketName);
}
