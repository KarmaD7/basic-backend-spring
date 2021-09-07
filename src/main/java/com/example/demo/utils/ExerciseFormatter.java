package com.example.demo.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ExerciseFormatter {
  public static ObjectNode getFormattedChoiceExercise(String content) {
    Pattern pattern = Pattern.compile("^(.*)A.(.*)B.(.*)C.(.*)D.(.*)$");
    Matcher matcher = pattern.matcher(content);
    if (matcher.find() == true && matcher.groupCount() == 5) {
      ObjectNode json = new ObjectMapper().createObjectNode();
      json.put("question", matcher.group(1));
      json.put("A", matcher.group(2));
      json.put("B", matcher.group(3));
      json.put("C", matcher.group(4));
      json.put("D", matcher.group(5));
      return json;
    } else return null;
  }
}
