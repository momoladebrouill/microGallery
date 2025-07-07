# Template iOS

Add your iOS application in this folder.

In order to use KMP in your iOS application, you need to add the following build phase:

```
cd "$SRCROOT/.."
./gradlew :shared:embedAndSignAppleFrameworkForXcode
```

You will also need to initialize Koin:

```
KoinHelperKt.init()
```
