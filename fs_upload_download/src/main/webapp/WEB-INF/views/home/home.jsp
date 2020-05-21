<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<div id="content" class="content">
	<form id="stream_form" accept-charset="UTF-8" method="post" enctype="multipart/form-data" action="/stream_insert">
		<input type="file" name="video" id="video" accept="video/*"/>
		<input type="text" name="title" id="title"/>
		<input type="button" value="upload to server" onClick="stream_upload_server()"/>
		<input type="button" value="upload to sftp" onClick="stream_upload_sftp()"/>		
	</form>
</div>