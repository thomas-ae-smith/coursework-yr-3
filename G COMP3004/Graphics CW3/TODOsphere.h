/* temp sphere */
#ifndef SPHERE_H
#define SPHERE_H value

#include "GameObject.h"
#include <GL/glew.h>

class TODOsphere: public gameObject
{

	protected:
        static GLuint vertexshader, fragmentshader;
        static GLfloat drawColour[3];
        static glm::mat4 MVP;
        GLuint shaderprogram;
        static GLuint vbo[1];

        static GLuint wireshader;
        typedef union { GLdouble pos[3]; struct { GLdouble x; GLdouble y; GLdouble z; }; } vertex;
        vertex* sphere;
	
	public:
		TODOsphere();
		~TODOsphere() {delete sphere;};
		void update(double t);
		void render();

		/* data */
};

#endif //SPHERE_H
