#include "camera.h"

#include <stdio.h>
#include <GL/glfw.h>

camera::camera(abstractObject* parent) : gameObject(parent) {
	//printf("Happening\n");
	loc = new controlBehaviour(this, 1.f);
}

void camera::update(double delta) {
	loc->update(delta);
	M = loc->get_abs_loc();
	glm::mat4 Projection = glm::perspective(45.0f, 1.0f, 0.1f, 1000.0f);
	R = Projection * M;
	gameObject::setVP(&R);
}

void camera::setBehaviour(behaviour* loc) {
	if (this->loc) delete this->loc;
	this->loc = loc;
}