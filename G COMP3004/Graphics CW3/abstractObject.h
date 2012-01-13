/*
All objects can refer to their parent.
All objects can update internal stat based on elapsed time,
 render, and optionally show/report debug information
*/

#ifndef ABSTRACTOBJECT_H
#define ABSTRACTOBJECT_H

class abstractObject {
	protected:
		abstractObject* parent;

	public:
		abstractObject(abstractObject* parent) {this->parent = parent;};
		virtual void update(double t);
		virtual void render();
		virtual void debug();
		virtual ~abstractObject();
		
};

#endif //ABSTRACTOBJECT_H