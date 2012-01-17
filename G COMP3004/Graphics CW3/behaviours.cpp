#include "behaviours.h"

#include <GL/glfw.h>
#include <stdio.h>

staticBehaviour::staticBehaviour(abstractObject* parent, glm::vec3 loc) : behaviour(parent) {
	R = glm::translate(glm::mat4(1.f), loc);
	// M = glm::mat4(1.f);
}

staticBehaviour::staticBehaviour(abstractObject* parent, glm::mat4 loc) : behaviour(parent) {
	R = loc;
	// M = glm::mat4(1.f);
}

void staticBehaviour::update(double delta) {
	M = ((parent)? ((gameObject*)parent)->get_abs_loc() : glm::mat4(1.f)) * R;	//TODO: refactor, this should use target
}

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

managedBehaviour::managedBehaviour(abstractObject* parent,
							float max_speed) : behaviour(parent) {
	this->max_speed = max_speed;
// printf("happening\n");
	this->active = true;
	R = glm::mat4(1.);	//R is used as actual location
	R = glm::translate(R, glm::vec3(-.7f, 0.f, 3.f));
	phi = 0;
	theta = 1.9;
	this->speed = 0.f;
	// printf("happened\n");
}

managedBehaviour::managedBehaviour(abstractObject* parent, glm::mat4 loc,
							float max_speed) : behaviour(parent) {
	this->max_speed = max_speed;

	this->active = true;
	R = loc;	//R is used as actual location
	phi = 0;
	theta = 1.9;
	this->speed = 0.f;
}

void managedBehaviour::update(double delta){
	glm::vec4 dirvec(sinf(theta)*cosf(phi),cosf(theta),sinf(theta)*sinf(phi), 1.f);
	M = glm::lookAt(R[3].xyz, (glm::translate(glm::mat4(1.f), R[3].xyz) * dirvec.xzyw).xyz, glm::vec3(0.f, 0.f, 1.f));

	R = glm::translate(R, glm::vec3(dirvec[0]*delta*speed, dirvec[2]*delta*speed, dirvec[1]*delta*speed));
	
}

controlBehaviour::controlBehaviour(abstractObject* parent,
							float max_speed) : managedBehaviour(parent, max_speed) {};

controlBehaviour::controlBehaviour(abstractObject* parent, glm::mat4 loc,
							float max_speed) : managedBehaviour(parent, loc, max_speed) {/*printf("Located controlBehaviour constructed.");*/};

void controlBehaviour::update(double delta) {
	// printf("M:\t%f\t%f\t%f\t%f\n", M[3][0], M[3][1], M[3][2], M[3][3]);
//	if (glfwGetKey( 'H' ) ) {
		// printf("PgUp pressed.");
//	 printf("R:\t%f\t%f\t%f\t%f\n", R[3][0], R[3][1], R[3][2], R[3][3]);
//	 printf("theta:\t%f\t\tphi:\t%f\n", theta, phi);
//	}
	managedBehaviour::update(delta);

	if (belevate) {
		belevate = false;
		R[3][2] += 1*delta;
	}
	if (bdelevate ) {
		bdelevate = false;
		// printf("PgDown pressed.");
		R[3][2] -= 1*delta;
	}
	if (bspeedup) {
		bspeedup = false;
		// printf("Up pressed.");
		speed += 1* delta;
	}
	if (bslow ) {
		bslow = false;
		// printf("Down pressed.");
		speed = (0.f > speed - 1* delta)?0.f: speed - 1* delta;
	}
	if (bup ) {
		bup = false;
		// printf("Home pressed.");
		theta -= 1* delta;
	}
	if (bdown ) {
		bdown = false;
		// printf("End pressed.");
		theta += 1* delta;
	}
	if (bleft ) {
		bleft = false;
		// printf("Left pressed.");
		phi += 2* delta;
	}
	if (bright ) {
		bright = false;
		// printf("Right pressed.");
		phi -= 2* delta;
	}
	if (bstop ) {
		bstop = false;
		// printf("Space pressed.");
		speed = 0.f;
	}
}

void controlBehaviour::elevate() {
	belevate = true;
}

void controlBehaviour::delevate() {
	bdelevate = true;
}

void controlBehaviour::speedup() {
	bspeedup = true;
}

void controlBehaviour::slow() {
	bslow = true;
}

void controlBehaviour::up() {
	bup = true;
}

void controlBehaviour::down() {
	bdown = true;
}

void controlBehaviour::left() {
	bleft = true;
}

void controlBehaviour::right() {
	bright = true;
}

void controlBehaviour::stop() {
	bstop = true;
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