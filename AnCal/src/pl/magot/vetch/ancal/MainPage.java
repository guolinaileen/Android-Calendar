
package pl.magot.vetch.ancal;


import java.util.*;
import java.text.*;
import pl.magot.vetch.ancal.agenda.*;
import pl.magot.vetch.ancal.database.*;
import pl.magot.vetch.ancal.dataview.*;
import pl.magot.vetch.ancal.views.*;
import pl.magot.vetch.ancal.reminder.AlarmService;
import pl.magot.vetch.widgets.*;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.*;
import android.widget.LinearLayout.*;
import android.content.Intent;
import android.os.*;
import android.provider.Settings;
import android.content.*;
import android.content.res.Configuration;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.AnimationSet;

//Main activity
public class MainPage extends Activity
{
	private ImageButton button1=null;
	
  //methods
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
  	super.onCreate(savedInstanceState);
  	
	  setContentView(R.layout.mainpage);
	 
	  //Button button1=(Button)findViewById(R.id.button1);
	  button1=(ImageButton)findViewById(R.id.button1);

	  button1.setOnClickListener(new OnClickListener(){
	  	public void onClick(View v){
	  		
	  	//animation
//	  	  AnimationSet animationSet=new AnimationSet(true);
//	  	  ScaleAnimation scaleAnimation=new ScaleAnimation(1,0.1f,1,0.1f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
//	  	  animationSet.addAnimation(scaleAnimation);
//	  	  animationSet.setStartOffset(1000);
//	  	  animationSet.setFillAfter(true);
//	  	  animationSet.setFillBefore(false);
//	  	  animationSet.setDuration(2000);
//	  	  button1.startAnimation(animationSet);
	  		
	  		Intent intent=new Intent();
	  		intent.setClass(MainPage.this, AnCal.class);
	  		startActivity(intent);
	  	}
	  });
	  
	  
  }	
}

