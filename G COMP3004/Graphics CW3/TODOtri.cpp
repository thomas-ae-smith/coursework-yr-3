// // This is terrible code, largely hacekd together from various bits of my CW1

// #include "TODOtri.h"
// #include "behaviours.h"
// #include <glm/gtc/type_ptr.hpp>
// #include <stdio.h>

// TODOtri::TODOtri(abstractObject* parent, float init) : gameObject(parent) {
// // GLuint TODOtri::vertexshader, TODOtri::fragmentshader;
// // GLfloat TODOtri::drawColour[3] = {  1.0f, 1.0f, 1.0f };
// drawColour = {  1.0f, 1.0f, 1.0f };
// // glm::mat4 TODOtri::MVP;
// // GLuint TODOtri::vbo[1];
// // GLfloat TODOtri::viewerPosition[3] = {  0.0f, 0.0f, -1.0f };
// // GLfloat TODOtri::reflectance[3] = {  0.1f, 0.4f, 6.0f };
// viewerPosition = {  0.0f, 0.0f, -1.0f };
// reflectance = {  0.1f, 0.4f, 6.0f };
// // GLuint TODOtri::wireshader;

// 	int divisions = 12;
// //setupshaders
// 	// DrawStyles::setupShaders(); //create a new shaderprogram and attach the first two shaders
// 	//create the first two shaders if they do not already exist
// 	//if (!vertexshader) {
// 	//if (init > 0) {
// 	//	shaderprogram = shaders->getShader("shaded.vert", "shaded.frag", "tri_shaded.geom");
// 	//} else {
// 		shaderprogram = shaders->getShader("base.vert", "base.frag", "spheresubd.geom");        
// 	//}
	
// 	//if (!vbo[0]) {
// 		//printf("Create vertex buffer object\n");
// 		glGenBuffers(1, vbo);
// 		glBindBuffer(GL_ARRAY_BUFFER, vbo[0]);
// 	//} else { printf("Vertex buffer object already exists\n"); }
// 	//if (!wireshader) {
	
// 	glBindAttribLocation(shaderprogram, 0, "in_Position");
// 	glUseProgram(shaderprogram);
// 	glUniform3fv(glGetUniformLocation(shaderprogram, "in_Colour"), 1, drawColour);

// 	glUniform3fv(glGetUniformLocation(shaderprogram, "viewer"), 1, viewerPosition);
// 	glUniform3fv(glGetUniformLocation(shaderprogram, "K"), 1, reflectance);

// //setupgeometry
// 	int trivertexes = divisions * divisions;
// 	tri = new vertex[24];
// 	// generatetriVertexes(divisions);



// 	const double TAU = 6.283185307179586476924;
// 	const double arc = TAU/divisions;
// 	if (tri == NULL)
// 		tri = new vertex[divisions * divisions];

// 	int v_num = 0;

// 	double circ_arc = 0.0;
// 	double height_arc = arc;

// 	GLdouble z = cos(height_arc);
// 	double r = sin(height_arc);
// 	// generate the first ring. These can't look back, and are each duplicated
// 	// for ( int i = 0; i < divisions; i++) {
// 	// 	tri[v_num].x = 0.0;
// 	// 	tri[v_num].y = 0.0;
// 	// 	tri[v_num].z = 1.0;
// 	// 	v_num++;
// 	// 	tri[v_num].x = 0.0;
// 	// 	tri[v_num].y = 1.0;
// 	// 	tri[v_num].z = 0.0;
// 	// 	v_num++;
// 	// 	tri[v_num].x = 1.0;
// 	// 	tri[v_num].y = 0.0;
// 	// 	tri[v_num].z = 0.0;
// 	// 	v_num++;
// 	// }
// 	for (int i = 0; i < 8; i++) {
// 		tri[v_num].x = 0.0;
// 		tri[v_num].y = 0.0;
// 		tri[v_num].z = ((i % 2) == 0)? -1. : 1.;
// 		printf("%f", tri[v_num].z);
// 		v_num++;
// 		tri[v_num].x = 0.0;
// 		tri[v_num].y = ((i & 0x2) == 0)? -1. : 1.;
// 		tri[v_num].z = 0.0;
// 		printf("%f", tri[v_num].y);
// 		v_num++;
// 		tri[v_num].x = ((i & 0x4) == 0)? -1. : 1.;
// 		tri[v_num].y = 0.0;
// 		tri[v_num].z = 0.0;
// 		printf("%f\n", tri[v_num].x);
// 		v_num++;
// 	}
	








// 	// glBufferData ( GL_ARRAY_BUFFER, trivertexes * sizeof ( vertex ), tri, GL_STATIC_DRAW );
// 	// glVertexAttribPointer ( ( GLuint ) 0, 3, GL_DOUBLE, GL_FALSE, sizeof ( vertex ), ( const GLvoid* ) 0 );
// 	// glEnableVertexAttribArray(0);




// 	// void DrawStyles::setupCamera() {
// 	// glm::mat4 Projection = glm::perspective(45.0f, 1.0f, 0.1f, 100.0f);
// 	// glm::mat4 View = glm::mat4(1.);
// 	// View = glm::translate(View, glm::vec3(0.f, 0.f, -5.0f));
// 	if (init == 0) m.size = 0.75f;
// 	else m.size = 1.f;
	
// 	// loc = new orbitBehaviour(this, (gameObject*)parent, 0.f, init, -init*10.f);
// 	loc = new staticBehaviour(this, glm::vec3(init));
// 	v.rot = init*5.f;

// 	// MVP = Projection * View * Model;
// 	printf("Created a TODOtri.\n");
// }

// TODOtri::~TODOtri() {
// 	printf("Deleted a TODOtri.\n");
// 	delete tri;
// }

// void TODOtri::update(double delta) { 
// 	loc->update(delta);
// 	m.rot += v.rot * delta;
// 	//printf("%f\n", m.orbit);
// 	// TODO M = glm::rotate(M, m.inc, glm::vec3(1.f, 0.f, 0.f));
	
// };

// void TODOtri::render() {
// 	//rotate around the model based on delta-time
// 	//MVP = glm::rotate(MVP, (float)delta * -10.0f, glm::vec3(1.f, 0.f, 0.f));
// 	M = get_abs_loc(); //HACK
// 	R = glm::mat4(1.f);
// 	R = glm::rotate(R, m.rot, glm::vec3(0.f, 0.f, 1.f));
// 	//R = glm::scale(R, glm::vec3(1.f,1.f,0.02f*m.size));
// 	glm::mat4 MVP = *VP * M * R;
// 	glBufferData ( GL_ARRAY_BUFFER, 24 * sizeof ( vertex ), tri, GL_STATIC_DRAW );
// 	glVertexAttribPointer ( ( GLuint ) 0, 3, GL_DOUBLE, GL_FALSE, sizeof ( vertex ), ( const GLvoid* ) 0 );
// 	glEnableVertexAttribArray(0);
// 	glUseProgram(shaderprogram);
// 	// glm::vec3 colour(1.f, .5f, .15f);
// 	// glUniform3fv(glGetUniformLocation(shaderprogram, "Color"), 1, (GLfloat*)&colour);
// 	// glm::vec3 radius(.5f, 1.f, 5.f);
// 	// glUniform3fv(glGetUniformLocation(shaderprogram, "Radius"), 1, (GLfloat*)&radius);
// 	// glm::vec3 fplevel(0.f, 0.f, 10.f);
// 	// glUniform3fv(glGetUniformLocation(shaderprogram, "FpLevel"), 1, (GLfloat*)&fplevel);

// 	glUniformMatrix4fv(glGetUniformLocation(shaderprogram, "mvpmatrix"), 1, GL_FALSE, glm::value_ptr(MVP));
// 	glDrawArrays(GL_TRIANGLE_STRIP, 0, 24);
// }


// glm::mat4 TODOtri::get_abs_loc() { 
// 	return loc->get_abs_loc();
// }
