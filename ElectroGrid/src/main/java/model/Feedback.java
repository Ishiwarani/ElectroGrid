package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Feedback {
	public Connection connect() {
		Connection con = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/electrogrid", "root", "");
			// For testing
			System.out.print("Successfully connected");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return con;
	}
	
	// insert feedback
			public String insertFeedback(String name, String email, String rate, String notes) {
				Connection con = connect();
				String output = "";
				if (con == null) {
					return "Error while connecting to the database";
				}

				// create a prepared statement
				String query = " insert into feedbacks (`FeedbackID`,`CustomerName`,`CustomerEmail`,`Rate`,`FeedbackNotes`)"
						+ " values (?, ?, ?, ?, ?)";
				PreparedStatement preparedStmt;
				try {
					preparedStmt = con.prepareStatement(query);

					preparedStmt.setInt(1, 0);
					preparedStmt.setString(2, name);
					preparedStmt.setString(3, email);
					preparedStmt.setString(4, rate);
					preparedStmt.setString(5, notes);

					preparedStmt.execute();
					con.close();
					output = "Inserted successfully";
				} catch (SQLException e) {
					output = "Error while inserting";
					System.err.println(e.getMessage());
				}

				return output;
			}
			
			//read feedbacks
			public String readFeedbacks() {
				String output = "";
				try {
					Connection con = connect();
					if (con == null) {
						return "Error while connecting to the database for reading Customers.";
					}
					// Prepare the html table to be displayed
					output = "<table border='1'><tr><th>Customer Name</th><th>Customer Email</th>" + "<th>Feedback Rate</th>"
							+ "<th>Feedback Notes</th></tr>";

					String query = "select * from feedbacks";
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery(query);
					// iterate through the rows in the result set
					while (rs.next()) {
						String FeedbackID = Integer.toString(rs.getInt("FeedbackID"));
						String CustomerName = rs.getString("CustomerName");
						String CustomerEmail = rs.getString("CustomerEmail");
						String Rate = rs.getString("Rate");
						String Notes = rs.getString("FeedbackNotes");

						// Add into the html table
						output += "<tr><td>" + CustomerName + "</td>";
						output += "<td>" + CustomerEmail + "</td>";
						output += "<td>" + Rate + "</td>";
						output += "<td>" + Notes + "</td>";
					}
					con.close();

					output += "</table>";
				}

				catch (Exception e) {
					output = "Error while reading the Customers.";
					System.err.println(e.getMessage());
				}
				return output;
			}
			
			//update feedback
			public String updateFeedback(String ID, String name, String email, String rate, String notes)

			{
				String output = "";
				try {
					Connection con = connect();
					if (con == null) {
						return "Error while connecting to the database for updating.";
					}
					// create a prepared statement

					String query = " update feedbacks set CustomerName= ? , CustomerEmail = ? , Rate = ? , FeedbackNotes = ?  where FeedbackID = ? ";

					PreparedStatement preparedStmt = con.prepareStatement(query);
					// binding values
					preparedStmt.setString(1, name);
					preparedStmt.setString(2, email);
					preparedStmt.setString(3, rate);
					preparedStmt.setString(4, notes);

					preparedStmt.setInt(5, Integer.parseInt(ID));
					// execute the statement
					preparedStmt.execute();
					con.close();
					output = "Updated successfully";
				} catch (Exception e) {
					output = "Error while updating the customer.";
					System.err.println(e.getMessage());
				}
				return output;
			}	
			
			//delete feedback
			public String deleteFeedback(String FeedbackID) {
				String output = "";
				try {
					Connection con = connect();
					if (con == null) {
						return "Error while connecting to the database for deleting.";
					}
					// create a prepared statement
					String query = "delete from feedbacks where FeedbackID=?";
					PreparedStatement preparedStmt = con.prepareStatement(query);
					// binding values
					preparedStmt.setInt(1, Integer.parseInt(FeedbackID));
					// execute the statement
					preparedStmt.execute();
					con.close();
					output = "Deleted successfully";
				} catch (Exception e) {
					output = "Error while deleting the Customer.";
					System.err.println(e.getMessage());
				}
				return output;
			}

}
