/* inherits UIFrame*/
#ifndef _GAMEFRAME_H_
#define _GAMEFRAME_H_

#include <vector>
#include "UIFrame.h"

#include "camera.h"
#include "tour.h"

using namespace std;

class gameFrame: public UIFrame {

	private:
		vector<abstractObject*> items;
		glm::mat4 VP, V;
		tour* camTour;
		camera* cam;
		bool T, P;

	public:
		gameFrame(abstractObject* parent);
		virtual ~gameFrame();
		virtual void update(double delta);
		virtual void render();
		virtual void debug() {};

		/* data */
};

#endif /* #ifndef _GAMEFRAME_H_ */