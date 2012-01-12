#version 150
precision highp float;

in vec3 in_Position;
uniform vec3 in_Colour;
uniform vec3 K;
uniform vec3 viewer; //light is coaxial

// mvpmatrix is the result of multiplying the model, view, and projection matrices */ 
uniform mat4 mvpmatrix;
vec3 normal;

float diffuse() {
	return max( 0.0, K.y * dot(normal, (vec4(viewer, 0.0) * mvpmatrix).xyz));
}

out vec3 geom_Color;

void main(void) {
	// Multiply the mvp matrix by the vertex to obtain our final vertex position
	//gl_Position = mvpmatrix * vec4(in_Position, 1.0); 
	gl_Position = vec4(in_Position, 1.0);
	normal = normalize(in_Position);
	geom_Color = in_Colour * K.x + in_Colour * diffuse();
 }

//send the specular calculation to the pixel shader. invert the view vector. increase the resolution
