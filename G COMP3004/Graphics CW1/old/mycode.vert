#version 150
precision highp float;

in vec3 in_Position;
uniform vec3 in_Colour;

 // mvpmatrix is the result of multiplying the model, view, and projection matrices */ 
uniform mat4 mvpmatrix;

 out vec3 geom_Color;
 void main(void) {
// Multiply the mvp matrix by the vertex to obtain our final vertex position

// gl_Position = mvpmatrix * vec4(in_Position, 1.0); 
gl_Position = vec4(in_Position, 1.0);
geom_Color = in_Colour;
 }

