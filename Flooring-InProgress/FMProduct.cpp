#include <string>
#include "FMProduct.h"

using namespace std;

    //default constructor and destructor
    FMProduct::FMProduct() {}
    FMProduct::FMProduct() {}

    string FMProduct::getProductType() {
        return mProductType;
    }

    void FMProduct::setProductType(string productType) {
        mProductType = productType;
    }

    float FMProduct::getCostSqFt() {
        return mCostSqFt;
    }

    void FMProduct::setCostSqFt(float costSqFt) {
        mCostSqFt = costSqFt;
    }

    float FMProduct::getLaborCostSqFt() {
        return mLaborCostSqFt;
    }

    void FMProduct::setLaborCostSqFt(float laborCostSqFt) {
        mLaborCostSqFt = laborCostSqFt;
    }