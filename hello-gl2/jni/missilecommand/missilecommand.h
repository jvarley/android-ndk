
#include "spaceman.h"
#include "../chipmunk/include/chipmunk/chipmunk.h"
#include <vector>
class Missilecommand 
{

public:
    Missilecommand();
    void handleTouch(float, float);
    void updateGame();
    void createGame();
    void fire(float);
    int getPointsPerCity();
    int getNumBodies();
    cpBody* getBody(int i);
    cpBody* getSpaceman();
    bool hasSpaceman();
    cpVect getImpulseOnSpaceman();

    
private:
    std::vector<cpBody*> cpBodies;
    cpBody* spaceman;
    bool hasSpaceman1;
    bool hasFired;
};




