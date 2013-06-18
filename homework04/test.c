#include <stdlib.h>
#include <stdio.h>

int main() {
  int i = 0;
  int a = 5;
  int b = 5;
  struct timeval time1, time2, time3;
  gettimeofday(&time1, 0);
  for(i=0; i<100000000; i++) {
    a = a + i;
  }
  gettimeofday(&time2, 0);
  for(i=0; i<100000000; i++) {
    b = b * i;
  }
  gettimeofday(&time3, 0);

  printf("%d, %d\n", (int)(time2.tv_sec-time1.tv_sec), (int)(time3.tv_sec-time2.tv_sec));
  printf("%d, %d\n", (int)(time2.tv_usec-time1.tv_usec), (int)(time3.tv_usec-time2.tv_usec));
  return 0;
}
