#ifndef _BEHAVIOURS_H_
#define _BEHAVIOURS_H_

#include "gameObject.h"

class behaviour : public gameObject {
	protected:
		bool active;

	public:
		behaviour(abstractObject* parent) : gameObject(parent) {};
		virtual ~behaviour() {};
		virtual void update(double delta) = 0;
		virtual void render() = 0;
};

class orbitBehaviour : public behaviour {
	protected:
		gameObject* target;
		float angle; 
		float orbit; 
		float omega;

	public:
		orbitBehaviour(abstractObject* parent, 
							gameObject* target, 
							float angle, 
							float orbit, 
							float omega);
		virtual ~orbitBehaviour() {};
		virtual void update(double delta);
		virtual void render() {};
};

#endif /* #ifndef _BEHAVIOURS_H_ */
