# QwePlayer

![Kotlin](https://img.shields.io/badge/Kotlin-1.9+-blue.svg?logo=kotlin)
![Jetpack Compose](https://img.shields.io/badge/Jetpack_Compose-Ready-4CAF50.svg?logo=android)
![Media3](https://img.shields.io/badge/Media3-ExoPlayer-blue)
![License](https://img.shields.io/badge/License-Apache%202.0-red.svg)

**QwePlayer** 是一款基于 Jetpack Compose 构建的 Android 本地音乐播放器。

项目秉承极简主义与隐私优先（Privacy-first）的开发理念，核心集成了设备端 AI 语音引擎（Sherpa-onnx），在完全离线（Offline-only）的环境下实现语音交互与音频控制。

[📥 Download Latest APK](#) <!-- TODO: Replace with your Releases link -->

---

## Features

### 🎙️ On-Device Voice Engine
基于 `sherpa-onnx` (Paraformer & Transducer) 实现的端侧语音唤醒与识别。
- **Privacy-first**: 零网络请求，所有音频数据均在设备本地处理。
- **Natural Language Control**: 支持按歌手、歌曲名精准检索，支持播放控制（上一首/下一首/暂停/播放）与音量调节。
- **Smart Resolution**: 具备同名歌曲冲突处理机制，通过倒计时自动匹配最佳结果。

### 🚀 High-Performance UI
100% 采用 Jetpack Compose 构建，针对性优化渲染管道。
- **Optimized Transitions**: 通过重构 Coil 解码策略与降级 `Crossfade` 动画，消除长列表与高频切歌场景下的 GPU 渲染掉帧。
- **Zero-latency Slider**: 自定义手势进度条，重写触控响应逻辑，解决原生组件的交互迟滞。
- **Large Dataset Support**: 毫秒级加载本地万首音频，内置原生字母索引导航。

### 🎨 Adaptive Aesthetics
- **Dynamic Theming**: 基于当前专辑封面实时提取色彩（Palette），生成流体渐变背景。
- **Customizable Lyrics**: 独立歌词渲染引擎，支持动态字号、行距调节以及 10+ 种内置中文字体切换。
- **Responsive Layout**: 针对设备方向自适应，提供竖屏写真模式与横屏（沉浸/列表分屏）模式。

### 🎛️ Advanced Audio & Media
- **Audio Effects**: 内置 10 段均衡器（附带风格预设）、低音增强（Bass Boost）、虚拟环绕与混响控制。
- **Loudness Normalization**: 引入 `DynamicsProcessing` 实现全局统一响度，平衡多源音频的音量差异。
- **M3U Integration**: 完整兼容标准 `.m3u` 播放列表格式，支持可视化创建、拖拽排序与指定目录排除。

---

## Screenshots

|<img src="https://via.placeholder.com/250x500.png?text=Portrait+Player" width="250">|<img src="https://via.placeholder.com/250x500.png?text=Lyrics+Engine" width="250">|<img src="https://via.placeholder.com/250x500.png?text=Settings+Panel" width="250">|
|:---:|:---:|:---:|
| Portrait Playback | Dynamic Lyrics | Preferences |

---

## Tech Stack

- **UI**: [Jetpack Compose](https://developer.android.com/jetpack/compose)
- **Media Playback**: [AndroidX Media3 (ExoPlayer)](https://developer.android.com/media/media3)
- **Offline ASR/KWS**: [Sherpa-onnx](https://github.com/k2-fsa/sherpa-onnx)
- **Audio Metadata**: [JAudiotagger](http://www.jthink.net/jaudiotagger/)
- **Image Loading**: [Coil](https://coil-kt.github.io/coil/)
- **Architecture**: MVVM / Coroutines / Flow

---

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/qwejay/QwePlayer.git
```

### 2. Configure Offline Voice Models (Critical)

为控制仓库体积，预训练的 ONNX 语音模型未包含在版本控制中。**缺少模型将导致应用编译后运行崩溃或语音功能失效。** 
请按以下步骤部署模型文件：

#### A. ASR Model (Paraformer)
1. 下载: [sherpa-onnx-paraformer-zh-2023-09-14.tar.bz2](https://github.com/k2-fsa/sherpa-onnx/releases/download/asr-models/sherpa-onnx-paraformer-zh-2023-09-14.tar.bz2)
2. 解压并将指定文件放入 `app/src/main/assets/asr/` 目录，必须重命名如下：
   - `model.int8.onnx` ➡️ `asr_model.onnx`
   - `tokens.txt` ➡️ `asr_tokens.txt`

#### B. KWS Model (Transducer/Zipformer)
1. 下载: [sherpa-onnx-kws-zipformer-gigaspeech-3.3-2024-04-10.tar.bz2](https://github.com/k2-fsa/sherpa-onnx/releases/download/kws-models/sherpa-onnx-kws-zipformer-gigaspeech-3.3-2024-04-10.tar.bz2)
2. 解压并将指定文件放入 `app/src/main/assets/kws/` 目录，必须重命名如下：
   - `encoder-epoch-12-avg-2-chunk-16-left-64.onnx` ➡️ `kws_encoder.onnx`
   - `decoder-epoch-12-avg-2-chunk-16-left-64.onnx` ➡️ `kws_decoder.onnx`
   - `joiner-epoch-12-avg-2-chunk-16-left-64.onnx` ➡️ `kws_joiner.onnx`
   - `tokens.txt` ➡️ `kws_tokens.txt`
3. 在 `app/src/main/assets/kws/` 目录下创建 `keywords.txt` 文件，配置你的自定义唤醒词（每行一个，拼音间需加空格）：
   ```text
   QwePlayer
   播 放 音 乐
   ```

### 3. UDP Control Extension

应用内置了监听 `8888` 端口的本地 UDP Server，专为 IoT 设备或车载方向盘控制器提供低延迟的无界交互验证：

- 触发语音录入: `voice_start`
- 结束录入并执行 ASR: `voice_stop`
- 播放/暂停: `pause`
- 切换下一首: `next_song`

---

## Voice Commands

应用支持泛化匹配，以下为典型指令映射参考：

| Category | Query Examples |
| :--- | :--- |
| **Playback** | “播放七里香”、“我要听陈奕迅的歌” |
| **Queue** | “加入播放列表”、“把 [Track Name] 增加进来” |
| **Control** | “下一首”、“切歌”、“上一首”、“暂停”、“继续” |
| **Volume** | “大声”、“调大”、“小声”、“调小” |
| **Navigation** | “切换到曲库”、“切换到收藏”、“语音队列” |

---

## Contributing

我们欢迎任何形式的贡献（Bug 修复、性能优化或新特性如 App Widgets / Android Auto 适配）。
请通过提交 Issue 讨论新特性，或直接提交 Pull Request。

## License

QwePlayer 采用 [Apache License 2.0](LICENSE) 协议开源。
