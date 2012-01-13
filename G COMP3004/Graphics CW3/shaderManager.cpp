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

	GLuint shaderprogram, vertexshader, wireshader, fragmentshader;
	printf("Create vertex shader %s\n", vert);
		const GLchar *vertexsource = filetobuf(vert);
		vertexshader = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertexshader, 1, (const GLchar**)&vertexsource, 0);
		glCompileShader(vertexshader);
		printf("Create fragment shader\n");
		const GLchar* fragmentsource = filetobuf(frag);
		fragmentshader = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragmentshader, 1, (const GLchar**)&fragmentsource, 0);
		glCompileShader(fragmentshader);
	//} else { printf("Fragment shader already exists\n"); }
	//create a new shaderprogram and attach the first two shaders
	shaderprogram = glCreateProgram();
	glAttachShader(shaderprogram, vertexshader);
	glAttachShader(shaderprogram, fragmentshader);
	 printf("Create wireframe geometry shader\n");
		const GLchar* wiresource = filetobuf(geom);
		wireshader = glCreateShader(GL_GEOMETRY_SHADER);
		glShaderSource(wireshader, 1, (const GLchar**)&wiresource, 0);
		glCompileShader(wireshader);
	//} else { printf("Wireframe geometry shader already exists\n"); }
	//attatch the custom geometry shader, link the program and set it active
	glAttachShader(shaderprogram, wireshader);
	glLinkProgram(shaderprogram);
	return shaderprogram;
}