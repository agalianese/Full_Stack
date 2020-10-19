#include <string>
#include <ctime>

#include "FMOrder.h"


using namespace std;



//default constructor and destructor
    FMOrder::FMOrder() {}
    FMOrder::~FMOrder() {}

    //getter and setters

    int FMOrder::getOrderNumber() {
        return mOrderNumber;
    }

    void FMOrder::setOrderNumber(int orderNumber) {
        mOrderNumber = orderNumber;
    }

    string FMOrder::getCustomerName() {
        return mCustomerName;
    }

    void FMOrder::setCustomerName(string customerName) {
        mCustomerName = customerName;
    }

    string FMOrder::getState() {
        return mState;
    }

    void FMOrder::setState(string state) {
        mState = state;
    }
     float FMOrder::getTaxRate() {
        return mTaxRate;
    }

     void FMOrder::setTaxRate(float taxRate) {
        mTaxRate = taxRate;
    }

     string FMOrder::getProductType() {
        return mProductType;
    }

     void FMOrder::setProductType(string productType) {
        mProductType = productType;
    }

     float FMOrder::getArea() {
        return mArea;
    }

     void FMOrder::setArea(float area) {
        mArea = area;
    }

     float FMOrder::getCostSqFt() {
        return mCostSqFt;
    }

     void FMOrder::setCostSqFt(float costSqFt) {
        mCostSqFt = costSqFt;
    }

     float FMOrder::getLaborCostSqFt() {
        return mLaborCostSqFt;
    }

     void FMOrder::setLaborCostSqFt(float laborCostSqFt) {
        mLaborCostSqFt = laborCostSqFt;
    }

     float FMOrder::getMaterialCost() {
        return mMaterialCost;
    }

     void FMOrder::setMaterialCost(float materialCost) {
        mMaterialCost = materialCost;
    }

     float FMOrder::getLaborCost() {
        return mLaborCost;
    }

     void FMOrder::setLaborCost(float laborCost) {
        mLaborCost = laborCost;
    }

     float FMOrder::getTax() {
        return mTax;
    }

     void FMOrder::setTax(float tax) {
        mTax = tax;
    }

     float FMOrder::getTotal() {
        return mTotal;
    }

     void FMOrder::setTotal(float total) {
        mTotal = total;
    }

     string FMOrder::getOrderDate() {
        return mOrderDate;
    }

     void FMOrder::setOrderDate(string orderDate) {
        mOrderDate = orderDate;
    }