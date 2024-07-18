# 京版云 电子课本Android sdk  集成说明

## 简介
京版云电子课本Android sdk 是京版云提供的android sdk，可以方便的在android项目中集成电子课本功能

## 集成步骤

#### 1. 引入sdk aar
     把 libs/jbyEbook-release.aar 添加到你的项目中(位置 app目录下)

#### 2. 添加依赖
    1. setting->project structure->app->dependencies->+->file dependency->选择libs/jbyEbook-release.aar

    2. app/build.gradle -> dependencies 添加以下依赖
    implementation 'com.alibaba:fastjson:1.2.75'

#### 3 初始化 EbookManager 设置token, 并获取数据
    ```
    EbookManager ebookManager = EbookManager.getInstance();
    ebookManager.configToken("3aadbfcb4d45f55338fed3df7e02b79c1030ab18774de427777c8a13a330ae6a");
    ebookManager.loadData(new EbookLoadCallback() {
            @Override
            public void onDataLoaded(List<EbookItem> items) {
                // 这里可以处理加载完成的数据，例如更新UI
                listItems.clear();
                listItems.addAll(items);
                adapter.notifyDataSetChanged();

            }
        });

    ```



#### 4 打开电子课本页面
```
Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                intent.putExtra("id", "4508");
                startActivity(intent);
                
//这里的id 是从上面获取的list中 EbookItem 的id字段获取得到
                
```


    



