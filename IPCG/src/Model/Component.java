package Model;
// A component is a single obstacle, made up of a small collection of parts
public abstract class Component extends GameCollection<Part> {

	@Override
	public void tweak() {
		// TODO 
	}

	@Override
	public int rate() {
		int rating = 0;
		for (Part element : elements) {
			rating += element.rate();
		}
		return rating;
	}

}

