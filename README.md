# stanwood Loading Indicator (Android)

[![Release](https://jitpack.io/v/stanwood/Loading_Indicator_android.svg?style=flat-square)]
(https://jitpack.io/#stanwood/Loading_Indicator_android)

This library contains a simple to use data-binding compatible loading/error indicator with retry functionality.

## Import

The stanwood Loading Indicator is hosted on JitPack. Therefore you can simply import it by adding

```groovy
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```

to your project's `build.gradle`.

Then add this to you app's `build.gradle`:

```groovy
dependencies {
    compile 'com.github.stanwood:Loading_Indicator_android:1.0'
}
```

## Usage

First include the provided layout in your app's layout - preferably below the toolbar and pass an instance of `LoadingIndicatorViewModel` via data binding:

```xml
<layout>
    <data>
        <variable
            name="viewModel"
            type="io.sample.main.ViewModel"/>
    </data>

    <ConstraintLayout
        xmlns:app="http://schemas.android.com/apk/res-auto"
        ...
        >

        ...

        <include
            layout="@layout/layout_loading_indicator"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_gravity="top"
            app:vm="@{viewModel.loadingIndicatorViewModel}"/>

    </ConstraintLayout>
</layout>
```

Of course you can also directly use the provided `LoadingIndicatorView` and hook it up to the View model as in `layout_loading_indicator.xml`. This gives you full control about how the indicator looks like. Just make sure to wrap everything as children inside the `LoadingIndicatorView` so that the animations are applied properly. The `LoadingLayoutIndicatorView` is a `LinearLayout`.

## Roadmap

- implement View as custom View with attributes instead of forcing usage of an include tag
- write tests