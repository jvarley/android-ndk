
#include "../chipmunk/include/chipmunk/chipmunk.h"
#include <vector>
class Missilecommand 
{

public:
    Missilecommand();
    void updateGame();
    void fire(int, int);
    void reset();
    int getNumBodies();
    cpBody* getBody(int i);
    cpBody* getSpaceman();
    cpVect getImpulseOnSpaceman();
    void addBody(int x, int y, int r, int m);
    void addEndMoon(int x, int y, int r, int m);
    void addSpaceMan(int x, int y, int r, int m);

    
private:
    std::vector<cpBody*> cpBodies;
    std::vector<int> bodyMasses; 
    
    cpBody* spaceman;
    cpBody* endMoon;

    bool hasFired;
    bool missionComplete;
    int missionCompleteDistance;
};




