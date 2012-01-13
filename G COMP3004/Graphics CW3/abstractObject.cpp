/*

*parent
update(time t)
render()	(everything can render, even if only for debug)

*/

#include "AbstractObject.h"

void abstractObject::update(double t) {};
void abstractObject::render() {};
void abstractObject::debug() {};
abstractObject::~abstractObject() {};
