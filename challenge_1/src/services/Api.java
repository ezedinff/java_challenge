package services;

import models.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.apache.hc.client5.http.ClientProtocolException;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Api {
    private static String BASE_URL = "https://reqres.in/api";
    private CloseableHttpClient httpclient;
    public Api() {
       this.httpclient  = HttpClients.createDefault();
    }

    public List<User> getUsers() throws Exception {
        final HttpGet httpget = new HttpGet(BASE_URL + "/users");
        final String responseBody = this.httpclient.execute(httpget, this.responseHandler());
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(responseBody);
        JSONArray us = (JSONArray) json.get("data");
        List<User> users = new ArrayList<>();
        for (Object user  : us) {
            JSONObject d = (JSONObject) user;
           // System.out.println(d.get("avator").toString());
            User u = new User(d.get("id").toString(), d.get("first_name").toString(), d.get("last_name").toString(), d.get("email").toString(), "");
            u.setJob("");
            users.add(u);

        }
        return users;
    }

    public Object createUser(String jsonString) throws Exception {
        final HttpPost httpPost = new HttpPost(BASE_URL + "/users");
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        StringEntity entity = new StringEntity(jsonString);
        httpPost.setEntity(entity);
        final String responseBody = this.httpclient.execute(httpPost, this.responseHandler());
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(responseBody);
        return json;
    }

    public Object updateUser(String id, String jsonString) throws Exception {
        final HttpPut httpPut = new HttpPut(BASE_URL + "/users" + id);
        httpPut.setHeader("Accept", "application/json");
        httpPut.setHeader("Content-type", "application/json");
        StringEntity entity = new StringEntity(jsonString);
        httpPut.setEntity(entity);
        final String responseBody = this.httpclient.execute(httpPut, this.responseHandler());
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(responseBody);
        return json;
    }

    public void deleteUser(String id) throws IOException  {
        final HttpDelete httpDelete = new HttpDelete(BASE_URL + "/users" + id);
        final String responseBody = this.httpclient.execute(httpDelete, this.responseHandler());
        System.out.println(responseBody);
    }
    HttpClientResponseHandler<String> responseHandler() {
        return new HttpClientResponseHandler<String>() {
            @Override
            public String handleResponse(
                    final ClassicHttpResponse response) throws IOException {
                final int status = response.getCode();
                if (status >= HttpStatus.SC_SUCCESS && status < HttpStatus.SC_REDIRECTION) {
                    final HttpEntity entity = response.getEntity();
                    try {
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } catch (final ParseException ex) {
                        throw new ClientProtocolException(ex);
                    }
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            }

        };
    }
}
