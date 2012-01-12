#include <stdio.h>
#include <glm/glm.hpp>
#include <glm/gtc/matrix_transform.hpp>
#include <glm/gtc/type_ptr.hpp>
#include "stylesBase.h"
#include "sphereWireframe.h"
#include "sphereNormals.h"
#include "utils.h"


GLuint DrawSphereNormals::normalshader;

void DrawSphereNormals::setupShaders() {
    DrawStyles::setupShaders(); //create a new shaderprogram and attach the first two shaders
    //if (!normalshader) {
//        printf("Create normal geometry shader\n");
        const GLchar* normalsource = filetobuf("sphere_normals.geom");
        normalshader = glCreateShader(GL_GEOMETRY_SHADER);
        glShaderSource(normalshader, 1, (const GLchar**)&normalsource, 0);
        glCompileShader(normalshader);
    //} else { printf("Normal geometry shader already exists\n"); }
    //attatch the custom geometry shader, link the program and set it active
    glAttachShader(shaderprogram, normalshader);
    glLinkProgram(shaderprogram);
    glUseProgram(shaderprogram);
    glUniform3fv(glGetUniformLocation(shaderprogram, "in_Colour"), 1, drawColour);
}

void DrawSphereNormals::setupGeometry( const int divisions ) {
//    printf("setting up with %d divisions.", divisions);
    int spherevertexes = divisions * divisions + 2;
    sphere = new vertex[spherevertexes];
    generateSphereVertexes(divisions);
    sphere[spherevertexes-2] = sphere[spherevertexes-1] = sphere[spherevertexes-3]; //set the last vertex to form a degenerate triangle
    glBufferData( GL_ARRAY_BUFFER, spherevertexes * sizeof ( vertex ), sphere, GL_STATIC_DRAW );
    glVertexAttribPointer ( ( GLuint ) 0, 3, GL_DOUBLE, GL_FALSE, sizeof ( vertex ), ( const GLvoid* ) 0 );
    glEnableVertexAttribArray(0);
}

void DrawSphereNormals::render(double delta) {
    //rotate around the model based on delta-time
    MVP = glm::rotate(MVP, (float)delta * -10.0f, glm::vec3(1.f, 0.f, 0.f));

    glUniformMatrix4fv(glGetUniformLocation(shaderprogram, "mvpmatrix"), 1, GL_FALSE, glm::value_ptr(MVP));
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    glDrawArrays(GL_TRIANGLE_STRIP, 0, (146));
 }

