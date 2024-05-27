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

### Version 0.1.0-alpha (development)
> #### Window
> + Have all functions which needed to work with them. A list with the current working
>   functions coming soon.

> #### AbstractWindowFrame
> + Have the ability to provide windows. You can create new windows with them. Only need to extend
>   the AbstractWindowFrame.
> + Every window works in his own Thread, this make is easier to work with them.
> + You have the ability to say what will be doe at initialization, update or destroy state.
>   A full list with examples will come soon.
