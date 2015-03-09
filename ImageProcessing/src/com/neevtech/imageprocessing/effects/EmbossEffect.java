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
public class EmbossEffect {
	public Bitmap emboss(Bitmap bitmap) {
		double[][] EmbossConfig = new double[][] { { -1, 0, -1 }, { 0, 4, 0 },
				{ -1, 0, -1 } };
		ConvolutionMatrix convMatrix = new ConvolutionMatrix(3);
		convMatrix.applyConfig(EmbossConfig);
		convMatrix.Factor = 1;
		convMatrix.Offset = 127;
		return ConvolutionMatrix.computeConvolution3x3(bitmap, convMatrix);
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
public class EmbossEffect {
	public Bitmap emboss(Bitmap bitmap) {
		double[][] EmbossConfig = new double[][] { { -1, 0, -1 }, { 0, 4, 0 },
				{ -1, 0, -1 } };
		ConvolutionMatrix convMatrix = new ConvolutionMatrix(3);
		convMatrix.applyConfig(EmbossConfig);
		convMatrix.Factor = 1;
		convMatrix.Offset = 127;
		return ConvolutionMatrix.computeConvolution3x3(bitmap, convMatrix);
	}
}
>>>>>>> 89fda57b84f3f45b593875cb6cde08a56a510b8d
