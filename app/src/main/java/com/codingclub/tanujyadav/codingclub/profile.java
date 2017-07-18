package com.codingclub.tanujyadav.codingclub;


import android.app.ProgressDialog;
        import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.TextView;

        import com.bumptech.glide.Glide;
        import com.bumptech.glide.load.engine.DiskCacheStrategy;
        import com.google.android.gms.auth.api.Auth;
        import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
        import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
        import com.google.android.gms.auth.api.signin.GoogleSignInResult;
        import com.google.android.gms.common.ConnectionResult;
        import com.google.android.gms.common.SignInButton;
        import com.google.android.gms.common.api.GoogleApiClient;
        import com.google.android.gms.common.api.OptionalPendingResult;
        import com.google.android.gms.common.api.ResultCallback;
        import com.google.android.gms.common.api.Status;



public class profile extends AppCompatActivity implements
        View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 007;

    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;

    private SignInButton btnSignIn;
    private Button btnSignOut, btnRevokeAccess,b;
    private LinearLayout llProfileLayout;
    public ImageView imgProfilePic;
    public TextView txtName, txtEmail,userbt;
    Intent intent;

    public EditText e1,e2,e3,e4; public TextView v1,v2,v3,v4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        e1=(EditText)findViewById(R.id.userbt);
        e2=(EditText)findViewById(R.id.useryt);
        e3=(EditText)findViewById(R.id.userit);
        e4=(EditText)findViewById(R.id.userpt);
        v1=(TextView)findViewById(R.id.userbtt);
        v2=(TextView)findViewById(R.id.userytt);
        v3=(TextView)findViewById(R.id.useritt);
        v4=(TextView)findViewById(R.id.userptt);
        b=(Button)findViewById(R.id.edit);

        intent=new Intent();
        setResult(1,intent);

        btnSignIn = (SignInButton) findViewById(R.id.btn_sign_in);
        btnSignOut = (Button) findViewById(R.id.btn_sign_out);
        btnRevokeAccess = (Button) findViewById(R.id.btn_revoke_access);
        llProfileLayout = (LinearLayout) findViewById(R.id.llProfile);
        imgProfilePic = (ImageView) findViewById(R.id.imgProfilePic);
        txtName = (TextView) findViewById(R.id.txtName);
        txtEmail = (TextView) findViewById(R.id.txtEmail);

        btnSignIn.setOnClickListener(this);
        btnSignOut.setOnClickListener(this);
        btnRevokeAccess.setOnClickListener(this);


        v1.setText(MainActivity.ps.getString("branch","n/a"));
        v2.setText(MainActivity.ps.getString("year","n/a"));
        v3.setText(MainActivity.ps.getString("institute","n/a"));
        v4.setText(MainActivity.ps.getString("phone","n/a"));

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        // Customizing G+ button
        btnSignIn.setSize(SignInButton.SIZE_STANDARD);
        btnSignIn.setScopes(gso.getScopeArray());
    }


    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        updateUI(false);
                    }
                });
        intent=new Intent();
        setResult(2,intent);
        MainActivity.pe.putString("name","n/a");
        MainActivity.pe.putString("email","n/a");
        MainActivity.pe.commit();
    }

    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        updateUI(false);
                    }
                });

        intent=new Intent();
        setResult(2,intent);
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            Log.e(TAG, "display name: " + acct.getDisplayName());

            String personName = acct.getDisplayName();
            String personPhotoUrl=null;
            if(acct.getPhotoUrl()!=null)
            personPhotoUrl = acct.getPhotoUrl().toString();
            String email = acct.getEmail();

            Log.e(TAG, "Name: " + personName + ", email: " + email
                    + ", Image: " + personPhotoUrl);

            txtName.setText(personName);

            txtEmail.setText(email);
            MainActivity.pe.putString("name",personName);
            MainActivity.pe.putString("email",email);
            MainActivity.pe.commit();
            if(personPhotoUrl!=null)
            Glide.with(getApplicationContext()).load(personPhotoUrl)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgProfilePic);
            else
            {
                Drawable myDrawable = getResources().getDrawable(R.drawable.user);
                imgProfilePic.setImageDrawable(myDrawable);
            }
            intent=new Intent();
            setResult(2,intent);
            updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            MainActivity.pe.putString("name","n/a");
            MainActivity.pe.putString("email","n/a");
            MainActivity.pe.commit();
            updateUI(false);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btn_sign_in:
                signIn();
                break;

            case R.id.btn_sign_out:
                signOut();
                break;

            case R.id.btn_revoke_access:
                revokeAccess();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("loading");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    private void updateUI(boolean isSignedIn) {
        if (isSignedIn) {
            btnSignIn.setVisibility(View.GONE);
            btnSignOut.setVisibility(View.VISIBLE);
            btnRevokeAccess.setVisibility(View.VISIBLE);
            //llProfileLayout.setVisibility(View.VISIBLE);
        } else {
            btnSignIn.setVisibility(View.VISIBLE);
            txtEmail.setText("User Email");
            txtName.setText("User");
            Drawable myDrawable = getResources().getDrawable(R.drawable.user);
            imgProfilePic.setImageDrawable(myDrawable);
             btnSignOut.setVisibility(View.GONE);
            btnRevokeAccess.setVisibility(View.GONE);
            MainActivity.pe.putString("name","n/a");
            MainActivity.pe.putString("email","n/a");
            MainActivity.pe.commit();
           // llProfileLayout.setVisibility(View.GONE);
        }
    }
    public void edit(View v)
    {


        if(b.getText().toString().equals("Edit"))
        {
            b.setText("Save");
            e1.setText(v1.getText());
            e2.setText(v2.getText());
            e3.setText(v3.getText());
            e4.setText(v4.getText());
            v1.setVisibility(View.INVISIBLE);
            v2.setVisibility(View.INVISIBLE);
            v3.setVisibility(View.INVISIBLE);
            v4.setVisibility(View.INVISIBLE);
            e1.setVisibility(View.VISIBLE);
            e2.setVisibility(View.VISIBLE);
            e3.setVisibility(View.VISIBLE);
            e4.setVisibility(View.VISIBLE);
        }
        else
        {
            b.setText("Edit");
            v1.setText(e1.getText());
            v2.setText(e2.getText());
            v3.setText(e3.getText());
            v4.setText(e4.getText());

            MainActivity.pe.putString("branch",e1.getText().toString());
            MainActivity.pe.putString("year",e2.getText().toString());
            MainActivity.pe.putString("institute",e3.getText().toString());
            MainActivity.pe.putString("phone",e4.getText().toString());
            MainActivity.pe.commit();

            e1.setVisibility(View.GONE);
            e2.setVisibility(View.GONE);
            e3.setVisibility(View.GONE);
            e4.setVisibility(View.GONE);
            v1.setVisibility(View.VISIBLE);
            v2.setVisibility(View.VISIBLE);
            v3.setVisibility(View.VISIBLE);
            v4.setVisibility(View.VISIBLE);



        }

    }
}