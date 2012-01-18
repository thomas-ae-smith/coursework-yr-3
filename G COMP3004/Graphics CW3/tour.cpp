#include "tour.h"


#include <GL/glfw.h>

#define EFFECT_ROBOTS 3

waypoint::waypoint(abstractObject* parent, behaviour* loc, float phi, float theta, float duration) : abstractObject(parent) 
{
	this->loc = loc;
	this->phi = phi;
	this->theta = theta;
	this->duration = duration;
}

void waypoint::update(double delta) {
	loc->update(delta);
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

	robots.push_back(new robot(this, &(gameObject::amoeba), 1.f, 10., 47.));
	robots.push_back(new robot(this, &(gameObject::amoeba), .0f, 10., 62., 1.f));
	robots.push_back(new robot(this, &(gameObject::neg_lod), .02f, 10., 72.));

	printf("Starting tour.\n");
}

tour::~tour() {
	// if (loc) delete loc;
}

void tour::setupRobots(camera* cam, waypoint* point) {
	managedBehaviour* loc = ((managedBehaviour*)cam->getBehaviour());
	loc->speed = 0.f;
	robots.push_back(new robot(this, &(loc->phi), &(point->phi), point->duration/5));
	robots.push_back(new robot(this, &(loc->theta), &(point->theta), point->duration/5));
	// robots.push_back(new robot(this, &(loc->phi), &(point->phi), point->duration/5, 4*point->duration/5));
	// robots.push_back(new robot(this, &(loc->theta), &(point->theta), point->duration/5, 4*point->duration/5));
	robots.push_back(new robot(this, &(loc->R[3][0]), &(point->loc->M[3][0]), point->duration));
	robots.push_back(new robot(this, &(loc->R[3][1]), &(point->loc->M[3][1]), point->duration));
	robots.push_back(new robot(this, &(loc->R[3][2]), &(point->loc->M[3][2]), point->duration));
}

void tour::update(double delta) {
	// printf("updating tour.\n");
	for (vector<waypoint*>::iterator item = points.begin();
                           item != points.end();
                           ++item) {
    	(*item)->update(delta);
    }
	if (active) {
		// printf("%f\n", gameObject::amoeba);
		if (robots.size() <= EFFECT_ROBOTS)	setupRobots(slave, points.at(index));
		elapsed += delta;
		for (vector<robot*>::iterator item = robots.begin();
                           item != robots.end();
                           ++item) {
    		(*item)->update(delta);
    	}


		if (elapsed > points.at(index)->getDuration()) {
			// elapsed -= points.at(index)->getDuration();
			elapsed = 0.f;
			index++;
			for (vector<robot*>::iterator item = robots.begin()+EFFECT_ROBOTS;
                           item != robots.end();
                           ++item) {
                delete *item;
    		}
    		robots.erase(robots.begin()+EFFECT_ROBOTS, robots.end());
		}
		if (glfwGetKey( 'E' ) || index == (int)points.size()) {
			printf("Free control returned.\n");
			for (vector<robot*>::iterator item = robots.begin();
                           item != robots.end();
                           ++item) {
                delete *item;
    		}
    		robots.clear();
    		// gameObject::neg_lod = 1.f;
    		gameObject::amoeba = 0.0;
			active = false;
			// slave->setBehaviour(new controlBehaviour(slave, loc->get_abs_loc(), 1.f));
								//new controlBehaviour(slave, get(0).get_abs_loc(), 1.f));
		}
	}
}



//need to store camera

// void tour::finished(robot* done){
// 	int i = 0;
// 	for(; i < (int)robots.size(); i++) {
// 		if (robots.at(i) == done) break;
// 	}
// 	if (done) delete done;
// 	robots.erase(i);
// }

