
#include "city.h"
#include "../chipmunk/include/chipmunk/chipmunk.h"
#include <vector>
class Missilecommand 
{

public:
    Missilecommand();
    void handleTouch(float, float);
    void updateGame();
    int getPointsPerCity();
    int getNumCities();
    int getNumBodies();
    City* getCity(int i);
    cpBody* getBody(int i);
    
private:
    std::vector<City*> cities;
    std::vector<cpBody*> cpBodies;
};




