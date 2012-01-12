#include <stdio.h>
#include <glm/glm.hpp>
#include <glm/gtc/matrix_transform.hpp>
#include <glm/gtc/type_ptr.hpp>
#include "stylesBase.h"
#include "coneWireframe.h"
#include "utils.h"


GLuint DrawConeWireframe::wireshader;

void DrawConeWireframe::setupShaders() {
    DrawStyles::setupShaders(); //create a new shaderprogram and attach the first two shaders
    //if (!wireshader) {
//        printf("Create wireframe geometry shader\n");
        const GLchar* wiresource = filetobuf("sphere_wireframe.geom");
        wireshader = glCreateShader(GL_GEOMETRY_SHADER);
        glShaderSource(wireshader, 1, (const GLchar**)&wiresource, 0);
        glCompileShader(wireshader);
    //} else { printf("Wireframe geometry shader already exists\n"); }
    //attach the custom geometry shader, link the program and set it active
    glAttachShader(shaderprogram, wireshader);
    glLinkProgram(shaderprogram);
    glUseProgram(shaderprogram);
    glUniform3fv(glGetUniformLocation(shaderprogram, "in_Colour"), 1, drawColour);
}

void DrawConeWireframe::generateConeVertexes(const int divisions) {
    const double TAU = 6.283185307179586476924;
    const double arc = TAU/divisions;
        cone = new vertex[divisions * 4];

    int v_num = 0;

    double circ_arc = 0.0;

    // generate the outer ring.
    for (int i = 0; i < divisions; i++) {
        cone[v_num].x = 0.0;
        cone[v_num].y = 0.0;
        cone[v_num].z = 1.0;
        v_num++;
        cone[v_num].x = sin(circ_arc);
        cone[v_num].y = cos(circ_arc);
        cone[v_num].z = 0.0;
        v_num++;
        circ_arc+=arc;
        //printf("%d\n", v_num);
    }
//    printf("%d\n", v_num);
    int step_back = 2 * divisions-1;
    for (int i = 0; i < divisions; i++ ) {
        cone[v_num] = cone[v_num-step_back];
        v_num++;
        cone[v_num].x = 0.0;
        cone[v_num].y = 0.0;
        cone[v_num].z = 0.0;
        v_num++;
    }
//    printf("%d\n", v_num);

}

void DrawConeWireframe::setupGeometry(const int divisions) {
//    printf("setting up with %d divisions.\n", divisions);
    generateConeVertexes(divisions);
//    printf("generated the vertexes\n");
    glBufferData( GL_ARRAY_BUFFER, divisions * 4 * sizeof ( vertex ), cone, GL_STATIC_DRAW );
    glVertexAttribPointer( ( GLuint ) 0, 3, GL_DOUBLE, GL_FALSE, sizeof ( vertex ), ( const GLvoid* ) 0 );
    glEnableVertexAttribArray(0);
}

void DrawConeWireframe::render(double delta) {
    //rotate around the model based on delta-time
    MVP = glm::rotate(MVP, (float)delta * -10.0f, glm::vec3(1.f, 0.f, 0.f));

    glUniformMatrix4fv(glGetUniformLocation(shaderprogram, "mvpmatrix"), 1, GL_FALSE, glm::value_ptr(MVP));
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    glDrawArrays(GL_TRIANGLE_STRIP, 0, 192);
 }

