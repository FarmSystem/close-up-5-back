-- 플랫폼 추가

INSERT INTO platform (platform_name, created_at)
VALUES ('인스타그램', now());

INSERT INTO platform (platform_name, created_at)
VALUES ('트위치', now());

INSERT INTO platform (platform_name, created_at)
VALUES ('아프리카TV', now());

INSERT INTO platform (platform_name, created_at)
VALUES ('유튜브', now());

-- 크리에이터 추가
INSERT INTO user (nick_name, password, address, phone_number, profile_image_url, email, user_role, social_type, social_id, gender, birth_day, point, profile_comment, verification_image_url, platform_id, created_at)
VALUES ('주우재', '', '주소1', '010-1234-5678', '이미지1', '주우재@example.com', 'CREATOR', 'KAKAO', '101', 'male', '2000-06-17', 0, '안녕하세요 패션 유튜버 주우재에요', '확인 이미지1',1, '2023-11-02');

INSERT INTO user (nick_name, password, address, phone_number, profile_image_url, email, user_role, social_type, social_id, gender, birth_day, point, profile_comment, verification_image_url, platform_id, created_at)
VALUES ('침착맨', '', '주소2', '010-1234-5678', '이미지2', '침착맨@example.com', 'CREATOR', 'KAKAO', '102', 'male', '2000-06-17', 0, '침착맨입니당', '확인 이미지2',1, '2023-11-01');

INSERT INTO user (nick_name, password, address, phone_number, profile_image_url, email, user_role, social_type, social_id, gender, birth_day, point, profile_comment, verification_image_url, platform_id, created_at)
VALUES ('도티', '', '주소3', '010-1234-5678', '이미지3', '도티@example.com', 'CREATOR', 'KAKAO', '103', 'male', '2000-06-17', 0, '종합 크리에이터 도티에요', '확인 이미지3',1, '2023-10-02');

INSERT INTO user (nick_name, password, address, phone_number, profile_image_url, email, user_role, social_type, social_id, gender, birth_day, point, profile_comment, verification_image_url, platform_id, created_at)
VALUES ('페이커', '', '주소4', '010-1234-5678', '이미지4', '페이커@example.com', 'CREATOR', 'KAKAO', '104', 'male', '2000-06-17', 0, '페이커입니다', '확인 이미지4',3, '2023-09-02');

INSERT INTO user (nick_name, password, address, phone_number, profile_image_url, email, user_role, social_type, social_id, gender, birth_day, point, profile_comment, verification_image_url, platform_id, created_at)
VALUES ('인플루언서1', '', '주소5', '010-1234-5678', '이미지5', '인플루언서1@example.com', 'CREATOR', 'KAKAO', '105', 'female', '2000-06-17', 0, '인플루언서에요', '확인 이미지5',2, '2023-10-28');

INSERT INTO user (nick_name, password, address, phone_number, profile_image_url, email, user_role, social_type, social_id, gender, birth_day, point, profile_comment, verification_image_url, platform_id, created_at)
VALUES ('인플루언서2', '', '주소6', '010-1234-5678', '이미지6', '인플루언서2@example.com', 'CREATOR', 'KAKAO', '106', 'female', '2000-06-17', 0, '인플루언서에요', '확인 이미지6',2, '2023-10-02');

INSERT INTO user (nick_name, password, address, phone_number, profile_image_url, email, user_role, social_type, social_id, gender, birth_day, point, profile_comment, verification_image_url, platform_id, created_at)
VALUES ('인플루언서3', '', '주소7', '010-1234-5678', '이미지7', '인플루언서3@example.com', 'CREATOR', 'KAKAO', '107', 'male', '2000-06-17', 0, '인플루언서에요', '확인 이미지7',4, '2023-07-02');

INSERT INTO user (nick_name, password, address, phone_number, profile_image_url, email, user_role, social_type, social_id, gender, birth_day, point, profile_comment, verification_image_url, platform_id, created_at)
VALUES ('인플루언서4', '', '주소8', '010-1234-5678', '이미지8', '인플루언서4@example.com', 'CREATOR', 'KAKAO', '108', 'female', '2000-06-17', 0, '인플루언서에요', '확인 이미지8',4, '2023-01-02');


-- 회원 추가
INSERT INTO user (nick_name, password, address, phone_number, profile_image_url, email, user_role, social_type, social_id, gender, birth_day, point, profile_comment, verification_image_url, platform_id, created_at)
VALUES ('최필환1', null, '동국대학교', '010-1234-5678', '카카오url', '최필환@example.com', 'USER', 'KAKAO', '1', 'male', '2000-06-17', 0, null, null, NULL, '2023-03-02');

INSERT INTO user (nick_name, password, address, phone_number, profile_image_url, email, user_role, social_type, social_id, gender, birth_day, point, profile_comment, verification_image_url, platform_id, created_at)
VALUES ('최필환2', null, '동국대학교', '010-1234-5678', '카카오url', '최필환@example.com', 'USER', 'KAKAO', '1', 'male', '2000-06-17', 0, null , null, NULL, '2023-09-12');

INSERT INTO user (nick_name, password, address, phone_number, profile_image_url, email, user_role, social_type, social_id, gender, birth_day, point, profile_comment, verification_image_url, platform_id, created_at)
VALUES ('최필환3', null, '동국대학교', '010-1234-5678', '카카오url', '최필환@example.com', 'USER', 'KAKAO', '1', 'male', '2000-06-17', 0, null, null, NULL, '2023-09-13');

INSERT INTO user (nick_name, password, address, phone_number, profile_image_url, email, user_role, social_type, social_id, gender, birth_day, point, profile_comment, verification_image_url, platform_id, created_at)
VALUES ('최필환4', null, '동국대학교', '010-1234-5678', '카카오url', '최필환@example.com', 'USER', 'KAKAO', '1', 'male', '2000-06-17', 0, null, null, NULL, '2023-08-12');

INSERT INTO user (nick_name, password, address, phone_number, profile_image_url, email, user_role, social_type, social_id, gender, birth_day, point, profile_comment, verification_image_url, platform_id, created_at)
VALUES ('최필환5', null, '동국대학교', '010-1234-5678', '카카오url', '최필환@example.com', 'USER', 'KAKAO', '1', 'male', '2000-06-17', 0, null, null ,NULL, '2023-07-02');

-- 래플 카테고리 추가
INSERT INTO category (parent_id, category_name) VALUES (NULL, '유형');
INSERT INTO category (parent_id, category_name) VALUES (NULL, '무형');
INSERT INTO category (parent_id, category_name) VALUES (1, '사진');
INSERT INTO category (parent_id, category_name) VALUES (1, '영상');
INSERT INTO category (parent_id, category_name) VALUES (1, '음성');
INSERT INTO category (parent_id, category_name) VALUES (2, '손편지');
INSERT INTO category (parent_id, category_name) VALUES (2, '굿즈');
INSERT INTO category (parent_id, category_name) VALUES (2, '소장품');

-- 래플 상품 추가
INSERT INTO raffle_product (title, start_date, end_date, content, winner_count, raffle_price, address, winning_date, category_id)
VALUES ('라플1', '2023-11-02 12:00:00', '2023-11-03 12:00:00', '라플1 내용', 100, 1000, '서울시 강남구', '2023-11-03 13:00:00', 3);

INSERT INTO raffle_product (title, start_date, end_date, content, winner_count, raffle_price, address, winning_date,category_id)
VALUES ('라플2', '2023-10-02 12:00:00', '2023-11-12 12:00:00', '라플2 내용', 10, 3000, '서울시 강남구', '2023-12-05 13:00:00', 4);

INSERT INTO raffle_product (title, start_date, end_date, content, winner_count, raffle_price, address, winning_date, category_id)
VALUES ('라플3', '2023-11-01 12:00:00', '2023-11-29 12:00:00', '라플3 내용', 50, 4000, '서울시 강남구', '2023-12-07 13:00:00', 6);

INSERT INTO raffle_product (title, start_date, end_date, content, winner_count, raffle_price, address, winning_date, category_id)
VALUES ('라플4', '2023-09-02 12:00:00', '2023-12-03 12:00:00', '라플4 내용', 20, 10000, '서울시 강남구', '2023-12-03 13:00:00', 8);

-- 래플 이미지
INSERT INTO image (created_at, raffle_product_id, image_url, original_image_name, upload_image_name)
VALUES (now(), 1, '래플1 이미지 url 1', '원래 이미지 이름1', '업로드 이미지 이름1');
INSERT INTO image (created_at, raffle_product_id, image_url, original_image_name, upload_image_name)
VALUES (now(), 1, '래플1 이미지 url 2', '원래 이미지 이름2', '업로드 이미지 이름2');
INSERT INTO image (created_at, raffle_product_id, image_url, original_image_name, upload_image_name)
VALUES (now(), 1, '래플1 이미지 url 3', '원래 이미지 이름3', '업로드 이미지 이름3');
INSERT INTO image (created_at, raffle_product_id, image_url, original_image_name, upload_image_name)
VALUES (now(), 2, '래플2 이미지 url', '원래 이미지 이름1', '업로드 이미지 이름1');
INSERT INTO image (created_at, raffle_product_id, image_url, original_image_name, upload_image_name)
VALUES (now(), 3, '래플3 이미지 url', '원래 이미지 이름1', '업로드 이미지 이름1');
INSERT INTO image (created_at, raffle_product_id, image_url, original_image_name, upload_image_name)
VALUES (now(), 4, '래플4 이미지 url', '원래 이미지 이름1', '업로드 이미지 이름1');


-- 래플 판매자 설정
UPDATE raffle_product SET user_id = 1 WHERE raffle_product_id = 1;
UPDATE raffle_product SET user_id = 2 WHERE raffle_product_id = 2;
UPDATE raffle_product SET user_id = 3 WHERE raffle_product_id = 3;
UPDATE raffle_product SET user_id = 4 WHERE raffle_product_id = 4;

-- 팔로우 설정
INSERT INTO follow (user_id, creator_id) VALUES (9, 1);
INSERT INTO follow (user_id, creator_id) VALUES (9, 2);
INSERT INTO follow (user_id, creator_id) VALUES (9, 5);
INSERT INTO follow (user_id, creator_id) VALUES (9, 6);
INSERT INTO follow (user_id, creator_id) VALUES (10, 2);
INSERT INTO follow (user_id, creator_id) VALUES (10, 3);
INSERT INTO follow (user_id, creator_id) VALUES (11, 6);
INSERT INTO follow (user_id, creator_id) VALUES (11, 7);
INSERT INTO follow (user_id, creator_id) VALUES (12, 8);

-- 공지 사항
INSERT INTO notification (created_at, user_id, notification_content, notification_title, notification_thumbnail_url)
VALUES ('2023-09-02 12:00:00', 1, '공지사항1 내용', '공지사항1 제목', '공지사항1 썸네일 url');
INSERT INTO notification (created_at, user_id, notification_content, notification_title, notification_thumbnail_url)
VALUES ('2023-09-03 12:00:00', 1, '공지사항2 내용', '공지사항2 제목', '공지사항2 썸네일 url');
INSERT INTO notification (created_at, user_id, notification_content, notification_title, notification_thumbnail_url)
VALUES ('2023-09-04 12:00:00', 1, '공지사항3 내용', '공지사항3 제목', '공지사항3 썸네일 url');
INSERT INTO notification (created_at, user_id, notification_content, notification_title, notification_thumbnail_url)
VALUES ('2023-09-05 12:00:00', 1, '공지사항4 내용', '공지사항4 제목', '공지사항4 썸네일 url');

-- 공지사항 이미지 설정
INSERT INTO image (created_at, notification_id, image_url, original_image_name, upload_image_name)
VALUES (now(), 1, '공지1 이미지 url 1', '원래 이미지 이름1', '업로드 이미지 이름1');
INSERT INTO image (created_at, notification_id, image_url, original_image_name, upload_image_name)
VALUES (now(), 1, '공지1 이미지 url 2', '원래 이미지 이름2', '업로드 이미지 이름2');

-- 방명록
INSERT INTO guest_book (user_id, creator_id, content, created_at)
VALUES (9, 1, 'ㅎㅇ', '2023-09-02 12:00:00');
INSERT INTO guest_book (user_id, creator_id, content, created_at)
VALUES (9, 2, '헬로우', '2023-09-02 13:00:00');
INSERT INTO guest_book (user_id, creator_id, content, created_at)
VALUES (9, 3, '봉주르', '2023-09-02 14:00:00');
INSERT INTO guest_book (user_id, creator_id, content, created_at)
VALUES (10, 7, '사와디캅', '2023-09-05 12:00:00');

-- 관심사
Insert INTO interest (interest_id, interest_name) VALUES (1, '게임');
Insert INTO interest (interest_id, interest_name) VALUES (2, '여행');
Insert INTO interest (interest_id, interest_name) VALUES (3, '먹방');
Insert INTO interest (interest_id, interest_name) VALUES (4, '패션');
