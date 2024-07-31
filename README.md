# 京版云 电子课本Android sdk  集成说明（小猿）

## 简介
京版云电子课本Android sdk 是京版云提供的android sdk，可以方便的在android项目中集成电子课本功能

## 集成步骤

#### 1. 引入sdk aar
     把 libs/jbyEbook-release.aar 添加到你的项目中(位置 app目录下)

#### 2. 添加依赖
    1. setting->project structure->app->dependencies->+->file dependency->选择libs/jbyEbook-release.aar

    2. app/build.gradle -> dependencies 添加以下依赖
    implementation 'com.alibaba:fastjson:1.2.75'

#### 3 初始化 EbookManager 设置token，监听事件
    ##### 3.1 设置token 
```
    EbookManager ebookManager = EbookManager.getInstance();
    ebookManager.configToken("3aadbfcb4d45f55338fed3df7e02b79c1030ab18774de427777c8a13a330ae6a");
```
  
    ##### 3.2 设置监听（sdk 点击的跟读的事件）
```
ebookManager.setEvaluationListener(new EvaluationListener() {
    @Override
    public void onEvaluation(String json) {
        System.out.println("onEvaluation");
        System.out.println(json);
        
        /*
        json 是 json 数据
        {"bookId":4510,"unit":41,"content":[{"en_text":"Hi"}......]}
        
        */
    }
});

```

#### 4 获取列表数据(先获取签名)，根据签名获取列表数据

    1. 通过签名获取数据 
    2. 使用签名获取目录

```
        //步骤一 获取签名 （app 应该缓存起来起来 ）
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
                
                // 使用改签获取目录数据
                ebookManager.loadRawCatalogueData(sign, new RawLoadCallback() {
                    @Override
                    public void onDataLoaded(String raw) {
                        System.out.println(raw);
                    }
                });
            }
        });

```


#### 5 打开电子课本页面（可指定页码参数）
```
Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
intent.putExtra("id", selectedItem.getId().toString());
//页面参数（非必填 不传就是起始页）
intent.putExtra("page_start", "4");
startActivity(intent);
                
//这里的 是课程id, 示例中的id 是从资源列表接口中获取的
                
```

#### 6. sdk内点击跟读 事件给外部 集成者(看3.2)


    



