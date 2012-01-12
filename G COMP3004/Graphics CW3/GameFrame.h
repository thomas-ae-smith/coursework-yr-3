/* inherits UIFrame*/
#ifndef GAMEFRAME_H
#define GAMEFRAME_H

#include "AbstractObject.h"

class gameFrame : public abstractObject
{
	private:
		std::vector<abstractObject> v;

	public:
		gameFrame();
		~gameFrame();

		/* data */
};

#endif