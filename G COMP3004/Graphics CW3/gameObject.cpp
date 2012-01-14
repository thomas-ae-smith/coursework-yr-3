#include "gameObject.h"

shaderManager* gameObject::shaders;
glm::mat4* gameObject::VP;

gameObject::gameObject(abstractObject* parent) : abstractObject(parent) {
	if (!shaders) {
		shaders = shaderManager::get();
	}
	
}