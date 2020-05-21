<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
<script>
	function stream_upload_server(){
		var formData = new FormData(document.getElementById('stream_form'));
		
		if(!formData.get('file')){
			alert('파일이 누락 되었습니다.');
			return;
		}
		
		if(!formData.get('title')){
			alert('제목을 입력하세요.');
			return;
		}
		
		$.ajax({
		    type : 'POST',
		    url : '/upload_local_server',
		    enctype: 'multipart/form-data',
		    data : formData,
		    processData: false,
		    contentType: false,
		    error : function(error) {
		    	console.log(error);
		        alert("Error!");
		    },
		    success : function(data) {
		        alert("success!");
		    },
		    complete : function() {
		        alert("complete!");    
		    }
		});
	}
	
	function stream_upload_sftp(){
		var formData = new FormData(document.getElementById('stream_form'));
		
		if(!formData.get('file')){
			alert('파일이 누락 되었습니다.');
			return;
		}
		
		if(!formData.get('title')){
			alert('제목을 입력하세요.');
			return;
		}
		
		$.ajax({
		    type : 'POST',
		    url : '/upload_sftp_server',
		    enctype: 'multipart/form-data',
		    data : formData,
		    processData: false,
		    contentType: false,
		    error : function(error) {
		    	console.log(error);
		        alert("Error!");
		    },
		    success : function(data) {
		        alert("success!");
		    },
		    complete : function() {
		        alert("complete!");    
		    }
		});
	}
</script>