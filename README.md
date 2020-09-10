#  Utils 常用工具类

常用工具类，通过项目实践总结积累而成,包含android-utils、java-utils工具类

[![](https://jitpack.io/v/itxiaox/utils.svg)](https://jitpack.io/#itxiaox/utils)

[最新API文档](https://javadoc.jitpack.io/com/github/itxiaox/utils/latest/javadoc/)

[最新java-utils文档](https://javadoc.jitpack.io/com/github/itxiaox/utils/java-utils/latest/javadoc/)

[最新android-utils文档](https://javadoc.jitpack.io/com/github/itxiaox/utils/android-utils/latest/javadoc/)
## 使用
 1. 引用

	在根gradle中添加
	```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
	```
2.在module 中添加依赖
- 只引用java-utils，适用于仅java项目中

```
implementation 'com.github.itxiaox.utils:java-utils:Tag'
```
- 只引用android-utils,适用于android项目
```
implementation 'com.github.itxiaox.utils:android-utils:Tag'
```
- 同时需要android-utils和java-utils

```
implementation 'com.github.itxiaox.utils:java-utils:Tag'
implementation 'com.github.itxiaox.utils:android-utils:Tag'
```
或
```
implementation 'com.github.itxiaox:utils:Tag'
```
 
# LICENSE

	Copyright 2018 Xiaox

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

	   http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.

 
 
