#include <stdio.h>
#include <glm/glm.hpp>
#include <glm/gtc/matrix_transform.hpp>
#include <glm/gtc/type_ptr.hpp>
#include "stylesBase.h"
#include "sphereWireframe.h"
#include "utils.h"


GLuint DrawSphereWireframe::wireshader;

void DrawSphereWireframe::setupShaders() {
    DrawStyles::setupShaders(); //create a new shaderprogram and attach the first two shaders
    //if (!wireshader) {
//        printf("Create wireframe geometry shader\n");
        const GLchar* wiresource = filetobuf("sphere_wireframe.geom");
        wireshader = glCreateShader(GL_GEOMETRY_SHADER);
        glShaderSource(wireshader, 1, (const GLchar**)&wiresource, 0);
        glCompileShader(wireshader);
    //} else { printf("Wireframe geometry shader already exists\n"); }
    //attatch the custom geometry shader, link the program and set it active
    glAttachShader(shaderprogram, wireshader);
    glLinkProgram(shaderprogram);
    glUseProgram(shaderprogram);
    glUniform3fv(glGetUniformLocation(shaderprogram, "in_Colour"), 1, drawColour);
}

void DrawSphereWireframe::generateSphereVertexes(const int divisions) {
    const double TAU = 6.283185307179586476924;
    const double arc = TAU/divisions;
    if (sphere == NULL)
        sphere = new vertex[divisions * divisions];

    int v_num = 0;

    double circ_arc = 0.0;
    double height_arc = arc;

    GLdouble z = cos(height_arc);
    double r = sin(height_arc);
    // generate the first ring. These can't look back, and are each duplicated
    for ( int i = 0; i < divisions; i++) {
        sphere[v_num].x = 0.0;
        sphere[v_num].y = 0.0;
        sphere[v_num].z = 1.0;
        v_num++;
        sphere[v_num].x = r*sin(circ_arc);
        sphere[v_num].y = r*cos(circ_arc);
        sphere[v_num].z = z;
        v_num++;
        circ_arc+=arc;
        //printf("%d\n", v_num);
    }
    // generate the remaining rings, looking back to avoid recalculating vertex positions
    int step_back;
    for ( int i = 1; i < divisions/2; i++) {
        height_arc += arc;
        z = cos(height_arc);
        r = sin(height_arc);
        circ_arc = 0.0;
        for ( int j = 0; j < divisions; j++ ) {
            step_back = 2 * divisions-1;
            sphere[v_num] = sphere[v_num-step_back];
            v_num++;
            sphere[v_num].x = r*sin(circ_arc);
            sphere[v_num].y = r*cos(circ_arc);
            sphere[v_num].z = z;
            v_num++;
            circ_arc+=arc;
        }
        //printf("vertexes count: %d\n", v_num);
    }
}

void DrawSphereWireframe::setupGeometry(const int divisions) {
//    printf("setting up with %d divisions.", divisions);
    int spherevertexes = divisions * divisions;
    sphere = new vertex[spherevertexes];
    generateSphereVertexes(divisions);
    glBufferData ( GL_ARRAY_BUFFER, spherevertexes * sizeof ( vertex ), sphere, GL_STATIC_DRAW );
    glVertexAttribPointer ( ( GLuint ) 0, 3, GL_DOUBLE, GL_FALSE, sizeof ( vertex ), ( const GLvoid* ) 0 );
    glEnableVertexAttribArray(0);
}

void DrawSphereWireframe::render(double delta) {
    //rotate around the model based on delta-time
    MVP = glm::rotate(MVP, (float)delta * -10.0f, glm::vec3(1.f, 0.f, 0.f));

    glUniformMatrix4fv(glGetUniformLocation(shaderprogram, "mvpmatrix"), 1, GL_FALSE, glm::value_ptr(MVP));
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    glDrawArrays(GL_TRIANGLE_STRIP, 0, (144));
 }
