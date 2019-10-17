package cz.muni.fi.pb162.hw01.impl;

import cz.muni.fi.pb162.hw01.url.SmartUrl;

import java.util.Arrays;

/**
 * @author Petr Adamec
 */
public class Url implements SmartUrl {
    private String web;
    private String host;
    private String protocol;
    private boolean defaultPort= true;

    /***
     * ses
     * @param address is god
     */
    public Url(String address){
        web = address;
        int protocolIndex = address.indexOf(":");
        protocol = address.substring(0,protocolIndex);


    }

    /***
     * int
     * @return host int
     */
    public String getHost() {
        String[] splitted = web.split("//");
        String[] hostSplit = splitted[1].split("/");
        host = hostSplit[0];
        return host;
    }

    @Override
    public String getProtocol() {
        return protocol;
    }

    @Override
    public int getPort() {
        String[] splittedWeb = web.split(":");
        if(splittedWeb.length !=2) {
            return this.notDefault(splittedWeb);
        }
        protocol.toLowerCase();
        switch (protocol){
            case("http"):
                return 80;

            case("https"):
                return 443;
            case("ftp"):
                    int i = 21;
                    return i;
            case("ssh"):
            case("sftp"):
                return 22;
            default:
                return 0;
        }

    }

    /***
     *
     * @param splittedWeb splitted
     * @return port number
     */
    private int notDefault(String[] splittedWeb){
        String[] portArray = splittedWeb[2].split("/");
        int port = Integer.parseInt(portArray[0]);
        if(!(port == 80 || port == 443 || port == 21 || port == 22)){
            this.defaultPort = false;
        }
        return port;
    }
    @Override
    public String getPath() {
        int index = web.indexOf("/",8);
        if (index == -1 || index == web.length()-1){
            return null;
        }
        int end = web.indexOf("?");
        if(web.contains("/..")){
            int backPath = web.indexOf("/..");
            int counter = 0;
            for(int i = 0; i<web.substring(index).length();i++){
                if(web.substring(index).charAt(i)=='/'){
                    counter = counter-1;
                }
            }
            while (backPath !=-1){
                counter = counter+1;
                if(backPath != web.length()-1){
                    backPath = web.indexOf("/..",backPath+1);
                }
                else{
                    break;
                }
            }
            if(counter >=-3){
                return null;
            }
        }
        if(end ==-1){
            if(web.charAt(web.length()-1)=='/'){
                return web.substring(index,web.length()-1);
            }
            return web.substring(index);
        }
        return web.substring(index,end);
    }

    @Override
    public String getQuery() {

        int begin = web.indexOf("?");
        if(begin == -1 || begin == web.length()-1){
            return null;
        }
        String[] query = web.substring(begin+1).split("&");
        Arrays.sort(query);
        String result = "?";
        result = String.join("&",query);
        return result ;
        }


    @Override
    public String getFragment() {
        if(!(web.contains("#"))){
            return null;
        }
        int hashIndex = web.indexOf('#');
        int endIndex = web.indexOf('?');
        if (endIndex == -1){
            return web.substring(hashIndex);
        }
        return web.substring(hashIndex,endIndex-1);
    }

    @Override
    public String getAsString() {
        String result = protocol+"://"+getHost();
        if(this.defaultPort == false){
            result = result+ ":"+getPort();
        }
        if(getPath() != null){
            result = result + getPath();
        }
        if(getFragment()!= null){
            result = result+"#"+getFragment();
        }
        if(getQuery()!=null){
            result = result+"?"+getQuery();
        }
        return result;
    }

    @Override
    public String getAsRawString() {
        return web;
    }

    @Override
    public boolean isSameAs(SmartUrl url) {
        return false;
    }

    @Override
    public boolean isSameAs(String url) {
        return false;
    }
}
