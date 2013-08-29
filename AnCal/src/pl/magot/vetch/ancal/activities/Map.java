package pl.magot.vetch.ancal.activities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pl.magot.vetch.ancal.R;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.widget.Toast;

import android.util.Log;

public class Map extends MapActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		 
		MapView mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		MapController mc=mapView.getController(); 

		 List<Overlay> mapOverlays = mapView.getOverlays();
		 Drawable drawable = this.getResources().getDrawable(R.drawable.location_red);
		 HelloItemizedOverlay itemizedoverlay = new HelloItemizedOverlay(drawable, this);
		 Point result=new Point(); 
		 result.lat=(double) 34.03152690; 
		 result.lng=(double) (-118.2893710); 
//		 String urlString="https://maps.googleapis.com/maps/api/place/textsearch/json?query=2637+Ellendale+Pl+Los+Angeles+&sensor=true&key=AIzaSyA0_250ptHdc8lbWmxO6D5eJIOkJ5TWaCE"; 
//		 
//		 GrabURL grab = new GrabURL();  
//         grab.execute(urlString);  

//		 Log.d("messages in main: ", MapChange.lat);
//		 Log.d("messages in main: ", MapChange.lng);
	 
		 int lat=(int)(Double.parseDouble(ActivityAppointment.lat)*1E6); 
		 int lng=(int)(Double.parseDouble(ActivityAppointment.lng)*1E6);
		 GeoPoint point = new GeoPoint(lat,lng);
		 OverlayItem overlayitem = new OverlayItem(point, ActivityAppointment.lat, ActivityAppointment.lng);
		 mc.setCenter(point);
		 mc.setZoom(17); 
		 
		 itemizedoverlay.addOverlay(overlayitem);
		 mapOverlays.add(itemizedoverlay);
		 	 
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.layout.appointment, menu);
		return true;
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
