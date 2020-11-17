import React from 'react';

import slide1 from "./components/HangmanImages/Slide1.JPG";
import slide2 from "./components/HangmanImages/Slide2.JPG";
import slide3 from "./components/HangmanImages/Slide3.JPG";
import slide4 from "./components/HangmanImages/Slide4.JPG";
import slide5 from "./components/HangmanImages/Slide5.JPG";
import slide6 from "./components/HangmanImages/Slide6.JPG";
import slide7 from "./components/HangmanImages/Slide7.JPG";
import slide8 from "./components/HangmanImages/Slide8.JPG";
import slide9 from "./components/HangmanImages/Slide9.JPG";
import slide10 from "./components/HangmanImages/Slide10.JPG";

import './App.css';


//create a const of the alphabet and images, as these will never change
const alphabet = "abcdefghijklmnopqrstuvwxyz";
const images = [slide10, slide9, slide8, slide7, slide6, slide5, slide4, slide3, slide2, slide1];


class App extends React.Component {

  /* occurs at the beginning of the component lifecycle, is used for translation of props
  into local components, or binding functions to the component - here it's passing the props to the 
  react.compoennt constructor using super */
  constructor(props) {
    super(props);

    {/* state is for internal storage of dynamic data as these will change over time */}
    this.state = {
      lives: 9,
      answer: '',
      displayAnswer: '', 
      clicked: []
    }
  }

  //conducts state recalculations
  componentDidMount(){
    this.createDisplayAnswer();
    console.log("Component has mounded");
  }
  
  //chooses an answer and creates the associated display answer
  createDisplayAnswer = () => {
    //create inital variables
    var words = require('an-array-of-english-words');
    var ans = '';

    //choose a word of at least length 5
    while (ans.length < 5) {
      var randIndex = Math.floor(Math.random() * words.length);
      ans = words[randIndex];
    }

    //if the word is hello, it creates a display answer of -----
    var displayAns = '-'.repeat(ans.length);

    //sets the state and logs the answer to the console
    this.setState({displayAnswer: displayAns, answer: ans}); 
    console.log("My answer is " + this.state.answer); 
  }

  //onClick that takes a letter as input and updates lives and answer accordingly
  makeGuess(myGuess) {

    //declare variables
    let guess = myGuess.singLetter;
    let { answer, displayAnswer, clicked} = this.state;
    var ans = answer;
    var displayAns = displayAnswer;
    var correctGuess = "false";

    //iterate through word and replace the letter that has been guessed if found in the word
    for (var i = 0; i < ans.length; i++) {
      if (ans.charAt(i) == guess) {    
        displayAns = displayAns.substr(0, i) + guess + displayAns.substr(i + 1, displayAns.length);
        //mark the guess as correct
        correctGuess = 'true';
      } 
    }

    //if the guess is false and the letter hasn't been clicked yet, decrease the lives
    if (correctGuess == "false" && !this.hasLetter(guess)) {
      this.setState({lives: this.state.lives - 1});
    }

    //update state 
    this.setState({displayAnswer: displayAns});
    clicked.push(guess);
  }

  //onClick to reset the state to the inital state, and recreate the display answer
  resetGame = () => {
    this.setState({
      lives: 9, 
      answer: '',
      displayAnswer: '', 
      clicked: []
    })
    {this.createDisplayAnswer()}
  }
  
  //returns true if the letter has already been clicked
  hasLetter(letter) {
    let myClicked = this.state.clicked;
    if (myClicked.indexOf(letter) == -1) {
      return false;
    }
    return true;
  }

  //larger function to gather output and update page if there is an update
  render () {

    //checks if the game is over, and creates an alert if so
    const weContinue = (this.state.answer != this.state.displayAnswer && this.state.lives > 0);
    if (!weContinue) {
      if (this.state.answer.length > 1 && this.state.answer == this.state.displayAnswer) {
        alert("Congratulations! You won! Please press restart to play again.");
    } else if (this.state.lives < 1) {
        alert("You lost! Your word was " + this.state.answer + ". Please press restart to play again.");
    } 
  }

    //return these elements to display the page  
    return (
      <div>
        <div className = "display-img">
          <img src={images[this.state.lives]} alt={this.state.lives} height="300"/>
        </div>
        <div className = "answer">
          {this.state.displayAnswer}
        </div>
        <div className = 'row text-center'> {alphabet.substring(0,7).split("").map((singLetter,i) => (
          <button className = {this.hasLetter(singLetter) ? 'clicked' : 'btn'}
          onClick = {() => this.makeGuess({singLetter})}>
             {singLetter}
            </button>))} 
        </div> 
        <div className = 'row text-center'> {alphabet.substring(7,14).split("").map((singLetter,i) => (
           <button className = {this.hasLetter(singLetter) ? 'clicked' : 'btn'}
          onClick = {() => this.makeGuess({singLetter})}>
             {singLetter}
            </button>))} 
        </div> 
        <div className = 'row text-center'> {alphabet.substring(14,21).split("").map((singLetter,i) => (
           <button className = {this.hasLetter(singLetter) ? 'clicked' : 'btn'} 
          onClick = {() => this.makeGuess({singLetter})}>
             {singLetter}
            </button>))} 
        </div> 
        <div className = 'row text-center'> {alphabet.substring(21,26).split("").map((singLetter,i) => (
          <button className = {this.hasLetter(singLetter) ? 'clicked' : 'btn'}
          onClick = {() => this.makeGuess({singLetter})}>
             {singLetter}
            </button>))} 
        </div> 
        <div className = 'row text-center'>
          <button class = 'btn' onClick = {() => this.resetGame()}>Restart</button>
        </div>
      </div>
        );     
          
        }
  }

export default App;