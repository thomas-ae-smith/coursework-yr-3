/* inherits UIFrame*/
#ifndef GAMEFRAME_H
#define GAMEFRAME_H

#include <vector>
#include "AbstractObject.h"


#include "TODOsphere.h"

using namespace std;

class gameFrame: public abstractObject {

	private:
		vector<abstractObject> items;
		TODOsphere *blob;

	public:
		gameFrame();
		~gameFrame();
		void update(double t);
		void render();

		/* data */
};

#endif