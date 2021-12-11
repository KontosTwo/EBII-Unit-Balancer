package com.ebii.shoebopp.parser;

public interface ParserContext {
  float getContext(String stat);

  void setContext(String stat, float value);
}
