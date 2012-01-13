/* inherits UIFrame */
#include "GameFrame.h"
#include "TODOsphere.h"

gameFrame::gameFrame(abstractObject* parent) : UIFrame(parent) {
	// Testing objects
	adam = new TODOsphere(this, -1.f);
	bob = new TODOsphere(this, 0.5f);
}

gameFrame::~gameFrame() {
	if (adam) delete adam;
	if (bob) delete bob;
}

void gameFrame::update(double t) {
	
}

void gameFrame::render() {
	adam->render();
	bob->render();
}