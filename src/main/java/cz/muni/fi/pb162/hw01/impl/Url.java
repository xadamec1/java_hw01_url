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
        if(host.contains(":")){
            return host.split(":")[0];
        }
        if(!(host.contains("."))){
            return null;
        }
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
                this.defaultPort = false;
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
            return "";
        }
        int end = web.indexOf("?");
        if( web.contains("/..")){

            if(end == -1){
                return slash(web.substring(index));
            }
            return slash(web.substring(index, end));
        }
        if(end ==-1){
            if(web.charAt(web.length()-1)=='/'){
                String result = web.substring(index+1,web.length()-1);
                return result.replace("/.", "");

            }
            return web.substring(index+1).replace("/.","");
        }
        if(web.charAt(end-1)=='/'){
            return web.substring(index+1,end-1).replace("/.","");
        }
        return web.substring(index+1,end).replace("/.","");
    }

    /***
     * function to find Trailing slashes, used in function public String getPath()
     * @return path if the path is correct, otherwise return ""
     */
    private String slash(String path){
        int index = 0;
        int backPath = path.indexOf("/..");

        while(backPath !=-1){
            for(int i=0;i<backPath;i++){
                if(path.charAt(i)=='/'){
                    index = i;
                }
            }
            String inFront = path.substring(0,index);
            String after = "";
            if(path.length() > backPath+3) {
                after = path.substring(backPath + 3);
            }
            path = inFront+after;
            backPath = path.indexOf("/..");
        }
        if(path.equals("/")){
            return "";
        }
        return path;
    }
    @Override
    public String getQuery() {

        int begin = web.indexOf("?");
        if(begin == -1 || begin == web.length()-1){
            return "";
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
            return "";
        }
        int hashIndex = web.indexOf('#');

        return web.substring(hashIndex+1);
    }

    @Override
    public String getAsString() {
        if(anotherProblems()==false){
            return null;
        }
        String result = protocol+"://"+getHost();
        getPort();
        if(!this.defaultPort){
            result = result+ ":"+getPort();
        }
        if(getPath() != "") {
            if (getPath().charAt(0) == '/') {
                result = result + getPath();
            } else {
                result = result + '/' + getPath();
            }
        }
        if(getQuery()!=""){
            result = result+"?"+getQuery();
        }
        if(getFragment()!= ""){
            result = result+"#"+getFragment();
        }
        return result;
    }

    /**
     * detect bad protocol and domain
     * @return True if protocol and domain are ok else false
     */
    private boolean anotherProblems(){
        if (getPort()==0){
            System.out.println("Protocol missing or does not exist");
            return false;
        }
        if (getHost() == null){
            System.out.println("Domain (.com....) is missing");
            return false;
        }
        return true;
    }
    @Override
    public String getAsRawString() {
        return web;
    }

    @Override
    public boolean isSameAs(SmartUrl url) {
        if(getAsString().equals(url.getAsString())){
            return true;
        }
        return false;
    }

    @Override
    public boolean isSameAs(String url) {
        return false;
    }
}
