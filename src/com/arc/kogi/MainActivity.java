package com.arc.kogi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;

import com.arc.connection.DownloadClass;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.utils.PrincipalClass;
import com.utils.UtilsConnection;
import com.utils.UtilsConstants;
import com.utils.UtilsMessage;

/**
 * Clase principal que se encarga de hacer la gestión de las descargas de las imágenes.
 * @author AndresRubiano
 *
 */
public class MainActivity extends PrincipalClass implements FragmentLista.InterfaceCallBack, FragmentDetalle.InterfaceImageDetail{
	
	private MainActivity mActivity = MainActivity.this;
	
	ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();
	public static DisplayImageOptions  options;
	public static ImageLoader imageLoader = ImageLoader.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public void onStart() {
		super.onStart();
		
		initImageLoader();
		init();
	}
	
	/**
	 * Método que se encarga de iniciar la descarga de las imágenes.
	 */
	private void initImageLoader(){
		try{
			options = new DisplayImageOptions.Builder()
        										.showImageForEmptyUri(R.drawable.ic_empty)
        										.showImageOnFail(R.drawable.ic_error)
        										.resetViewBeforeLoading(true)
        										.cacheInMemory(true)
        										.imageScaleType(ImageScaleType.EXACTLY)
        										.bitmapConfig(Bitmap.Config.RGB_565)
        										.considerExifParams(true)
        										.displayer(new FadeInBitmapDisplayer(300))
        										.build();
        
	        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
	         															.defaultDisplayImageOptions(options)
	         															.build();
	        ImageLoader.getInstance().init(config);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Método que se encarga de realizar la gestión de la pantalla principal. 
	 */
	private void init(){
		try{
			if(UtilsConnection.hasConnection(mActivity)){
				if(dataList.size() > 0){
					dataList.clear();
				}
				DownloadClass mDownloadClass = new DownloadClass(mActivity, getString(R.string.download_text), dataList);
				mDownloadClass.execute();
			} else {
				UtilsMessage.alertaMensaje(mActivity, "titulo", "mensaje	");
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Procedimiento que se encarga de agregar el fragment en la Framelayout de la actividad.
	 */
	public void colocarListaMaestra(){
		try{
			Fragment mFragment = new FragmentLista(mActivity, dataList);
			cambiarFragment(mFragment, R.id.framelayout_maestro);
			cambioItem(0);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void cambiarFragment(Fragment mFragment, int layout){
		getFragmentManager().beginTransaction()
				.replace(layout, mFragment).commit();
	}

	/**
	 * Función que contiene el llamado al Fragment para que se pueda mostrar el detalle de las imágenes.
	 */
	@Override
	public void onEntradaSelecionada(int id) {
		cambioItem(id);
	}
	
	private void cambioItem(int id){
		try{
			Bundle arguments = new Bundle();
			arguments.putInt(FragmentDetalle.ARG_ID_ENTRADA_SELECIONADA, id);
			arguments.putStringArrayList(UtilsConstants.PARAMS.ARRAY_IMAGENES, 
										 obtenerArrayImagenes(UtilsConstants.DOWNLOAD.TAG_IMAGES_STANDARD_RESOLUTION));
			
			arguments.putStringArrayList(UtilsConstants.PARAMS.ARRAY_AUTHOR, 
										obtenerArrayImagenes(UtilsConstants.DOWNLOAD.TAG_USER_USERNAME));
			
			arguments.putStringArrayList(UtilsConstants.PARAMS.ARRAY_PUBLISH, 
										obtenerArrayImagenes(UtilsConstants.DOWNLOAD.TAG_CREATED_TIME));
			
			arguments.putStringArrayList(UtilsConstants.PARAMS.ARRAY_TAG, 
										obtenerArrayImagenes(UtilsConstants.DOWNLOAD.TAG_TAGS));
			
			arguments.putStringArrayList(UtilsConstants.PARAMS.ARRAY_URL, 
										obtenerArrayImagenes(UtilsConstants.DOWNLOAD.TAG_URL_INSTAGRAM));
			
			ArrayList<String> fullname = obtenerArrayImagenes(UtilsConstants.DOWNLOAD.TAG_USER_FULLNAME); 
			arguments.putStringArrayList(UtilsConstants.PARAMS.ARRAY_FULLNAME, 
										fullname);
			
			arguments.putInt(UtilsConstants.IMAGE.TYPE_GALLERY_IMAGES, 
										UtilsConstants.IMAGE.GALLERY);
			
			arguments.putString(UtilsConstants.DOWNLOAD.TAG_CREATED_TIME, 
										dataList.get(id).get(UtilsConstants.DOWNLOAD.TAG_CREATED_TIME)); //PUBLISH DATE
			
			arguments.putString(UtilsConstants.DOWNLOAD.TAG_TAGS, 
										dataList.get(id).get(UtilsConstants.DOWNLOAD.TAG_TAGS)); //TAGS
			
			arguments.putString(UtilsConstants.DOWNLOAD.TAG_USER_USERNAME, 
										dataList.get(id).get(UtilsConstants.DOWNLOAD.TAG_USER_USERNAME)); //AUTHOR
			
			arguments.putString(UtilsConstants.DOWNLOAD.TAG_USER_FULLNAME, 
										dataList.get(id).get(UtilsConstants.DOWNLOAD.TAG_USER_FULLNAME)); //IMAGE TITLE
			
			arguments.putString(UtilsConstants.DOWNLOAD.TAG_URL_INSTAGRAM, 
										dataList.get(id).get(UtilsConstants.DOWNLOAD.TAG_URL_INSTAGRAM)); //URL USER
			
			Fragment fragment = new FragmentDetalle();
			fragment.setArguments(arguments);
			getFragmentManager().beginTransaction().replace(R.id.framelayout_detalle, fragment, "FRAGMENT_DETALLE").commit();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Función que se encarga de obtener todas las rutas de las imágenes de tamaño stándar para enviarla por parámetros
	 * al fragment que contiene el Adapter
	 * @return
	 */
	private ArrayList<String> obtenerArrayImagenes(String keyList){
		ArrayList<String> imagenes = new ArrayList<String>();
		try{
			for(int i=0; i<dataList.size(); i++){
				HashMap<String, String> dato = dataList.get(i);
				for (Entry<String, String> key : dato.entrySet()) {
				    if(key.getKey().equals(keyList)){
				    	imagenes.add(key.getValue());
				    }
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return imagenes;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.menu_refresh) {
			init();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onEntradaSelecionada(int idPosicion, int tipoSeleccion) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onNombreImagen(String nombre) {
		try{
			actionBarCambiarNombre(nombre);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		//Quitamos la línea trasera.
		getActionBar().setDisplayHomeAsUpEnabled(false);
	}
}
