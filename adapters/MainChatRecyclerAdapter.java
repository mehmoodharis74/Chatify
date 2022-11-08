package com.harismehmood.i200902_i200485.adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.auth.User;
import com.harismehmood.i200902_i200485.R;
import com.harismehmood.i200902_i200485.User.ChatModel;
import com.harismehmood.i200902_i200485.User.UserModel;
import com.harismehmood.i200902_i200485.listeners.MainConversionListener;
import com.harismehmood.i200902_i200485.listeners.UserListener;
import com.harismehmood.i200902_i200485.sharedPreferences.PreferencesManager;

import java.util.List;

public class MainChatRecyclerAdapter extends RecyclerView.Adapter<MainChatRecyclerAdapter.viewholder> {

    Context context;
    List<ChatModel> arrayContacts;
    MainConversionListener conversionListener;
    PreferencesManager sharedPreferences;

    public MainChatRecyclerAdapter(Context context, List<ChatModel> array_contact) {
        this.context = context;
        this.arrayContacts = array_contact;
        this.conversionListener = (MainConversionListener) context;
        sharedPreferences = new PreferencesManager(context);
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(context).inflate(R.layout.main_chats_row_design, parent, false);
        viewholder view_holder = new viewholder(item);
        return view_holder;
    }

    @NonNull
    @Override
    public void onBindViewHolder(@NonNull MainChatRecyclerAdapter.viewholder holder, int position) {
        holder.txtUserName.setText(arrayContacts.get(position).conversationName);
       holder.txtLastMessage.setText(arrayContacts.get(position).message);
        // holder.txtLastMessageTime.setText(arrayContacts.get(position).lastMessageTime);
       holder.imgUserImage.setImageBitmap(getUserBitmapImage(arrayContacts.get(position).conversationImage));
//                if(sharedPreferences.getBoolean("available") == true){
//            holder.onlineCircle.setVisibility(View.VISIBLE);
//        }
//        else if(sharedPreferences.getBoolean("available") == false){
//            holder.onlineCircle.setVisibility(View.INVISIBLE);
//        }
       holder.singleChatRow.setOnClickListener(v -> {
           UserModel user = new UserModel();
           user.userId = arrayContacts.get(position).conversationId;
           user.userName = arrayContacts.get(position).conversationName;
           user.userImage = arrayContacts.get(position).conversationImage;
              conversionListener.onConversionClicked(user);
       });


    }


    @Override
    public int getItemCount() {
        return arrayContacts.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView txtUserName, txtLastMessage, txtLastMessageTime;
        ImageView imgUserImage,onlineCircle;
        LinearLayout singleChatRow;
        //declare elements here like textview, imageview etc.

        public viewholder(@NonNull View itemView) {
            super(itemView);

            // create elements and store data in it by getting element id by item.
            // and store data in those elements get the id of element from where they belongs to
            txtUserName = itemView.findViewById(R.id.mainChatRowUserName);
            txtLastMessage = itemView.findViewById(R.id.mainChatRowLastMessage);
           // txtLastMessageTime = itemView.findViewById(R.id.mainChatRowLastMessageTime);
            imgUserImage = itemView.findViewById(R.id.mainChatRowProfileImage);
            singleChatRow = itemView.findViewById(R.id.mainChatSingleRow);
           // onlineCircle = itemView.findViewById(R.id.onlineCircle);
        }

    }
    public Bitmap getUserBitmapImage(String encodedImage){
        byte [] encodeByte=Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
    }
}
