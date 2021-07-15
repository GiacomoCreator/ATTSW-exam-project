package com.spring.morganti.giacomo.attsw.app.services;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

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
		
		Supermarket supermarket = new Supermarket(1L, "supermarket", "address");
		when(supermarketRepository.findAll())
			.thenReturn(asList(supermarket));
		assertThat(supermarketService.getAllSupermarkets())
			.containsExactly(supermarket);
		
		verify(supermarketRepository).findAll();
		verifyNoMoreInteractions(supermarketRepository);
		
	}
	
	@Test
	public void test_getAllSupermarkets_withMoreSupermarkets() {
		
		Supermarket supermarket1 = new Supermarket(1L, "supermarket1", "address1");
		Supermarket supermarket2 = new Supermarket(2L, "supermarket2", "address2");
		when(supermarketRepository.findAll())
			.thenReturn(asList(supermarket1, supermarket2));
		assertThat(supermarketService.getAllSupermarkets())
			.containsExactly(supermarket1, supermarket2);
		
		verify(supermarketRepository).findAll();
		verifyNoMoreInteractions(supermarketRepository);
		
	}
	
	@Test
	public void test_getSupermarketById_found() {
		
		Supermarket supermarket = new Supermarket(1L, "supermarket", "address");
		when(supermarketRepository.findById(1L))
			.thenReturn(Optional.of(supermarket));
		assertThat(supermarketService.getSupermarketById(1))
			.isSameAs(supermarket);
		
		verify(supermarketRepository).findById(1L);
		verifyNoMoreInteractions(supermarketRepository);
	}

	@Test
	public void test_getSupermarketById_notFound() {
		
		when(supermarketRepository.findById(anyLong()))
			.thenReturn(Optional.empty());
		assertThat(supermarketService.getSupermarketById(1))
			.isNull();
		
		verify(supermarketRepository).findById(anyLong());
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
		Supermarket supermarket = new Supermarket(1L, "supermarketName", "address");
		
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
		Supermarket supermarket1 = new Supermarket(1L, "supermarketName", "address1");
		Supermarket supermarket2 = new Supermarket(2L, "supermarketName", "address2");
		
		when(supermarketRepository.findByName(supermarketName))
			.thenReturn(asList(supermarket1, supermarket2));
		assertThat(supermarketService.getSupermarketsByName(supermarketName))
			.containsExactly(supermarket1, supermarket2);
		
		verify(supermarketRepository).findByName(supermarketName);
		verifyNoMoreInteractions(supermarketRepository);
			
	}
	
	@Test
	public void test_insertNewSupermarket_setsIdToNull_and_returnsSavedSupermarket() {
		
		Supermarket toBeSavedSupermarket = spy(new Supermarket(3L, "toBeSavedSupermarket", "toBeSavedAddress"));
		Supermarket savedSupermarket = new Supermarket(1L, "savedSupermarket", "savedAddress");

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
		Supermarket replacedSupermarket = new Supermarket(1L, "replacedSupermarket", "replacedAddress");

		when(supermarketRepository.save(any(Supermarket.class)))
			.thenReturn(replacedSupermarket);

		Supermarket result = supermarketService.updateSupermarketById(1L, replacementSupermarket);

		assertThat(result).isSameAs(replacedSupermarket);

		InOrder inOrder = inOrder(replacementSupermarket, supermarketRepository);
		inOrder.verify(replacementSupermarket).setId(1L);
		inOrder.verify(supermarketRepository).save(replacementSupermarket);
		
		verifyNoMoreInteractions(supermarketRepository);
		
	}

	@Test
	public void test_deleteOneSupermarket() {
		
		Supermarket supermarketToDelete = new Supermarket(1L, "toBeDeletedSupermarket", "toBeDeletedAddress");
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
