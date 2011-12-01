package com.inventory.controllers;

import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import com.inventory.model.Product;


public class InventoryControllerTest {
	InventoryController ic;
	
	@Before
	public void setUp() throws Exception {
		this.ic = new InventoryControllerImpl();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddItem_empty() {
		Product product = new Product(0,"","");
		try {
			ic.addItem(product);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testListItems_not_null() {
		List<Product> products = ic.listItems();
		
		assertNotNull(products);
	}

	@Test
	public void testUpdateItem_empty() {
		Product product = new Product(0,"","");
		int updatedItems = ic.updateItem(product);
		
		assertEquals(0, updatedItems);
	}

	@Test
	public void testGetItem_unidentified_item() {
		Product product = ic.getItem(999);
		assertNotNull(product);
	}

}
