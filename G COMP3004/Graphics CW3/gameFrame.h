/* inherits UIFrame*/
#ifndef _GAMEFRAME_H_
#define _GAMEFRAME_H_

#include <vector>
#include "UIFrame.h"


#include "TODOsphere.h"

using namespace std;

class gameFrame: public UIFrame {

	private:
		vector<abstractObject*> items;
		//camera* camera;

	public:
		gameFrame(abstractObject* parent);
		~gameFrame();
		void update(double delta);
		void render();
		void debug() {};

		/* data */
};

#endif /* #ifndef _GAMEFRAME_H_ */