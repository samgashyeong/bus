package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    TextView busNumer;
    String getData;
    int busNumber = 0;
    int busNum = 5621;

    String strSrch = busNumber + "";
    int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.data);
        busNumer = findViewById(R.id.bus);

        String serviceUrl = "http://ws.bus.go.kr/api/rest/busRouteInfo/getStaionByRoute";
        String serviceKey = "KSudKwnrnmAe3IzRikeHzW2vfVQ0zAsieDXdBzd1IZZgWMlJPjJJQfp8LDu8jNmlSU0kibIK%2F0fm2x%2BhachMHw%3D%3D";

        String strUrl = serviceUrl + "?ServiceKey" + serviceKey +"&strSrch" + strSrch;

        DownloadWebContent dwc1 =new DownloadWebContent();
        dwc1.execute(strUrl);
    }



    public class DownloadWebContent extends AsyncTask<String, Void, String>{
        @Override

        protected String doInBackground(String... urls){
            try{
                return (String)downloadByUrl((String) urls[0]);
            }
            catch (IOException e){
                return "다운로드 실패";
            }
        }

        protected  void onPostExecute(String result){
            String headerCd = "";
            String busRouteId = "";


            boolean bus_headerCd = false;
            boolean bus_busRouteId = false;

            try{
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);


                XmlPullParser xmlpp= factory.newPullParser();

                ((XmlPullParser) xmlpp).setInput(new StringReader(result));

                int eventType = xmlpp.getEventType();

                while(eventType != XmlPullParser.END_DOCUMENT){
                    if(eventType == XmlPullParser.START_DOCUMENT){

                    }
                    else if(eventType == XmlPullParser.START_TAG){
                        String tag_name = xmlpp.getName();
                        if (tag_name.equals("HeaderCD")) {
                            bus_headerCd = true;
                        }
                        if(tag_name.equals("busRouteId")){
                            bus_busRouteId = true;
                        }
                    }
                    else if(eventType == XmlPullParser.TEXT){
                        if(bus_headerCd){
                            headerCd = xmlpp.getText();
                            bus_headerCd = false;
                        }
                        if(headerCd.equals("0")){
                            if(bus_busRouteId){
                                busRouteId = xmlpp.getText();
                                bus_busRouteId = false;
                            }
                        }
                    }
                    else if(eventType == XmlPullParser.END_TAG){

                    }
                    eventType = xmlpp.next();
                }
            }
            catch (Exception e){
                textView.setText(e.getMessage());
            }
            String serviceUrl = "http://ws.bus.go.kr/api/rest/busRouteInfo/getStaionByRoute";
            String serviceKey = "KSudKwnrnmAe3IzRikeHzW2vfVQ0zAsieDXdBzd1IZZgWMlJPjJJQfp8LDu8jNmlSU0kibIK%2F0fm2x%2BhachMHw%3D%3D";

            String strUrl = serviceUrl + "?ServiceKey" + serviceKey +"&busRouteId" + busRouteId;

            DownloadWebContent dwc2 =new DownloadWebContent();
            dwc2.execute(strUrl);

        }
    public  String downloadByUrl(String myurl) throws IOException{
        HttpURLConnection conn = null;

        try{
            URL url = new URL(myurl);
            conn = (HttpURLConnection)url.openConnection();

            BufferedInputStream buffer = new BufferedInputStream(conn.getInputStream());
            BufferedReader buffer_reader = new BufferedReader((new InputStreamReader(buffer, "utf-8")));
            String line = null;
            getData = "";
            while ((line = buffer_reader.readLine()) != null){
                getData += line;
            }
            return getData;
        }
        finally {
                conn.disconnect();
            }
        }
    }
    public class DownloadWebContant1 extends AsyncTask<String, Void, String>{
        @Override

        protected String doInBackground(String... urls){
            try{
                return (String)downloadByUrl((String) urls[0]);
            }
            catch (IOException e){
                return "다운로드 실패";
            }
        }
        protected void onPostExcute(String result){
            String headerCd = "";
            String gpsX = "";
            String gpsY = "";
            String startlorm = "";
            String direction = "";
            String sectSpd = "";
            
            boolean bus_headerCd = false;
            boolean bus_gpsX = false;
            boolean bus_gpsY = false;
            boolean bus_startlorm = false;
            boolean bus_sectSpd = false;
            boolean bus_direction = false;
            
            textView.append("버스 위치 검색 결과\n");
            
            try {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                
                XmlPullParser xmlpp = factory.newPullParser();
                
                ((XmlPullParser) xmlpp).setInput(new StringReader(result));
                int eventType = ((XmlPullParser) xmlpp).getEventType();
                
                count =0;
                while(eventType != XmlPullParser.END_DOCUMENT){
                    if(eventType == XmlPullParser.START_DOCUMENT){
                        
                    }
                    else if(eventType == XmlPullParser.START_TAG){
                        String tag_name = ((XmlPullParser) xmlpp).getName();
                        switch (tag_name){
                            case "headerCd":
                                bus_headerCd = true;
                                break;
                            case "gpsX" :
                                bus_gpsX = true;
                                break;
                            case "gpsY" :
                                bus_gpsY = true;
                                break;
                            case "sectSpd" :
                                bus_sectSpd = true;
                                break;
                            case "startlorm" :
                                bus_startlorm = true;
                                break;
                            case "direction" :
                                bus_direction = true;
                                break;
                        }
                    }
                    else if(eventType == XmlPullParser.TEXT){
                        if(bus_headerCd){
                            headerCd = xmlpp.getText();
                            bus_headerCd = false;
                        }
                        if(headerCd.equals(0)){
                            if(bus_gpsX){
                                count++;
                                textView.append("----------------------\n");
                                gpsX = xmlpp.getText();
                                textView.append("(" + count + ")" + "gpsX : " + gpsX + "\n");

                                bus_gpsX = false;
                            }
                            if(bus_gpsY){
                                count++;
                                textView.append("----------------------\n");
                                gpsX = xmlpp.getText();
                                textView.append("(" + count + ")" + "gpsY : " + gpsY + "\n");

                                bus_gpsY = false;
                            }
                            if(bus_startlorm){
                                textView.append("----------------------\n");
                                gpsX = xmlpp.getText();
                                textView.append("(" + count + ")" + "정류장 이름 : " + startlorm + "\n");

                                bus_startlorm = false;
                            }
                            if(bus_direction){
                                textView.append("----------------------\n");
                                gpsX = xmlpp.getText();
                                textView.append("(" + count + ")" + "진행방향 : " + direction + "\n");

                                bus_direction = false;
                            }
                            if(bus_sectSpd){
                                textView.append("----------------------\n");
                                gpsX = xmlpp.getText();
                                textView.append("(" + count + ")" + "구간속도 : " + sectSpd + "\n");

                                bus_startlorm = false;
                            }
                        }
                    }
                    else if(eventType == XmlPullParser.END_TAG){
                        
                    }
                    eventType = ((XmlPullParser) xmlpp).next();
                }
                
            }
            catch (Exception e){
                textView.setText(e.getMessage());
            }
            
            
            
            
            
        }
        
        public String downloadByUrl(String myurl) throws IOException{
            HttpURLConnection conn = null;
            BufferedReader buffer_reader;
            try{
                URL url = new URL(myurl);
                conn = (HttpURLConnection)url.openConnection();
                ((HttpURLConnection) conn).setRequestMethod("GET");
                
                BufferedInputStream buffer = new BufferedInputStream(conn.getInputStream());
                buffer_reader = new BufferedReader(new InputStreamReader(buffer, "uft-8"));
                
                String line = null;
                getData = "";
                while((line = buffer_reader.readLine()) != null){
                    getData += line;
                }
                return getData;
            }
            finally {
                conn.disconnect();
            }
        }
    }
    public void plusBusNumber(View v){
        busNum += 1;
        String serviceUrl = "http://ws.bus.go.kr/api/rest/busRouteInfo/getStaionByRoute";
        String serviceKey = "KSudKwnrnmAe3IzRikeHzW2vfVQ0zAsieDXdBzd1IZZgWMlJPjJJQfp8LDu8jNmlSU0kibIK%2F0fm2x%2BhachMHw%3D%3D";
        strSrch = busNum + "";
        String strUrl = serviceUrl + "?ServiceKey" + serviceKey +"&strSrch" + strSrch;
        
        DownloadWebContent dwc1 = new DownloadWebContent();
        
        dwc1.execute(strUrl);
        textView.setText("");
        busNumer.setText("");
        busNumer.append("취조완료 : ");
        busNumer.append(strSrch + "\n");
    }
    
    
    public void minusBusNumber(View v){
        busNum -= 1;
        String serviceUrl = "http://ws.bus.go.kr/api/rest/busRouteInfo/getStaionByRoute";
        String serviceKey = "KSudKwnrnmAe3IzRikeHzW2vfVQ0zAsieDXdBzd1IZZgWMlJPjJJQfp8LDu8jNmlSU0kibIK%2F0fm2x%2BhachMHw%3D%3D";
        strSrch = busNum + "";
        String strUrl = serviceUrl + "?ServiceKey" + serviceKey +"&strSrch" + strSrch;

        DownloadWebContent dwc1 = new DownloadWebContent();

        dwc1.execute(strUrl);
        textView.setText("");
        busNumer.setText("");
        busNumer.append("취소완료 : ");
        busNumer.append(strSrch + "\n");
    }
    public void minusBeek(View v){
        busNum -= 100;
        String serviceUrl = "http://ws.bus.go.kr/api/rest/busRouteInfo/getStaionByRoute";
        String serviceKey = "KSudKwnrnmAe3IzRikeHzW2vfVQ0zAsieDXdBzd1IZZgWMlJPjJJQfp8LDu8jNmlSU0kibIK%2F0fm2x%2BhachMHw%3D%3D";
        strSrch = busNum + "";
        String strUrl = serviceUrl + "?ServiceKey" + serviceKey +"&strSrch" + strSrch;

        DownloadWebContent dwc1 = new DownloadWebContent();

        dwc1.execute(strUrl);
        textView.setText("");
        busNumer.setText("");
        busNumer.append("취소완료 : ");
        busNumer.append(strSrch + "\n");
    }
    public void plusBeek(View v){
        busNum -= 100;
        String serviceUrl = "http://ws.bus.go.kr/api/rest/busRouteInfo/getStaionByRoute";
        String serviceKey = "KSudKwnrnmAe3IzRikeHzW2vfVQ0zAsieDXdBzd1IZZgWMlJPjJJQfp8LDu8jNmlSU0kibIK%2F0fm2x%2BhachMHw%3D%3D";
        strSrch = busNum + "";
        String strUrl = serviceUrl + "?ServiceKey" + serviceKey +"&strSrch" + strSrch;

        DownloadWebContent dwc1 = new DownloadWebContent();

        dwc1.execute(strUrl);
        textView.setText("");
        busNumer.setText("");
        busNumer.append("버스번호 : ");
        busNumer.append(strSrch + "\n");
    }
    
    public void resetCurrentBus(View v){
        String serviceUrl = "http://ws.bus.go.kr/api/rest/busRouteInfo/getStaionByRoute";
        String serviceKey = "KSudKwnrnmAe3IzRikeHzW2vfVQ0zAsieDXdBzd1IZZgWMlJPjJJQfp8LDu8jNmlSU0kibIK%2F0fm2x%2BhachMHw%3D%3D";
        strSrch = busNum + "";
        String strUrl = serviceUrl + "?ServiceKey" + serviceKey +"&strSrch" + strSrch;

        DownloadWebContent dwc1 = new DownloadWebContent();

        dwc1.execute(strUrl);
        textView.setText("");
        busNumer.setText("");
        busNumer.append("버스번호 : ");
        busNumer.append(strSrch + "\n");
    }
}