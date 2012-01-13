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
#include "AbstractObject.h"
#include "shaderManager.h"

class gameObject : public abstractObject
{
	protected:
		static shaderManager* shaders;
		glm::mat4 m;
		glm::mat4 v;

	public:
		gameObject(abstractObject* parent);
		virtual ~gameObject() {};
		glm::mat4 get_abs_loc();

};

#endif /* #ifndef _GAMEOBJECT_H_ */