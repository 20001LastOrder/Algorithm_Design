package encoding;

public class Label implements Comparable<Label>{
	public String name;
	public String encoding;
	public double frequency;
	
	public Label(String name, double frequency) {
		this.name = name;
		this.frequency = frequency;
		this.encoding = "";
	}
	
	public void addPrefix(char c) {
		this.encoding = c + this.encoding;
	}
	
	public String toString() {
		return String.format("%s: %f, %s \n", name, frequency, encoding);
	}

	@Override
	public int compareTo(Label l) {
		if (this.frequency == l.frequency) return 0;
		return (this.frequency - l.frequency)<0? -1:1;
	}

}
