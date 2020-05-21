package com.overload.util;

import java.io.InputStream;
import java.util.Properties;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.overload.base.Constants;

public class SftpConnector {
	//SFTP classes
	private Session session = null;
	private Channel channel = null;
	private ChannelSftp channelSftp = null;

	public SftpConnector() {
		connection();
	}

	private void connection() {
		JSch jsch = new JSch();
		try {
			session = jsch.getSession(Constants.SFTP_USER, Constants.SFTP_URL, Constants.SFTP_PORT); // create session object
			session.setPassword(Constants.SFTP_PASSWORD); // set password
			
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
