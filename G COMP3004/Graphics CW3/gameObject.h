/*

Inherits abstractObj
{ parent, render(), update(time t)}

relative location matrix m (inc rotation)
get_absolute_loc()
velocity matrix v, defined as desired motion in 1 sec
model? TODO
*/

#include <glm/glm.hpp>
#include "AbstractObject.h"
#include "shaderManager.h"

class gameObject : public abstractObject
{
	protected:
		static shaderManager* shaders;
		glm::mat4 m;
		glm::mat4 v;

	public:
		gameObject(abstractObject* parent);
		virtual ~gameObject() {};
		glm::mat4 get_abs_loc();


};