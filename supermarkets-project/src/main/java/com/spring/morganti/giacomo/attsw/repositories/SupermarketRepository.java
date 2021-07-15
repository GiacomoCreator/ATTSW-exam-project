package com.spring.morganti.giacomo.attsw.repositories;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.spring.morganti.giacomo.attsw.app.model.Supermarket;

public interface SupermarketRepository extends MongoRepository<Supermarket, BigInteger>{
	
	Optional<Supermarket> findById(BigInteger id);
	
}
