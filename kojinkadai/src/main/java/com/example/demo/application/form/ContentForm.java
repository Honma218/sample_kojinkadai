package com.example.demo.application.form;
import jakarta.validation.constraints.Email;

import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

import lombok.Data;

@Data
public class ContentForm {
	  @Nullable
	  @Length(max=20)
	  private String name;
	  @Nullable
	  @Email
	  @Length(max=100)
	  private String mailAddress;
	  @Nullable
	  @Length(min=1, max=400)
	  private String content;
	  
	  // 新しい投稿システム用
	  @Nullable
	  @Length(min=1, max=400, message = "投稿内容は1文字以上400文字以内で入力してください")
	  private String text;
	  
	  // text が入力されている場合はそれを返し、そうでなければ content を返す
	  public String getText() {
	      return text != null && !text.trim().isEmpty() ? text : content;
	  }
}
