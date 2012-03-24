// A component is a single obstacle, made up of a small collection of parts
public class Component extends GameCollection<Part> {

	@Override
	public void tweak() {
		// TODO 
	}

	@Override
	public float rate() {
		float rating = 0;
		for (Part element : elements) {
			rating += element.rate();
		}
		return rating;
	}
	
}

