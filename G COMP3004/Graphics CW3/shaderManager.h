#ifndef _SHADERMANAGER_H_
#define _SHADERMANAGER_H_

#include <GL/glew.h>
#include <vector>
#include <string>

#include "abstractObject.h"

using namespace std;

class shaderManager: abstractObject
{
	private:
		static shaderManager *manager;
		vector<string> names;
		vector<GLuint> shaders;
		shaderManager(abstractObject* parent);
		
	public:
		static shaderManager* get();
		GLuint getShader(const char* vert, const char* frag, const char* geom);
		virtual ~shaderManager();
		virtual void update(double delta) {};
		virtual void render() {};
		virtual void debug() {};

};

#endif /* #ifndef _SHADERMANAGER_H_ */