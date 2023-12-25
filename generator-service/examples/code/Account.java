abstract class Account implements Closeable {

	private int id;
	private Customer customer;

	public int getId() {
 		return this.id; 
	}

	public void setId(int id) {
 		this.id = id; 
	}

	public Customer getCustomer() {
 		return this.customer; 
	}

	public void setCustomer(Customer customer) {
 		this.customer = customer; 
	}

	public abstract String getIdentifier();

}
