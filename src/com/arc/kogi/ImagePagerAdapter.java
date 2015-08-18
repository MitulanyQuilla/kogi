/*
 *  Ésta clase nos permite manejar la imagen en el Pager para reusarlas en diferentes fragments y/o Activity
*/
package com.arc.kogi;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.utils.UtilsConstants;

public class ImagePagerAdapter extends PagerAdapter {

	private ArrayList<String> images;
	private LayoutInflater inflater;
	Activity act;
	int position = 0, tipoGaleria = 0;
	Bundle mBundle = null;
	boolean isDetalle = false;

	public ImagePagerAdapter(Activity act, Bundle mBundle, int position) {
		this.inflater = act.getLayoutInflater();
		this.act = act;
		this.mBundle = mBundle;
		this.position = position;
		obtenerInfoBundle();
	}
	
	public ImagePagerAdapter(Activity act, Bundle mBundle, int position, boolean isDetalle) {
		this.inflater = act.getLayoutInflater();
		this.act = act;
		this.mBundle = mBundle;
		this.position = position;
		this.isDetalle = isDetalle;
		obtenerInfoBundle();
	}
	
	private void obtenerInfoBundle(){
		try{
			if (mBundle.containsKey(UtilsConstants.PARAMS.ARRAY_IMAGENES)) {
				images = new ArrayList<String>();
				images = mBundle.getStringArrayList(UtilsConstants.PARAMS.ARRAY_IMAGENES);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public int getCount() {
		if(images != null)
			return images.size();
		return 0;
	}
	
	//Valida dependiendo de una variable en Constants, hacia donde navegará
	public void createActivity(){
		try{
			if(!isDetalle){
				Intent intent = new Intent(act, ImageActivity.class);
				intent = setData(intent);
				act.startActivity(intent);
			}else if(isDetalle){
				ArrayList<String> urls = new ArrayList<String>();
				Bundle mBunInfo = this.mBundle.getBundle(UtilsConstants.PARAMS.BUNDLE_INFO);
				urls = mBunInfo.getStringArrayList(UtilsConstants.PARAMS.ARRAY_AUTHOR);
				
				Intent intent = new Intent(act, InstagramPage.class);
				intent.putExtra(UtilsConstants.PARAMS.IMAGE_POSITION, position - 1);
				intent.putExtra(UtilsConstants.PARAMS.ARRAY_AUTHOR, urls);
				act.startActivity(intent);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public Intent setData(Intent intent){
		intent.putExtra(UtilsConstants.PARAMS.ARRAY_IMAGENES, images);
		intent.putExtra(UtilsConstants.PARAMS.IMAGE_POSITION, position - 1);
		intent.putExtra(UtilsConstants.PARAMS.BUNDLE_INFO, this.mBundle);
		return intent;
	}

	@Override
	public Object instantiateItem(ViewGroup view, int position) {
		View imageLayout = inflater.inflate(R.layout.item_pager_image, view, false);
		assert imageLayout != null;
		ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
		
		this.position = position;
		
		imageView.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				createActivity();
			}
		});
		
		final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.loading);

		try{
			int pos = this.position;
			cambiarTexto(pos);
			MainActivity.imageLoader.displayImage(images.get(pos), imageView, MainActivity.options, new SimpleImageLoadingListener() {	
				
			@Override
			public void onLoadingStarted(String imageUri, View view) {
				spinner.setVisibility(View.VISIBLE);
			}

			@Override
			public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
				String message = null;
				switch (failReason.getType()) {
					case IO_ERROR:
						message = "Input/Output error";
						break;
					case DECODING_ERROR:
						message = "Image can't be decoded";
						break;
					case NETWORK_DENIED:
						message = "Downloads are denied";
						break;
					case OUT_OF_MEMORY:
						message = "Out Of Memory error";
						break;
					case UNKNOWN:
						message = "Unknown error";
						break;
				}

				spinner.setVisibility(View.GONE);
				
			}
			

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				spinner.setVisibility(View.GONE);
			}
		});
		}catch(Exception e){}

		view.addView(imageLayout, 0);
		return imageLayout;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view.equals(object);
	}

	@Override
	public void restoreState(Parcelable state, ClassLoader loader) {
	}

	@Override
	public Parcelable saveState() {
		return null;
	}
	
	private void cambiarTexto(int posicion){
		try{
			if(act != null){
				if(act instanceof ImageActivity){
					((ImageActivity) act).cambiarInformacion(posicion - 1);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}