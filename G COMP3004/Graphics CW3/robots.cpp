#include "robots.h"
#include "tour.h"	//dangerous hack enabler

#include <stdio.h>

robot::robot(abstractObject* parent, float* value, float* endValue, float duration) : abstractObject(parent) {
	// printf("%p robot created.\n", this);
	this->value = value;
	this->startValue = *value;
	this->endValue = endValue;
	this->duration = duration;
	this->delay = 0.;
	elapsed = 0.;
	active = true;
	owner = false;
}

robot::robot(abstractObject* parent, float* value, float endValue, float duration) : abstractObject(parent) {
	// printf("%p robot created.\n", this);
	this->value = value;
	this->startValue = *value;
	this->endValue = new float(endValue);
	this->duration = duration;
	this->delay = 0.;
	elapsed = 0.;
	active = true;
	owner = true;
}

robot::robot(abstractObject* parent, float* value, float endValue, float duration, float delay) : abstractObject(parent) {
	// printf("%p robot created.\n", this);
	this->value = value;
	this->startValue = *value;
	this->endValue = new float(endValue);
	this->duration = duration;
	this->delay = delay;
	elapsed = 0.;
	active = true;
	owner = true;
}

robot::robot(abstractObject* parent, float* value, float endValue, float duration, float delay, float startValue) : abstractObject(parent) {
	// printf("%p robot created.\n", this);
	this->value = value;
	this->startValue = startValue;
	this->endValue = new float(endValue);
	this->duration = duration;
	this->delay = delay;
	elapsed = 0.;
	active = true;
	owner = true;
}

void robot::update(double delta) {
	if (active == false) return;
	//printf("%p robot updating.\n", this);
	if (delay > 0.f) {
		delay -= delta;
		return;
	}
	elapsed += delta;
	if (elapsed >= duration) {
		*value = *endValue;
		active = false;
		// printf("%p robot finished at %f.\n", this, *endValue);
		// if (parent) ((tour*)parent)->finished(this);	//dangerous hack
	} else {
		*value = startValue + (elapsed/duration) * (*endValue - startValue);
	}
}

robot::~robot() {
	if (owner && endValue) delete endValue;
}
