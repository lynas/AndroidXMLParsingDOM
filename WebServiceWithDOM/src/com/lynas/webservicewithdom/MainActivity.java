package com.lynas.webservicewithdom;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		new DOMParser().execute("");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	
	private class DOMParser extends AsyncTask<String, Void, Void>{

		@Override
		protected Void doInBackground(String... params) {
			String downloadUrl = "http://www.w3schools.com/xml/cd_catalog.xml";
			try {
				URL url = new URL(downloadUrl);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");
				InputStream inputStream =  connection.getInputStream();
				processXML(inputStream);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			return null;
		}
		
		private void processXML(InputStream inputstream) throws Exception{
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document xmlDocument = documentBuilder.parse(inputstream);
			Element rootElement = xmlDocument.getDocumentElement();
			L.m(rootElement.getTagName());
			
			NodeList itemList = rootElement.getElementsByTagName("CD");
			Node currentItem = null; 
			NodeList itemChild = null;
			Node currentChild = null;
			
			 
			
			for(int i=0; i<itemList.getLength();i++){
				currentItem = itemList.item(i);
				L.m(currentItem.getNodeName());
				itemChild = currentItem.getChildNodes();
				for (int j = 0; j < itemChild.getLength(); j++) {
					currentChild = itemChild.item(j);
					if(currentChild.getNodeName().equalsIgnoreCase("TITLE")){
						L.m(currentChild.getTextContent());
					}
				}
			}
		}
		
	}
}













