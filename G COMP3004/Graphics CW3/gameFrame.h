/* inherits UIFrame*/
#ifndef _GAMEFRAME_H_
#define _GAMEFRAME_H_

#include <vector>
#include "UIFrame.h"


#include "TODOsphere.h"

using namespace std;

class gameFrame: public UIFrame {

	private:
		vector<abstractObject> items;
		TODOsphere *adam;
		TODOsphere *bob;

		void render_item(abstractObject item) {item.render();};
		void update_item(abstractObject item){};
		void debug_item(abstractObject item){};

	public:
		gameFrame(abstractObject* parent);
		~gameFrame();
		void update(double t);
		void render();

		/* data */
};

#endif /* #ifndef _GAMEFRAME_H_ */