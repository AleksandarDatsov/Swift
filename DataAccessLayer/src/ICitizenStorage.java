
public interface ICitizenStorage {

public int addCitizen(Citizen citizen, int address_id);

public boolean deleteCitizen(int citizenId);

public Citizen getCitizen(int citizen_id);
}
