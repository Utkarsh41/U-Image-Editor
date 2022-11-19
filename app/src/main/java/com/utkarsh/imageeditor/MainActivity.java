package com.utkarsh.imageeditor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, imageFileName + ".jpg");
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);

        ContentResolver resolver = getContentResolver();
        uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        OutputStream imageOutStream = null;

        try {
            if (uri == null) {
                throw new IOException("Failed to insert MediaStore row");
            }

            imageOutStream = resolver.openOutputStream(uri);

            if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 100, imageOutStream)) {
                try {
                    throw new IOException("Failed to compress bitmap");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            Toast.makeText(this, "Imave Saved", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (imageOutStream != null) {
                imageOutStream.close();


            }
            Intent intent = new Intent(this, EditImageActivity.class);

            startActivity(intent);




    }
    public void clickSelfie(){

    }
    public void clickGallery(){

    }
    private File createImageFile()  {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
       String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File image = null;
            try {
                image = File.createTempFile(
                        imageFileName,  /* prefix */
                        ".jpg",         /* suffix */
                        storageDir      /* directory */
                );
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Save a file: path for use with ACTION_VIEW intents
     String   currentPhotoPath = image.getAbsolutePath();
        System.out.println(currentPhotoPath);
        return image;
    }
}