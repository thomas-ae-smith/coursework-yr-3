CFLAGS=-I/usr/cslib/include -Wall -g
LDFLAGS=-L/usr/cslib/lib -lglfw -lGL -lXrandr -lGLEW

all: *.cpp 
	g++ -o taes1g09CW3 $^ $(CFLAGS) $(LDFLAGS) 

clean:
	rm -rf *.o taes1g09CW3

.PHONY: all clean
