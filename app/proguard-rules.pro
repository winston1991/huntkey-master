#代码混淆压缩比，在0~7之间，默认为5，一般不做修改
-optimizationpasses 5

#把混淆类中的方法名也混淆了
-useuniqueclassmembernames

#优化时允许访问并修改有修饰符的类和类的成员
-allowaccessmodification


-keepattributes InnerClasses,Signature,EnclosingMethod

