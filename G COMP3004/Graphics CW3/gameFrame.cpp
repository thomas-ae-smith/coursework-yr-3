/* inherits UIFrame */

#include <algorithm>
#include <stdio.h>
#include "GameFrame.h"
#include "TODOsphere.h"

gameFrame::gameFrame(abstractObject* parent) : UIFrame(parent) {
	// Testing objects
	items.push_back(new TODOsphere(this, -1.f));
	items.push_back(new TODOsphere(this, 0.5f));
	items.push_back(new TODOsphere(this, 1.4f));
}

gameFrame::~gameFrame() {
	items.clear();
}

void gameFrame::update(double t) {
	
}

void gameFrame::render() {
	for (vector<abstractObject*>::iterator item = items.begin();
                           item != items.end();
                           ++item) {
    	(*item)->render();
    }for (vector<abstractObject*>::iterator item = items.begin();
                           item != items.end();
                           ++item) {
    	(*item)->debug();
    }
	//for_each(items.begin(), items.end(), render_item);
	//render_item(*items.begin());
	//render_item(*items.end());
}