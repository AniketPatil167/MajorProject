package com.example.miniprojectv3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EmergencyContactsAdapter extends RecyclerView.Adapter<EmergencyContactsAdapter.ContactsViewHolder> {

    private Context context;
    private ArrayList contacts;

    public EmergencyContactsAdapter(Context context, ArrayList contacts) {
        this.context = context;
        this.contacts = contacts;
    }
    @NonNull
    @Override
    public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.contacts_recyclerview, parent, false);
        return new ContactsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmergencyContactsAdapter.ContactsViewHolder holder, int position) {
        holder.tvContacts.setText(String.valueOf(contacts.get(position)));
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class ContactsViewHolder extends RecyclerView.ViewHolder {
        TextView tvContacts;
        public ContactsViewHolder(@NonNull View itemView) {
            super(itemView);

            tvContacts = itemView.findViewById(R.id.tvContacts);

        }
    }
}
