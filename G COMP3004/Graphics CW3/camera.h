#ifndef _CAMERA_H_
#define _CAMERA_H_ 

#include "gameObject.h"
#include "behaviours.h"

class camera : public gameObject
{
	protected:
		behaviour* loc;

	public:
		camera(abstractObject* parent);
		virtual ~camera() {};
		virtual void update(double delta);
		virtual void render() {};
		virtual void debug() {};

		/* data */
};
#endif /* #ifndef _CAMERA_H_ */