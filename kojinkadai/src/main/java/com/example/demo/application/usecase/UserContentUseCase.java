package com.example.demo.application.usecase;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.example.demo.application.form.ContentForm;
import com.example.demo.domain.model.UserContent;
import com.example.demo.domain.model.UserContentRepository;
import com.example.demo.domain.model.UserContents;
import com.example.demo.domain.model.UserId;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserContentUseCase {
  private final UserContentRepository repository;

  /**
   * ユーザの書き込みをDBに反映し、表示するデータをプレゼンテーション層に渡す
   * @param contentForm ユーザの入力データ
   * @return 表示するデータ
   */
  public void write(ContentForm contentForm, User user) {
    // フォームオブジェクトからドメインオブジェクトへ変換
    UserContent usercontent = UserContent.from(
        user.getUsername(),
        contentForm.getContent()
    );

    // 例えばここで、直近の投稿の一覧を取得し、今回と同じ内容の投稿がないかチェックする

    repository.save(usercontent);
  }
  
  /**
   * 投稿の取得
   * @return 投稿のリスト
   */
  public UserContents read(){
    return repository.select();
  }
  public UserContents read(UserId userId) {
	    return repository.select(userId);
    }

}

