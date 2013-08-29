package pl.magot.vetch.ancal.activities;
//list contact in the database, adapter

import java.util.ArrayList;
import java.util.List;

import pl.magot.vetch.ancal.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

class ContactModel {

	private String name;
	private String id;
	private String phone;
	

	public ContactModel(String name, String id, String phone) {
		this.name = name;
		this.id = id;
		this.phone = phone;
	}

	public String getName() {
		return name;
	}
	
	public String getId(){
		return id;
	}
	
	public String getPhone(){
		return phone;
	}

//	public boolean isSelected() {
//		return selected;
//	}
//
//	public void setSelected(boolean selected) {
//		this.selected = selected;
//	}
}

public class MyContactAdapter extends ArrayAdapter<ContactModel> {
	

	private final List<ContactModel> list;
	private final Activity context;
	boolean checkAll_flag = false;
	boolean checkItem_flag = false;
//	ArrayList<String> contactNames;
//	ArrayList<String> contactPhones;
//	ArrayList<String> contactIDs;
	Context cont;
	public MyContactAdapter(Activity context, List<ContactModel> list, Context c) {
		super(context, R.layout.contactitem, list);
		this.context = context;
		this.list = list;
//		contactNames= names;
		cont = c;
//		contactPhones = phones;
	}

	static class ViewHolder {
		protected TextView textView;
		protected Button dialButton;
		protected Button smsButton;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		ViewHolder viewHolder = null;
		if (convertView == null) {
			LayoutInflater inflator = context.getLayoutInflater();
			convertView = inflator.inflate(R.layout.listviewitem, null);
			viewHolder = new ViewHolder();
			viewHolder.textView = (TextView) convertView.findViewById(R.id.item);
			viewHolder.dialButton = (Button) convertView.findViewById(R.id.button4);
			viewHolder.dialButton.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Intent dialIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + list.get(position).getPhone()));  
							dialIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							cont.startActivity(dialIntent);
													}
					});
			viewHolder.smsButton = (Button) convertView.findViewById(R.id.button5);
			viewHolder.smsButton.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Intent textIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + list.get(position).getPhone()));
							textIntent.putExtra("sms_body", ListContact.message);
							textIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							cont.startActivity(textIntent);
													}
					});
			convertView.setTag(viewHolder);
			convertView.setTag(R.id.item, viewHolder.textView);
			convertView.setTag(R.id.button4, viewHolder.dialButton);
			convertView.setTag(R.id.button5, viewHolder.smsButton);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.textView.setTag(position); // This line is important.
		viewHolder.textView.setText(list.get(position).getName());
		
		return convertView;
	}
}