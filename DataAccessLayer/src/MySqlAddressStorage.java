import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;

public class MySqlAddressStorage implements IAddressStorage {

	DataAccess dAccess = new DataAccess();

	@Override
	public int addAddress(Address address, Object getCitizenApartmentNo) {
		int address_id = 0;
		try {

			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(dAccess.DBMS_CONN_STRING, dAccess.DBMS_USERNAME,
					dAccess.DBMS_PASSWORD);
			CallableStatement statement;
			statement = con.prepareCall("{call insert_address(?, ?, ?, ?, ?, ?, ?, ?, ?)}");

			statement.setString(1, address.getCountry());
			statement.setString(2, address.getCity());
			statement.setString(3, address.getMunicipality());
			statement.setString(4, address.getPostalCode());
			statement.setString(5, address.getStreet());
			statement.setString(6, address.getNumber());
			statement.registerOutParameter(9, Types.INTEGER);
			statement.setInt(7, 0);
			statement.setInt(8, 0);
			if (getCitizenApartmentNo != null) {
				statement.setInt(7, address.getFloor());
				statement.setInt(8, address.getApartmentNo());
			}

			statement.executeQuery();
			address_id = statement.getInt("address_id");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			while (e != null) {
				System.out.println(e.getSQLState());
				System.out.println(e.getMessage());
				System.out.println(e.getErrorCode());
				e = e.getNextException();
			}
		}
		return address_id;
	}

	@Override
	public boolean removeAddress(int citizen_id) {

		return false;
	}

	@Override
	public boolean changeAddress(Address newAddress, int citizenId) {

		return false;
	}

}
