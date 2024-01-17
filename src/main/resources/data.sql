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
VALUES ('주우재', '', '주소1', '010-1234-5678', 'https://i.namu.wiki/i/-ddLtSDHza27R3QTcZzgS5opPp4wZEAGOn-WiQ4mgDY8D526pspvBWZN_G67l10esNu0jy3u4d1A_OlKbLG_UDs02K6PeVTcHQ3Uf545h2H1rh6mLiaEmuaaf_8YQPwuCoLimZ4wJdBAyzfTUTnglQ.webp', '주우재@example.com', 'CREATOR', 'KAKAO', '101', 'male', '1986-11-28', 0, '안녕하세요 패션 유튜버 주우재에요', '확인 이미지1',4, '2023-11-02');

INSERT INTO user (nick_name, password, address, phone_number, profile_image_url, email, user_role, social_type, social_id, gender, birth_day, point, profile_comment, verification_image_url, platform_id, created_at)
VALUES ('침착맨', '', '주소2', '010-1234-5678', 'https://i.namu.wiki/i/JHSI-HGyY342gXkpgRO2PvzTEzVlEB9Ij2wQCzotXPv0BzQ3Ome567pYWjoSQSTKoPj-02qbEvJppRrI8mLq1sit_czKHDRLpPtD45NECDmj6ujrZGWyzT2L8LTfCoJMdrwJDuLCUmN3-UW7DgXJsA.webp', '침착맨@example.com', 'CREATOR', 'KAKAO', '102', 'male', '1983-12-05', 0, '안녕하세요 침착맨입니당', '확인 이미지2',4, '2024-01-01');

INSERT INTO user (nick_name, password, address, phone_number, profile_image_url, email, user_role, social_type, social_id, gender, birth_day, point, profile_comment, verification_image_url, platform_id, created_at)
VALUES ('도티', '', '주소3', '010-1234-5678', 'https://m.segye.com/content/image/2020/03/11/20200311510987.jpg', '도티@example.com', 'CREATOR', 'KAKAO', '103', 'male', '1986-11-28', 0, '종합 크리에이터 도티에요', '확인 이미지3',4, '2023-10-02');

INSERT INTO user (nick_name, password, address, phone_number, profile_image_url, email, user_role, social_type, social_id, gender, birth_day, point, profile_comment, verification_image_url, platform_id, created_at)
VALUES ('쯔양', '', '주소4', '010-1234-5678', 'https://thumb.mtstarnews.com/06/2023/06/2023062711523398401_1.jpg/dims/optimize', '쯔양@example.com', 'CREATOR', 'KAKAO', '104', 'female', '1987-04-25', 0, '저랑 먹방하실분들 환영합니다!!', '확인 이미지4',4, '2023-09-02');

-- 회원 추가
-- INSERT INTO user (nick_name, password, address, phone_number, profile_image_url, email, user_role, social_type, social_id, gender, birth_day, point, profile_comment, verification_image_url, platform_id, created_at)
-- VALUES ('최필환1', null, '동국대학교', '010-1234-5678', '카카오url', '최필환@example.com', 'USER', 'KAKAO', '1', 'male', '2000-06-17', 0, null, null, NULL, '2023-03-02');
--
-- INSERT INTO user (nick_name, password, address, phone_number, profile_image_url, email, user_role, social_type, social_id, gender, birth_day, point, profile_comment, verification_image_url, platform_id, created_at)
-- VALUES ('최필환2', null, '동국대학교', '010-1234-5678', '카카오url', '최필환@example.com', 'USER', 'KAKAO', '1', 'male', '2000-06-17', 0, null , null, NULL, '2023-09-12');
--
-- INSERT INTO user (nick_name, password, address, phone_number, profile_image_url, email, user_role, social_type, social_id, gender, birth_day, point, profile_comment, verification_image_url, platform_id, created_at)
-- VALUES ('최필환3', null, '동국대학교', '010-1234-5678', '카카오url', '최필환@example.com', 'USER', 'KAKAO', '1', 'male', '2000-06-17', 0, null, null, NULL, '2023-09-13');
--
-- INSERT INTO user (nick_name, password, address, phone_number, profile_image_url, email, user_role, social_type, social_id, gender, birth_day, point, profile_comment, verification_image_url, platform_id, created_at)
-- VALUES ('최필환4', null, '동국대학교', '010-1234-5678', '카카오url', '최필환@example.com', 'USER', 'KAKAO', '1', 'male', '2000-06-17', 0, null, null, NULL, '2023-08-12');
--
-- INSERT INTO user (nick_name, password, address, phone_number, profile_image_url, email, user_role, social_type, social_id, gender, birth_day, point, profile_comment, verification_image_url, platform_id, created_at)
-- VALUES ('최필환5', null, '동국대학교', '010-1234-5678', '카카오url', '최필환@example.com', 'USER', 'KAKAO', '1', 'male', '2000-06-17', 0, null, null ,NULL, '2023-07-02');

-- 래플 카테고리 추가
INSERT INTO category ( category_name) VALUES ( '사진');
INSERT INTO category ( category_name) VALUES ( '영상');
INSERT INTO category ( category_name) VALUES ( '음성');
INSERT INTO category ( category_name) VALUES ( '손편지');
INSERT INTO category ( category_name) VALUES ( '굿즈');
INSERT INTO category ( category_name) VALUES ( '소장품');
INSERT INTO category ( category_name) VALUES ( '파일');

INSERT INTO winning_product (upload_file_name, original_file_name, file_url, created_at)
VALUES ('도티 쿠키 영상.png', '도티 쿠키 영상.png', 'https://close-up-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%83%E1%85%A9%E1%84%90%E1%85%B5+%E1%84%8F%E1%85%AE%E1%84%8F%E1%85%B5+%E1%84%8B%E1%85%A7%E1%86%BC%E1%84%89%E1%85%A1%E1%86%BC.png', '2024-01-03');

INSERT INTO winning_product (upload_file_name, original_file_name, file_url, created_at)
VALUES ('맛집 목록 - 쯔양.docx', '맛집 목록 - 쯔양.docx', 'https://close-up-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%86%E1%85%A1%E1%86%BA%E1%84%8C%E1%85%B5%E1%86%B8+%E1%84%86%E1%85%A9%E1%86%A8%E1%84%85%E1%85%A9%E1%86%A8+-+%E1%84%8D%E1%85%B3%E1%84%8B%E1%85%A3%E1%86%BC.docx', '2024-01-14');

-- 래플 상품 추가
INSERT INTO raffle_product (title, start_date, end_date, content, winner_count, raffle_price, address, winning_date, thumbnail_image_url, user_id, category_id, winning_product_id, created_at)
VALUES ('안입는 옷 나눔', '2024-01-02', '2024-01-25', '제가 지금껏 모델, 유튜버를 해오면서 많은 옷들이 생겼는데, 이번 기회에 여러분들께 나눔을 하고 싶어서 응모를 열게됐어요.', 20, 1000, '서울시 강남구', '2024-01-26 12:00:00', 'https://i.ytimg.com/vi/ig_w-B46j1Y/maxresdefault.jpg', 1, 6, null,'2024-01-02');

INSERT INTO raffle_product (title, start_date, end_date, content, winner_count, raffle_price, address, winning_date, thumbnail_image_url, user_id, category_id, winning_product_id, created_at)
VALUES ('침착맨 굿즈 나눔', '2024-01-13', '2024-01-26', '이번에 새로 출시한 저의 굿즈입니다. 미리 선공개하는 거니까 많이들 응모하세요!!', 40, 5000, '서울시 강남구', '2024-01-27 12:00:00', 'https://mblogthumb-phinf.pstatic.net/MjAyMjA4MDdfMjAy/MDAxNjU5ODA0NTUzMDkw.1b0gGGpM00dkD-h3BAsNiTUbsvU_LNxjtwpkLdV54eQg.4r_iO1v778AVTJTB7Ec8GReBMTAkdCHcQFIOQHuavrEg.JPEG.jhggo3/IMG_2926.jpg?type=w800', 2, 6, null,'2024-01-13');

INSERT INTO raffle_product (title, start_date, end_date, content, winner_count, raffle_price, address, winning_date, thumbnail_image_url, user_id, category_id, winning_product_id, created_at)
VALUES ('미공개 게임 유튜브 영상', '2024-01-03', '2024-01-15', '이번에 찍은 유튜브 영상의 쿠키 영상인데, 여러분들에게만 공개합니다!', 10, 3000, '서울시 강남구', '2024-01-16 12:00:00', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSiD0zrSU0_f7oEWbrMYgptJbzxlxGpWommBQ&usqp=CAU', 3, 2, 1,'2024-01-03');

INSERT INTO raffle_product (title, start_date, end_date, content, winner_count, raffle_price, address, winning_date, thumbnail_image_url, user_id, category_id, winning_product_id, created_at)
VALUES ('맛집 목록 파일', '2024-01-14', '2024-01-27', '제가 먹방 많이 다녔는데, 그간 맛있었던 음식점과 느낀점 목록입니다. 여러분도 모두 맛있는 하루 되시길 바래요!', 20, 6000, '서울시 강남구', '2024-01-26 12:00:00', 'https://www.siksinhot.com/theme/view/wp-content/uploads/2023/02/%EC%AF%94%EC%96%91%EB%A7%9B%EC%A7%91-%EC%A0%9C%EC%9D%BC%EB%B6%84%EC%8B%9D01.jpg', 4, 7, 2,'2024-01-14');

-- 래플 응모
-- INSERT INTO raffle (winning_info, raffle_product_id, user_id)
-- VALUES ('NONE', 4, 9);
--
-- INSERT INTO raffle (winning_info, raffle_product_id, user_id)
-- VALUES ('NONE', 4, 10);
--
-- INSERT INTO raffle (winning_info, raffle_product_id, user_id)
-- VALUES ('NONE', 4, 11);
--
-- INSERT INTO raffle (winning_info, raffle_product_id, user_id)
-- VALUES ('NONE', 4, 12);
--
-- INSERT INTO raffle (winning_info, raffle_product_id, user_id)
-- VALUES ('NONE', 4, 13);

-- 래플 이미지
INSERT INTO image (created_at, raffle_product_id, image_url, original_image_name, upload_image_name)
VALUES (now(), 1, 'https://leekorea.co.kr/web/product/small/202212/03800e3b352194397a9accebd3bc5917.jpg', 'lee 후드', 'lee 후드');
INSERT INTO image (created_at, raffle_product_id, image_url, original_image_name, upload_image_name)
VALUES (now(), 1, 'https://1746b291a6740af9.kinxzone.com/upload/images/product/257/257507/Product_1694180246152.jpg', '노페 패딩', '노페 패딩');
INSERT INTO image (created_at, raffle_product_id, image_url, original_image_name, upload_image_name)
VALUES (now(), 1, 'https://img.arcteryx.co.kr/29/683429/AENFUX6756_BTA_view.jpg', '아크테릭스 비니', '아크테릭스 비니');
INSERT INTO image (created_at, raffle_product_id, image_url, original_image_name, upload_image_name)
VALUES (now(), 2, 'https://mblogthumb-phinf.pstatic.net/MjAyMjA4MDdfMjAy/MDAxNjU5ODA0NTUzMDkw.1b0gGGpM00dkD-h3BAsNiTUbsvU_LNxjtwpkLdV54eQg.4r_iO1v778AVTJTB7Ec8GReBMTAkdCHcQFIOQHuavrEg.JPEG.jhggo3/IMG_2926.jpg?type=w800', '침착맨 티', '침착맨 티');
-- INSERT INTO image (created_at, raffle_product_id, image_url, original_image_name, upload_image_name)
-- VALUES (now(), 3, '래플4 이미지 url', '원래 이미지 이름1', '업로드 이미지 이름1');
INSERT INTO image (created_at, raffle_product_id, image_url, original_image_name, upload_image_name)
VALUES (now(), 4, 'https://close-up-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA+2024-01-17+%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE+10.36.47.png', '스크린샷 2024-01-17 오후 10.36.47.png', '스크린샷 2024-01-17 오후 10.36.47.png');

-- -- 래플 판매자 설정
-- UPDATE raffle_product SET user_id = 1 WHERE raffle_product_id = 1;
-- UPDATE raffle_product SET user_id = 2 WHERE raffle_product_id = 2;
-- UPDATE raffle_product SET user_id = 3 WHERE raffle_product_id = 3;
-- UPDATE raffle_product SET user_id = 4 WHERE raffle_product_id = 4;

-- -- 팔로우 설정
-- INSERT INTO follow (user_id, creator_id) VALUES (9, 1);
-- INSERT INTO follow (user_id, creator_id) VALUES (9, 2);
-- INSERT INTO follow (user_id, creator_id) VALUES (9, 5);
-- INSERT INTO follow (user_id, creator_id) VALUES (9, 6);
-- INSERT INTO follow (user_id, creator_id) VALUES (10, 2);
-- INSERT INTO follow (user_id, creator_id) VALUES (10, 3);
-- INSERT INTO follow (user_id, creator_id) VALUES (11, 6);
-- INSERT INTO follow (user_id, creator_id) VALUES (11, 7);
-- INSERT INTO follow (user_id, creator_id) VALUES (12, 8);

-- -- 공지 사항
-- INSERT INTO notification (created_at, user_id, notification_content, notification_title, notification_thumbnail_url)
-- VALUES ('2023-09-02 12:00:00', 1, '공지사항1 내용', '공지사항1 제목', '공지사항1 썸네일 url');
-- INSERT INTO notification (created_at, user_id, notification_content, notification_title, notification_thumbnail_url)
-- VALUES ('2023-09-03 12:00:00', 1, '공지사항2 내용', '공지사항2 제목', '공지사항2 썸네일 url');
-- INSERT INTO notification (created_at, user_id, notification_content, notification_title, notification_thumbnail_url)
-- VALUES ('2023-09-04 12:00:00', 1, '공지사항3 내용', '공지사항3 제목', '공지사항3 썸네일 url');
-- INSERT INTO notification (created_at, user_id, notification_content, notification_title, notification_thumbnail_url)
-- VALUES ('2023-09-05 12:00:00', 1, '공지사항4 내용', '공지사항4 제목', '공지사항4 썸네일 url');

-- -- 공지사항 이미지 설정
-- INSERT INTO image (created_at, notification_id, image_url, original_image_name, upload_image_name)
-- VALUES (now(), 1, '공지1 이미지 url 1', '원래 이미지 이름1', '업로드 이미지 이름1');
-- INSERT INTO image (created_at, notification_id, image_url, original_image_name, upload_image_name)
-- VALUES (now(), 1, '공지1 이미지 url 2', '원래 이미지 이름2', '업로드 이미지 이름2');

-- -- 방명록
-- INSERT INTO guest_book (user_id, creator_id, content, created_at)
-- VALUES (9, 1, 'ㅎㅇ', '2023-09-02 12:00:00');
-- INSERT INTO guest_book (user_id, creator_id, content, created_at)
-- VALUES (9, 2, '헬로우', '2023-09-02 13:00:00');
-- INSERT INTO guest_book (user_id, creator_id, content, created_at)
-- VALUES (9, 3, '봉주르', '2023-09-02 14:00:00');
-- INSERT INTO guest_book (user_id, creator_id, content, created_at)
-- VALUES (10, 7, '사와디캅', '2023-09-05 12:00:00');

-- -- 관심사
-- Insert INTO interest (interest_id, interest_name) VALUES (1, '게임');
-- Insert INTO interest (interest_id, interest_name) VALUES (2, '여행');
-- Insert INTO interest (interest_id, interest_name) VALUES (3, '먹방');
-- Insert INTO interest (interest_id, interest_name) VALUES (4, '패션');
