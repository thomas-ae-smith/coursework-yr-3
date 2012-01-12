#ifndef SPHERESHADED_H
#define SPHERESHADED_H

#include <GL/glew.h>

class DrawSphereShaded : public DrawSphereWireframe {
    private:
        static GLuint shadedvertexshader;
        static GLuint shadedfragmentshader;
        static GLuint shadedgeometryshader;
        void setupShaders();
        static GLfloat viewerPosition[3];
        static GLfloat reflectance[3];

    protected:
        //all inherited

    public:
        DrawSphereShaded() { /*printf("Creating shaded sphere\n");*/ setupShaders(); setupGeometry(118); setupCamera(); };
        void render(double delta);
};

#endif

