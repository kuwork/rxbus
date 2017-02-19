# rxbus
## 基于Rxjava2+RxRelay 的RxBus

这个小项目基于[RxRelay](https://github.com/JakeWharton/RxRelay)项目，站在大神JakeWharton的肩膀上，一小步就实现了类似EventBus的Rxbus。


Relay that, once an Observer has subscribed, emits all subsequently observed items to the subscriber.
```java
PublishRelay<Object> relay = PublishRelay.create();
// observer1 will receive all events
relay.subscribe(observer1);
relay.accept("one");
relay.accept("two");
// observer2 will only receive "three"
relay.subscribe(observer2);
relay.accept("three");
```
![](intro.png)

## 发送事件

```java
RxBus.getInstance().send(new UserEvent(1,"名字"));
```

## 接受事件

```java
RxBus.getInstance().toObservable(UserEvent.class).subscribe(new Consumer<UserEvent>() {
            @Override
            public void accept(UserEvent userEvent) {
                btnNext.setText(userEvent.getName());
                Toast.makeText(getBaseContext(),userEvent.toString(),Toast.LENGTH_SHORT).show();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Toast.makeText(getBaseContext(),throwable.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
```

Download
--------

Maven:
```xml
<dependency>
  <groupId>com.kw</groupId>
  <artifactId>rxbus</artifactId>
  <version>1.0.0</version>
  <type>pom</type>
</dependency>
```
Gradle:
```groovy
compile 'com.kw:rxbus:1.0.0'
```



License
-------
	Copyright (C) 2014 Alejandro Rodriguez Salamanca.

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

	   http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.