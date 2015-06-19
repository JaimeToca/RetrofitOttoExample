package toca.jaime.com.RetrofitExample;
import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.squareup.otto.ThreadEnforcer;

import toca.jaime.com.RetrofitExample.entities.ActorsWrapper;
import toca.jaime.com.RetrofitExample.rest.RestActorSource;

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

public class MainActivity extends Activity implements View.OnClickListener {

    private Button downloadButton;
    private TextView notificationText,notificationInfo;
    private RestActorSource downloadInformation;
    private Bus bus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        downloadButton = (Button) findViewById(R.id.button);
        notificationText = (TextView) findViewById(R.id.textView);
        notificationInfo = (TextView) findViewById(R.id.textViewInfo);
        downloadButton.setOnClickListener(this);
        bus = new Bus (ThreadEnforcer.ANY);
        bus.register(this);
        downloadInformation = new RestActorSource(bus);

    }

    @Override
    public void onStop() {
        super.onStop();
        bus.unregister(this);
    }

    @Subscribe
    public void ActorsReceived(ActorsWrapper actorsWrapper) {
        downloadButton.setText(R.string.done);
        notificationText.setText(R.string.downloaded);
        showActors(actorsWrapper);
    }

    public void showActors(ActorsWrapper actorsWrapper){

        StringBuilder actorsNames = new StringBuilder();

        for (int i=0; i < actorsWrapper.getResults().size(); i++){
            actorsNames.append(actorsWrapper.getResults().get(i).getName()).append(',');
        }
        actorsNames.setCharAt(actorsNames.length()-1, '.');
        notificationInfo.setText(actorsNames);
    }

    @Override
    public void onClick(View v){
        if (v.getId() == R.id.button) {
            downloadInformation.getPopularActors();
        }
    }



}
