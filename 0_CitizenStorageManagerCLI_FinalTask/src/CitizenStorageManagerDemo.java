import java.util.ArrayList;
import java.util.Queue;

public class CitizenStorageManagerDemo {
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();

		FiReader fiReader = new FiReader();
		MySqlCitizenStorage mCitizenStorage = new MySqlCitizenStorage();
		MySqlAddressStorage mySqlAddressStorage = new MySqlAddressStorage();
		MySqlEducationStorage mySqlEducationStorage = new MySqlEducationStorage();
		MySqlSocialInsuranceStorage mySqlSocialInsuranceStorage = new MySqlSocialInsuranceStorage();

		ArrayList<Citizen> citizens = new ArrayList<>();
		ArrayList<String> arList = fiReader.readInfo();
		Queue<String> sInsurance;
		Citizen citizen;
		Address address = null;
		SocialInsuranceRecord sInsuranceRecord;
		Education education;

		String[] splitLine;
		int citizenId;
		for (int indexOfFirstL = 1, indexOfSecondL = 2; indexOfFirstL < arList
				.size(); indexOfFirstL += 2, indexOfSecondL += 2) {
			splitLine = arList.get(indexOfFirstL).split(";");

			if (splitLine.length > 12) {
				if (!(fiReader.isInteger(splitLine[12]))) {
					address = fiReader.newAddress(splitLine, true);
				} else {
					address = fiReader.newAddress(splitLine, false);
				}
			}
			citizen = fiReader.newCitizen(splitLine);
			for (int indexDegree = 1; indexDegree < fiReader.returnNumberOfIteration(splitLine) + 1; indexDegree++) {
				education = fiReader.newEducation(splitLine, indexDegree);
				citizen.addEducation(education);
			}
			citizen.setAddress(address);
			citizens.add(citizen);
			sInsurance = fiReader.separateStringsAndAddThemToArrayList(arList.get(indexOfSecondL));
			int index = sInsurance.size() / 3;
			for (int i = 0; i < index; i++) {
				sInsuranceRecord = new SocialInsuranceRecord(Integer.parseInt(sInsurance.poll()),
						Integer.parseInt(sInsurance.poll()), Double.parseDouble(sInsurance.poll()));
				citizens.get(indexOfSecondL / 2 - 1).addSocialInsuranceRecord(sInsuranceRecord);
			}
		}
		int address_id;
		for (int i = 0; i < citizens.size(); i++) {
			address_id = mySqlAddressStorage.addAddress(citizens.get(i).getAddress(),
					citizens.get(i).getAddress().getApartmentNo());
			citizenId = mCitizenStorage.addCitizen(citizens.get(i), address_id);
			
			for (int j = 0; j < citizens.get(i).getEducations().size(); j++) {
				mySqlEducationStorage.addEducationToDB(citizens.get(i).getEducations().get(j), citizenId);
			}
			for (int j = 0; j < citizens.get(i).getSocialInsuranceRecords().size(); j++) {
				mySqlSocialInsuranceStorage.addSocialInsurance(citizens.get(i).getSocialInsuranceRecords().get(j),
						citizenId);
			}
		}
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println(totalTime);
	}
}