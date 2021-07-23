  
package com.spring.morganti.giacomo.attsw.app.services;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.spring.morganti.giacomo.attsw.app.model.Supermarket;

@Service
public class SupermarketService {

	private Map<Long, Supermarket> supermarkets = new LinkedHashMap<>();

	public SupermarketService() {
		supermarkets.put(1L, new Supermarket(1L, "supermarket1", "address1"));
		supermarkets.put(2L, new Supermarket(2L, "supermarket2", "address1"));
	}

	public List<Supermarket> getAllSupermarkets() {
		return new LinkedList<>(supermarkets.values());
	}

	public Supermarket getSupermarketById(long i) {
		return supermarkets.get(i);
	}

	public Supermarket insertNewSupermarket(Supermarket supermarket) {
		// dumb way of generating an automatic ID
		supermarket.setId(supermarkets.size()+1L);
		supermarkets.put(supermarket.getId(), supermarket);
		return supermarket;
	}

	public Supermarket updateSupermarketById(long id, Supermarket replacement) {
		replacement.setId(id);
		supermarkets.put(replacement.getId(), replacement);
		return replacement;
	}

	public void delete(Supermarket supermarketToDelete) {
		supermarkets.remove(supermarketToDelete.getId(), supermarketToDelete);
		
	}

	public void deleteAllSupermarkets() {
		supermarkets.clear();
	}

	public List<Supermarket> getSupermarketsByName(String supermarketName) {
		
		List<Supermarket> originalList = new LinkedList<>(supermarkets.values());
	    List<Supermarket> filteredList = new LinkedList<>();

	    for (Supermarket Supermarket : originalList) {
	            if (Supermarket.getName().equals(supermarketName)) {
	                filteredList.add(Supermarket);
            }
        }
	    
	    return filteredList;
    }

}