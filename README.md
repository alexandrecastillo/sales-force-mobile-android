# Sales Force Mobile Application 
[![ForTheBadge built-with-science](http://ForTheBadge.com/images/badges/built-with-science.svg)](https://GitHub.com/Naereen/) <img src="https://img.shields.io/badge/kotlin-%230095D5.svg?&style=for-the-badge&logo=kotlin&logoColor=white"/>
<img src="https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white"/>
### APK - Local Building
First, download Bundle JAR (https://github.com/google/bundletool/releases)
```sh
./gradlew bundleReview
./bundletool --build-apk multiprofileReview
```
### APK - Local Install
```sh
./gradlew clean
./gradlew bundleStage

./bundletool --install-aab businesspartnersStage
./bundletool --install-aab multiprofileStage

./bundletool --build-apk businesspartnersStage
./bundletool --build-apk multiprofileStage
./bundletool --install-apk businesspartnersStage
./bundletool --install-apk multiprofileStage
```
### ADB - Scripts
Filter Logcat:
```sh

(bash)adb shell ps | grep belcorp
(cmd)adb shell ps | findstr 'belcorp'
adb logcat --pid=`adb shell pidof -s biz.belcorp.salesforce.multiprofile`

```
### Developer Tools
| Tool | URL |
| ------ | ------ |
| Mirroring | https://github.com/Genymobile/scrcpy|
