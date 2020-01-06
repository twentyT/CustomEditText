＃-EditText带计数的edittext：依赖地址implementation:'com.github.twentyT:CustomEditText:1.0.1'
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        api 'com.github.twentyT:CustomEditText：1.0.1' 	}
                
步骤：控件使用，下面文本会将右下角的计数文本统一用index称呼。   


<com.example.numberedittext.EditTextCount

        android:layout_width="wrap_content"

        android:layout_width="wrap_content"
        
        android:layout_height="wrap_content"
        
        app:defaultHeight="200"//不设置EditTextCount高度的默认高度设置,默认为300
        
        app:indexColor="#15bfea"//index字体的颜色,默认为灰色
        
        app:indexMarginBottom="30"//index距离文本输入框底部的距离，默认为25
        
        app:indexMarginRight="30"//index距离文本输入框右边的距离，默认为25
        
        app:indexMaxNumber="100"//index最大值的设置如设置为100则显示(0/100)，默认为0/600
        
        app:indexSize="20"//index字体的大小,默认为20
        
        app:textType="monospace" />//index字体的样式可以选择默认,加粗，斜体 等。
	

