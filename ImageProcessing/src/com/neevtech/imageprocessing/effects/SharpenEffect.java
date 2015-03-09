<<<<<<< HEAD
/**
 * 
 */
package com.neevtech.imageprocessing.effects;

import android.graphics.Bitmap;

import com.neevtech.imageprocessing.util.ConvolutionMatrix;

/**
 * @author Shruti
 * 
 */
public class SharpenEffect {
	
	/** 
	 * For sharpness of the image
	 * @param src
	 * @param weight
	 * @return
	 */
	public static Bitmap sharpen(Bitmap src, double weight) {
		double[][] SharpConfig = new double[][] { { 0, -2, 0 },
				{ -2, weight, -2 }, { 0, -2, 0 } };
		ConvolutionMatrix convMatrix = new ConvolutionMatrix(3);
		convMatrix.applyConfig(SharpConfig);
		convMatrix.Factor = weight - 8;
		return ConvolutionMatrix.computeConvolution3x3(src, convMatrix);
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
 * @author Shruti
 * 
 */
public class SharpenEffect {
	
	/** 
	 * For sharpness of the image
	 * @param src
	 * @param weight
	 * @return
	 */
	public static Bitmap sharpen(Bitmap src, double weight) {
		double[][] SharpConfig = new double[][] { { 0, -2, 0 },
				{ -2, weight, -2 }, { 0, -2, 0 } };
		ConvolutionMatrix convMatrix = new ConvolutionMatrix(3);
		convMatrix.applyConfig(SharpConfig);
		convMatrix.Factor = weight - 8;
		return ConvolutionMatrix.computeConvolution3x3(src, convMatrix);
	}

}
>>>>>>> 89fda57b84f3f45b593875cb6cde08a56a510b8d
