#include <GLES2/gl2.h>
#include <GLES2/gl2ext.h>


class Spaceman
{
public:
	void setVertices(GLfloat vertex, int i);
    GLfloat* getVertices();

private:
    GLfloat gTriangleVertices[6];
};
