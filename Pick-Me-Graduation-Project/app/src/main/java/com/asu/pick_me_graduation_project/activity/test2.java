package com.asu.pick_me_graduation_project.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.asu.pick_me_graduation_project.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by ahmed on 6/13/2016.
 */
public class test2 extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener
    {

        private static final int RC_SIGN_IN = 42;
        private GoogleApiClient mGoogleApiClient;
        private TextView mStatus;


        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.test2);

            // init client
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.server_client_id))
                    .build();
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this, this)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();

            // set listener
            mStatus = (TextView) findViewById(R.id.statuslabel);
            SignInButton buttonSignIn = (SignInButton) findViewById(R.id.buttonGoogleSignIn);
            buttonSignIn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                    startActivityForResult(signInIntent, RC_SIGN_IN);
                }
            });
        }


        @Override
        protected void onStart()
        {
            super.onStart();
            mGoogleApiClient.connect();
        }

        @Override
        protected void onStop()
        {
            super.onStop();
            if (mGoogleApiClient.isConnecting())
                mGoogleApiClient.disconnect();
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data)
        {
            super.onActivityResult(requestCode, resultCode, data);

            // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
            if (requestCode == RC_SIGN_IN)
            {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                if (result.isSuccess())
                {
                    // Signed in successfully, show token
                    GoogleSignInAccount acct = result.getSignInAccount();
                    String idToken = acct.getIdToken();
                    Log.e("Game", "token = " + idToken);

                    String personName= acct.getDisplayName();
                    String personEmail = acct.getEmail();
                    String personId = acct.getId();
                    Uri personPhoto = acct.getPhotoUrl();

                    mStatus.setText(idToken);
                } else
                {
                    mStatus.setText("idToken");
                }
            }
        }


        @Override
        public void onConnectionFailed(ConnectionResult connectionResult)
        {
            Log.e("Game", "on connection failed " + connectionResult.toString());
        }
    }


