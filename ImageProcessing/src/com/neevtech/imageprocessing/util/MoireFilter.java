package com.neevtech.imageprocessing.util;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.util.Log;
/**
 * @author Shruti
 */
public class MoireFilter {
	public static Point [][] moireFilter(Bitmap b) {
		int nWidth = b.getWidth();
		int nHeight = b.getHeight();
		Point[][] pt = new Point[nWidth][nHeight];
		Point mid = new Point();
		mid.x = nWidth / 2;
		mid.y = nHeight / 2;

		double theta, radius, fdegree = 300;
		double newX, newY;

		for (int x = 0; x < nWidth; ++x){
			for (int y = 0; y < nHeight; ++y) {
				pt[x][y] = new Point();
			}
		}

		for (int x = 0; x < nWidth; ++x){
			for (int y = 0; y < nHeight; ++y) {
				int trueX = x - mid.x;
				int trueY = y - mid.y;
				theta = Math.atan2((trueY), (trueX));

				//double newRadius = 5;
				radius = Math.sqrt(trueX * trueX + trueY * trueY);

			/*	double newRadius = radius * radius / (Math.max(mid.x, mid.y));*/

				newX = mid.x + (radius * Math.cos(theta + (fdegree * radius)));

				if (newX > 0 && newX < nWidth) {
					pt[x][y].x = (int) newX;
				} else {
					pt[x][y].x = 0;
					pt[x][y].y = 0;
				}

				newY = mid.y + (radius * Math.sin(theta + (fdegree * radius)));

				if (newY > 0 && newY < nHeight && newX > 0 && newX < nWidth) {
					pt[x][y].y = (int) newY;
					pt[x][y].x = (int) newX;
					Log.e("Pixel calc","Displaced coordinates"+newX+" "+newY);
				} else {
					pt[x][y].x = pt[x][y].y = 0;
				}
			}
		}
		/*offsetFilterAbs(b, pt);*/
		return pt;
	}
	
}
