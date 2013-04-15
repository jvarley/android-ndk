#include "city.h"


void City::setVertices(GLfloat vertex,int index)
{
    gTriangleVertices[index] = vertex;
}


GLfloat* City::getVertices()
{
    return gTriangleVertices;
}


