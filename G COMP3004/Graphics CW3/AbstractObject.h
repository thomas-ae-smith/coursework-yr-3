/*

*parent
update(time t)
render()	(everything can render, even if only for debug)

*/
#ifndef ABSTRACTOBJECT_H
#define ABSTRACTOBJECT_H

class abstractObject {
	protected:
		abstractObject* parent;
		virtual void update(double t);
		virtual void render();
		
};

#endif //ABSTRACTOBJECT_H