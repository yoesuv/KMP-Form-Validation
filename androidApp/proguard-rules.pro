# Add project specific ProGuard rules here.

# kotlinx-serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt

# Keep generated serializers
-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}
-keepclasseswithmembers class kotlinx.serialization.json.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# Keep @Serializable classes and their companion serializer()
-keep,includedescriptorclasses class com.yoesuv.kmpformvalidation.**$$serializer { *; }
-keepclassmembers class com.yoesuv.kmpformvalidation.** {
    *** Companion;
}
-keepclasseswithmembers class com.yoesuv.kmpformvalidation.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# Keep names of @Serializable classes for polymorphic serialization
-keepnames class com.yoesuv.kmpformvalidation.** { *; }
