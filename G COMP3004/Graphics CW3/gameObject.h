/*
All game objects are visible, and so must have:
relative location matrix m (including rotation and scale)
get_absolute_loc() (calls parent's get_absolute_loc(), then adds m)
velocity matrix v, defined as desired motion over 1 sec
model? TODO
*/
#ifndef _GAMEOBJECT_H_
#define _GAMEOBJECT_H_

#include <glm/glm.hpp>
#include <stdio.h>
#include "AbstractObject.h"
#include "shaderManager.h"



#include <glm/gtc/matrix_transform.hpp>

class gameObject : public abstractObject
{
	protected:
		static shaderManager* shaders;
		static glm::mat4* VP;
		glm::mat4 M;
		struct space_data
		{
			float angle;
			float orbit;
			float inc;
			float rot;
			float size;
		} m, v;

	public:
		gameObject(abstractObject* parent);
		virtual ~gameObject() {};
		glm::mat4 get_abs_loc() { return glm::rotate(glm::scale(M, glm::vec3(1/m.size)), -m.rot, glm::vec3(0.f, 0.f, 1.f));};

		virtual void update(double delta) {};// { printf("%f\n",delta);M = (v*delta)*M; };
		virtual void debug() {};	//need to implement this at this level

		static void setVP(glm::mat4* VP) {gameObject::VP = VP;};
		

};

#endif /* #ifndef _GAMEOBJECT_H_ */