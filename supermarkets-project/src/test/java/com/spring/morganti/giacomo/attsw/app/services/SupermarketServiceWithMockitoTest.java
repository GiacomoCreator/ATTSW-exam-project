package com.spring.morganti.giacomo.attsw.app.services;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.math.BigInteger;
import java.util.Optional;
import static java.util.Collections.emptyList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.spring.morganti.giacomo.attsw.app.model.Supermarket;
import com.spring.morganti.giacomo.attsw.app.repositories.SupermarketRepository;

@RunWith(MockitoJUnitRunner.class)
public class SupermarketServiceWithMockitoTest {
	
	@Mock
	private SupermarketRepository supermarketRepository;
	
	@InjectMocks
	private SupermarketService supermarketService;
	
	@Test
	public void test_getAllSupermarkets_withZeroSupermarkets() {
		
		when(supermarketRepository.findAll())
			.thenReturn(emptyList());
		assertThat(supermarketService.getAllSupermarkets())
			.isEmpty();
		
		verify(supermarketRepository).findAll();
		verifyNoMoreInteractions(supermarketRepository);
		
	}
	
	@Test
	public void test_getAllSupermarkets_withOneSupermarket() {
		
		Supermarket supermarket = new Supermarket(BigInteger.valueOf(1), "supermarket", "address");
		when(supermarketRepository.findAll())
			.thenReturn(asList(supermarket));
		assertThat(supermarketService.getAllSupermarkets())
			.containsExactly(supermarket);
		
		verify(supermarketRepository).findAll();
		verifyNoMoreInteractions(supermarketRepository);
		
	}
	
	@Test
	public void test_getAllSupermarkets_withMoreSupermarkets() {
		
		Supermarket supermarket1 = new Supermarket(BigInteger.valueOf(1), "supermarket1", "address1");
		Supermarket supermarket2 = new Supermarket(BigInteger.valueOf(2), "supermarket2", "address2");
		when(supermarketRepository.findAll())
			.thenReturn(asList(supermarket1, supermarket2));
		assertThat(supermarketService.getAllSupermarkets())
			.containsExactly(supermarket1, supermarket2);
		
		verify(supermarketRepository).findAll();
		verifyNoMoreInteractions(supermarketRepository);
		
	}
	
	@Test
	public void test_getSupermarketById_found() {
		
		Supermarket supermarket = new Supermarket(BigInteger.valueOf(1), "supermarket", "address");
		when(supermarketRepository.findById(BigInteger.valueOf(1)))
			.thenReturn(Optional.of(supermarket));
		assertThat(supermarketService.getSupermarketById(BigInteger.valueOf(1)))
			.isSameAs(supermarket);
		
		verify(supermarketRepository).findById(BigInteger.valueOf(1));
		verifyNoMoreInteractions(supermarketRepository);
	}

	@Test
	public void test_getSupermarketById_notFound() {
		
		when(supermarketRepository.findById(BigInteger.valueOf(1)))
			.thenReturn(Optional.empty());
		assertThat(supermarketService.getSupermarketById(BigInteger.valueOf(1)))
			.isNull();
		
		verify(supermarketRepository).findById(BigInteger.valueOf(1));
		verifyNoMoreInteractions(supermarketRepository);
	}
	
	@Test
	public void test_getSupermarketsByName_withZeroSupermarkets() {
		
		String supermarketName="supermarketName";
		
		when(supermarketRepository.findByName(supermarketName))
			.thenReturn(emptyList());
	    assertThat(supermarketService.getSupermarketsByName(supermarketName))
	        .isEmpty();
	
	    verify(supermarketRepository).findByName(supermarketName);
	    verifyNoMoreInteractions(supermarketRepository);
	
	}
	
	@Test
	public void test_getSupermarketsByName_withOneSupermarket() {
		
		String supermarketName="supermarketName";
		Supermarket supermarket = new Supermarket(BigInteger.valueOf(1), "supermarketName", "address");
		
		when(supermarketRepository.findByName(supermarketName))
			.thenReturn(asList(supermarket));
		assertThat(supermarketService.getSupermarketsByName(supermarketName))
			.containsExactly(supermarket);
		
		verify(supermarketRepository).findByName(supermarketName);
		verifyNoMoreInteractions(supermarketRepository);
	
	}
	
	@Test
	public void test_getSupermarketsByName_withMoreSupermarkets() {
		
		String supermarketName="supermarketName";
		Supermarket supermarket1 = new Supermarket(BigInteger.valueOf(1), "supermarketName", "address1");
		Supermarket supermarket2 = new Supermarket(BigInteger.valueOf(2), "supermarketName", "address2");
		
		when(supermarketRepository.findByName(supermarketName))
			.thenReturn(asList(supermarket1, supermarket2));
		assertThat(supermarketService.getSupermarketsByName(supermarketName))
			.containsExactly(supermarket1, supermarket2);
		
		verify(supermarketRepository).findByName(supermarketName);
		verifyNoMoreInteractions(supermarketRepository);
			
	}
	
	@Test
	public void test_insertNewSupermarket_setsIdToNull_and_returnsSavedSupermarket() {
		
		Supermarket toBeSavedSupermarket = spy(new Supermarket(BigInteger.valueOf(3), "toBeSavedSupermarket", "toBeSavedAddress"));
		Supermarket savedSupermarket = new Supermarket(BigInteger.valueOf(1), "savedSupermarket", "savedAddress");

		when(supermarketRepository.save(any(Supermarket.class)))
			.thenReturn(savedSupermarket);

		Supermarket resultingSupermarket = supermarketService.insertNewSupermarket(toBeSavedSupermarket);

		assertThat(resultingSupermarket).isSameAs(savedSupermarket);

		InOrder inOrder = inOrder(toBeSavedSupermarket, supermarketRepository);
		inOrder.verify(toBeSavedSupermarket).setId(null);
		inOrder.verify(supermarketRepository).save(toBeSavedSupermarket);
		
		verifyNoMoreInteractions(supermarketRepository);
		
	}

	@Test
	public void test_updateSupermarketById_setsIdToArgument_and_returnsSavedSupermarket() {
		
		Supermarket replacementSupermarket = spy(new Supermarket(null, "replacementSupermarket", "replacementAddress"));
		Supermarket replacedSupermarket = new Supermarket(BigInteger.valueOf(1), "replacedSupermarket", "replacedAddress");

		when(supermarketRepository.save(any(Supermarket.class)))
			.thenReturn(replacedSupermarket);

		Supermarket result = supermarketService.updateSupermarketById(BigInteger.valueOf(1), replacementSupermarket);

		assertThat(result).isSameAs(replacedSupermarket);

		InOrder inOrder = inOrder(replacementSupermarket, supermarketRepository);
		inOrder.verify(replacementSupermarket).setId(BigInteger.valueOf(1));
		inOrder.verify(supermarketRepository).save(replacementSupermarket);
		
		verifyNoMoreInteractions(supermarketRepository);
		
	}

	@Test
	public void test_deleteOneSupermarket() {
		
		Supermarket supermarketToDelete = new Supermarket(BigInteger.valueOf(1), "toBeDeletedSupermarket", "toBeDeletedAddress");
		supermarketService.delete(supermarketToDelete);
		
		verify(supermarketRepository).delete(supermarketToDelete);
		verifyNoMoreInteractions(supermarketRepository);
		
	}

	@Test
	public void test_deleteAllSupermarkets() {
		
		supermarketService.deleteAll();
		
		verify(supermarketRepository).deleteAll();
		verifyNoMoreInteractions(supermarketRepository);
		
	}
	
}
