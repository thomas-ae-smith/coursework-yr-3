/* temp sphere, terrible code*/
#ifndef SPHERE_H
#define SPHERE_H value

#include "GameObject.h"
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
	
	public:
		TODOsphere(abstractObject* parent, float init);
		~TODOsphere();
		void update(double delta);
		void render();

		/* data */
};

#endif //SPHERE_H
