<div align="center">

<h1>ClawGUI-app</h1>

[![Android](https://img.shields.io/badge/Android-8.0%2B-3DDC84?logo=android&logoColor=white)](https://www.android.com/)
[![License](https://img.shields.io/badge/license-Apache%202.0-green)](LICENSE)
[![Shizuku](https://img.shields.io/badge/Shizuku-required-orange)](https://github.com/RikkaApps/Shizuku)

[English](README.md) | [中文](README_zh.md)

</div>

**ClawGUI-app** 是 [ClawGUI](../README_zh.md) 的真机部署模块,对应主仓路线图中的「真机部署 ClawGUI-Agent」。它把 ClawGUI 的「brain + GUI agent」能力整体搬到一台 Android 手机上独立运行,不再依赖"电脑端调度 + 手机端执行"的双端架构。基于 [Shizuku](https://github.com/RikkaApps/Shizuku) 提供的非 root 高权限通道构建,由两层 agent 驱动:**brain**(function-calling LLM)负责理解用户意图、调度工具、管理会话与记忆;**phone agent**(VLM)负责截屏、规划动作、执行点击/滑动/输入。手机端形态方面,执行过程以悬浮球呈现实时状态,自带输入法保证中文输入稳定性,任务轨迹落盘为 trace 便于回放;远程接入方面,可通过飞书等外部 channel 接收消息,在群聊里 @ 机器人即可下发任务。

## ✨ 项目特点

- **纯手机端工作流** —— 一台已 root 或装 Shizuku 的 Android 设备即可独立跑通,无电脑端依赖
- **双层 Agent 设计** —— brain 负责策略与工具调度,phone agent 负责屏幕理解与动作执行,职责清晰
- **对话和自动化一体化** —— 不是脚本执行器,而是带会话、长期记忆、外部 channel、trace 的完整 App
- **面向真实使用场景** —— 悬浮球、输入法、会话持久化、外部消息、诊断日志都已经接在产品链路里

## 🚀 快速开始

ClawGUI 涉及 Shizuku、悬浮窗、输入法等较多权限,首次上手需要按顺序完成几步配置。完整流程见 [SETUP_zh.md](SETUP_zh.md)。

最短路径:

1. 安装 [Shizuku](https://github.com/RikkaApps/Shizuku) 和 ClawGUI APK
2. 启动 Shizuku(三种方式按设备情况选,详见 [SETUP_zh.md](SETUP_zh.md))
3. 打开 ClawGUI,按引导完成 Shizuku 授权、悬浮窗权限、输入法启用
4. 进入设置页配置 brain / VLM provider 和 API Key
5. 用「打开微信」「返回桌面」这类简单指令做一次验证

开发者环境要求、APK 编译流程、各 Android 版本的 Shizuku 启动方式,以及常见问题排查都在 [SETUP_zh.md](SETUP_zh.md)。

## 🏗️ 架构概览

ClawGUI-app 采用两层 Agent:

- **nanobot / brain**(`core/nano/`):function-calling LLM,理解用户意图、决定是否动手,通过 `gui_execute` / `read_memory` / `write_memory` 等工具调度,管理会话、记忆、trace、外部 channel。
- **phone agent / VLM executor**(`core/phone/`):被 brain 通过 `gui_execute` 指派后接管,截图 → 调 VLM → 解析动作 → Shizuku 执行 `tap / swipe / type / launch / scroll`。

## 📦 当前能力

### 应用内对话

- 主聊天界面接收指令
- 抽屉式会话管理
- 执行过程悬浮球反馈
- 支持手动停止

### GUI 自动化

- 启动应用
- 点击、长按、双击、滑动、返回、回到桌面
- 通过自带输入法路径输入文本
- 在当前屏幕做多步规划

### 会话与记忆

- 多会话本地持久化
- 长期记忆工具:`read_memory` / `write_memory`
- 注入近期 history 和 trace,减少重复操作

### 外部通道

- 飞书机器人接入
- 外部消息进入应用 inbox
- 应用内可只读查看外部会话

### 可观测性

- 每轮 trace 落盘到 `workspace/traces/<date>/`
- 可选诊断日志导出
- 前台服务 + 悬浮球反馈执行状态

## 📖 文档

- [SETUP_zh.md](SETUP_zh.md):安装、Shizuku 启动、首次配置、常见问题

## 🙏 致谢

ClawGUI-app 在以下开源项目的基础上构建,感谢各项目的贡献者:

- [**Shizuku**](https://github.com/RikkaApps/Shizuku) —— 非 root 条件下的高权限设备控制能力
- [**roubao**](https://github.com/Turbo1123/roubao) —— Android / Shizuku 集成实现的部分借鉴(MIT 协议)

## 📄 许可证

本项目基于 [Apache License 2.0](LICENSE) 开源。
