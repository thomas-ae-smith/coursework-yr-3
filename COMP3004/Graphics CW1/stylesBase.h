#ifndef STYLESBASE_H
#define STYLESBASE_H

#include <glm/glm.hpp>
#include <GL/glew.h>

class DrawStyles {
    protected:
        static GLuint vertexshader, fragmentshader;
        static GLfloat drawColour[3];
        static glm::mat4 MVP;
        GLuint shaderprogram;
        static GLuint vbo[1];
        virtual void setupShaders();
        virtual void setupGeometry( const int divisions ){};
        virtual void setupCamera();

    public:
        DrawStyles() {};
        virtual ~DrawStyles() { /*glDeleteBuffers(1, vbo);*/ };
        virtual void render(double delta) = 0;

};

#endif
