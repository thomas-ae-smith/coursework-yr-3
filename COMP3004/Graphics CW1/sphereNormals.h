#ifndef SPHERENORMALS_H
#define SPHERENORMALS_H

#include <GL/glew.h>

class DrawSphereNormals : public DrawSphereWireframe {
    private:
        static GLuint normalshader;
        void setupShaders();
        void setupGeometry( const int divisions );

    protected:
        //all inherited

    public:
        DrawSphereNormals() { /*printf("Creating sphere with normals\n"); */setupShaders(); setupGeometry(12); setupCamera(); };
        void render(double delta);
};

#endif

