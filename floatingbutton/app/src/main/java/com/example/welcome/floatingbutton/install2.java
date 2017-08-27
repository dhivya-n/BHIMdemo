package com.example.welcome.floatingbutton;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class install2 extends AppCompatActivity {
    String scanContent,jwt;

    Bundle b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_divert2);
        b=getIntent().getExtras();
        jwt=b.getString("jwt");

        IntentIntegrator scanIntegrator = new IntentIntegrator(this);
        scanIntegrator.initiateScan();


    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        try{
            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
            if (scanningResult != null) {
                scanContent = scanningResult.getContents();

                b=getIntent().getExtras();
                jwt=b.getString("jwt");

                try {
                    // Toast.makeText(LoginActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    // RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    JSONObject body = new JSONObject();
                    body.put("barcode",scanContent);
                    body.put("uid", b.getString("uid"));
                    body.put("type",b.getString("type"));
                     //Toast.makeText(install2.this, body.toString(), Toast.LENGTH_SHORT).show();
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "https://techwhiz.cuff79.hasura-app.io/inventory/install/barcode",body, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                           // Toast.makeText(install2.this, response.toString(), Toast.LENGTH_SHORT).show();
                            try{
                                if(response.optBoolean("success")==false)
                                {
                                    Toast.makeText(install2.this,"Unable to install devive Try again later", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(install2.this,"Install successfull!!!", Toast.LENGTH_SHORT).show();
                                }
                                Intent in=new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(in);
                            }
                            catch(Exception e)
                            {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Volley Error!", error.toString());
                        }
                    }){  @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String,String> params = new HashMap<String, String>();
                        params.put("Content-Type","application/json");
                        params.put("auth","Bearer "+jwt);
                        return params;
                    }};


                    queue.add(jsonObjectRequest);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
