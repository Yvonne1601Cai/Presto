package com.presto.gallery;

import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;



public class GetPictures extends AsyncTask<String,Void,List> {

    public interface AsyncResponse{
        void processFinish(List output);
    }
    private AsyncResponse delegate = null;

    GetPictures( AsyncResponse delegate){
        this.delegate = delegate;
    }

    @Override
    protected List doInBackground(String ... strings) {
        String response;
        String perpage = strings[0];
        String page = strings[1];
        try {
            HttpRequestHandler http = new HttpRequestHandler();
            String url = String.format("https://api.flickr.com/services/rest/?api_key=949e987" +
                    "78755d1982f537d56236bbb42&per_page=10&method=flickr.photos.getRecent&perpage=%s&page=%s",perpage,page);
            response = http.getHTTPData(url);

            //parse xml file
            return parseXml(response);


        }catch (Exception e){
            Log.e("Error",e.getMessage());
            e.getStackTrace();

        }
        return null;
    }

    @Override
    protected void onPostExecute(List result) {
        delegate.processFinish(result);
    }

    private List parseXml(String s){
        List<Photo> photos = new ArrayList<>();
        List<Object> result = new ArrayList<>();
        String currentPage = null,totalPages = null;
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
                            if(xpp.getName().equals("photos")){
                                currentPage = xpp.getAttributeValue(null,"page");
                                totalPages = xpp.getAttributeValue(null,"pages");
                                while (xpp.next() != XmlPullParser.END_TAG || photo) {
                                    if(xpp.getEventType() == XmlPullParser.START_TAG
                                            && xpp.getName().equals("photo")){
                                        String id = xpp.getAttributeValue(null, "id");
                                        String title = xpp.getAttributeValue(null,"title");
                                        Photo photoObject = new Photo(id,title);
                                        photos.add(photoObject);
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
            result.add(currentPage);
            result.add(totalPages);
            result.add(photos);
            return result;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
