package com.gaoxi.springcloud.entity;

//Result：返回结果对象，以json格式返回
public class Result<T> {
    private String code; //状态码
    private String msg; //对状态说明
    private T data; // 返回时，携带的数据, 为了扩展性好，老师使用泛型

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    //无参构造器
    public Result() {

    }

    //带参构造器-指定返回的data
    public Result(T data) {
        this.data = data;
    }

    //编写方法-返回需要的Result对象-表示成功的Result
    public static Result success() {
        Result result = new Result<>();
        result.setCode("200");
        result.setMsg("success");
        return result;
    }

    //编写方法-返回需要的Result对象-表示成功的Result,同时可以携带数据
    //如果需要在static方法使用泛型，需要在 static <T>
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>(data);
        result.setCode("200");
        result.setMsg("success");
        return result;
    }

    //编写方法-返回需要的Result对象-表示成功的Result,同时可以携带数据和指定msg
    //如果需要在static方法使用泛型，需要在 static <T>
    public static <T> Result<T> success(String msg, T data) {
        Result<T> result = new Result<>(data);
        result.setCode("200");
        result.setMsg(msg);
        return result;
    }

    //编写方法-返回需要的Result对象-表示失败的Result
    public static Result error(String code, String msg) {
        Result result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    //编写方法-返回需要的Result对象-表示失败的Result,同时可以携带数据

    public static <T> Result<T> error(String code, String msg, T data) {
        Result<T> result = new Result<>(data);
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}
