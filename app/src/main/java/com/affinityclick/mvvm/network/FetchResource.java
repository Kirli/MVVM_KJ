package com.affinityclick.mvvm.network;

import androidx.annotation.Nullable;

/**
 * A wrapper around a web request that contains information on the state of the request.
 * @param <T> The data we are returning.
 */
public class FetchResource<T> {
  public enum State {
    UNINITIALIZED,
    LOADING,
    SUCCESS,
    ERROR,
    CACHED
  }

  private String errorResponse;
  private T data;
  private State state;

  public FetchResource() {
    this.state = State.UNINITIALIZED;
    this.errorResponse = null;
    this.data = null;
  }

  public FetchResource(State state, T data, String errorResponse) {
    this.errorResponse = errorResponse;
    this.data = data;
    this.state = state;
  }

  protected void setToSuccess(T data) {
    this.state = State.SUCCESS;
    this.errorResponse = null;
    this.data = data;
  }

  protected void setToLoading() {
    this.state = State.LOADING;
    errorResponse = null;
    data = null;
  }

  protected void setToError(String errorResponse) {
    this.state = State.ERROR;
    this.errorResponse = errorResponse;
    this.data = null;
  }

  @Nullable
  public String getErrorResponse() {
    return errorResponse;
  }

  @Nullable public T getData() {
    return data;
  }

  public State getState() {
    return state;
  }

  public static <T> FetchResource<T> success(T data) {
    return new FetchResource<>(State.SUCCESS, data, null);
  }

  public static <T> FetchResource<T> loading() {
    return new FetchResource<>(State.LOADING, null, null);
  }

  public static <T> FetchResource<T> loading(T data) {
    return new FetchResource<>(State.LOADING, data, null);
  }

  public static<T> FetchResource<T> error(String errorResponse) {
    return new FetchResource<>(State.ERROR, null, errorResponse);
  }

  public static<T> FetchResource<T> error(String errorResponse, T data) {
    return new FetchResource<>(State.ERROR, data, errorResponse);
  }
}
