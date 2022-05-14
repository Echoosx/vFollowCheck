# vFollowCheck

> 基于 [Mirai Console](https://github.com/mamoe/mirai-console) 的 [查成分](https://tools.asoulfan.com) 插件

[![Release](https://img.shields.io/github/v/release/Echoosx/vFollowCheck)](https://github.com/Echoosx/vFollowCheck/releases)
[![Build](https://github.com/Echoosx/vFollowCheck/workflows/Java%20CI%20with%20Gradle/badge.svg?branch=master)](https://github.com/Echoosx/vFollowCheck/actions/workflows/gradle.yml)
## 功能
本插件的数据来源基于 [枝江成分姬](https://tools.asoulfan.com) 和 [vtbs.moe](https://github.com/dd-center/vtbs.moe) <br/>可以查询B站用户关注的虚拟主播

![image](https://user-images.githubusercontent.com/22994765/153237477-cf5bded8-0ec6-4933-a2f7-cdfce78f69de.png)

## 指令
注意: 使用前请确保可以 [在聊天环境执行指令](https://github.com/project-mirai/chat-command)  
带括号的`/`前缀是缺省的  
`<...>`中的是指令名，由`空格`隔开表示其中任一名称都可执行  
`[...]`表示参数，当`[...]`后面带`?`时表示参数可选  
`{...}`表示连续的多个参数


| 指令                        | 描述             |
|:--------------------------|:---------------|
| `(/)<ccf 查成分> [B站昵称或uid]` | 查询B站用户关注的虚拟主播  |
| `(/)<fsp 粉丝牌> [B站昵称或uid]` | 查询B站用户的虚拟主播粉丝牌 |

## 配置
### Setting.yml
```yaml
# Cookie
cookie: ''
# Vtb列表更新间隔（Cron表达式）
updateCron: '0 0 0 * * ?'
```
- `cookie`：登录B站的cookie值，用`双引号`包裹
- `updateCron`：Vtuber总列表的更新间隔，请使用正确的Cron表达式格式，不知道Cron表达式的请自行百度。 默认值为每天0点更新

### Supply.yml
```yaml
# 补充UID
supply:
  - 123456
  - 456789
```
`Supply`：除了默认的Vtuber总列表之外，你还可以自行在`Supply`中添加一些，不在总表中的up主的uid
## 安装
- 从 [Releases](https://github.com/Echoosx/vFollowCheck/releases) 下载`jar`包，将其放入工作目录下`plugins`文件夹
- 如果没有`plugins`文件夹，先运行 [Mirai Console](https://github.com/mamoe/mirai-console) ，会自动生成
