package com.arc.connection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.arc.kogi.MainActivity;
import com.utils.UtilsConstants;

public class DownloadClass extends AsyncTask<Void, Void, Void>{
	
	private ProgressDialog pDialog;
	private MainActivity mActivity = null;
	private String mDialogMessage = "";
	ArrayList<HashMap<String, String>> mDataList = null;
	
	public DownloadClass(MainActivity mActivity, String dialogMessage, ArrayList<HashMap<String, String>> dataList){
		this.mActivity 		= mActivity;
		this.mDialogMessage = dialogMessage;
		this.mDataList 		= dataList;
	}
	
	@Override
	protected void onPreExecute(){
		super.onPreExecute();
		if(mActivity != null){
			pDialog = new ProgressDialog(mActivity);
			pDialog.setMessage(mDialogMessage);
			pDialog.setCancelable(true);
			pDialog.show();
		}
	}
	
	@Override
	protected Void doInBackground(Void...arg0){
		ServiceHandler sh = new ServiceHandler();
		
		try{
			String jsonStr = sh.makeServiceCall(UtilsConstants.DOWNLOAD.URL_JSON, ServiceHandler.GET);
			if(jsonStr != null && jsonStr.length() > 0){
				JSONObject jsonObj = new JSONObject(jsonStr);
				
				JSONArray datas = jsonObj.getJSONArray(UtilsConstants.DOWNLOAD.TAG_DATA);
				
				for(int i=0; i<datas.length(); i++){
					JSONObject c = datas.getJSONObject(i);
					
					//Images node
					JSONObject images 			= c.getJSONObject(UtilsConstants.DOWNLOAD.TAG_IMAGES);
					
					JSONObject lowResolution 	= images.getJSONObject(UtilsConstants.DOWNLOAD.TAG_IMAGES_LOW_RESOLUTION);
					String url_low 				= lowResolution.getString(UtilsConstants.DOWNLOAD.TAG_IMAGES_URL);
					
					JSONObject standartResolution 	= images.getJSONObject(UtilsConstants.DOWNLOAD.TAG_IMAGES_STANDARD_RESOLUTION);
					String url_standard 			= standartResolution.getString(UtilsConstants.DOWNLOAD.TAG_IMAGES_URL);
					
					//User Node
					JSONObject user = c.getJSONObject(UtilsConstants.DOWNLOAD.TAG_USER);
					String username = user.getString(UtilsConstants.DOWNLOAD.TAG_USER_USERNAME);
					String fullName = user.getString(UtilsConstants.DOWNLOAD.TAG_USER_FULLNAME);
					
					String createdTime = c.getString(UtilsConstants.DOWNLOAD.TAG_CREATED_TIME);
					String tags		   = c.getString(UtilsConstants.DOWNLOAD.TAG_TAGS);
					String url		   = c.getString(UtilsConstants.DOWNLOAD.TAG_URL_INSTAGRAM);
					
					HashMap<String, String> contact = new HashMap<String, String>();
					contact.put(UtilsConstants.DOWNLOAD.TAG_IMAGES_LOW_RESOLUTION, 	url_low);
					contact.put(UtilsConstants.DOWNLOAD.TAG_IMAGES_STANDARD_RESOLUTION, url_standard);
					contact.put(UtilsConstants.DOWNLOAD.TAG_USER_USERNAME, 			username);
					contact.put(UtilsConstants.DOWNLOAD.TAG_USER_FULLNAME, 			fullName);
					contact.put(UtilsConstants.DOWNLOAD.TAG_CREATED_TIME, 			createdTime);
					contact.put(UtilsConstants.DOWNLOAD.TAG_TAGS, 					tags);
					contact.put(UtilsConstants.DOWNLOAD.TAG_URL_INSTAGRAM, 			url);
					
					mDataList.add(i, contact);
				}
			}
		}catch(JSONException e){
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result){
		super.onPostExecute(result);
		
		for(int i=0; i<mDataList.size(); i++){
			HashMap<String, String> dato = mDataList.get(i);
			int j = 1;
			for (Entry<String, String> key : dato.entrySet()) {
			    String llave = key.getKey();
			    String valor = key.getValue();
			    Log.i("datos", "Item : " + j + " - Llave => " + llave  + "Valor => " + valor);
			    j++;
			}
		}
		
		if(pDialog.isShowing()){
			pDialog.dismiss();
		}
		mActivity.colocarListaMaestra();
	}
	
}
