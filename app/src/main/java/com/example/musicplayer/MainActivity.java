package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private boolean clicked=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        listView=findViewById(R.id.listView1);
        Dexter.withContext(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                ArrayList<File> mysongs=fetchSongs(Environment.getExternalStorageDirectory());
                String [] items=new String[mysongs.size()];
                for(int i=0;i<mysongs.size();i++){
                    items[i]=mysongs.get(i).getName().replace(".mp3","");
                }
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1,items);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent=new Intent(MainActivity.this,MainActivity2.class);
                        String currentSong=listView.getItemAtPosition(i).toString();
                        intent.putExtra("songList",mysongs);
                        intent.putExtra("currentSong",currentSong);
                        intent.putExtra("position",i);
                        startActivity(intent);

                    }
                });
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }

    public ArrayList<File> fetchSongs(File file){
        ArrayList arraylist=new ArrayList();
        File [] songs=file.listFiles();
        if(songs!=null){
            for(File myfile : songs){
                if(myfile.isDirectory() && !myfile.isHidden()){
                    arraylist.addAll(fetchSongs(myfile));
                }
                else{
                    if(myfile.getName().endsWith(".mp3") && !myfile.getName().startsWith(".")){
                        arraylist.add(myfile);
                    }
                }
            }
        }
        else{
            Log.d("null","null is there");
        }
        return arraylist;
    }
}