package com.presto.gallery;

import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;


public class GetSizes extends AsyncTask<String,Void,String[]> {

    @Override
    protected String[] doInBackground(String... strings) {
        //return string array: square image url, original width, original height, original url
        String photo_id = strings[0];
        String response;
        try {
            HttpRequestHandler http = new HttpRequestHandler();
            String url = String.format("https://api.flickr.com/services/rest/?api_key=949e987" +
                    "78755d1982f537d56236bbb42&per_page=10&method=flickr.photos.GetSizes&photo_id=%s",photo_id);
            response = http.getHTTPData(url);

            //parse xml file
            return parseXml(response);


        }catch (Exception e){
            Log.e("Error",e.getMessage());
            e.getStackTrace();

        }


        return null;
    }

    private String[] parseXml(String s){
        String[] results = new String[4];
        try {
            boolean photo = false;
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput( new StringReader(s) ); // pass input whatever xml you have
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if(eventType == XmlPullParser.START_TAG) {
                    if(xpp.getName().equals("rsp")){
                        if(xpp.next() == XmlPullParser.START_TAG){
                            if(xpp.getName().equals("sizes")){

                                while (xpp.next() != XmlPullParser.END_TAG || photo) {
                                    if(xpp.getEventType() == XmlPullParser.START_TAG
                                            && xpp.getName().equals("size")){
                                        if(xpp.getAttributeValue(null, "label").equals("Square")){
                                            results[0] = xpp.getAttributeValue(null, "source");
                                        }
                                        if(xpp.getAttributeValue(null, "label").equals("Original")){
                                            results[1] = xpp.getAttributeValue(null, "width");
                                            results[2] = xpp.getAttributeValue(null,"height");
                                            results[3] = xpp.getAttributeValue(null,"source");
                                        }

                                        photo = true;
                                    }else {
                                        photo = false;
                                    }
                                }
                            }
                        }
                    }
                }
                eventType = xpp.next();
            }
            return results;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


}
