package com.example.miniprojectv3.chatbot;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniprojectv3.R;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {
    private ArrayList<ChatItem> mChatList;
    private Context context;
    private Activity activity;


    public static class ChatViewHolder extends RecyclerView.ViewHolder{
        public ImageView userFace;
        public TextView message;
        public TextView name;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            userFace = itemView.findViewById(R.id.userFace);
            message = itemView.findViewById(R.id.sentMessage);
            name = itemView.findViewById(R.id.name);
        }
    }

    public ChatAdapter(Activity activity, Context context, ArrayList<ChatItem> chatList) {
        this.activity = activity;
        this.context = context;
        this.mChatList = chatList;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_ui_page, parent, false);
        ChatViewHolder cvh = new ChatViewHolder(v);
        return cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        ChatItem currentItem = mChatList.get(position);
        holder.userFace.setImageResource(currentItem.getImageResource());
        holder.message.setText(currentItem.getText1());
        holder.name.setText(currentItem.getText2());
    }

    @Override
    public int getItemCount() {
        return mChatList.size();
    }


}
