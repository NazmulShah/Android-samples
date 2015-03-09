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
public class AddUserActivity extends Activity {
	private UserDataSource datasource;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_contact_layout);
		datasource = new UserDataSource(this);
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

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		System.out.println("back button pressed");
		finish();
	}

	public void savContact(View view) {

		System.out.println("saveContact called");
		User user = null;
		String name = ((EditText) findViewById(R.id.name_create)).getText()
				.toString();
		if (name == null || name == "" || name.length() == 0) {
			System.out.println("name not found");
			return;
		}
		String phoneNo = ((EditText) findViewById(R.id.phone_create)).getText()
				.toString();
		if (phoneNo == null || phoneNo == "" || phoneNo.length() == 0) {
			System.out.println("Phone not found");
			return;
		}
		String address = ((EditText) findViewById(R.id.address_create))
				.getText().toString();
		if (address == null || address == "" || address.length() == 0) {
			System.out.println("address not found");
			return;
		}
		user = new User();
		user.setName(name);
		user.setPhoneNo(phoneNo);
		user.setAddress(address);
		// Save the new comment to the database
		datasource.createUser(user);
		datasource.close();
		finish();
	}

	public void cancelAdd(View view) {
		System.out.println("cancel button pressed");
		finish();
	}

}
