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
		virtual void render() {};
		virtual void setM(glm::mat4 M){this->M = M;};	//TODO:
		virtual glm::mat4 getR() = 0;				// these are horribly hacky
};

class staticBehaviour : public behaviour {
	public:
		staticBehaviour(abstractObject* parent, glm::vec3 loc);
		staticBehaviour(abstractObject* parent, glm::mat4 loc = glm::mat4(1.f));
		virtual ~staticBehaviour() {};
		virtual void update(double delta);
		virtual glm::mat4 getR() {return glm::mat4(0.f);};
		virtual glm::mat4 get_abs_loc() {return M;};
			
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
		virtual glm::mat4 getR() {return glm::mat4(0.f);};	
};

class managedBehaviour : public behaviour {
	protected:
		float max_speed;

	public:
		float speed;
		float phi, theta;
		glm::vec4 vec;	//TODO this needs to be private with getters and setters
		managedBehaviour(abstractObject* parent, float max_speed);
		managedBehaviour(abstractObject* parent, glm::mat4 loc, float max_speed);
		virtual ~managedBehaviour() {};
		virtual void update(double delta);
		virtual glm::mat4 getR() {return glm::mat4(0.f);};	
};

class controlBehaviour : public managedBehaviour{
	public:
		controlBehaviour(abstractObject* parent, float max_speed);
		controlBehaviour(abstractObject* parent, glm::mat4 loc, float max_speed);
		virtual ~controlBehaviour() {};
		virtual void update(double delta);
		virtual glm::mat4 getR() {return R;};				//TODO these are horribly hacky
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
		virtual glm::mat4 getR() {return glm::mat4(0.f);};	
};

#endif /* #ifndef _BEHAVIOURS_H_ */
