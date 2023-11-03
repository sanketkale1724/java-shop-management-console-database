package user_management;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import db_operations.DBUtils;

public class UserManagement {

	public static void userManagement() throws IOException {

		Scanner sc = new Scanner(System.in);

		boolean canIKeepRunningTheProgram = true;

		while (canIKeepRunningTheProgram == true) {
			System.out.println("------WELCOME TO THE USER_MANAGEMENT PROGRAM------");
			System.out.println("\n");

			System.out.println("What would you like to do?");
			System.out.println("1. Add User");
			System.out.println("2. Edit User");
			System.out.println("3. Delete User");
			System.out.println("4. Search User");
			System.out.println("5. Quit");

			int optionSelectedByUser = sc.nextInt();

			if (optionSelectedByUser == UserOptions.EXIT) {
				System.out.println("!!! Program Closed !!!");
				canIKeepRunningTheProgram = false;
			} else if (optionSelectedByUser == UserOptions.ADD_USER) {
				addUser();
				System.out.println();
			} else if (optionSelectedByUser == UserOptions.SEARCH_USER) {
				System.out.print("Enter the User_Name which you wanna search :");
				sc.nextLine();
				String sn = sc.nextLine();
				searchUser(sn);
				System.out.println("\n");
			} else if (optionSelectedByUser == UserOptions.DELETE_USER) {
				System.out.print("Enter the User_Name which you wanna Delete :");
				sc.nextLine();
				String su = sc.nextLine();
				deleteUser(su);
				System.out.println("\n");
			} else if (optionSelectedByUser == UserOptions.EDIT_USER) {
				System.out.print("Enter the User_Name which you wanna Edit :");
				sc.nextLine();
				String eu = sc.nextLine();
				editUser(eu);
				System.out.println("\n");
			}
		}
	}

	public static void addUser() {
		Scanner sc = new Scanner(System.in);
		User user = new User();

		System.out.println("Enter Username :");
		user.userName = sc.nextLine();

		System.out.println("Enter loginname :");
		user.loginName = sc.nextLine();

		System.out.println("Enter password :");
		user.password = sc.nextLine();

		System.out.println("Enter confirmPassword :");
		user.confirmPassword = sc.nextLine();

		System.out.println("Enter UserRole :");
		user.userRole = sc.nextLine();

		System.out.println("\n");
		System.out.println("Username is :" + user.userName);
		System.out.println("Loginname is :" + user.loginName);
		System.out.println("Password is :" + user.password);
		System.out.println("ConfirmPassword is :" + user.confirmPassword);
		System.out.println("UserRole is :" + user.userRole);

		String query = "INSERT INTO User(Username,LoginName,Password,ConfirmPassword,UserRole)VALUES('" + user.userName
				+ "','" + user.loginName + "','" +user.password + "','" + user.password + "','" + user.userRole + "')";

		DBUtils.executeQuery(query);
		System.out.println("User added successfully.");
	}

	public static void editUser(String Username) {
		String Query = "select * from User where Username = '" + Username + "'";

		ResultSet rs = DBUtils.executeQueryGetResult(Query);
		try {

			if (rs.next()) {
				Scanner sc = new Scanner(System.in);
				User user=new User();

				System.out.print("New UserName is :");
				user.userName= sc.nextLine();

				System.out.print("New LoginName is :");
				user.loginName = sc.nextLine();

				System.out.print("New Password is :");
				user.password = sc.nextLine();

				System.out.print("New Conform_Password is :");
				user.confirmPassword= sc.nextLine();

				System.out.print("New User_Role is :");
				user.userRole = sc.nextLine();

				String query = "UPDATE User SET Username = '" + user.userName+ "', " + "LoginName = '" +user.loginName
						+ "', " + "Password = '" +user.password + "', " + "ConfirmPassword = '" + user.confirmPassword
						+ "'," + "UserRole = '" +user.userRole + "'" + "WHERE Username = '" + Username + "'";

				DBUtils.executeQuery(query);

				System.out.println("User updated successfully.");

			} else {
				System.out.println("!!!!USER NOT FOUND!!!!!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void searchUser(String Username) {
		String query = "SELECT * FROM User WHERE Username = '" + Username + "'";

		ResultSet reultSet = DBUtils.executeQueryGetResult(query);

		try {
			if (reultSet.next()) {
				System.out.println("Username is :" + reultSet.getString("Username"));
				System.out.println("LoginName is :" + reultSet.getString("LoginName"));
				System.out.println("Password is :" + reultSet.getString("Password"));
				System.out.println("Conform_Password is :" +reultSet.getString("ConfirmPassword"));
				System.out.println("User_Role is :" + reultSet.getString("UserRole"));
			} else {
				System.out.println("!!!!USER NOT FOUND!!!!!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void deleteUser(String Username) {

		String query = "DELETE FROM User WHERE Username = '" + Username + "'";

		DBUtils.executeQuery(query);

	//	int rowsDeleted = DBUtils.getRowsDeleted();

		// if (rowsDeleted > 0) {
		//	System.out.println("User " + Username + " has been deleted");
		//} else {
		//	System.out.println("!!!!USER NOT FOUND!!!!");
		//}
	}

	public static boolean validateUserAndPassword(String loginName, String password) {
		String query = " Select * from User where LoginName='" + loginName + "' and password = '" + password + "' ";

		ResultSet rs = DBUtils.executeQueryGetResult(query);
		try {
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
