#include "behaviours.h"

orbitBehaviour::orbitBehaviour(abstractObject* parent, 
								gameObject* target, 
								float angle, 
								float orbit, 
								float omega) : behaviour(parent) {
	this->target = target;
	this->angle = angle;
	this->orbit = orbit;
	this->omega = omega;

	this->active = true;
}

void orbitBehaviour::update(double delta) {
	angle += omega * delta;
	if (target == NULL) M = glm::mat4(1.0);
	else M = target->get_abs_loc();
	M = glm::rotate(M, angle, glm::vec3(0.f, 0.f, 1.f));
	M = glm::translate(M, glm::vec3(orbit, 0.f, 0.f));
	M = glm::rotate(M, -angle, glm::vec3(0.f, 0.f, 1.f));
	//print(M);
}