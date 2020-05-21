<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html >
<html lang="ko">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title><tiles:insertAttribute name="title" ignore="true"/></title>
</head>
<body>
	
	<div id="page-container">
		<tiles:insertAttribute name="header"/>
		<tiles:insertAttribute name="content"/>
		<tiles:insertAttribute name="page_js"/>
	</div>
	
	<script type="text/javascript">
	// Write javascript to use in common.
	</script>
	
</body>
</html>