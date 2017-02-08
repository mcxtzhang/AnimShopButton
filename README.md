# AnimShopButton
[![](https://jitpack.io/v/mcxtzhang/AnimShopButton.svg)](https://jitpack.io/#mcxtzhang/AnimShopButton)

A shopping cart button with a telescopic displacement rotation animation ...

一个仿饿了么 带伸缩位移旋转动画的购物车按钮

注意，本控件非继承自`ViewGroup`,而是**纯自定义View**，实现的仿饿了么加入购物车控件，自带**闪转腾挪动画**的按钮。


图1 项目中使用的效果，考虑到了`View`的**回收复用**，

并且可以看到在`RecyclerView`中使用，切换`LayoutManager`也是没有问题的，

![项目中使用的效果](https://github.com/mcxtzhang/AnimShopButton/blob/master/gif/new.gif)

图2 Demo效果，测试各种属性值

![图2 Demo效果，测试各种属性值](https://github.com/mcxtzhang/AnimShopButton/blob/master/gif/testAttr.gif)

图3 最新静态图

![图3 最新静态图，测试各种属性值](https://github.com/mcxtzhang/AnimShopButton/blob/master/gif/attrs.png)


# Article
相关博文：

http://blog.csdn.net/zxt0601/article/details/54235736

想经济上支持我 or 想通过视频看我是怎么实现的:

http://edu.csdn.net/course/detail/3898

# Import
**Step 1. Add the JitPack repository to your build file**

**Step 1. 在项目根build.gradle文件中增加JitPack仓库依赖。** 
```
    allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
```
Step 2. Add the dependency
```
    dependencies {
	        compile 'com.github.mcxtzhang:AnimShopButton:V1.2.0'
	}
```

# Usage
 xml:


```
	<!--使用默认UI属性-->
    <com.mcxtzhang.lib.AnimShopButton
        android:id="@+id/btn1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:maxCount="3"/>
    <!--设置了两圆间距-->
    <com.mcxtzhang.lib.AnimShopButton
        android:id="@+id/btn2"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:count="3"
        app:gapBetweenCircle="90dp"
        app:maxCount="99"/>
    <!--仿饿了么-->
    <com.mcxtzhang.lib.AnimShopButton
        android:id="@+id/btnEle"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:addEnableBgColor="#3190E8"
        app:addEnableFgColor="#ffffff"
        app:hintBgColor="#3190E8"
        app:hintBgRoundValue="15dp"
        app:hintFgColor="#ffffff"
        app:maxCount="99"/>
```

注意：
加减点击后，具体的操作，要根据业务的不同来编写了，设计到实际的购物车可能还有写数据库操作，或者请求接口等，要操作成功后才执行动画、或者修改count，这一块代码每个人写法可能不同。

使用时，可以重写`onDelClick()`和` onAddClick()`方法，并在合适的时机回调`onCountAddSuccess()`和` onCountDelSuccess()`以执行动画。

效果图如图2.

# Attributes
|name|format|description|中文解释
|:---:|:---:|:---:|:---:|
| isAddFillMode| boolean| Plus button is opened Fill mode default is stroke (false)|加按钮是否开启fill模式 默认是stroke(false)
| addEnableBgColor| color|The background color of the plus button|加按钮的背景色
| addEnableFgColor| color|The foreground color of the plus button|加按钮的前景色
| addDisableBgColor| color|The background color when the button is not available|加按钮不可用时的背景色
| addDisableFgColor| color |The foreground color when the button is not available|加按钮不可用时的前景色
| isDelFillMode| boolean| Plus button is opened Fill mode default is stroke (false)|减按钮是否开启fill模式 默认是stroke(false)
| delEnableBgColor| color|The background color of the minus button|减按钮的背景色
| delEnableFgColor| color|The foreground color of the minus button|减按钮的前景色
| delDisableBgColor| color|The background color when the button is not available|减按钮不可用时的背景色
| delDisableFgColor| color |The foreground color when the button is not available|减按钮不可用时的前景色
| radius| dimension|The radius of the circle|圆的半径
| circleStrokeWidth| dimension|The width of the circle|圆圈的宽度
| lineWidth| dimension|The width of the line (+ - sign)|线(+ - 符号)的宽度
| gapBetweenCircle| dimension| The spacing between two circles|两个圆之间的间距
| numTextSize| dimension| The textSize of draws the number|绘制数量的textSize
| maxCount| integer| max count|最大数量
| count| integer| current count|当前数量
| hintText| string| The hint text when number is 0|数量为0时，hint文字
| hintBgColor| color| The hint background when number is 0|数量为0时，hint背景色
| hintFgColor| color| The hint foreground when number is 0|数量为0时，hint前景色
| hingTextSize| dimension| The hint text size when number is 0|数量为0时，hint文字大小
| hintBgRoundValue| dimension| The background fillet value when number is 0|数量为0时，hint背景圆角值
| ignoreHintArea| boolean| The UI/animation whether ignores the hint area|UI显示、动画是否忽略hint收缩区域
| perAnimDuration| integer| The duration of each animation, in ms|每一段动画的执行时间，单位ms
| hintText| string| The hint text when number is 0|数量为0时，hint文字
| replenishTextColor| color| TextColor in replenish status|补货中状态的文字颜色
| replenishTextSize| dimension| TextSize in replenish status|补货中状态的文字大小
| replenishText| string | Text hint in replenish status|补货中状态的文字
这么多属性够你用了吧。


## Where to find me:

[Github](https://github.com/mcxtzhang)

[CSDN](http://blog.csdn.net/zxt0601)

[稀土掘金](http://gold.xitu.io/user/56de210b816dfa0052e66495)

[简书](http://www.jianshu.com/users/8e91ff99b072/timeline)

QQ群 ：**557266366**
***

## History
Version : 1.1.0,Time: 2017/01/12
 * 1 Feature : Add a boolean variable `ignoreHintArea` ：The UI/animation whether ignores the hint area
 * 2 Feature : Add a int variable `perAnimDuration` : The duration of each animation, in ms

Version : 1.2.0 Time: 2017/02/08
 * 1 Feature : Add a status: replenishment.Click is not allowed at this time.
 * Judgment by setReplenish (boolean) and isReplenish ()



