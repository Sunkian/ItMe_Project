package com.example.itme;

import android.app.Application;

public class GlobalVariable extends Application {
  private String myState;

  public String getId() {
    return myState;
  }//End method

  public void setId(String s) {
    myState = s;
  }//End method
}//End Class