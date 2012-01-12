/* inherits UIFrame*/
#include "GameFrame.h"
#include "TODOsphere.h"

gameFrame::gameFrame() {
	blob = new TODOsphere();
}

gameFrame::~gameFrame() {}

void gameFrame::update(double t) {
	
}

void gameFrame::render() {
	blob->render();
}