/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.finalproject5100.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 *
 * @author kiara
 */
public class dbUtils {
    private static dbUtils instance;
    private Connection connection;
    private String url = "jdbc:mysql://localhost:3306/flowcart";
    private String username = "root";
    private String password = "1234";
    
    private dbUtils() throws SQLException {
        try{
            connection = DriverManager.getConnection(url, username, password);
            
        }catch(SQLException e){
            throw new RuntimeException("Error connection to the database", e);
        }
        
    }
    
    public static dbUtils getInstance() throws SQLException{
        if(instance == null){
            instance = new dbUtils();
        }
        return instance;
    }
    
    public Connection getConnection(){
        return connection;
    }
    
    public int save(String query, Object... params) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        for(int i =0; i<params.length;i++){
            preparedStatement.setObject(i+1,params[i]); 
        }
        //use executeUpdate for insertion, updating and deleting
        return preparedStatement.executeUpdate();
    }
    
    public ResultSet fetch(String query, Object... params) throws SQLException{
        PreparedStatement ps = connection.prepareStatement(query);
        for(int i=0;i<params.length;i++){
            ps.setObject(i+1,params[i]);
        }
        //use executeQuery for fetching data
        return ps.executeQuery();
    }
    
    public int update(String query, Object...params)throws SQLException{
        return save(query,params);
    }
    
     public int delete(String query, Object...params)throws SQLException{
        return save(query,params);
    }
}