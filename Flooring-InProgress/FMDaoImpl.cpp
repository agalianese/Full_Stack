#include <string>
#include<iostream>
#include<vector>
#include<sstream>
#include <map>

#include "FMDao.h"

using namespace std;

//this is the map for the order and their total information
map<int, FMOrder> orderMap;

//this is the map for the tax information
map<string, FMTax> taxMap;

//map for product information
map<string, FMProduct> productMap;


string ROSTER_FILE = "C:\\Users\\mc\\Documents\\NetBeansProjects\\FlooringMastery\\src\\main\\java\\orders\\SampleFileData\\Orders\\roster.txt";
string EXPORT_FILE = "C:\\Users\\mc\\Documents\\NetBeansProjects\\FlooringMastery\\src\\main\\java\\orders\\SampleFileData\\Backup\\DataExport.txt";
string ORDER_FILE = "C:\\Users\\mc\\Documents\\NetBeansProjects\\FlooringMastery\\src\\main\\java\\orders\\SampleFileData\\Orders\\Orders_";
string PRODUCT_FILE = "C:\\Users\\mc\\Documents\\NetBeansProjects\\FlooringMastery\\src\\main\\java\\orders\\SampleFileData\\Data\\Products.txt";
string TAX_FILE = "C:\\Users\\mc\\Documents\\NetBeansProjects\\FlooringMastery\\src\\main\\java\\orders\\SampleFileData\\Data\\Taxes.txt";




/** add the order to the hashmap using the order number as the key, and the order as the value
*@param returns the order that has been added*/
FMOrder FMDao::addItem(FMOrder order, string orderDate) {
    if (order.getOrderNumber().equals(-1000)) {
        order.setOrderNumber(maxOrderNumber());
    }

    orderMap.insert(order.getOrderNumber(), order);
    FMOrder newOrder = orderMap.insert(order.getOrderNumber(), order);
    writeOrders(orderDate);
    return newOrder;
}



/** remove order based on string title -- THIS SHOULD CHANGE
 * returns the removed order
 */
    FMOrder FMDao::removeOrder(int orderNumber, string orderDate)  {
    loadOrders(orderDate);
    FMOrder removedItem = orderMap.remove(orderNumber);
    return removedItem;
}


vector<FMOrder> FMDao::getAllOrders(string orderDate) {
    loadOrders(orderDate);
    //vector all of the items in the vending machine
    return vector(orderMap.values());
}


vector<FMProduct> FMDao::getAllProducts() {
    loadProducts();
    //vector all of the items in the vending machine
    return Arrayvector(productMap.values());
}


vector<FMTax> FMDao::getAllTaxes() {
    loadTaxes();
    //vector all of the items in the vending machine
    return Arrayvector(taxMap.values());
}


FMOrder FMDao::getOrder(int orderNumber, string orderDate) {
    loadOrders(orderDate);
    //load an item's information
    return orderMap.get(orderNumber);
}


//get the product's  information

FMProduct FMDao::getProduct(string productName) {
    loadProducts();
    return productMap.get(productName);
}

//get the state's  tax information

FMTax FMDao::getTax(string stateAbbrev) {
    loadTaxes();
    return taxMap.get(stateAbbrev);
}

/**method to find the largest number in the orderMap
 * @param orderMap
 * @return int
 */
int FMDao::maxOrderNumber() {
    //turns the keyset into a set, then finds the max number
    Set<int> orderNums = orderMap.keySet();
    if (orderNums.isEmpty()) {
        return(1);
    }
    int maxNum = max(orderNums);
    int newNum = maxNum + 1;
    return(newNum);
} //end of maxOrderNumber

//use the order date to access the true orderfile
string FMDao::finalOrderFile(string orderDate) {
    //access the actual order file 

    //get the value of the month
    string month = string.valueOf(orderDate.getMonthValue());

    //if its length is 1, add a 0 in front to correct the format
    if (month.length() == 1) {
        month = "0" + month;
    }

    //get the value of the day then correct the format if needed
    string day = string.valueOf(orderDate.getDayOfMonth());
    if (day.length() == 1) {
        day = "0" + day;
    }
    //get the year
    string year = string.valueOf(orderDate.getYear());

    //create a string using the correct format
    string correctFormat = month + day + year;
    string FINAL_ORDER_FILE = ORDER_FILE + correctFormat + ".txt";

    //return the actual order file
    return(FINAL_ORDER_FILE);
}


/** pull in the tax information, pull in the order, and return the order
 * @param state
 * @param order
 * @return order
 * @throws exceptions.FMPersistanceException
 */

FMOrder FMDao::insertInTax(string state, FMOrder order) {
    try {
        state = state.toUpperCase();
        FMTax Tax = getTax(state);
        //set the tax rate according to the state
        order.setTaxRate(Tax.getTaxRate());
    }
    catch (NullPointerException e) {
        throw FMPersistanceException(
            "We do not do business in that state, and this order cannot be completed.", e);
    }
    //return the order
    return(order);

}


//   ============== READING IN METHODS ==============     

/** takes in a text string of the order, and returns it as the order object
     *@param takes in order as text
     * @return FMOrder order */
FMOrder unmarshallOrder(string orderAsText) {
    //create a vector to put orderAsText after its split on its delimiter
    vector<string> orderTokens;

    //create string stream from the string
    stringstream s_stream(orderAsText); 
    while (s_stream.good()) {
        //get string delimited by comma and add it to orderTokens
        string substr;
        getline(s_stream, substr, ',');
        orderTokens.push_back(substr);
    }

    //create a new order
    FMOrder orderFromFile = FMOrder();

    // 1. start with order number
    int orderNumber = stoi(orderTokens.at(0));
    orderFromFile.setOrderNumber(orderNumber);

    //2. string customer name
    orderFromFile.setCustomerName(orderTokens.at(1));
    //3. string state - two letter code
    orderFromFile.setState(orderTokens.at(2));
    //4. BD tax rate for that stat
    float taxRate = stof(orderTokens.at(3));
    orderFromFile.setTaxRate(taxRate);
    //5. string product type
    orderFromFile.setProductType(orderTokens.at(4));
    //6. BD area
    float area = stof(orderTokens.at(5));
    orderFromFile.setArea(area);
    //7. BD costSqFt 
    float costSqFt = stof(orderTokens.at(6));
    orderFromFile.setCostSqFt(costSqFt);
    //8. BD laborCostSqFt
    float laborCostSqFt = stof(orderTokens.at(7));
    orderFromFile.setLaborCostSqFt(laborCostSqFt);
    //9. BD material cost
    float materialCost = stof(orderTokens.at(8));
    orderFromFile.setMaterialCost(materialCost);
    //10. BD labor cost
    float laborCost = stof(orderTokens.at(9));
    orderFromFile.setLaborCost(materialCost);
    //11. tax
    float tax = stof(orderTokens.at(10));
    orderFromFile.setTax(tax);
    //12. total amount
    float total = stof(orderTokens.at(11));
    orderFromFile.setTotal(total);

    //return the object 
    return orderFromFile;
} //end of unmarshallOrder


/** read in the order file and adds it to the order hash ma
 * @param orderDate
 * @throws exceptions.FMPersistanceException*/
void loadOrders(string orderDate) {

    string FINAL_ORDER_FILE = finalOrderFile(orderDate);
    createOrderFile(FINAL_ORDER_FILE);
    Scanner scanner;
    try {
        // Create Scanner for reading the file
        scanner = Scanner(
            BufferedReader(
                FileReader(FINAL_ORDER_FILE)));
    }
    catch (FileNotFoundException e) {
        throw FMPersistanceException(
            "Could not find order file.", e);
    }
    // currentLine holds the most recent line read from the file
    string currentLine;
    // currentItem holds the most recent item unmarshalled
    FMOrder currentItem;
    // Go through ORDER_FILE line by line, decoding each line into an object by calling the unmarshallOrder method.
    // Process while we have more lines in the file
    while (scanner.hasNextLine()) {
        // get the next line in the file
        currentLine = scanner.nextLine();
        // unmarshall the line into an item
        currentItem = unmarshallOrder(currentLine);
        System.out.println(currentItem);
        // We are going to use the name as the map key
        orderMap.insert(currentItem.getOrderNumber(), currentItem);
    }
    // close scanner
    scanner.close();
} //end of loadItems


//06022013 to 2013-06-02
string reformatDate(string fileName) {
    string shortStr = fileName.substring(7, 15);
    string month = shortStr.substring(0, 2);
    string day = shortStr.substring(2, 4);
    string year = shortStr.substring(4);

    string correct = year + "-" + month + "-" + day;
    string orderDate = string.parse(correct);
    return orderDate;
}

/**
 * @param taxAsText read in the tax file and return the tax object per state
 * @return */
FMTax unmarshallTaxRate(string taxAsText) {  //split the ininsert on its delimiter
        //split by the delimiter

    vector<string> orderTokens;

    //create string stream from the string
    stringstream s_stream(taxAsText);
    while (s_stream.good()) {
        //get string delimited by comma and add it to orderTokens
        string substr;
        getline(s_stream, substr, ',');
        orderTokens.push_back(substr);
    }
    //create new tax object
    FMTax taxObject = FMTax();

    //fill in the object and return it
    taxObject.setStateAbbreviation(orderTokens.at(0));
    taxObject.setStateName(orderTokens.at(1));
    float taxRate = stof(orderTokens.at(2));
    taxObject.setTaxRate(taxRate);

    //return the tax rate
    return(taxObject);
}//end of readTaxRate

/** read in the tax file and adds it to the hash map */
void loadTaxes() {

    Scanner scanner;
    try {
        // Create Scanner for reading the file
        scanner = Scanner(
            BufferedReader(
                FileReader(TAX_FILE)));
    }
    catch (FileNotFoundException e) {
        throw FMPersistanceException(
            "Could not find tax file.", e);
    }
    // currentLine holds the most recent line read from the file
    string currentLine;
    // currentItem holds the most recent item unmarshalled
    FMTax currentItem;
    // Go through ORDER_FILE line by line, decoding each line into an object by calling the unmarshallOrder method.
    // Process while we have more lines in the file
    while (scanner.hasNextLine()) {
        // get the next line in the file
        currentLine = scanner.nextLine();
        // unmarshall the line into an item
        currentItem = unmarshallTaxRate(currentLine);

        // We are going to use the name as the map key
        taxMap.insert(currentItem.getStateAbbreviation(), currentItem);
    }
    // close scanner
    scanner.close();
} //end of loadItems

/** read in the tax file and return the tax object per stat
 * @param productAsText
 * @return */
FMProduct unmarshallProducts(string productAsText) {  

    //create a vector to put orderAsText after its split on its delimiter
    vector<string> orderTokens;

    //create string stream from the string
    stringstream s_stream(productAsText);
    while (s_stream.good()) {
        //get string delimited by comma and add it to orderTokens
        string substr;
        getline(s_stream, substr, ',');
        orderTokens.push_back(substr);
    }

    //create a object from the state abbreviation
    string productType = orderTokens[0];
    FMProduct Product = FMProduct();
    Product.setProductType(productType);

    //fill in the rest of the object and return it
    float costSqFt = stof(orderTokens.at(1));
    Product.setCostSqFt(costSqFt);

    float laborCostSqFt = stof(orderTokens.at(2));
    Product.setLaborCostSqFt(laborCostSqFt);

    //return the tax rate
    return(Product);
}//end of readTaxRate

/** read in the product file and adds it to the hash map */
void loadProducts() {

    try {
        // Create Scanner for reading the file
        scanner = Scanner(
            BufferedReader(
                FileReader(PRODUCT_FILE)));
    }
    catch (FileNotFoundException e) {
        throw FMPersistanceException(
            "Could not find product file.", e);
    }
    // currentLine holds the most recent line read from the file
    string currentLine;
    // currentItem holds the most recent item unmarshalled
    FMProduct currentItem;
    // Go through PRODUCT_FILE line by line, decoding each line into an object by calling the unmarshallOrder method.
    // Process while we have more lines in the file
    while (scanner.hasNextLine()) {
        // get the next line in the file
        currentLine = scanner.nextLine();
        // unmarshall the line into an item
        currentItem = unmarshallProducts(currentLine);

        // We are going to use the name as the map key
        productMap.insert(currentItem.getProductType(), currentItem);
    }
    // close scanner
    scanner.close();
} //end of loadItems





//    ============ WRITING OUT METHODS ===============

/** method to turn a order of class FMOrder into a text string
  @param FMOrder order
  @return text string of that order  */
string marshallOrder(FMOrder anOrder) {
    // We need to turn an object into a line of text for our file.

        // 1. start with order number
    string orderAsText = anOrder.getOrderNumber() + ",";
    //2. string customer name
    orderAsText += anOrder.getCustomerName() + ",";
    //3. string state - two letter code
    orderAsText += anOrder.getState() + ",";
    //4. BD tax rate for that state
    orderAsText += to_string(anOrder.getTaxRate()) + ",";
    //5. string product type
    orderAsText += anOrder.getProductType() + ",";
    //6. BD area
    orderAsText += to_string(anOrder.getArea()) + ",";
    //7. BD costSqFt
    orderAsText += to_string(anOrder.getCostSqFt()) + ",";
    //8. BD laborCostSqFt
    orderAsText += to_string(anOrder.getLaborCostSqFt()) + ",";
    //9. BD material cost
    orderAsText += to_string(anOrder.getMaterialCost()) + ",";
    //10. BD labor cost
    orderAsText += to_string(anOrder.getLaborCost()) + ",";
    //11. tax
    orderAsText += to_string(anOrder.getTax()) + ",";
    //12. total amount
    orderAsText += anOrder.getTotal();

    // return final order as text
    return orderAsText;
} //end of marshall object

void createOrderFile(string finalFile) {

    try {
        File newFile = File(finalFile);
        if (newFile.createNewFile()) {
        }
    }
    catch (IOException e) {
        throw FMPersistanceException("Order file couldn't be created.", e);
    }

}



/**writes out the information in the order to a file */
void writeOrders(string orderDate) {
    //try-catch statements
    string FINAL_ORDER_FILE = finalOrderFile(orderDate);
    createOrderFile(FINAL_ORDER_FILE);
    PrintWriter out;

    try {
        out = PrintWriter(FileWriter(FINAL_ORDER_FILE));
    }
    catch (IOException e) {
        throw FMPersistanceException(
            "Could not save order data.", e);
    }



    // Write out the objects to the order file.
    string studentAsText;
    vector<FMOrder> itemsvector = Arrayvector(orderMap.values());
    //vector<FMOrder> ordersvector =
    for (FMOrder item : itemsvector) {
        // turn an item into a string
        studentAsText = marshallOrder(item);
        // write the object to the file
        out.write(studentAsText + "\n");
        // force PrintWriter to write line to the file
        out.flush();
    }
    // Clean up
    out.close();
}

/** method to turn a order of class FMOrder into a text string
@param FMOrder order
@return text string of that order  */
string marshallExportData(FMOrder anOrder) {
    // We need to turn an object into a line of text for our file.

        // 1. start with order number
    string orderAsText = anOrder.getOrderNumber() + ",";
    //2. string customer name
    orderAsText += anOrder.getCustomerName() + ",";
    //3. string state - two letter code
    orderAsText += anOrder.getState() + ",";
    //4. float tax rate for that stat
    orderAsText += anOrder.getTaxRate() + ",";
    //5. string product type
    orderAsText += anOrder.getProductType() + ",";
    //6. BD area
    orderAsText += anOrder.getArea() + ",";
    //7. BD costSqFt
    orderAsText += anOrder.getCostSqFt() + ",";
    //8. BD laborCostSqFt
    orderAsText += anOrder.getLaborCostSqFt() + ",";
    //9. BD material cost
    orderAsText += anOrder.getMaterialCost() + ",";
    //10. BD labor cost
    orderAsText += anOrder.getLaborCost() + ",";
    //11. tax
    orderAsText += anOrder.getTax() + ",";
    //12. total amount
    orderAsText += anOrder.getTotal() + ",";

    //get the order date and write it here
    orderAsText += anOrder.getOrderDate();

    // return final order as text
    return orderAsText;
} //end of marshall object



void exportData() {
    //the path to be searched
    File path = File("C:\\Users\\mc\\Documents\\NetBeansProjects\\FlooringMastery\\src\\main\\java\\orders\\SampleFileData\\Orders");
    PrintWriter out;

    //write out to the export file and append 
    try {
        out = PrintWriter(FileWriter(EXPORT_FILE));
    }
    catch (IOException e) {
        throw FMPersistanceException("Could not save export file.", e);
    } //end of catch

    //create a vector of files to be read through
    File[] files = path.vectorFiles();
    for (File file : files) {
        string fileName = file.getName();
        System.out.println("in " + fileName);

        string orderDate = reformatDate(fileName);
        vector<FMOrder> ordersvector = getAllOrders(orderDate);
        System.out.println("orders vector size is " + ordersvector.size() + "\n");
        System.out.println(orderMap.size());
        ordersvector.stream().map(order -> {
            System.out.println(" writing out " + order.getCustomerName());
            return order;
        }).map(order -> {
            order.setOrderDate(orderDate);
            return order;
        }).map(order->marshallExportData(order)).map(orderAsText -> {
            out.write(orderAsText + "\n");
            return orderAsText;
        }).forEachOrdered(_item -> {
            out.flush();
        }); //end of for order in ordervector loop
    }//end of for file in files loop
// Clean up
    out.close();
} //end of export data 5

} //end of class FMDaoFileImpl
