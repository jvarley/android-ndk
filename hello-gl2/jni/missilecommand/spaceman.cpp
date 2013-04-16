#include "spaceman.h"


void Spaceman::setVertices(GLfloat vertex,int index)
{
    gTriangleVertices[index] = vertex;
}


GLfloat* Spaceman::getVertices()
{
    return gTriangleVertices;
}


