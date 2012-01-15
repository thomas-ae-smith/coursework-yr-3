#ifndef _CAMERA_H_
#define _CAMERA_H_ 

#include "gameObject.h"
#include "behaviours.h"

class camera : public gameObject
{
	protected:
		behaviour* loc;
		glm::vec4 target;
		float phi, theta;

	public:
		camera(abstractObject* parent);
		virtual ~camera() {};
		virtual void update(double delta);
		virtual void render() {};
		virtual void debug() {};

		/* data */
};
#endif /* #ifndef _CAMERA_H_ */