
public interface IAddressStorage {

	public boolean removeAddress(int citizenId);

	public int addAddress(Address newAddress, Object citizenApartmentNo);

	public boolean changeAddress(Address newAddress, int citizenId);

}
