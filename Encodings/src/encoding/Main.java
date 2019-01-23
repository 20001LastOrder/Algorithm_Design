package encoding;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.PriorityQueue;
import java.util.Scanner;

import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

public class Main {
	public static boolean morse = false;
	public static boolean shanno = false;
	public static boolean huffman = true;
	public static void main(String[] args) {
		
		if(shanno) {
			ArrayList<Label> shannonFanoList = loadFrequency();
			Collections.sort(shannonFanoList);
			shannonFano(shannonFanoList, 0, shannonFanoList.size()-1);
			Hashtable<String, String> shannonTable = createEncodingHash(shannonFanoList);
			GraphCreator g = new GraphCreator("shanno", shannonTable, 128, 10, 0.5);
			System.out.println("Shanno: "+ calculateBitsPerSymbol(shannonFanoList));
			generateReport("shanno", shannonFanoList);
			g.display();
			
		}
	
		if(huffman) {
			ArrayList<Label> huffmanList = loadFrequency();
			huffman(huffmanList);
			Hashtable<String, String> huffmanTable = createEncodingHash(huffmanList);
			GraphCreator hg = new GraphCreator("huffman", huffmanTable, 128, 10, 0.5);
			generateReport("huffman", huffmanList);
			hg.display();
			System.out.println("Huffman: "+ calculateBitsPerSymbol(huffmanList));
		}
		

		//morse
		if(morse) {
			ArrayList<Label> mooreList = loadFrequency();

			generateMorseGraph(mooreList);
			System.out.println("Moore: "+ calculateBitsPerSymbol(mooreList));

		}
	}
	
	public static ArrayList<Label> loadFrequency() {
		ArrayList<Label> labels = new ArrayList<Label>();
		File file = new File("newFrequency.txt");
		try {
			@SuppressWarnings("resource")
			Scanner f = new Scanner(file);
			while(f.hasNextLine()) {
				String[] pair = f.nextLine().split(" ");
				Label l = new Label(pair[0], Double.parseDouble(pair[1]));
				labels.add(l);
			}
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		return labels;
	}
	
	public static void huffman(ArrayList<Label> labels) {
		PriorityQueue<HuffmanCollector> queue = new PriorityQueue<HuffmanCollector>();
		for(Label label : labels) {
			queue.add(new HuffmanCollector(label));
		}
		
		while(queue.size() > 1) {
			HuffmanCollector c1 = queue.poll();
			HuffmanCollector c2 = queue.poll();
			c1.addPrefix('0');
			c2.addPrefix('1');
			c1.merge(c2);
			queue.add(c1);
		}
	}
	public static void shannonFano(ArrayList<Label> labels, int start, int end) {
		if(start == end) return;
		double totalFreq = 0;
		for(int i = start; i <=end; i++) {
			totalFreq += labels.get(i).frequency;
		}
		
		double acc = 0;
		int breakIndex = 0;
		for(int i = start; i<= end; i++) {
			acc += labels.get(i).frequency;
			if(acc / totalFreq<0.5) continue;
			//when acc first exceed 0.5
			if(i-1>=start) {
				//get frequency of the last index which is smaller the 0.5
				double lastAcc = acc - labels.get(i).frequency;
				if(Math.abs(lastAcc / totalFreq-0.5) <= Math.abs(acc / totalFreq-0.5)) {
					breakIndex = i-1;
					break;
				}
			}
			//assign the break point to i
			breakIndex = i;
			//find the divide point, no need to continue searching
			break;
		}
		shannonFano(labels, start, breakIndex);
		shannonFano(labels, breakIndex+1, end);
		//add prefix
		addFreq(labels, start, breakIndex, '0');
		addFreq(labels, breakIndex+1, end, '1');
	}
	
	public static void addFreq(ArrayList<Label> labels, int start, int end, char c) {
		for(int i = start; i <= end; i++) {
			labels.get(i).addPrefix(c);
		}
	}
	
	public static Hashtable<String, String> createEncodingHash(ArrayList<Label> labels){
		Hashtable<String, String> table = new Hashtable<String,String>();
		for(Label label : labels) {
			table.put(label.name, label.encoding);
		}
		return table;
	}
	
	public static double calculateBitsPerSymbol(ArrayList<Label> labels) {
		double acc = 0;
		for(Label l : labels) {
			//System.out.println(l.name+": "+l.encoding.length());
			acc += l.frequency * l.encoding.length();
		}
		return acc;
	}
	
	public static void generateReport(String name, ArrayList<Label> labels) {
		PrintStream system = System.out;
		try {
			PrintStream out = new PrintStream(new File(name+".txt"));
			System.setOut(out);
			for(Label l : labels) {
				System.out.printf("%s %f %s%n", l.name, l.frequency, l.encoding);
			}
			System.setOut(system);
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void generateMorseGraph(ArrayList<Label> labels) {
		Hashtable<String, String> table = new Hashtable<String,String>();
		File file1 = new File("Morse.txt");
		try {
			@SuppressWarnings("resource")
			Scanner f = new Scanner(file1);
			int i = 0;
			while(f.hasNextLine()) {
				String[] pairs = f.nextLine().split(" ");
				labels.get(i++).encoding = pairs[1];
				table.put(pairs[0], pairs[1]);
			}
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		GraphCreator g = new GraphCreator("moore", table, 40, 3, 0.7);
		g.display();
	}
}
