package usonsonate.com.descargaweb;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ImageView img;
    private ArrayList<ImageView> IMAGENES;
    private static final int DELAY = 500;
    ProgressDialog Dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = findViewById(R.id.imgDownload);

        IMAGENES = new ArrayList<ImageView>();
        final int IdImageView = 0;

        Dialog = new ProgressDialog(this);
        Dialog.setMessage("Cargando");
        Dialog.show();

        Picasso.with(this).load("https://raw.githubusercontent.com/martinezrmena/IMAGES/master/DOWNLOAD/image_1.png")
                .into(picassoImageTarget(getApplicationContext(), "imageDir", "my_image.jpeg"));


        final ImageView imagen = new ImageView(MainActivity.this);
        Picasso.with(MainActivity.this)
                .load("https://raw.githubusercontent.com/martinezrmena/IMAGES/master/DOWNLOAD/image_1.png")
                .error(R.mipmap.ic_launcher)
                .fit()
                .centerInside()
                .into(imagen);
        IMAGENES.add(imagen);

        img.setImageDrawable(IMAGENES.get(0).getDrawable());
        Dialog.cancel();




    }


    private Target picassoImageTarget(Context context, final String imageDir, final String imageName) {
        Log.d("picassoImageTarget", " picassoImageTarget");
        ContextWrapper cw = new ContextWrapper(context);
        final File directory = cw.getDir(imageDir, Context.MODE_PRIVATE); // path to /data/data/yourapp/app_imageDir

        return new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final File myImageFile = new File(directory, imageName); // Create image file
                        FileOutputStream fos = null;
                        try {
                            fos = new FileOutputStream(myImageFile);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.i("image", "image saved to >>>" + myImageFile.getAbsolutePath());

                    }
                }).start();
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
            }
            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                if (placeHolderDrawable != null) {}
            }
        };
    }
}
