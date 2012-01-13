#include <stdio.h>
#include "shaderManager.h"

shaderManager* shaderManager::manager;

shaderManager* shaderManager::get(){
	if (manager == NULL) {
		manager = new shaderManager();
	}
	return manager;
};

shaderManager::shaderManager(){

	printf("Created shaderManager\n");
}

shaderManager::~shaderManager() {
	printf("Unloaded shaderManager\n");
}

GLuint shaderManager::getShader(const char* vert, const char* frag, const char* geom){};