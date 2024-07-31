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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jtcloud.jbyebook.EbookItem;
import com.jtcloud.jbyebook.EbookManager;
import com.jtcloud.jbyebook.WebViewActivity;
import com.jtcloud.jbyebook.interfaces.EbookLoadCallback;
import com.jtcloud.jbyebook.interfaces.EvaluationListener;
import com.jtcloud.jbyebook.interfaces.RawLoadCallback;
import com.jtcloud.jbyebook.interfaces.SignLoadCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        ebookManager.configToken("xiaoyuanbd284dbe3d7bdf");


        // 设置监听
        ebookManager.setEvaluationListener(new EvaluationListener() {
            @Override
            public void onEvaluation(String json) {
                System.out.println("onEvaluation");
                System.out.println(json);
            }
        });


        //获取签名
        ebookManager.loadSignData(new SignLoadCallback() {
            @Override
            public void onDataLoaded(String sign) {
                System.out.println(sign);


                // 使用签名获取数据
                ebookManager.loadRawData(sign, new RawLoadCallback() {

                    @Override
                    public void onDataLoaded(String raw) {
                        try {
                            JSONObject jsonObject = JSON.parseObject(raw);
                            JSONArray jsonArray = jsonObject.getJSONArray("list");

                            List<EbookItem> tempList = new ArrayList<EbookItem>();
                            // 打印或处理list
                            for (Object item : jsonArray) {
                                Map map = (Map) item;
                                tempList.add(new EbookItem(
                                        map.get("server_path").toString(),
                                        map.get("name").toString(),
                                        (Integer) map.get("id")
                                ));
                            }
                            listItems.clear();
                            listItems.addAll(tempList);
                            adapter.notifyDataSetChanged();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                });

                // 使用改签获取目录数据
                ebookManager.loadRawCatalogueData(sign, new RawLoadCallback() {
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
                //页面参数（非必填 不传就是起始页）
                intent.putExtra("page_start", "4");
                startActivity(intent);

            }
        });



    }


}