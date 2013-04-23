#include "../chipmunk/include/chipmunk/chipmunk.h"
#include <vector>
class gameObject
{

public:
    gameObject();
    void setMass(int);
    void setX(int);
    void SetY(int);
    void SetR(int);

  
    void setBody(cpBody);
   

    
private:
    std::vector<cpBody*> cpBodies;
    cpBody* spaceman;
    cpBody* endMoon;
    bool hasFired;
    bool missionComplete;
};
