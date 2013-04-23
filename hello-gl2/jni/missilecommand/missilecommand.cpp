#include "missilecommand.h"
#include <iostream>
#include <string>
#include "../chipmunk/include/chipmunk/chipmunk.h"

using namespace std;

cpSpace *space = cpSpaceNew();

Missilecommand::Missilecommand()
{
    hasFired = false;
    missionComplete = false;
    missionCompleteDistance = 0;
}

void Missilecommand::reset()
{
    hasFired = false;
    missionComplete = false;
    missionCompleteDistance = 0;
    //cpSpaceDestroy(space);
    //cpSpaceFree(space);
    space = cpSpaceNew();
    cpBodies.clear();
    bodyMasses.clear();
    

}

void Missilecommand::fire(int x, int y)
{
    if(!hasFired){
        float xf = x;
        float yf = y;
        cpBodySetVel(spaceman, {xf,yf});
    }
    
    hasFired = true;
}

void Missilecommand::updateGame() {
    if(hasFired){
        cpBodyApplyImpulse(spaceman, getImpulseOnSpaceman(),{0,0});
    }
    
    cpSpaceStep(space, 1.0f/60.0f);
}

cpVect Missilecommand::getImpulseOnSpaceman(){
    cpVect impulse = {0.0,0.0};
    for(int j =0; j < getNumBodies(); j++){
        cpBody* body = getBody(j);
        cpVect delta = cpvsub(spaceman->p,body->p);
        cpFloat sqdist = cpvlengthsq(delta);
        cpFloat planetMass = bodyMasses.at(j);
        cpFloat spacemanMass = cpBodyGetMass(spaceman);
        impulse = cpvadd(impulse, cpvmult(delta,-1000.0*planetMass*spacemanMass/(sqdist*cpfsqrt(sqdist))));
    }
    return impulse;
}

int Missilecommand::getNumBodies() {
    return cpBodies.size();
}

cpBody* Missilecommand::getBody(int i) {
    return cpBodies.at(i);
}

void Missilecommand::addBody(int x, int y, int r, int mass) {
    //cpBody* planetBody= cpBodyNew(10,10);
    cpBody* planetBody= cpBodyNew(INFINITY, INFINITY);
    
    float xf = x;
    float yf = y;
    cpVect p = {xf,yf};
    cpFloat moment = cpMomentForCircle(mass, 0, r, cpvzero);

    //cpBody* planetBody= cpBodyNew(mass,moment);

    cpBodySetPos(planetBody,p );
    //cpBodySetAngVel(planetBody, 0.2f);
    //cpSpaceAddBody(space,planetBody); // don't add or it will start to moce

    cpShape *shape = cpSpaceAddShape(space, cpCircleShapeNew(planetBody, r, cpvzero));
    cpBodies.push_back(planetBody);
    bodyMasses.push_back(mass);
}

void Missilecommand::addEndMoon(int x, int y, int r, int mass) {
    missionCompleteDistance += r;
    float endMoonxf = x;
    float endMoonyf = y;
    cpVect pEndMoon = {endMoonxf,endMoonyf};
    cpFloat moment = cpMomentForCircle(mass, 0, r, cpvzero);

    //cpBody* endMoonBody= cpBodyNew(mass,moment);
    cpBody* endMoonBody= cpBodyNew(INFINITY, INFINITY);

    cpBodySetPos(endMoonBody,pEndMoon );
    //cpSpaceAddBody(space,endMoonBody);

    cpShape *shape = cpSpaceAddShape(space, cpCircleShapeNew(endMoonBody, r, cpvzero));
    endMoon = endMoonBody;
    cpBodies.push_back(endMoonBody);
    bodyMasses.push_back(mass);
}

void Missilecommand::addSpaceMan(int x, int y, int r, int mass) {
    missionCompleteDistance += r + 2;
    float spaceManxf = x;
    float spaceManyf = y;
    cpVect pSpaceMan = {spaceManxf,spaceManyf};
    cpFloat moment = cpMomentForCircle(mass, 0, r, cpvzero);

    cpBody* spacemanBody= cpBodyNew(mass,moment);

    cpBodySetAngVel(spacemanBody, 0.2f);
    cpBodySetPos(spacemanBody,pSpaceMan);

    cpSpaceAddBody(space,spacemanBody);
    cpBodySetVelLimit(spacemanBody, 1000.0);

    cpShape *shape = cpSpaceAddShape(space, cpCircleShapeNew(spacemanBody, r, cpvzero));

    spaceman = spacemanBody;
}

cpBody* Missilecommand::getSpaceman() {
    return spaceman;
}




