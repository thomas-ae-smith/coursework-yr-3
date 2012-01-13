#version 150
precision highp float;

uniform vec3 in_Colour;
uniform vec3 K;
uniform vec3 viewer; //light is coaxial
uniform mat4 mvpmatrix;

in  vec3 ex_Color;
in vec3 normal;

out vec4 gl_FragColor;

float specular() {
	return min(max( 0.0, K.z * pow(dot(normalize((vec4(viewer, 0.0) * mvpmatrix).xyz), reflect(normalize((vec4(viewer, 0.0) * mvpmatrix).xyz), normalize(normal))), 2)), 1.0);
}

void main(void) {
     gl_FragColor = vec4(ex_Color + in_Colour * specular(),1.0);
     //gl_FragColor = vec4(normalize(normal), 1.0);
}
