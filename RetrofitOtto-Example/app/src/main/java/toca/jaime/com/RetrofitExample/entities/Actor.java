package toca.jaime.com.RetrofitExample.entities;

import java.util.List;

/*
 * Copyright (C) 2015 Jaime Toca.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@SuppressWarnings("UnusedDeclaration")
public class Actor {

    private String adult;
    private Number id;
    private List<Known_for> known_for;
    private String name;
    private Number popularity;
    private String profile_path;
    private boolean isLoaded;

    public Actor(Number id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getAdult(){
        return adult;
    }

    public Number getId(){
        return id;
    }

    public List<Known_for> getKnownFor(){
        return known_for;
    }

    public String getName(){
        return name;
    }

    public Number getPopularity(){
        return popularity;
    }

    public String getProfilePath(){
        return profile_path;
    }

    public void setIsLoaded(boolean isLoadedActor){
        this.isLoaded=isLoadedActor;
    }

    public Boolean getIsLoaded(){
        return isLoaded;
    }

}
