# Popular Movies Stage 2

This is an Android application that helps users discover popular and recent movies.

### User Experience

In this stage I built the next features:

* A grid arrangement of movie posters.
* A menu to change sort order (Favorites, Most popular or Top rated).
* A detail screen to show additional information (Title, Movie poster, Plot synopsis, User rating, Release date, Trailers & Reviews).
* A option to save your favorite movies.

### Implementation Guidance

#### Image Library - Picasso

This project uses Picasso in order to load movie posters. To use place holders I added icons from Material Design Icons.

#### Data Persistence - Room

This project uses Room for saving user's favorite movies.

#### Architecture - Android Architecture Components

This project uses LiveData in order to observe changes in database and update UI.

#### Working with the themoviedb.org API

This project implements The Movie DB API to provides a movie information list.

If somebody wants to use this project it's necessary to create an API KEY TheMovieDB site and add it in *gradle.properties*.

Example:

##### Reviews

* In the movies detail screen, a user can tap a button (for example, a star) to mark it as a Favorite. Tap the button on a favorite movie will unfavorite it.


MOVIE_DB_API_KEY = "XXXX"