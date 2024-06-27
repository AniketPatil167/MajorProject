package com.example.miniprojectv3.chatbot;

import java.util.List;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MessageSender {

    @POST("webhooks/rest/webhook")
    Call<List<BotResponse>> createPost(@Body Post post);
}

