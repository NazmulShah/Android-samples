<<<<<<< HEAD
package com.neevtech.imageprocessing.util;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.util.Log;

/**
 * @author Shruti
 */

public class RippleFilter {
	public static Point[][] rippleFilter(Bitmap b) {
		int nWidth = b.getWidth();
		int nHeight = b.getHeight();
		Point  [][] pt = new Point[nWidth][nHeight];
		pt = new Point[nWidth][nHeight];
		/*Point mid = new Point();*/
		/*mid.x = nWidth / 2;
		mid.y = nHeight / 2;*/

		/*double theta, radius;*/
		double newX, newY;
		double x0, y0;
		double nWave = 100;

		for (int x = 0; x < nWidth; ++x){
			for (int y = 0; y < nHeight; ++y) {
				pt[x][y] = new Point();
			}
		}

		for (int x = 0; x < nWidth; ++x){
			for (int y = 0; y < nHeight; ++y) {
				
				x0 = ((double)nWave * Math.sin(2.0 * 3.1415 * (float)y/128.0));
				y0 = ((double)nWave * Math.cos(2.0 * 3.1415 * (float)x/128.0));
				
				newX = (x + x0);
				newY = (y + y0);

				if (newX > 0 && newX < nWidth) {
					pt[x][y].x = (int) newX;
				} else {
					pt[x][y].x = 0;
				}

				if (newY > 0 && newY < nHeight && newX > 0 && newX < nWidth) {
					pt[x][y].y = (int) newY;
					pt[x][y].x = (int) newX;
					Log.e("Pixel calc","Displaced coordinates"+newX+" "+newY);
				} else {
					pt[x][y].x = pt[x][y].y = 0;
				}
			}
		}
		return pt;
	}
	
	
}
=======
package com.neevtech.imageprocessing.util;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.util.Log;

/**
 * @author Shruti
 */

public class RippleFilter {
	public static Point[][] rippleFilter(Bitmap b) {
		int nWidth = b.getWidth();
		int nHeight = b.getHeight();
		Point  [][] pt = new Point[nWidth][nHeight];
		pt = new Point[nWidth][nHeight];
		/*Point mid = new Point();*/
		/*mid.x = nWidth / 2;
		mid.y = nHeight / 2;*/

		/*double theta, radius;*/
		double newX, newY;
		double x0, y0;
		double nWave = 100;

		for (int x = 0; x < nWidth; ++x){
			for (int y = 0; y < nHeight; ++y) {
				pt[x][y] = new Point();
			}
		}

		for (int x = 0; x < nWidth; ++x){
			for (int y = 0; y < nHeight; ++y) {
				
				x0 = ((double)nWave * Math.sin(2.0 * 3.1415 * (float)y/128.0));
				y0 = ((double)nWave * Math.cos(2.0 * 3.1415 * (float)x/128.0));
				
				newX = (x + x0);
				newY = (y + y0);

				if (newX > 0 && newX < nWidth) {
					pt[x][y].x = (int) newX;
				} else {
					pt[x][y].x = 0;
				}

				if (newY > 0 && newY < nHeight && newX > 0 && newX < nWidth) {
					pt[x][y].y = (int) newY;
					pt[x][y].x = (int) newX;
					Log.e("Pixel calc","Displaced coordinates"+newX+" "+newY);
				} else {
					pt[x][y].x = pt[x][y].y = 0;
				}
			}
		}
		return pt;
	}
	
	
}
>>>>>>> 89fda57b84f3f45b593875cb6cde08a56a510b8d
