package com.example.processimagetest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

  //constants
  public static final String KEY_IMAGE_BINDER = "imageBinder";
  //data
  Bitmap bitmapTest;
  //ui
  private Button btnTest;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    initViews();
    bitmapTest = getBitmap();
  }

  private void initViews() {
    btnTest = findViewById(R.id.btn_test);
    btnTest.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, ShowImageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBinder(KEY_IMAGE_BINDER, new ImageInterface.Stub() {

          @Override public Bitmap getBitmap() throws RemoteException {
            return bitmapTest;
          }
        });
        intent.putExtras(bundle);
        startActivity(intent);
      }
    });
  }

  //实际操作中，bitmap一般是从网络拉取
  private Bitmap getBitmap() {
    return drawableToBitmap(getResources().getDrawable(R.drawable.img_test));
  }

  public static Bitmap drawableToBitmap(Drawable drawable) {
    if (drawable instanceof BitmapDrawable) {
      BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
      if (bitmapDrawable.getBitmap() != null) {
        return bitmapDrawable.getBitmap();
      }
    }
    return null;
  }
}