CFLAGS = -ggdb
DEFINES = -DDEBUGGA
INCLUDES = 
LIBS = -lstdc++
EXAMPLE = quadrature_rules.o pset.o
CC=g++

.cc.o:
	$(CC) -c $(CFLAGS) $(DEFINES) $(INCLUDES) $<

all::	pset

gnuplot_i.o:	gnuplot_i.hpp
pset.o:	pset.cc
quadrature_rules.o: quadrature_rules.cc

pset: $(EXAMPLE)
	$(CC) -o $@ $(CFLAGS) $(EXAMPLE) $(LIBS)

clean: 
	rm -f $(EXAMPLE) pset_1