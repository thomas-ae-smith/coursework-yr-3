#include "gameObject.h"

shaderManager* gameObject::shaders;

gameObject::gameObject(abstractObject* parent) : abstractObject(parent) {
	if (!shaders) {
		shaders = shaderManager::get();
	}
	
}