CFLAGS=-I/usr/local/include -I/usr/include -Wall -g
LDFLAGS=-L/usr/local/lib -L/usr/lib -lglfw -framework OpenGL -lGLEW

all: *.cpp 
	g++ -o taes1g09CW3 $^ $(CFLAGS) $(LDFLAGS) 

clean:
	rm -rf *.o taes1g09CW3

.PHONY: all clean
