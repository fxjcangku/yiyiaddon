# Fabric / Mixin 通过外部名称和注解加载，保留这些契约。
# 不裁剪、不优化：Mixin 的外部调用和运行期反射不能仅靠静态可达性推断。
-dontshrink
-dontoptimize
-overloadaggressively
-useuniqueclassmembernames
-keeppackagenames com.yiyiaddon.mixin.**
-keepattributes *Annotation*,Signature,InnerClasses,EnclosingMethod,Exceptions,NestHost,NestMembers,Record,PermittedSubclasses

-keep class com.yiyiaddon.YiyiAddon { public <init>(); public void onInitialize(); }
-keep class com.yiyiaddon.YiyiAddonClient { public <init>(); public void onInitializeClient(); }
-keep class com.yiyiaddon.mixin.** { *; }

# 跨进程入口：本地世界生成 Worker 是「第二个 JVM」，由客户端按类名 +
# public static void main(String[]) 启动（见 SeedWorldgenWorkerLauncher 与
# 02-开发报告 232-P）。它不属于 Fabric 入口点，verifyObfuscatedJar 的入口检查覆盖不到，
# 一旦被改名，正式产物里这一项功能会直接死掉（且只在真实用户机器上才发现）。
# 所以这里把整个 worker 引导包保持原名：它是本工程唯一以字符串跨进程寻址的类型集合。
-keep class com.yiyiaddon.seedworker.SeedWorkerMain { public static void main(java.lang.String[]); }
-keepnames class com.yiyiaddon.seedworker.** { *; }

# 加固运行期：发布顺序是「先混淆、后加固」，加固工具在构建期按类名写出 invokedynamic 引导句柄，
# 之后没有任何重映射步骤，所以这个类与 bootstrap 方法必须保持原名与签名，
# 否则加密常量/加密分派键在运行期找不到引导方法（表现是产物能装、一进功能就 NoSuchMethodError）。
-keep class com.yiyiaddon.utils.ReleaseProtection {
    public static java.lang.Object bootstrap(java.lang.invoke.MethodHandles$Lookup, java.lang.String, java.lang.Class, java.lang.String, long, long, long, long);
}

# 枚举名称参与配置读写；保留枚举常量与 JVM 反射入口。
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
    public static final <fields>;
}

# 以下 DTO 由 Gson 按字段名反射读取资源 JSON，类名仍可混淆。
# 外层业务方法无需保留名称；规则只覆盖真实数据载体。
-keepclassmembers class com.yiyiaddon.feature.enchant.gear.GearEnchantData$* {
    <fields>;
    <init>();
}
-keepclassmembers class com.yiyiaddon.feature.enchant.vanilla.VanillaEnchantDatabase$* {
    <fields>;
    <init>();
}

# 原生方法名称属于 JNI 契约，未来加入时同样不能改名。
-keepclasseswithmembernames,includedescriptorclasses class * {
    native <methods>;
}
