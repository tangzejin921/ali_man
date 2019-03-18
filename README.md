# 移动数据分析
> https://help.aliyun.com/document_detail/30037.html

# Maven依赖
    allprojects {
        repositories {
            maven {
                url 'http://maven.aliyun.com/nexus/content/repositories/releases/'
            }
        }
    }
    dependencies {
        compile 'com.aliyun.ams:alicloud-android-man:1.2.0'
    }
    
    
# 混淆
    -keep class com.alibaba.sdk.android.**{*;}
    -keep class com.ut.**{*;}
    -keep class com.ta.**{*;}