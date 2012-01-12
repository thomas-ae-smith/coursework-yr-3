#ifndef SCENEWIREFRAME_H
#define SCENEWIREFRAME_H

#include <GL/glew.h>
#include "sphereWireframe.h"

class DrawSceneWireframe : public DrawSphereWireframe {
    private:
        static GLuint wireshader;
        void setupShaders();

    protected:
        static glm::mat4 MVPcone;
        vertex* cone;
        void generateConeVertexes( const int divisions );
        void setupGeometry( const int divisions );
        void setupCamera();

    public:
        DrawSceneWireframe() { /*printf("Creating wireframe scene\n");*/ setupShaders(); setupGeometry(48); setupCamera(); };
        ~DrawSceneWireframe() { /*delete sphere, cone;*/ };
        void render(double delta);
};

#endif


