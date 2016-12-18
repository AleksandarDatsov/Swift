import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.time.Instant;
import java.time.ZoneId;

public class MySqlCitizenStorage implements ICitizenStorage {
	private DataAccess dAccess = new DataAccess();

	@Override
	public int addCitizen(Citizen citizen, int address_id) {
		int personId = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(dAccess.DBMS_CONN_STRING, dAccess.DBMS_USERNAME,
					dAccess.DBMS_PASSWORD);
			java.sql.CallableStatement statement = con
					.prepareCall("{call insert_person_characteristics(?, ?, ?, ?, ?,?,?, ?)}");
			String gender = "M";
			if (citizen.getGender() == Gender.Male) {
				gender = "M";
			} else {
				gender = "F";
			}
			Date date = java.sql.Date.valueOf(citizen.getDateOfBirth());

			statement.setString("first_name", citizen.getFirstName());
			statement.setString(2, citizen.getMiddleName());
			statement.setString(3, citizen.getLastName());
			statement.setString(4, gender);
			statement.setInt(5, citizen.getHeight());
			statement.setDate(6, date);
			statement.setInt(7, address_id);
			statement.registerOutParameter(8, Types.INTEGER);

			statement.executeQuery();
			personId = statement.getInt("person_id");
		} catch (ClassNotFoundException e) {
			System.out.println(" | ClassNotFoundException");
		} catch (SQLException e) {
			while (e != null) {
				System.out.println(e.getSQLState());
				System.out.println(e.getMessage());
				System.out.println(e.getErrorCode());
				e = e.getNextException();
			}
		}
		return personId;
	}

	@Override
	public boolean deleteCitizen(int citizenId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Citizen getCitizen(int citizen_id) {
//		Citizen citizen = null;
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//			Connection con = DriverManager.getConnection(dAccess.DBMS_CONN_STRING, dAccess.DBMS_USERNAME,
//					dAccess.DBMS_PASSWORD);
//			java.sql.CallableStatement statement = con
//					.prepareCall("{call insert_person_characteristics(?, ?, ?, ?, ?,?,?, ?)}");
//
//			String fName = statement.getString("first_name");
//			String mName = statement.getString(2);
//			String lName = statement.getString(3);
//			String gend = statement.getString(4);
//			int height = statement.getInt(5);
//			Date dateOfB = statement.getDate(6);
//			int addressId = statement.getInt(7);
//			Gender gender;
//			if (gend.equals("M")) {
//				gender = Gender.Male;
//			} else {
//				gender = Gender.Female;
//			}
//
//			Instant.ofEpochMilli(dateOfB.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
//			citizen = new Citizen(fName, mName, lName, gender, height,
//					Instant.ofEpochMilli(dateOfB.getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
//			statement.executeQuery();
//		} catch (Exception e) {
//			System.out.println(" | ClassNotFoundException");
//			e.printStackTrace();
//		}
		return null;
	}

}
