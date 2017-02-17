package com.test;

import java.io.IOException;

import org.testng.annotations.Test;

import com.taoke.TaoKe;

public class TestTaoKe {
  @Test
  public void f() throws InterruptedException, IOException {
	  new TaoKe().test();
  }
}