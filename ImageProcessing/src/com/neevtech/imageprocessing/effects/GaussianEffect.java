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
public class GaussianEffect {
	public Bitmap applyGaussianBlur(Bitmap src) {
		double[][] GaussianBlurConfig = new double[][] { { 1, 2, 1 },
				{ 2, 4, 2 }, { 1, 2, 1 } };
		ConvolutionMatrix convMatrix = new ConvolutionMatrix(3);
		convMatrix.applyConfig(GaussianBlurConfig);
		convMatrix.Factor = 16;
		convMatrix.Offset = 0;
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
 * @author Ashish R Agre
 * 
 */
public class GaussianEffect {
	public Bitmap applyGaussianBlur(Bitmap src) {
		double[][] GaussianBlurConfig = new double[][] { { 1, 2, 1 },
				{ 2, 4, 2 }, { 1, 2, 1 } };
		ConvolutionMatrix convMatrix = new ConvolutionMatrix(3);
		convMatrix.applyConfig(GaussianBlurConfig);
		convMatrix.Factor = 16;
		convMatrix.Offset = 0;
		return ConvolutionMatrix.computeConvolution3x3(src, convMatrix);
	}

}
>>>>>>> 89fda57b84f3f45b593875cb6cde08a56a510b8d
