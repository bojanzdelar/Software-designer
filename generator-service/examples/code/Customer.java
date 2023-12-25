public class Customer extends Person {

	private String address;
	private String phoneNum;
	private Account account;
	private Bank bank;

	public String getAddress() {
 		return this.address; 
	}

	public void setAddress(String address) {
 		this.address = address; 
	}

	public String getPhoneNum() {
 		return this.phoneNum; 
	}

	public void setPhoneNum(String phoneNum) {
 		this.phoneNum = phoneNum; 
	}

	public Account getAccount() {
 		return this.account; 
	}

	public void setAccount(Account account) {
 		this.account = account; 
	}

	public Bank getBank() {
 		return this.bank; 
	}

	public void setBank(Bank bank) {
 		this.bank = bank; 
	}

	public void sayHelloEnglish() {
 		// TODO: Must be implemented! 
	}

	public void sayHelloGerman() {
 		// TODO: Must be implemented! 
	}

	public void sayGoodbye() {
 		// TODO: Must be implemented! 
	}

	public boolean generalInquiry() {

	}

	public boolean depositMoney(double amount) {

	}

	public boolean withdrawMoney(double amount) {

	}

	public Account openAccount() {

	}

	public Account closeAccount() {

	}

	public boolean applyForLoan(double amount, int years) {

	}

	public boolean requestCard(String type) {

	}

}
