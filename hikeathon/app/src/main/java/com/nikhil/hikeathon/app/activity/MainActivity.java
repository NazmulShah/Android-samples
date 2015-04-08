package com.nikhil.hikeathon.app.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import com.nikhil.hikeathon.app.R;
import com.nikhil.hikeathon.app.activity.com.nikhil.hikeathon.app.xmpp.XmppManager;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        Button registerButton = (Button) findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    private void register() {
        try {
            String username = "testuser1";
            String password = "testuser1pass";
            String server = "hackathon.hike.in";
            XmppManager xmppManager = new XmppManager(server, 8282);

            xmppManager.init();
            xmppManager.performLogin(username, password);
            xmppManager.setStatus(true, "Hello everyone");

            String buddyJID = "sharat";
            String buddyName = "sharat";
            xmppManager.createEntry(buddyJID, buddyName);

            xmppManager.sendMessage("Hello mate", buddyJID + "@" + server);

            xmppManager.printRoster();

            boolean isRunning = true;

            while (isRunning) {
                Thread.sleep(50);
            }

            xmppManager.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
