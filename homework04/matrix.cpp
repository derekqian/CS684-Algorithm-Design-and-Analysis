#include <stdlib.h>
#include <stdio.h>
#include <sys/time.h>

int* brute(int* a, int* b, int n) {
  int* c = new int[n*n];
  for(int i=0; i<n; i++) {
    for(int j=0; j<n; j++) {
      int e = 0;
      for(int k=0; k<n; k++) {
	e += a[i*n+k] * b[k*n+j];
      }
      c[i*n+j] = e;
    }
  }
  return c;
}

int* add(int* a, int* b, int n) {
  int* c = new int[n*n];
  for(int i=0; i<n; i++) {
    for(int j=0; j<n; j++) {
      c[i*n+j] = a[i*n+j] + b[i*n+j];
    }
  }
  return c;
}
int* minus(int* a, int* b, int n) {
  int* c = new int[n*n];
  for(int i=0; i<n; i++) {
    for(int j=0; j<n; j++) {
      c[i*n+j] = a[i*n+j] - b[i*n+j];
    }
  }
  return c;
}
int* strassen(int* a, int* b, int n) {
  int* c = new int[n*n];
  if(n == 1) {
    c[0] = a[0] * b[0];
  } else {
    int* a11 = new int[n/2*n/2];
    int* a12 = new int[n/2*n/2];
    int* a21 = new int[n/2*n/2];
    int* a22 = new int[n/2*n/2];
    int* b11 = new int[n/2*n/2];
    int* b12 = new int[n/2*n/2];
    int* b21 = new int[n/2*n/2];
    int* b22 = new int[n/2*n/2];
    for(int i=0; i<n/2; i++) {
      for(int j=0; j<n/2; j++) {
	a11[i*n/2+j] = a[i*n+j];
	a12[i*n/2+j] = a[i*n+j+n/2];
	a21[i*n/2+j] = a[(i+n/2)*n+j];
	a22[i*n/2+j] = a[(i+n/2)*n+j+n/2];
	b11[i*n/2+j] = b[i*n+j];
	b12[i*n/2+j] = b[i*n+j+n/2];
	b21[i*n/2+j] = b[(i+n/2)*n+j];
	b22[i*n/2+j] = b[(i+n/2)*n+j+n/2];
      }
    }

    int* s1 = minus(b12, b22, n/2);
    int* s2 = add(a11, a12, n/2);
    int* s3 = add(a21, a22, n/2);
    int* s4 = minus(b21, b11, n/2);
    int* s5 = add(a11, a22, n/2);
    int* s6 = add(b11, b22, n/2);
    int* s7 = minus(a12, a22, n/2);
    int* s8 = add(b21, b22, n/2);
    int* s9 = minus(a11, a21, n/2);
    int* s10 = add(b11, b12, n/2);

    int* p1 = strassen(a11, s1, n/2);
    int* p2 = strassen(s2, b22, n/2);
    int* p3 = strassen(s3, b11, n/2);
    int* p4 = strassen(a22, s4, n/2);
    int* p5 = strassen(s5, s6, n/2);
    int* p6 = strassen(s7, s8, n/2);
    int* p7 = strassen(s9, s10, n/2);

    int* temp1 = add(p5, p4, n/2);
    int* temp2 = minus(temp1, p2, n/2);
    int* c11 = add(temp2, p6, n/2);
    int* c12 = add(p1, p2, n/2);
    int* c21 = add(p3, p4, n/2);
    int* temp3 = add(p5, p1, n/2);
    int* temp4 = minus(temp3, p3, n/2);
    int* c22 = minus(temp4, p7, n/2);

    for(int i=0; i<n/2; i++) {
      for(int j=0; j<n/2; j++) {
	c[i*n+j] = c11[i*n/2+j];
	c[i*n+j+n/2] = c12[i*n/2+j];
	c[(i+n/2)*n+j] = c21[i*n/2+j];
	c[(i+n/2)*n+j+n/2] = c22[i*n/2+j];
      }
    }

    delete[] a11;
    delete[] a12;
    delete[] a21;
    delete[] a22;
    delete[] b11;
    delete[] b12;
    delete[] b21;
    delete[] b22;
    delete[] s1;
    delete[] s2;
    delete[] s3;
    delete[] s4;
    delete[] s5;
    delete[] s6;
    delete[] s7;
    delete[] s8;
    delete[] s9;
    delete[] s10;
    delete[] p1;
    delete[] p2;
    delete[] p3;
    delete[] p4;
    delete[] p5;
    delete[] p6;
    delete[] p7;
    delete[] temp1;
    delete[] temp2;
    delete[] temp3;
    delete[] temp4;
    delete[] c11;
    delete[] c12;
    delete[] c21;
    delete[] c22;
  }
  return c;
}

void disp(int* a, int n) {
  for(int i=0; i<n; i++) {
    for(int j=0; j<n; j++) {
      printf("\t%d",a[i*n+j]);
    }
  }
  printf("\n");
}

void testtime() {
  // test the time for addition and multiplication
  int i;
  int aaa = 5;
  printf("%d\n", aaa);
  struct timeval time1, time2, time3;
  gettimeofday(&time1, 0);
  for(i=0; i<1000000; i++) {
    aaa = aaa + i;
  }
  gettimeofday(&time2, 0);
  for(i=0; i<1000000; i++) {
    aaa = aaa * i;
  }
  gettimeofday(&time3, 0);
  printf("%d,%d,%d\n", (int)(time2.tv_usec-time1.tv_usec)/1,(int)(time3.tv_usec-time2.tv_usec)/1,aaa);
}

int main() {
  int i, j;

  int num = 11;
  int* sizes = new int[num];
  int temp = 1;
  for(int i=0; i<num; i++) {
    sizes[i] = temp;
    temp = temp << 1;
  }

  for(int i=0; i<num; i++) {
    struct timeval begin;
    struct timeval end;
    int interval1, interval2;;

    int* c;

    int* a = new int[sizes[i]*sizes[i]];
    int* b = new int[sizes[i]*sizes[i]];

    gettimeofday(&begin, 0);
    c = brute(a, b, sizes[i]);
    delete[] c;
    gettimeofday(&end, 0);
    interval1 = (int)((end.tv_sec - begin.tv_sec) * 1000000 + (end.tv_usec - begin.tv_usec));

    gettimeofday(&begin, 0);
    c = strassen(a, b, sizes[i]);
    delete[] c;
    gettimeofday(&end, 0);
    interval2 = (int)((end.tv_sec - begin.tv_sec) * 1000000 + (end.tv_usec - begin.tv_usec));
    printf("%d, %d, %d, %f\n", sizes[i], interval1, interval2, interval2*1.0/interval1);
  }

  delete[] sizes;

  return 0;
}

