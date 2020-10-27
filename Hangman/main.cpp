#include <iostream>
#include <string>
#include <algorithm>

#include "chooseAnswer"
#include "displayAnswer"
#include "begin"

using namespace std;

//main method
int main() {
    
    //declare variables
    string keepGoing;
    bool keepPlaying = true;
    

    //do while loop to keep plaing
    do {
        
        //reset the answer and showAnswer
        string answer = chooseAnswer();
        string showCorrect = "";
        
        //have showCorrect be equal to ----- for however long the answer is
        for (char let : answer) {
            showCorrect.append("-");
        }
        
        //beginning method 
        begin(answer);
        
         //allow the user to guess 6 times
         for (int i = 0; i < 6; i++) {
            bool correct = true;
            
            //while the game is not finished and they haven't made a wrong guess
            while (correct && showCorrect != answer) {
                
                //output how many lives they have
                int lives = 6-i;
                cout << "Lives: " << lives << endl;
                
                //take in their guess and update the answer and showCorrect
                char guess;
                cin >> guess;
                correct = displayAnswer(answer, showCorrect, guess);
                
                //if they make a wrong guess, print that they have lost a life
                if (correct == false) {
                    cout << "\nIncorrect. You have lost a life. \n" << endl;
                }
            } //end of while 
            
         }//end of for loop allowing user to guess
        
        
        if (showCorrect == answer) {
            cout << "Congratulations! You have won." << endl;
        } else {
            cout << "Your word was " << answer << ". Better luck next time!" << endl;
        }
            
        //ask the user to keep playing then make it upper
        cout << "Would you like to keep playing? Y or N. ";
        cin >> keepGoing;
        transform(keepGoing.begin(), keepGoing.end(), keepGoing.begin(), ::toupper);
            
            
        //cout << boolalpha << keepGoing "Y" << endl;
        if (keepGoing != "Y") {
            keepPlaying = false;
        }
            
            
    } while (keepPlaying);
       
    //exit 
    cout << "\nGoodbye!";
    return 0;
        
} //end of main
