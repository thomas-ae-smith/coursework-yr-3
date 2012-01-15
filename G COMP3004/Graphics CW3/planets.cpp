#include "planets.h"
#include "behaviours.h"
#include <glm/gtc/type_ptr.hpp>	//TODO take a look at why this is here
#include <stdio.h>


planet::planet(abstractObject* parent, float size) : gameObject(parent) {
// drawColour = {  1.0f, 1.0f, 1.0f };
// viewerPosition = {  0.0f, 0.0f, -1.0f };
// reflectance = {  0.1f, 0.4f, 6.0f };
	shaderprogram = shaders->getShader("base.vert", "base.frag", "sphere_wireframe.geom");        
		glGenBuffers(1, vbo);
		glBindBuffer(GL_ARRAY_BUFFER, vbo[0]);
	
	glBindAttribLocation(shaderprogram, 0, "in_Position");
	glUseProgram(shaderprogram);

	model = new vertex[3];
	for (int i = 0; i < 3; ++i)
	{
		model[i][i] = 1.f;
	}
	// glBufferData ( GL_ARRAY_BUFFER, 3 * sizeof ( vertex ), model, GL_STATIC_DRAW );
	// glVertexAttribPointer ( ( GLuint ) 0, 3, GL_DOUBLE, GL_FALSE, sizeof ( vertex ), ( const GLvoid* ) 0 );
	// glEnableVertexAttribArray(0);


	this->size = size;
	loc = new staticBehaviour(this);

	// loc = new orbitBehaviour(this, (gameObject*)parent, 0.f, init, -init*10.f);
	// v.rot = init*5.f;

	// MVP = Projection * View * Model;
	printf("Created a planet.\n");
}

planet::~planet() {
	printf("Deleted a planet.\n");
	if (model) delete model;
	if (loc) delete loc;
}

void planet::update(double delta) { 
	loc->update(delta);
	// m.rot += v.rot * delta;
	// TODO M = glm::rotate(M, m.inc, glm::vec3(1.f, 0.f, 0.f));
	
};

void planet::render() {
	//rotate around the model based on delta-time
	//MVP = glm::rotate(MVP, (float)delta * -10.0f, glm::vec3(1.f, 0.f, 0.f));
	M = get_abs_loc(); //HACK
	R = glm::mat4(1.f);
	// R = glm::rotate(R, /**/, glm::vec3(0.f, 0.f, 1.f));
	R = glm::scale(R, glm::vec3(size));
	glm::mat4 MVP = *VP * M * R;
		glBufferData ( GL_ARRAY_BUFFER, 3 * sizeof ( vertex ), model, GL_STATIC_DRAW );
	glVertexAttribPointer ( ( GLuint ) 0, 3, GL_DOUBLE, GL_FALSE, sizeof ( vertex ), ( const GLvoid* ) 0 );
	glEnableVertexAttribArray(0);
	glm::vec3 colour(1.f);
	// glUseProgram(shaderprogram);
	glUseProgram(0);
	glUniformMatrix4fv(glGetUniformLocation(shaderprogram, "mvpmatrix"), 1, GL_FALSE, glm::value_ptr(MVP));
	glUniform3fv(glGetUniformLocation(shaderprogram, "in_Colour"), 1, (GLfloat*)&colour);

	glDrawArrays(GL_LINE_STRIP, 0, (3));
}

void planet::setBehaviour(behaviour* loc) {
	if (this->loc) delete this->loc;
}


glm::mat4 planet::get_abs_loc() { 
	return loc->get_abs_loc();
}
