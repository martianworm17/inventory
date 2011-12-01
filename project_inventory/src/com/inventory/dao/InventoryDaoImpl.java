package com.inventory.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inventory.model.Product;

public class InventoryDaoImpl implements InventoryDao{
	
	private static final String TABLE_NAME = "inventory";
	private DatabaseManager db;
	
	public InventoryDaoImpl(){
		this.db = DatabaseManager.getInstance();
	}
	
	@Override
	public void create(Product product) {
		try {
			PreparedStatement statement = null;
			Connection connection = db.getConnection();
			try {
				
				String sql = "INSERT INTO "+TABLE_NAME+" " +
					"(name,description)" +
					" VALUES " +
					"(?,?)";
					
				statement = connection.prepareStatement(sql);
				statement.setString(1, product.getName());
				statement.setString(2, product.getDescription());
			
				statement.execute();
			} finally {
				statement.close();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * Returns all the products in the database
	 */
	@Override
	public List<Product> listAllProducts() 
	{
		List<Product> products = new ArrayList<Product>();
		
		try
		{			
			Connection connection = db.getConnection();
			Statement statement = connection.createStatement();			
			try 
			{
				String sql = "SELECT id, name, description FROM inventory";
				ResultSet result = statement.executeQuery(sql);
				
				while(result.next())
				{
					int key = result.getInt("id");
					String name = result.getString("name");
					String description = result.getString("description");
					
					products.add(new Product(key,name, description));
				}
				
			} 
			finally 
			{
				statement.close();
			}
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return products;
		
		
	}

	@Override
	public int update(Product product) {
		int numberOfUpdates = 0;
		try {
			PreparedStatement statement = null;
			Connection connection = db.getConnection();
			
			
			String sql = "UPDATE " + TABLE_NAME +
					" SET " +
					"name=?,description=?" +
					" WHERE id=?;";
			
			statement = connection.prepareStatement(sql);
			statement.setString(1, product.getName());
			statement.setString(2, product.getDescription());
			statement.setInt(3, product.getId());
			
			numberOfUpdates = statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return numberOfUpdates;
	}
	
	@Override
	public Product get(int id) {
		Product product = new Product();
		try {
			ResultSet rs = null;
			Connection connection = db.getConnection();
			try {
				
				String sql = "SELECT id,name,description FROM "+ TABLE_NAME +
						" WHERE id = ?;";
					
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setInt(1, id);
			
				rs = statement.executeQuery();
				
				while (rs.next()) {
					product.setId(rs.getInt("id"));
					product.setName(rs.getString("name"));
					product.setDescription(rs.getString("description"));
				}
			} finally {
				rs.close();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return product;
	}
	
	
	

}
