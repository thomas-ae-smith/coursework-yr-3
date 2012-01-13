#include "gameObject.h"

shaderManager* gameObject::shaders;

gameObject::gameObject() {
	if (!shaders) {
		shaders = shaderManager::get();
	}
	
}