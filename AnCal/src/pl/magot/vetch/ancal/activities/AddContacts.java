package pl.magot.vetch.ancal.activities;
//select contacts from android contacts list.

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.magot.vetch.ancal.R;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class AddContacts extends ListActivity{
	//ArrayList<ContactsContract> contacts = new ArrayList<ContactsContract>();
	ArrayList<String> selectedContacts = new ArrayList<String>();
	ArrayList<String> contactNames = new ArrayList<String>();
	ArrayList<String> contactIDs = new ArrayList<String>();
	ArrayList<String> contactPhones = new ArrayList<String>();
	ArrayList<String> contactEmails = new ArrayList<String>();
	List<Model> contacts = new ArrayList<Model>();
	private static final String[] PHONES_PROJECTION = new String[] {  
        Phone.DISPLAY_NAME, Phone.NUMBER, Phone._ID };
	private static final int NAME_INDEX = 0; 
	private static final int NUMBER_INDEX = 1; 
	private static final int CONTACT_INDEX = 2; 
	Context context;
	private String contactStr="";
	 
	public void onCreate(Bundle savedInstanceState) {  
		super.onCreate(savedInstanceState);  
		setContentView(R.layout.addcontact); 
		context = this.getApplicationContext();
		final ListView listView = getListView();  
		//listView.setItemsCanFocus(false);  
		//listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);  
		ImageButton okbtn=(ImageButton)findViewById(R.id.button2);  
		ImageButton cancelbtn=(ImageButton)findViewById(R.id.button3);
		
		okbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//save selectedContacts to database!!!!!!!!!!!!!!!!!!!!!
				
				selectedContacts = new ArrayList<String>();
				for (Model m:contacts)
					if (m.isSelected())
						selectedContacts.add(m.ID);
				if(selectedContacts.size() == 0) 
					Toast.makeText(context, "Please select contacts!", 2).show();
				else{
					//save to database
					contactStr = "";
					for(int i = 0; i< selectedContacts.size();i++){
						contactStr+= selectedContacts.get(i)+",";
					}
					Intent resultIntent = new Intent();
					resultIntent.putExtra("contacts", contactStr);
					setResult(1, resultIntent);
					finish();
					
					

				}
			} 
		});  
		
		cancelbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				selectedContacts.clear();
				for (Model m : contacts){
					m.setSelected(false);
				}
				listView.invalidateViews();
				Intent resultIntent = new Intent();
				resultIntent.putExtra("contacts", "");
				setResult(1, resultIntent);
				finish();
			} 
		});    
		
		
        Cursor cur = getContentResolver().query(Phone.CONTENT_URI,PHONES_PROJECTION, null, null, "sort_key_alt asc");

        
        if (cur.moveToFirst()) {  
            do {   
                
                contactNames.add(cur.getString(NAME_INDEX));  
                contactIDs.add(cur.getString(CONTACT_INDEX));
                contactPhones.add(cur.getString(NUMBER_INDEX));
                //contactEmails.add();  
            } while (cur.moveToNext());  
  
        } 
        
          
        for(int i = 0;i < contactNames.size(); i++){ 
            contacts.add(new Model(contactNames.get(i).toString() + "\t\t\t" + contactPhones.get(i).toString(), contactIDs.get(i)));  
        }  
       
        listView.setAdapter(new MyAdapter(this, contacts, this.getApplicationContext(), this.selectedContacts, contactIDs));  

 
	}
	
	
}
