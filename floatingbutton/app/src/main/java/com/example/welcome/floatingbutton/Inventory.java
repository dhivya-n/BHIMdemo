package com.example.welcome.floatingbutton;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Inventory extends AppCompatActivity {
    // String s=new String();
    Bundle b;
    String jwt;
    ListView listView;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        b=getIntent().getExtras();
        jwt=b.getString("jwt");

        try {
            // Toast.makeText(LoginActivity.this, "clicked", Toast.LENGTH_SHORT).show();
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

            //  Toast.makeText(LoginActivity.this, body.toString(), Toast.LENGTH_SHORT).show();
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://techwhiz.cuff79.hasura-app.io/inventory/incomplete",null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                 //   Toast.makeText(Inventory.this,response.toString(), Toast.LENGTH_SHORT).show();

                    try{
                        //String s;
                        //  s=response.getString("quote");
                        //  Toast.makeText(Inventory.this, s, Toast.LENGTH_SHORT).show();
                       table_display(response);
                        }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                       /* if(response.optBoolean("status")==false)
                        {
                            Toast.makeText(disposeActivity.this,"Authentication failed!!Invalid Details", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Bundle b=new Bundle();
                            try {
                                // Toast.makeText(LoginActivity.this, response.getString("jwt"), Toast.LENGTH_SHORT).show();
                                b.putString("jwt", response.getString("jwt"));
                            }
                            catch(Exception e)
                            {
                                e.printStackTrace();
                            }

                            Intent in=new Intent(getApplicationContext(),MainActivity.class);
                            in.putExtras(b);
                            startActivity(in);
                        }*/
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
/*
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        final StringRequest sr = new StringRequest(Request.Method.POST,"https://techwhiz.cuff79.hasura-app.io/viewrequests", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast toast = Toast.makeText(getApplicationContext(),
                      response, Toast.LENGTH_SHORT);
                toast.show();
               table_display(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Error!",error.toString());
            }
        }){
            @Override
            protected Map<String,String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("approver","1d7e6c1b-d133-4f89-bb60-b069aee3f9cf");

                return params;
            }
        };
        queue.add(sr);
*/
//

    }

    void table_display(JSONObject s) {
//        String[] thisIsAStringArray = {"Apple", "Banana", "Orange"};
//        String[] tempArray = new String[ thisIsAStringArray.length + 1 ];
        List<String> a = new ArrayList<String>();

        final JSONArray json;
//    { Toast.makeText(Inventory.this, "here", Toast.LENGTH_SHORT).show();
////        String[] values = new String[20];
//        List<String> a = new ArrayList<String>();
//        a.add("kk");
//        a.add("pp");
//        String[] values1 = new String[a.size()];
//        a.toArray(values1);
//


try{
            json = s.getJSONArray("result");
            int i;
//
//
//            String s1;
//
            try {
//                TableRow row = new TableRow(this);
//                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
//                row.setLayoutParams(lp);
                for (i = 0; i < json.length(); i++) {
                    JSONObject j = json.getJSONObject(i);

//                    TextView qty1 = new TextView(this);
//                    qty1.setText(j.getString("model"));
//                    TextView qty2 = new TextView(this);
//                    qty2.setText(j.getString("manufact"));
//                    TextView qty3 = new TextView(this);
//                    qty3.setText(j.getString("cost"));
//                    TextView qty4 = new TextView(this);
//                    qty4.setText(j.getString("date_of_purchase"));

                    a.add(j.getString("model"));


                }
            } catch (Exception e) {
                e.printStackTrace();
                //   alist.add(s1);
            }

//          //  listView.setAdapter(adapter);
//            // lay.addView(b);
////            TableRow row= new TableRow(this);
////            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
////            row.setLayoutParams(lp);
//////            Button b1=new Button(this);
////            b1.setText("Approve requests");
////            row.addView(b1);
////            // i++;
////            ll.addView(row,1);
//
//            ListView listView = (ListView) findViewById(R.id.list);
//            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                    android.R.layout.simple_list_item_1, android.R.id.text1,values1);
//            listView.setAdapter(adapter);
//
        listView = (ListView) findViewById(R.id.list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1,a);


        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                try {

                    String uid;
                        uid = json.getJSONObject(position).getString("uid");
                        b.putString("uid",uid);
                      //  Toast.makeText(Inventory.this,uid, Toast.LENGTH_SHORT).show();
                        Intent in=new Intent(getApplicationContext(),divert2.class);
                        in.putExtras(b);
                        startActivity(in);
                    }catch(Exception e)
                    {
                        e.printStackTrace();
                    }
              //  Toast.makeText(getApplicationContext(),
                     //   "Position :"+position , Toast.LENGTH_LONG)
                     //   .show();

            }


        });
//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view,
//                                        int position, long id) {
//
////                    int itemPosition     = position;
////
////                    String  itemValue    = (String) listView.getItemAtPosition(position);
////
////                    Toast.makeText(getApplicationContext(),
////                            "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
////                            .show();
//
//                    Toast.makeText(Inventory.this,position, Toast.LENGTH_SHORT).show();
//             /*add
//                    try {
//
//                    String uid;
//                        uid = json.getJSONObject(position).getString("uid");
//                        b.putString("uid",uid);
//                        Toast.makeText(Inventory.this,uid, Toast.LENGTH_SHORT).show();
//                        Intent in=new Intent(getApplicationContext(),divert2.class);
//                        in.putExtras(b);
//                        startActivity(in);
//                    }catch(Exception e)
//                    {
//                        e.printStackTrace();
//                    }
//                    */
//                }
//
//            });
////            b1.setOnClickListener(new View.OnClickListener(){
////
////                @Override
////                public void onClick(View view) {
////
////                    try {
////
////                        Toast.makeText(Inventory.this,"hello", Toast.LENGTH_SHORT).show();
////
////                        Integer i = r1.getCheckedRadioButtonId();
////                        String uid;
////
////                        uid = json.getJSONObject(i).getString("uid");
////                        b.putString("uid",uid);
////                        Toast.makeText(Inventory.this,uid, Toast.LENGTH_SHORT).show();
////                        Intent in=new Intent(getApplicationContext(),divert2.class);
////                        in.putExtras(b);
////                        startActivity(in);
////                    }catch(Exception e)
////                    {
////                        e.printStackTrace();
////                    }
////
////                }
////            });
//
//            //r1.addView();
//            /*
//            i = r1.getCheckedRadioButtonId();
//            String uid = json.getJSONObject(i).getString("uid");
//            Toast.makeText(Inventory.this,uid, Toast.LENGTH_SHORT).show();
//*/
//            //}
//
//        }
//
//        catch(Exception ex)
//        {
//            ex.printStackTrace();
//        }
//    }
    }catch (Exception e) {
        e.printStackTrace();
        //   alist.add(s1);
    }}
}
