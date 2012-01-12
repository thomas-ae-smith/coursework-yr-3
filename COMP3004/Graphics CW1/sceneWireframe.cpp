#include <stdio.h>
#include <glm/glm.hpp>
#include <glm/gtc/matrix_transform.hpp>
#include <glm/gtc/type_ptr.hpp>
#include "stylesBase.h"
#include "sceneWireframe.h"
#include "utils.h"


GLuint DrawSceneWireframe::wireshader;

glm::mat4 DrawSceneWireframe::MVPcone;

void DrawSceneWireframe::setupShaders() {
    DrawStyles::setupShaders(); //create a new shaderprogram and attach the first two shaders
    //if (!wireshader) {
        //printf("Create wireframe geometry shader\n");
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

void DrawSceneWireframe::generateConeVertexes(const int divisions) {
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
        cone[v_num].x = 0.5*sin(circ_arc);
        cone[v_num].y = 0.5*cos(circ_arc);
        cone[v_num].z = 0.0;
        v_num++;
        circ_arc+=arc;
        //printf("%d\n", v_num);
    }
    //printf("%d\n", v_num);
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

void DrawSceneWireframe::setupGeometry(const int divisions) {
    //printf("setting up with %d divisions.", divisions);
    int spherevertexes = divisions * divisions;
    sphere = new vertex[spherevertexes];
    generateSphereVertexes(divisions);
    generateConeVertexes(divisions);
    glBufferData ( GL_ARRAY_BUFFER, spherevertexes * sizeof ( vertex ), sphere, GL_STATIC_DRAW );
    glVertexAttribPointer ( ( GLuint ) 0, 3, GL_DOUBLE, GL_FALSE, sizeof ( vertex ), ( const GLvoid* ) 0 );
    glEnableVertexAttribArray(0);
}

void DrawSceneWireframe::render(double delta) {
    //rotate around the model based on delta-time
    MVP = glm::rotate(MVP, (float)delta * 10.0f, glm::vec3(0.1f, 0.2f, 1.f));
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    glUniformMatrix4fv(glGetUniformLocation(shaderprogram, "mvpmatrix"), 1, GL_FALSE, glm::value_ptr(MVP));
    glBufferData ( GL_ARRAY_BUFFER, 2304 * sizeof ( vertex ), sphere, GL_STATIC_DRAW );
    glDrawArrays(GL_TRIANGLE_STRIP, 0, 2304);

    //MVPcone = glm::rotate(MVPcone, (float)delta * -10.0f, glm::vec3(0.f, 0.f, 1.f));
    MVPcone = glm::translate(MVPcone, glm::vec3(0.f, -1.5f, 0.5f));
        MVPcone = glm::rotate(MVPcone, (float)delta * 10.0f, glm::vec3(1.f, 0.f, 0.f));
    MVPcone = glm::translate(MVPcone, glm::vec3(0.f, 1.5f, -0.5f));
    glUniformMatrix4fv(glGetUniformLocation(shaderprogram, "mvpmatrix"), 1, GL_FALSE, glm::value_ptr(MVPcone));
    glBufferData( GL_ARRAY_BUFFER, 192 * sizeof ( vertex ), cone, GL_STATIC_DRAW );
    glDrawArrays(GL_TRIANGLE_STRIP, 0, 192);
 }



void DrawSceneWireframe::setupCamera() {
    glm::mat4 Projection = glm::perspective(45.0f, 1.0f, 0.1f, 100.0f);
    glm::mat4 View = glm::mat4(1.);
    View = glm::translate(View, glm::vec3(0.f, 0.f, -5.0f));
    glm::mat4 Model = glm::mat4(1.0);
    MVPcone = MVP = Projection * View * Model;
    MVPcone = glm::rotate(MVPcone, 90.f, glm::vec3(0.f, 1.f, 0.f));
    //MVPcone = glm::scale(MVPcone, glm::vec3(0.5f, 0.5f, 1.f));
    MVPcone = glm::translate(MVPcone, glm::vec3(0.f, 1.5f, -0.5f));
}

