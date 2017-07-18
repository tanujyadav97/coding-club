package com.codingclub.tanujyadav.codingclub;


import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class discussion_fragment extends Fragment {
View view;
    private ListView messagelistView;
    private ProgressBar progressBar;
    private ImageButton photopicker;
    private EditText text;
    private TextView name;
    private Button sendbutton;
    private String username;
    private static final String TAG = "Discussion";
    private static final String ANON = "anonymous";
    private MessageAdapter adapter;
    public static final String MSG_LENGTH_KEY = "msg_length";
    public static final int RC_SIGN_IN = 1;
    public static final int RC_PHOTO_PICKER = 2;
    private FirebaseDatabase database;
    private DatabaseReference messagesdatabaseReference;
    private ChildEventListener childEventListener;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseStorage firebaseStorage;
    private StorageReference photoChatStorageReference;
    private FirebaseRemoteConfig firebaseRemoteConfig;
    public ImageView imv,i;
    int flag=0;
    public discussion_fragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) { //happens before oncreate even starts
        super.onActivityResult(requestCode, resultCode, data);
        //Toast.makeText(getActivity(), "tanuj", Toast.LENGTH_SHORT).show();
         if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK) {

            Uri selectimageuri = data.getData();
            // Toast.makeText(getActivity(), selectimageuri.getLastPathSegment(), Toast.LENGTH_SHORT).show();
            StorageReference photoref = photoChatStorageReference.child(selectimageuri.getLastPathSegment());// file  name of picture will be lastsegment of the uri
            photoref.putFile(selectimageuri).addOnSuccessListener(getActivity(), new OnSuccessListener<UploadTask.TaskSnapshot>() {
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadurl = taskSnapshot.getDownloadUrl();//getting url of pic in storage
                //    Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
                    FriendlyMessage message = new FriendlyMessage(null, username, downloadurl.toString());
                    messagesdatabaseReference.push().setValue(message);//putting it in database
                }
            });
            // OnSignedInInitialize("tanuj");
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_discussion, container, false);
        username = ANON;
        //imv=(ImageView)view.findViewById(R.id.imagethis);
       // imvv=(ImageView)view.findViewById(R.id.photoImageView);
        database = FirebaseDatabase.getInstance(); //getinstance for all functionality
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        messagesdatabaseReference = database.getReference().child("messages");
        photoChatStorageReference = firebaseStorage.getReference().child("chat_photos");

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        photopicker = (ImageButton) view.findViewById(R.id.photoPickerButton);
        sendbutton = (Button) view.findViewById(R.id.sendButton);
        messagelistView = (ListView) view.findViewById(R.id.messageListView);
        text = (EditText) view.findViewById(R.id.messageEditText);

        messagelistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View vieww,
                                    int position, long id) {
         FriendlyMessage fm= (FriendlyMessage) parent.getItemAtPosition(position);

                if(fm.getPhotoUrl()!=null) {

                    flag=1;
                    i = (ImageView) view.findViewById(R.id.imagethis);
                    // Toast.makeText(getActivity(), fm.getPhotoUrl(), Toast.LENGTH_SHORT).show();
                    Glide.with(getActivity()).load(fm.getPhotoUrl()).into(i);
                    i.setVisibility(View.VISIBLE);
                }
            }
        });


        getActivity().startService(new Intent(getActivity(), NotificationService.class));

        progressBar.setVisibility(ProgressBar.INVISIBLE);

        photopicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // to start the photo picker intent(built in)
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
            }
        });

        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    sendbutton.setEnabled(true);
                } else {
                    sendbutton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        text.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1000)});

        sendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username=MainActivity.ps.getString("name","n/a");
                if(username.equals("n/a"))
                {
                    Toast.makeText(getActivity(), "Please login first to send messages", Toast.LENGTH_SHORT).show();
                }
                else {
                    FriendlyMessage newmessage = new FriendlyMessage(text.getText().toString(), username, null);
                    messagesdatabaseReference.push().setValue(newmessage); //push to create new push id
                    text.setText("");
                }
            }
        });

        username=MainActivity.ps.getString("name","n/a");
//username="tanujjjj";
                    OnSignedInInitialize();



        FirebaseRemoteConfigSettings firebaseRemoteConfigSettings = new FirebaseRemoteConfigSettings.Builder().
                setDeveloperModeEnabled(BuildConfig.DEBUG).build();
        firebaseRemoteConfig.setConfigSettings(firebaseRemoteConfigSettings);

        Map<String, Object> defaultConfigMap = new HashMap<>();
        defaultConfigMap.put(MSG_LENGTH_KEY, 1000);
        firebaseRemoteConfig.setDefaults(defaultConfigMap);
        fetchConfig();



        return view;
    }

    private void OnSignedInInitialize() {

        //username = musername;
        List<FriendlyMessage> friendlyMessages = new ArrayList<>();
        adapter = new MessageAdapter(getActivity(), R.layout.item_message, friendlyMessages, username);
        messagelistView.setAdapter(adapter);
        AttachDatabaseReadListener();
    }

    private void OnSignedOutCleanUp() {
        username = ANON;
        if (adapter != null)
            adapter.clear();

    }

    public void AttachDatabaseReadListener() {
        if (childEventListener == null) {
            childEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {//listens to added messages in firebase and displays it
                    FriendlyMessage readmessage = dataSnapshot.getValue(FriendlyMessage.class);
                    adapter.add(readmessage);

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };

            messagesdatabaseReference.addChildEventListener(childEventListener);
        }
    }

    private void DetachDatabaseReadListner() {
        if (childEventListener != null) {
            messagesdatabaseReference.removeEventListener(childEventListener);
            childEventListener = null;
        }
    }

    private void fetchConfig() {
        long CacheExpiration = 3600;

        if (firebaseRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
            CacheExpiration = 0;
        }
        firebaseRemoteConfig.fetch(CacheExpiration).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                firebaseRemoteConfig.activateFetched();
                applyFetchedLimit();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "error", e);
            }
        });
    }

    private void applyFetchedLimit() {
        Long friendlymsglen = firebaseRemoteConfig.getLong(MSG_LENGTH_KEY);
        text.setFilters(new InputFilter[]{new InputFilter.LengthFilter(friendlymsglen.intValue())});
        Log.d(TAG, MSG_LENGTH_KEY + "=" + friendlymsglen);
    }





    @Override
    public void onResume() {

        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){

                    // handle back button
                     if(flag==0)
                     {
                         getActivity().onBackPressed();
                     }
                    else
                     {
                         i.setVisibility(View.GONE);
                         flag=0;
                     }

                    return true;

                }

                return false;
            }
        });
    }
}