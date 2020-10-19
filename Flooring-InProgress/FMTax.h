#pragma once
#include <string>

using namespace std;

class FMTax {
public:

    FMTax();
    ~FMTax();
    string stateAbbreviation;
    string stateName;
    float taxRate;

    string getStateAbbreviation() {}
    void setStateAbbreviation(string stateAbbreviation) {}

    string getStateName() {}
    void setStateName(string stateName) {}

    float getTaxRate() {}
    void setTaxRate(float taxRate) {}

private:
    string mStateAbbreviation;
    string mStateName;
    float mTaxRate;
};