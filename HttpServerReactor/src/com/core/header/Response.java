package com.core.header;

/**
 * 在这里拼装响应头信息，包括，响应HTTP包括：状态行、消息报头、响应正文。 这里处理状态行、消息报头 响应头说明：
 * 
 * Location: http://www.it315.org/index.jsp(控制浏览器显示哪个页面)
 * 
 * Server:apache tomcat(服务器的类型)
 * 
 * Content-Encoding: gzip(服务器发送的压缩编码方式)
 * 
 * Content-Length: 80(服务器发送显示的字节码长度)
 * 
 * Content-Language: zh-cn(服务器发送内容的语言和国家名)
 * 
 * Content-Type: image/jpeg; charset=UTF-8(服务器发送内容的类型和编码类型)
 * 
 * Last-Modified: Tue, 11 Jul 2000 18:23:51 GMT(服务器最后一次修改的时间)
 * 
 * Refresh: 1;url=http://www.it315.org(控制浏览器1秒钟后转发URL所指向的页面)
 * 
 * Content-Disposition: attachment; filename=aaa.jpg(服务器控制浏览器发下载方式打开文件)
 * 
 * Transfer-Encoding: chunked(服务器分块传递数据到客户端）
 * 
 * Set-Cookie:SS=Q0=5Lb_nQ; path=/search(服务器发送Cookie相关的信息)
 * 
 * Expires: -1(服务器控制浏览器不要缓存网页，默认是缓存)
 * 
 * Cache-Control: no-cache(服务器控制浏览器不要缓存网页)
 * 
 * Pragma: no-cache(服务器控制浏览器不要缓存网页)
 * 
 * Connection: close/Keep-Alive(HTTP请求的版本的特点)
 * 
 * Date: Tue, 11 Jul 2000 18:23:51 GMT(响应网站的时间)
 * 
 * @author pactera
 * 
 */
public class Response {

	// 状态行
	private String proolVersion;
	private String responseCode;
	private String responseResult;

	// 消息报文
	private String location;
	private String refresh;
	private String contentType;
	private String contenLen;
	private String contentEncode;
	private String contentDisposition;
	private String transferEncoding;

	// ...

	public Response(String responseCode, String contentType, String contenLen) {
		this.proolVersion = "HTTP/1.1";
		this.responseCode = responseCode;
		if (responseCode.equals("200")) {
			this.responseResult = "OK";
		}
		this.contentType = contentType;
		this.contenLen = contenLen;
	}

	// 简单的拼装
	public String getRespHeadInfo() {

		StringBuffer responseStr = new StringBuffer();
		location = "http://localhost:8989/WebRoot/index.jsp";
		refresh = "5;url=http://www.baidu.com";
		// 拼装HTTP响应头信息
		responseStr.append(proolVersion + " " + responseCode + " " + responseResult).append("\r\n");
		// responseStr.append("Location: " + location).append("\r\n");
		// responseStr.append("Refresh: " + refresh).append("\r\n");
		// responseStr.append("Content-Disposition: attachment; filename=a.txt").append("\r\n");
		responseStr.append("Content-Type: " + contentType).append("\r\n");
		// responseStr.append("Content-Encoding: gzip").append("\r\n");
		responseStr.append("Content-Length: " + contenLen).append("\r\n");
		// responseStr.append("Connection: close/Keep-Alive").append("\r\n");
		responseStr.append("\r\n");

		System.out.println("respone: " + responseStr.toString());

		return responseStr.toString();
	}

	public String getProolVersion() {
		return proolVersion;
	}

	public void setProolVersion(String proolVersion) {
		this.proolVersion = proolVersion;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseResult() {
		return responseResult;
	}

	public void setResponseResult(String responseResult) {
		this.responseResult = responseResult;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContenLen() {
		return contenLen;
	}

	public void setContenLen(String contenLen) {
		this.contenLen = contenLen;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getRefresh() {
		return refresh;
	}

	public void setRefresh(String refresh) {
		this.refresh = refresh;
	}

	public String getContentEncode() {
		return contentEncode;
	}

	public void setContentEncode(String contentEncode) {
		this.contentEncode = contentEncode;
	}

	public String getContentDisposition() {
		return contentDisposition;
	}

	public void setContentDisposition(String contentDisposition) {
		this.contentDisposition = contentDisposition;
	}

	public String getTransferEncoding() {
		return transferEncoding;
	}

	public void setTransferEncoding(String transferEncoding) {
		this.transferEncoding = transferEncoding;
	}

}
