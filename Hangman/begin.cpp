//print the beginning message and the blank answer
void begin(string answer) {
    cout << "Welcome to hangman! You have to try and guess the word I have chosen. ";
    cout << "You can make 6 errors, then I win!\n\n" << endl;
    
    //print the answer as " __ __ __ __ __"
    for (char letter : answer) {
        cout << " __ ";
    }
    cout << endl;
}
