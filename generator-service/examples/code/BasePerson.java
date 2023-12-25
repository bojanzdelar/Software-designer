abstract class BasePerson {

	private int id;

	public int getId() {
 		return this.id; 
	}

	public void setId(int id) {
 		this.id = id; 
	}

	public abstract void sayHelloEnglish();

	public abstract void sayHelloGerman();

}
