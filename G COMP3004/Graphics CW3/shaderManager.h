#ifndef SHADERMANAGER_H
#define SHADERMANAGER_H

#include <GL/glew.h>
#include <vector>

#include "abstractObject.h"

using namespace std;

class shaderManager: abstractObject
{
	private:
		static shaderManager *manager;
		vector<const char*> names;
		vector<GLuint> shaders;
		shaderManager();
		
	public:
		static shaderManager* get();
		GLuint getShader(const char* vert, const char* frag, const char* geom);
		~shaderManager();

		/* data */
};

#endif //SHADERMANAGER_H