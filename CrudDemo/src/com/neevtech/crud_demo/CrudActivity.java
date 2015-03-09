package com.neevtech.crud_demo;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.neevtech.crud_demo.db.UserDataSource;
import com.neevtech.crud_demo.model.User;

public class CrudActivity extends Activity {

	private UserDataSource datasource;
	private ListView listView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base);
		datasource = new UserDataSource(this);
	}

	@Override
	protected void onResume() {
		datasource.open();

		final List<User> values = datasource.getAllUsers();

		// Use the SimpleCursorAdapter to show the
		// elements in a ListView
		listView = (ListView) findViewById(R.id.list_view_contactlist);
		listView.setAdapter(new ContactListAdapter(this, values));
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent intent = new Intent(CrudActivity.this,
						ViewActivity.class);

				intent.putExtra("userObject", values.get(position));
				startActivity(intent);

			}
		});
		super.onResume();
	}

	@Override
	protected void onPause() {
		datasource.close();
		super.onPause();
	}

	public void addContact(View view) {
		Intent intent = new Intent(this, AddUserActivity.class);
		startActivity(intent);
	}
}
