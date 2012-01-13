/*

*parent
update(time t)
render()	(everything can render, even if only for debug)

*/
#ifndef ABSTRACTOBJECT_H
#define ABSTRACTOBJECT_H

class abstractObject {
	protected:
		abstractObject *parent;

	public:
		virtual void update(double t);
		virtual void render();
		virtual void debug();
		virtual ~abstractObject();
		
};

#endif //ABSTRACTOBJECT_H