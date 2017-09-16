package com.dmac.rest;

/**
 * Created by aad-pc1 on 7/3/17.
 */
public class RestMain {

    public static void main(String args[]) {
//        RestGETClient client = new RestGETClient();
//        client.hitURL();


        RESTPostClient client = new RESTPostClient();
        client.post("news","docs","{\"qty\":222,\"name\":\"iPad 555\"}");
    }
}
