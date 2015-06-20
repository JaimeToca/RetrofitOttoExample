# Retrofit-Otto-Example
This is just an example of how you can use Retrofit and Otto libraries from square in your android projects. I decided to work with [themovieDB](https://www.themoviedb.org/), an opensource database for TV Shows, Movies and Actors. The purpose of this project is to show the last 20 most famous actors using retrofit wich works with Rest-Json among others, also Otto is used to notify the main activity that the information is ready and send the actors aswell.

**MovieDB API** <br />
This is how the request looks like for getting the 20 most popular actors : 

    http://api.themoviedb.org/3/person/popular?api_key=XXXXX

 You can check out the JSON response [here](http://docs.themoviedb.apiary.io/#reference/people/personpopular/get)

This JSON response dictates how entities should be define. In this case 3 entities will be more than enough ;  Actors, Actors_Wrapper and Known_For.

**Project structure** <br />
*APP*<br />
*MainActivity* <br />
 |-> Entities (*Actor, ActorsWrapper, Known_For*) <br />
 |-> Rest (*ActorDatabaseApi, MediaSource, RestActorSource*) <br />
 
 
**References:** <br />
http://square.github.io/otto/ <br />
http://square.github.io/retrofit/ <br />
http://docs.themoviedb.apiary.io/ <br />
https://www.themoviedb.org/documentation/api <br />
https://github.com/saulmm/Material-Movies <br />
