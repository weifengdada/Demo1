package com.example.exam03;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<DataDataBean.ResultBean.DataBean> data;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        getData();
        LinearLayoutManager manager=new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false);
        adapter = new MyAdapter(this);

        recyclerView.setLayoutManager(manager);
        adapter.setOnItemClickListener(new MyAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
              Intent intent=new Intent(MainActivity.this,Second.class);
              intent.putExtra("title",data.get(position).getTitle());
              startActivity(intent);
            }
        });


    }

    private void getData() {

        AsyncTask<Void,Void,String> asyncTask=new AsyncTask<Void,Void,String>() {
            @Override
            protected String doInBackground(Void... voids) {
                String path="http://v.juhe.cn/toutiao/index?type=top&key=dbedecbcd1899c9785b95cc2d17131c5";
                try {
                    URL url=new URL(path.trim());
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setConnectTimeout(5000);
                    urlConnection.setReadTimeout(5000);
                    int responseCode = urlConnection.getResponseCode();
                    if(responseCode==200){
                        InputStream inputStream = urlConnection.getInputStream();
                        String json=streamToString(inputStream,"utf-8");
                        return  json;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "";
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Gson gson=new Gson();
                Log.i("-------",s);
                DataDataBean bean = gson.fromJson(s, DataDataBean.class);
                data = bean.getResult().getData();
                adapter.add(data);
                recyclerView.setAdapter(adapter);

            }
        };
        asyncTask.execute();


    }
    private String streamToString(InputStream inputStream,String charset) {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,charset);

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String s = null;
            StringBuilder builder = new StringBuilder();
            while ((s = bufferedReader.readLine()) != null){
                builder.append(s);
            }

            bufferedReader.close();
            return builder.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }
}
