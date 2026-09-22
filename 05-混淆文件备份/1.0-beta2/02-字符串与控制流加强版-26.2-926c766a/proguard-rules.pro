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
