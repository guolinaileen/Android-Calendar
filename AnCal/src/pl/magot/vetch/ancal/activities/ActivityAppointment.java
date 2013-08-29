
package pl.magot.vetch.ancal.activities;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import pl.magot.vetch.widgets.*;
import pl.magot.vetch.ancal.CommonActivity;
import pl.magot.vetch.ancal.R;
import pl.magot.vetch.ancal.RepeatData;

import pl.magot.vetch.ancal.database.DataRowAppointment;
import pl.magot.vetch.ancal.database.DataTable;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.*;
import android.util.Log;
import android.view.*;
import android.view.View.OnClickListener;


//New appointment activity
public class ActivityAppointment extends CommonActivity
{	
	//fields
	private Calendar dateStart = null;
	private DataRowAppointment dataRow = null;
	private DataTable dataTable = null;


	//views
	private TouchEdit edSubject = null;
	private Button btnStartDate = null;
	private Button btnStartTime = null;
	private CheckBox chkAllDay = null;
	private CheckBox chkAlarm = null;
	private TouchEdit chkAddress = null;
	private Button btnRepeatSelect = null;
	//private Button btnContacts = null;
	private Button btnContacts = null;
	private Button btnViewContacts = null;
	
	private ImageButton btnDelete = null;
	private ImageButton btnSave = null;
	
	//repeat data
	private int iRepeatType = -1;
	private int iRepeatEvery = 0;
	private Calendar dateEndOn = null;

	//
	public static String lat=""; 
	public static String lng="";
	//contacts
	public String contacts = "";
	public String newContacts = "";
  //methods
  @Override
  public void onCreate(Bundle icicle)
  {
    super.onCreate(icicle);      
    setContentView(R.layout.appointment);
          
    //initialize objects
    dataRow = new DataRowAppointment(userdb);
  	dataTable = new DataTable(dataRow);


    //check startup mode and open data
  	if (GetStartMode() == StartMode.EDIT)
  		if (!OpenDataForEdit(dataTable))
  			finish();
        	
    //initialize views
    InitViews();
    InitState();
  }

  @Override
  public void onStart()
  {
  	super.onStart();
  	    
  }
  
  @Override
  public void onResume()
  {
  	super.onResume();

  }
      
  @Override
  public void onPause()
  {
  	super.onPause();
  	
  }
  
  private void InitViews()
  {
  	edSubject = (TouchEdit)findViewById(R.id.edAppointmentSubject);
  	edSubject.setOnOpenKeyboard(new TouchEdit.OnOpenKeyboard()
    {
			public void OnOpenKeyboardEvent()
			{
        KeyboardWidget.Open(ActivityAppointment.this, edSubject.getText().toString());						
			}        	        	
    });  	
  	chkAddress = (TouchEdit)findViewById(R.id.editText1);

  	chkAddress.setOnOpenKeyboard(new TouchEdit.OnOpenKeyboard()
    {
			public void OnOpenKeyboardEvent()
			{
        KeyboardWidget.Open(ActivityAppointment.this, chkAddress.getText().toString());						
			}        	        	
    });
  	btnStartDate = (Button)findViewById(R.id.btnAppointmentStartDate);
  	btnStartTime = (Button)findViewById(R.id.btnAppointmentStartTime);  	
  	chkAllDay = (CheckBox)findViewById(R.id.chkAppointmentAllDay);   
  	chkAlarm = (CheckBox)findViewById(R.id.chkAppointmentAlarm); 
  	

  	btnRepeatSelect = (Button)findViewById(R.id.btnRepeatSelect);
  	btnRepeatSelect.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				OpenRepeatDialog();
			}		
  	});
  	final Button jump=(Button) findViewById(R.id.button1);
		final EditText text=(EditText)findViewById(R.id.editText1); 	
		final ProgressBar progressBr =(ProgressBar)findViewById(R.id.progressBar);
		  jump.setOnClickListener(new OnClickListener() {
	      	@Override
					public void onClick(View arg0) {

		      	String txt=text.getText().toString().trim(); 	
		      	if(!txt.equals("")){
		      	txt=txt.replaceAll(" ", "+");
	      		progressBr.setVisibility(View.VISIBLE);
		      	StringBuffer sb=new StringBuffer(); 
		      	sb.append("https://maps.googleapis.com/maps/api/place/textsearch/json?query="); 
		      	sb.append(txt); 
		      	sb.append("&sensor=true&key=AIzaSyAWt8k2PkD46FgBeUrBwPqn3c1sRzwZsWI"); 
		      	
		      	Log.d("check it text messages: ", sb.toString());
//		   		String urlString="https://maps.googleapis.com/maps/api/place/textsearch/json?query=2637+Ellendale+Pl+Los+Angeles&sensor=true&key=AIzaSyA0_250ptHdc8lbWmxO6D5eJIOkJ5TWaCE"; 	 
				 GrabURL grab = new GrabURL();  
		         grab.execute(sb.toString()); 

		         progressBr.setVisibility(View.GONE); 

					}else
					{
						Toast.makeText(ActivityAppointment.this,"Location is empty! " , Toast.LENGTH_LONG).show(); 
					}
	      	}

			});

  	btnContacts = (Button)findViewById(R.id.btnAddContacts);
  	btnContacts.setOnClickListener(new View.OnClickListener() {

				public void onClick(View arg0) {
					 
					Intent newIntent = new Intent(ActivityAppointment.this, AddContacts.class);
					startActivityForResult(newIntent, 1);
	    
				}
				
  	});
  	btnViewContacts = (Button)findViewById(R.id.btnContacts);
  	btnViewContacts.setOnClickListener(new View.OnClickListener() {

				public void onClick(View arg0) {
					Bundle bundle = new Bundle();
					//pass contacts to arrayList
					ArrayList<String> selectedContacts = new ArrayList<String>();
					String []c= contacts.split(",");
					for(int i=0;i<c.length;i++){
						if(!selectedContacts.contains(c[i])){
							selectedContacts.add(c[i]);
						}
						
					}
					ArrayList<String> message = new ArrayList<String>();
					bundle.putStringArrayList("cid", selectedContacts);
					message.add(edSubject.getText().toString());
					message.add(chkAddress.getText().toString());
					message.add(btnStartDate.getText().toString());
					message.add(btnStartTime.getText().toString());
					bundle.putStringArrayList("message", message);
				Intent newIntent = new Intent(ActivityAppointment.this, ListContact.class);
				newIntent.putExtras(bundle);
				startActivity(newIntent);
	    
				}
				
  	});

  	btnSave = (ImageButton)findViewById(R.id.btnAppointmentSave);
  	btnSave.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				SaveData();
			}		
  	});
  	
  	btnDelete = (ImageButton)findViewById(R.id.btnAppointmentDelete);
  	btnDelete.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				new AlertDialog.Builder(ActivityAppointment.this)
		    .setTitle("Delete Activity")
		    .setMessage("Are you sure you want to delete this appointment?")
		    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		            // continue with delete
		        	DeleteData();
		        }
		     })
		    .setNegativeButton("No", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		            // do nothing
		         
		        }
		     })
		     .show();
				
			}	
  	});
  		
  	btnStartDate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				DateWidget.Open(ActivityAppointment.this, false, dateStart, prefs.iFirstDayOfWeek);
				
			}
		});

  	btnStartTime.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
	    	TimeWidget.Open(ActivityAppointment.this, false, prefs.b24HourMode,
	    			dateStart.get(Calendar.HOUR_OF_DAY), dateStart.get(Calendar.MINUTE));
				
			}
		});

  	chkAllDay.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				UpdateStartDateTimeView();
			}
		});
  	
  }    
  @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.layout.appointment, menu);
		return true;
	}
	
	public class GrabURL extends AsyncTask<String, Void, Point>{


		private ProgressBar mProgressBar= (ProgressBar) findViewById(R.id.progressBar);
	
			@Override
			protected Point doInBackground(String... urls) {
				// TODO Auto-generated method stub
			 return	getPosition(urls[0]); 
			}
			
			public Point getPosition(String urlString)
			{
				Point result=new Point(); 
				String jsn="";
		 		
				try {
					URL url=new URL(urlString);
					URLConnection urlConnection;
		    		urlConnection = url.openConnection();
					urlConnection.setAllowUserInteraction(false);
					InputStream urlStream=url.openStream();	
					BufferedReader in=new BufferedReader(new InputStreamReader(urlStream));
					String inputLine=""; 	
					while((inputLine=in.readLine())!=null) 
					{
						jsn=jsn+inputLine; 
					}
					Log.d("message: ", jsn);
					JSONObject obj=new JSONObject(jsn);
					JSONArray array=obj.getJSONArray("results"); 
					JSONObject resultJson=array.getJSONObject(0); 
					JSONObject geo= resultJson.getJSONObject("geometry");
					JSONObject loc=geo.getJSONObject("location"); 
					lat=loc.getString("lat"); 
					lng=loc.getString("lng");
					Log.d("latitude: ", lat); 
					Log.d("longtitude: ", lng);

					
				} catch (MalformedURLException e) {e.printStackTrace();}
				  catch(IOException e){e.printStackTrace();  }
				  catch(JSONException e) {e.printStackTrace();} 
				 result.lat=(double) ( Double.parseDouble(lat)); 
				 result.lng=(double) ( Double.parseDouble(lng)); 
				return result; 
			}
			
			  protected void onProgressUpdate(Integer... progress) 
			  {
		          mProgressBar.setProgress(progress[0]);
		       }  

		       protected void onPostExecute(Point result) {
		      	
				   Intent detailIntent = new Intent(ActivityAppointment.this, Map.class);
		           startActivity(detailIntent);
		       }  
		         
		       protected void onPreExecute () {
		           mProgressBar.setProgress(0);
		       }  
		         
		       protected void onCancelled () {
		           mProgressBar.setProgress(0);
		       }  
			

		}
  private void SetStartDateByAgendaView(Calendar calDate)
  {
  	if (getIntent() != null)
  	{
  		Bundle extras = getIntent().getExtras();  	  	
	  	if (extras != null)
	  	{  	
				if (extras.containsKey(CommonActivity.bundleAgendaView))
				{
					int iView = extras.getInt(CommonActivity.bundleAgendaView);
					if (iView == 1) //day
					{
						long lStartDate = extras.getLong(CommonActivity.bundleAgendaViewStartDate);
						calDate.setTimeInMillis(lStartDate);
					}  		
				} 
	  	}
  	}
  }
    
  private void SetStartTimeForDayAgendaView(Calendar calDate)
  {
  	if (getIntent() != null)
  	{
  		Bundle extras = getIntent().getExtras();  	  	
	  	if (extras != null)
	  	{  	
				if (extras.containsKey(CommonActivity.bundleHourOfDay))
				{
					int iView = extras.getInt(CommonActivity.bundleAgendaView);
					if (iView == 1) //day
					{
						int iHourOfDay = extras.getInt(CommonActivity.bundleHourOfDay);
						int iMinutes = extras.getInt(CommonActivity.bundleMinutes);					
						calDate.set(Calendar.HOUR_OF_DAY, iHourOfDay);
						calDate.set(Calendar.MINUTE, iMinutes);					
					}  		
				}
	  	}
  	}
  }
  
  private void updateStartDateTimeForNewAppointment(Calendar calDate)
  {
  	int iHour = calDate.get(Calendar.HOUR_OF_DAY);
  	int iMinute = calDate.get(Calendar.MINUTE);
  	
  	if (iHour < 23)
  		iHour += 1;
  	iMinute = 0;
  	
  	calDate.set(Calendar.HOUR_OF_DAY, iHour);
  	calDate.set(Calendar.MINUTE, iMinute);
  	calDate.set(Calendar.SECOND, 0);
  	calDate.set(Calendar.MILLISECOND, 0); 
  }
  
  private void InitState()
  {
  	//title
  	String sSubTitle = utils.GetResStr(R.string.titleDefaultAppointment); 

  	//date values
  	dateStart = Calendar.getInstance();
  	dateEndOn = Calendar.getInstance();
  	
  	dataRow.SetDuration(prefs.iMinutesDuration);

  	//INSERT MODE
  	if (GetStartMode() == StartMode.NEW)
  	{
  		sSubTitle = utils.GetResStr(R.string.titleNewAppointment);
      btnDelete.setVisibility(View.INVISIBLE);
  		
			//initialize data
  		SetStartDateByAgendaView(dateStart);
  		updateStartDateTimeForNewAppointment(dateStart);
    	SetStartTimeForDayAgendaView(dateStart);
    	
    	chkAllDay.setChecked(false);
    	chkAlarm.setChecked(true);
      
    	//repeat data
    	iRepeatType = 0; //none
    	iRepeatEvery = 1;
    	dateEndOn.setTimeInMillis(0); //no end date     	
  	}
  	
  	//EDIT MODE
  	if (GetStartMode() == StartMode.EDIT)
  	{
      sSubTitle = utils.GetResStr(R.string.titleEditAppointment);
      
      dateStart.setTimeInMillis(dataRow.GetStartDate().getTimeInMillis());
      
  		btnDelete.setVisibility(View.VISIBLE);
  		edSubject.setText(dataRow.GetSubject());
  		chkAddress.setText(dataRow.GetAddress());
  		//contact
  		contacts = dataRow.GetContacts();
  		
    	chkAllDay.setChecked(dataRow.GetAllDay());
    	chkAlarm.setChecked(dataRow.GetAlarm());
    	//repeat data
    	iRepeatType = dataRow.GetRepeat().GetRepeatTypeAsInt();
    	iRepeatEvery = dataRow.GetRepeat().GetEvery();
    	dateEndOn.setTimeInMillis(dataRow.GetRepeat().GetEndOnDate().getTimeInMillis());
  	}

  	restoreStateFromFreezeIfRequired();
  	
  	SetActivityTitle(sSubTitle);
    UpdateStartDateTimeView();

    UpdateRepeatInfo();

  	//set focus to subject
  	edSubject.requestFocus();
  	if (GetStartMode() == StartMode.EDIT)
  		edSubject.setSelection(edSubject.length());  	
  }

  private void UpdateRepeatInfo()
  {
  	btnRepeatSelect.setText(GetRepeatInfo());  	
  }

  private String GetRepeatInfo()
  {
  	String s = utils.GetResStr(R.string.strRepeatTypeNone);  	
  	String sUntil = utils.GetResStr(R.string.strRepeatInfoUntil);  	
  	String sEndDate = (dateEndOn.getTimeInMillis() == 0)?"":" " + sUntil + " " + utils.GetLongDate(dateEndOn);
  	//daily
  	if (iRepeatType == 1)
  		s = String.format(utils.GetResStr(R.string.strRepeatInfoDaily), iRepeatEvery, sEndDate);  		
  	//weekly
    if (iRepeatType == 2)
  		s = String.format(utils.GetResStr(R.string.strRepeatInfoWeekly), iRepeatEvery, sEndDate);
    //monthly
    if (iRepeatType == 3)
  		s = String.format(utils.GetResStr(R.string.strRepeatInfoMonthly), iRepeatEvery, sEndDate);
    //monthly
    if (iRepeatType == 4)
  		s = String.format(utils.GetResStr(R.string.strRepeatInfoYearly), sEndDate);    
  	return s;
  }
  
  private void UpdateStartDateTimeView()
  {
  	btnStartDate.setText(utils.GetLongDate(dateStart));
		if (chkAllDay.isChecked())
		{
	  	btnStartTime.setText(utils.GetResStr(R.string.labNoTime));
		} else {
	  	btnStartTime.setText(utils.GetLongTime(dateStart, prefs.b24HourMode));
		}	
  }
  
  protected void onSizeChanged(int w, int h, int oldw, int oldh)
  {
  	
  }

  public void SaveData()
  {
  	//check date if no repeat
  	if ((iRepeatType == 0) && (DateBeforeNow(dateStart)))
  		return;
  	  	
  	dataRow.SetSubject(edSubject.getText().toString());
  	dataRow.SetAddress(chkAddress.getText().toString());
  	dataRow.SetStartDate(dateStart);
  	dataRow.SetAllDay(chkAllDay.isChecked());
  	dataRow.SetAlarm(chkAlarm.isChecked());
  	//contact

  	dataRow.SetContacts(contacts);
  	
		//set repeat type
  	RepeatData rd = dataRow.GetRepeat(); 	  	
		rd.SetRepeatTypeAsInt(iRepeatType);  			
		rd.SetEvery(iRepeatEvery);		
		rd.SetEndOnDate(dateEndOn.getTimeInMillis());
		
		if (SaveDataToTable(dataTable))
			CloseActivity(dataTable);		
  }  
	
  public void DeleteData()
  {  	
		if (DeleteDataFromTable(dataTable))
			CloseActivity(dataTable);
  }
  
  @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
  {
  	super.onActivityResult(requestCode, resultCode, data);
  	
  	  	//return from contacts
  	if(resultCode ==1){
  		newContacts = data.getStringExtra("contacts");
  		if(!newContacts.equals("")){
  			Toast.makeText(ActivityAppointment.this, "New Contacts Added", Toast.LENGTH_SHORT).show();
  		
  		}
  		contacts += newContacts;
  		  		
  	}
  	
  	Bundle extras = CommonActivity.getIntentExtras(data);
  	if (extras != null)
  	{
  	
	  	//check for repeat update
  		if (ActivityAppointmentRepeat.GetActivityResult(requestCode, resultCode, extras))
  		{
  			iRepeatType = ActivityAppointmentRepeat.getExtraRepeatType(extras);
  			iRepeatEvery = ActivityAppointmentRepeat.getExtraRepeatEvery(extras);
  			dateEndOn.setTimeInMillis(ActivityAppointmentRepeat.getExtraRepeatEndOnDate(extras));
  			UpdateRepeatInfo();
  			return;
  		}
  			  	
	  	//check for date widget edit request code
	  	if (requestCode == DateWidget.SELECT_DATE_REQUEST)
	  	{
	    	final long lDate = DateWidget.GetSelectedDateOnActivityResult(requestCode, resultCode, extras, dateStart);
	    	if (lDate != -1)
	    	{
	  			UpdateStartDateTimeView();
	  			return;
	    	}
	  	}
	  	
	  	//check for time widget edit request code
	  	if ((requestCode == TimeWidget.SELECT_TIME_REQUEST) && (resultCode == RESULT_OK))
			{
	  		final int iHour = TimeWidget.GetSelectedTimeHourOnActivityResult(requestCode, resultCode, extras);
	  		final int iMinute = TimeWidget.GetSelectedTimeMinuteOnActivityResult(requestCode, resultCode, extras);    		
		  	dateStart.set(Calendar.HOUR_OF_DAY, iHour);
		  	dateStart.set(Calendar.MINUTE, iMinute);
				chkAllDay.setChecked(false);
				UpdateStartDateTimeView();
				return;
			}
	  	
	  	//get KeyboardWidget result
	  	if ((requestCode == KeyboardWidget.EDIT_TEXT_REQUEST) && (resultCode == RESULT_OK)) 
	  	{
				String sText = KeyboardWidget.GetTextOnActivityResult(requestCode, resultCode, extras);    			
				edSubject.setText(sText);
				return;
	  	}
  	
  	}
  }  
      
  public void OpenRepeatDialog()
  {
  	ActivityAppointmentRepeat.OpenRepeatForEdit(this, bundleOtherDataStartup, iRepeatType, iRepeatEvery, dateEndOn.getTimeInMillis());
  }
  
  public void OpenContacts()
  {
  	//ActivityAppointmentContacts.OpenRepeatForEdit(this, bundleOtherDataStartup, iRepeatType, iRepeatEvery, dateEndOn.getTimeInMillis());
  }
  
    
  @Override
  public void SaveDateValuesBeforeChange(Bundle data)
  {
  	super.SaveDateValuesBeforeChange(data);
  	if (GetStartMode() == StartMode.EDIT)
  	{
  		data.putLong("dateStart", dataRow.GetStartDate().getTimeInMillis());
  		data.putLong("dateEndOn", dataRow.GetRepeat().GetEndOnDate().getTimeInMillis());
  	}
  }
  
  @Override
  public boolean DateValuesChanged(Bundle data)
  {
  	super.DateValuesChanged(data);
  	if (GetStartMode() == StartMode.EDIT)
  	{
  		if (dateStart.getTimeInMillis() != data.getLong("dateStart"))
  			return true;
  	}
  	return false;
  }

  @Override
  protected void onSaveInstanceState(Bundle outState)
  {
  	super.onSaveInstanceState(outState);
  	//save controls state
  	outState.putString("subject", edSubject.getText().toString());
  	outState.putString("address", chkAddress.getText().toString());
  	outState.putBoolean("alarm", chkAlarm.isChecked());  	
  	outState.putLong("dateStart", dateStart.getTimeInMillis());  	
  	outState.putBoolean("allday", chkAllDay.isChecked());  	
  	outState.putInt("repeatType", iRepeatType);
  	outState.putInt("repeatEvery", iRepeatEvery);  	  	
  	outState.putLong("dateEndOn", dateEndOn.getTimeInMillis()); 
  	outState.putString("contacts", contacts); 
  	outState.putLong("dateEndOn", dateEndOn.getTimeInMillis()); 
  }  
  
	@Override
	protected void restoreStateFromFreeze()
	{
 		edSubject.setText(freeze.getString("subject"));  
 		chkAddress.setText(freeze.getString("address"));  
 		
 		chkAlarm.setChecked(freeze.getBoolean("alarm"));
 		dateStart.setTimeInMillis(freeze.getLong("dateStart"));
 		chkAllDay.setChecked(freeze.getBoolean("allday"));
 		iRepeatType = freeze.getInt("repeatType");
 		iRepeatEvery = freeze.getInt("repeatEvery");
 		dateEndOn.setTimeInMillis(freeze.getLong("dateEndOn"));
	}
  
}
