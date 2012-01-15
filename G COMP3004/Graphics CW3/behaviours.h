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

class controlBehaviour : public behaviour {
	protected:
		float max_speed;
		float speed;
		float phi, theta;

	public:
		glm::vec4 vec;	//TODO this needs to be private with getters and setters
		controlBehaviour(abstractObject* parent, float max_speed);
		virtual ~controlBehaviour() {};
		virtual void update(double delta);
		virtual void render() {};
};

class moveBehaviour : public behaviour {
	protected:
		float max_speed;

	public:
		glm::vec4 vec;	//TODO this needs to be private with getters and setters
		moveBehaviour(abstractObject* parent, 
							glm::vec4 vec,
							float max_speed);
		virtual ~moveBehaviour() {};
		virtual void update(double delta);
		virtual void render() {};
};

#endif /* #ifndef _BEHAVIOURS_H_ */
