package pl.magot.vetch.ancal.activities;
//get contacts from database, and list(call and text)

import java.util.ArrayList;
import java.util.List;

import pl.magot.vetch.ancal.R;

import android.R.string;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
 
public class ListContact extends ListActivity {
	
	//ArrayList<ContactsContract> contacts = new ArrayList<ContactsContract>();
	List<ContactModel> contacts = new ArrayList<ContactModel>();
	ArrayList<String> contactNames = new ArrayList<String>();
	ArrayList<String> contactIDs = new ArrayList<String>();
	public static String message = "";
	ArrayList<String> contactPhones = new ArrayList<String>();
	ArrayList<String> contactEmails = new ArrayList<String>();
	private static final String[] PHONES_PROJECTION = new String[] {  
        Phone.DISPLAY_NAME, Phone.NUMBER, Phone._ID };
	private static final int NAME_INDEX = 0; 
	private static final int NUMBER_INDEX = 1; 
	private static final int CONTACT_INDEX = 2; 
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview);
		
		//get contactNames from database!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		Bundle bundle = this.getIntent().getExtras();////////////////////////////
		contactIDs = bundle.getStringArrayList("cid");////////////////////////////
		message = "Activity: "+bundle.getStringArrayList("message").get(0)+"\n"+
		"Location: "+bundle.getStringArrayList("message").get(1)+"\n"+
		"Time: "+bundle.getStringArrayList("message").get(2)+" at "+bundle.getStringArrayList("message").get(3);
		
		
		// 获得所有的联系人  
        Cursor cur = getContentResolver().query(Phone.CONTENT_URI,PHONES_PROJECTION, null, null, "sort_key_alt asc");

        // 循环遍历  
        if (cur.moveToFirst()) {  
        	int i = 0;
            do {   
                if(cur.getString(CONTACT_INDEX).equals(contactIDs.get(i).toString()) ){// 获得联系人姓名 
                	ContactModel contact = new ContactModel(cur.getString(NAME_INDEX), cur.getString(CONTACT_INDEX), cur.getString(NUMBER_INDEX));
                	contacts.add(contact);
                	contactNames.add(cur.getString(NAME_INDEX));  
                	//contactIDs.add(cur.getString(CONTACT_INDEX));
                	contactPhones.add(cur.getString(NUMBER_INDEX));
                	//contactEmails.add();
                	i++;
                }
            } while (cur.moveToNext() && i < contactIDs.size());  
  
        } 
 
//        setListAdapter(new ArrayAdapter<String>(this, R.layout.listviewitem, contactNames));
		ListView listView = getListView();//(ListView) findViewById(R.id.listview1);
		listView.setAdapter(new MyContactAdapter(this, contacts, this.getApplicationContext()));
		listView.setTextFilterEnabled(true);
 
//		listView.setOnItemClickListener(new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				Intent dialIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contactPhones.get(position)));  
//				startActivity(dialIntent);
//				
//				Intent textIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + contactPhones.get(position)));
//				textIntent.putExtra("sms_body", "Just Testing!");
//				startActivity(textIntent);
//				
//			}
//
//		});
		
	}
 
}