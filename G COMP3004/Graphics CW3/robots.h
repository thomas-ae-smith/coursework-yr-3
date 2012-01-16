#ifndef _ROBOTS_H_
#define _ROBOTS_H_ 

#include "gameObject.h"
#include "behaviours.h"

class robot : public gameObject
{
	protected:
		float* value;
		float startValue;
		float endValue;
		float duration;

		float elapsed;

		boolean active;

	public:
		robot(abstractObject* parent, float* value, endValue, duration);
		virtual ~robot() {};
		virtual void update(double delta);
		virtual void render() {};
		virtual void debug() {};

		/* data */
};
#endif /* #ifndef _ROBOTS_H_ */