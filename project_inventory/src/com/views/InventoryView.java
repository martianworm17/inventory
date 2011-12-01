package com.views;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.inventory.controllers.InventoryController;
import com.inventory.controllers.InventoryControllerImpl;
import com.inventory.model.Product;

public class InventoryView extends JFrame {

	private InventoryController controller;
	private AddInventoryPanel addInventory;
	private UpdateInventoryPanel updateInventory;
	private ListInventoryPanel listInventory;

	public InventoryView(InventoryController controller) {
		this.setController(controller);
		addInventory = new AddInventoryPanel(controller);
		updateInventory = new UpdateInventoryPanel(controller);
		listInventory = new ListInventoryPanel(controller);
		buildGui();
	}

	public void setController(InventoryControllerImpl controller) {
		this.controller = controller;
	}

	private void buildGui() {
		this.setSize(640, 640);

		JPanel topPanel = new JPanel();
		JTabbedPane tabMenu = new JTabbedPane();

		// add here
		tabMenu.addTab("List Item", listInventory);
		tabMenu.addTab("Add Item", addInventory);
		tabMenu.addTab("Update Item", updateInventory);

		tabMenu.setSelectedComponent(addInventory);
		topPanel.add(tabMenu);
		getContentPane().add(topPanel);
		setVisible(true);

	}

	public void updateListeners() 
	{
		updateListeners(this.controller.listItems());
	}
	
	public void updateListeners(List<Product> products)
	{
		listInventory.buildGui(products);
	}
	
	public InventoryController getController() {
		return controller;
	}

	public void setController(InventoryController controller) {
		this.controller = controller;
	}


}
