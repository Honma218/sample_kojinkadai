-- Spring Security用ユーザー（既存）
INSERT INTO USERS (USERNAME, PASSWORD, ENABLED) VALUES
('admin', '{bcrypt}$2a$10$vC.r53zKYPwEXplBYH3mxuZP52r2u3udRcEg9yTUmwYE5yjmoUXyG', true),
('user1', '{bcrypt}$2a$10$vC.r53zKYPwEXplBYH3mxuZP52r2u3udRcEg9yTUmwYE5yjmoUXyG', true),
('user2', '{bcrypt}$2a$10$vC.r53zKYPwEXplBYH3mxuZP52r2u3udRcEg9yTUmwYE5yjmoUXyG', true);

INSERT INTO AUTHORITIES (USERNAME, AUTHORITY) VALUES 
('admin', 'ROLE_ADMIN'),
('admin', 'ROLE_USER'),
('user1', 'ROLE_USER'),
('user2', 'ROLE_USER');

-- アプリケーション用ユーザープロフィール
INSERT INTO APP_USERS (USERNAME, EMAIL, DISPLAY_NAME, BIO) VALUES
('admin', 'admin@example.com', '管理者', 'システム管理者です'),
('user1', 'user1@example.com', '田中太郎', 'よろしくお願いします！'),
('user2', 'user2@example.com', '佐藤花子', 'プログラミングが好きです');

-- サンプル投稿
INSERT INTO POSTS (USER_ID, CONTENT) VALUES
(1, 'システム開設しました！みなさんよろしくお願いします。'),
(2, 'はじめての投稿です。SNSっぽくなってきましたね！'),
(3, 'Spring Bootでの開発は楽しいですね。'),
(2, 'フォロー機能も実装予定です！');

-- サンプルフォロー関係
INSERT INTO FOLLOWS (FOLLOWER_ID, FOLLOWING_ID) VALUES
(2, 1), -- user1がadminをフォロー
(3, 1), -- user2がadminをフォロー
(3, 2); -- user2がuser1をフォロー

