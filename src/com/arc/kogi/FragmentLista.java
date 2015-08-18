package com.arc.kogi;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class FragmentLista extends Fragment{
	private GridView mGridView = null;
	private Activity mActivity = null;
	private ArrayList<HashMap<String, String>> mDataList = null;
	InterfaceCallBack mCallBack = null;
	
	public FragmentLista(){
		super();
	}

	public FragmentLista(Activity mActivity, ArrayList<HashMap<String, String>> dataList){
		super();
		this.mActivity = mActivity;
		this.mDataList = dataList;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_lista, container, false);
		
		mGridView = (GridView) rootView.findViewById(R.id.ListView_listado);
		mGridView.setAdapter(new ItemAdapter(mActivity, mDataList));
        
		mGridView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				mCallBack.onEntradaSelecionada(position);
			}
        });
		
		mGridView.setChoiceMode(GridView.CHOICE_MODE_SINGLE);

		return rootView;
	}
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallBack = (InterfaceCallBack) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement CallBacks");
        }
    }
	
	public interface InterfaceCallBack {
		public void onEntradaSelecionada(int id);
	}

}
