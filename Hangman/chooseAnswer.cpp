#include <iostream>
#include <string>
#include <vector>
#include <chrono>

std::string chooseAnswer() {
    //create a vector of the answers
    std::vector<std::string> answers;
    
    //randomly generate a number from 0-9
    srand (time(NULL));
    int randNum = rand() % 10;
    
    //create answers and put them in my vector
    answers.push_back("national");
    answers.push_back("destination");
    answers.push_back("incredible");
    answers.push_back("unfortunately");
    answers.push_back("piercing");
    answers.push_back("canyon");
    answers.push_back("because");
    answers.push_back("cabinet");
    answers.push_back("wallpaper");
    answers.push_back("beginning");
    
    //return a random answer from the vector
    return(answers[randNum]);
    
}