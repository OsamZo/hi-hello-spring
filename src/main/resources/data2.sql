-- Department 데이터 삽입 (완)
INSERT INTO department (department_seq, department_name)
VALUES (1, '교육팀'),
       (2, '영업팀'),
       (3, '총무팀'),
       (4, '인사팀'),
       (5, '개발팀'),
       (6, '마케팅팀')
;

-- Quiz Category 데이터 삽입 (완)
INSERT INTO quiz_category (quiz_category_name, reg_date)
VALUES
    ('공통 교육 퀴즈', NOW()),
    ('직무 교육 퀴즈', NOW());

-- Position 데이터 삽입
INSERT INTO positions (position_seq, position_name)
VALUES
    (1, '팀장'),
    (2, '차장'),
    (3, '과장'),
    (4, '대리'),
    (5, '주임'),
    (6, '사원'),
    (7, '인턴');

-- Employee 데이터 삽입 (완)
INSERT INTO employee (employee_seq, department_seq, position_seq, employee_num, employee_name, employee_phone, employee_email, employee_role, employee_password, reg_date)
VALUES
    (1,4, 3, '202400001', '김민준', '010-1234-1234', 'hr@company.com', 'HR', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),

    (2,1, 1, '202400002', '이서윤', '010-0004-0001', 'leader1@company.com', 'STAFF', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (3, 2, 1, '202400003', '박지우', '010-0004-0002', 'leader2@company.com', 'STAFF', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (4, 3, 1, '202400004', '최지민', '010-0004-0003', 'leader3@company.com', 'STAFF', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (5, 4, 1, '202400005', '정예은', '010-0004-0004', 'leader4@company.com', 'STAFF', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (6, 5, 1, '202400006', '유도현', '010-0004-0005', 'leader5@company.com', 'STAFF', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (7, 6, 1, '202400007', '장수민', '010-0004-0006', 'leader6@company.com', 'STAFF', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),

    (8, 1, 4, '202400008', '윤하준', '010-0003-0001', 'mentor1@company.com', 'MENTOR', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (9, 1, 4, '202400009', '강서현', '010-0003-0002', 'mentor2@company.com', 'MENTOR', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (10, 2, 4, '202400010', '오시우', '010-0003-0003', 'mentor3@company.com', 'MENTOR', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (11, 2, 4, '202400011', '조민서', '010-0003-0004', 'mentor4@company.com', 'MENTOR', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (12, 3, 4, '202400012', '임도영', '010-0003-0005', 'mentor5@company.com', 'MENTOR', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (13, 3, 4, '202400013', '배하람', '010-0003-0006', 'mentor6@company.com', 'MENTOR', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (14, 4, 4, '202400014', '권유진', '010-0003-0007', 'mentor7@company.com', 'MENTOR', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (15, 4, 4, '202400015', '신태윤', '010-0003-0008', 'mentor8@company.com', 'MENTOR', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (16, 5, 4, '202400016', '홍아린', '010-0003-0009', 'mentor9@company.com', 'MENTOR', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (17, 5, 4, '202400017', '서현우', '010-0003-0010', 'mentor10@company.com', 'MENTOR', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (18, 6, 4, '202400018', '황예빈', '010-0003-0011', 'mentor11@company.com', 'MENTOR', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (19, 6, 4, '202400019', '안시현', '010-0003-0012', 'mentor12@company.com', 'MENTOR', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),

    (20, 1, 7, '202400020', '문지호', '010-0001-0001', 'mentee1@company.com', 'MENTEE', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (21, 1, 7, '202400021', '손하린', '010-0001-0002', 'mentee2@company.com', 'MENTEE', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (22, 2, 7, '202400022', '송지아', '010-0001-0003', 'mentee3@company.com', 'MENTEE', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (23, 2, 7, '202400023', '백준혁', '010-0001-0004', 'mentee4@company.com', 'MENTEE', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (24, 3, 7, '202400024', '허윤서', '010-0001-0005', 'mentee5@company.com', 'MENTEE', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (25, 3, 7, '202400025', '한태민', '010-0001-0006', 'mentee6@company.com', 'MENTEE', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (26, 4, 7, '202400026', '남유나', '010-0001-0007', 'mentee7@company.com', 'MENTEE', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (27, 4, 7, '202400027', '전다원', '010-0001-0008', 'mentee8@company.com', 'MENTEE', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (28, 5, 7, '202400028', '방우진', '010-0001-0009', 'mentee9@company.com', 'MENTEE', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (29, 5, 7, '202400029', '심가현', '010-0001-0010', 'mentee10@company.com', 'MENTEE', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (30, 6, 7, '202400030', '노주원', '010-0001-0011', 'mentee11@company.com', 'MENTEE', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (31, 6, 7, '202400031', '고채은', '010-0001-0012', 'mentee12@company.com', 'MENTEE', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW())
;

-- Quiz 데이터 - 변경 없음 (문제 자체는 유지)
INSERT INTO quiz (quiz_seq, quiz_category_seq, quiz_question, quiz_answer, quiz_explanation, reg_date)
VALUES
    -- 공통 교육 퀴즈
    (5, 1, '회사 내부에서 개인 USB 사용이 허용되나요?', FALSE, '보안을 위해 승인된 저장장치만 사용 가능합니다.', NOW()),
    (6, 1, '사내 메신저로 업무 자료 공유시 암호화가 필요한가요?', TRUE, '기밀문서는 반드시 암호화가 필요합니다.', NOW()),
    (7, 1, '재택근무 시 공용 Wi-Fi 사용이 가능한가요?', FALSE, '보안상의 이유로 공용 Wi-Fi 사용은 금지됩니다.', NOW()),
    (8, 1, '회사 계정의 비밀번호는 분기별로 변경해야 하나요?', TRUE, '정보보안 정책에 따라 분기별 변경이 필수입니다.', NOW()),
    (9, 1, '외부 미팅 시 노트북 보안케이블 사용은 필수인가요?', TRUE, '도난 방지를 위해 보안케이블 사용은 필수입니다.', NOW()),
    -- 직무 교육 퀴즈
    (10, 2, 'Java의 기본 데이터 타입 중 정수형은 byte, short, int, long이다.', TRUE, '자바의 정수형 기본 타입은 크기별로 4가지가 있습니다.', NOW()),
    (11, 2, 'Spring Framework에서 @Autowired는 생성자 주입만 지원한다.', FALSE, '@Autowired는 생성자, 필드, 세터 주입을 모두 지원합니다.', NOW()),
    (12, 2, 'REST API에서 PUT 메서드는 멱등성을 가진다.', TRUE, 'PUT은 같은 요청을 여러 번 보내도 결과가 동일합니다.', NOW()),
    (13, 2, 'JPA의 즉시로딩(EAGER)이 항상 성능상 유리하다.', FALSE, '상황에 따라 지연로딩(LAZY)이 더 유리할 수 있습니다.', NOW()),
    (14, 2, 'Docker 컨테이너는 반드시 하나의 프로세스만 실행해야 한다.', FALSE, '여러 프로세스 실행이 가능하나, 하나를 권장합니다.', NOW());
