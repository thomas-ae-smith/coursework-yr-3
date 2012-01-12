#ifndef CONEWIREFRAME_H
#define CONEWIREFRAME_H

#include <GL/glew.h>

//#define DIVISIONS 118        //expose to change easily

class DrawConeWireframe : public DrawStyles {
    private:
        static GLuint wireshader;
        void setupShaders();

    protected:
        typedef union { GLdouble pos[3]; struct { GLdouble x; GLdouble y; GLdouble z; }; } vertex;
        vertex* cone;
        void generateConeVertexes( const int divisions );
        void setupGeometry( const int divisions );

    public:
       DrawConeWireframe() { /*printf("Creating wireframe cone\n"); */setupShaders(); setupGeometry(48); setupCamera(); };
        ~DrawConeWireframe() { delete cone; };
        void render(double delta);
};

#endif

