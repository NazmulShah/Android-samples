<<<<<<< HEAD
/**
 * 
 */
package com.neevtech.imageprocessing.effects;

import android.graphics.Bitmap;

import com.neevtech.imageprocessing.util.ConvolutionMatrix;

/**
 * @author Ashish R Agre
 *
 */
public class EngraveEffect {
	public Bitmap engrave(Bitmap bitmap) {
		ConvolutionMatrix convMatrix = new ConvolutionMatrix(3);
		convMatrix.setAll(0);
		convMatrix.Matrix[0][0] = -2;
		convMatrix.Matrix[1][1] = 2;
		convMatrix.Factor = 1;
		convMatrix.Offset = 95;
		return convMatrix.computeConvolution3x3(bitmap, convMatrix);

	}
}
=======
/**
 * 
 */
package com.neevtech.imageprocessing.effects;

import android.graphics.Bitmap;

import com.neevtech.imageprocessing.util.ConvolutionMatrix;

/**
 * @author Ashish R Agre
 *
 */
public class EngraveEffect {
	public Bitmap engrave(Bitmap bitmap) {
		ConvolutionMatrix convMatrix = new ConvolutionMatrix(3);
		convMatrix.setAll(0);
		convMatrix.Matrix[0][0] = -2;
		convMatrix.Matrix[1][1] = 2;
		convMatrix.Factor = 1;
		convMatrix.Offset = 95;
		return convMatrix.computeConvolution3x3(bitmap, convMatrix);

	}
}
>>>>>>> 89fda57b84f3f45b593875cb6cde08a56a510b8d
