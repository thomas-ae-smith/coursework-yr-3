/* inherits UIFrame*/
#include "GameFrame.h"
#include "TODOsphere.h"

gameFrame::gameFrame() {
	adam = new TODOsphere(-1.f);
	bob = new TODOsphere(0.5f);
}

gameFrame::~gameFrame() {}

void gameFrame::update(double t) {
	
}

void gameFrame::render() {
	adam->render();
	bob->render();
}