/**
 * 
 */
package com.neevtech.crud_demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.neevtech.crud_demo.db.UserDataSource;
import com.neevtech.crud_demo.model.User;

/**
 * @author prashant
 * 
 */
public class UpdateUserActivity extends Activity {

	private UserDataSource datasource;
	private User user;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update_user_layout);
		datasource = new UserDataSource(this);

		user = (User) getIntent().getExtras().getSerializable("userObject");
		System.out.println(user.getId() + user.getName() + user.getPhoneNo()
				+ user.getAddress());
		((EditText) findViewById(R.id.name_update)).setText(user.getName());
		((EditText) findViewById(R.id.phone_no_update)).setText(user
				.getPhoneNo());
		((EditText) findViewById(R.id.address_update)).setText(user
				.getAddress());
	}

	@Override
	protected void onResume() {
		datasource.open();

		super.onResume();
	}

	@Override
	protected void onPause() {
		datasource.close();
		super.onPause();
	}

	public void saveUser(View view) {

		String name = ((EditText) findViewById(R.id.name_update)).getText()
				.toString();
		if (name == null || name == "") {
			System.out.println("Name not found");
			return;
		}
		String phoneNo = ((EditText) findViewById(R.id.phone_no_update))
				.getText().toString();
		if (phoneNo == null || phoneNo == "") {
			System.out.println("Phone not found");
			return;
		}
		String address = ((EditText) findViewById(R.id.address_update))
				.getText().toString();
		if (address == null || address == "") {
			System.out.println("address not found");
			return;
		}
		user.setName(name);
		user.setPhoneNo(phoneNo);
		user.setAddress(address);
		// Save the new comment to the database
		datasource.updateUser(user);
		finish();
	}

	public void cancelUpdate(View view) {
		finish();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}

}
