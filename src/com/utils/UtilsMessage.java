package com.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class UtilsMessage {
	
	public static boolean alertaMensaje(Activity mActivity, String titulo, String mensaje){
		try{
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(mActivity);
			alertDialog.setTitle(titulo);
			alertDialog.setMessage(mensaje);
	
			alertDialog.setPositiveButton("Aceptar",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
						}
					});
				alertDialog.show();
				return true;
			} catch(Exception e){
				e.printStackTrace();
				return false;
			}
	} 

}
