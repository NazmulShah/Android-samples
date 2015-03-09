package com.neev.android.POC.myjargon.util;

import java.io.*;

public class FileCopy {
	public static void copyfile(String source, String destination){
		try{
			File sourceFile = new File(source);
			File destinationFile = new File(destination);
			InputStream in = new FileInputStream(sourceFile);

			//For Append the file.
			//  OutputStream out = new FileOutputStream(f2,true);

			//For Overwrite the file.
			OutputStream out = new FileOutputStream(destinationFile);

			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0){
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
		}
		catch(FileNotFoundException ex){
			System.out.println(ex.getMessage()+ "in the specified directory..............");
			System.exit(0);
		}
		catch(IOException e){
			System.out.println(e.getMessage());
		}
	}
}
