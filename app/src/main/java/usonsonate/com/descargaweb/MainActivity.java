package usonsonate.com.descargaweb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = findViewById(R.id.imgDownload);

        Picasso.with(this)
                .load("https://raw.githubusercontent.com/martinezrmena/IMAGES/master/DOWNLOAD/image_1.png")
                .error(R.mipmap.ic_launcher)
                .fit()
                .centerInside()
                .into(img);

    }
}
