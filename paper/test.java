import java.io.*;
import java.util.*;

class test {
    public test() {
    }

    private int assign2Atom(int v, int c, int k) {
	return (v-1)*k+c;
    }
    private int atom2Vertex(int a, int k) {
	return (a-1)/k+1;
    }
    private int atom2Color(int a, int k) {
	return (a-1)%k+1;
    }

    private void writeTime(String name, LinkedList<LinkedList<Integer>> time) {
	try {
	    File dotfile = new File(name);
	    FileOutputStream os = null;
	    if(!dotfile.exists()) {
		dotfile.createNewFile();
	    }
	    os = new FileOutputStream(dotfile, false);
	    for(LinkedList<Integer> t : time) {
		os.write(String.format("%d,%d\n",t.get(0),t.get(1)).getBytes());
	    }
	    os.close();
	    os = null;
	    dotfile = null;
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    private LinkedList<String> toGC(LinkedList<LinkedList<Integer>> edge, int vertex, int k) {
	LinkedList<String> clause = new LinkedList<String>();
	Set<Integer> vertexes = new HashSet<Integer>();
	for(int v=1; v<=vertex; v++) {
	    vertexes.add(v);
	}

	// enforce the edge constrains
	for(LinkedList<Integer> e : edge) {
	    for(int c=1; c<=k; c++) {
		clause.add((-1*assign2Atom(e.get(0),c,k))+" "+(-1*assign2Atom(e.get(1),c,k)));
	    }
	}
	// ensure that every vertex is colored
	for(Integer v : vertexes) {
	    StringBuffer str = new StringBuffer("");
	    for(int c=1; c<=k; c++) {
		str.append(assign2Atom(v,c,k)+" ");
	    }
	    str.deleteCharAt(str.length()-1);
	    clause.add(str.toString());
	}
	// ensure that every vertex is uniquely colored
	for(Integer v : vertexes) {
	    for(int c1=1; c1<=k; c1++) {
		for(int c2=1; c2<=k; c2++) {
		    if(c1!=c2) {
			clause.add((-1*assign2Atom(v,c1,k))+" "+(-1*assign2Atom(v,c2,k)));
		    }
		}
	    }
	}
	return clause;
    }

    public static void main(String[] args) {
	test inst = new test();
	LinkedList<LinkedList<Integer>> time = new LinkedList<LinkedList<Integer>>();
	LinkedList<LinkedList<Integer>> edge = new LinkedList<LinkedList<Integer>>();
	LinkedList<Integer> e = new LinkedList<Integer>();
	e.add(1);
	e.add(2);
	edge.add(e);
	int k = 4;
	int vertex = 10;
	for(vertex=10; vertex<=1000; vertex=vertex+10) {
	    long begin = System.nanoTime();
	    LinkedList<String> cnf = inst.toGC(edge,vertex,k);
	    long end = System.nanoTime();
	    LinkedList<Integer> t = new LinkedList<Integer>();
	    t.add(vertex);
	    t.add((int)(end-begin)/1000);
	    time.add(t);
	}
	System.out.println(time);
	inst.writeTime("vertex.txt",time);

	// edge
	time.clear();
	edge.clear();
	vertex = 1000;
	k = 4;
	for(int i=10; i<=1000; i=i+10) {
	    for(int j=1; j<10; j++) {
		e.clear();
		e.add(i-j);
		e.add(i-j+1);
		edge.add(e);
	    }
	    long begin = System.nanoTime();
	    LinkedList<String> cnf = inst.toGC(edge,vertex,k);
	    long end = System.nanoTime();
	    LinkedList<Integer> t = new LinkedList<Integer>();
	    t.add(i);
	    t.add((int)(end-begin)/1000);
	    time.add(t);
	}
	System.out.println(time);
	inst.writeTime("edge.txt",time);

	// color
	time.clear();
	edge.clear();
	for(int i=0; i<10; i++) {
	    e.clear();
	    e.add(i+1);
	    e.add(i+2);
	    edge.add(e);
	}
	vertex = 20;
	for(k=1; k<=100; k=k+1) {
	    long begin = System.nanoTime();
	    LinkedList<String> cnf = inst.toGC(edge,vertex,k);
	    long end = System.nanoTime();
	    LinkedList<Integer> t = new LinkedList<Integer>();
	    t.add(k);
	    t.add((int)(end-begin)/1000);
	    time.add(t);
	}
	System.out.println(time);
	inst.writeTime("k.txt",time);
    }
}
