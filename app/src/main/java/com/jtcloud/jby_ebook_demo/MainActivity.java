package com.jtcloud.jby_ebook_demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.jtcloud.jbyebook.EbookItem;
import com.jtcloud.jbyebook.EbookManager;
import com.jtcloud.jbyebook.WebViewActivity;
import com.jtcloud.jbyebook.interfaces.EbookLoadCallback;
import com.jtcloud.jbyebook.interfaces.RawLoadCallback;
import com.jtcloud.jbyebook.interfaces.SignLoadCallback;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private CustomAdapter adapter;
    List<EbookItem> listItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        EbookManager ebookManager = EbookManager.getInstance();
        ebookManager.configToken("3aadbfcb4d45f55338fed3df7e02b79c1030ab18774de427777c8a13a330ae6a");
        
        
        //第一种方式 获取列表数据模型
        ebookManager.loadData(new EbookLoadCallback() {
            @Override
            public void onDataLoaded(List<EbookItem> items) {
                // 这里可以处理加载完成的数据，例如更新UI
                listItems.clear();
                listItems.addAll(items);
                adapter.notifyDataSetChanged();

            }
        });

        //第二种方式 获取签名(app 应该缓存起来)
        ebookManager.loadSignData(new SignLoadCallback() {
            @Override
            public void onDataLoaded(String sign) {
                System.out.println(sign);


                // 使用改签获取数据
                ebookManager.loadRawData(sign, new RawLoadCallback() {
                    @Override
                    public void onDataLoaded(String raw) {
                        System.out.println(raw);
                    }
                });
            }
        });

        listView = findViewById(R.id.list_view);
        adapter = new CustomAdapter(this, listItems);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 在这里处理点击事件
                // 参数position是被点击项的位置，id是数据集中的行ID
                EbookItem selectedItem = listItems.get(position);
                System.out.println(selectedItem.getText());

                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                intent.putExtra("id", selectedItem.getId().toString());
                startActivity(intent);

            }
        });

    }


}