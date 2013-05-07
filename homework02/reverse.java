import java.util.*;

class reverse {
    void rev(int[] a, int p, int q) {
	int n = q-p+1;
	if(n <= 1) {
	    return;
	}
	int m1 = n/2 -1;
	int m2 = m1 + 1;
	if(n%2 == 1) {
	    m2 = m2 + 1;
	}
	rev(a, p, p+m1);
	rev(a, p+m2, q);
	for(int i=0; i<=m1; i++) {
	    int temp = a[p+i];
	    a[p+i] = a[p+m2+i];
	    a[p+m2+i] = temp;
	}
    }
    void disp(int[] a) {
	int i;
	System.out.print("dump: ");
	for(i=0; i<a.length; i++){
	    System.out.print(a[i]+", ");
	}
	System.out.println();
    }
    public static void main(String[] args) {
	reverse r = new reverse();

	int[] a = new int[]{};
	r.disp(a);
	r.rev(a,-1,-1);
	r.disp(a);

	a = new int[]{1};
	r.disp(a);
	r.rev(a,0,0);
	r.disp(a);

	a = new int[]{1,2};
	r.disp(a);
	r.rev(a,0,1);
	r.disp(a);

	a = new int[]{1,2,3};
	r.disp(a);
	r.rev(a,0,2);
	r.disp(a);

	a = new int[]{1,2,3,4,5,6,7,8,9};
	r.disp(a);
	r.rev(a,0,8);
	r.disp(a);

	int num = 26;
	int[] sizes = new int[num];
	int temp = 1<<1;
	for(int i=0; i<num; i++) {
	    sizes[i] = temp;
	    temp = temp << 1;
	}

	for(int i=0; i<num; i++) {
	    a = new int[sizes[i]];

	    //r.disp(a);
	    long begin = System.nanoTime();
	    r.rev(a, 0, a.length-1);
	    long end = System.nanoTime();
	    long time = (end - begin) / 1000;
	    long ratio = (long)((sizes[i]*Math.log(sizes[i]))/(double)time);
	    System.out.println(sizes[i] + ", " + time + ", " + ratio);
	    //r.disp(a);
	}
	return;
    }
}