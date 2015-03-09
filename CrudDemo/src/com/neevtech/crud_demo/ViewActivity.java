package com.neevtech.crud_demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.neevtech.crud_demo.db.UserDataSource;
import com.neevtech.crud_demo.model.User;

public class ViewActivity extends Activity {

	private UserDataSource datasource;
	private User user;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view);
		datasource = new UserDataSource(this);

		user = (User) getIntent().getExtras().getSerializable("userObject");
	}
	
	@Override
	protected void onResume() {
		datasource.open();
		user = datasource.findUserById(user.getId());
		((TextView) findViewById(R.id.name_view)).setText(user.getName());
		((TextView) findViewById(R.id.phone_view)).setText(user.getPhoneNo());
		((TextView) findViewById(R.id.address_view)).setText(user.getAddress());
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		datasource.close();
		super.onPause();
	}

	public void close(View view) {
		finish();
	}

	public void updateContact(View view) {

		Intent intent = new Intent(this, UpdateUserActivity.class);
		intent.putExtra("userObject", user);
		startActivity(intent);
	}
	
	public void deleteContact(View view) {
			datasource.deleteUser(user.getId());
			finish();
	}
}
