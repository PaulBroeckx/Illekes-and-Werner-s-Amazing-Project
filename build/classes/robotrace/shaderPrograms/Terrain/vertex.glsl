#version 120
// simple vertex shader

float offset(vec3 p) {
	vec3 v = normalize(p);
        v.z = 0.6 * cos(0.3*v.x + 0.2*v.y) + 0.4 * cos(v.x - 0.5*v.y);
        return v; 
}

void main() {
	// convert from homogeneous coordinates to 3D-coordinates
	vec3 p = gl_Vertex.xyz/gl_Vertex.w;

	// output of vertex shader
	gl_FrontColor = gl_Color;
	gl_TexCoord[0] = gl_MultiTexCoord0;
	gl_Position = gl_ModelViewProjectionMatrix * vec4(p, 1);
}