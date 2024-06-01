# 前言

最近团队里写了一套规范的编译 jni c++ 流程，感觉不少细节，自己理解后整理了一番，在此记录一下。

完整工程见：[simple_jni_cpp](https://github.com/Xiaoccer/simple_jni_cpp)

## 步骤

首先是安装好 java、maven 和编译 c++ 的工具链，这里不赘述，我工程用的 java8。

写好含 jni 接口的 java 类后，通过 `javac -h java 类` 命令生成 c/c++ 头文件。

根据这个头文件用 c/c++ 实现 jni 接口，具体与 java 类之间传参、访问其成员变量等写法可以参考这个文档： [https://www3.ntu.edu.sg/home/ehchua/programming/java/JavaNativeInterface.html](https://www3.ntu.edu.sg/home/ehchua/programming/java/JavaNativeInterface.html)

最后将 c++ 实现编译成动态链接库，通过 maven 把 so 文件和 java 类打包成 jar 包。

## 细节

步骤看似简单，但也有不少细节。

### 头文件

生成的 c/c++ 头文件是含有 `jni.h` 这个头文件的，可以在 java 的安装路径里把这个文件及其依赖都单独提出来，当做第三库来用，这样能方便项目的移植。甚至还可以把不同 java 版本的 `jin.h` 文件都找出来，方便适应不同的版本。

### 链接参数

在编译动态链接库时，可加入一个链接参数 `--version-script` 来指定一个链接脚本，这个脚本指定了哪些符号是全局的，哪些是局部的，可减少动态库间的符号冲突。

### 从 jar 包加载 so

从 jar 包中加载 so，不能直接调用 `System.loadLibrary()` 方法，具体做法可以参考这个资料 [https://stackoverflow.com/questions/2937406/how-to-bundle-a-native-library-and-a-jni-library-inside-a-jar](https://stackoverflow.com/questions/2937406/how-to-bundle-a-native-library-and-a-jni-library-inside-a-jar)

## 总结

虽然平时不写 java 和 jni，但还是从这个流程中看到了不少细节，拓展了下知识~
