package encoding;

import java.util.ArrayList;

public class HuffmanCollector implements Comparable<HuffmanCollector> {
	public ArrayList<Label> labels;
	public double frequency;
	
	public HuffmanCollector(Label label) {
		labels = new ArrayList<Label>();
		labels.add(label);
		this.frequency = label.frequency;
	}
	
	public void merge(HuffmanCollector h) {
		this.frequency = this.frequency + h.frequency;
		labels.addAll(h.labels);
	}

	public void addPrefix(char c) {
		for(Label label : labels) {
			label.addPrefix(c);
		}
	}
	
	@Override
	public int compareTo(HuffmanCollector a) {
		if (this.frequency == a.frequency) return 0;
		return (this.frequency - a.frequency < 0)? -1: 1;
	}
}