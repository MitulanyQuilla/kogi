package com.arc.kogi;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentDetalle extends Fragment{
	
	private ImagePagerAdapter imagePagerAdapter = null;
	public static final String ARG_ID_ENTRADA_SELECIONADA = "item_id";	
	ViewPager pager;	
	int pagerPosition;	
	ArrayList<String> imagenes = null;
	
	private InterfaceImageDetail mCallBack = null;

	public interface InterfaceImageDetail {	
		public void onEntradaSelecionada(int idPosicion, int tipoSeleccion);
		
		public void onNombreImagen(String nombre);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.layout_fragment_detalle, container, false);
		
		pager = (ViewPager) rootView.findViewById(R.id.pager);
		return rootView;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		Bundle mBundle = getArguments();
		int position = 0;
		try{
			if (mBundle.containsKey(FragmentDetalle.ARG_ID_ENTRADA_SELECIONADA)) {
				//Cargamos el contenido de la entrada con cierto ID seleccionado en la lista. 
				position = mBundle.getInt(FragmentDetalle.ARG_ID_ENTRADA_SELECIONADA);
			}
			if(imagePagerAdapter == null){
				imagePagerAdapter = new ImagePagerAdapter(getActivity(), mBundle, position);
			}
			if(pager != null){
				pager.setAdapter(imagePagerAdapter);
				pager.setCurrentItem(position);
			}
			
		} catch (Exception e){
			e.printStackTrace();
		}
	}	
	
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallBack = (InterfaceImageDetail) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement CallBacks");
        }
    }
	
}
