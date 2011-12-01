package com.inventory.controllers;

import java.util.List;
import java.util.Map;

import com.inventory.model.Product;
import com.views.InventoryView;

public interface InventoryController 
{	
	
	 void addItem(Product product);
	 
	 List<Product> listItems();
	 
	 void setView(InventoryView view);
	 
	 int updateItem(Product product);
	 
	 Product getItem(int id);
	 
	 void search(String keyword, String category);
}
