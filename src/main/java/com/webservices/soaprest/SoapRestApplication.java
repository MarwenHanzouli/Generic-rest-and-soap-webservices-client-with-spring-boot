package com.webservices.soaprest;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class SoapRestApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(SoapRestApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		/*****************TEST REST PUT METHOD*********************/
		Map<String,String> httpParams=new HashMap<>();
		httpParams.put("email", "marwenhanzouli@gmail.com");
		httpParams.put("cv", "marwenhanzouli@gmail.com");
		Map<String,String> httpHeaders=new HashMap<>();
		httpHeaders.put("Content-Type", "application/json");
		httpHeaders.put("Accept", "*/*");
		httpHeaders.put("Authorization","Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtYXJ3ZWVuIiwiZXhwIjoxNjAwNTY5MjcwLCJwcmVub20iOiJtYXJ3ZW5ubm5ubm5ubiIsIm5vbSI6ImhhbnpvdWxpaWlpIiwiaWF0IjoxNjAwNTMzMjcwLCJhdXRob3JpdGllcyI6W3siYXV0aG9yaXR5IjoiUk9MRV9BRE1JTiJ9LHsiYXV0aG9yaXR5IjoiUk9MRV9TVVBFUl9BRE1JTiJ9XSwiZW1haWwiOiJtYXJ3ZW5oYW56b3VsaUBnbWFpbC5jb20ifQ.uth9eIaYXj6aVrNEJehvpAEfQLi2vl-ZFmm_bUBBLpKpyXkmnC1K8j5O_BBfew1CqRam-CGHRTiQ95_dwPpGVw");
		try{
			RestSoapWebServicesClient.Response response= RestSoapWebServicesClient
					.callWebService("http://localhost:8080/arbitres/updateArbitre",
							httpHeaders,null,"{\n" +
									"    \"id\": 102,\n" +
									"    \"email\": \"foughali.ahmed@hotmail.com\",\n" +
									"    \"username\": \"ahmeedfough\",\n" +
									"    \"nom\": \"Foughalii\",\n" +
									"    \"prenom\": \"Ahmed\",\n" +
									"    \"dateNaissance\": \"1994-10-11\",\n" +
									"    \"emailEnabled\": false,\n" +
									"    \"niveau\": \"Professionnel\",\n" +
									"    \"region\": \"Grand Tunis\",\n" +
									"    \"peutArbitrer\": false\n" +
									"}", HttpMethod.PUT);
			System.out.println("body"+response.getBody()+"\n\n\n");
			System.out.println("headers"+response.getHttpHeaders().get("vary")+"\n\n\n");
			System.out.println("status"+response.getStatus()+"\n\n\n");
		}catch (HttpClientErrorException e){
			System.out.println("response body: "+e.getResponseBodyAsString());
		}catch (Exception e){
			System.out.println("messaaaage: "+e.getMessage());
		}

		/*************TEST SOAP METHOD**************************/
		Map<String,String> httpHeaders2=new HashMap<>();
		httpHeaders2.put("Content-Type", "text/xml");
		try{
			RestSoapWebServicesClient.Response response2= RestSoapWebServicesClient
					.callWebService("https://www.w3schools.com/xml/tempconvert.asmx?WSDL", httpHeaders2,null,
					"<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
							"<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
							"  <soap12:Body>\n" +
							"    <CelsiusToFahrenheit xmlns=\"https://www.w3schools.com/xml/\">\n" +
							"      <Celsius>20</Celsius>\n" +
							"    </CelsiusToFahrenheit>\n" +
							"  </soap12:Body>\n" +
							"</soap12:Envelope>", HttpMethod.POST);
			System.out.println("body: "+response2.getBody()+"\n\n\n");
			System.out.println("headers: "+response2.getHttpHeaders().get("vary")+"\n\n\n");
			System.out.println("status: "+response2.getStatus()+"\n\n\n");
		}catch (HttpClientErrorException e){
			System.out.println("response body: "+e.getResponseBodyAsString());
		}catch (Exception e){
			System.out.println("messaaaage: "+e.getMessage());
		}

		/*****************TEST MULTIPART METHOD*********************/
		Map<String,String> httpHeaders3=new HashMap<>();
		httpHeaders3.put("Content-Type", MediaType.MULTIPART_FORM_DATA_VALUE);
		//InputStream file1InputStream = new FileInputStream(new File("C:\\Users\\Lenovo\\Desktop\\automationtest.xls"));
		Map<String,Object> bodyRequest=new HashMap<>();
		bodyRequest.put("apikey","dab475022388957");
		bodyRequest.put("isOverlayRequired",false);
		bodyRequest.put("language","fre");
		bodyRequest.put("file",new File("/home/marwen/Images/Capturede202.png"));
		try{

			RestSoapWebServicesClient.Response response= RestSoapWebServicesClient
					.multipartRequestWebService("https://api.ocr.space/parse/image",
							httpHeaders3,null,bodyRequest, HttpMethod.POST);
			System.out.println("body"+response.getBody()+"\n\n\n");
			System.out.println("headers"+response.getHttpHeaders().get("vary")+"\n\n\n");
			System.out.println("status"+response.getStatus()+"\n\n\n");
		}catch (HttpClientErrorException e){
			System.out.println("response body: "+e.getResponseBodyAsString());
		}catch (Exception e){
			System.out.println("messaaaage: "+e.getMessage());
		}
	}
}
