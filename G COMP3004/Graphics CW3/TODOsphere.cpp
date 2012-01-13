// This is terrible code, largely hacekd together from various bits of my CW1

#include "TODOsphere.h"
#include <glm/glm.hpp>
#include <glm/gtc/matrix_transform.hpp>
#include <glm/gtc/type_ptr.hpp>
#include <stdio.h>


TODOsphere::TODOsphere(abstractObject* parent, float offset) : gameObject(parent) {
// GLuint TODOsphere::vertexshader, TODOsphere::fragmentshader;
// GLfloat TODOsphere::drawColour[3] = {  1.0f, 1.0f, 1.0f };
drawColour = {  1.0f, 1.0f, 1.0f };
// glm::mat4 TODOsphere::MVP;
// GLuint TODOsphere::vbo[1];
// GLfloat TODOsphere::viewerPosition[3] = {  0.0f, 0.0f, -1.0f };
// GLfloat TODOsphere::reflectance[3] = {  0.1f, 0.4f, 6.0f };
viewerPosition = {  0.0f, 0.0f, -1.0f };
reflectance = {  0.1f, 0.4f, 6.0f };
// GLuint TODOsphere::wireshader;

	int divisions = 12;
//setupshaders
	// DrawStyles::setupShaders(); //create a new shaderprogram and attach the first two shaders
	//create the first two shaders if they do not already exist
	//if (!vertexshader) {
	if (offset > 0) {
		shaderprogram = shaders->getShader("shaded.vert", "shaded.frag", "sphere_shaded.geom");
	} else {
		shaderprogram = shaders->getShader("base.vert", "base.frag", "sphere_wireframe.geom");        
	}
	
	//if (!vbo[0]) {
		printf("Create vertex buffer object\n");
		glGenBuffers(1, vbo);
		glBindBuffer(GL_ARRAY_BUFFER, vbo[0]);
	//} else { printf("Vertex buffer object already exists\n"); }
	//if (!wireshader) {
	
	glBindAttribLocation(shaderprogram, 0, "in_Position");
	glUniform3fv(glGetUniformLocation(shaderprogram, "in_Colour"), 1, drawColour);
	glUseProgram(shaderprogram);
	glUniform3fv(glGetUniformLocation(shaderprogram, "in_Colour"), 1, drawColour);

	glUniform3fv(glGetUniformLocation(shaderprogram, "viewer"), 1, viewerPosition);
	glUniform3fv(glGetUniformLocation(shaderprogram, "K"), 1, reflectance);

//setupgeometry
	int spherevertexes = divisions * divisions;
	sphere = new vertex[spherevertexes];
	// generateSphereVertexes(divisions);



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
		printf("vertexes count: %d\n", v_num);
	}








	glBufferData ( GL_ARRAY_BUFFER, spherevertexes * sizeof ( vertex ), sphere, GL_STATIC_DRAW );
	glVertexAttribPointer ( ( GLuint ) 0, 3, GL_DOUBLE, GL_FALSE, sizeof ( vertex ), ( const GLvoid* ) 0 );
	glEnableVertexAttribArray(0);




	// void DrawStyles::setupCamera() {
	glm::mat4 Projection = glm::perspective(45.0f, 1.0f, 0.1f, 100.0f);
	glm::mat4 View = glm::mat4(1.);
	View = glm::translate(View, glm::vec3(0.f, 0.f, -5.0f));
	glm::mat4 Model = glm::mat4(1.0);
	Model = glm::translate(Model, glm::vec3(offset, 0.f, 0.f));
	MVP = Projection * View * Model;
}

void TODOsphere::update(double t) {};

void TODOsphere::render() {
	//rotate around the model based on delta-time
	//MVP = glm::rotate(MVP, (float)delta * -10.0f, glm::vec3(1.f, 0.f, 0.f));
	glUseProgram(shaderprogram);
	glUniformMatrix4fv(glGetUniformLocation(shaderprogram, "mvpmatrix"), 1, GL_FALSE, glm::value_ptr(MVP));
	// glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	glDrawArrays(GL_TRIANGLE_STRIP, 0, (144));
 }