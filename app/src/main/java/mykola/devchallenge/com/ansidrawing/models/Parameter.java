package mykola.devchallenge.com.ansidrawing.models;

/**
 * Created by mykola on 04.05.17.
 */

public class Parameter<T> {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Parameter(T data) {
        this.data = data;
    }



}
