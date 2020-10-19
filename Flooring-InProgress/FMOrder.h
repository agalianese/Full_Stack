#include <string>
#include <ctime>


using namespace std;


class FMOrder {
public:
    FMOrder();
    ~FMOrder();
    int orderNumber; //1
    string customerName; //2
    string state; //3
    float taxRate; //4
    string productType; //5
    float area; //6
    float costSqFt; //7
    float laborCostSqFt; // 8
    float materialCost; //9
    float laborCost; //10
    float tax;  //11
    float total; //12

    string orderDate; //not written out

    //getters and setters
    int getOrderNumber() {}
    void setOrderNumber(int orderNumber) {}

    string getCustomerName() {}
    void setCustomerName(string customerName) {}

    string getState();
    void setState(string state) {}

    float getTaxRate() {}
    void setTaxRate(float taxRate) {}

    string getProductType() { }
    void setProductType(string productType) {}

    float getArea() {}
    void setArea(float area) {}

    float getCostSqFt() {}
    void setCostSqFt(float costSqFt) {}

    float getLaborCostSqFt() {}
    void setLaborCostSqFt(float laborCostSqFt) {}

    float getMaterialCost() {}
    void setMaterialCost(float materialCost) {}

    float getLaborCost() { }
    void setLaborCost(float laborCost) {}

    float getTax() {}
    void setTax(float tax) {}

    float getTotal() {}
    void setTotal(float total) {}

    string getOrderDate() {}
    void setOrderDate(string orderDate) {}

private:
    int mOrderNumber; //1
    string mCustomerName; //2
    string mState; //3
    float mTaxRate; //4
    string mProductType; //5
    float mArea; //6
    float mCostSqFt; //7
    float mLaborCostSqFt; // 8
    float mMaterialCost; //9
    float mLaborCost; //10
    float mTax;  //11
    float mTotal; //12

    string mOrderDate; //this will not be written out
};