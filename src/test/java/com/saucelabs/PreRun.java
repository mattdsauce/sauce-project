package com.saucelabs;

import com.google.gson.Gson;

public class PreRun {

    private String executable;
    private String[] args;
    private Boolean background;
    private Integer timeout;

    public String getExecutable() {
        return executable;
    }

    public void setExecutable(String executable) {
        this.executable = executable;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public Boolean getBackground() {
        return background;
    }

    public void setBackground(Boolean background) {
        this.background = background;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public String toJson(){ return new Gson().toJson(this); }

    public static String getPreRunConfiguration(String executable, String[] args, boolean background, int timeout) {

        PreRun prerun = new PreRun();

        prerun.setExecutable(executable);
        prerun.setArgs(args);
        prerun.setBackground(background);
        prerun.setTimeout(timeout);

        return prerun.toJson();
    }


}



