#pragma once
#include "FMOrder.cpp"
#include "FMTax.cpp"
#include "FMProduct.cpp"
#include <string>
#include <vector>


using namespace std;


class FMDao {
    //interface of basic methods

    //add order to the database
    FMOrder addItem(FMOrder order, string orderDate);

    //vector all of the orders
    vector <FMOrder> getAllOrders(string orderDate);

    //vector all of the products and its information
    vector<FMProduct> getAllProducts();

    //vector all of the taxes and its information
    vector<FMTax> getAllTaxes();

    //get the order's  information
    FMOrder getOrder(int orderNumber, string orderDate);

    //get the product's  information
    FMProduct getProduct(string productName);

    //get the state's  tax information
    FMTax getTax(string stateAbbrev);

    //remove the item
    FMOrder removeOrder(int orderNumber, string orderDate);

    //fill in tax informaion
    FMOrder putInTaxInfo(string state, FMOrder order);

    void loadOrders(string orderDate);

    void exportData();

};