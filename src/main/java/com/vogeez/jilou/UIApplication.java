package com.vogeez.jilou;

public final class UIApplication {

    /**
     * Function for handle applications with need an ui. You need to call this in your main
     * function. This function is{@link Thread}safe because the ui handling is working in
     * a separate{@link Thread}. More about ui handling below this.
     * @param args Program arguments form your main or given array.
     * @apiNote visit our website for mor information's ( <a href="https://www.vogeez-dev.com/docs/jilou/getting-started">Getting Started</a> )
     */
    public static void load(String[] args) {
        Configuration.setFromArguments(args);
    }

}
