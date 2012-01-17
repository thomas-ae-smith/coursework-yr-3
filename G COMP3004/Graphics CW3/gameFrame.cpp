/* inherits UIFrame */

#include <algorithm>
#include <stdio.h>
#include <GL/glew.h>
#include <GL/glfw.h>

#include "GameFrame.h"
#include "TODOsphere.h"
#include "TODOtri.h"
#include "planets.h"
#include "robots.h"

struct planets {
	int parent; //index to
	float size;	//uh...
	float height;	//ditto
	float orbit;	//1 unit = 10% AU
	float omega;	//speed
	float inc;		//polar inlination
	float angle;	//initial angle
	GLfloat r,g,b;//colour
	int lod;
	const char* frag;
} pdat[] =
{//	parent	size	height	orbit 	omega	inc 	angle 	colour			lod 	frag
	{0, 	10.f, 	1.f, 	0.f, 	1.f,	0.f,	0.f,	1.f, 1.f, 0.f,	8,		"base.frag"},//sun
	{0, 	.24f, 	1.f, 	4.8f,	.24f,	7.f,	0.f,	1.f, 1.f, 0.f,	4,		"base.frag"},//mercury
	{0, 	.60f, 	1.f, 	7.2f,	-0.62f,	3.4f,	0.f,	1.f, 1.f, 0.f,	4,		"base.frag"},//venus
	{0, 	.63f, 	1.f, 	10.f,	1.f,	0.f,	0.f,	0.f, 0.f, 1.f,	5,		"base.frag"},//earth
	{0, 	.33f, 	1.f, 	15.2f,	1.8f,	0.f,	0.f,	1.f, 0.f, 0.f,	3,		"base.frag"},//mars
	{0, 	7.14f,	1.f,	52.f, 	11.8f,	0.f,	0.f, 	1.f, .5f, 0.f,	6, 		"base.frag"},//jupiter
	{0, 	6.02f,	1.f, 	95.f, 	29.5f,	0.f,	0.f,	.5f, .5f, 0.f,	6, 		"base.frag"},//saturn
	{0,		2.6f,	1.f, 	191.f,	84.f,	0.f,	0.f,	.8f, .8f, 1.f,	4,	 	"base.frag"},//uranus
	{0, 	2.5f, 	1.f, 	300.f,	164.f, 	0.f, 	0.f,	.5f, .5f, 1.f,	4, 		"base.frag"},//neptune
	{0, 	.11f, 	1.f, 	394.f, 	248.f,	0.f, 	0.f, 	0.f, 0.f, .5f,	3, 		"base.frag"},//pluto
	{3, 	.17f, 	1.f, 	.4f, 	.07f,	0.f, 	0.f, 	.5f, .5f, .5f,	3, 		"base.frag"},//moon
	{6, 	14.f, 	.02f, 	0.f, 	1.f,	0.f, 	45.f,	.5f, .5f, .5f,	6, 		"base.frag"},//saturn's rings
	{0, 	.2f, 	.5f, 	10.f, 	0.f,	0.f, 	90.f,	.01f,.01f,.01f,	1, 		"base.frag"},//UFO
	{12, 	.1f, 	.5f, 	.12f, 	.01f,	0.f, 	0.f,	.01f,.01f,.01f,	1, 		"base.frag"},//UFO baby
	{12, 	.1f, 	.5f, 	.12f, 	.01f,	0.f, 	90.f,	.01f,.01f,.01f,	1, 		"base.frag"},//UFO baby
	{12, 	.1f, 	.5f, 	.12f, 	.01f,	0.f, 	180.f,	.01f,.01f,.01f,	1, 		"base.frag"},//UFO baby
	{12, 	.1f, 	.5f, 	.12f, 	.01f,	0.f, 	270.f,	.01f,.01f,.01f,	1, 		"base.frag"}//UFO baby
};
#define PLANET_NUM 17

gameFrame::gameFrame(abstractObject* parent) : UIFrame(parent) {
	// Testing objects
	// planet* sun = new planet(NULL, 1.f);
	// cam = new camera(sun);
	// items.push_back(cam);
	// items.push_back(sun);

	gameObject::neg_lod = 1.f;
	staticBehaviour* sun = new staticBehaviour(NULL, glm::vec3(0.f));
	camTour = new tour(sun);
	items.push_back(camTour);
 	cam = new camera(sun);
 	items.push_back(cam);
 // 	items.push_back(sun);
	// items.push_back(new planet(sun, 2.5f));






	planet* all_p[PLANET_NUM];
	// planet* one_p;
	for (int p = 0; p < PLANET_NUM; p++) {
		all_p[p] = new planet(all_p[pdat[p].parent], pdat[p].size/5, pdat[p].height, &pdat[p].r, pdat[p].lod, pdat[p].frag);
		if (pdat[p].omega != 0.f) all_p[p]->setBehaviour(new orbitBehaviour(all_p[p], all_p[pdat[p].parent], pdat[p].angle, pdat[p].orbit/2., 2./pdat[p].omega));
		else {
			glm::mat4 R = glm::rotate(glm::mat4(1.f), pdat[p].angle, glm::vec3(0.f,0.f,1.f)) * glm::translate(glm::mat4(1.0), glm::vec3(0.f, pdat[p].orbit/2.f, 0.f));
			printf("R:\t%f\t%f\t%f\t%f\n", R[3][0], R[3][1], R[3][2], R[3][3]);
			all_p[p]->setBehaviour(new staticBehaviour(all_p[pdat[p].parent], R[3].xyz));
		}
		items.push_back(all_p[p]);
	}

	camTour->push_back(new waypoint(camTour, new staticBehaviour(NULL, glm::vec3(-.7f, 0.f, 3.f)), 0.f, 1.9, 0.5f));
	camTour->push_back(new waypoint(camTour, new staticBehaviour(all_p[3], glm::vec3(.0f, 0.f, .7f)), 0.f, 2.5f, 5.f));
	// for (int p = 0; p < 1; p++) {
	// 	one_p = new planet(sun, 2.f);
	// 	one_p->setBehaviour(new orbitBehaviour(one_p, one_p, 0.f, 0.f, 0.f));
	// 	items.push_back(one_p);
	// // }

	// planet* jupiter = new planet(sun, 2.5f);
	// jupiter->setBehaviour(new orbitBehaviour(jupiter, sun, 0.f, 5.f, 5.f));
	// items.push_back(jupiter);
	// items.push_back(new planet(sun, 1.f));
	// TODOsphere* earth = new TODOsphere(sun, 4.4f);
	// items.push_back(earth);
	// TODOsphere* moon = new TODOsphere(earth, -1.5f);
	// items.push_back(moon);

	// V = glm::mat4(1.);
	// V = glm::translate(V, glm::vec3(0.f, 0.f, -15.0f));
//	 V = glm::rotate(V, 45.f, glm::vec3(1.f, 0.f, 0.f));
	// glm::mat4 Projection = glm::perspective(45.0f, 1.0f, 0.1f, 100.0f);
	// VP = Projection * V;
	// gameObject::setVP(&VP);

	T = P = false;
	printf("Created a gameFrame.\n");
}

gameFrame::~gameFrame() {
	items.clear();
}

void gameFrame::update(double delta) {
	// printf("updating gameFrame\n");
    for (vector<abstractObject*>::iterator item = items.begin();
                           item != items.end();
                           ++item) {
    	(*item)->update(delta);
    }
    if (P && !glfwGetKey( 'P' ) ) {
    	P = false;
		printf("P pressed.\n");
		cam->setBehaviour(//new staticBehaviour(cam, cam->get_abs_loc()));
		new controlBehaviour(cam, camTour->get(0).get_abs_loc(), 1.f));
		items.push_back(new robot(this, &(gameObject::neg_lod), .1f, 10.));
	}
	if (T && !glfwGetKey( 'T' ) ) {
		T = false;
		printf("T pressed.\n");
		//items.push_back(new robot(this, &(((managedBehaviour*)cam->getBehaviour())->phi), 20., 5.));


		camTour->start(cam);
	}

	T = glfwGetKey( 'T' );
	P = glfwGetKey( 'P' );

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