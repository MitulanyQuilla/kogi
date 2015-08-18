package com.utils;

public class UtilsConstants {
	
	public static class DOWNLOAD {
		public static final String TAG_DATA = "data",
				  
				  	TAG_IMAGES 						= "images",
				  	TAG_IMAGES_LOW_RESOLUTION 		= "low_resolution",
				  	TAG_IMAGES_STANDARD_RESOLUTION  = "standard_resolution",
				  	TAG_IMAGES_URL					= "url",
				  
				  	TAG_USER 			= "user",
				  	TAG_USER_USERNAME 	= "username",  
					TAG_USER_FULLNAME 	= "full_name", //IMAGE TITLE
					
					TAG_CREATED_TIME  	= "created_time",
					TAG_TAGS			= "tags",
					TAG_URL_INSTAGRAM   = "link";
		
		public static final String URL_JSON = "https://api.instagram.com/v1/media/popular?client_id=05132c49e9f148ec9b8282af33f88ac7";
	}
	
	public static class PARAMS {
		public static final String ARRAY_IMAGENES 	= "imagenes",
								   ARRAY_PUBLISH	= "publishDate",
								   ARRAY_AUTHOR		= "author",
								   ARRAY_TAG		= "tag",
								   ARRAY_URL		= "url",
								   ARRAY_USERNAME	= "username",
								   ARRAY_FULLNAME	= "fullname",
								   
								   IMAGE_POSITION 	= "imagePosition",
								   
								   BUNDLE_INFO = "Bundle";
		
	}
	
	public static class IMAGE {
		public static final int GALLERY = 1,
								DETAIL  = 2;
		
		public static final String TYPE_GALLERY_IMAGES = "galleryImage";

		
	}
	
	public static class GENERAL {
		public static final String URL_INSTAGRAMA = "https://instagram.com/";
	}

}
