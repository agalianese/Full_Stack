#include <iostream>
#include <string>

using namespace std;


//displayAnswer based on the guess
bool displayAnswer(string &answer, string &showCorrect, char guess) {
    //declare correctGuess as false
    bool correctGuess = false;
    
    //iterate through the answer
    for (int i = 0; i < answer.size(); i++) {
        
        //if that letter has already been guess, show that
        if (showCorrect[i] != '-') {
            cout << " " << showCorrect[i] << " ";
        
            
        //if the guess is correct, update showCorrect and display that letter 
        } else if (guess == answer[i]) {
            showCorrect[i] = guess;
            cout << " " << guess << " ";
            correctGuess = true;
            
        //otherwise print the letter as a blank
        } else {
            cout << " __ ";
        } //end of else
    } //end of for loop
    
    //new line and return if the guess was correct
    cout << endl;
    return correctGuess;
}