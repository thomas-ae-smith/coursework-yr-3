#include <stdio.h>
#include <glm/glm.hpp>
#include <glm/gtc/matrix_transform.hpp>
#include "stylesBase.h"
#include "utils.h"

GLuint DrawStyles::vertexshader, DrawStyles::fragmentshader;
GLfloat DrawStyles::drawColour[3] = {  1.0f, 1.0f, 1.0f };
glm::mat4 DrawStyles::MVP;
GLuint DrawStyles::vbo[1];


void DrawStyles::setupShaders() {
    //create the first two shaders if they do not already exist
    //if (!vertexshader) {
        //printf("Create vertex shader\n");
        const GLchar *vertexsource = filetobuf("base.vert");
        vertexshader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexshader, 1, (const GLchar**)&vertexsource, 0);
        glCompileShader(vertexshader);
    //} else { printf("Vertex shader already exists\n"); }
    //if (!fragmentshader) {
        //printf("Create fragment shader\n");
        const GLchar* fragmentsource = filetobuf("base.frag");
        fragmentshader = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentshader, 1, (const GLchar**)&fragmentsource, 0);
        glCompileShader(fragmentshader);
    //} else { printf("Fragment shader already exists\n"); }
    //create a new shaderprogram and attach the first two shaders
    shaderprogram = glCreateProgram();
    glAttachShader(shaderprogram, vertexshader);
    glAttachShader(shaderprogram, fragmentshader);
    glBindAttribLocation(shaderprogram, 0, "in_Position");
    glUniform3fv(glGetUniformLocation(shaderprogram, "in_Colour"), 1, drawColour);
    //if (!vbo[0]) {
        //printf("Create vertex buffer object\n");
        glGenBuffers(1, vbo);
        glBindBuffer(GL_ARRAY_BUFFER, vbo[0]);
    //} else { printf("Vertex buffer object already exists\n"); }
}

void DrawStyles::setupCamera() {
    glm::mat4 Projection = glm::perspective(45.0f, 1.0f, 0.1f, 100.0f);
    glm::mat4 View = glm::mat4(1.);
    View = glm::translate(View, glm::vec3(0.f, 0.f, -5.0f));
    glm::mat4 Model = glm::mat4(1.0);
    MVP = Projection * View * Model;
}
