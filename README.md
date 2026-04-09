# Codex Code Bridge for Android Studio

[GitHub Repository](https://github.com/hosh1kuzu/codex-code-bridge-for-android-studio) · [Latest Release](https://github.com/hosh1kuzu/codex-code-bridge-for-android-studio/releases/latest) · [MIT License](https://github.com/hosh1kuzu/codex-code-bridge-for-android-studio/blob/main/LICENSE)

`Codex Code Bridge for Android Studio` 是一个 Android Studio 插件，用来把当前选中代码的位置快速复制成适合粘贴到 Codex 对话框里的 Markdown 文件链接。

选中编辑器中的一段代码后，原生选区悬浮工具栏里会出现 Codex 图标。点击后，插件会把类似下面这种格式复制到剪贴板：

```md
[Example.kt:L120C20-L168C80](/absolute/path/to/Example.kt#L120C19)
```

这样就可以直接粘贴到 Codex，对某一段代码做分析、审查或讨论。

## 功能特性

- 集成到 Android Studio 原生选区悬浮工具栏
- 同时提供编辑器右键菜单入口 `Copy Codex Link`
- 自动读取本地绝对路径和选区起止行号
- 单行与多行选区都会生成稳定的 Markdown 链接格式
- 一键复制，无需手动整理文件路径和行号

## 适用版本

- Android Studio Ladybug `2024.2.1 Patch 2`
- Android Studio Meerkat Feature Drop `2024.3.2 Patch 1`
- IntelliJ Platform `242+`

## 安装方式

### 从 zip 安装

1. 打开 Android Studio
2. 进入 `Settings` -> `Plugins`
3. 点击右上角齿轮按钮
4. 选择 `Install Plugin from Disk...`
5. 选择仓库中的安装包：

`build/distributions/codex-code-bridge-for-android-studio-0.4.1.zip`

也可以直接从 GitHub Release 页面下载：

`https://github.com/hosh1kuzu/codex-code-bridge-for-android-studio/releases/latest`

6. 安装完成后重启 Android Studio

## 使用方法

1. 在 Android Studio 中打开本地源码文件
2. 选中一段代码
3. 点击选区旁原生悬浮工具栏中的 Codex 图标
4. 链接会自动复制到剪贴板
5. 把内容粘贴到 Codex 对话框中即可

也可以在编辑器右键菜单中使用 `Copy Codex Link`。

## 输出格式

插件当前复制的格式如下：

- 单点选区：`[Foo.kt:L120C20](/abs/path/Foo.kt#L120C19)`
- 范围选区：`[Foo.kt:L100C20-L200C80](/abs/path/Foo.kt#L100C19)`

说明：

- 链接文本里会带上文件名和选区范围
- 链接目标使用本地绝对路径
- 锚点固定使用起始行号

## 本地开发

### 运行测试

```bash
env JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home" \
GRADLE_USER_HOME=/tmp/codex-gradle-home \
./gradlew test
```

### 打包插件

```bash
env JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home" \
GRADLE_USER_HOME=/tmp/codex-gradle-home \
./gradlew buildPlugin
```

打包后的产物位于：

`build/distributions/codex-code-bridge-for-android-studio-0.4.1.zip`

## 当前版本

- 插件名称：`Codex Code Bridge for Android Studio`
- 当前版本：`0.4.1`
- 插件 ID：`io.github.hosh1kuzu.codexlink`
- License：`MIT`

## 注意事项

- 仅对本地文件系统中的真实源码文件生效
- 没有选区时，悬浮工具栏按钮不会显示
- 当前版本不附带选中文本内容，只复制代码位置链接
- 当前版本显示的列号为 1-based，但链接目标中的起始列按 0-based 编码，以适配 Codex app 的跳转行为
- 当前版本不支持多选区合并、或自动发送到 Codex
- 已显式取消 `untilBuild` 限制，避免构建工具自动写回 `242.*` 并阻塞后续 Android Studio 版本安装
