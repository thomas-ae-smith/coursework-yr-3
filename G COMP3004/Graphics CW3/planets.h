/* temp sphere, terrible code*/
#ifndef _PLANETS_H_
#define _PLANETS_H_

#include "gameObject.h"
#include "behaviours.h"
#include <GL/glew.h>

class planet: public gameObject
{

	protected:
		GLuint shaderprogram;
		GLuint vbo[1];

		typedef GLdouble vertex[3];	//FIXME this isn't very nice
		vertex* model;
		float size;

		behaviour* loc;	// TODO: refactor this (look down)
	
	public:
		planet(abstractObject* parent, float size);
		virtual ~planet();
		virtual void update(double delta);
		virtual void render();
		virtual void setBehaviour(behaviour* loc); //TODO and this up to gameObject

		virtual glm::mat4 get_abs_loc();

		/* data */
};

#endif /* #ifndef _PLANETS_H_ */
