package com.gmail.berndivader.heewhomee.ai;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map.Entry;

class Utils {
    
    public static String parametersToWWWFormURLEncoded(HashMap<String,String>vars) throws Exception {
        StringBuilder builder=new StringBuilder();
        for(Entry<String,String>var:vars.entrySet()) {
            if(builder.length()>0) builder.append("&");
            builder.append(URLEncoder.encode(var.getKey(),"UTF-8")).append("=");
            builder.append(URLEncoder.encode(var.getValue(),"UTF-8"));
        }
        return builder.toString();
    }
    
    public static String md5(String input) throws Exception {
        MessageDigest md5=MessageDigest.getInstance("MD5");
        md5.update(input.getBytes("UTF-8"));
        BigInteger hash=new BigInteger(1,md5.digest());
        return String.format("%1$032X",hash);
    }

    public static String toAcceptLanguageTags(Locale...locals) {
        if(locals.length==0) return "";
        float f1=1f/(float)locals.length;
        float f2=1f;
        StringBuilder builder=new StringBuilder();
        for (int i1=0;i1<locals.length;i1++) {
            Locale locale = locals[i1];
            if (builder.length()>0) builder.append(", ");
            if (!locale.getCountry().equals("")) {
            	builder.append(locale.getLanguage()).append("-").append(locale.getCountry());
            	builder.append(";q="+f2).append(", ").append(locale.getLanguage()).append(";q="+(f2-0.01));
            } else {
            	builder.append(locale.getLanguage()).append(";q="+f2);
            }
            f2-=f1;
        }
        return builder.toString();
    }

    public static String request(String url,HashMap<String,String>vars) throws Exception {
        HttpURLConnection connection=(HttpURLConnection)URI.create(url).toURL().openConnection();
        connection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.95 Safari/537.36");
        connection.setDoInput(true);
        if (vars!=null&&!vars.isEmpty()) {
            connection.setDoOutput(true);
            try(OutputStreamWriter osw=new OutputStreamWriter(connection.getOutputStream())){
                osw.write(parametersToWWWFormURLEncoded(vars));
                osw.flush();
            }
        }
        String outcome="";
        try(Reader reader=new BufferedReader(new InputStreamReader(connection.getInputStream()))){
            try(StringWriter writer=new StringWriter()){
                char[]chars=new char[1024];
                int n=0;
                while((n=reader.read(chars))!=-1) {
                	writer.write(chars,0,n);
                }
                outcome=writer.toString();
            }
        }
        connection.disconnect();
        return outcome;
    }
    
    public static String xPathSearch(String s1,String s2) throws Exception {
        DocumentBuilder db1=DocumentBuilderFactory.newInstance().newDocumentBuilder();
        XPath xp1=XPathFactory.newInstance().newXPath();
        XPathExpression xPathExpression=xp1.compile(s2);
        String search;
        try(ByteArrayInputStream bytes=new ByteArrayInputStream(s1.getBytes("UTF-8"))){
            Document document=db1.parse(bytes);
            search=(String)xPathExpression.evaluate(document,XPathConstants.STRING);
        }
        return search==null?"":search.trim();
    }
    
    public static String stringAtIndex(String[]strings,int i1) {
        if (i1>=strings.length) return "";
        return strings[i1];
    }
}