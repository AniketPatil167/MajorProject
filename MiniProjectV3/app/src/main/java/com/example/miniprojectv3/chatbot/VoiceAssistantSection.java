package com.example.miniprojectv3.chatbot;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniprojectv3.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VoiceAssistantSection extends Fragment {
    private static final int RESULT_OK = 1;
//    private TextView responseTV;
    private EditText input;
    private Button sendButton;
    private ImageView speakButton;
    private ProgressBar progressBar;
    public MessageSender messageSender;
    public RecyclerView chatRecyclerView;
    public RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<ChatItem> chatItems = new ArrayList<>();
    private static final int REQUEST_CODE_SPEECH_INPUT = 1234;
    public String message;
    public String username = "testuser";
    TextToSpeech textToSpeech;
    Context context;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.voice_assistant_section, container, false);
        chatRecyclerView = rootView.findViewById(R.id.chatRecyclerView);
        layoutManager = new LinearLayoutManager(getContext());
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        sendButton = rootView.findViewById(R.id.sendButton);
        speakButton = rootView.findViewById(R.id.speakButton);
//        responseTV = rootView.findViewById(R.id.responseTV);
//        sendUsername = findViewById(R.id.sendUsername);
        input = rootView.findViewById(R.id.sendMessage);
        adapter = new ChatAdapter(getActivity(), getContext(), chatItems);
        chatRecyclerView.setAdapter(adapter);
        message = input.getText().toString();

        textToSpeech = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.US);
                }
            }
        });


        speakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                speechIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to Chatbot");


                try {
                    startActivityForResult(speechIntent, REQUEST_CODE_SPEECH_INPUT);
                }catch (Exception e) {
                    Toast.makeText(getContext(), " " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    System.out.println(e.getMessage());
                }
            }
        });


//        OkHttpClient okHttpClient = new OkHttpClient();
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://898e-2405-201-1f-185a-bc13-1e8e-638-6ce.ngrok.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        messageSender = retrofit.create(MessageSender.class);
        createPost(username, "Hi");


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.isEmpty() && input.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Please enter username and message", Toast.LENGTH_SHORT).show();
                    chatItems.add(new ChatItem(R.drawable.bot_face, "Please enter username and message", "Bot"));
                }else {
                    createPost(username, input.getText().toString());
                    chatItems.add(new ChatItem(R.drawable.user_face, input.getText().toString(), "User"));
                }
            }
        });

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        for(Fragment fragment : getChildFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
        if(requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if(resultCode == Activity.RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(
                        RecognizerIntent.EXTRA_RESULTS
                );
                message = Objects.requireNonNull(result).get(0);
                System.out.println(message);
//                responseTV.setText(Objects.requireNonNull(result).get(0));
                createPost(username, message);
                chatItems.add(new ChatItem(R.drawable.user_face, message, "User"));
            }
        }
    }

    private void createPost(String username, String message) {
        Post post = new Post(username, message);

        
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new ChatAdapter(getActivity(), getContext(), chatItems);

        Call<List<BotResponse>> call = messageSender.createPost(post);
        call.enqueue(new Callback<List<BotResponse>>() {
            @Override
            public void onResponse(Call<List<BotResponse>> call, Response<List<BotResponse>> response) {
                if(!response.isSuccessful()) {
//                    responseTV.setText("Code: " + response.code());
                    System.out.println(response.code());
                    return;
                }
//                BotResponse botResponse = response.body().get();
                List<BotResponse> botResponses = response.body();
                for(BotResponse botResponse : botResponses) {
                    String responseText = botResponse.getText();
//                    responseTV.setText(responseText);
                    chatItems.add(new ChatItem(R.drawable.bot_face, responseText, "Bot"));
                    System.out.println(responseText);
//                    recyclerView.setHasFixedSize(true);
                    chatRecyclerView.setLayoutManager(layoutManager);
                    chatRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    chatRecyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            // Call smooth scroll
                            chatRecyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
                        }
                    });
                    textToSpeech.speak(responseText, TextToSpeech.QUEUE_FLUSH, null, null);
                }

//                Post responsePost = response.body();

//                for(Post post : posts) {
//                    List<String> content = post.getResponse();
//                    responseTV.setText(content + "\n");
//                    System.out.println(content);
//                }
//                String content = responsePost.getResponse();
//                responseTV.setText(content);

            }

            @Override
            public void onFailure(Call<List<BotResponse>> call, Throwable t) {
//                responseTV.setText("Error: " + t.getMessage());
                System.out.println(t.getMessage());
                chatItems.add(new ChatItem(R.drawable.bot_face, t.getMessage(), "Bot"));
            }
        });
    }

}
