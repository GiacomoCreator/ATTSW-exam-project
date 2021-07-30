package com.spring.morganti.giacomo.attsw.app;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import com.spring.morganti.giacomo.attsw.app.model.Supermarket;
import com.spring.morganti.giacomo.attsw.app.repositories.SupermarketRepository;
import com.spring.morganti.giacomo.attsw.app.services.SupermarketService;

@RunWith(SpringRunner.class)
@DataMongoTest
@Import(SupermarketService.class)
public class SupermarketServiceRepositoryIT {

	@Autowired
	private SupermarketService supermarketService;
	
	@Autowired
	private SupermarketRepository supermarketRepository;
	
	@Before
	public void setUp() {
		supermarketRepository.deleteAll();
	}
	
	@After
	public void cleanUp() {
		supermarketRepository.deleteAll();
	}
	
	@Test
	public void test_serviceCanInsertSupermarketIntoRepository() {
		
		Supermarket supermarket = supermarketService.insertNewSupermarket(
				new Supermarket(null, "supermarket", "address"));
		
		assertThat(supermarketRepository.findById(supermarket.getId())).isPresent();
		
	}
	
	@Test
	public void test_serviceCanRetrieveSupermarketFromRepository_byId() {
		
		Supermarket supermarket = supermarketRepository.save(new Supermarket(null, "supermarket", "address"));
		
		Supermarket supermarketToRetrieve = supermarketService.getSupermarketById(supermarket.getId());
		
		assertThat(supermarketRepository.findById(supermarket.getId()).get()).isEqualTo(supermarketToRetrieve);
	}
	
	@Test
	public void test_serviceCanRetrieveSupermarketFromRepository_byName() {
		
		supermarketRepository.save(new Supermarket(null, "supermarket1", "address1"));
		supermarketRepository.save(new Supermarket(null, "supermarket2", "address2"));
		
		String nameToRetrieve = "supermarket1";
			
		List<Supermarket> supermarketToRetrieve = supermarketService.getSupermarketsByName(nameToRetrieve);
		
		assertThat(supermarketRepository.findByName(nameToRetrieve)).isEqualTo(supermarketToRetrieve);
	}
	
	@Test
	public void test_serviceCanUpdateSupermarketIntoRepository(){
		
		Supermarket originalSupermarket = supermarketRepository.save(new Supermarket(null, "originalName", "originalAddress"));
		
		Supermarket updatedSupermarket = new Supermarket(originalSupermarket.getId(), "updatedName", "updatedAddress");
		
		Supermarket result = supermarketService.updateSupermarketById(originalSupermarket.getId(), updatedSupermarket);
		
		assertThat(supermarketRepository.findById(originalSupermarket.getId()).get()).isEqualTo(result);
		
	}
	
	@Test
	public void test_serviceCanDeleteSupermarketFromRepository(){
		
		Supermarket supermarketToDelete = supermarketRepository.save(new Supermarket(null, "supermarket", "address"));
		
		supermarketService.delete(supermarketToDelete);
		
		assertThat(supermarketRepository.findById(supermarketToDelete.getId())).isNotPresent();
	}
	
}
