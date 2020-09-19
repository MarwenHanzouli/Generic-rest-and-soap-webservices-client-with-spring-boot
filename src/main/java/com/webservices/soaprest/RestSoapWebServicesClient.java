package com.webservices.soaprest;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.util.Map;

public class RestSoapWebServicesClient {

    private static final RestTemplate rest=new RestTemplate();

    public static Response callWebService(String uri,
                                          Map<String,String> headersMap,
                                          Map<String,String> paramsQueryMap,
                                          String bodyRequest,
                                          HttpMethod httpMethod) {
        HttpHeaders headers= new HttpHeaders();
        if(headersMap!=null){
            headersMap.forEach((key,value)->{
                headers.add(key,value);
            });
        }
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri);
        if(paramsQueryMap!=null){
            paramsQueryMap.forEach((key,value)->{
                builder.queryParam(key,value);
            });
        }

        String body="";
        if(httpMethod==HttpMethod.POST || httpMethod==HttpMethod.PUT){
            body=bodyRequest;
        }
        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        ResponseEntity<String> responseEntity=null;
        try {
            responseEntity = rest.exchange(builder.toUriString(), httpMethod, requestEntity, String.class);
            String bodyResponse=null;
            if(requestEntity.hasBody()){
                bodyResponse=responseEntity.getBody();
            }
            return new Response(bodyResponse,responseEntity.getHeaders(),responseEntity.getStatusCode());
        }catch (HttpClientErrorException httpClientErrorException){
            throw httpClientErrorException;
        }catch (Exception e){
            throw e;
        }
    }
    public static Response multipartRequestWebService(String uri,
                                                      Map<String,String> headersMap,
                                                      Map<String,String> paramsQueryMap,
                                                      Map<String, Object> bodyRequest,
                                                      HttpMethod httpMethod) {
        HttpHeaders headers= new HttpHeaders();
        if(headersMap!=null){
            headersMap.forEach((key,value)->{
                headers.add(key,value);
            });
        }
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri);
        if(paramsQueryMap!=null){
            paramsQueryMap.forEach((key,value)->{
                builder.queryParam(key,value);
            });
        }
        MultiValueMap<String, Object> body
                = new LinkedMultiValueMap<>();
        if(bodyRequest!=null){
            bodyRequest.forEach((key,value)->{
                if(value instanceof MultipartFile){
                    body.add(key,((MultipartFile) value).getResource());
                }
                else if(value instanceof File){
                    body.add(key,new FileSystemResource((File) value));
                }
                else{
                    body.add(key, value);
                }
            });
        }

        HttpEntity<MultiValueMap<String, Object>> requestEntity
                = new HttpEntity<>(body, headers);
        ResponseEntity<String> responseEntity=null;
        try {
            responseEntity = rest.exchange(builder.toUriString(), httpMethod, requestEntity, String.class);
            String bodyResponse=null;
            if(requestEntity.hasBody()){
                bodyResponse=responseEntity.getBody();
            }
            return new Response(bodyResponse,responseEntity.getHeaders(),responseEntity.getStatusCode());
        }catch (HttpClientErrorException httpClientErrorException){
            throw httpClientErrorException;
        }catch (Exception e){
            throw e;
        }
    }

    static class Response{

        private String body;
        private HttpHeaders httpHeaders;
        private HttpStatus status;

        public Response(String body, HttpHeaders httpHeaders, HttpStatus status) {
            this.body = body;
            this.httpHeaders = httpHeaders;
            this.status = status;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public HttpHeaders getHttpHeaders() {
            return httpHeaders;
        }

        public void setHttpHeaders(HttpHeaders httpHeaders) {
            this.httpHeaders = httpHeaders;
        }

        public HttpStatus getStatus() {
            return status;
        }

        public void setStatus(HttpStatus status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return "Response{" +
                    "body='" + body + '\'' +
                    ", httpHeaders=" + httpHeaders +
                    ", status=" + status +
                    '}';
        }
    }
}
