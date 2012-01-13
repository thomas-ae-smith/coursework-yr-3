/* inherits UIFrame*/
#include "GameFrame.h"
#include "TODOsphere.h"

gameFrame::gameFrame(abstractObject* parent) : abstractObject(parent) {
	adam = new TODOsphere(this, -1.f);
	bob = new TODOsphere(this, 0.5f);
}

gameFrame::~gameFrame() {}

void gameFrame::update(double t) {
	
}

void gameFrame::render() {
	adam->render();
	bob->render();
}