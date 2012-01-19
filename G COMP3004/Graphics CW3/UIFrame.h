/* inherits abstractObject*/
#ifndef _UIFRAME_H_
#define _UIFRAME_H_

#include <vector>
#include "abstractObject.h"



using namespace std;

class UIFrame: public abstractObject {

	public:
		UIFrame(abstractObject* parent) : abstractObject(parent) {};
		virtual ~UIFrame();

};

#endif /* #ifndef _UIFRAME_H_ */