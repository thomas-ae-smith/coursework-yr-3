/* inherits UIFrame*/
#ifndef _GAMEFRAME_H_
#define _GAMEFRAME_H_

#include <vector>
#include "UIFrame.h"

#include "camera.h"
#include "tour.h"
#include "planets.h"

#define PLANET_NUM 18

using namespace std;

class gameFrame: public UIFrame {

	private:
		vector<abstractObject*> items;
		glm::mat4 VP, V;
		tour* camTour;
		camera* cam;
		bool R, T, P, H;
		planet* all_p[PLANET_NUM];

	public:
		gameFrame(abstractObject* parent);
		virtual ~gameFrame();
		virtual void update(double delta);
		virtual void render();
		virtual void debug() {};
		virtual void resetPlanets();

		/* data */
};

#endif /* #ifndef _GAMEFRAME_H_ */