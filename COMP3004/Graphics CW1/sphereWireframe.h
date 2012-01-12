#ifndef SPHEREWIREFRAME_H
#define SPHEREWIREFRAME_H

#include <GL/glew.h>

class DrawSphereWireframe : public DrawStyles {
    private:
        static GLuint wireshader;
        void setupShaders();

    protected:
        typedef union { GLdouble pos[3]; struct { GLdouble x; GLdouble y; GLdouble z; }; } vertex;
        vertex* sphere;
        void generateSphereVertexes( const int divisions );
        void setupGeometry( const int divisions );

    public:
       DrawSphereWireframe() {};
       DrawSphereWireframe(bool override) { /*printf("Creating wireframe sphere\n");*/ setupShaders(); setupGeometry(12); setupCamera(); };
        ~DrawSphereWireframe() { delete sphere; };
        void render(double delta);
};

#endif
