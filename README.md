# JavaHub
Website development based on Vue+SpringBoot+MySQL
## Configuration
* node.js
从node.js官网下载并安装
安装成功后在命令行查看版本：“node -v”用于查看是否安装成功，以及node版本（建议14以上）
* Vue
打开cmd，输入“npm install vue -g”全局安装Vue
具体可查看Vue官方文档
* JDK1.9
下载安装JDK1.9，配置环境变量
在命令行输入“java -version”“java”“javac”等命令，若有对应内容出现则说明安装配置成功
* MySQL
下载安装MySQL8.0

## Pages
### 登录注册
#### 登录/login
用户访问网站，若本地内存中没有登录记录，会自动跳转到登录界面

![image](https://github.com/YyyyyyiZ/JavaHub/assets/109188165/7ddb2b4a-6d81-4ba7-98ed-154028ecbf07)

#### 注册/register
点击登录界面左下角的“Sign up?”，跳转到注册界面。用户以真实学号注册，注册成功后学号无法修改，同时填写验证问题和验证答案，便于后续忘记密码时找回密码。

![image](https://github.com/YyyyyyiZ/JavaHub/assets/109188165/dcd6dc38-f705-49c0-9be7-a9f993fd499e)

#### 找回密码/pswd
点击登录界面左下角的“Forget Password?”，跳转到找回密码界面。用户输入学号后，点击验证按钮。

![image](https://github.com/YyyyyyiZ/JavaHub/assets/109188165/0377dad1-d22b-4664-a20d-15be7dba3a27)
![image](https://github.com/YyyyyyiZ/JavaHub/assets/109188165/044777c6-5096-4ea8-a787-de15fc0becfd)
![image](https://github.com/YyyyyyiZ/JavaHub/assets/109188165/31542926-2088-4af1-a8b3-36282bda916a)

### 学习模块
#### 学习路径/learn
为各个为章chapter节section设置二级侧边栏。对用户的学习路径进行限制，用户可以学习本章（尚未完成所有学习任务的对应章节）以及下一章的所有内容
![image](https://github.com/YyyyyyiZ/JavaHub/assets/109188165/73d4755f-88d4-4f7a-a7d6-1ed00470fbac)

#### 知识点呈现/learn
点击右上方导航栏“Learn”，进入学习模块。对每一小节section包含的若干个知识点进行呈现，并对每个知识点进行详细解释，用户可以展开或者隐藏每个知识点的详细解释

![image](https://github.com/YyyyyyiZ/JavaHub/assets/109188165/7f61bc6d-58a0-407b-99f9-6915801ba4e1)

#### 用户笔记/learn
知识点卡片下方为文本编辑器，用户可以自由输入文字，并设置文字的字号、字型、字体、前景色、背景色等等，点击“保存笔记”，可以对笔记进行保存。

![image](https://github.com/YyyyyyiZ/JavaHub/assets/109188165/0af509ac-cf57-4e50-b061-0100744da6e8)

在“我的笔记”下方，为“我的文件”，点击“上传图片”，选择要上传的图片，点击“打开”后，图片自动上传，“我的图片”进行相应的更新。

![image](https://github.com/YyyyyyiZ/JavaHub/assets/109188165/5499a2b8-fbf7-47f9-977b-6e0315c6c4b8)
![image](https://github.com/YyyyyyiZ/JavaHub/assets/109188165/a13c49fc-a590-4e83-bf49-cade376a9a7b)

### 练习模块
#### 练习进度展示/exercise
点击右上方导航栏“Exercise”，进入习题模块。点击左侧侧边栏，可以对习题章节进行选择。所有习题被分类为单选题、多选题、判断题、填空题，右侧主界面显示该章节下，每种题型的做题进度。点击“开始练习”，进入做题界面。
![image](https://github.com/YyyyyyiZ/JavaHub/assets/109188165/1e4c9e3d-608b-4967-995a-2707f3f39a85)

#### 习题呈现与时间记录
计时器对每道题的做题时间进行记录，点击“上一题”或“下一题”，可进行题目的切换，如果当前题目为第一题或最后一题，则对应的按钮失效。

![image](https://github.com/YyyyyyiZ/JavaHub/assets/109188165/4ee27d74-87e6-47a6-8f03-7f820eba4bc2)

#### 练习结果评估
点击提交按钮后，对答案进行评估，显示“回答正确”或“回答错误”，并提供参考答案和该题对应的知识点。
如果该题回答错误，则自动计入该用户的错题集，同时，用户也可以点击“收藏”按钮，对题目进行收藏，界面提示“收藏成功”；如果该题已经在用户的收藏夹中，则会提示“此题已收藏”。

![image](https://github.com/YyyyyyiZ/JavaHub/assets/109188165/e6b667f7-363f-4412-a4ad-cf38185dfd75)
![image](https://github.com/YyyyyyiZ/JavaHub/assets/109188165/1694f046-509b-4b0b-8726-a4ca592d62ca)
![image](https://github.com/YyyyyyiZ/JavaHub/assets/109188165/f5af9a29-21cd-4dce-a6a4-3768f63e1995)

#### 习题评论区
用户提交答案后，还可以在习题评论区对该题进行评论，或查看他人对该题的评论。

![image](https://github.com/YyyyyyiZ/JavaHub/assets/109188165/a3b6a610-2621-47b7-9620-b3f26364def6)

### 测试模块
#### 试卷区/test
点击右上方导航栏“Test”，进入试卷模块。试卷按章节进行划分，共十章，用户可以自由选择章节进行任意次数的测试，每次测试的题目根据用户当前的学习情况个性化生成。

![image](https://github.com/YyyyyyiZ/JavaHub/assets/109188165/1fd3445d-a796-4bcd-980d-dc31abab14f9)

#### 查看历史试卷/testDetail
点击左侧紫色圆形按钮，可以查看该用户在该章节下所做过的所有测试，包括测试日期、用时、得分。

![image](https://github.com/YyyyyyiZ/JavaHub/assets/109188165/166def28-8998-46b3-88f6-4884ff8b90d3)


#### 试题呈现与测试时间记录/testDo
点击右侧绿色圆形按钮，可以开始新的测试。试卷分为单选题、多选题、填空题、选择题四个部分，每个部分的习题可以展开或收起。左下角的计时器对测试时间进行记录，点击右下角“提交”按钮，可提交试卷

![image](https://github.com/YyyyyyiZ/JavaHub/assets/109188165/2717fb62-94a2-443b-ae5a-9d930d4e5ff6)
![image](https://github.com/YyyyyyiZ/JavaHub/assets/109188165/70c6b136-fb70-40d3-8abf-f60747a49a65)

#### 测试结果评估/testDeail
提交试卷后，提示“提交成功”，跳转到历史试卷界面，下图中，日期为2022-12-03的试卷为刚才提交的试卷，得分为8。点击绿色按钮，对试卷的细节进行查看。

![image](https://github.com/YyyyyyiZ/JavaHub/assets/109188165/b5c0ec31-53bb-413c-b23e-6b1ce88e83f0)

点击右上角按钮，可以将试卷全屏放大。试卷详情展示了该张试卷的题目，以及每道题目对应的题型、考察的知识点、对错，可以对题目进行展开，查看题目详情，对于错误题目，会自动计入该用户的错题集。

![image](https://github.com/YyyyyyiZ/JavaHub/assets/109188165/d7b2d1ad-cb06-41fc-ada9-85c78d13e308)
![image](https://github.com/YyyyyyiZ/JavaHub/assets/109188165/588db76d-7b0f-47c9-b79b-e67442d63768)

灰色五角星表明该题没有被用户收藏，点击后可进行收藏，五角星填充为黄色，再次点击可以取消收藏，五角星再次变为灰色。

![image](https://github.com/YyyyyyiZ/JavaHub/assets/109188165/2303cfed-760b-4af0-a833-e469110653e0)
![image](https://github.com/YyyyyyiZ/JavaHub/assets/109188165/b72c676a-ab37-4f54-9591-424801973f67)

### 用户模块
#### 个人信息维护/user
点击右上方导航栏“User-个人中心”，进入个人中心。点击“修改个人信息”，可以对除学号以外的个人信息进行修改。修改完成后，点击“确认修改”，可以提交修改结果。

![image](https://github.com/YyyyyyiZ/JavaHub/assets/109188165/3686be5e-5814-42d5-a006-4a3a97b400b7)

#### 日历/user
日历对用户的学习情况进行了记录。“exercise”记录用户当天做的习题数量，“section”记录用户当天学过的章节，“test”记录用户当天的测试数量。

![image](https://github.com/YyyyyyiZ/JavaHub/assets/109188165/5c629fd9-15e7-4080-a9f9-f720d55b90f0)

#### 错题集/wrongSet
点击右上方导航栏“User-错题集”，进入错题集。可以在左上方搜索中输入关键字进行查找。

![image](https://github.com/YyyyyyiZ/JavaHub/assets/109188165/c950a968-1097-46d3-a48b-fe6a67b227d3)

也可在备注栏对进行备注。

![image](https://github.com/YyyyyyiZ/JavaHub/assets/109188165/e45e4ce9-ef05-4bad-98f4-6c66681f7a47)

也可以将每道题展开，查看参考答案。

![image](https://github.com/YyyyyyiZ/JavaHub/assets/109188165/d921947d-e069-4830-a431-ad280ccdc052)

点击黄色按钮，弹出确认对话框，二次确认后将错题从错题集中删除。

![image](https://github.com/YyyyyyiZ/JavaHub/assets/109188165/773383ff-f528-4585-a08c-2f5d166c9db6)
![image](https://github.com/YyyyyyiZ/JavaHub/assets/109188165/ed0c1f5c-5025-4010-9bae-7ebc5f96e7c7)

#### 收藏夹
点击右上方导航栏“User-收藏夹”，进入收藏夹。收藏夹与错题集功能相似，此处不再赘述，错题集为自动生成，记录用户在所有练习和测试中的错题，收藏夹为用户在练习和测试中主动收藏。




## 测试模块的试题生成算法——DKT
### 算法原理
试题生成在DTKService类中继续，核心算法为深度知识追踪DTK（Deep Knowledge Tracing）。

假设某一章节一共包含6个知识点（列），20道题目（行）

* 选择该章节中的所有题目，根据习题-知识点的多对多映射关系，生成exerid-knowid的变换矩阵。比如，exerid=1的题目，涉及到了knowid=4的知识点；exerid=2的题目，涉及到了knowid=4的知识点。

![image](https://github.com/YyyyyyiZ/JavaHub/assets/109188165/be4d18e6-fef3-48fd-a03c-8086b7495bdc)

* 根据时间顺序在exerrecord中选择该章节该用户所有题目的做题记录。
* 对于用户做过的所有题目，根据习题-知识点的变换矩阵，对该题对应的知识点是否掌握进行赋值。比如，题目序列的第一题，exerid=3，用户做对了，计为1，则该题涉及到的knowid=4的知识点都记为1，表示已经掌握；题目序列的第二题，exerid=2的题目，用户做错了，记为0，则该题涉及到的knowid=4的知识点记为0，表示没有掌握。
* 以此类推，生成知识点矩阵的时间序列。如下图，纵坐标表示所有的知识点，横坐标表示对于某个知识点，在某一时刻是否掌握（1表示掌握，0表示未掌握），从左到右形成一个时间序列。在某一时刻，画一条平行于纵轴的竖线，穿过5个方格，对应的就是这个时间节点对5个知识点的掌握情况。

![image](https://github.com/YyyyyyiZ/JavaHub/assets/109188165/90bf00c1-eb99-41f5-992f-42c8eada60bd)

* 通过长短期记忆模型LSTM对下一期知识点的掌握情况进行预测。
* 通过知识点-习题的映射矩阵，通过线性变换将对知识点的掌握程度转化为对该知识点对应的习题的掌握程度。显然，如果预测表明用户对某习题的掌握不佳，则该习题在新试卷中被选中的概率就会更大。
* 在知识点-矩阵的变换过程中，可能会出现用户某道题一直错，于是DKT算法判断对该题的掌握情况不佳，再次选中该题。一方面，需要减少试题中出现用户做过的题的概率；另一方面，对于用户一直出错的题目，让用户重做也无可厚非。因此，对于用户已经做过的题，设置一个权重系数，减小其被重复选择的概率，但是又不排除再次被选中的可能。


### Requirements
```
absl-py==1.3.0
aiohttp==3.8.3
aiosignal==1.2.0
aircv==1.4.6
astunparse==1.6.3
async-generator==1.10
async-timeout==4.0.2
attrs==22.1.0
beautifulsoup4==4.11.1
bs4==0.0.1
cachetools==5.2.0
certifi==2022.9.24
cffi==1.15.1
charset-normalizer==2.1.1
contourpy==1.0.6
cycler==0.11.0
Deprecated==1.2.13
dnspython==2.2.1
docopt==0.6.2
et-xmlfile==1.1.0
eventlet==0.33.1
exceptiongroup==1.0.0rc9
flatbuffers==22.10.26
fonttools==4.38.0
frozenlist==1.3.1
gast==0.4.0
google-auth==2.14.1
google-auth-oauthlib==0.4.6
google-pasta==0.2.0
greenlet==1.1.3.post0
grpcio==1.50.0
h11==0.14.0
h5py==3.7.0
ichrome==3.0.6
idna==3.4
importlib-metadata==5.0.0
jieba==0.42.1
joblib==1.2.0
keras==2.11.0
kiwisolver==1.4.4
libclang==14.0.6
lxml==4.9.1
Markdown==3.4.1
MarkupSafe==2.1.1
matplotlib==3.6.2
mpf==0.55.0
mplfinance==0.12.9b5
multidict==6.0.2
numpy==1.23.3
oauthlib==3.2.2
opencv-python==4.6.0.66
openpyxl==3.0.10
opt-einsum==3.3.0
outcome==1.2.0
packaging==21.3
pandas==1.5.0
Pillow==9.3.0
pinyin==0.4.0
pipreqs==0.4.11
protobuf==3.19.6
psutil==5.7.3
pyasn1==0.4.8
pyasn1-modules==0.2.8
pycparser==2.21
pynput==1.7.6
pyparsing==3.0.9
pypinyin==0.47.1
pyserial==3.5
pyserial-asyncio==0.4
PySocks==1.7.1
python-dateutil==2.8.2
python-opencv==1.0.0.14
pytz==2022.4
pywin32==304
redis==4.3.4
requests==2.28.1
requests-oauthlib==1.3.1
rsa==4.9
ruamel.yaml==0.15.100
scikit-learn==1.1.3
scipy==1.9.3
selenium==4.5.0
six==1.16.0
sklearn==0.0.post1
sniffio==1.3.0
some-package==0.1
sortedcontainers==2.3.0
soupsieve==2.3.2.post1
tensorboard==2.11.0
tensorboard-data-server==0.6.1
tensorboard-plugin-wit==1.8.1
tensorflow==2.11.0
tensorflow-estimator==2.11.0
tensorflow-intel==2.11.0
tensorflow-io-gcs-filesystem==0.27.0
termcolor==2.1.0
threadpoolctl==3.1.0
torch==1.13.0
torchvision==0.14.0
torequests==6.0.0
trio==0.22.0
trio-websocket==0.9.2
typing-extensions==4.4.0
urllib3==1.26.12
websocket-client==1.4.1
Werkzeug==2.2.2
wrapt==1.14.1
wsproto==1.2.0
xlwt==1.3.0
yarg==0.1.9
yarl==1.8.1
zipp==3.10.0
```
