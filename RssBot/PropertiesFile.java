package com.searchweb.util;

import java.io.FileInputStream;
import java.io.InterruptedIOException;
import java.util.Properties;

public class PropertiesFile {

	public static Properties load(String CONFIGFILE) {

		Properties props = null;
	    FileInputStream istream = null;
	    try {
	      istream = new FileInputStream(CONFIGFILE);
	      props = new Properties();
	      props.load(istream);
	      istream.close();
	    }
	    catch (Exception e) {
	      if (e instanceof InterruptedIOException || e instanceof InterruptedException) {
	          Thread.currentThread().interrupt();
	      }
	      e.printStackTrace();
	    } finally {
	        if(istream != null) {
	            try {
	                istream.close();
	            } catch(InterruptedIOException ignore) {
	                Thread.currentThread().interrupt();
	            } catch(Throwable ignore) {
	            }

	        }
	    }
	    return props;
	}
}
