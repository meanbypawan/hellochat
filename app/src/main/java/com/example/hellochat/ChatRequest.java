package com.example.hellochat;

public class ChatRequest {
  private String requestType;
  public ChatRequest(String requestType){
      this.requestType = requestType;
  }
  public ChatRequest(){}
    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }
}
