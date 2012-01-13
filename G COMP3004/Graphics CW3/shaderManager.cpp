#include <stdio.h>
#include "shaderManager.h"
#include "Utils.h"

shaderManager* shaderManager::manager;

shaderManager* shaderManager::get(){
	if (manager == NULL) {
		manager = new shaderManager(NULL);
	}
	return manager;
};

shaderManager::shaderManager(abstractObject* parent) : abstractObject(parent) {
	printf("Created shaderManager\n");
}

shaderManager::~shaderManager() {
	printf("Unloaded shaderManager\n");
}

GLuint shaderManager::getShader(const char* vert, const char* frag, const char* geom){
// Unfinished code
	//create a new shaderprogram and attach the shaders
	GLuint shaderprogram, vertexshader, wireshader, fragmentshader;
	shaderprogram = glCreateProgram();
	
	printf("Create vertex shader %s\n", vert);
	const GLchar *vertexsource = filetobuf(vert);
	vertexshader = glCreateShader(GL_VERTEX_SHADER);
	glShaderSource(vertexshader, 1, (const GLchar**)&vertexsource, 0);
	glCompileShader(vertexshader);
	glAttachShader(shaderprogram, vertexshader);

	printf("Create fragment shader %s\n", frag);
	const GLchar* fragmentsource = filetobuf(frag);
	fragmentshader = glCreateShader(GL_FRAGMENT_SHADER);
	glShaderSource(fragmentshader, 1, (const GLchar**)&fragmentsource, 0);
	glCompileShader(fragmentshader);
	glAttachShader(shaderprogram, fragmentshader);

 	printf("Create geometry shader%s\n", geom);
	const GLchar* wiresource = filetobuf(geom);
	wireshader = glCreateShader(GL_GEOMETRY_SHADER);
	glShaderSource(wireshader, 1, (const GLchar**)&wiresource, 0);
	glCompileShader(wireshader);
	glAttachShader(shaderprogram, wireshader);
	
	//link the program
	glLinkProgram(shaderprogram);
	return shaderprogram;
}