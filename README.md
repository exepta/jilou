# JILOU UI Framework

### Examples
#### How to create a new window?

```java

public static void main(String[] args) {
    UIApplication.load(args); // Initialize the framework.

    Window windowDirect = new Window(); // Create a new window.
    ApplicationFactory.registerWindow(windowDirect); // Register the created window.

    /* You can use our ApplicationFactory for better usage. */
    Window window = ApplicationFactory.createWindow(null, "Test Title", Window.class);
}
```

#### What is the future goal?
The goal is that the UI Framework can handle more than one system. Currently, it will
work with Swing style, but the next step is HTML style.

As Example:
````html
<html lang="en"> <!-- can be used for language support -->
    <head>
        <title>Window Title</title> <!-- Using for set the title. -->
    </head>

    <body datatype="pane"> <!-- will use the JILOU Pane as root -->
        <div class="style-me"> <!-- is DivWidget and all widget can be css styled -->
            Hello JILOU Application!
        </div>
    </body>
</html>

````

This is the goal and many more features.

### Version 0.2.0-alpha (development)
> #### Window
> + Have now an update function with ups param.

> #### Stylesheet
> + Is now used at many classes for handling styles.
> + Can be used for handle css as design pattern for widgets.

> #### Scene
> + NEW implemented for handling multiple ui's in one window.
>   Can only be used at AbstractWindow.
> + Have a layout system for handle alignment, padding and margin.

> #### Other
> + Color utils which supported color gradients.
> + Bounds and Insets calculation.
> + Event management like minecraft. (Port from EventAPI written by Niklas Tat)
> + NanoVG helper engine at the logic package.
