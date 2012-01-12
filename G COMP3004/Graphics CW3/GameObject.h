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

class gameObject : public abstractObject
{
	protected:
		glm::mat4 m;
		glm::mat4 v;


	public:
		gameObject();
		~gameObject();
		glm::mat4 get_abs_loc();

};