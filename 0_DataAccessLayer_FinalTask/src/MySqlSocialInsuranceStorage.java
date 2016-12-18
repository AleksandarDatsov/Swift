import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;

public class MySqlSocialInsuranceStorage implements ISocialInsuranceStorage {
	private DataAccess dataAccess = new DataAccess();

	@Override
	public void addSocialInsurance(SocialInsuranceRecord soInsuranceRecord, int citizenId) {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(dataAccess.DBMS_CONN_STRING, dataAccess.DBMS_USERNAME,
					dataAccess.DBMS_PASSWORD);
			java.sql.CallableStatement statement = con.prepareCall("{call insert_social_insurance( ?, ?, ?,?,?)}");

			statement.setInt(1, soInsuranceRecord.getYear());
			statement.setInt(2, soInsuranceRecord.getMonth());
			statement.setDouble(3, soInsuranceRecord.getAmount());
			statement.setInt(4, citizenId);
			statement.registerOutParameter(5, Types.INTEGER);

			statement.executeQuery();


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

	}

}
