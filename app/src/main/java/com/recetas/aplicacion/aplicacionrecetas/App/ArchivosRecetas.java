package com.recetas.aplicacion.aplicacionrecetas.App;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by anton on 13/05/2017.
 */

public class ArchivosRecetas {


    public static String MEDIA_DIRECTORY = /*APP_DIRECTORY +*/"RecetasApp";//carpeta que me crea
    public static String  IMAGE_DIRECTORY = MEDIA_DIRECTORY+File.separator+"images";

    public  static  void crearImagen(String ruta, Bitmap bmp, Context c) {
        try {
            comprobarDirectorioImagenes();
            File file_imagen = new File(ruta);


            file_imagen.createNewFile();

            FileOutputStream file = new FileOutputStream(ruta);

            bmp.compress(Bitmap.CompressFormat.JPEG, 80, file);
            MediaScannerConnection.scanFile(c,new String[]{file_imagen.toString()},null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                        }
                    });
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static  void comprobarDirectorioImagenes() {
        File dir1 = new File(Environment.getExternalStorageDirectory() + File.separator+IMAGE_DIRECTORY);

        if (!dir1.exists()) {
            dir1.mkdirs();
        }
    }

    public static String getRutaImagen(Uri uri, Context c) {
        String[] projection = { MediaStore.Images.Media.DATA };
        ContentResolver cr = c.getContentResolver();
        Cursor cursor = cr.query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public  static  String fechaActual(){
        SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyyMMddHHmmssSS");
        Date myDate = new Date();
        return timeStampFormat.format(myDate);
    }

    public static  void asignarImagenAImageViewPorRuta(ImageView iv, String ruta) {
        WeakReference<Bitmap> reference = new WeakReference<Bitmap>(BitmapFactory.decodeFile(ruta));
        iv.setImageBitmap(reference.get());
        reference.clear();
    }
}
