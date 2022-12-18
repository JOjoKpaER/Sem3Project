package db;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.sqlite.SQLiteDataSource;

public class User {
	
	public String Login = null;
	public String Password = null;
	
	public User (String log, String pas) 
	{
		try 
		{
			log = UserConstructorLogException(log);
			pas = UserConstructorPasException(pas);
		}
		catch (Exception e)
		{
			log = null;
			pas = null;
		}
		finally {
		Login = log; 
		Password = pas;
		}
	}
	
	private String UserConstructorLogException (String log) throws Exception
	{
		if (log.length() < 2 || log.length() > 20) throw new Exception("");
		return log;
	}
	
	private String UserConstructorPasException (String pas) throws Exception
	{
		if (pas.length() < 2 || pas.length() > 20) throw new Exception("");
		return pas;
	}

	
	private static Database db = new Database();
	private static SQLiteDataSource ds = db.ConnectToDB();
	
	public boolean Check () 
	{
		return db.matchRowInDB(ds, Login, /*toSHA1(*/Password/*.getBytes())*/);
	}
	
	public void AddUser (/*Shell shell*/) throws Exception
	{
		try {
		if (Login == null || Password == null) throw new Exception("");
		}
		catch (Exception exc)
		{
			//MessageDialog.openError(shell, "", exc.getMessage());
		}
		finally {
			if (Login != null && Password != null) {
		db.insertValueToDB(ds, "NAME", Login);
		db.updateRowInDB(ds, "PASSWORD", /*toSHA1(*/Password/*.getBytes())*/, "NAME", Login);
			}
		}
	}
	
	public static void main (String args[])
	{
		
		db.deleteDB(ds);
		db.initializeDB(ds);
		
		db.insertValueToDB(ds, "NAME", "user1");
		db.insertValueToDB(ds, "NAME", "user2");
		db.insertValueToDB(ds, "NAME", "user3");
		db.updateRowInDB(ds, "PASSWORD", toSHA1("12345".getBytes()), "NAME", "user1");
		db.updateRowInDB(ds, "PASSWORD", toSHA1("23456".getBytes()), "NAME", "user2");
		db.updateRowInDB(ds, "PASSWORD", toSHA1("34567".getBytes()), "NAME", "user3");
		/**/
		System.out.println("	Count : " + db.printAllRowsInDB(ds));
	}

	public static String toSHA1(byte[] byteString) {
	    MessageDigest md = null;
	    try {
	        md = MessageDigest.getInstance("SHA-1");
	    }
	    catch(NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    } 
	    return new String(md.digest(byteString));
	}
	
}
	
