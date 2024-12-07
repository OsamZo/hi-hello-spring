-- Department 데이터 삽입
INSERT INTO department (department_name)
VALUES ('교육팀');

-- Position 데이터 삽입
INSERT INTO positions (position_name)
VALUES
    ('팀장'),
    ('대리'),
    ('부장'),
    ('인턴');

-- Employee 데이터 삽입
INSERT INTO employee (department_seq, position_seq, employee_num, employee_name, employee_phone, employee_email, employee_role, employee_password, reg_date)
VALUES
    (1, 4, 'E001', '김멘티1', '010-1111-1111', 'mentee1@company.com', 'MENTEE', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (1, 4, 'E002', '김멘티2', '010-2222-2222', 'mentee2@company.com', 'MENTEE', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (1, 4, 'E003', '김멘티3', '010-3333-3333', 'mentee3@company.com', 'MENTEE', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (1, 4, 'E004', '김멘티4', '010-4444-4444', 'mentee4@company.com', 'MENTEE', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (1, 2, 'E005', '박멘토1', '010-5555-5555', 'mentor1@company.com', 'MENTOR', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (1, 2, 'E006', '박멘토2', '010-6666-6666', 'mentor2@company.com', 'MENTOR', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW()),
    (1, 4, 'E008', '최담당자', '010-8888-8888', 'manager@company.com', 'HR', '$2a$10$vYyPaZ.alZXIOdRdFbNg6urdu6p3tVJlWL2jeHXFIzIsnmrcwRKJ.', NOW());

-- Task 데이터 삽입
INSERT INTO task (department_seq, task_type, task_content, task_url, reg_date)
VALUES
    (1, 'PERSONAL', '온보딩 교육 자료 준비', 'onboarding@url.com' ,NOW()),
    (1, 'GROUP', '직무 교육 평가 준비', 'workedu@url.com', NOW());

-- Template 데이터 삽입
INSERT INTO template (template_type, template_check_required_status, template_training_type, template_title, template_sub, template_detail, template_url, template_task_round, template_procedure, reg_date)
VALUES
    ('온보딩', TRUE, '기본교육', '교육 템플릿1', '교육 템플릿 설명', '온보딩 교육 내용', 'https://example.com','3주차', 1, NOW()),
    ('직무교육', FALSE, '심화교육', '교육 템플릿2', '직무 교육 설명', '직무 교육 내용', 'https://example.com','2주차', 2, NOW());

-- Checklist 데이터 삽입
INSERT INTO checklist (checklist_content, template_seq)
VALUES
    ('교육 자료 확인', 1),
    ('과제 제출', 1),
    ('테스트 완료', 2);

-- Mentoring 데이터 삽입
INSERT INTO mentoring (mentor_seq, mentee_seq, reg_date)
VALUES
    (5, 1, NOW()),
    (5, 2, NOW()),
    (6, 3, NOW()),
    (6, 4, NOW());

-- Planning 데이터 삽입
INSERT INTO planning (employee_seq, planning_name, planning_content, planning_status, reg_date)
VALUES
    (1, '멘티 교육 계획', '교육 계획 내용', '진행 중', NOW()),
    (5, '멘토 계획', '멘토링 계획 내용', '완료', NOW());

-- Report 데이터 삽입
INSERT INTO report (mentoring_seq, report_content, report_feeling, report_week, reg_date)
VALUES
    (1, '멘토링 첫 주차 보고서', '잘 진행 중입니다.', '2024-12-01', NOW()),
    (2, '멘토링 두 번째 주차 보고서', '문제가 없습니다.', '2024-12-08', NOW());

-- Quiz Category 데이터 삽입
INSERT INTO quiz_category (quiz_category_name, reg_date)
VALUES
    ('온보딩 퀴즈', NOW()),
    ('직무 교육 퀴즈', NOW());

-- Quiz 데이터 삽입
INSERT INTO quiz (quiz_category_seq, quiz_question, quiz_answer, quiz_explanation, reg_date)
VALUES
    (1, '온보딩 퀴즈 질문1', TRUE, '정답 설명1', NOW()),
    (1, '온보딩 퀴즈 질문2', FALSE, '정답 설명2', NOW()),
    (2, '직무 교육 퀴즈 질문1', TRUE, '정답 설명3', NOW());

-- Quiz Result 데이터 삽입
INSERT INTO quiz_result (quiz_seq, employee_seq, quiz_correct_status, reg_date)
VALUES
    -- 온보딩 퀴즈 1번을 맞춘 김멘티1
    (1, 1, TRUE, NOW()),
    -- 온보딩 퀴즈 2번을 틀린 김멘티2
    (2, 2, FALSE, NOW()),
    -- 직무 교육 퀴즈 1번을 맞춘 김멘티3
    (3, 3, TRUE, NOW()),
    -- 온보딩 퀴즈 1번을 틀린 김멘티4
    (1, 4, FALSE, NOW());

-- Task Group 데이터 삽입
INSERT INTO task_group (task_seq, task_group_active_status, reg_date)
VALUES
    (1, TRUE, NOW()),
    (2, TRUE, NOW());

-- Group Member 데이터 삽입
INSERT INTO group_member (task_group_seq, employee_seq)
VALUES
    (1, 1),
    (1, 2),
    (2, 3),
    (2, 4);

-- Onboarding Status 데이터 삽입
INSERT INTO onboarding_status (template_seq, employee_seq, onboarding_completed_status, reg_date)
VALUES
    (1, 1, TRUE, NOW()),
    (1, 2, FALSE, NOW()),
    (2, 3, TRUE, NOW()),
    (2, 4, FALSE, NOW());

-- Peer Review List 데이터 삽입
INSERT INTO peer_review_list (peer_review_list_content, peer_review_list_score, reg_date)
VALUES
    ('팀워크 평가', 10, NOW()),
    ('의사소통 평가', 8, NOW());

-- Peer Review 데이터 삽입
INSERT INTO peer_review (peer_review_list_seq, reviewer_seq, reviewee_seq, peer_review_score, reg_date)
VALUES
    (1, 5, 1, 9, NOW()),
    (1, 6, 2, 8, NOW()),
    (2, 5, 3, 7, NOW());

-- Wiki 데이터 삽입
INSERT INTO wiki (wiki_title, wiki_current_ver, reg_date)
VALUES
    ('온보딩 가이드', 1, NOW()),
    ('직무 교육 매뉴얼', 1, NOW());



-- Notification 데이터 삽입
INSERT INTO noti (employee_seq, template_seq, noti_content, noti_url, reg_date)
VALUES
    (1, 1, '온보딩 교육이 시작되었습니다.', 'https://example.com/onboarding', NOW()),
    (3, 2, '직무 교육이 시작되었습니다.', 'https://example.com/job-training', NOW());

-- Wiki Snapshot 데이터 삽입
INSERT INTO wiki_snapshot (wiki_seq, wiki_snapshot_ver, wiki_snapshot_content, reg_date)
VALUES
    (1, 1, '온보딩 가이드 초기 버전', NOW()),
    (2, 1, '직무 교육 매뉴얼 초기 버전', NOW());


-- Wiki Mod Content 데이터 삽입
INSERT INTO wiki_mod_content (wiki_seq, employee_seq, wiki_snapshot_seq, mod_content, reg_date)
VALUES
    (1, 5, 1, '{"content": "온보딩 가이드 수정"}', NOW()),
    (2, 6, 1, '{"content": "직무 교육 매뉴얼 수정"}', NOW());

-- Task Submit 데이터 삽입
INSERT INTO task_submit (task_seq, employee_seq, task_submit_content)
VALUES
    (1, 1, "1차 과제 제출합니다."),
    (1, 2, "제출합니다."),