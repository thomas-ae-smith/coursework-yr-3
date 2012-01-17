#include "planets.h"
#include "behaviours.h"
#include <glm/gtc/type_ptr.hpp>	//TODO take a look at why this is here
#include <stdio.h>


planet::planet(abstractObject* parent, float size, float height, GLfloat colour[], int lod, const char* frag) : gameObject(parent) {
	shaderprogram = shaders->getShader("base.vert", frag, "spheresubd.geom");        
	glGenBuffers(1, vbo);
	glBindBuffer(GL_ARRAY_BUFFER, vbo[0]);
	
	// glBindAttribLocation(shaderprogram, 0, "in_Position");
	// glUseProgram(shaderprogram);

	model = new vertex[24];
	int v_num = 0;
	for (int i = 0; i < 8; i++) {
		model[v_num][0] = 0.0;
		model[v_num][1]= 0.0;
		model[v_num][2] = ((i % 2) == 0)? -1. : 1.;
		// printf("%f", model[v_num][2]);
		v_num++;
		model[v_num][0] = 0.0;
		model[v_num][1] = ((i & 0x2) == 0)? -1. : 1.;
		model[v_num][2] = 0.0;
		// printf("%f", model[v_num][1]);
		v_num++;
		model[v_num][0] = ((i & 0x4) == 0)? -1. : 1.;
		model[v_num][1] = 0.0;
		model[v_num][2] = 0.0;
		// printf("%f\n", model[v_num][0]);
		v_num++;
	}
	glBufferData ( GL_ARRAY_BUFFER, 24 * sizeof ( vertex ), model, GL_STATIC_DRAW );

	// for (int i = 0; i < 3; ++i)
	// {
	// 	model[i][i] = 1.f;
	// }

	this->size = size;
	this->height = height;
	this->colour[0] = colour[0];
	this->colour[1] = colour[1];
	this->colour[2] = colour[2];
	this->lod = lod;
	loc = new staticBehaviour(this);


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
	R = glm::scale(R, glm::vec3(size, size, size*height));
	glm::mat4 MVP = *VP * M * R;
	// glBufferData ( GL_ARRAY_BUFFER, 24 * sizeof ( vertex ), model, GL_STATIC_DRAW );
	glBindBuffer(GL_ARRAY_BUFFER, vbo[0]);
	glEnableVertexAttribArray(0);
	glVertexAttribPointer ( ( GLuint ) 0, 3, GL_DOUBLE, GL_FALSE, sizeof ( vertex ), ( const GLvoid* ) 0 );
	glUseProgram(shaderprogram);
	glm::vec3 drawColour(1.f, 1.f, 0.f);
	int curr_lod = ceil(((float)lod) * neg_lod);
	glUniform3fv(glGetUniformLocation(shaderprogram, "in_Colour"), 1, colour);
	glUniform1i(glGetUniformLocation(shaderprogram, "iter"), curr_lod);
	glUniformMatrix4fv(glGetUniformLocation(shaderprogram, "mvpmatrix"), 1, GL_FALSE, glm::value_ptr(MVP));

	glDrawArrays(GL_TRIANGLE_STRIP, 0, 24);
}

void planet::setBehaviour(behaviour* loc) {
	if (this->loc) delete this->loc;
	this->loc = loc;
}


glm::mat4 planet::get_abs_loc() { 
	return loc->get_abs_loc();
}
