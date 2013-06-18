import java.io.*;
import java.util.*;

class gc {
    private String graphfile = null;
    private int k;
    private LinkedList<LinkedList<Integer>> graph = null;

    public gc(String graphfile, int k) {
	this.graphfile = graphfile;
	this.k = k;
	this.graph = new LinkedList<LinkedList<Integer>>();
	try {
	    FileInputStream instream = new FileInputStream(graphfile);
	    DataInputStream datain = new DataInputStream(instream);
	    BufferedReader br = new BufferedReader(new InputStreamReader(datain));
	    String strline = null;
	    while((strline = br.readLine()) != null) {
		if(!strline.isEmpty() && strline.charAt(0) == 'e') {
		    String[] strs = strline.substring(2).split(" ");
		    LinkedList<Integer> temp = new LinkedList<Integer>();
		    temp.add(Integer.valueOf(strs[0]));
		    temp.add(Integer.valueOf(strs[1]));
		    this.graph.add(temp);
		}
	    }
	    br.close();
	    datain.close();
	    instream.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    private void writeString(String name, String string, boolean append) {
	try {
	    File dotfile = new File(name);
	    FileOutputStream os = null;
	    if(!dotfile.exists()) {
		dotfile.createNewFile();
	    }
	    os = new FileOutputStream(dotfile, append);
	    os.write(string.getBytes());
	    os.close();
	    os = null;
	    dotfile = null;
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    private int assign2Atom(int v, int c) {
	return (v-1)*k+c;
    }
    private int atom2Vertex(int a) {
	return (a-1)/k+1;
    }
    private int atom2Color(int a) {
	return (a-1)%k+1;
    }
    private void createCNF(String filename) {
	LinkedList<String> clause = new LinkedList<String>();
	Set<Integer> vertexes = new HashSet<Integer>();
	for(LinkedList<Integer> edge : graph) {
	    vertexes.add(edge.get(0));
	    vertexes.add(edge.get(1));
	}

	// enforce the edge constrains
	for(LinkedList<Integer> edge : graph) {
	    for(int c=1; c<=k; c++) {
		clause.add((-1*assign2Atom(edge.get(0),c))+" "+(-1*assign2Atom(edge.get(1),c)));
	    }
	}
	// ensure that every vertex is colored
	for(Integer vertex : vertexes) {
	    StringBuffer str = new StringBuffer("");
	    for(int c=1; c<=k; c++) {
		str.append(assign2Atom(vertex,c)+" ");
	    }
	    str.deleteCharAt(str.length()-1);
	    clause.add(str.toString());
	}
	// ensure that every vertex is uniquely colored
	for(Integer vertex : vertexes) {
	    for(int c1=1; c1<=k; c1++) {
		for(int c2=1; c2<=k; c2++) {
		    if(c1!=c2) {
			clause.add((-1*assign2Atom(vertex,c1))+" "+(-1*assign2Atom(vertex,c2)));
		    }
		}
	    }
	}

	writeString(filename, "p cnf "+(vertexes.size()*k)+" "+clause.size(), false);
	for(String str : clause) {
	    writeString(filename, "\n"+str+" 0", true);
	}
    }
    private void solveCNF(String filename) {
	try {
	    Runtime.getRuntime().exec("./minisat_static "+filename+" "+filename+".out");
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    private LinkedList<LinkedList<Integer>> toGC(String filename) {
	LinkedList<LinkedList<Integer>> coloring = new LinkedList<LinkedList<Integer>>();
	try {
	    FileInputStream instream = new FileInputStream(filename+".out");
	    DataInputStream datain = new DataInputStream(instream);
	    BufferedReader br = new BufferedReader(new InputStreamReader(datain));
	    String strline = null;
	    int state = 0;
	    while((strline = br.readLine()) != null) {
		System.out.println(strline); // weird, does not print
		switch(state) {
		case 0: // init
		    if(strline.equals("SAT")) {
			state = 1;
		    }
		    break;
		case 1: // parse result
		    String[] strs = strline.split(" ");
		    for(String s : strs) {
			int atom = Integer.valueOf(s);
			if(atom > 0) {
			    LinkedList<Integer> temp = new LinkedList<Integer>();
			    temp.add(atom2Vertex(atom));
			    temp.add(atom2Color(atom));
			    coloring.add(temp);
			}
		    }
		    System.out.println(coloring); // weird, does not print
		    state = 2; // put into invalid state
		    break;
		default:
		    break;
		}
	    }
	    br.close();
	    datain.close();
	    instream.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return coloring;
    }

    private void drawGraph(LinkedList<LinkedList<Integer>> coloring) {
	writeString("gc.dot", "graph gc {\n", false);
	for(LinkedList<Integer> col : coloring) {
	    writeString("gc.dot", "node"+col.get(0)+"[label=\""+col.get(0)+" ("+col.get(1)+")\", style=filled, color=\"#"+String.format("%06x",(col.get(1)-1)*0x4f4f4f/(k-1)+0x808080)+"\"];\n", true);
	}
	for(LinkedList<Integer> edge : graph) {
	    writeString("gc.dot", "node"+edge.get(0)+" -- node"+edge.get(1)+";\n", true);
	}
	writeString("gc.dot", "}\n", true);

	try {
	    Runtime.getRuntime().exec("dot -Grankdir=TB -Gratio=0.7727 -Tps -o gc.ps gc.dot");
	    Runtime.getRuntime().exec("evince -w gc.ps");
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    public static void main(String[] args) {
	if(args.length != 2) {
	    System.out.println("Usage:");
	    System.out.println("    java gc graphfile k");
	    return;
	}

	gc inst = new gc(args[0], Integer.valueOf(args[1]));
	inst.createCNF("gc.cnf");
	inst.solveCNF("gc.cnf");
	LinkedList<LinkedList<Integer>> coloring = inst.toGC("gc.cnf");
	if(coloring.size() != 0) {
	    inst.drawGraph(coloring);
	} else {
	    System.out.println("Unsolvable!");
	}
    }
}
