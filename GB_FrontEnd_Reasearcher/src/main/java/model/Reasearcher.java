package model;

import java.sql.Connection;
import java.sql.DriverManager;
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


}
