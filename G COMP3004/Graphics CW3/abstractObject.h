/*
All objects can refer to their parent.
All objects can update internal stat based on elapsed time,
 render, and optionally show/report debug information
*/
// Protection against multiple definitions
#ifndef _ABSTRACTOBJECT_H_
#define _ABSTRACTOBJECT_H_

class abstractObject {
	protected:
		abstractObject* parent;

	public:
		// Using inline syntax to define simple constructor
		abstractObject(abstractObject* parent) {this->parent = parent;};
		// Virtual functions will all be overridden
		virtual void update(double t) = 0;
		virtual void render();
		virtual void debug();
		virtual ~abstractObject();
		
};

#endif /* #ifndef _ABSTRACTOBJECT_H) */