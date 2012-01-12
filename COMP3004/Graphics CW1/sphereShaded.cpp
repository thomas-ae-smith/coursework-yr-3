#include <stdio.h>
#include <glm/glm.hpp>
#include <glm/gtc/matrix_transform.hpp>
#include <glm/gtc/type_ptr.hpp>
#include "stylesBase.h"
#include "sphereWireframe.h"
#include "sphereShaded.h"
#include "utils.h"


GLuint DrawSphereShaded::shadedvertexshader;
GLuint DrawSphereShaded::shadedfragmentshader;
GLuint DrawSphereShaded::shadedgeometryshader;
GLfloat DrawSphereShaded::viewerPosition[3] = {  0.0f, 0.0f, -1.0f };
GLfloat DrawSphereShaded::reflectance[3] = {  0.1f, 0.4f, 6.0f };

void DrawSphereShaded::setupShaders() {
     //create the first two shaders if they do not already exist
    //if (!shadedvertexshader) {
//        printf("Create shaded vertex shader\n");
        const GLchar *shadedvertexsource = filetobuf("shaded.vert");
        shadedvertexshader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(shadedvertexshader, 1, (const GLchar**)&shadedvertexsource, 0);
        glCompileShader(shadedvertexshader);
    //} else { printf("Shaded vertex shader already exists\n"); }
    //if (!shadedfragmentshader) {
//        printf("Create shaded fragment shader\n");
        const GLchar* shadedfragmentsource = filetobuf("shaded.frag");
        shadedfragmentshader = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(shadedfragmentshader, 1, (const GLchar**)&shadedfragmentsource, 0);
        glCompileShader(shadedfragmentshader);
    //} else { printf("Shaded fragment shader already exists\n"); }
    //create a new shaderprogram and attach the first two shaders
    shaderprogram = glCreateProgram();
    glAttachShader(shaderprogram, shadedvertexshader);
    glAttachShader(shaderprogram, shadedfragmentshader);
    glBindAttribLocation(shaderprogram, 0, "in_Position");
    glUniform3fv(glGetUniformLocation(shaderprogram, "in_Colour"), 1, drawColour);
    //if (!vbo[0]) {
//        printf("Create vertex buffer object\n");
        glGenBuffers(1, vbo);
        glBindBuffer(GL_ARRAY_BUFFER, vbo[0]);
    //} else { printf("Vertex buffer object already exists\n"); }
    //if (!shadedgeometryshader) {
//        printf("Create shaded geometry shader\n");
        const GLchar* shadedgeometrysource = filetobuf("sphere_shaded.geom");
        shadedgeometryshader = glCreateShader(GL_GEOMETRY_SHADER);
        glShaderSource(shadedgeometryshader, 1, (const GLchar**)&shadedgeometrysource, 0);
        glCompileShader(shadedgeometryshader);
    //} else { printf("Shaded geometry shader already exists\n"); }
    //attatch the custom geometry shader, link the program and set it active
    glAttachShader(shaderprogram, shadedgeometryshader);
    glLinkProgram(shaderprogram);
    glUseProgram(shaderprogram);
    glUniform3fv(glGetUniformLocation(shaderprogram, "in_Colour"), 1, drawColour);
    glUniform3fv(glGetUniformLocation(shaderprogram, "viewer"), 1, viewerPosition);
    glUniform3fv(glGetUniformLocation(shaderprogram, "K"), 1, reflectance);

    char text[1000];
    int length;

    glGetProgramInfoLog(shaderprogram, 1000, &length, text);
    if(length>0)
        fprintf(stderr, "Validate Shader Program\n%s\n",text );
}


void DrawSphereShaded::render(double delta) {
    //rotate around the model based on delta-time
    MVP = glm::rotate(MVP, (float)delta * -10.0f, glm::vec3(1.f, 0.f, 0.f));

    glUniformMatrix4fv(glGetUniformLocation(shaderprogram, "mvpmatrix"), 1, GL_FALSE, glm::value_ptr(MVP));
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    glDrawArrays(GL_TRIANGLE_STRIP, 0, 118 * 118);
 }
