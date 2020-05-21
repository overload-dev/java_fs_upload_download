package com.overload.util;

import java.io.InputStream;
import java.util.Properties;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class SftpConnector {
	//server connection info
	private String url = null;
	private int port;
	private String user = null;
	private String password = null;
	
	//SFTP classes
	private Session session = null;
	private Channel channel = null;
	private ChannelSftp channelSftp = null;

	public SftpConnector(String url, int port, String user, String password) {
		this.url = url;
		this.port = port;
		this.user = user;
		this.password = password;
		connection();
	}

	private void connection() {
		JSch jsch = new JSch();
		try {
			session = jsch.getSession(user, url, port); // create session object
			session.setPassword(password); // set password
			
			// about session configuration setting
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no"); // do not check host key

			session.setConfig(config); // set config
			session.connect(); // sftp server connection

			channel = session.openChannel("sftp"); 
			channel.connect(); // sftp channel connect

		} catch (JSchException e) {
			e.printStackTrace();
		}
		channelSftp = (ChannelSftp) channel;
	}

	public void sendFile(String dir, InputStream inputStream, String fileName) {
		try {
			channelSftp.cd(dir); //change directory by server
			channelSftp.put(inputStream, fileName); //send file stream
		} catch (SftpException e) {
			e.printStackTrace();
		}
	}

	public void disconnection() {
		if (channelSftp != null)
			channelSftp.quit();
		if (session != null)
			session.disconnect();
	}
}
