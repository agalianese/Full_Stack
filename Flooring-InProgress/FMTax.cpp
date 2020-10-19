#include <string>

#include "FMTax.h"

using namespace std;



    FMTax::FMTax() {}
    FMTax::~FMTax() {}

    string FMTax::getStateAbbreviation() {
        return mStateAbbreviation;
    }

    void FMTax::setStateAbbreviation(string stateAbbreviation) {
        mStateAbbreviation = stateAbbreviation;
    }

    string FMTax::getStateName() {
        return mStateName;
    }

    void FMTax::setStateName(string stateName) {
        mStateName = stateName;
    }

    float FMTax::getTaxRate() {
        return mTaxRate;
    }

    void FMTax::setTaxRate(float taxRate) {
        mTaxRate = taxRate;
    }