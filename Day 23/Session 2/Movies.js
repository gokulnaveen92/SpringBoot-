class Movie{
    constructor(title,director,year,genre){
        this.title = title;
        this.director = director;
        this.year = year;
        this.genre = genre;
    }

    getMovieInfo = function(){
        return "Title: "+this.title+", Director: "+this.director+", Year: "+this.year+", Genre: "+this.genre;
        
    }
}

let movies = [
    new Movie("Mov 1","Dir 1",2004),
    new Movie("mov 2","dir 2",2005),
    new Movie("mov 3","dir 3",2010)
];

movies.forEach(Movie => console.log(Movie.getMovieInfo()));

module.exports=Movie;
