#version 120
// simple vertex shader
uniform sampler2D tex; 

void main()
{
    gl_Position    = gl_ModelViewProjectionMatrix * gl_Vertex;      // model view transform
    gl_FrontColor = gl_Color;
    gl_TexCoord[0] = gl_MultiTexCoord0;
}