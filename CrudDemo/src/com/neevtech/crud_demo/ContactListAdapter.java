/**
 * 
 */
package com.neevtech.crud_demo;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.neevtech.crud_demo.model.User;

/**
 * @author prashant
 *
 */
public class ContactListAdapter extends ArrayAdapter<User> {
	

	private final Context context;
	private final List<User> users;
	
	public ContactListAdapter(Context context, List<User> values) {
		super(context, R.layout.contact_list_layout, values);
		this.context = context;
		this.users = values;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View rowView = inflater.inflate(R.layout.contact_list_layout, parent, false);
		TextView textName = (TextView) rowView.findViewById(R.id.name);
		TextView textPhone = (TextView) rowView.findViewById(R.id.phone_no);
		
		textName.setText(users.get(position).getName());
		textPhone.setText(users.get(position).getPhoneNo());
		return rowView;
	}
}