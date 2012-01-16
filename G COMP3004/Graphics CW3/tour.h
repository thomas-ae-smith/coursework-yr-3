#ifndef _TOUR_H_
#define _TOUR_H_

#include <GL/glew.h>
#include <vector>

#include "abstractObject.h"
#include "behaviours.h"
#include "camera.h"
#include "robots.h"

using namespace std;

class waypoint: public abstractObject
{
	protected:
		behaviour* loc;
		float phi, theta;
		float duration;
	public:
		waypoint(abstractObject* parent, behaviour* loc, float phi, float theta, float duration);
		virtual ~waypoint() {};
		virtual void update(double delta) {};
		virtual void render() {};
		virtual void debug() {};
		virtual glm::mat4 get_abs_loc();
		virtual float getDuration() {return duration;};
};

class tour: public abstractObject
{
	protected:
		vector<waypoint*> points;
		vector<robot*> robots;
		bool active;
		// behaviour* loc;
		// float elapsed;
		int index;	
		camera* slave;
		
	public:
		tour(abstractObject* parent);
		virtual ~tour();
		virtual void update(double delta);
		virtual void render() {};
		virtual void debug() {};
		virtual void start(camera* slave);	//TODO this should be a gameObject, which should all have setBehaviour
		virtual waypoint get(int i) {return *(points.at(i));};
		virtual void push_back(waypoint* w) {points.push_back(w);}
		virtual void finished(robot* done);

};
#endif /* #ifndef _TOUR_H_ */
