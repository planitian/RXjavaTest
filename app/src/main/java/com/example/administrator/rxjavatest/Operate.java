package com.example.administrator.rxjavatest;

public  class Operate {
    private String name;
    public Operate(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void change(CallBack callBack){
        callBack.change("ss");
    };

    interface CallBack{
        void change(String info);

    }
}
