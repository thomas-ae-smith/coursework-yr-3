/* inherits UIFrame */

#include <algorithm>
#include <stdio.h>
#include <GL/glew.h>
#include <GL/glfw.h>

#include "GameFrame.h"
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
	{0, 	10.f, 	1.f, 	0.f, 	1.f,	0.f,	0.f,	1.f, 1.f, 0.f,	10,		"sun.frag"},//sun
	{0, 	.24f, 	1.f, 	5.8f,	.24f,	7.f,	0.f,	1.f, 1.f, 0.f,	4,		"merc.frag"},//mercury
	{0, 	.60f, 	1.f, 	7.2f,	-0.62f,	3.4f,	100.f,	1.f, 1.f, 0.f,	4,		"venus.frag"},//venus
	{0, 	.63f, 	1.f, 	10.f,	1.f,	0.f,	-30.f,	0.f, 0.f, 1.f,	5,		"earth.frag"},//earth
	{0, 	.33f, 	1.f, 	15.2f,	1.8f,	0.f,	15.f,	1.f, 0.f, 0.f,	3,		"mars.frag"},//mars
	{0, 	7.14f,	1.f,	52.f, 	11.8f,	0.f,	-27.f, 	1.f, .5f, 0.f,	6, 		"jupiter.frag"},//jupiter
	{0, 	6.02f,	1.f, 	95.f, 	29.5f,	0.f,	22.f,	.5f, .5f, 0.f,	6, 		"saturn.frag"},//saturn
	{0,		2.6f,	1.f, 	191.f,	84.f,	0.f,	270.f,	.8f, .8f, 1.f,	4,	 	"gasgiant.frag"},//uranus
	{0, 	2.5f, 	1.f, 	300.f,	164.f, 	0.f, 	80.f,	.5f, .5f, 1.f,	4, 		"gasgiant.frag"},//neptune
	{0, 	.11f, 	1.f, 	394.f, 	248.f,	0.f, 	127.f, 	0.f, 0.f, .5f,	3, 		"base.frag"},//pluto
	{3, 	.17f, 	1.f, 	.4f, 	.07f,	0.f, 	0.f, 	.5f, .5f, .5f,	3, 		"moon.frag"},//moon
	{6, 	14.f, 	.02f, 	0.f, 	1.f,	0.f, 	45.f,	.5f, .5f, .5f,	6, 		"rings.frag"},//saturn's rings
	{0, 	.2f, 	.5f, 	12.5f, 	0.f,	0.f, 	90.f,	.01f,.01f,.01f,	1, 		"base.frag"},//UFO
	{12, 	.1f, 	.5f, 	.12f, 	.01f,	0.f, 	0.f,	.01f,.01f,.01f,	1, 		"base.frag"},//UFO baby
	{12, 	.1f, 	.5f, 	.12f, 	.01f,	0.f, 	90.f,	.01f,.01f,.01f,	1, 		"base.frag"},//UFO baby
	{12, 	.1f, 	.5f, 	.12f, 	.01f,	0.f, 	180.f,	.01f,.01f,.01f,	1, 		"base.frag"},//UFO baby
	{12, 	.1f, 	.5f, 	.12f, 	.01f,	0.f, 	270.f,	.01f,.01f,.01f,	1, 		"base.frag"},//UFO baby
	{0, 	800.f, 	.5f, 	0.f, 	0.f,	0.f, 	0.f,	.01f,.01f,.01f,	3, 		"stars.frag"}//starfield
};


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






	//planet* all_p[PLANET_NUM];
	// planet* one_p;
	for (int p = 0; p < PLANET_NUM; p++) {
		all_p[p] = new planet(all_p[pdat[p].parent], pdat[p].size/5, pdat[p].height, &pdat[p].r, pdat[p].lod, pdat[p].frag);
		if (pdat[p].omega != 0.f) all_p[p]->setBehaviour(new orbitBehaviour(all_p[p], all_p[pdat[p].parent], pdat[p].angle, pdat[p].orbit/2., 2./pdat[p].omega));
		else {
			glm::mat4 R = glm::rotate(glm::mat4(1.f), pdat[p].angle, glm::vec3(0.f,0.f,1.f)) * glm::translate(glm::mat4(1.0), glm::vec3(0.f, pdat[p].orbit/2.f, 0.f));
			//printf("R:\t%f\t%f\t%f\t%f\n", R[3][0], R[3][1], R[3][2], R[3][3]);
			all_p[p]->setBehaviour(new staticBehaviour(all_p[pdat[p].parent], R));
		}
		items.push_back(all_p[p]);
	}

	camTour->push_back(new waypoint(camTour, new staticBehaviour(NULL, glm::vec3(-.7f, 0.f, 3.f)), 0.f, 1.9, .1f));	//instastart
	camTour->push_back(new waypoint(camTour, new staticBehaviour(NULL, glm::vec3(-.7f, 0.f, 3.f)), 0.f, 1.9, 15.f));	//wait a bit
	camTour->push_back(new waypoint(camTour, new staticBehaviour(NULL, glm::vec3(-6.25f, -2.72f, 8.9f)), 0.23f, 2.33, 5.f));	//above sun
	camTour->push_back(new waypoint(camTour, new staticBehaviour(NULL, glm::vec3(-6.25f, -2.72f, 8.9f)), 0.23f, 2.33, 5.f));	//wait
	camTour->push_back(new waypoint(camTour, new staticBehaviour(all_p[3], glm::vec3(-1.5f, 0.f, 0.35f)), 0.23f, 2.f, 5.f));		//head to earth
	camTour->push_back(new waypoint(camTour, new staticBehaviour(all_p[3], glm::vec3(-1.5f, 0.f, 0.3f)), 0.f, 1.57f, 5.f));		//precede it
	camTour->push_back(new waypoint(camTour, new staticBehaviour(NULL, glm::vec3(-7.75f, 0.f, 0.375f)), 0.2f, 1.9f, 5.f));		// reveal ufo
	camTour->push_back(new waypoint(camTour, new staticBehaviour(NULL, glm::vec3(-7.75f, 0.f, 0.375f)), 0.2f, 1.9f, 2.f));		// hover
	camTour->push_back(new waypoint(camTour, new staticBehaviour(NULL, glm::vec3(-7.895f, -.308f, 0.339f)), 0.289f, 1.59f, 5.f));	//get a good view
	//start fading neg_lod
	camTour->push_back(new waypoint(camTour, new staticBehaviour(NULL, glm::vec3(-7.895f, -.308f, 0.339f)), 0.289f, 1.59f, 35.f));	//get a good view
	// for (int p = 0; p < 1; p++) {
	// 	one_p = new planet(sun, 2.f);
	// 	one_p->setBehaviour(new orbitBehaviour(one_p, one_p, 0.f, 0.f, 0.f));
	// 	items.push_back(one_p);
	// // }
// printf("cap%p\n", cam);

	// glm::mat4 R = cam->getBehaviour()->R;
		 // printf("constrR:\t%f\t%f\t%f\t%f\n", cam->getBehaviour()->R[3][0], cam->getBehaviour()->R[3][1], cam->getBehaviour()->R[3][2], cam->getBehaviour()->R[3][3]);


	// cam->setBehaviour(//new staticBehaviour(cam, cam->get_abs_loc()));
	// 		new controlBehaviour(cam, camTour->get(1).get_abs_loc(), 1.f));

		 // printf("afterconstrR:\t%f\t%f\t%f\t%f\n", cam->getBehaviour()->R[3][0], cam->getBehaviour()->R[3][1], cam->getBehaviour()->R[3][2], cam->getBehaviour()->R[3][3]);
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

	Y = U = R = H = T = P = false;
	//printf("Created a gameFrame.\n");
}

gameFrame::~gameFrame() {
	items.clear();
}

void gameFrame::resetPlanets() {
	planet* all_p[PLANET_NUM];
	// planet* one_p;
	for (int p = 0; p < PLANET_NUM; p++) {
		if (pdat[p].omega != 0.f) all_p[p]->setBehaviour(new orbitBehaviour(all_p[p], all_p[pdat[p].parent], pdat[p].angle, pdat[p].orbit/2., 2./pdat[p].omega));
		else {
			glm::mat4 R = glm::rotate(glm::mat4(1.f), pdat[p].angle, glm::vec3(0.f,0.f,1.f)) * glm::translate(glm::mat4(1.0), glm::vec3(0.f, pdat[p].orbit/2.f, 0.f));
			//printf("R:\t%f\t%f\t%f\t%f\n", R[3][0], R[3][1], R[3][2], R[3][3]);
			all_p[p]->setBehaviour(new staticBehaviour(all_p[pdat[p].parent], R));
		}
	}
	
}

void gameFrame::update(double delta) {
	// printf("updating gameFrame\n");
    for (vector<abstractObject*>::iterator item = items.begin();
                           item != items.end();
                           ++item) {
    	(*item)->update(delta);
    }
    if (!camTour->getActive()) {

    	if (P && !glfwGetKey( 'P' ) ) {
	    	P = false;
			// printf("P pressed.\n");
			cam->setBehaviour(//new staticBehaviour(cam, cam->get_abs_loc()));
			new controlBehaviour(cam, camTour->get(0).get_abs_loc(), 1.f));
    		gameObject::neg_lod = 1.f;
			// items.push_back(new robot(this, &(gameObject::neg_lod), .1f, 10.));
		}
		P = glfwGetKey( 'P' );

		if (Y && !glfwGetKey( 'Y' ) ) {
	    	Y = false;
			// printf("P pressed.\n");
			cam->setBehaviour(//new staticBehaviour(cam, cam->get_abs_loc()));
			new controlBehaviour(cam, camTour->get(5).get_abs_loc(), 1.f));
    		gameObject::neg_lod = 1.f;
			// items.push_back(new robot(this, &(gameObject::neg_lod), .1f, 10.));
		}
		Y = glfwGetKey( 'Y' );

		if (U && !glfwGetKey( 'U' ) ) {
	    	U = false;
			// printf("P pressed.\n");
			cam->setBehaviour(//new staticBehaviour(cam, cam->get_abs_loc()));
			new controlBehaviour(cam, camTour->get(6).get_abs_loc(), 1.f));
    		gameObject::neg_lod = .1f;
			// items.push_back(new robot(this, &(gameObject::neg_lod), .1f, 10.));
		}
		U = glfwGetKey( 'U' );

		// if (R && !glfwGetKey( 'R' ) ) {
	 //    	R = false;
		// 	// printf("P pressed.\n");
		// 	resetPlanets();
		// 	// items.push_back(new robot(this, &(gameObject::neg_lod), .1f, 10.));
		// }
		// R = glfwGetKey( 'R' );

		if (T && !glfwGetKey( 'T' ) ) {
			T = false;
			// printf("T pressed.\n");
			//items.push_back(new robot(this, &(((managedBehaviour*)camloc)->phi), 20., 5.));
    		gameObject::neg_lod = 1.f;


			camTour->start(cam);
		}
		T = glfwGetKey( 'T' );

		if (H && !glfwGetKey( 'H' ) ) {
			H = false;
			printf("Demo help:\n - Arrow keys control the camera\n - PGUP, PGDOWN control elevation\n");
			printf(" - HOME, END control inclination\n - SPACE halts all camera movement\n - T starts the tour, E to quit\n");
			printf(/* - R to reset positions\n*/" - P, Y, U to visit particular locations\n");
		}
		H = glfwGetKey( 'H' );

		controlBehaviour* camloc = (controlBehaviour*)cam->getBehaviour();
		if (glfwGetKey( GLFW_KEY_PAGEUP ) ) {
			camloc->elevate();
		}
		if (glfwGetKey( GLFW_KEY_PAGEDOWN ) ) {
			camloc->delevate();
		}
		if (glfwGetKey( GLFW_KEY_UP ) ) {
			camloc->speedup();
		}
		if (glfwGetKey( GLFW_KEY_DOWN ) ) {
			camloc->slow();
		}
		if (glfwGetKey( GLFW_KEY_HOME ) ) {
			camloc->up();
		}
		if (glfwGetKey( GLFW_KEY_END ) ) {
			camloc->down();
		}
		if (glfwGetKey( GLFW_KEY_LEFT ) ) {
			camloc->left();
		}
		if (glfwGetKey( GLFW_KEY_RIGHT ) ) {
			camloc->right();
		}
		if (glfwGetKey( GLFW_KEY_SPACE ) ) {
			camloc->stop();
		}
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