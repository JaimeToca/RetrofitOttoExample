# Retrofit-Otto-Example
This is just a basic example of how you can use Retrofit and Otto libraries  in your android projects. I decided to work with [themovieDB](https://www.themoviedb.org/), an opensource database for TV Shows, Movies and Actors. The purpose of this project is to show the last 20 most famous actors using retrofit wich works with Rest-Json among others, also Otto is used to notify the main activity that the information is ready and send the actors aswell.

**MovieDB API** <br />
This is how the request looks like for getting the 20 most popular actors : 

    http://api.themoviedb.org/3/person/popular?api_key=XXXXX

check out the JSON response [here](http://docs.themoviedb.apiary.io/#reference/people/personpopular/get)



    "page": 1,
    "results": [
     {
      "adult": false,
      "id": 85,
      "known_for": [
        {
          "adult": false,
          "backdrop_path": "/jpRpigNQUjNlfx0gYRBJ30tQIOl.jpg",
          "id": 22,
          "original_title": "Pirates of the Caribbean: The Curse of the Black Pearl",
          "release_date": "2003-07-09",
          "poster_path": "/mL3mGWeiANcAeyJQDwEdwpxQKYv.jpg",
          "popularity": 3.97737662538628,
          "title": "Pirates of the Caribbean: The Curse of the Black Pearl",
          "vote_average": 6.9,
          "vote_count": 2191,
          "media_type": "movie"
        }
      ],
      "name": "Johnny Depp",
      "popularity": 51.9316242947408,
      "profile_path": "/5sc2pu4YWItxCaXSvrFpg64zN9J.jpg"
       .
       .
       .   
 
This JSON response dictates how entities should be define. In this case, 3 entities is more than enough; Actor, Known_For and Actor_Wrapper. <br/>
**Actor**

    private String adult;
    private Number id;
    private List<Known_for> known_for;
    private String name;
    private Number popularity;
    private String profile_path;

**Known_For**

    private String adult;
    private String backdrop_path;
    private Number id;
    private String original_title;
    private String release_date;
    private String poster_path;
    private Number popularity;
    private String title;
    private Number vote_average;
    private Number vote_count;
    private String media_type;

**ActorsWrapper**

    private Number page;
    private List<Actor> results;
    private Number total_pages;
    private Number total_results;


**When to use Otto?** <br/>
Otto is a great way to communicate between your activities/fragments/services. In this project, there are 2 modules (Rest and Entities) and once the information is downloaded the rest module needs to comunicate with the main activity (the one that will show the information ).<br/>
First of all, a singleton instance of the Bus class will be created in order to provide access to it for the android components :

MainActivity.class
	   	
       private Bus bus;
	   @Override
	   protected void onCreate(Bundle savedInstanceState) {
	       bus = new Bus (ThreadEnforcer.ANY);
	       bus.register(this);
	       .....
	   }

MainActivity Class will wait for events, the registration is done via register and  @Subcribe annotation on a public single parameter method.

MainActivity.Class

    @Subscribe
    public void ActorsReceived(ActorsWrapper actorsWrapper) {
        downloadButton.setText(R.string.done);
        notificationText.setText(R.string.downloaded);
        showActors(actorsWrapper);
    }

Then, if the information is ready the main activity is notified through the bus using post

RestActorSource.class

    @Override
    public void sendActorsToActivity (ActorsWrapper actorsResponse) {
        bus.post(actorsResponse);
    }

    //RetrofitCallback
    public Callback retrofitCallback = new Callback() {
        @Override
        public void success(Object o, Response response) {

            if (o instanceof ActorsWrapper){
                actorsCallbackResponse = (ActorsWrapper) o;
                sendActorsToActivity(actorsCallbackResponse);
            }
        }

        @Override
        public void failure(RetrofitError error) {
            Log.e("Callback failure", ""+ error.getMessage());
        }
    };

**Retrofit** <br/>
 Following the instructions read on retrofit website, the interface that cotains the url http call is defined:

    public interface ActorDatabaseAPI {

    @GET("/person/popular")
    void getPopularActors(
            @Query("api_key") String apiKey,
            Callback<ActorsWrapper> callback);
	}

and then finally we create the rest adapter that will let us make the calls.

    RestAdapter movieAPIRest = new RestAdapter.Builder()
       .setEndpoint(HOST)
       .setLogLevel(RestAdapter.LogLevel.HEADERS_AND_ARGS)
       .build();
      
  
  I recommend you to check out the REST module in which everything is pretty clear.

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
http://www.vogella.com/tutorials/JavaLibrary-EventBusOtto/article.html

# Licence

Copyright 2015 Jaime Toca <br/>
Licensed under the Apache License, Version 2.0 (the "License"); <br/>
you may not use this file except in compliance with the License. <br/>
You may obtain a copy of the License at <br/>

   http://www.apache.org/licenses/LICENSE-2.0 <br/>

Unless required by applicable law or agreed to in writing, software <br/>
distributed under the License is distributed on an "AS IS" BASIS,<br/>
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.<br/>
See the License for the specific language governing permissions and<br/>
limitations under the License.<br/>








