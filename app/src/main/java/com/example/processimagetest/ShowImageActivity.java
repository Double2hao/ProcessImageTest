package com.example.processimagetest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

/**
 * author: xujiajia
 * created on: 2021/1/28 10:17 AM
 * description:
 */
public class ShowImageActivity extends Activity {

  //ui
  private Button btnShow;
  private TextView tvShow;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_show);

    initViews();
  }

  private void getBitmapAndShow() {
    Intent intent = getIntent();
    if (intent == null) {
      return;
    }
    Bundle bundle = intent.getExtras();
    if (bundle == null) {
      return;
    }
    IBinder iBinder = bundle.getBinder(MainActivity.KEY_IMAGE_BINDER);
    ImageInterface imageInterface = ImageInterface.Stub.asInterface(iBinder);
    try {
      Bitmap bitmap = imageInterface.getBitmap();
      if (bitmap != null) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        tvShow.setText(String.format("图片大小为：%d*%d", width, height));
      }
    } catch (RemoteException e) {
      e.printStackTrace();
    }
  }

  private void initViews() {
    btnShow = findViewById(R.id.btn_show);
    tvShow = findViewById(R.id.tv_show);

    btnShow.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        getBitmapAndShow();
      }
    });
  }
}
