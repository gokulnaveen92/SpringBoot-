import { Component, OnInit } from '@angular/core';
import { Recipe } from '../model/recipe.model';

@Component({
  selector: 'app-recipe-list',
  templateUrl: './recipe-list.component.html',
  styleUrls: ['./recipe-list.component.css']
})
export class RecipeListComponent implements OnInit {
  recipes:Recipe[]=[
    {
      id : 1,
      name : "Pancakes",
      type : "breakfast",
      ingredients : ["Flour","Milk","Eggs","Butter"],
      instructions : "Mix flour,milk,and eggs.Cook in a pan with butter."
    },
    {
      id : 2,
      name : "Dosa",
      type : "dinner",
      ingredients : ["Flour","Oil"],
      instructions : "Just eat its none of your business"
    }
  ];
  selectedRecipe:Recipe=null;



  constructor() { }

  ngOnInit(): void {
  }

  showDetails(recipe:Recipe){
    this.selectedRecipe=recipe;
  }

  hideDetails(){
    this.selectedRecipe=null;
  }

  deleteRecipe(recipe:Recipe){
    this.recipes.splice(this.recipes.indexOf(recipe),1);
    this.hideDetails();
  }
}
