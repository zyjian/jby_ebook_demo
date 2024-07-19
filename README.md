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

#### 3 初始化 EbookManager 设置token
    ```
    EbookManager ebookManager = EbookManager.getInstance();
    ebookManager.configToken("3aadbfcb4d45f55338fed3df7e02b79c1030ab18774de427777c8a13a330ae6a");
  

    ```
#### 4 获取列表数据 有两种方式2选一即可
    1. 直接获取列表数据模型

```
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

    2. 通过签名 获取列表数据模型

```
        //步骤一 获取签名 （app 应该缓存起来起来 ）
        ebookManager.loadSignData(new SignLoadCallback() {
            @Override
            public void onDataLoaded(String sign) {
                System.out.println(sign);


                // 使用签名 获取数据 返回的 json 字符串
                ebookManager.loadRawData(sign, new RawLoadCallback() {
                    @Override
                    public void onDataLoaded(String raw) {
                        System.out.println(raw);
                    }
                });
            }
        });

```



#### 5 打开电子课本页面
```
Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                intent.putExtra("id", "4508");
                startActivity(intent);
                
//这里的 是课程id, 示例中的id 是从资源列表接口中获取的
                
```


    



