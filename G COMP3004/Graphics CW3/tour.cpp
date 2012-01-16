#include "tour.h"


#include <GL/glfw.h>

waypoint::waypoint(abstractObject* parent, behaviour* loc, float phi, float theta, float duration) : abstractObject(parent) 
{
	this->loc = loc;
	this->phi = phi;
	this->theta = theta;
	this->duration = duration;
}

glm::mat4 waypoint::get_abs_loc() {
	return loc->get_abs_loc();
}

tour::tour(abstractObject* parent) : abstractObject(parent) { active = false;};

void tour::start(camera* slave) {
	active = true;
	index = 0;
	this->slave = slave;
	// loc = new staticBehaviour(this, slave->get_abs_loc());	//TODO horribly hacky
	// glm::mat4 R = slave->getBehaviour()->getR();
	// printf("copy:\t%f\t%f\t%f\t%f\n", R[3][0], R[3][1], R[3][2], R[3][3]);
	//slave->setBehaviour(loc);

	// robots.push_back(new robot(this))
	printf("Starting tour.\n");
}

tour::~tour() {
	if (loc) delete loc;
}

void tour::update(double delta) {
	// printf("updating tour.\n");
	if (active) {
		// loc->setM(loc->get_abs_loc() + (points.at(index)->get_abs_loc() - loc->get_abs_loc()) * ((points.at(index)->getDuration() - elapsed)/delta));
		// elapsed += delta;



		if (elapsed > points.at(index)->getDuration()) {
			elapsed -= points.at(index)->getDuration();
			index++;
		}
		if (glfwGetKey( 'E' ) || index == (int)points.size()) {
			printf("E pressed.");
			active = false;
			loc->setM(points.back()->get_abs_loc());
			slave->setBehaviour(new controlBehaviour(slave, loc->get_abs_loc(), 1.f));
								//new controlBehaviour(slave, get(0).get_abs_loc(), 1.f));
		}
	}
}
//need to store camera