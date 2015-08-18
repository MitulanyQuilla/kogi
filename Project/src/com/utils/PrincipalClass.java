package com.utils;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;

import com.arc.kogi.R;

public class PrincipalClass extends Activity{
	
	@Override
	public void onStart() {
		super.onStart();

		UtilsGeneral.restoreActionBar(PrincipalClass.this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.global, menu);

		return super.onCreateOptionsMenu(menu);
	}

	// Valida si un item de las opciones del ActionBar, es seleccionado.
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home) {
			this.finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	protected void finalizar() {
		this.finish();
	}
	
	protected void actionBarCambiarNombre(String nombre){
		try{
			getActionBar().setTitle(nombre);
		} catch (Exception e){
			e.printStackTrace();
		}
		
	}

}
