import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class FiReader {
	String[] lineSeparator;

	public Citizen newCitizen(String[] str) {

		return new Citizen(str[0], str[1], str[2], checkGender(str[3]), Integer.parseInt(str[5]),
				convertStringToLocalDate(str[4]));
	}

	public Gender checkGender(String strGender) {

		if (strGender.equalsIgnoreCase("M")) {
			return Gender.Male;
		}
		return Gender.Female;
	}

	public Address newAddress(String[] str, boolean isShortAddress) {
		if (isShortAddress) {

			return new Address(str[6], str[7], str[8], str[9], str[10], str[11]);
		}
		return new Address(str[6], str[7], str[8], str[9], str[10], str[11], Integer.parseInt(str[12]),
				Integer.parseInt(str[13]));
	}

	public boolean isGraduated(String graduatedDate) {

		if (convertStringToLocalDate(graduatedDate).isBefore(LocalDate.now())) {
			return true;
		}
		return false;
	}

	// educationTypes: 1-Primary 2-Secondary 3-Bachelor 4-Master 5-Doctorate
	public Education newEducation(String[] strArr, int educationType) {
		Education education;
		GradedEducation gradedEd;
		if (educationType == 1) {
			// strArr[14],
			return education = new PrimaryEducation(strArr[15], convertStringToLocalDate(strArr[16]),
					convertStringToLocalDate(strArr[17]));
		}
		if (educationType == 2) {
			gradedEd = new SecondaryEducation(strArr[19], convertStringToLocalDate(strArr[20]),
					convertStringToLocalDate(strArr[21]));
			if (isGraduated(strArr[21])) {
				gradedEd.finalGrade = Float.parseFloat(strArr[22]);
			}
			return gradedEd;
		}
		if (educationType == 3) {

			gradedEd = new HigherEducation(strArr[24], convertStringToLocalDate(strArr[25]),
					convertStringToLocalDate(strArr[26]), EducationDegree.Bachelor);
			if (isGraduated(strArr[26])) {
				gradedEd.finalGrade = Float.parseFloat(strArr[27]);
			}
			return gradedEd;
		}
		if (educationType == 4) {
			return null;
		}
		if (educationType == 5) {
			return null;
		}
		return null;
	}

	public Education addingDataToEducation(String[] strArr) {
		if (strArr.length > 14) {
			return new Education(strArr[15], convertStringToLocalDate(strArr[16]),
					convertStringToLocalDate(strArr[17])) {

				@Override
				public EducationDegree getDegree() {
					switch (strArr[14]) {
					case "P":
						return EducationDegree.Primary;
					case "S":
						return EducationDegree.Secondary;
					case "B":
						return EducationDegree.Bachelor;
					case "M":
						return EducationDegree.Master;
					case "D":
						return EducationDegree.Doctorate;
					default:
						return EducationDegree.Primary;
					}
				}
			};
		}
		return null;

	}

	// public Queue<String> separateSocialInsuranceData(Queue<String> queue, int
	// index) {
	// if (index > 0) {
	// queue.remove(0);
	// queue.remove(0);
	// queue.remove(0);
	// }
	// return queue;
	// }

	public Queue<String> separateStringsAndAddThemToArrayList(String insuranceLine) {
		String[] sepData = insuranceLine.split(";");
		Queue<String> queue = new LinkedList<>();
		for (String s : sepData) {
			queue.add(s);
		}
		return queue;
	}

	public int returnNumberOfIteration(String[] str) {
		if (str.length == 28) {
			return 3;
		}
		if (str.length == 23) {
			return 2;
		}
		if (str.length == 18) {
			return 1;
		}
		return 0;
	}

	public LocalDate convertStringToLocalDate(String date) {

		LocalDate dateOfBirth = LocalDate.parse(date, DateTimeFormatter.ofPattern("d.M.yyyy"));
		return dateOfBirth;
	}

	public ArrayList<String> readInfo() {
		ArrayList<String> arList = new ArrayList<>();

		String thisLine = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\frog\\Desktop\\in_10"));
			while ((thisLine = br.readLine()) != null) {
				// System.out.println(thisLine);
				arList.add(thisLine);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arList;
	}

	public boolean isInteger(String str) {
		try {
			Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
		return true;
	}
}
