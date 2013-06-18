import java.util.*;

class matrix {
    int[][] brute(int[][] a, int[][] b) {
	int n = a.length;
	int[][] c = new int[n][n];
	for(int i=0; i<n; i++) {
	    for(int j=0; j<n; j++) {
		int e = 0;
		for(int k=0; k<n; k++) {
		    e += a[i][k] * b[k][j];
		}
		c[i][j] = e;
	    }
	}
	return c;
    }

    int[][] add(int[][] a, int[][] b) {
	int n = a.length;
	int[][] c = new int[n][n];
	for(int i=0; i<n; i++) {
	    for(int j=0; j<n; j++) {
		c[i][j] = a[i][j] + b[i][j];
	    }
	}
	return c;
    }
    int[][] minus(int[][] a, int[][] b) {
	int n = a.length;
	int[][] c = new int[n][n];
	for(int i=0; i<n; i++) {
	    for(int j=0; j<n; j++) {
		c[i][j] = a[i][j] - b[i][j];
	    }
	}
	return c;
    }
    int[][] strassen(int[][] a, int[][] b) {
	int n = a.length;
	int[][] c = new int[n][n];
	if(n == 1) {
	    c[0][0] = a[0][0] * b[0][0];
	} else {
	    int[][] a11 = new int[n/2][n/2];
	    int[][] a12 = new int[n/2][n/2];
	    int[][] a21 = new int[n/2][n/2];
	    int[][] a22 = new int[n/2][n/2];
	    int[][] b11 = new int[n/2][n/2];
	    int[][] b12 = new int[n/2][n/2];
	    int[][] b21 = new int[n/2][n/2];
	    int[][] b22 = new int[n/2][n/2];
	    for(int i=0; i<a11.length; i++) {
		for(int j=0; j<a11.length; j++) {
		    a11[i][j] = a[i][j];
		    a12[i][j] = a[i][j+n/2];
		    a21[i][j] = a[i+n/2][j];
		    a22[i][j] = a[i+n/2][j+n/2];
		    b11[i][j] = b[i][j];
		    b12[i][j] = b[i][j+n/2];
		    b21[i][j] = b[i+n/2][j];
		    b22[i][j] = b[i+n/2][j+n/2];
		}
	    }

	    int[][] s1 = minus(b12, b22);
	    int[][] s2 = add(a11, a12);
	    int[][] s3 = add(a21, a22);
	    int[][] s4 = minus(b21, b11);
	    int[][] s5 = add(a11, a22);
	    int[][] s6 = add(b11, b22);
	    int[][] s7 = minus(a12, a22);
	    int[][] s8 = add(b21, b22);
	    int[][] s9 = minus(a11, a21);
	    int[][] s10 = add(b11, b12);

	    int[][] p1 = strassen(a11, s1);
	    int[][] p2 = strassen(s2, b22);
	    int[][] p3 = strassen(s3, b11);
	    int[][] p4 = strassen(a22, s4);
	    int[][] p5 = strassen(s5, s6);
	    int[][] p6 = strassen(s7, s8);
	    int[][] p7 = strassen(s9, s10);

	    int[][] c11 = add(minus(add(p5, p4), p2), p6);
	    int[][] c12 = add(p1, p2);
	    int[][] c21 = add(p3, p4);
	    int[][] c22 = minus(minus(add(p5, p1), p3), p7);

	    for(int i=0; i<c11.length; i++) {
		for(int j=0; j<c11.length; j++) {
		    c[i][j] = c11[i][j];
		    c[i][j+n/2] = c12[i][j];
		    c[i+n/2][j] = c21[i][j];
		    c[i+n/2][j+n/2] = c22[i][j];
		}
	    }
	}
	return c;
    }

    void disp(int[][] a) {
	int n = a.length;
	for(int i=0; i<n; i++) {
	    for(int j=0; j<n; j++) {
		System.out.print("\t"+a[i][j]);
	    }
	}
	System.out.println();
    }

    public static void main(String[] args) {
	matrix m = new matrix();

	// test the two methods
	int[][] aa = new int[][]{{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,16}};
	int[][] bb = new int[][]{{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,16}};
	int[][] cc = null;
	m.disp(aa);
	m.disp(bb);
    	//cc = m.brute(aa, bb);
	//m.disp(cc);
	cc = m.strassen(aa, bb);
	m.disp(cc);

	// test the time for addition and multiplication
	int aaa = (int)(Math.random() * 100);
	int bbb = (int)(Math.random() * 100);
	long time1 = System.nanoTime();
	for(int i=0; i<1000; i++)aaa = aaa * i;
	long time2 = System.nanoTime();
	for(int i=0; i<1000; i++)bbb = bbb + i;
	long time3 = System.nanoTime();
	System.out.println((time2-time1)/1+","+(time3-time2)/1+","+aaa);

	//int num = 11;
	int num = 8;
	int[] sizes = new int[num];
	int temp = 1;
	for(int i=0; i<num; i++) {
	    sizes[i] = temp;
	    temp = temp << 1;
	}

	for(int i=0; i<num; i++) {
	    int[][] c = null;

	    int[][] a = new int[sizes[i]][sizes[i]];
	    int[][] b = new int[sizes[i]][sizes[i]];

	    //r.disp(a);
	    long begin = System.nanoTime();
	    c = m.brute(a, b);
	    long end = System.nanoTime();
	    long time = (end - begin) / 1000;
	    //long ratio = (long)((sizes[i]*Math.log(sizes[i]))/(double)time);
	    System.out.println(sizes[i] + ", " + time);
	    //r.disp(a);

	    begin = System.nanoTime();
	    c = m.strassen(a, b);
	    end = System.nanoTime();
	    time = (end - begin) / 1000;
	    //long ratio = (long)((sizes[i]*Math.log(sizes[i]))/(double)time);
	    System.out.println(sizes[i] + ", " + time);
	}
	return;
    }
}