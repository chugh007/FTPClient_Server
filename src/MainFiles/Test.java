package MainFiles;

import org.apache.commons.net.ftp.FTPFile;

public class Test {

	public static void main(String[] args) throws Exception {
		FTPupload_download f = new FTPupload_download("192.168.1.101",2221,"francis","francis");
		
		if(f.uploadfile("new1.txt", "/home/chugh/cnprograms.txt")){
			System.out.println("File uploaded");
		}
		else
			System.out.println("Some error occurred");
		if(f.downloadfile("new1.txt", "/home/chugh/Desktop/new.txt")){
			System.out.println("File downloaded");
		}
		else
			System.out.println("Some error occurred in download");
		
		/*FTPFile files[] = f.listserverfiles();
		for(FTPFile x : files)
			System.out.println(x.getName().toString());*/
	}

}
