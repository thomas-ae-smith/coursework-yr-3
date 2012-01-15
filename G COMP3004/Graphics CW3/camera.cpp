#include "camera.h"

#include <stdio.h>
#include <GL/glfw.h>

camera::camera(abstractObject* parent) : gameObject(parent) {
	M = glm::mat4(1.);
	M = glm::translate(M, glm::vec3(0.f, 0.f, 0.0f));
	loc = new controlBehaviour(this, 1.f);
	target[2] = 1.f;
	// M = glm::lookAt(glm::vec3(0.f, -2.f, 1.0f), target.xyz, glm::vec3(0.f, 0.f, 1.f));
	phi = 0;
	theta = 1.5707963268;
}

void camera::update(double delta) {
	loc->update(delta);
	// M = loc->get_abs_loc();
	printf("target:\t%f\t%f\t%f\t%f\n", target[0], target[1], target[2], target[3]);
	printf("M:\t%f\t%f\t%f\t%f\n", M[3][0], M[3][1], M[3][2], M[3][3]);
	printf("R:\t%f\t%f\t%f\t%f\n", R[3][0], R[3][1], R[3][2], R[3][3]);
	glm::vec4 dirvec(sinf(theta)*cosf(phi),cosf(theta),sinf(theta)*sinf(phi), 1.f);
	target = glm::translate(glm::mat4(1.f), M[3].xyz) * dirvec.xzyw;
	//M = glm::lookAt(M[3].xyz, target.xyz, glm::vec3(0.f, 0.f, 1.f));
	glm::mat4 Projection = glm::perspective(45.0f, 1.0f, 0.1f, 100.0f);
	R = Projection * glm::lookAt(M[3].xyz, target.xyz, glm::vec3(0.f, 0.f, 1.f));
	gameObject::setVP(&R);


	if (glfwGetKey( GLFW_KEY_PAGEUP ) ) {
		printf("PgUp pressed.");
		M = glm::translate(M, glm::vec3(dirvec[0]*delta, dirvec[2]*delta, dirvec[1]*delta));
	}
	if (glfwGetKey( GLFW_KEY_UP ) ) {
		printf("Up pressed.");
		theta += 1* delta;
	}
	if (glfwGetKey( GLFW_KEY_DOWN ) ) {
		printf("Down pressed.");
		theta -= 1* delta;
	}
	if (glfwGetKey( GLFW_KEY_LEFT ) ) {
		printf("Left pressed.");
		phi += 1* delta;
	}
	if (glfwGetKey( GLFW_KEY_RIGHT ) ) {
		printf("Right pressed.");
		phi -= 1* delta;
	}
}