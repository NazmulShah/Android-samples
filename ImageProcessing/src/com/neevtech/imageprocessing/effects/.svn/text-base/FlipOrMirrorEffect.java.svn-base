<<<<<<< HEAD
/**
 * 
 */
package com.neevtech.imageprocessing.effects;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.neevtech.imageprocessing.constants.Neevimagick;

/**
 * @author Shruti
 *
 */
public class FlipOrMirrorEffect {
	// type definition

	public static Bitmap flip(Bitmap src, int type) {
		// create new matrix for transformation
		Matrix matrix = new Matrix();
		// if vertical
		if (type == Neevimagick.FLIP_VERTICAL) {
			// y = y * -1
			matrix.preScale(1.0f, -1.0f);
		}
		// if horizonal
		else if (type == Neevimagick.FLIP_HORIZONTAL) {
			// x = x * -1
			matrix.preScale(-1.0f, 1.0f);
			// unknown type
		} else {
			return null;
		}

		// return transformed image
		return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(),
				matrix, true);
	}

}
=======
/**
 * 
 */
package com.neevtech.imageprocessing.effects;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.neevtech.imageprocessing.constants.Neevimagick;

/**
 * @author Shruti
 *
 */
public class FlipOrMirrorEffect {
	// type definition

	public static Bitmap flip(Bitmap src, int type) {
		// create new matrix for transformation
		Matrix matrix = new Matrix();
		// if vertical
		if (type == Neevimagick.FLIP_VERTICAL) {
			// y = y * -1
			matrix.preScale(1.0f, -1.0f);
		}
		// if horizonal
		else if (type == Neevimagick.FLIP_HORIZONTAL) {
			// x = x * -1
			matrix.preScale(-1.0f, 1.0f);
			// unknown type
		} else {
			return null;
		}

		// return transformed image
		return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(),
				matrix, true);
	}

}
>>>>>>> 89fda57b84f3f45b593875cb6cde08a56a510b8d
