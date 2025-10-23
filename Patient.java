package Hospital;

import java.sql.*;
import java.util.*;


public class Patient {
	
	private Connection connection;
	
	private Scanner scanner;
	
	public Patient(Connection connection,Scanner scanner) {
		
		this.connection = connection;
		this.scanner = scanner;
		
	}
	
	public void admitpatient(){
		System.out.println("Enter Patient name");
		String name = scanner.nextLine();
		
		System.out.println("Enter Patient age");
		int age = scanner.nextInt();
		
		scanner.nextLine();
		
		System.out.println("Enter Patirnt gender");
		String gender = scanner.nextLine();
		
		try {
			String query = "INSERT INTO patients(name,age,gender) VALUES(?,?,?)";//? are place holders for the values 
			
			//preparedstatment is a method of connection wghich is used for passing sql queries as an arguments 
			
			PreparedStatement preparedstatement = connection.prepareStatement(query);
			preparedstatement.setString(1,name);
			preparedstatement.setInt(2,age);
			preparedstatement.setString(3,gender);
			
			int affectedrows = preparedstatement.executeUpdate();
			
			if(affectedrows>0) {
				System.out.println("Patient admited successfully");
			}
			else {
				System.out.println("Patient DIED!!! RIP");
			}
			
		}
		catch (SQLException e) {
			// jo bhi exception ka data aa rha hoga usse ek ek karke print karna h 
			e.printStackTrace();
			
		}
	}
	
	public void viewpatient(){
		
		String query = "SELECT * FROM patients";
		try {
		PreparedStatement preparedstatement = connection.prepareStatement(query);
		
		ResultSet resultSet = preparedstatement.executeQuery();
		
		// formating the way patients table will look exactly in MYSQL
		
		System.out.println("Patients:");
		System.out.println("+------------+-----------------------+-------------+----------------+");
		System.out.println("| Patient ID | Patient Name          | Patient Age | Patient Gender |");
		System.out.println("+------------+-----------------------+-------------+----------------+");
		
		// creating java local variable for mysql tables coloumn to store the data
		
		while(resultSet.next()) {
			
			int id = resultSet.getInt("id");
			String name = resultSet.getString("name");
			int age = resultSet.getInt("age");
			String gender = resultSet.getString("gender");
			//formating the columns
			System.out.printf("|%-12s|%-23s|%-13s|%-16s|\n",id,name,age,gender);
			System.out.println("+------------+-----------------------+-------------+----------------+");
		}
		
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public Boolean checkpatient(int id) {
		
		String query = "SELECT * FROM patients WHERE id = ?";
		
		try {
			PreparedStatement preparedstatement = connection.prepareStatement(query);
			preparedstatement.setInt(1,id);
			ResultSet resultSet = preparedstatement.executeQuery();
			
			if(resultSet.next()) {
				
				
				return true;
			}
			else {
				return false;
			}
			
			
		
		
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
		
	}
}
