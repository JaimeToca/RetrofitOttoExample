# Retrofit-Otto-Example
This is just a basic example of how you can use Retrofit and Otto libraries  in your android projects. I decided to work with [themovieDB](https://www.themoviedb.org/), an opensource database for TV Shows, Movies and Actors. The purpose of this project is to show the last 20 most famous actors using retrofit wich works with Rest-Json among others, also Otto is used to notify the main activity that the information is ready and send the actors aswell.

**MovieDB API** <br />
This is how the request looks like for getting the 20 most popular actors : 

    http://api.themoviedb.org/3/person/popular?api_key=XXXXX

check out the JSON response [here](http://docs.themoviedb.apiary.io/#reference/people/personpopular/get)

**When to use Otto?** <br/>
Otto is a great way to communicate between different components in your project. Events are sent through a bus and the classes interested to some specific events have to suscribe to them. I recommend you play with Otto, but keep in mind that sometimes is not the best option, specially when it comes to nesting events, you can really get in trouble and become mad with the debugging. In my opinion, Otto is good, but in the end I would recommend to use simple listeners / Threads or even better RxJava (this repository was created before I started programming with RxJava) , although some of them might require more code, will make things eassier to understand .In this project, there are 2 modules and once the information is downloaded the rest module needs to comunicate with the main activity (the one that will show the information ).<br/>
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
                sendActorsToActivity((ActorsWrapper) o);
            }
        }

        @Override
        public void failure(RetrofitError error) {
            Log.e("Callback failure", ""+ error.getMessage());
        }
    };


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








