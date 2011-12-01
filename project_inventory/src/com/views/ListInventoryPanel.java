package com.views;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.inventory.controllers.InventoryController;
import com.inventory.model.Product;

public class ListInventoryPanel extends JPanel implements KeyListener{
	private JButton addItem;
	
	public static final String DESCRIPTION_HEADING = "Description";
	public static final String NAME_HEADING = "Name";
	public static final String ALL_HEADING = "All";
	
	private JTextField myProductId;
	private JTextField myProductName;
	private JTextField myProductDesc;
	private JTextArea resultArea;
	
	private boolean isSortedByName;
	private boolean isSortedByDescription;
	private boolean isSortedById;
	private InventoryController controller;
	private JTextField searchField;
	private JPanel tablePanel;
	private JPanel controlPanel;
	private JComboBox searchCategory;
	
	public ListInventoryPanel(InventoryController controller) {
		this.controller = controller;
		setLayout(new BorderLayout());
		List<Product> products = controller.listItems();
		buildControls();
		buildGui(products);
		this.isSortedByName = false;
		this.isSortedByDescription = false;
	}
	
	public void buildControls()
	{
		controlPanel = new JPanel();
		searchField = new JTextField(30);
		searchField.addKeyListener(this);
		controlPanel.add(new JLabel("Search"));
		controlPanel.add(searchField);		
		controlPanel.add(new JLabel(" by "));
		
		searchCategory = new JComboBox();
		DefaultComboBoxModel searchComboModel = new DefaultComboBoxModel();
		searchComboModel.addElement(ALL_HEADING);
		searchComboModel.addElement(NAME_HEADING);
		searchComboModel.addElement(DESCRIPTION_HEADING);
		searchCategory.setModel(searchComboModel);
		controlPanel.add(searchCategory);
		
		JPanel sortingPanel = new JPanel();
		Button sortId = new Button("Sort by id");
		Button sortName = new Button("Sort by name");
		Button sortDescription = new Button("Sort by Description");
		
		sortingPanel.add(sortId);
		sortingPanel.add(sortName);
		sortingPanel.add(sortDescription);
		controlPanel.add(sortingPanel, BorderLayout.NORTH);
		
		sortId.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isSortedByName = false;
				isSortedByDescription = false;
				isSortedById = true;
				
				buildGui(controller.listItems());
			}
		});
		
		sortName.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isSortedByName = true;
				isSortedByDescription = false;
				isSortedById = false;
				
				buildGui(controller.listItems());
			}
		});
		
		sortDescription.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isSortedByName = false;
				isSortedByDescription = true;
				isSortedById = false;
				
				buildGui(controller.listItems());
			}
		});

		
		add(controlPanel, BorderLayout.NORTH);
	}
	
	public void buildGui(List<Product> products) 
	{
		if(tablePanel == null)
		{			
			tablePanel = new JPanel(new BorderLayout());
		}
		tablePanel.removeAll();
			
		//product headers
		String[] productHeaders = new String[]{"ID", NAME_HEADING, DESCRIPTION_HEADING};
		if (isSortedByName == true) {
			Collections.sort(products,new SortByName());
		} else if (isSortedByDescription == true) {
			Collections.sort(products,new SortByDescription());
		} else if (isSortedById == true) {
			Collections.sort(products,new SortById());
		}
		
		TableColumnModel columnModel = new DefaultTableColumnModel();
		
		TableColumn firstColumn = new TableColumn(0);
		firstColumn.setHeaderValue(productHeaders[0]);
		TableColumn secondColumn = new TableColumn(1);
		secondColumn.setHeaderValue(productHeaders[1]);
		TableColumn thirdColumn = new TableColumn(2);
		thirdColumn.setHeaderValue(productHeaders[2]);
		columnModel.addColumn(firstColumn);
		columnModel.addColumn(secondColumn);
		columnModel.addColumn(thirdColumn);
		
		DefaultTableModel tableModel = new DefaultTableModel();
		tableModel.addColumn(firstColumn);
		tableModel.addColumn(secondColumn);
		tableModel.addColumn(thirdColumn);				
		
		for (Product product : products) 
		{
			Vector<String> rowData = new Vector<String>();
			rowData.addElement(String.valueOf(product.getId()));
			rowData.addElement(product.getName());
			rowData.addElement(product.getDescription());
			tableModel.addRow(rowData);
		}
		
		JTable productTable = new JTable(tableModel, columnModel);		
		JScrollPane resultScrollPane = new JScrollPane(productTable);
		
		tablePanel.add(new JLabel("List all entry"), BorderLayout.PAGE_START);		
		tablePanel.add(resultScrollPane, BorderLayout.CENTER);
		
		add(tablePanel, BorderLayout.CENTER);
		revalidate();
	}

	@Override
	public void keyTyped(KeyEvent e) 
	{	
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		
	}
	@Override
	public void keyReleased(KeyEvent e) 
	{
		this.controller.search(searchField.getText(), ((String) searchCategory.getModel().getSelectedItem()));
	}
	
	private class SortByName implements Comparator<Product> {

		@Override
		public int compare(Product p1, Product p2) {
			return p1.getName().compareTo(p2.getName());
		}
		
	}
	
	private class SortByDescription implements Comparator<Product> {

		@Override
		public int compare(Product p1, Product p2) {
			return p1.getDescription().compareTo(p2.getDescription());
		}
		
	}
	
	private class SortById implements Comparator<Product> {

		@Override
		public int compare(Product p1, Product p2) {
			return new Integer(p1.getId()).compareTo(new Integer(p2.getId()));
		}
		
	}

}
