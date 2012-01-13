#ifndef _SHADERMANAGER_H_
#define _SHADERMANAGER_H_

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
		shaderManager(abstractObject* parent);
		
	public:
		static shaderManager* get();
		GLuint getShader(const char* vert, const char* frag, const char* geom);
		~shaderManager();

};

#endif /* #ifndef _SHADERMANAGER_H_ */