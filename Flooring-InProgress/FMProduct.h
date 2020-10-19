#pragma once
#include <string>

using namespace std;


class FMProduct {
public:
    FMProduct();
    ~FMProduct();

    string productType;
    float costSqFt;
    float laborCostSqFt;

    string getProductType() {}
    void setProductType(string productType) { }

    float getCostSqFt() {}
    void setCostSqFt(float costSqFt) {}

    float getLaborCostSqFt() {}
    void setLaborCostSqFt(float laborCostSqFt) {}

private:
    string mProductType;
    float mCostSqFt;
    float mLaborCostSqFt;
       
};