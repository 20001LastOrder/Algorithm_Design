package encoding;

import java.util.Enumeration;
import java.util.Hashtable;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

public class GraphCreator {
	/*
	 * tree to insert
	 */
	Graph tree;
	
	public GraphCreator(String name, Hashtable<String, String> table, double x, double dy, double horiRatio) {
		tree = new SingleGraph(name);
		//add source node
		Node source = tree.addNode("source");
		source.addAttribute("xy", 0.0, 0.0);
		for(Enumeration<String> e = table.keys(); e.hasMoreElements();) {
			String key = e.nextElement();
			String value = table.get(key);
			Node parent = tree.getNode("source");
			double dx = x;
			for(int i = 1; i < value.length()+1; i++) {
				String encoding = value.substring(0, i);
				Object[] coor = parent.getAttribute("xy");
				double width = (double)coor[0];
				double depth = (double)coor[1];
				dx = dx*horiRatio;
				if(tree.getNode(encoding) == null) {
					//if there is no edge of this prefix, add an edge
					Node n = tree.addNode(encoding);
					if(i == value.length()) {
						n.addAttribute("ui.label", key);
					}
					//put the position depend on the value 0: left child, 1 right child
					if(value.charAt(i-1) == '0') {
						n.addAttribute("xy", width-dx, depth - dy);
					}else {
						n.addAttribute("xy", width+dx, depth - dy);
					}
					Edge edge = tree.addEdge(encoding, parent, n);
					edge.addAttribute("ui.label", value.charAt(i-1));
				}
				//update parent
				parent = tree.getNode(encoding);
			}
			tree.addAttribute("ui.stylesheet", " node {\r\n" + 
			" fill-color: black;" +
			"text-size:20;" +
			"text-color: red;" + 
			"   }"
			+ "edge{\r\n"
			+ "text-color: blue;"
			+ "}");
		}
	}
	
	public void display() {
		Viewer viewer = tree.display(false);
		View view = viewer.getDefaultView();

	}
}
