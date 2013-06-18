all : 
	javac gc.java
	java gc test.col 4

paper :
	pdflatex paper.tex
	bibtex paper
	pdflatex paper.tex
	pdflatex paper.tex

eval : 
	javac test.java
	java -Djava.compiler=NONE test