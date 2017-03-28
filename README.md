# BigNews: Android增量升级/增量更新框架
## 支持增量包/差分包/升级包

***原理***：在服务器端使用bsdiff工具将新老安装包的差异打包为一个体积较小的差分包/升级包，然后在App端通过bspatch工具（和bsdiff配套的）用差分包/升级包升级本地的安装包并安装，达到增量更新的目的。

###### 本框架集成了bsdiff/bspatch工具，可以在Android端完成差分与合并操作。

****方案****：把升级包/差分包从服务器上下载下来，使用BigNews.make()结合本地安装包生成最新安装包进行安装更新。

****框架使用方法****：直接导入项目即可。

合并: 从差分包/升级包和老安装包合并升级到新安装包，新安装包放在newApkPath。
```java
/**
 * oldApkPath: 老安装包路径
 * newApkPath: 新安装包路径（输出）
 * patchPath: 升级/差分包路径
 * return: 成功返回true，否则为false。
 */
boolean BigNews.make(oldApkPath, newApkPath, patchPath);
 ```

差分: 将新安装包和老安装包的差异打包为差分包/升级包，输出到patchPath。
```java
/**
 * oldApkPath: 老安装包路径
 * newApkPath: 新安装包路径
 * patchPath: 升级/差分包路径（输出）
 * return: 成功返回true，否则为false。
 */
boolean BigNews.diff(oldApkPath, newApkPath, patchPath);
```

项目中一般使用合并功能。

### Make a BigNews!!