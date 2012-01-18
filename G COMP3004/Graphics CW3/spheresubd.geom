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
 out vec3 vert_pos;
 vec3 V0, V01, V02;


uniform float time;








//
// Description : Array and textureless GLSL 2D/3D/4D simplex 
//               noise functions.
//      Author : Ian McEwan, Ashima Arts.
//  Maintainer : ijm
//     Lastmod : 20110822 (ijm)
//     License : Copyright (C) 2011 Ashima Arts. All rights reserved.
//               Distributed under the MIT License. See LICENSE file.
//               https://github.com/ashima/webgl-noise
// 

vec4 mod289(vec4 x) {
  return x - floor(x * (1.0 / 289.0)) * 289.0; }

float mod289(float x) {
  return x - floor(x * (1.0 / 289.0)) * 289.0; }

vec4 permute(vec4 x) {
     return mod289(((x*34.0)+1.0)*x);
}

float permute(float x) {
     return mod289(((x*34.0)+1.0)*x);
}

vec4 taylorInvSqrt(vec4 r)
{
  return 1.79284291400159 - 0.85373472095314 * r;
}

float taylorInvSqrt(float r)
{
  return 1.79284291400159 - 0.85373472095314 * r;
}

vec4 grad4(float j, vec4 ip)
  {
  const vec4 ones = vec4(1.0, 1.0, 1.0, -1.0);
  vec4 p,s;

  p.xyz = floor( fract (vec3(j) * ip.xyz) * 7.0) * ip.z - 1.0;
  p.w = 1.5 - dot(abs(p.xyz), ones.xyz);
  s = vec4(lessThan(p, vec4(0.0)));
  p.xyz = p.xyz + (s.xyz*2.0 - 1.0) * s.www; 

  return p;
  }

float snoise(vec4 v)
  {
  const vec4  C = vec4( 0.138196601125011,  // (5 - sqrt(5))/20  G4
                        0.276393202250021,  // 2 * G4
                        0.414589803375032,  // 3 * G4
                       -0.447213595499958); // -1 + 4 * G4
						
// (sqrt(5) - 1)/4 = F4, used once below
#define F4 0.309016994374947451

// First corner
  vec4 i  = floor(v + dot(v, vec4(F4)) );
  vec4 x0 = v -   i + dot(i, C.xxxx);

// Other corners

// Rank sorting originally contributed by Bill Licea-Kane, AMD (formerly ATI)
  vec4 i0;
  vec3 isX = step( x0.yzw, x0.xxx );
  vec3 isYZ = step( x0.zww, x0.yyz );
//  i0.x = dot( isX, vec3( 1.0 ) );
  i0.x = isX.x + isX.y + isX.z;
  i0.yzw = 1.0 - isX;
//  i0.y += dot( isYZ.xy, vec2( 1.0 ) );
  i0.y += isYZ.x + isYZ.y;
  i0.zw += 1.0 - isYZ.xy;
  i0.z += isYZ.z;
  i0.w += 1.0 - isYZ.z;

  // i0 now contains the unique values 0,1,2,3 in each channel
  vec4 i3 = clamp( i0, 0.0, 1.0 );
  vec4 i2 = clamp( i0-1.0, 0.0, 1.0 );
  vec4 i1 = clamp( i0-2.0, 0.0, 1.0 );

  //  x0 = x0 - 0.0 + 0.0 * C.xxxx
  //  x1 = x0 - i1  + 1.0 * C.xxxx
  //  x2 = x0 - i2  + 2.0 * C.xxxx
  //  x3 = x0 - i3  + 3.0 * C.xxxx
  //  x4 = x0 - 1.0 + 4.0 * C.xxxx
  vec4 x1 = x0 - i1 + C.xxxx;
  vec4 x2 = x0 - i2 + C.yyyy;
  vec4 x3 = x0 - i3 + C.zzzz;
  vec4 x4 = x0 + C.wwww;

// Permutations
  i = mod289(i); 
  float j0 = permute( permute( permute( permute(i.w) + i.z) + i.y) + i.x);
  vec4 j1 = permute( permute( permute( permute (
             i.w + vec4(i1.w, i2.w, i3.w, 1.0 ))
           + i.z + vec4(i1.z, i2.z, i3.z, 1.0 ))
           + i.y + vec4(i1.y, i2.y, i3.y, 1.0 ))
           + i.x + vec4(i1.x, i2.x, i3.x, 1.0 ));

// Gradients: 7x7x6 points over a cube, mapped onto a 4-cross polytope
// 7*7*6 = 294, which is close to the ring size 17*17 = 289.
  vec4 ip = vec4(1.0/294.0, 1.0/49.0, 1.0/7.0, 0.0) ;

  vec4 p0 = grad4(j0,   ip);
  vec4 p1 = grad4(j1.x, ip);
  vec4 p2 = grad4(j1.y, ip);
  vec4 p3 = grad4(j1.z, ip);
  vec4 p4 = grad4(j1.w, ip);

// Normalise gradients
  vec4 norm = taylorInvSqrt(vec4(dot(p0,p0), dot(p1,p1), dot(p2, p2), dot(p3,p3)));
  p0 *= norm.x;
  p1 *= norm.y;
  p2 *= norm.z;
  p3 *= norm.w;
  p4 *= taylorInvSqrt(dot(p4,p4));

// Mix contributions from the five corners
  vec3 m0 = max(0.6 - vec3(dot(x0,x0), dot(x1,x1), dot(x2,x2)), 0.0);
  vec2 m1 = max(0.6 - vec2(dot(x3,x3), dot(x4,x4)            ), 0.0);
  m0 = m0 * m0;
  m1 = m1 * m1;
  return 49.0 * ( dot(m0*m0, vec3( dot( p0, x0 ), dot( p1, x1 ), dot( p2, x2 )))
               + dot(m1*m1, vec2( dot( p3, x3 ), dot( p4, x4 ) ) ) ) ;

  }
























 void makeVert(float s, float t)
 {
 	vert_pos = V0 + s*V01 + t*V02;
 	vert_pos = normalize(vert_pos);
 	if (iter == 10) {
 		vert_pos *= 1.0 + snoise(vec4(vert_pos,time*0.5)) * 0.02;
 	}
ex_Color = geom_Color[0];
 	gl_Position = mvpmatrix * vec4(vert_pos, 1.);
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
