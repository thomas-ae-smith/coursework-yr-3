#include "camera.h"

#include <glm/glm.hpp>
#include <glm/gtc/matrix_transform.hpp>

camera::camera(abstractObject* parent) : gameObject(parent) {
	//glm::mat4 Projection = glm::perspective(45.0f, 1.0f, 0.1f, 100.0f);
	M = glm::mat4(1.);
	M = glm::translate(M, glm::vec3(0.f, 0.f, -15.0f));
	//M = Projection * View;
}

void camera::update(double delta) {
	M = glm::rotate(M, (float)(10.f * delta), glm::vec3(1.f, 0.f, 0.f));
		glm::mat4 Projection = glm::perspective(45.0f, 1.0f, 0.1f, 100.0f);
	R = Projection * M;
	gameObject::setVP(&R);
}