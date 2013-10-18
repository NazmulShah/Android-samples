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
