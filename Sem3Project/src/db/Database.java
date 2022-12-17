package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.sqlite.SQLiteDataSource;

public class Database {
	
	private static String dbName = "test";
	private static String columnName0 = "NAME";
	private static String columnName1 = "PASSWORD";

	public SQLiteDataSource ConnectToDB()
	{
		
		SQLiteDataSource ds = null;

        try {
            ds = new SQLiteDataSource();
            ds.setUrl("jdbc:sqlite:"+dbName+".db");
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        System.out.println( "Opened database successfully" );        
        return ds;
    }
	
	public void initializeDB(SQLiteDataSource ds)
	{
        String initializeStatment = "CREATE TABLE IF NOT EXISTS "+dbName+" ( " +
	   			  		  "ID INTEGER PRIMARY KEY, " +
	   			  		  columnName0 + " TEXT NOT NULL, " + 
	   			  		  columnName1 + " TEXT )";		//initialize table;
		 
        try ( Connection conn = ds.getConnection(); 
        	Statement stmt = conn.createStatement(); ) 
        {	
        int rv = stmt.executeUpdate(initializeStatment);
        System.out.println("InitializeDB state return value : " + rv);
        }
         catch ( SQLException e ) {
            e.printStackTrace();
        }
        finally {
            System.out.println( "Created database successfully" );
        }
	}
	
	public void deleteDB(SQLiteDataSource ds)
	{
        String deleteStatment = "DROP TABLE IF EXISTS "+dbName; //delete table
        
        try ( Connection conn = ds.getConnection(); 
            	Statement stmt = conn.createStatement(); ) 
            {
                int rv = stmt.executeUpdate( deleteStatment );
                System.out.println( "deleteDB state return value : " + rv );
            }
        catch ( SQLException e ) {
           e.printStackTrace();
       }
	}
	
	public void insertValueToDB(SQLiteDataSource ds, String column, String value)
	{
		try {
			if (column != columnName0 && column != columnName1) throw new Exception("Inexistent column name!");
		}
		catch (Exception e) {System.out.println(e.getMessage());}
		
        String insertStatment = "INSERT INTO "+dbName+" ( "+ column  +" ) VALUES ( '" + value + "' )"; // create new row
        
        try ( Connection conn = ds.getConnection(); 
            	Statement stmt = conn.createStatement(); ) 
            {
                int rv = stmt.executeUpdate( insertStatment );
                System.out.println( "insertValueToDB state return value : " + rv );
            }
        catch ( SQLException e ) {
           e.printStackTrace();
       }
	}
	
	public void updateRowInDB(SQLiteDataSource ds, String column, String valueIn, String row, String valueWhat)
	{
		try {
			if (column != columnName0 && column != columnName1) throw new Exception("Inexistent column name!");
			if (row != columnName0 && row != columnName1) throw new Exception("Inexistent column name!");
		}
		catch (Exception e) {System.out.println(e.getMessage());}
		
        String updateStatment = "UPDATE "+dbName+" SET "+ column  +" = '" + valueIn + "' WHERE "+row+" = '" + valueWhat + "'"; // change row

        try ( Connection conn = ds.getConnection(); 
            	Statement stmt = conn.createStatement(); ) 
            {
                int rv = stmt.executeUpdate( updateStatment );
                System.out.println( "updateRowInDB state return value : " + rv );
            }
        catch ( SQLException e ) {
           e.printStackTrace();
       }
	}
	
	public boolean matchRowInDB(SQLiteDataSource ds, String name, String password)
	{
        
      String getRow = "SELECT " + columnName0 + ", " + columnName1 + 
    		  		  " FROM " + dbName + 
    		  		  " WHERE " + columnName0 + " = '"+ name +"' AND " + 
    		  		  columnName1 + " = '" + password + "'"; // get row
      
      try ( Connection conn = ds.getConnection(); 
      	Statement stmt = conn.createStatement(); ) 
      		{
      		ResultSet rs = stmt.executeQuery(getRow);
      	
      		int i = 0; 	
      		while (rs.next()) i++; 		
  			if (i > 0) return true;
      		}
       	catch ( SQLException e ) {
       			e.printStackTrace();
       			return false;
       			}	
      return false;
		}
	
	public int printAllRowsInDB(SQLiteDataSource ds)
	{
		
		String getAnyRow = "SELECT ID, NAME, PASSWORD FROM " + dbName;
		 int i = 0;
		 
		 try(Connection conn = ds.getConnection();
			 Statement stmt = conn.createStatement();)
		 {
			 ResultSet rs = stmt.executeQuery(getAnyRow);
			 System.out.println("ID	" + "NAME	" + "PASSWORD");
			 while (rs.next()) 
			 { 
				 i++;
				 int id = rs.getInt("ID");
				 String name = rs.getString(columnName0);
				 String pass = rs.getString(columnName1);
				 System.out.println(id + " 	" + name + " 	" + pass.replace("\n", "").replace("\r", ""));
			 }
			 
		 }
		 catch (SQLException e) {
			 e.printStackTrace();
			 return i;
		 }
		 return i;
	}
	
}