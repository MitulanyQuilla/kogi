package com.arc.kogi;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.utils.PrincipalClass;
import com.utils.UtilsConstants;

public class InstagramPage extends PrincipalClass {

	//private Button button;
    private WebView webView;
    final Activity activity = this;
    public Uri imageUri;
    String webViewUrl = "";
    ArrayList<String> mArrayUrl = null;
    private ProgressDialog progDailog; 
     
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_activity_url);
		
		webView = (WebView) this.findViewById(R.id.webView);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		int posicion = 0;
		Bundle mBundle = getIntent().getExtras();
		
		if (mBundle.containsKey(UtilsConstants.PARAMS.IMAGE_POSITION)) {
			posicion = mBundle.getInt(UtilsConstants.PARAMS.IMAGE_POSITION);
		}
		
		if (mBundle.containsKey(UtilsConstants.PARAMS.ARRAY_AUTHOR)) {
			mArrayUrl = new ArrayList<String>();
			mArrayUrl = mBundle.getStringArrayList(UtilsConstants.PARAMS.ARRAY_AUTHOR);
		}
		
		webViewUrl = UtilsConstants.GENERAL.URL_INSTAGRAMA + mArrayUrl.get(posicion);
		actionBarCambiarNombre(webViewUrl);
		setWebViewSettings(webViewUrl);
	}
	
	private void setWebViewSettings(String url){
		
		 progDailog = ProgressDialog.show(activity, 
				 							getString(R.string.download_text),
				 							getString(R.string.general_wait),
				 							true);
	     progDailog.setCancelable(false);
	        
		webView.getSettings().setJavaScriptEnabled(true);     
	       webView.getSettings().setLoadWithOverviewMode(true);
	       webView.getSettings().setUseWideViewPort(true);        
	        webView.setWebViewClient(new WebViewClient(){

	            @Override
	            public boolean shouldOverrideUrlLoading(WebView view, String url) {
	                progDailog.show();
	                view.loadUrl(url);

	                return true;                
	            }
	            @Override
	            public void onPageFinished(WebView view, final String url) {
	                progDailog.dismiss();
	            }
	        });

	        webView.loadUrl(url);
	}

	@Override
	protected void onResume() {
		super.onResume();

		getActionBar().setLogo(
				getResources()
						.getDrawable(R.drawable.ic_menu_search_holo_light));
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.menu_refresh) {
			webView.reload();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
