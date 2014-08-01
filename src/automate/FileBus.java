package automate;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;


public class FileBus {
      Boolean connected;
      FTPClient ftp;
      FTPClientConfig config;
      String localPath;
      Set<String> downloaded;
      //String serverPath;
      Set<String> filenames;
      
      
      FileBus(){
            connected = false;
            ftp = new FTPClient();
            config = new FTPClientConfig();
      //    localPath;
            //String serverPath;
            filenames = new HashSet<String>();
            downloaded = new HashSet<String>();
      }
      void download(String url){
    	  String filename = URLDecoder.decode(url.substring(url.lastIndexOf('/') + 1));
    	  if (downloaded.contains(filename)){
    		  return;
    	  }
    	  downloaded.add(filename);
    	  String oldString = url;
    	  if(url.endsWith("jpg")||
    		 url.endsWith("png")||
    		 url.endsWith("gif")||
    		 url.endsWith("pdf")||
    		 url.endsWith("ppt")||
    		 url.endsWith("pptx")||
    		 url.endsWith("doc")||
    		 url.endsWith("docx")||
    		 url.endsWith("jpeg"))
    	  {
	            try{
	            URL website = new URL(url);
	            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
	            FileOutputStream fos = new FileOutputStream(filename);
	            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
	            fos.close();
	            filenames.add(filename);
	            }
	            catch(Exception e){
	                  System.out.println("downloadfailed");
	            }
      	}     
      }
      void connect(String host, String username, String password, String serverPath){
            try{
                  ftp.configure(config);
                  ftp.connect(host);
                  ftp.login(username, password);
                  ftp.changeWorkingDirectory(serverPath);
            }
            catch(Exception e){
                  
            }
            
      }
      void upload(){
            Iterator<String> iter = filenames.iterator();
            FileInputStream f;
            while(iter.hasNext()){
                  try {
                        ftp.setFileType(FTP.BINARY_FILE_TYPE);
                  } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                  }
                  try {
                        String ss = iter.next();
                        f = new FileInputStream(ss);
                        ftp.storeFile(ss,f);
                        f.close();
                  //    filenames.remove(ss);
                  } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                  }
            }
            while(iter.hasNext()){
                  try {
                        String ss = iter.next();;
                        filenames.remove(ss);
                  } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                  }
            }
      }
}

