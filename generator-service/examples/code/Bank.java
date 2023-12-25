public class Bank {

	private int id;
	private String name;
	private Customer customer;
	private Teller teller;

	public int getId() {
 		return this.id; 
	}

	public void setId(int id) {
 		this.id = id; 
	}

	public String getName() {
 		return this.name; 
	}

	public void setName(String name) {
 		this.name = name; 
	}

	public Customer getCustomer() {
 		return this.customer; 
	}

	public void setCustomer(Customer customer) {
 		this.customer = customer; 
	}

	public Teller getTeller() {
 		return this.teller; 
	}

	public void setTeller(Teller teller) {
 		this.teller = teller; 
	}

	public class Branch {

		private String address;
		private String phoneNumber;

		public String getAddress() {
 			return this.address; 
		}

		public void setAddress(String address) {
 			this.address = address; 
		}

		public String getPhoneNumber() {
 			return this.phoneNumber; 
		}

		public void setPhoneNumber(String phoneNumber) {
 			this.phoneNumber = phoneNumber; 
		}

		public class Vault {

			private String id;
			private boolean occupied;

			public String getId() {
 				return this.id; 
			}

			public void setId(String id) {
 				this.id = id; 
			}

			public boolean getOccupied() {
 				return this.occupied; 
			}

			public void setOccupied(boolean occupied) {
 				this.occupied = occupied; 
			}

		}

	}

}
