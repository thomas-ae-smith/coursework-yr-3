/* temp tri, terrible code*/
#ifndef TRI_H
#define TRI_H value

#include "gameObject.h"
#include "behaviours.h"
#include <GL/glew.h>

class TODOtri: public gameObject
{

	protected:
		GLfloat drawColour[3];
		GLuint shaderprogram;
		GLuint vbo[1];

		typedef union { GLdouble pos[3]; struct { GLdouble x; GLdouble y; GLdouble z; }; } vertex;
		vertex* tri;

		GLfloat viewerPosition[3];
		GLfloat reflectance[3];
		behaviour* loc;
	
	public:
		TODOtri(abstractObject* parent, float init);
		virtual ~TODOtri();
		virtual void update(double delta);
		virtual void render();

		virtual glm::mat4 get_abs_loc();

		/* data */
};

#endif //TRI_H
