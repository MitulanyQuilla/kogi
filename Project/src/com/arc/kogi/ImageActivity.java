package com.arc.kogi;

import java.util.ArrayList;
import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.utils.PrincipalClass;
import com.utils.UtilsConstants;

public class ImageActivity extends PrincipalClass{
	
	//Variables a usar
	ViewPager pager;	
	private ShareActionProvider mShareActionProvider;
	String url_user, txtPublishDate, txtAuthor, txtTag;
	int pagerPosition = 0;
	TextView tvPublishDate = null, tvAuthor = null, tvTag = null;
	private ImagePagerAdapter imagePagerAdapter = null;
	ArrayList<String> imagenes = null, mArrayAuthors = null, mArrayPublish = null, mArrayTag = null, mArrayFullname = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_activity_image);
		
		initIntentExtra();
		initControls();
		setInfo();
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		getActionBar().setLogo(getResources().getDrawable(R.drawable.ic_menu_gallery));
	}
	
	//Inicializamos los componentes a usar
	public void initControls(){
		tvPublishDate = (TextView)findViewById(R.id.tv_ima_det_publish);
		tvAuthor = (TextView)findViewById(R.id.tv_ima_det_author);
		tvTag = (TextView)findViewById(R.id.tv_ima_det_tag);
		
		pager = (ViewPager) findViewById(R.id.pager_medium_image);
	}
	
	private void setInfo(){
		if(tvPublishDate != null){
			tvPublishDate.setText(txtPublishDate);
		}
		
		if(tvAuthor != null){
			tvAuthor.setText(txtAuthor);
		}
		
		if(tvTag != null){
			tvTag.setText(txtTag);
		}
	}
	
	//Obtenemos los valores que son enviados por parámetro
	public void initIntentExtra(){
		try {
			Bundle mBundle = getIntent().getExtras();
			Bundle mBundleInfo = mBundle.getBundle(UtilsConstants.PARAMS.BUNDLE_INFO);
			pagerPosition 	= mBundleInfo.getInt(FragmentDetalle.ARG_ID_ENTRADA_SELECIONADA, 0);
			url_user 		= mBundleInfo.getString(UtilsConstants.DOWNLOAD.TAG_URL_INSTAGRAM, "");
			txtPublishDate	= mBundleInfo.getString(UtilsConstants.DOWNLOAD.TAG_CREATED_TIME, "");
			txtAuthor		= mBundleInfo.getString(UtilsConstants.DOWNLOAD.TAG_USER_USERNAME, "");
			txtTag 			= mBundleInfo.getString(UtilsConstants.DOWNLOAD.TAG_TAGS, "");
			
			if (mBundleInfo.containsKey(UtilsConstants.PARAMS.ARRAY_AUTHOR)) {
				mArrayAuthors = new ArrayList<String>();
				mArrayAuthors = mBundleInfo.getStringArrayList(UtilsConstants.PARAMS.ARRAY_AUTHOR);
			}
			
			if (mBundleInfo.containsKey(UtilsConstants.PARAMS.ARRAY_PUBLISH)) {
				mArrayPublish = new ArrayList<String>();
				mArrayPublish = mBundleInfo.getStringArrayList(UtilsConstants.PARAMS.ARRAY_PUBLISH);
			}
			
			if (mBundleInfo.containsKey(UtilsConstants.PARAMS.ARRAY_TAG)) {
				mArrayTag = new ArrayList<String>();
				mArrayTag = mBundleInfo.getStringArrayList(UtilsConstants.PARAMS.ARRAY_TAG);
			}
			
			if (mBundleInfo.containsKey(UtilsConstants.PARAMS.ARRAY_FULLNAME)) {
				mArrayFullname = new ArrayList<String>();
				mArrayFullname = mBundleInfo.getStringArrayList(UtilsConstants.PARAMS.ARRAY_FULLNAME);
			}
			
			if (mBundle.containsKey(UtilsConstants.PARAMS.ARRAY_IMAGENES)) {
				imagenes = new ArrayList<String>();
				imagenes = mBundle.getStringArrayList(UtilsConstants.PARAMS.ARRAY_IMAGENES);
			}

		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	
	//Usamos el menú
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        
        mShareActionProvider = (ShareActionProvider) menu.findItem(R.id.menu_item_share).getActionProvider();
        mShareActionProvider.setShareIntent(getDefaultShareIntent());
 
        return true;
	}
	
	/** Returns a share intent */
    private Intent getDefaultShareIntent(){
    	try{
	        Intent intent = new Intent(Intent.ACTION_SEND);
	        intent.setType("text/plain");
	        if(pagerPosition > -1 && imagenes != null)
	        	intent.putExtra(Intent.EXTRA_TEXT,imagenes.get(pagerPosition));
	        return intent;
    	}catch(Exception e){
    		e.printStackTrace();
    		return null;
    	}
    }
	@Override
	public void onStart() {
		super.onStart();
		
		Bundle mBundle = getIntent().getExtras();
		int posicion = 0;
		try{
			Bundle mBundleInfo = mBundle.getBundle(UtilsConstants.PARAMS.BUNDLE_INFO);
			String titulo  = mBundleInfo.getString(UtilsConstants.DOWNLOAD.TAG_USER_FULLNAME, "");
			//Cambiamos el nombre a mostrar
			actionBarCambiarNombre(titulo);
			
			if (mBundle.containsKey(UtilsConstants.PARAMS.IMAGE_POSITION)) {
				//Cargamos el contenido de la entrada con cierto ID seleccionado en la lista. 
				posicion = mBundle.getInt(UtilsConstants.PARAMS.IMAGE_POSITION);
			}
			
		} catch(Exception e){
			e.printStackTrace();
		}
		
		if(imagePagerAdapter == null){
			imagePagerAdapter = new ImagePagerAdapter(ImageActivity.this, mBundle, posicion, true);
		}
		
		if(pager != null){
			pager.setAdapter(imagePagerAdapter);
			pager.setCurrentItem(posicion);
		}
	}
	
	/*
	 * Método que se encarga de actualizar la información
	 */
	public void cambiarInformacion(int posicion){
		try{
			pagerPosition = posicion;
			if(tvAuthor != null){
				tvAuthor.setText(mArrayAuthors.get(posicion));
			}
			
			if(tvPublishDate != null){
				Long mDate = new Long((mArrayPublish.get(posicion)));
				Date d = new Date(mDate * 1000);
				tvPublishDate.setText(d.toString());
			}
			
			if(tvTag != null){
				tvTag.setText(obtenerTags(mArrayTag.get(posicion)));
			}
			
			actionBarCambiarNombre(mArrayFullname.get(posicion));
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	
	private String obtenerTags(String dato){
		String datoFinal = "";
		try{
			String newString = dato.replace("[", "");
			String newStringTwo = newString.replace("]", "");
			String [] cadenaUno = newStringTwo.split(",");
			for(String data : cadenaUno){
				String dataDos = data.replace("\"", "");
				datoFinal += "#" + dataDos + ",";
			}
			if(datoFinal.equals("#,"))
				datoFinal = "";
		}catch(Exception e){
			e.printStackTrace();
		}
		return datoFinal;
	}
}

