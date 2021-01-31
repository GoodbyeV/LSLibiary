package com.example.lslibiary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.jnexecutorlibrary.JnExecutor;
import com.example.jnrecycleviewitem.FirstItem;
import com.example.jnrecycleviewitem.ItemData;
import com.example.jnrecycleviewitem.JnAdapter;
import com.example.jnrecycleviewitem.JnDataItem;
import com.example.loglibrary.LsLog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private JnAdapter adapter;
    private List<JnDataItem<?, RecyclerView.ViewHolder>> items = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.rl);
//        adapter = new JnAdapter(this);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
//        items.add(new FirstItem(new ItemData("hha")));
//        items.add(new FirstItem(new ItemData("ssss")));
//        adapter.addItems(items,false);
//        adapter.notifyDataSetChanged();
        ExecutorDemo();
    }
    private void printLog(){
        LsLog.a("9900");
    }

    private void ExecutorDemo(){
        for (int priority = 0; priority < 10; priority++) {
            int flag=priority;
            JnExecutor.INSTANCE.execute(priority, new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }


}