/* inherits UIFrame */

#include <algorithm>
#include <stdio.h>
#include "GameFrame.h"
#include "TODOsphere.h"
#include "TODOtri.h"
#include "planets.h"


gameFrame::gameFrame(abstractObject* parent) : UIFrame(parent) {
	// Testing objects
	staticBehaviour* sun = new staticBehaviour(NULL, glm::vec3(0.f));
	cam = new camera(sun);
	items.push_back(cam);
	items.push_back(sun);
	items.push_back(new planet(sun, 2.5f));
	items.push_back(new planet(sun, 1.f));
	planet* earth = new planet(sun, 4.4f);
	items.push_back(earth);
	planet* moon = new planet(earth, -1.5f);
	items.push_back(moon);

	// V = glm::mat4(1.);
	// V = glm::translate(V, glm::vec3(0.f, 0.f, -15.0f));
//	 V = glm::rotate(V, 45.f, glm::vec3(1.f, 0.f, 0.f));
	// glm::mat4 Projection = glm::perspective(45.0f, 1.0f, 0.1f, 100.0f);
	// VP = Projection * V;
	// gameObject::setVP(&VP);
}

gameFrame::~gameFrame() {
	items.clear();
}

void gameFrame::update(double delta) {
    for (vector<abstractObject*>::iterator item = items.begin();
                           item != items.end();
                           ++item) {
    	(*item)->update(delta);
    }

}

void gameFrame::render() {
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	for (vector<abstractObject*>::iterator item = items.begin();
                           item != items.end();
                           ++item) {
    	(*item)->render();
    }
    // for (vector<abstractObject*>::iterator item = items.begin();
    //                        item != items.end();
    //                        ++item) {
    // 	(*item)->debug();
    // }
}