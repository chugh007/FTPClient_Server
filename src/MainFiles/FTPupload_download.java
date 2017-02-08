package MainFiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class FTPupload_download {
	private String server,username,password;
	private int port ;
	private FTPClient ftpclient;
	public FTPupload_download(String server,int port,String username,String password) throws SocketException, IOException{
		this.server=server;
		this.port=port;
		this.username=username;
		this.password=password;
		ftpclient = new FTPClient();
		ftpclient.connect(this.server,this.port);
		if(ftpclient.login(this.username, this.password)==false)
			throw new SocketException();
		ftpclient.enterLocalPassiveMode();
		ftpclient.setFileType(FTP.BINARY_FILE_TYPE);
	}
	public boolean uploadfile(String remotefile,String localfile) throws Exception{
		File local = new File(localfile);
		InputStream localinputstream = new FileInputStream(local);
		boolean result = ftpclient.storeFile(remotefile, localinputstream);
		localinputstream.close();
		return result;
	}
	public boolean downloadfile(String remotefile,String localfile) throws Exception{
		File local = new File(localfile);
		OutputStream localoutputstream = new FileOutputStream(local);
		boolean result = ftpclient.retrieveFile(remotefile, localoutputstream);
		localoutputstream.close();
		return result;
	}
	public String[] listserverfiles() throws IOException{
		FTPFile[] f= ftpclient.listFiles();
		String[] result = new String[f.length];
		for(int  i = 0; i < f.length; ++i)
			result[i]=f[i].getName();
		return result;
	}
}
