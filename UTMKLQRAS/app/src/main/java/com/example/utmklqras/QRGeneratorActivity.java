package com.example.utmklqras;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QRGeneratorActivity extends AppCompatActivity {

    private EditText description;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_r_generator);

        description = findViewById(R.id.desc);
        imageView = findViewById(R.id.imageView);
    }

    public void QRCodeButton (View view){
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(description.getText().toString(), BarcodeFormat.QR_CODE, 200, 200);
            Bitmap bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.RGB_565);

            for (int x = 0; x < 200; x++) {
                for (int y = 0; y < 200; y++) {
                    bitmap.setPixel(x, y, bitMatrix.get(x, y)? Color.BLACK: Color.WHITE);
                }
            }

            imageView.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}