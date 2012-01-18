#ifndef _ROBOTS_H_
#define _ROBOTS_H_ 

#include "gameObject.h"
#include "behaviours.h"

class robot : public abstractObject {
	protected:
		float* value;
		float startValue;
		float* endValue;
		float duration;

		float elapsed;
		float delay;

		bool active;
		bool owner;


	public:
		robot(abstractObject* parent, float* value, float* endValue, float duration);
		robot(abstractObject* parent, float* value, float endValue, float duration);
		robot(abstractObject* parent, float* value, float endValue, float duration, float delay);
		virtual ~robot();
		virtual void update(double delta);
		virtual void render() {};
		virtual void debug() {};

		/* data */
};
#endif /* #ifndef _ROBOTS_H_ */