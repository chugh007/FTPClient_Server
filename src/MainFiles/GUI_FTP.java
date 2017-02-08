package MainFiles;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.SocketException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.apache.commons.net.ftp.FTPFile;

public class GUI_FTP extends JFrame {

//some random comments
	public static void main(String[] args) {
		new GUI_FTP();
	}

	JLabel lblserver, lblport, lbluser, lblpass, lblupdown, lblremote,lblserverfiles;
	JTextField txtserver, txtport, txtuser, txtupdown, txtremote;
	JPasswordField txtpass;
	JButton upload, download, connect;
	int textfieldlength = 45;
	JFilePicker filepicker;
	FTPupload_download f;
	JTree servertree;
	DefaultMutableTreeNode root;
	DefaultTreeModel treemodel;
	JPanel pnl = new JPanel();
	String[] serverfilelist=new String[1];
	JScrollPane jscrollpane ;
	private JLabel lblServerFiles;
	GUI_FTP() {
		lblserverfiles = new JLabel("Server Files");
		filepicker = new JFilePicker("Upload/Download", "Browse");
		filepicker.setMode(JFilePicker.MODE_OPEN);
		servertree = new JTree(serverfilelist);
		File fileroot = new File("/");
		root= new DefaultMutableTreeNode(fileroot.getName());
		treemodel = new DefaultTreeModel(root);
		servertree.setVisibleRowCount(5);
		
		jscrollpane = new JScrollPane(servertree);
		lblserver = new JLabel("Server");
		lblport = new JLabel("Port    ");
		lbluser = new JLabel("UserName");
		lblpass = new JLabel("Password");
		lblupdown = new JLabel("Upload/download path");
		lblremote = new JLabel("Remote File");

		txtserver = new JTextField(textfieldlength);
		txtport = new JTextField(textfieldlength);
		txtuser = new JTextField(textfieldlength);
		txtpass = new JPasswordField(textfieldlength);
		txtupdown = new JTextField(textfieldlength);
		txtremote = new JTextField(textfieldlength);

		upload = new JButton("UPLOAD");
		download = new JButton("DOWNLOAD");
		connect = new JButton("CONNECT");

		pnl.add(lblserver);
		pnl.add(txtserver);
		pnl.add(lblport);
		pnl.add(txtport);
		pnl.add(lbluser);
		pnl.add(txtuser);
		pnl.add(lblpass);
		pnl.add(txtpass);
		pnl.add(connect);
		pnl.add(filepicker);
		pnl.add(lblremote);
		pnl.add(txtremote);
		pnl.add(upload);
		pnl.add(download);
		jscrollpane.setName("Server Files");
		getContentPane().add(jscrollpane,BorderLayout.SOUTH);
		
		lblServerFiles = new JLabel("Server FIles");
		jscrollpane.setColumnHeaderView(lblServerFiles);

		ActionListener FTPlistener = new ActionListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == upload) {

					try {
						if (f.uploadfile(txtremote.getText(), filepicker.getSelectedFilePath()))
							JOptionPane.showMessageDialog(null, "FIle UPLOADED successfully!!!", "Message",
									JOptionPane.INFORMATION_MESSAGE);
						else
							JOptionPane.showMessageDialog(null, "File not uploaded Successfully", "Error",
									JOptionPane.ERROR_MESSAGE);

					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "File not uploaded Successfully", "Error",
								JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}finally{
						try {
							maketreefromserver();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
				if (e.getSource() == download) {

					try {servertree.setVisibleRowCount(5);
						if (f.downloadfile(txtremote.getText(), filepicker.getSelectedFilePath()))
							JOptionPane.showMessageDialog(null, "FIle downloadED successfully!!!", "Message",
									JOptionPane.INFORMATION_MESSAGE);
						else
							JOptionPane.showMessageDialog(null, "File not downloaded ", "Error",
									JOptionPane.ERROR_MESSAGE);

					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "File not downloaded!!!!!", "Error",
								JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}finally{
						try {
							maketreefromserver();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
				if (e.getSource() == connect) {
						try {
							f = new FTPupload_download(txtserver.getText(), Integer.parseInt(txtport.getText()),
									txtuser.getText(), txtpass.getText());
							JOptionPane.showMessageDialog(pnl, "Connection Established!!!!", "Message",
									JOptionPane.INFORMATION_MESSAGE);
							maketreefromserver();
						} catch (NumberFormatException | IOException e1) {
							JOptionPane.showMessageDialog(pnl, "Connection not Established!!!!", "Error",
									JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						}
				}
			}

		};
		upload.addActionListener(FTPlistener);
		download.addActionListener(FTPlistener);
		connect.addActionListener(FTPlistener);
		setSize(600, 400);
		this.setTitle("FTP CLIENT UPLOAD/DOWNLOAD By Parth Rastogi and RAhul Chugh");
		setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().add(pnl);
	}
	
	void maketreefromserver() throws IOException {
		
		String[] files = f.listserverfiles();
		for(String x : files){
			root.add(new DefaultMutableTreeNode(x));
		}
		treemodel = new DefaultTreeModel(root);
		servertree.setModel(treemodel);
		servertree.repaint();
	}
}
