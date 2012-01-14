/* temp sphere, terrible code*/
#ifndef SPHERE_H
#define SPHERE_H value

#include "gameObject.h"
#include "behaviours.h"
#include <GL/glew.h>

class TODOsphere: public gameObject
{

	protected:
		GLuint vertexshader, fragmentshader;
		GLfloat drawColour[3];
		GLuint shaderprogram;
		GLuint vbo[1];

		GLuint wireshader;
		typedef union { GLdouble pos[3]; struct { GLdouble x; GLdouble y; GLdouble z; }; } vertex;
		vertex* sphere;

		GLfloat viewerPosition[3];
		GLfloat reflectance[3];
		behaviour* loc;
	
	public:
		TODOsphere(abstractObject* parent, float init);
		virtual ~TODOsphere();
		virtual void update(double delta);
		virtual void render();

		virtual glm::mat4 get_abs_loc();

		/* data */
};

#endif //SPHERE_H
