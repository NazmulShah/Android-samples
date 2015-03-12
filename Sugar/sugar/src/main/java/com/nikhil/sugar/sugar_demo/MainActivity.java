package com.nikhil.sugar.sugar_demo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.nikhil.sugar.R;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialization();
    }

    private void initialization() {
        Button done = (Button) findViewById(R.id.done_button);
        done.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.done_button: {
                    saveRecord();
                    break;
                }
            }
        }
    };

    private void saveRecord() {
        EditText name = (EditText) findViewById(R.id.name_edit_text);
        EditText ageText = (EditText) findViewById(R.id.age_edit_text);
        EditText email = (EditText) findViewById(R.id.email_edit_text);
        EditText password = (EditText) findViewById(R.id.password_edit_text);

        Integer age = Integer.parseInt(ageText.getText().toString());
        User user = new User(name.getText().toString(), age, email.getText().toString(), password.getText().toString());
        user.save();
        User dbUser = User.findById(User.class, user.getId());
        Toast.makeText(this, user.getId()+"HI :" + dbUser.getName(), Toast.LENGTH_SHORT).show();
    }

}
