# AnyDialog 使用说明

简化dialog创建，内置5个默认样式，力求还原Android系统动画效果



[GitHub主页](https://github.com/goweii/AnyDialog)

[Demo下载](https://github.com/goweii/AnyDialog/raw/master/app/release/app-release.apk)



# 简介

- 链式调用
- 可自定义数据绑定
- 可自定义进出场动画



# 使用说明

## 集成

- ### 添加jitpack库

```java
// build.gradle(Project:)
allprojects {
    repositories {
        ...
            maven { url 'https://www.jitpack.io' }
    }
}
```

- ### 添加依赖

  [点击查看最新版本号](https://github.com/goweii/AnyDialog/releases)

  从3.0.3版本开始，版本号前不加v，引用时需要注意。

  从4.0.0版本开始，重构到androidx+kotlin，精简功能，移除背景图和高斯模糊效果。

```java
// build.gradle(Module:)
dependencies {
    implementation 'com.github.goweii:AnyDialog:4.0.0'
}
```

## 新建布局

在布局文件根节点设置layout_width，layout_height，layout_margin等属性控制dialog的显示大小

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.cardview.widget.CardView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:cardBackgroundColor="#fff"
    app:cardCornerRadius="12dp"
    app:cardElevation="0dp">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:orientation="vertical">

        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:gravity="center"
            android:padding="10dp"
            android:text="@string/app_name"
            android:textColor="#232323"
            android:textSize="17sp" />

        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:gravity="center"
            android:padding="15dp"
            android:text="@string/dialog_msg"
            android:textColor="#232323"
            android:textSize="15sp" />

        <View
            android:layout_width="match_parent"
            android:layout_height="1dp"
            android:background="#f5f5f5" />

        <TextView
            android:id="@+id/tv_close"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:gravity="center"
            android:padding="10dp"
            android:text="关闭"
            android:textColor="@color/colorPrimary"
            android:textSize="15sp" />

    </LinearLayout>

</androidx.cardview.widget.CardView>
```

## 在代码中调用

```java
 AnyDialog(this).apply {
     style(AnyDialog.Style.CENTER)
     contentView(R.layout.dialog_def)
     clickDismiss(R.id.tv_close)
 }.show()
```
