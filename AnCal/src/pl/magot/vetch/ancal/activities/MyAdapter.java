package pl.magot.vetch.ancal.activities;
//adapter of add contacts

import java.util.ArrayList;
import java.util.List;

import pl.magot.vetch.ancal.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

class Model {

	private String name;
	private boolean selected;
	public String ID;
	public Model(String name, String id) {
		this.name = name;
		ID = id;
	}

	public String getName() {
		return name;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}

public class MyAdapter extends ArrayAdapter<Model> {
	

	private final List<Model> list;
	private final Activity context;
	boolean checkAll_flag = false;
	boolean checkItem_flag = false;
	ArrayList<String> selectedContacts;
	ArrayList<String> contactIDs;
	Context cont;
	public MyAdapter(Activity context, List<Model> list, Context c, ArrayList<String> sc, ArrayList<String> cid) {
		super(context, R.layout.contactitem, list);
		this.context = context;
		this.list = list;
		selectedContacts = sc;
		cont = c;
		contactIDs = cid;
	}

	static class ViewHolder {
		protected CheckBox checkbox;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder = null;
		if (convertView == null) {
			LayoutInflater inflator = context.getLayoutInflater();
			convertView = inflator.inflate(R.layout.contactitem, null);
			viewHolder = new ViewHolder();
			viewHolder.checkbox = (CheckBox) convertView.findViewById(R.id.checked);
			viewHolder.checkbox.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					
				}});
			viewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
							int getPosition = (Integer) buttonView.getTag(); 
							list.get(getPosition).setSelected(buttonView.isChecked()); 
//							if(list.get(getPosition).isSelected()){   
//								selectedContacts.add(contactIDs.get(getPosition)); 
//								Toast.makeText(cont, "Add "+contactIDs.get(getPosition)+" into list", 2).show();
//							}  
//							else{    
//								selectedContacts.remove(contactIDs.get(getPosition));
//								Toast.makeText(cont, "Remove "+contactIDs.get(getPosition)+" from list", 2).show();
//							}  
						}
					});
			convertView.setTag(viewHolder);
			convertView.setTag(R.id.checked, viewHolder.checkbox);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.checkbox.setTag(position); // This line is important.

		viewHolder.checkbox.setText(list.get(position).getName());
		viewHolder.checkbox.setChecked(list.get(position).isSelected());

		return convertView;
	}
}