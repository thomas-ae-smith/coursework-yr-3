#include "gameObject.h"

shaderManager* gameObject::shaders;
glm::mat4* gameObject::VP;
float gameObject::neg_lod;

gameObject::gameObject(abstractObject* parent) : abstractObject(parent) {
	if (!shaders) {
		shaders = shaderManager::get();
	}
	
}