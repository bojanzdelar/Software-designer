abstract class Person extends BasePerson {

	private String name;

	public String getName() {
 		return this.name; 
	}

	public void setName(String name) {
 		this.name = name; 
	}

	public abstract void sayGoodbye(String language);

}
