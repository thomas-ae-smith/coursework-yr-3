#include "behaviours.h"

#include <GL/glfw.h>

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

controlBehaviour::controlBehaviour(abstractObject* parent,
							float max_speed) : behaviour(parent) {
	this->max_speed = max_speed;

	this->active = true;
		M = glm::mat4(1.);
	M = glm::translate(M, glm::vec3(0.f, 0.f, 0.2f));
phi = 0;
	theta = 1.5707963268;
}

void controlBehaviour::update(double delta) {
	// loc->update(delta);
	// M = loc->get_abs_loc();
	// printf("target:\t%f\t%f\t%f\t%f\n", target[0], target[1], target[2], target[3]);
	printf("M:\t%f\t%f\t%f\t%f\n", M[3][0], M[3][1], M[3][2], M[3][3]);
	printf("R:\t%f\t%f\t%f\t%f\n", R[3][0], R[3][1], R[3][2], R[3][3]);
	glm::vec4 dirvec(sinf(theta)*cosf(phi),cosf(theta),sinf(theta)*sinf(phi), 1.f);
	// target = (glm::translate(glm::mat4(1.f), M[3].xyz) * dirvec.xzyw);
	//M = glm::lookAt(M[3].xyz, target.xyz, glm::vec3(0.f, 0.f, 1.f));
	glm::mat4 Projection = glm::perspective(45.0f, 1.0f, 0.1f, 100.0f);
	R = Projection * glm::lookAt(M[3].xyz, (glm::translate(glm::mat4(1.f), M[3].xyz) * dirvec.xzyw).xyz, glm::vec3(0.f, 0.f, 1.f));
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

moveBehaviour::moveBehaviour(abstractObject* parent, 
							glm::vec4 vec,
							float max_speed) : behaviour(parent) {
	// this->vec = vec;
	this->max_speed = max_speed;

	this->active = true;


		M = glm::lookAt(glm::vec3(0.f, 0.f, 15.0f), glm::vec3(0.f, 0.f, 0.f), glm::vec3(0.f, 1.f, 0.f));
	// M = glm::translate(M, glm::vec3(0.f, 0.f, -15.0f));
}

void moveBehaviour::update(double delta) {
	float length = glm::length(vec.xyz);
	if (length > max_speed) {
		printf("Speed too high. Restricting");
		vec = glm::scale(glm::mat4(1.f), glm::vec3(max_speed/length)) * vec;
	}
	if (glfwGetKey( GLFW_KEY_UP ) ) {
		printf("Up pressed.");
		// loc->vec = 
		vec[2] += 1*delta;
	}
	if (glfwGetKey( GLFW_KEY_DOWN ) ) {
		printf("Down pressed.");
		vec[2] = (vec[2] - 1*delta < 0)? 0.f: vec[2] - 1 * delta;	//TODO probably full of casting
	}
	if (glfwGetKey( GLFW_KEY_PAGEUP ) ) {
		printf("Up pressed.");
		// loc->vec = 
		glm::vec4 test = M[3];
		M = glm::translate(M, glm::vec3(-test[0], -test[1], -test[2]));
		M = glm::rotate(M, (float)(10.f * delta), glm::vec3(1.f, 0.f, 0.f));
		M = glm::translate(M, glm::vec3(test[0], test[1], test[2]));
	}
	if (glfwGetKey( GLFW_KEY_PAGEDOWN ) ) {
		printf("Down pressed.");
		// loc->vec = 
		glm::vec4 test = M[3];
		M = glm::translate(M, glm::vec3(-test[0], -test[1], -test[2]));
		M = glm::rotate(M, (float)(-10.f * delta), glm::vec3(1.f, 0.f, 0.f));
		M = glm::translate(M, glm::vec3(test[0], test[1], test[2]));
	}
	if (glfwGetKey( GLFW_KEY_LEFT ) ) {
		printf("Left pressed.");
		// loc->vec = 
		glm::vec4 test = M[3];
		M = glm::translate(M, glm::vec3(-test[0], -test[1], -test[2]));
		M = glm::rotate(M, (float)(10.f * delta), glm::vec3(0.f, 1.f, 0.f));
		M = glm::translate(M, glm::vec3(test[0], test[1], test[2]));
	}
	if (glfwGetKey( GLFW_KEY_RIGHT ) ) {
		printf("Right pressed.");
		// loc->vec = 
		glm::vec4 test = M[3];
		M = glm::translate(M, glm::vec3(-test[0], -test[1], -test[2]));
		M = glm::rotate(M, (float)(-10.f * delta), glm::vec3(0.f, 1.f, 0.f));
		M = glm::translate(M, glm::vec3(test[0], test[1], test[2]));
	}
	if (glfwGetKey( GLFW_KEY_SPACE ) ) {
		printf("Space pressed.");
		vec = glm::vec4(0.f,0.f,0.f,1.f);
	}

	printf("vec:\t%f\t%f\t%f\t%f\n", vec[0], vec[1], vec[2], vec[3]);
	M = glm::translate(M, (glm::scale(glm::mat4(1.f), glm::vec3(delta)) * vec).xyz); //TODO need to get delta in here somehow
}