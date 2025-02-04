#version 150

precision highp float;

uniform mat4 mvpmatrix;

// Declare what type of incoming primitive our geometry shader is receiving
layout(triangles) in;

// Declare what type of primitives we are creating and the maximum amount of vertices we will output per use of the geometry shader.
// We will be outputting 6 vertices per use of this shader, creating 2 triangles.
layout(line_strip, max_vertices = 8) out;

// Inputs to geometry shaders are always as arrays in the quantity of the incoming primitive
// In our case this value is 3, since triangles have 3 vertices
 out vec3 ex_Color;
 in vec3 geom_Color[3];
 
 void main()
 {
          // simple iterator
     int i;
    
    // Create our original primitive
    for (i=0; i < gl_in.length(); i++)
    {
        gl_Position = mvpmatrix * vec4(gl_in[i].gl_Position.xyz, 1.0); 
        ex_Color = geom_Color[i];
        EmitVertex();
    }
	gl_Position = mvpmatrix * vec4(gl_in[0].gl_Position.xyz, 1.0); 
        ex_Color = geom_Color[0];
        EmitVertex();
    EndPrimitive();
	gl_Position = mvpmatrix * vec4(gl_in[0].gl_Position.xyz, 1.0); 
        ex_Color = geom_Color[0];
        EmitVertex();
	gl_Position = mvpmatrix * vec4(gl_in[0].gl_Position.xyz * 1.5, 1.0); 
        ex_Color = geom_Color[0];
        EmitVertex();
    EndPrimitive();
   
}
