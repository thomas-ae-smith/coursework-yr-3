#include "robots.h"

robot::robot(abstractObject* parent, float* value, endValue, duration) : abstractObject(parent) {
	this->value = value;
	this->startValue = *value;
	this->endValue = endValue;
	this->duration = duration;
	elapsed = 0.;
}

void robot::update(double delta) {
	if (active = false) return;
	elapsed += delta;
	if (elapsed >= duration) {
		*value = endValue;
		active = false;
		if (parent) parent->finished(this);
	} else {
		*value = startValue + (elapsed/duration) * (endValue - startValue);
	}
}
