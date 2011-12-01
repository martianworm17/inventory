package com.inventory.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.inventory.dao.InventoryDao;
import com.inventory.dao.InventoryDaoImpl;
import com.inventory.model.Product;
import com.views.InventoryView;
import com.views.ListInventoryPanel;

public class InventoryControllerImpl implements InventoryController {
			
	private InventoryDao dao;
	private InventoryView view;
	
    public InventoryControllerImpl(){
    	this.dao = new InventoryDaoImpl();
    }
    
	public void setView(InventoryView view) {
		this.view = view;
	}

	@Override
	public void addItem(Product product) {
		this.dao.create(product);
		view.updateListeners();	
	}
	
	public List<Product> listItems()
	{
		return this.dao.listAllProducts();
	}
	

	@Override
	public int updateItem(Product product) {
		int numberOfUpdates = 0;
		Product foundProduct = getItem(product.getId());
		
		if (foundProduct.getName() != null) {
			numberOfUpdates = dao.update(product);
			view.updateListeners();	
		}		
		return numberOfUpdates;
	}

	@Override
	public Product getItem(int id) {
		return dao.get(id);
	}
	
	public void search(String keyword, String category)
	{	
		List<Product> products = listItems();
		List<Product> filteredProducts = new ArrayList<Product>();
		for (Product product : products) 
		{	
			if(ListInventoryPanel.NAME_HEADING.equals(category))
			{
				if(product.getName().contains(keyword))
				{
					filteredProducts.add(product);
				}
			}
			else if(ListInventoryPanel.DESCRIPTION_HEADING.equals(category))
			{
				if(product.getDescription().contains(keyword))
				{
					filteredProducts.add(product);
				}
			}
			else if(ListInventoryPanel.ALL_HEADING.equals(category))
			{
				if(product.getName().contains(keyword) || product.getDescription().contains(keyword))
				{
					filteredProducts.add(product);
				}
			}
		}
		view.updateListeners(filteredProducts);
	}

}
