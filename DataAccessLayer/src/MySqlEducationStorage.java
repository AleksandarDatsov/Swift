import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;

public class MySqlEducationStorage implements IEducationStorage  {

	private int edDegreeType(EducationDegree edDegree) {
		switch (edDegree) {
		case Primary:
			return 1;
		case Secondary:
			return 2;
		case Bachelor:
			return 3;
		case Master:
			return 4;
		}
		return 5;
	}

	@Override
	public int addEducationToDB(Education education, int citizenId) {
		DataAccess dataAccess = new DataAccess();
		int educationId = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(dataAccess.DBMS_CONN_STRING, dataAccess.DBMS_USERNAME,
					dataAccess.DBMS_PASSWORD);
			java.sql.CallableStatement statement = con.prepareCall("{call insert_education(?, ?, ?, ?, ?, ?, ?)}");
			float finalGrade;
			if(education instanceof GradedEducation){
			finalGrade = ((GradedEducation) education).finalGrade;
			}else{
				finalGrade = 0;
			}
			Date date = java.sql.Date.valueOf(education.getEnrollmentDate());

			statement.setDate(1, date);
			date = Date.valueOf(education.getGraduationDate());
			statement.setDate(2, date);
			statement.setString(3, education.getInstitutionName());
			statement.setFloat(4, finalGrade);
			statement.setInt(5, edDegreeType(education.getDegree()));
			statement.setInt(6, citizenId);
			statement.registerOutParameter(7, Types.INTEGER);

			statement.executeQuery();

			educationId = statement.getInt("education_id");

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

		return educationId;
	}

}
