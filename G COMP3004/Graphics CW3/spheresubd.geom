#version 150

precision highp float;

uniform mat4 mvpmatrix;
uniform int iter;

// Declare what type of incoming primitive our geometry shader is receiving
layout(triangles) in;

// Declare what type of primitives we are creating and the maximum amount of vertices we will output per use of the geometry shader.
// We will be outputting 6 vertices per use of this shader, creating 2 triangles.
layout(triangle_strip, max_vertices = 124) out;

// Inputs to geometry shaders are always as arrays in the quantity of the incoming primitive
// In our case this value is 3, since triangles have 3 vertices
 out vec3 ex_Color;
 in vec3 geom_Color[3];
 
 vec3 V0, V01, V02;

 void makeVert(float s, float t)
 {
 	vec3 v = V0 + s*V01 + t*V02;
 	v = normalize(v);
 	gl_Position = mvpmatrix * vec4(v, 1.);
 	EmitVertex();
 }

 void main()
 {
 	V01 = (gl_in[1].gl_Position - gl_in[0].gl_Position).xyz;
 	V02 = (gl_in[2].gl_Position - gl_in[0].gl_Position).xyz;
 	V0 = gl_in[0].gl_Position.xyz;

 	//int iter  = 5;

 	float dt = 1./float(iter);

 	float t_upper = 1.;
 	ex_Color = geom_Color[0];

 	for( int t = 0; t < iter; t++)
 	{
 		float t_lower = t_upper - dt;
 		float smax_upper = 1. - t_upper;
 		float smax_lower = 1. - t_lower;

 		int num_tris = t + 1;
 		float ds_upper = smax_upper / float(num_tris - 1);
 		float ds_lower = smax_lower / float(num_tris);

 		float s_upper = 0.;
 		float s_lower = 0.;

 		for( int s = 0; s < num_tris; s++)
 		{
 			//gl_Position = mvpmatrix * vec4(normalize(V0+s_lower*V01+t_lower*V02), 1.0);
 			//EmitVertex();

 			makeVert(s_lower, t_lower);

 			//gl_Position = mvpmatrix * vec4(V0+s_upper*V01+t_upper*V02, 1.0);
 			//EmitVertex();

 			makeVert(s_upper, t_upper);

 			s_upper += ds_upper;
 			s_lower += ds_lower;
 		}

 		//gl_Position = mvpmatrix * vec4(normalize(V0+s_lower*V01+t_lower*V02), 1.0);
 		//EmitVertex();

 		makeVert(s_lower, t_lower);

 		EndPrimitive();

 		t_upper = t_lower;
 		t_lower -= dt;
 	}

// 	gl_Position = mvpmatrix * vec4(V0, 1.0); 
//	ex_Color = geom_Color[0];
// 	EmitVertex();
	
//	gl_Position = mvpmatrix * vec4(normalize(V0+V01), 1.0); 
//	ex_Color = geom_Color[1];
// 	EmitVertex();
	
//	gl_Position = mvpmatrix * vec4(normalize(V0+V02), 1.0); 
//	ex_Color = geom_Color[2];
// 	EmitVertex();

	
//	gl_Position = mvpmatrix * vec4(V0, 1.0); 
//	ex_Color = geom_Color[0];
// 	EmitVertex();

// 	EndPrimitive();
}
		  // simple iterator
//	 int i;
	
	// Create our original primitive
//	for (i=0; i < gl_in.length(); i++)
//	{
//		gl_Position = mvpmatrix * vec4(gl_in[i].gl_Position.xyz, 1.0); 
//		ex_Color = geom_Color[i];
//		EmitVertex();
//	}
//	gl_Position = mvpmatrix * vec4(gl_in[0].gl_Position.xyz, 1.0); 
//		ex_Color = geom_Color[0];
//		EmitVertex();
//	EndPrimitive();
//}
