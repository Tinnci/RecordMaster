<div align="center">
   <img src="prev/main_app_icon.png" alt="" width="150px">
</div>
<h1 align="center">
 RecordMaster
</h1>
   <div  align="center">
      <img src="https://img.shields.io/github/license/Tinnci/RecordMaster?style=for-the-badge&color=cba6f7&labelColor=302D41">
      <img src="https://img.shields.io/github/last-commit/Tinnci/RecordMaster?style=for-the-badge&color=b1d18a&labelColor=1f3701">
      <br>
      <img src="https://img.shields.io/github/release/Tinnci/RecordMaster?style=for-the-badge&color=dbc66e&labelColor=3a3000">
      <br>
      <br>

</a>
   </div>

 <div align="center">

[Contact](https://github.com/Tinnci/RecordMaster?tab=readme-ov-file#contact) • [License](https://github.com/Tinnci/RecordMaster?tab=readme-ov-file#license)

 </div>
       <div align="center">
   <h3>RecordMaster: inspired by the Google Pixel Recorder app</h3>
    <p>Fork maintained by Tinnci in 2026.</p>

   </div>
<div align="center">
 <a href="https://github.com/Tinnci/RecordMaster/releases"><img alt="GitHub" src="https://user-images.githubusercontent.com/69304392/148696068-0cfea65d-b18f-4685-82b5-329a330b1c0d.png" height="80"/></a>
   </div>
<br>

# 👁️ Screenshots

<div align="center">
<img src="prev/prev_1.png"  width="250">
<img src="prev/prev_2.png"  width="250">
<img src="prev/prev_3.png"  width="250">

</div>

<br>

# ✉️ Contact

For any questions or feedback, feel free to open an issue on GitHub or contact pranshul.devmain@gmail.com

# Build

Local CI-equivalent verification:

```bash
./gradlew ktlintCheck test assembleDebug
```

# Release Signing

Release signing is optional and is only enabled when all four values are provided:

```text
RELEASE_STORE_FILE
RELEASE_STORE_PASSWORD
RELEASE_KEY_ALIAS
RELEASE_KEY_PASSWORD
```

The app module reads these values from either Gradle properties or environment variables, so the same setup works both locally and in GitHub Actions.

Local macOS setup idea:

```bash
export RELEASE_STORE_FILE="$HOME/Library/Mobile Documents/com~apple~CloudDocs/Android/RecordMaster/release.jks"
export RELEASE_STORE_PASSWORD="..."
export RELEASE_KEY_ALIAS="..."
export RELEASE_KEY_PASSWORD="..."
./gradlew assembleRelease
```

Recommended GitHub Secrets layout:

```text
RELEASE_KEYSTORE_BASE64
RELEASE_STORE_PASSWORD
RELEASE_KEY_ALIAS
RELEASE_KEY_PASSWORD
```

In GitHub Actions, decode `RELEASE_KEYSTORE_BASE64` into a temporary keystore file, then expose its path as `RELEASE_STORE_FILE` for the Gradle build.

The repository also includes a tag-triggered release workflow at `.github/workflows/release.yml`.
Existing tags include `v1.0.0` and `v1.0.0-beta.1`, so the next stable tag should start from `v1.0.1` or later.
Pushing a tag such as `v1.0.1` will build a signed release APK, upload it as a workflow artifact, and attach it to the matching GitHub Release on `Tinnci/RecordMaster`.
For tag-triggered release builds, the workflow also derives:

```text
RELEASE_VERSION_NAME=1.0.1
RELEASE_VERSION_CODE=1000199
```

That means the published APK version now follows the tag automatically.

Supported tag formats:

```text
vMAJOR.MINOR.PATCH
vMAJOR.MINOR.PATCH-alpha.N
vMAJOR.MINOR.PATCH-beta.N
vMAJOR.MINOR.PATCH-rc.N
```

Version code strategy:

```text
stable  -> base + 99
alpha.N -> base + N
beta.N  -> base + 30 + N
rc.N    -> base + 60 + N
base    -> major * 1000000 + minor * 10000 + patch * 100
```

Examples:

```text
v1.0.1-alpha.1 -> versionName 1.0.1-alpha.1, versionCode 1000101
v1.0.1-beta.1  -> versionName 1.0.1-beta.1,  versionCode 1000131
v1.0.1-rc.1    -> versionName 1.0.1-rc.1,    versionCode 1000161
v1.0.1         -> versionName 1.0.1,         versionCode 1000199
```

Beta and RC tags are published as GitHub prereleases automatically.

First release tag checklist:

```bash
# 1. push the current branch to origin/master
git push origin master

# 2. create the first fork release tag
git tag -a v1.0.1 -m "RecordMaster fork release v1.0.1"
git push origin v1.0.1
```

After the tag is pushed, GitHub Actions should run `Android Release`, publish the signed APK artifact, and attach it to the `v1.0.1` GitHub Release.

# ©️ License

This project is licensed under the GPL-3.0 license. See the `LICENSE` file for details.
