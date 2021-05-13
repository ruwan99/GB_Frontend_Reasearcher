package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Reasearcher {
	
	// A common method to connect to the DB
		private Connection connect() {
			Connection con = null;

			try {
				Class.forName("com.mysql.jdbc.Driver");
				// Provide the correct details: DBServer/DBName, username, password
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/reasearcher", "root", "");

				// For testing
				System.out.print("Successfully connected");

			} catch (Exception e) {
				e.printStackTrace();
			}

			return con;
		}
		
		public String readReasearcher() {
			String output = "";

			try {
				Connection con = connect();
				if (con == null) {
					return "Error while connecting to the database for reading.";
				}

				// Prepare the html table to be displayed
				output = "<table border='1'><tr><th>Reasearcher Name</th>"
						+ "<th>Reasearcher Email</th><th>Reasearcher Type</th>" + "<th>Reasearcher Contact</th>"
						+ "<th>Update</th><th>Remove</th></tr>";

				String query = "select * from researcher";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);

				// iterate through the rows in the result set
				while (rs.next()) {

					String ReasearcherID = Integer.toString(rs.getInt("ReasearcherID"));
					String ReasearcherName = rs.getString("ReasearcherName");
					String ReasearcherEmail = rs.getString("ReasearcherEmail");
					String ReasearcherType = rs.getString("ReasearcherType");
					String ReasearcherContact = Integer.toString(rs.getInt("ReasearcherContact"));

					// Add into the html table

					output += "<tr><td><input id='hidReasearcherUpdate' name='hidReasearcherUpdate' type='hidden' value='"
							+ ReasearcherID + "'>" + ReasearcherName + "</td>";

					output += "<td>" + ReasearcherEmail + "</td>";
					output += "<td>" + ReasearcherType + "</td>";
					output += "<td>" + ReasearcherContact + "</td>";

					// buttons
					output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"
							+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-ReasearcherID='"
							+ ReasearcherID + "'>" + "</td></tr>";

				}

				con.close();

				// Complete the html table
				output += "</table>";
			} catch (Exception e) {
				output = "Error while reading the Reasearcher Details.";
				System.err.println(e.getMessage());
			}

			return output;
		}
		
		// Insert Reasearcher
		public String insertReasearcher(String ReasearcherName, String ReasearcherEmail, String ReasearcherType,
				String ReasearcherContact) {
			String output = "";

			try {
				Connection con = connect();

				if (con == null) {
					return "Error while connecting to the database";
				}

				// create a prepared statement
				String query = " insert into researcher (`ReasearcherID`,`ReasearcherName`,`ReasearcherEmail`,`ReasearcherType`,`ReasearcherContact`)"
						+ " values (?, ?, ?, ?, ?)";

				PreparedStatement preparedStmt = con.prepareStatement(query);

				// binding values
				preparedStmt.setInt(1, 0);
				preparedStmt.setString(2, ReasearcherName);
				preparedStmt.setString(3, ReasearcherEmail);
				preparedStmt.setString(4, ReasearcherType);
				preparedStmt.setString(5, ReasearcherContact);

				// execute the statement
				preparedStmt.execute();
				con.close();

				// Create JSON Object to show successful msg.
				String newReasearcher = readReasearcher();
				output = "{\"status\":\"success\", \"data\": \"" + newReasearcher + "\"}";
			} catch (Exception e) {
				// Create JSON Object to show Error msg.
				output = "{\"status\":\"error\", \"data\": \"Error while Inserting Reasearcher.\"}";
				System.err.println(e.getMessage());
			}

			return output;
		}
		
		// Update Reasearcher
		public String updateReasearcher(String ReasearcherID, String ReasearcherName, String ReasearcherEmail,
				String ReasearcherType, String ReasearcherContact) {
			String output = "";

			try {
				Connection con = connect();

				if (con == null) {
					return "Error while connecting to the database for updating.";
				}

				// create a prepared statement
				String query = "UPDATE researcher SET ReasearcherName=?,ReasearcherEmail=?,ReasearcherType=?,ReasearcherContact=? WHERE ReasearcherID=?";

				PreparedStatement preparedStmt = con.prepareStatement(query);

				// binding values
				preparedStmt.setString(1, ReasearcherName);
				preparedStmt.setString(2, ReasearcherEmail);
				preparedStmt.setString(3, ReasearcherType);
				preparedStmt.setInt(4, Integer.parseInt(ReasearcherContact));
				preparedStmt.setInt(5, Integer.parseInt(ReasearcherID));

				// execute the statement
				preparedStmt.execute();
				con.close();

				// create JSON object to show successful msg
				String newReasearcher = readReasearcher();
				output = "{\"status\":\"success\", \"data\": \"" + newReasearcher + "\"}";
			} catch (Exception e) {
				output = "{\"status\":\"error\", \"data\": \"Error while Updating Researcher Details.\"}";
				System.err.println(e.getMessage());
			}

			return output;
		}
		public String deleteReasearcher(String ReasearcherID) {
			String output = "";

			try {
				Connection con = connect();

				if (con == null) {
					return "Error while connecting to the database for deleting.";
				}

				// create a prepared statement
				String query = "DELETE FROM researcher WHERE ReasearcherID=?";

				PreparedStatement preparedStmt = con.prepareStatement(query);

				// binding values
				preparedStmt.setInt(1, Integer.parseInt(ReasearcherID));
				// execute the statement
				preparedStmt.execute();
				con.close();

				// create JSON Object
				String newReasearcher = readReasearcher();
				output = "{\"status\":\"success\", \"data\": \"" + newReasearcher + "\"}";
			} catch (Exception e) {
				// Create JSON object
				output = "{\"status\":\"error\", \"data\": \"Error while Deleting Reasearcher.\"}";
				System.err.println(e.getMessage());

			}

			return output;
		}



}
