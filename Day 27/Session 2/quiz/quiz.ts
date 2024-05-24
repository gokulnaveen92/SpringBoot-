import { Component, OnInit } from '@angular/core';
import { quizQuestions } from 'src/quiz';

@Component({
  selector: 'app-quiz',
  templateUrl: './quiz.component.html',
  styleUrls: ['./quiz.component.css']
})
export class QuizComponent implements OnInit {
  

  quizQuestions:any[] = quizQuestions;
  currentQuestionIndex:number =0;
  showFeedBack:boolean = false;
  feedback:string="";
  score:number=0;

  
  constructor(){}
  
  ngOnInit(): void {
  }

  checkAnswer(optionIndex:number){
    if(this.quizQuestions[this.currentQuestionIndex].correctAnswer === this.quizQuestions[this.currentQuestionIndex].options[optionIndex]){
      this.score++;
      this.feedback="Correct";
      this.showFeedBack = true;
      setInterval(()=>{this.nextQuestion();},5000);
    }else{
      this.feedback="Incorrect";
      this.showFeedBack = true;
      setInterval(()=>{this.nextQuestion();},5000);
      return false;
    }
  }

  nextQuestion(){
    this.endQuiz();
    if(this.showFeedBack){
      this.currentQuestionIndex++;
      this.showFeedBack=false;
      this.feedback="";
    }
  }

  endQuiz():boolean{
    if(this.currentQuestionIndex>=quizQuestions.length){
      this.showFeedBack=false;
      return true;
    }
    else{
      return false;
    }
  }

  restartQuiz(){
    this.score=0;
    this.feedback="";
    this.currentQuestionIndex=0;
  }

}


