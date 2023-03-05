package Invoicing;
import java.sql.*;
import java.util.Scanner;
public class JDBC {

	public static void main(String[] args) {

		String url = "jdbc:sqlserver://localhost:1433;" +
	              "databaseName=Invoicing_System;" +
	              "encrypt=true;" +
	              "trustServerCertificate=true";
	      String user = "sa";
	      String pass = "root";
	//Scanner scanner = new Scanner(System.in);
	//System.out.println("enter name");
//	      String name = scanner.next();
	//System.out.println("enter roll no");
	//Integer roll = scanner.nextInt();
	//System.out.println("enter class");
//	      String cls = scanner.next();

	 	 Connection con = null;

	      try {
	Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
	DriverManager.registerDriver(driver);
	          con = DriverManager.getConnection(url, user, pass);
	Statement st = con.createStatement();
	String sql1 = "sql code here...";
			
	Integer m = st.executeUpdate(sql1); //sql execution
	          if (m >= 1) {
	System.out.println("inserted successfully : " + sql1);
	} else {
	System.out.println("insertion failed");
	}
	//ResultSet resultSet = st.executeQuery(sql1);
//	           while (resultSet.next()) {
	//System.out.println(resultSet.getString("name"));
	//System.out.println(resultSet.getString("roll"));
	//System.out.println(resultSet.getString("cls"));
	//}
	          con.close();
	} catch (Exception ex) {
	System.err.println(ex);
	}
	}

}
