/*
 * Agregará los elementos en el VIewGroup correspondiente
 */

package com.arc.kogi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import android.app.Activity;
import android.provider.SyncStateContract.Constants;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.utils.UtilsConstants;

public class ItemAdapter extends BaseAdapter {
	
	Activity act;
	ArrayList<HashMap<String, String>> mDataList = null;
	
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	
	private class ViewHolder {
		public TextView text;
		public ImageView image;
	}
	
	public ItemAdapter(Activity act, ArrayList<HashMap<String, String>> dataList){
		this.act 	   = act;
		this.mDataList = dataList;
	}

	@Override
	public int getCount() {
		if(mDataList != null){
			return mDataList.size();	
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = convertView;
		try{ 
			final ViewHolder holder;
			if (convertView == null) {
				view = act.getLayoutInflater().inflate(R.layout.layout_fragment_lista_item, parent, false);
				holder = new ViewHolder();
				holder.text = (TextView) view.findViewById(R.id.textView_titulo);
				holder.image = (ImageView) view.findViewById(R.id.imageView_imagen_miniatura);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag(); 
			}
			String url = "", title = "";
			HashMap<String, String> dato = mDataList.get(position);
			for (Entry<String, String> key : dato.entrySet()) {
			    if(key.getKey().equals(UtilsConstants.DOWNLOAD.TAG_IMAGES_LOW_RESOLUTION)){
			    	url = key.getValue();
			    }
			    if(key.getKey().equals(UtilsConstants.DOWNLOAD.TAG_USER_USERNAME)){
			    	title = key.getValue();
			    }
			}
			holder.text.setText(title);
			ImageLoader.getInstance().displayImage(url, holder.image, MainActivity.options, animateFirstListener);
		}catch(Exception e){
			e.printStackTrace();
		}
		return view;
	}
}