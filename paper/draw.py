import os, sys
import random
import math
from numpy import *
try:
    import pylab
except:
    print "error:\tcan't import pylab module, you must install the module:\n"
    print "\tmatplotlib to plot charts!'\n"

data = [map(int,line.strip().split(',')) for line in open("vertex.txt", 'r')]
pylab.figure()
pylab.plot([x[0] for x in data], [y[1]/1000.0 for y in data], 'rx-')
#pylab.plot([0.0,1.0], [0.0,1.0],'k-.')
#pylab.ylim((0,1))
#pylab.xlim((0,1))
#pylab.xticks(pylab.arange(0,1010,.1))
#pylab.yticks(pylab.arange(0,1,.1))
pylab.grid(True)
#cax = pylab.gca()
#cax.set_aspect('equal')
pylab.xlabel('# of vertex')
pylab.ylabel('time (ms)')
pylab.title('Running Time')

data = [map(int,line.strip().split(',')) for line in open("edge.txt", 'r')]
pylab.figure()
pylab.plot([x[0] for x in data], [y[1]/1000.0 for y in data], 'rx-')
#pylab.plot([0.0,1.0], [0.0,1.0],'k-.')
#pylab.ylim((0,1))
#pylab.xlim((0,1))
#pylab.xticks(pylab.arange(0,1010,.1))
#pylab.yticks(pylab.arange(0,1,.1))
pylab.grid(True)
#cax = pylab.gca()
#cax.set_aspect('equal')
pylab.xlabel('# of edges')
pylab.ylabel('time (ms)')
pylab.title('Running Time')

data = [map(int,line.strip().split(',')) for line in open("k.txt", 'r')]
pylab.figure()
pylab.plot([x[0] for x in data], [y[1]/1000.0 for y in data], 'rx-')
#pylab.plot([0.0,1.0], [0.0,1.0],'k-.')
#pylab.ylim((0,1))
#pylab.xlim((0,1))
#pylab.xticks(pylab.arange(0,1010,.1))
#pylab.yticks(pylab.arange(0,1,.1))
pylab.grid(True)
#cax = pylab.gca()
#cax.set_aspect('equal')
pylab.xlabel('# of colors')
pylab.ylabel('time (ms)')
pylab.title('Running Time')

pylab.show()

