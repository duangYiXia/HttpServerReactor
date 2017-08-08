package com.core.header;

/**
 * ������ƴװ��Ӧͷ��Ϣ����������ӦHTTP������״̬�С���Ϣ��ͷ����Ӧ���ġ� ���ﴦ��״̬�С���Ϣ��ͷ ��Ӧͷ˵����
 * 
 * Location: http://www.it315.org/index.jsp(�����������ʾ�ĸ�ҳ��)
 * 
 * Server:apache tomcat(������������)
 * 
 * Content-Encoding: gzip(���������͵�ѹ�����뷽ʽ)
 * 
 * Content-Length: 80(������������ʾ���ֽ��볤��)
 * 
 * Content-Language: zh-cn(�������������ݵ����Ժ͹�����)
 * 
 * Content-Type: image/jpeg; charset=UTF-8(�������������ݵ����ͺͱ�������)
 * 
 * Last-Modified: Tue, 11 Jul 2000 18:23:51 GMT(���������һ���޸ĵ�ʱ��)
 * 
 * Refresh: 1;url=http://www.it315.org(���������1���Ӻ�ת��URL��ָ���ҳ��)
 * 
 * Content-Disposition: attachment; filename=aaa.jpg(��������������������ط�ʽ���ļ�)
 * 
 * Transfer-Encoding: chunked(�������ֿ鴫�����ݵ��ͻ��ˣ�
 * 
 * Set-Cookie:SS=Q0=5Lb_nQ; path=/search(����������Cookie��ص���Ϣ)
 * 
 * Expires: -1(�����������������Ҫ������ҳ��Ĭ���ǻ���)
 * 
 * Cache-Control: no-cache(�����������������Ҫ������ҳ)
 * 
 * Pragma: no-cache(�����������������Ҫ������ҳ)
 * 
 * Connection: close/Keep-Alive(HTTP����İ汾���ص�)
 * 
 * Date: Tue, 11 Jul 2000 18:23:51 GMT(��Ӧ��վ��ʱ��)
 * 
 * @author pactera
 * 
 */
public class Response {

	// ״̬��
	private String proolVersion;
	private String responseCode;
	private String responseResult;

	// ��Ϣ����
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

	// �򵥵�ƴװ
	public String getRespHeadInfo() {

		StringBuffer responseStr = new StringBuffer();
		location = "http://localhost:8989/WebRoot/index.jsp";
		refresh = "5;url=http://www.baidu.com";
		// ƴװHTTP��Ӧͷ��Ϣ
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
