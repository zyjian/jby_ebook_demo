# 京版云 电子课本Android sdk  集成说明

## 简介
京版云电子课本Android sdk 是京版云提供的android sdk，可以方便的在android项目中集成电子课本功能

## 集成步骤

#### 1. 引入sdk aar 
     把 libs/jbyEbook-release.aar 添加到你的项目中(位置 app目录下)

#### 2. 添加依赖
    setting->project structure->app->dependencies->+->file dependency->选择libs/jbyEbook-release.aar

#### 3 资源列表接口，集成方可以根据自己的需求自定义该列显示页面UI
    https://new.jingbanyun.com/api/Booktext/textbook_list?token=3aadbfcb4d45f55338fed3df7e02b79c1030ab18774de427777c8a13a330ae6a


#### 4 打开电子课本页面
```
Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                intent.putExtra("id", "4508");
                startActivity(intent);
                
//这里的 是课程id, 示例中的id 是从资源列表接口中获取的
                
```


    



