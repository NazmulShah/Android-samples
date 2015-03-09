package com.neevtech.imageprocessing.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.neevtech.imageprocessing.DistortionActivity;
import com.neevtech.imageprocessing.ImageProcessingActivity;
import com.neevtech.imageprocessing.PaintActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

public class ShareImage {

	public static String shareImage(Bitmap sharingImage,
			String extStorageDirectory, String picname, Context context, int j) {
		String filePath = SaveImage.savingOfBitMap(extStorageDirectory,
				picname, sharingImage, context, j);
		Log.e("Temp saved file path", "path.." + filePath);

		Intent share = new Intent(Intent.ACTION_SEND);
		share.setType("image/jpeg");
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		sharingImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
		File f = new File(filePath);
		try {
			f.createNewFile();
			FileOutputStream fo = new FileOutputStream(f);
			fo.write(bytes.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
		share.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///" + filePath));
		context.startActivity(Intent.createChooser(share, "Share image via"));
		String part = "" + context;
		String[] parts = part.split("@");
		if (parts[0]
				.equals("com.neevtech.imageprocessing.ImageProcesingActivity")) {
			ImageProcessingActivity.isShared = true;
		} else if (parts[0].equals("com.neevtech.imageprocessing.PaintActivity")) {
			PaintActivity.isShared = true;
		}  else if (parts[0].equals("com.neevtech.imageprocessing.DistortionActivity")) {
			DistortionActivity.isShared = true;
		}
		return filePath;
	}

}
