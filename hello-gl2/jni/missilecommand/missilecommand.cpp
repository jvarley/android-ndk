#include "missilecommand.h"
#include <iostream>
#include <string>
#include "../chipmunk/include/chipmunk/chipmunk.h"
//#include "../../../chipmunk.h"
using namespace std;

int POINTS_PER_CITY = 6;
cpSpace *space = cpSpaceNew();

Missilecommand::Missilecommand()
{



    City* city1 = new City();
    City* city2 = new City();
    City* city3 = new City();

    GLfloat cityVertices1[] = {-0.2f,  0.0f, -0.3f,  0.0f, -0.25f, 0.1f };
    GLfloat cityVertices2[] = { 0.05f, 0.0f, -0.05f, 0.0f,  0.0f,  0.1f };
    GLfloat cityVertices3[] = { 0.2f,  0.0f,  0.3f,  0.0f,  0.25f, 0.1f };
    //GLfloat cityVertices3[] = { 0.5f,  0.5f,  0.5f,  -0.5f,  0.0f, 0.0f };

    for(int i= 0; i < POINTS_PER_CITY; i++){
        city1->setVertices(cityVertices1[i],i);
        city2->setVertices(cityVertices2[i],i);
        city3->setVertices(cityVertices3[i],i);
    }

    cities.push_back(city1);
    cities.push_back(city2);
    cities.push_back(city3);
    //cpSpaceSetGravity(space, cpv(0, -100));

}

void Missilecommand::updateGame() {
    City* city= cities.at(1);
    float value = city->getVertices()[0];
    value += .01;
    if (value > 1){
        value = 0;
    }
    city->getVertices()[0] = value;
    cpSpaceStep(space, 1.0f/60.0f);
}
void Missilecommand::handleTouch(float x, float y) {
    City* city= cities.at(1);
    float value =0;
    city->getVertices()[0] = value;

    cpBody* planetBody= cpBodyNew(10, 10);
    //cpBody* planetBody= cpBodyNew(INFINITY, INFINITY);
    cpBodySetAngVel(planetBody, 0.2f);
    float xf = x;
    float yf = y;
    cpVect p = {xf,yf};
    cpBodySetPos(planetBody,p );

    //cpVect vel = {0.01,0.1};
    //cpBodySetVel(planetBody,vel );

    cpSpaceAddBody(space,planetBody);

    cpBodies.push_back(planetBody);
}

int Missilecommand::getNumCities() {
    return cities.size();
}

int Missilecommand::getNumBodies() {
    return cpBodies.size();
}

int Missilecommand::getPointsPerCity() {
    return POINTS_PER_CITY;
}

City* Missilecommand::getCity(int i) {
    return cities.at(i);
}

cpBody* Missilecommand::getBody(int i) {
    return cpBodies.at(i);
}



