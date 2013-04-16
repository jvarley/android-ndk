#include "missilecommand.h"
#include <iostream>
#include <string>
#include "../chipmunk/include/chipmunk/chipmunk.h"

using namespace std;

int POINTS_PER_CITY = 6;
cpSpace *space = cpSpaceNew();

Missilecommand::Missilecommand()
{
    hasSpaceman1 = false;
    hasFired = false;

}

void Missilecommand::fire(float velocity)
{

    cpBodySetVel(spaceman, {velocity,0});
    hasFired = true;

}

void Missilecommand::createGame() {
    cpBody* planetBody= cpBodyNew(10,10);
    //cpBody* planetBody= cpBodyNew(INFINITY, INFINITY);
    cpBodySetAngVel(planetBody, 0.2f);
    float xf = 240;
    float yf = 400;
    cpVect p = {xf,yf};
    cpBodySetPos(planetBody,p );

    cpSpaceAddBody(space,planetBody);

    cpBodies.push_back(planetBody);


    cpBody* spacemanBody= cpBodyNew(10,10);
    cpBodySetAngVel(spacemanBody, 0.2f);
    float spaceManxf = 200.0;
    float spaceManyf = 200.0;
    cpVect pSpaceMan = {spaceManxf,spaceManyf};
    cpBodySetPos(spacemanBody,pSpaceMan );
    cpSpaceAddBody(space,spacemanBody);
    cpBodySetVelLimit(spacemanBody, 1000.0);


    spaceman = spacemanBody;
    hasSpaceman1 = true;
}

void Missilecommand::updateGame() {
    if(hasSpaceman1 && hasFired){
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
        impulse = cpvadd(impulse, cpvmult(delta,-200000.0/(sqdist*cpfsqrt(sqdist))));
    }
    return impulse;
}
void Missilecommand::handleTouch(float x, float y) {

    
}


int Missilecommand::getNumBodies() {
    return cpBodies.size();
}

int Missilecommand::getPointsPerCity() {
    return POINTS_PER_CITY;
}

cpBody* Missilecommand::getBody(int i) {
    return cpBodies.at(i);
}

cpBody* Missilecommand::getSpaceman() {
    return spaceman;
}

bool Missilecommand::hasSpaceman() {
    return hasSpaceman1;
}



