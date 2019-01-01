package com.example.hduser.karmaalab;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class HomeActivity extends AppCompatActivity {

    private GridviewAdapter mAdapter;
    private ArrayList<String> listFeatures;
    private ArrayList<Integer> listImages;
    private GridView gridView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        prepareList();
        // prepared arraylist and passed it to the Adapter class
        mAdapter = new GridviewAdapter(this,listFeatures, listImages);
        // Set custom adapter to gridview
        gridView = (GridView) findViewById(R.id.gridView1);
        gridView.setAdapter(mAdapter);

        // Implement On Item click listener
        gridView.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
            //    Toast.makeText(HomeActivity.this, mAdapter.getItem(position), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),AssetActivity.class);
                startActivity(intent);
            }
        });

    }

    public void prepareList()
    {
        listFeatures = new ArrayList<String>();

        listFeatures.add("Dashboard");
        listFeatures.add("Asset");
        /*listFeatures.add("Scheduled Tasks");
        listFeatures.add("Maintenance");
        listFeatures.add("Manage Tickets");
        listFeatures.add("Create Ticket");*/


        listImages = new ArrayList<Integer>();
        listImages.add(R.drawable.ic_action_home1);
        listImages.add(R.drawable.ic_action_asset);
        /*listImages.add(R.drawable.ic_action_task);
        listImages.add(R.drawable.ic_action_maintenance);
        listImages.add(R.drawable.ic_action_ticket);
        listImages.add(R.drawable.ic_action_newticket);*/
    }
}
