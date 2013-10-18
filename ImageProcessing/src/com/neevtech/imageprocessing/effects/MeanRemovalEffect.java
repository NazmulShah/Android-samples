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
public class MeanRemovalEffect {
	public static Bitmap applyMeanRemoval(Bitmap src) {
		double[][] MeanRemovalConfig = new double[][] { { -1, -1, -1 },
				{ -1, 9, -1 }, { -1, -1, -1 } };
		ConvolutionMatrix convMatrix = new ConvolutionMatrix(3);
		convMatrix.applyConfig(MeanRemovalConfig);
		convMatrix.Factor = 1;
		convMatrix.Offset = 0;
		return ConvolutionMatrix.computeConvolution3x3(src, convMatrix);
	}

}
