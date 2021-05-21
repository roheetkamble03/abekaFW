package constants;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public @interface DataBaseQueryConstant {
    String STUDENT_GRADE_WITH_SUBJECT_AD_DB = "SELECT tboxh.txh_subj, tnp.eno_desc || ' - ' || tboxd.txd_desc AS assessment,\\n\" +\n" +
            "            \"                            CASE WHEN assign.grade_percentage = 777.7 OR assign.da_status IN (3, 5, 6) THEN 'Grade Pending' WHEN assign.da_status IN (1) THEN 'In Progress' ELSE to_char(assign.grade_percentage) || '%' END AS grade,\\n\" +\n" +
            "            \"                            CASE WHEN assign.grade_percentage = 777.7 OR assign.da_status IN (1, 3, 5, 6) THEN '' ELSE lgrds.lgr_lgrd END AS lettergrade,\\n\" +\n" +
            "            \"                            assign.completion_date, assign.da_grading_lockdate, assign.da_enddate\\n\" +\n" +
            "            \"                   FROM homeschoolhouse.student_assignments assign\\n\" +\n" +
            "            \"                       JOIN linc.abadb_tboxh tboxh\\n\" +\n" +
            "            \"                         ON assign.pr_form = tboxh.txh_form\\n\" +\n" +
            "            \"                        AND assign.pr_version = tboxh.txh_ver\\n\" +\n" +
            "            \"                        AND assign.pr_school = tboxh.txh_school\\n\" +\n" +
            "            \"                        AND assign.pr_boxletter = tboxh.txh_boxltr\\n\" +\n" +
            "            \"                       JOIN linc.abadb_tboxd tboxd\\n\" +\n" +
            "            \"                         ON tboxh.txh_form = tboxd.form_number\\n\" +\n" +
            "            \"                        AND tboxh.txh_school = tboxd.school\\n\" +\n" +
            "            \"                        AND tboxh.txh_boxltr = tboxd.box_letter\\n\" +\n" +
            "            \"                        AND tboxh.txh_ver = tboxd.version\\n\" +\n" +
            "            \"                        AND assign.pr_itemnumber = tboxd.txd_itmnbr\\n\" +\n" +
            "            \"                        AND CASE WHEN assign.pr_subitemnumber IS NULL THEN 0 ELSE assign.pr_subitemnumber END = 0\\n\" +\n" +
            "            \"                       JOIN linc.abadb_tenop tnp\\n\" +\n" +
            "            \"                         ON tboxh.txh_subj = tnp.eno_rcard\\n\" +\n" +
            "            \"                       JOIN linc.abadb_appld appld\\n\" +\n" +
            "            \"                         ON assign.apref = appld.ap_apref\\n\" +\n" +
            "            \"                       JOIN linc.abadb_applc applc\\n\" +\n" +
            "            \"                         ON appld.ap_apref = applc.apc_apref\\n\" +\n" +
            "            \"                        AND applc.ap_item = tnp.eno_subj\\n\" +\n" +
            "            \"                      INNER JOIN linc.abadb_grads grads\\n\" +\n" +
            "            \"                         ON appld.ap_grade = grads.gr_grade\\n\" +\n" +
            "            \"                        AND appld.ap_beg_dt BETWEEN grads.gr_beg_dt AND grads.gr_end_dt\\n\" +\n" +
            "            \"                       LEFT OUTER JOIN linc.abadb_lgrds lgrds\\n\" +\n" +
            "            \"                         ON grads.gr_scale = lgrds.lgr_scale\\n\" +\n" +
            "            \"                        AND CASE WHEN assign.grade_percentage = 0 THEN 1 ELSE assign.grade_percentage END BETWEEN lgrds.lgr_min_gr AND lgrds.lgr_max_gr\\n\" +\n" +
            "            \"                      WHERE assign.student_id = 'STUDENT_ID_DATA'\\n\" +\n" +
            "            \"                        AND appld.ap_end_dt >= trunc(sysdate)\\n\" +\n" +
            "            \"                        AND assign.da_enddate is not null\\n\" +\n" +
            "            \"                        AND coalesce(assign.da_grading_lockdate, CASE WHEN da_status NOT IN(7,8) THEN sysdate ELSE assign.da_enddate END) >= sysdate - 'ROW_COUNT_DATA'\\n\" +\n" +
            "            \"                        AND tboxd.txd_da_testid > 0\\n\" +\n" +
            "            \"                      UNION\\n\" +\n" +
            "            \"                     SELECT tboxh.txh_subj, tnp.eno_desc || ' - ' || tboxm.txm_desc AS assessment,\\n\" +\n" +
            "            \"                            CASE WHEN assign.grade_percentage = 777.7 OR assign.da_status IN (1, 3, 5, 6) THEN 'Grade Pending' WHEN assign.da_status IN (1) THEN 'In Progress' ELSE to_char(assign.grade_percentage) || '%' END AS grade,\\n\" +\n" +
            "            \"                            CASE WHEN assign.grade_percentage = 777.7 OR assign.da_status IN (1, 3, 5, 6) THEN '' ELSE lgrds.lgr_lgrd END AS lettergrade,\\n\" +\n" +
            "            \"                            assign.completion_date, assign.da_grading_lockdate, assign.da_enddate\\n\" +\n" +
            "            \"                       FROM homeschoolhouse.student_assignments assign\\n\" +\n" +
            "            \"                       JOIN linc.abadb_tboxh tboxh\\n\" +\n" +
            "            \"                         ON assign.pr_form = tboxh.txh_form\\n\" +\n" +
            "            \"                        AND assign.pr_version = tboxh.txh_ver\\n\" +\n" +
            "            \"                        AND assign.pr_school = tboxh.txh_school\\n\" +\n" +
            "            \"                        AND assign.pr_boxletter = tboxh.txh_boxltr\\n\" +\n" +
            "            \"                       JOIN linc.abadb_tboxm tboxm\\n\" +\n" +
            "            \"                         ON tboxh.txh_form = tboxm.form_number\\n\" +\n" +
            "            \"                        AND tboxh.txh_school = tboxm.school\\n\" +\n" +
            "            \"                        AND tboxh.txh_boxltr = tboxm.box_letter\\n\" +\n" +
            "            \"                        AND tboxh.txh_ver = tboxm.version\\n\" +\n" +
            "            \"                        AND assign.pr_itemnumber = tboxm.txm_itmnbr\\n\" +\n" +
            "            \"                        AND assign.pr_subitemnumber = tboxm.txm_sub_itmnbr\\n\" +\n" +
            "            \"                       JOIN linc.abadb_tenop tnp\\n\" +\n" +
            "            \"                         ON tboxh.txh_subj = tnp.eno_rcard\\n\" +\n" +
            "            \"                       JOIN linc.abadb_appld appld\\n\" +\n" +
            "            \"                         ON assign.apref = appld.ap_apref\\n\" +\n" +
            "            \"                       JOIN linc.abadb_applc applc\\n\" +\n" +
            "            \"                         ON appld.ap_apref = applc.apc_apref\\n\" +\n" +
            "            \"                        AND applc.ap_item = tnp.eno_subj\\n\" +\n" +
            "            \"                      INNER JOIN linc.abadb_grads grads\\n\" +\n" +
            "            \"                         ON appld.ap_grade = grads.gr_grade\\n\" +\n" +
            "            \"                        AND appld.ap_beg_dt BETWEEN grads.gr_beg_dt AND grads.gr_end_dt\\n\" +\n" +
            "            \"                       LEFT OUTER JOIN linc.abadb_lgrds lgrds\\n\" +\n" +
            "            \"                         ON grads.gr_scale = lgrds.lgr_scale\\n\" +\n" +
            "            \"                        AND CASE WHEN assign.grade_percentage = 0 THEN 1 ELSE assign.grade_percentage END BETWEEN lgrds.lgr_min_gr AND lgrds.lgr_max_gr\\n\" +\n" +
            "            \"                      WHERE assign.student_id = 'STUDENT_ID_DATA'\\n\" +\n" +
            "            \"                        AND appld.ap_end_dt >= trunc(sysdate)\\n\" +\n" +
            "            \"                        AND assign.da_enddate is not null\\n\" +\n" +
            "            \"                        AND coalesce(assign.da_grading_lockdate, CASE WHEN da_status NOT IN(7,8) THEN sysdate ELSE assign.da_enddate END) >= sysdate - 'ROW_COUNT_DATA'\\n\" +\n" +
            "            \"                        AND tboxm.txm_da_testid > 0\\n\" +\n" +
            "            \"                      ORDER BY completion_date";

    String MY_LESSONS_TODAY_SD_DB = "WITH\\n\" +\n" +
            "            \"    studentAssignments AS (SELECT *\\n\" +\n" +
            "            \"                             FROM homeschoolhouse.student_assignments@abadb assign\\n\" +\n" +
            "            \"                             JOIN homeschoolhouse.student_assignments_types@abadb types\\n\" +\n" +
            "            \"                               ON assign.assignment_type_fk = types.assignment_type_pk\\n\" +\n" +
            "            \"                            WHERE assign.student_id = 'STUDENT_ID_DATA'\\n\" +\n" +
            "            \"                              AND types.name IN('DVD', 'VIDEO')),\\n\" +\n" +
            "            \"    videoAssignments AS (SELECT ap_grade, ap_end_dt, eventID, apref, start_date, end_date, completion_date, lesson_number, lock_assignment, segment_id_fk, subject, subject_id_fk, assignType,\\n\" +\n" +
            "            \"                                short_description, long_description, da_testassignmentid, pr_form, pr_version, pr_school, pr_boxletter, pr_itemnumber, pr_subitemnumber, grade,\\n\" +\n" +
            "            \"                                book_item_number, image_url, ap_credt, teacher, sub_start_date, sortorder, sessionsrequired, requirementsRemaining,\\n\" +\n" +
            "            \"                                CASE\\n\" +\n" +
            "            \"                                    WHEN SUM(requirementsRemaining) OVER (PARTITION BY subject_id_fk, lesson_number) = 0 THEN 'Y'\\n\" +\n" +
            "            \"                                    ELSE 'N'\\n\" +\n" +
            "            \"                                END AS IsRequirementMet,\\n\" +\n" +
            "            \"                                subscription_number_pk, latest_point, restart_at, completed\\n\" +\n" +
            "            \"                           FROM (SELECT ap_grade, ap_end_dt, eventid, apref, start_date, end_date, completion_date, lesson_number, lock_assignment, segment_id_fk, subject, subject_id_fk, assignType,\\n\" +\n" +
            "            \"                                        short_description, long_description, da_testassignmentid, pr_form, pr_version, pr_school, pr_boxletter, pr_itemnumber, pr_subitemnumber, grade,\\n\" +\n" +
            "            \"                                        book_item_number, image_url, ap_credt, teacher, sub_start_date, sortorder,\\n\" +\n" +
            "            \"                                        sessionsrequired, sessionoptioncount, min(sessionRemaining) OVER (PARTITION BY subject_id_fk, supergroup, lesson_number) AS requirementsRemaining,\\n\" +\n" +
            "            \"                                        subscription_number_pk, latest_point, restart_at, completed\\n\" +\n" +
            "            \"                                   FROM (SELECT ap_grade, ap_end_dt, assign.student_assignments_id AS eventID, assign.apref, assign.start_date, assign.end_date, assign.completion_date, segments.lesson AS lesson_number,\\n\" +\n" +
            "            \"                                                assign.lock_assignment, assign.segment_id_fk, assign.subject, assign.subject_id_fk,\\n\" +\n" +
            "            \"                                                CASE mediatypes.name\\n\" +\n" +
            "            \"                                                    WHEN 'STREAMING' THEN 'LESSON'\\n\" +\n" +
            "            \"                                                    ELSE 'DVD'\\n\" +\n" +
            "            \"                                                END AS assignType,\\n\" +\n" +
            "            \"                                                sessions.sessionname AS short_description, 'Lesson ' || segments.lesson AS long_description, assign.da_testassignmentid,\\n\" +\n" +
            "            \"                                                assign.pr_form, assign.pr_version, assign.pr_school, assign.pr_boxletter, assign.pr_itemnumber, COALESCE(assign.pr_subitemnumber, 0) AS pr_subitemnumber,\\n\" +\n" +
            "            \"                                                subjects.grade, to_number(subjects.book_item_number) AS book_item_number, subjects.image_url, appld.ap_credt,\\n\" +\n" +
            "            \"                                                CASE\\n\" +\n" +
            "            \"                                                    WHEN NVL(title.ttitl_nbr, 0) IN (3, 9, 10, 20, 21) THEN NVL(title.ttitledesc,'') || ' ' || NVL(teacher.teacher_last_name,'')\\n\" +\n" +
            "            \"                                                    ELSE NVL(title.ttitledesc,'') || '. ' || NVL(teacher.teacher_last_name,'')\\n\" +\n" +
            "            \"                                                END AS teacher, subs.start_date AS sub_start_date, sessions.sortorder, COALESCE(sessions.supergroup, sessions.sessiontype) AS supergroup, segments.sessionsrequired, segments.sessionoptioncount,\\n\" +\n" +
            "            \"                                                CASE\\n\" +\n" +
            "            \"                                                    WHEN assign.completion_date IS NOT NULL THEN 0\\n\" +\n" +
            "            \"                                                    ELSE 1\\n\" +\n" +
            "            \"                                                END AS sessionRemaining, subs.subscription_number_pk, COALESCE(uitems.latest_point, 0) AS latest_point, COALESCE(uitems.restart_at, 0) AS restart_at,\\n\" +\n" +
            "            \"                                                CASE\\n\" +\n" +
            "            \"                                                    WHEN uitems.completed IS NULL THEN\\n\" +\n" +
            "            \"                                                        CASE\\n\" +\n" +
            "            \"                                                            WHEN assign.completion_date IS NOT NULL THEN 'Y'\\n\" +\n" +
            "            \"                                                            ELSE 'N'\\n\" +\n" +
            "            \"                                                        END\\n\" +\n" +
            "            \"                                                    ELSE COALESCE(uitems.completed, 'N')\\n\" +\n" +
            "            \"                                                END AS completed\\n\" +\n" +
            "            \"                                           FROM studentAssignments assign\\n\" +\n" +
            "            \"                                           JOIN abadb.appld\\n\" +\n" +
            "            \"                                             ON assign.apref = appld.ap_apref\\n\" +\n" +
            "            \"                                           JOIN abashared.vlessonsegmentrequirements segments\\n\" +\n" +
            "            \"                                             ON assign.segment_id_fk = segments.segmentid\\n\" +\n" +
            "            \"                                           JOIN abashared.vsessiontypes sessions\\n\" +\n" +
            "            \"                                             ON segments.sessiontypeid = sessions.sessiontypeid\\n\" +\n" +
            "            \"                                           JOIN abashared.vsubjects subjects\\n\" +\n" +
            "            \"                                             ON subjects.subjectid = assign.subject_id_fk\\n\" +\n" +
            "            \"                                           -- streaming only? use JOIN\\n\" +\n" +
            "            \"                                           -- with DVDs? use LEFT JOIN\\n\" +\n" +
            "            \"                                           JOIN abashared.streaming_subscriptions subs\\n\" +\n" +
            "            \"                                             ON assign.apref = subs.application_number\\n\" +\n" +
            "            \"                                           -- streaming only? use JOIN\\n\" +\n" +
            "            \"                                           -- with DVDs? use LEFT JOIN                                    \\n\" +\n" +
            "            \"                                           JOIN abashared.streaming_subscriptions_items subitems\\n\" +\n" +
            "            \"                                             ON subs.subscription_number_pk = subitems.subscription_number_fk\\n\" +\n" +
            "            \"                                            AND subitems.subject_id_fk = assign.subject_id_fk\\n\" +\n" +
            "            \"                                      LEFT JOIN abashared.streaming_users_items uitems\\n\" +\n" +
            "            \"                                             ON uitems.subscription_items_fk = subitems.subscription_items_pk\\n\" +\n" +
            "            \"                                            AND uitems.segment_id_fk = segments.segmentid\\n\" +\n" +
            "            \"                                      LEFT JOIN abashared.teacher_subjects teacher_sbj\\n\" +\n" +
            "            \"                                             ON teacher_sbj.subject_id_fk = segments.subjectid\\n\" +\n" +
            "            \"                                      LEFT JOIN abashared.teachers teacher\\n\" +\n" +
            "            \"                                             ON teacher.teacher_pk = teacher_sbj.teacher_pk_fk\\n\" +\n" +
            "            \"                                           JOIN abadb.ttitl title\\n\" +\n" +
            "            \"                                             ON title.ttitl_nbr = teacher.teacher_title\\n\" +\n" +
            "            \"                                      LEFT JOIN abashared.unfinished_rchdr urchdr\\n\" +\n" +
            "            \"                                             ON appld.ap_apref = urchdr.rch_apref\\n\" +\n" +
            "            \"                                           JOIN enrollment.media_format_types@abadb mediatypes\\n\" +\n" +
            "            \"                                             ON mediatypes.media_format_type_id = appld.media_format_type_id\\n\" +\n" +
            "            \"                                          WHERE (subs.subscription_status_fk = 1\\n\" +\n" +
            "            \"                                                    OR subs.subscription_status_fk IS NULL\\n\" +\n" +
            "            \"                                                    OR mediatypes.name != 'STREAMING')\\n\" +\n" +
            "            \"                                            AND (sessions.subject_group = subitems.subject_group\\n\" +\n" +
            "            \"                                                    OR subitems.subject_group IS NULL\\n\" +\n" +
            "            \"                                                    OR sessions.subject_group IS NULL\\n\" +\n" +
            "            \"                                                    OR uitems.completed = 'Y')\\n\" +\n" +
            "            \"                                            AND (TRUNC(SYSDATE)  BETWEEN COALESCE(subs.start_date, to_date(appld.ap_beg_dt, 'yyyymmdd') - 14, trunc(sysdate)) AND COALESCE(subs.end_date, appld.ap_end_dt, trunc(sysdate))\\n\" +\n" +
            "            \"                                                    OR (mediatypes.name != 'STREAMING' AND (TRUNC(SYSDATE) BETWEEN to_date(appld.ap_beg_dt, 'yyyymmdd') - 14  AND COALESCE(appld.ap_end_dt, trunc(sysdate)) ) ) )\\n\" +\n" +
            "            \"                                            AND (segments.lesson BETWEEN subitems.start_lesson_number and subitems.end_lesson_number\\n\" +\n" +
            "            \"                                                    OR subs.subscription_status_fk IS NULL\\n\" +\n" +
            "            \"                                                    OR mediatypes.name != 'STREAMING')\\n\" +\n" +
            "            \"                                            AND (urchdr.rch_status IN ('A', 'I')\\n\" +\n" +
            "            \"                                                    OR appld.ap_credt != 'Y')\\n\" +\n" +
            "            \"                                            AND (sessions.pen_key = appld.pen_key OR sessions.pen_key IS NULL)\\n\" +\n" +
            "            \"                                            AND appld.ap_status = 8\\n\" +\n" +
            "            \"                                        )\\n\" +\n" +
            "            \"                                )\\n\" +\n" +
            "            \"                        ),\\n\" +\n" +
            "            \"    nextLessonsMinLesson AS (SELECT DISTINCT assign.ap_end_dt, assign.ap_grade, assign.subject_id_fk, assign.apref, MIN(assign.lesson_number) OVER(PARTITION BY assign.subject_id_fk) AS nextLesson, MIN(assign.lesson_number) OVER(PARTITION BY assign.apref) AS minLesson\\n\" +\n" +
            "            \"                               FROM videoAssignments assign\\n\" +\n" +
            "            \"                              WHERE assign.completion_date IS NULL\\n\" +\n" +
            "            \"                                AND assign.IsRequirementMet = 'N'\\n\" +\n" +
            "            \"                           GROUP BY assign.ap_grade, assign.ap_end_dt, assign.subject_id_fk, assign.apref, assign.lesson_number),\\n\" +\n" +
            "            \"    lastCompletedLessons AS (SELECT DISTINCT assign.ap_grade, assign.ap_end_dt, assign.subject_id_fk, MAX(assign.lesson_number) OVER(PARTITION BY assign.subject_id_fk) AS lastCompletedLesson\\n\" +\n" +
            "            \"                               FROM videoAssignments assign\\n\" +\n" +
            "            \"                              WHERE assign.completion_date IS NOT NULL\\n\" +\n" +
            "            \"                                AND assign.IsRequirementMet = 'Y'\\n\" +\n" +
            "            \"                           GROUP BY assign.ap_grade, assign.ap_end_dt, assign.subject_id_fk, assign.lesson_number)\\n\" +\n" +
            "            \"                           \\n\" +\n" +
            "            \"    SELECT vAssign.ap_grade, vAssign.ap_end_dt, vAssign.apref, vAssign.eventID AS eventID, vAssign.start_date, vAssign.end_date, vAssign.short_description, vAssign.long_description, vAssign.completion_date,\\n\" +\n" +
            "            \"           vAssign.assignType, CAST(vAssign.subject_id_fk AS NUMBER(10)) AS subject_id_fk, vAssign.segment_id_fk, vAssign.subject, 0 AS linkitTestID, vAssign.da_testassignmentid, vAssign.book_item_number, vAssign.image_url, vAssign.lock_assignment,\\n\" +\n" +
            "            \"           vAssign.pr_form, vAssign.pr_version, vAssign.pr_school, vAssign.pr_boxletter, vAssign.pr_itemnumber, vAssign.pr_subitemnumber, CAST(vAssign.sortorder AS NUMBER(3)) AS sortorder, vAssign.lesson_number,\\n\" +\n" +
            "            \"           vAssign.subscription_number_pk, vAssign.restart_at, vAssign.latest_point, vAssign.completed, vAssign.teacher, vAssign.ap_credt, 'N' AS lockedlesson,\\n\" +\n" +
            "            \"           vAssign.requirementsRemaining, vAssign.IsRequirementMet\\n\" +\n" +
            "            \"      FROM videoAssignments vAssign\\n\" +\n" +
            "            \" LEFT JOIN lastCompletedLessons lCL\\n\" +\n" +
            "            \"        ON vAssign.subject_id_fk = lCL.subject_id_fk\\n\" +\n" +
            "            \" LEFT JOIN nextLessonsMinLesson nLML\\n\" +\n" +
            "            \"        ON vAssign.subject_id_fk = nLML.subject_id_fk\\n\" +\n" +
            "            \"       AND vAssign.apref = nLML.apref\\n\" +\n" +
            "            \"     WHERE vAssign.lesson_number = CASE\\n\" +\n" +
            "            \"                                       WHEN (vAssign.grade IN(14,15,1,2,3,4,5,6) AND :flag = ' ') OR :flag = 'Y' THEN nLmL.minLesson\\n\" +\n" +
            "            \"                                       WHEN (vAssign.grade IN(7,8,9,10,11,12) AND :flag = ' ') OR :flag = 'N' THEN nLmL.nextLesson\\n\" +\n" +
            "            \"                                   END\\n\" +\n" +
            "            \"        OR (vAssign.grade IN(7,8,9,10,11,12) AND trunc(vAssign.completion_date) = trunc(sysdate) )\\n\" +\n" +
            "            \"  ORDER BY vAssign.completion_date desc, vAssign.sub_start_date, vAssign.sortorder, vAssign.lesson_number";

    String AVAILABLE_TEST_QUIZZES_AD_DB = "WITH\\n\" +\n" +
            "            \"              nextVideoLessons AS (\\n\" +\n" +
            "            \"                 SELECT DISTINCT appld.ap_grade, appld.ap_end_dt, assign.apref, assign.subject, min(lessonInfo.lesson) OVER (PARTITION BY assign.subject, assign.apref) AS nextVideoLesson\\n\" +\n" +
            "            \"                   FROM homeschoolhouse.student_assignments assign\\n\" +\n" +
            "            \"                   JOIN homeschoolhouse.student_assignments_types types\\n\" +\n" +
            "            \"                     ON assign.assignment_type_fk = types.assignment_type_pk\\n\" +\n" +
            "            \"                   JOIN homeschoolhouse.lesson_information lessonInfo\\n\" +\n" +
            "            \"                     ON assign.segment_id_fk = lessonInfo.segmentid\\n\" +\n" +
            "            \"                    AND assign.apref = lessonInfo.application_number\\n\" +\n" +
            "            \"                    AND assign.subject_id_fk = lessonInfo.subject_id_fk\\n\" +\n" +
            "            \"                   JOIN linc.abadb_appld appld\\n\" +\n" +
            "            \"                     ON assign.apref = appld.ap_apref\\n\" +\n" +
            "            \"                   LEFT JOIN linc.abadb_rchdr rchdr\\n\" +\n" +
            "            \"                     ON appld.ap_apref = rchdr.rch_apref\\n\" +\n" +
            "            \"                  WHERE assign.student_id = 'STUDENT_ID_DATA'\\n\" +\n" +
            "            \"                    AND appld.ap_status = 8\\n\" +\n" +
            "            \"                    AND trunc(appld.ap_end_dt) >= trunc(sysdate)\\n\" +\n" +
            "            \"                    AND assign.completion_date IS NULL\\n\" +\n" +
            "            \"                    AND lessonInfo.lesson BETWEEN lessonInfo.start_lesson_number AND lessonInfo.end_lesson_number\\n\" +\n" +
            "            \"                    AND types.name = 'VIDEO'\\n\" +\n" +
            "            \"                    AND (rchdr.rch_status IN ('A', 'I')\\n\" +\n" +
            "            \"                     OR  appld.ap_credt != 'Y')\\n\" +\n" +
            "            \"                  GROUP BY appld.ap_grade, appld.ap_end_dt, assign.subject, lessonInfo.lesson, assign.apref\\n\" +\n" +
            "            \"              ),\\n\" +\n" +
            "            \"              studentAssignments AS (\\n\" +\n" +
            "            \"              SELECT appld.ap_grade, appld.ap_end_dt, assign.apref, assign.student_assignments_id AS eventID, assign.start_date, assign.end_date, tenop.eno_desc, tboxd.txd_desc, tboxm.txm_desc, types.description, assign.completion_date, assign.lock_assignment,\\n\" +\n" +
            "            \"                     assign.da_enddate,tboxh.txh_type, assign.segment_id_fk, coalesce(to_number(assign.da_testid), tboxd.txd_da_testid, tboxm.txm_da_testid, 0) AS linkitTestID, assign.da_testassignmentid, types.name, tenop.eno_ver, tenop.eno_series,\\n\" +\n" +
            "            \"                     tboxh.txh_subj, assign.subject, assign.pr_form, assign.pr_version, assign.pr_school, assign.pr_boxletter, assign.pr_itemnumber, COALESCE(assign.pr_subitemnumber, 0) AS pr_subitemnumber, coalesce(to_number(tboxm.txm_lesson), to_number(tboxd.txd_lesson)) as lesson_number, appld.ap_ograde\\n\" +\n" +
            "            \"                FROM homeschoolhouse.student_assignments assign\\n\" +\n" +
            "            \"                JOIN homeschoolhouse.student_assignments_types types\\n\" +\n" +
            "            \"                  ON assign.assignment_type_fk = types.assignment_type_pk\\n\" +\n" +
            "            \"                JOIN linc.abadb_appld appld\\n\" +\n" +
            "            \"                  ON assign.apref = appld.ap_apref\\n\" +\n" +
            "            \"                LEFT JOIN linc.abadb_digital_assessments digAsmts\\n\" +\n" +
            "            \"                  ON assign.apref = digAsmts.application_number\\n\" +\n" +
            "            \"                 AND assign.subject = digAsmts.eno_subj\\n\" +\n" +
            "            \"                JOIN linc.abadb_tenop tenop\\n\" +\n" +
            "            \"                  ON assign.subject = tenop.eno_subj\\n\" +\n" +
            "            \"                LEFT JOIN linc.abadb_tboxh tboxh\\n\" +\n" +
            "            \"                  ON assign.pr_form = tboxh.txh_form\\n\" +\n" +
            "            \"                 AND assign.pr_version = tboxh.txh_ver\\n\" +\n" +
            "            \"                 AND assign.pr_school = tboxh.txh_school\\n\" +\n" +
            "            \"                 AND assign.pr_boxletter = tboxh.txh_boxltr\\n\" +\n" +
            "            \"                LEFT JOIN linc.abadb_tboxd tboxd\\n\" +\n" +
            "            \"                  ON tboxh.txh_form = tboxd.form_number\\n\" +\n" +
            "            \"                 AND tboxh.txh_school = tboxd.school\\n\" +\n" +
            "            \"                 AND tboxh.txh_boxltr = tboxd.box_letter\\n\" +\n" +
            "            \"                 AND tboxh.txh_ver = tboxd.version\\n\" +\n" +
            "            \"                 AND assign.pr_itemnumber = tboxd.txd_itmnbr\\n\" +\n" +
            "            \"                 AND coalesce(assign.pr_subitemnumber, 0) = 0\\n\" +\n" +
            "            \"                LEFT JOIN linc.abadb_tboxm tboxm\\n\" +\n" +
            "            \"                  ON tboxh.txh_form = tboxm.form_number\\n\" +\n" +
            "            \"                 AND tboxh.txh_school = tboxm.school\\n\" +\n" +
            "            \"                 AND tboxh.txh_boxltr = tboxm.box_letter\\n\" +\n" +
            "            \"                 AND tboxh.txh_ver = tboxm.version\\n\" +\n" +
            "            \"                 AND assign.pr_itemnumber = tboxm.txm_itmnbr\\n\" +\n" +
            "            \"                 AND assign.pr_subitemnumber = tboxm.txm_sub_itmnbr\\n\" +\n" +
            "            \"                LEFT JOIN linc.abadb_rchdr rchdr\\n\" +\n" +
            "            \"                  ON appld.ap_apref = rchdr.rch_apref\\n\" +\n" +
            "            \"               WHERE assign.student_id = 'STUDENT_ID_DATA'\\n\" +\n" +
            "            \"                 AND types.name IN('DIGITAL-ASSESSMENT', 'PE ACTIVITY', 'PE SKILLS TEST')\\n\" +\n" +
            "            \"                 AND coalesce(assign.grade_percentage, 0) != 333.3\\n\" +\n" +
            "            \"                 AND trunc(sysdate) BETWEEN coalesce(to_date(appld.ap_beg_dt,'yyyymmdd') - 14, digAsmts.start_date) AND coalesce(appld.ap_end_dt, digAsmts.end_date)\\n\" +\n" +
            "            \"                 AND coalesce(to_number(tboxm.txm_lesson), to_number(tboxd.txd_lesson)) BETWEEN coalesce(digAsmts.start_lesson, 1) AND coalesce(digAsmts.end_lesson, 180)\\n\" +\n" +
            "            \"                 AND appld.ap_status = 8\\n\" +\n" +
            "            \"                 AND (rchdr.rch_status IN ('A', 'I')\\n\" +\n" +
            "            \"                  OR  appld.ap_credt != 'Y')\\n\" +\n" +
            "            \"                 AND coalesce(tboxd.txd_da_testid, tboxm.txm_da_testid, 0) > 0 AND ap_ograde = 'Y'),\\n\" +\n" +
            "            \"              nextLessons AS (\\n\" +\n" +
            "            \"              SELECT DISTINCT assign.ap_grade, assign.ap_end_dt, assign.txh_subj, MIN(to_number(assign.lesson_number)) OVER (PARTITION BY assign.txh_subj) AS nextLesson\\n\" +\n" +
            "            \"                FROM studentAssignments assign\\n\" +\n" +
            "            \"               WHERE assign.completion_date IS NULL\\n\" +\n" +
            "            \"               GROUP BY assign.ap_grade, assign.ap_end_dt, assign.txh_subj, assign.lesson_number),\\n\" +\n" +
            "            \"              currentPELesson AS (\\n\" +\n" +
            "            \"              SELECT assign.ap_grade, assign.ap_end_dt, coalesce(assign.txh_subj, assign.subject) AS subject, assign.lesson_number AS currentLesson\\n\" +\n" +
            "            \"                FROM studentAssignments assign\\n\" +\n" +
            "            \"               WHERE trunc(assign.start_date) = trunc(sysdate)\\n\" +\n" +
            "            \"                 AND assign.name IN ('PE ACTIVITY', 'PE SKILLS TEST')),\\n\" +\n" +
            "            \"              streamSubjects AS (\\n\" +\n" +
            "            \"              SELECT subj.subjectid, subj.subjectname, subj.book_item_number, subj.image_url, subj.duration,\\n\" +\n" +
            "            \"                     subj.seriesno, subj.seriesversion, subj.rcg_subj,\\n\" +\n" +
            "            \"                     sTypes.supergroup, sTypes.sessionname, sTypes.sortorder\\n\" +\n" +
            "            \"                FROM homeschoolhouse.vsubjects subj\\n\" +\n" +
            "            \"                LEFT JOIN homeschoolhouse.vsessiontypes sTypes\\n\" +\n" +
            "            \"                  ON subj.subjectid = sTypes.subjectid\\n\" +\n" +
            "            \"               WHERE (sTypes.sessiontypeid = (SELECT max(sessiontypeid)\\n\" +\n" +
            "            \"                                                FROM homeschoolhouse.vsessiontypes sTypes2\\n\" +\n" +
            "            \"                                               WHERE sTypes2.subjectid = subj.subjectid\\n\" +\n" +
            "            \"                                                 AND coalesce(sTypes2.bonus_content, 'N') != 'Y')\\n\" +\n" +
            "            \"                  OR sTypes.sessiontypeid is null))\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"              SELECT assign.ap_grade, assign.ap_end_dt, assign.apref, assign.eventID, assign.start_date, assign.end_date, coalesce(CASE WHEN subj.supergroup IS NOT NULL then subj.subjectname ELSE coalesce(subj.sessionname, subj.subjectname) END, assign.eno_desc) AS short_description, coalesce(assign.txd_desc, assign.txm_desc, assign.description) AS long_description, assign.completion_date,\\n\" +\n" +
            "            \"                     box.description AS assignType, assign.segment_id_fk, linkitTestID, assign.da_testassignmentid, to_number(subj.book_item_number) AS book_item_number, subj.image_url,\\n\" +\n" +
            "            \"                     assign.subject, assign.pr_form, assign.pr_version, assign.pr_school, assign.pr_boxletter, assign.pr_itemnumber, coalesce(assign.pr_subitemnumber, 0) AS pr_subitemnumber,\\n\" +\n" +
            "            \"                     cast(coalesce(subj.sortorder, 99) AS NUMBER(3)) AS sortorder, assign.lesson_number, cast(coalesce(subj.subjectid, -1) AS NUMBER(10)) AS subject_id_fk,\\n\" +\n" +
            "            \"                     CASE\\n\" +\n" +
            "            \"                       WHEN nVL.nextVideoLesson > subj.duration THEN nVL.nextVideoLesson - subj.duration\\n\" +\n" +
            "            \"                       ELSE COALESCE(nVL.nextVideoLesson, 170)\\n\" +\n" +
            "            \"                     END AS nextVideoLesson, assign.ap_ograde\\n\" +\n" +
            "            \"                FROM studentAssignments assign\\n\" +\n" +
            "            \"                LEFT JOIN progress_report.pr_box_types box\\n\" +\n" +
            "            \"                  ON assign.txh_type = box.box_type\\n\" +\n" +
            "            \"                LEFT JOIN streamSubjects subj\\n\" +\n" +
            "            \"                  ON assign.eno_series = subj.seriesno\\n\" +\n" +
            "            \"                 AND assign.eno_ver = subj.seriesversion\\n\" +\n" +
            "            \"                 AND assign.txh_subj = subj.rcg_subj\\n\" +\n" +
            "            \"                LEFT JOIN nextLessons nLmL\\n\" +\n" +
            "            \"                  ON assign.txh_subj = nLmL.txh_subj\\n\" +\n" +
            "            \"                LEFT JOIN nextVideoLessons nVL\\n\" +\n" +
            "            \"                  ON assign.subject = nVL.subject\\n\" +\n" +
            "            \"                LEFT JOIN currentPELesson cPEL\\n\" +\n" +
            "            \"                  ON coalesce(assign.txh_subj, assign.subject) = cPEL.subject\\n\" +\n" +
            "            \"               WHERE (assign.lesson_number = CASE\\n\" +\n" +
            "            \"                                               WHEN coalesce(trim(assign.txh_type), 'PE') = 'PE' THEN cPEL.currentLesson\\n\" +\n" +
            "            \"                                               ELSE nLmL.nextLesson\\n\" +\n" +
            "            \"                                             END\\n\" +\n" +
            "            \"                  OR (trunc(coalesce(assign.completion_date, assign.da_enddate)) = trunc(sysdate)\\n\" +\n" +
            "            \"                 AND :digitalOnly != 'Y'))\\n\" +\n" +
            "            \"                 AND assign.completion_date IS NULL";

    String MY_TO_DO_LIST_AD_DB = "SELECT student_id AS STUDENT_ID, \n" +
            "       apref AS APPLICATION_ID,\n" +
            "       start_date AS START_DATE,\n" +
            "       long_description AS LONG_DESCRIPTION,\n" +
            "       short_description AS SUBJECT, \n" +
            "        CASE\n" +
            "            WHEN type_name = 'VIDEO' THEN 'Video'\n" +
            "            WHEN type_name = 'TBOXITEMS' THEN\n" +
            "                CASE\n" +
            "                    WHEN INSTR(LOWER(long_description), 'quiz') > 0 THEN 'Quiz'\n" +
            "                    WHEN INSTR(LOWER(long_description), 'test') > 0 THEN 'Test'\n" +
            "                    ELSE 'Lesson'\n" +
            "                END\n" +
            "            ELSE ''\n" +
            "        END AS Description\n" +
            "   FROM (SELECT assign.student_id, assign.apref, assign.assignmentid, coalesce(assign.da_status, 0) AS da_status,\n" +
            "                coalesce(assign.pr_form, 0) AS pr_form, coalesce(assign.pr_version, ' ') AS pr_version,\n" +
            "                coalesce(assign.pr_school, ' ') AS pr_school, coalesce(assign.pr_boxletter, ' ') AS pr_boxletter,\n" +
            "                coalesce(assign.pr_itemnumber, 0) AS pr_itemnumber, coalesce(assign.pr_subitemnumber, 0) AS\n" +
            "                pr_subitemnumber, coalesce(a.subject, ' ') AS subject, a.short_description, a.long_description,\n" +
            "                0 AS testid,\n" +
            "                to_char(start_date, 'YYYY-MM-DD') AS start_date,\n" +
            "                'T' || to_char(start_date, 'hh24:mi:ss') AS start_time,\n" +
            "                CASE WHEN assign.all_day = 'Y' THEN to_char(end_date + 1, 'YYYY-MM-DD')\n" +
            "                     ELSE to_char(end_date, 'YYYY-MM-DD')\n" +
            "                END AS end_date,\n" +
            "                'T' || to_char(end_date, 'hh24:mi:ss') AS end_time, assign.all_day, c.description AS category,\n" +
            "                c.categoryid,  coalesce(c.color,' ') AS color, a.lesson_number, a.lesson_number AS segmentlesson, 'Y'\n" +
            "                AS editable, completion_date, 'STUDENTNORMAL ' || coalesce(typ.name, '') AS type_name,\n" +
            "                student_assignments_id AS eventid, assign.subscription_items_fk, assign.subject_id_fk,\n" +
            "                assign.segment_id_fk, lock_assignment, 3 AS sort_order\n" +
            "           FROM homeschoolhouse.student_assignments assign\n" +
            "      LEFT JOIN homeschoolhouse.student_assignments_types typ\n" +
            "             ON typ.assignment_type_pk = assign.assignment_type_fk\n" +
            "           JOIN homeschoolhouse.assignments a\n" +
            "             ON assign.assignmentid = a.assignmentid\n" +
            "           JOIN homeschoolhouse.category c\n" +
            "             ON a.categoryid = c.categoryid\n" +
            "          WHERE assign.student_id = 'STUDENT_ID_DATA'\n" +
            "            AND start_date between to_date('START_DATE_DATA', 'YYYYMMDD') and to_date('END_DATE_DATA'||'235959',\n" +
            "                'YYYYMMDDhh24miss')\n" +
            "            AND c.categoryid != 3\n" +
            "          UNION\n" +
            "         SELECT to_number('STUDENT_ID_DATA') AS student_id, 0 AS apref, -1 AS assignmentid, 0 AS da_status,\n" +
            "                0 AS pr_form, ' ' AS pr_version, ' ' AS pr_school, ' ' AS pr_boxletter,\n" +
            "                0 AS pr_itemnumber, 0 AS pr_subitemnumber,\n" +
            "                ' ' AS subject, (SELECT Utilities.Format.ProperName(holiday) FROM dual) AS short_description, 'Enjoy\n" +
            "                the ' || (SELECT Utilities.Format.ProperName(holiday) FROM dual) || ' holiday!' AS long_description,\n" +
            "                0 AS testid,\n" +
            "                to_char(to_date(ho_beg_dt, 'YYYYMMDD'), 'YYYY-MM-DD') AS start_date,\n" +
            "                null AS start_time,\n" +
            "                to_char(to_date(ho_end_dt, 'YYYYMMDD') + 1, 'YYYY-MM-DD') AS end_date,\n" +
            "                null AS end_time,\n" +
            "                'Y' AS all_day, c.description AS category,\n" +
            "                c.categoryid,  coalesce(c.color,' ') AS color, 0 AS lesson_number, 0 AS segmentlesson, 'N' AS\n" +
            "                editable, null AS completion_date, 'HOLIDAY' AS type_name, 0 AS eventid,\n" +
            "                0 AS subscription_items_fk, 0 AS subject_id_fk, 0 AS segment_id_fk, ' ' AS lock_assignment, 4 AS\n" +
            "                sort_order\n" +
            "           FROM linc.abadb_holid\n" +
            "           JOIN homeschoolhouse.category c\n" +
            "             ON accountnumber in ('ACCOUNT_NUMBER_DATA', 1)\n" +
            "          WHERE description = 'Holiday'\n" +
            "            AND to_date(ho_beg_dt, 'YYYYMMDD') between to_date('START_DATE_DATA', 'YYYYMMDD') and\n" +
            "                to_date('END_DATE_DATA'||'235959', 'YYYYMMDDhh24miss')\n" +
            "          UNION\n" +
            "         SELECT assign.student_id, assign.apref, coalesce(assign.assignmentid,0), coalesce(assign.da_status, 0) AS\n" +
            "                da_status, coalesce(assign.pr_form, 0) AS pr_form, assign.pr_version, assign.pr_school,\n" +
            "                assign.pr_boxletter, assign.pr_itemnumber, coalesce(assign.pr_subitemnumber, 0) AS pr_subitemnumber,\n" +
            "                assign.subject,\n" +
            "                CASE WHEN tnp.eno_grade IN (1,2,3,4,5,6,14,15) THEN tsubj.description\n" +
            "                     ELSE tnp.eno_desc\n" +
            "                END AS short_description,\n" +
            "                replace(tboxd.txd_desc, '\"\"', '\\\"\"') || ' (Day ' || assign.lesson_number || ')' AS long_description,\n" +
            "                CASE WHEN ap_ograde = 'Y' THEN to_number(tboxd.txd_da_testid) ELSE 0 END AS testid,\n" +
            "                to_char(start_date, 'YYYY-MM-DD') AS start_date,\n" +
            "                'T' || to_char(start_date, 'hh24:mi:ss') AS start_time,\n" +
            "                to_char(end_date + 1, 'YYYY-MM-DD') AS end_date,\n" +
            "                'T' || to_char(end_date, 'hh24:mi:ss') AS end_time, 'Y' AS all_day,\n" +
            "                 c.description AS category,\n" +
            "                c.categoryid,  coalesce(c.color,' ') AS color, assign.lesson_number, assign.lesson_number AS\n" +
            "                segmentlesson, 'N' AS editable, completion_date, 'TBOXITEMS' AS type_name, student_assignments_id AS\n" +
            "                eventid, assign.subscription_items_fk, assign.subject_id_fk, assign.segment_id_fk, lock_assignment, 2\n" +
            "                AS sort_order\n" +
            "           FROM homeschoolhouse.student_assignments assign\n" +
            "           JOIN homeschoolhouse.category c\n" +
            "             ON accountnumber = 1\n" +
            "            AND upper(description) = 'ACADEMICS'\n" +
            "           JOIN linc.abadb_appld ap\n" +
            "             ON assign.apref = ap.ap_apref\n" +
            "           JOIN linc.abadb_applc applc\n" +
            "             ON ap.ap_apref = applc.apc_apref\n" +
            "            AND assign.subject = applc.ap_item\n" +
            "           JOIN linc.abadb_tenop tnp\n" +
            "             ON tnp.eno_subj = applc.ap_item\n" +
            "           JOIN enrollment.subjects tsubj\n" +
            "             ON tnp.eno_subj = tsubj.subject_version\n" +
            "           JOIN linc.abadb_tboxh tboxh\n" +
            "             ON ((tboxh.txh_subj = tsubj.subject AND\n" +
            "                 (ap.ap_grade = 15 OR ap.ap_grade <= 6))\n" +
            "             OR (tboxh.txh_subj = tnp.eno_rcard AND\n" +
            "                 ap.ap_grade BETWEEN 7 AND 12))\n" +
            "            AND assign.pr_form = tboxh.txh_form\n" +
            "            AND assign.pr_version = tboxh.txh_ver\n" +
            "            AND assign.pr_school = tboxh.txh_school\n" +
            "            AND assign.pr_boxletter = tboxh.txh_boxltr\n" +
            "           JOIN linc.abadb_tboxd tboxd\n" +
            "             ON tboxh.txh_form = tboxd.form_number\n" +
            "            AND tboxh.txh_school = tboxd.school\n" +
            "            AND tboxh.txh_boxltr = tboxd.box_letter\n" +
            "            AND tboxh.txh_ver = tboxd.version\n" +
            "            AND tboxd.txd_type != 'S'\n" +
            "            AND assign.pr_itemnumber = tboxd.txd_itmnbr\n" +
            "            AND CASE WHEN assign.pr_subitemnumber IS NULL THEN 0 ELSE assign.pr_subitemnumber END = 0\n" +
            "      LEFT JOIN linc.abadb_rchdr rchdr\n" +
            "             ON ap.ap_apref = rchdr.rch_apref\n" +
            "          WHERE assign.student_id = 'STUDENT_ID_DATA'\n" +
            "            AND start_date between to_date('START_DATE_DATA', 'YYYYMMDD') and to_date('END_DATE_DATA'||'235959',\n" +
            "                'YYYYMMDDhh24miss')\n" +
            "            AND ap.ap_status != 9\n" +
            "            AND (rchdr.rch_status IS NULL OR rchdr.rch_status != 'C')\n" +
            "          UNION\n" +
            "         SELECT assign.student_id, assign.apref, coalesce(assign.assignmentid,0) AS assignmentid,\n" +
            "                coalesce(assign.da_status, 0) AS da_status, coalesce(assign.pr_form, 0) AS pr_form, assign.pr_version,\n" +
            "                assign.pr_school, assign.pr_boxletter, assign.pr_itemnumber, assign.pr_subitemnumber, assign.subject,\n" +
            "                CASE WHEN tnp.eno_grade IN (1,2,3,4,5,6,14,15) THEN tsubj.description ELSE tnp.eno_desc END AS\n" +
            "                short_description, replace(tboxm.txm_desc, '\"\"', '\\\"\"') || ' (Day ' || tboxm.txm_lesson || ')' AS\n" +
            "                long_description,\n" +
            "                CASE WHEN ap_ograde = 'Y' THEN to_number(tboxm.txm_da_testid) ELSE 0 END AS testid,\n" +
            "                to_char(start_date, 'YYYY-MM-DD') AS start_date,\n" +
            "                'T' || to_char(start_date, 'hh24:mi:ss') AS start_time,\n" +
            "                to_char(end_date + 1, 'YYYY-MM-DD') AS end_date,\n" +
            "                'T' || to_char(end_date, 'hh24:mi:ss') AS end_time, 'Y' AS all_day,\n" +
            "                c.description AS category,\n" +
            "                c.categoryid,  coalesce(c.color,' ') AS color, assign.lesson_number, assign.lesson_number AS\n" +
            "                segmentlesson, 'N' AS editable, completion_date, 'TBOXITEMS' AS type_name, student_assignments_id AS\n" +
            "                eventid, assign.subscription_items_fk, assign.subject_id_fk, assign.segment_id_fk, lock_assignment, 2\n" +
            "                AS sort_order\n" +
            "           FROM homeschoolhouse.student_assignments assign\n" +
            "           JOIN homeschoolhouse.category c\n" +
            "             ON accountnumber = 1\n" +
            "            AND upper(description) = 'ACADEMICS'\n" +
            "           JOIN linc.abadb_appld ap\n" +
            "             ON assign.apref = ap.ap_apref\n" +
            "           JOIN linc.abadb_applc applc\n" +
            "             ON ap.ap_apref = applc.apc_apref\n" +
            "            AND assign.subject = applc.ap_item\n" +
            "           JOIN linc.abadb_tenop tnp\n" +
            "             ON tnp.eno_subj = applc.ap_item\n" +
            "           JOIN enrollment.subjects tsubj\n" +
            "             ON tnp.eno_subj = tsubj.subject_version\n" +
            "           JOIN linc.abadb_tboxh tboxh\n" +
            "             ON ((tboxh.txh_subj = tsubj.subject AND\n" +
            "                 (ap.ap_grade = 15 OR ap.ap_grade <= 6))\n" +
            "             OR (tboxh.txh_subj = tnp.eno_rcard AND\n" +
            "                 ap.ap_grade BETWEEN 7 AND 12))\n" +
            "            AND assign.pr_form = tboxh.txh_form\n" +
            "            AND assign.pr_version = tboxh.txh_ver\n" +
            "            AND assign.pr_school = tboxh.txh_school\n" +
            "            AND assign.pr_boxletter = tboxh.txh_boxltr\n" +
            "           JOIN linc.abadb_tboxm tboxm\n" +
            "             ON tboxh.txh_form = tboxm.form_number\n" +
            "            AND tboxh.txh_school = tboxm.school\n" +
            "            AND tboxh.txh_boxltr = tboxm.box_letter\n" +
            "            AND tboxh.txh_ver = tboxm.version\n" +
            "            AND assign.pr_itemnumber = tboxm.txm_itmnbr\n" +
            "            AND assign.pr_subitemnumber = tboxm.txm_sub_itmnbr\n" +
            "      LEFT JOIN linc.abadb_rchdr rchdr\n" +
            "             ON ap.ap_apref = rchdr.rch_apref\n" +
            "          WHERE assign.student_id = 'STUDENT_ID_DATA'\n" +
            "            AND start_date between to_date('START_DATE_DATA', 'YYYYMMDD') and to_date('END_DATE_DATA'||'235959',\n" +
            "                'YYYYMMDDhh24miss')\n" +
            "            AND ap.ap_status != 9\n" +
            "            AND (rchdr.rch_status IS NULL OR rchdr.rch_status != 'C')\n" +
            "          UNION\n" +
            "         SELECT assign.student_id, assign.apref, coalesce(assign.assignmentid,0) AS assignmentid,\n" +
            "                coalesce(assign.da_status, 0) AS da_status, coalesce(assign.pr_form, 0) AS pr_form, assign.pr_version,\n" +
            "                assign.pr_school, assign.pr_boxletter, assign.pr_itemnumber, assign.pr_subitemnumber, assign.subject,\n" +
            "                subj.subjectname AS short_Description,\n" +
            "                 'Video Lesson ' || segments.lesson || CASE WHEN TRIM(subj.subjectname) = TRIM(stype.sessionname) THEN\n" +
            "                '' ELSE ' - ' || coalesce(stype.sessionname, '') END AS long_description,\n" +
            "                null AS testid,\n" +
            "                to_char(assign.start_date, 'YYYY-MM-DD') AS start_date,\n" +
            "                'T' || to_char(assign.start_date, 'hh24:mi:ss') AS start_time,\n" +
            "                CASE WHEN coalesce(assign.all_day,'Y') = 'Y' THEN to_char(assign.end_date + 1, 'YYYY-MM-DD') ELSE\n" +
            "                     to_char(assign.end_date, 'YYYY-MM-DD') END AS end_date,\n" +
            "                'T' || to_char(assign.end_date, 'hh24:mi:ss') AS end_time, coalesce(assign.all_day,'Y') AS all_day,\n" +
            "                c.description AS category,\n" +
            "                c.categoryid,  coalesce(c.color,' ') AS color, assign.lesson_number, segments.lesson AS segmentlesson,\n" +
            "                'N' AS editable, completion_date, typ.name AS type_name, student_assignments_id AS eventid,\n" +
            "                assign.subscription_items_fk, assign.subject_id_fk, assign.segment_id_fk, lock_assignment, 1 AS\n" +
            "                sort_order\n" +
            "           FROM homeschoolhouse.student_assignments assign\n" +
            "           JOIN linc.abadb_appld ap\n" +
            "             ON assign.apref = ap.ap_apref\n" +
            "           JOIN homeschoolhouse.student_assignments_types typ\n" +
            "             ON typ.assignment_type_pk = assign.assignment_type_fk\n" +
            "           JOIN abashared.vlessonsegments@streamingDB segments\n" +
            "             ON segments.segmentid = assign.segment_id_fk\n" +
            "            AND segments.subjectid = assign.subject_id_fk\n" +
            "           JOIN homeschoolhouse.vsessiontypes stype\n" +
            "             ON stype.sessiontypeid = segments.sessiontypeid\n" +
            "           JOIN homeschoolhouse.vsubjects subj\n" +
            "             ON subj.subjectid = assign.subject_id_fk\n" +
            "           JOIN homeschoolhouse.category c\n" +
            "             ON c.accountnumber = 1\n" +
            "            AND upper(c.description) = 'ACADEMICS'\n" +
            "      LEFT JOIN linc.abadb_rchdr rchdr\n" +
            "             ON ap.ap_apref = rchdr.rch_apref\n" +
            "          WHERE assign.student_id = 'STUDENT_ID_DATA'\n" +
            "            AND typ.name IN ('VIDEO', 'DVD')\n" +
            "            AND assign.start_date between to_date('START_DATE_DATA', 'YYYYMMDD') and to_date('END_DATE_DATA'||'235959',\n" +
            "                'YYYYMMDDhh24miss')\n" +
            "            AND ap.ap_status != 9\n" +
            "            AND (rchdr.rch_status IS NULL OR rchdr.rch_status != 'C')\n" +
            "            AND (stype.pen_key = ap.pen_key OR stype.pen_key IS NULL)\n" +
            "          UNION\n" +
            "         SELECT assign.student_id, assign.apref, coalesce(assign.assignmentid,0) AS assignmentid,\n" +
            "                coalesce(assign.da_status, 0) AS da_status,\n" +
            "                coalesce(assign.pr_form, -1) AS pr_form, assign.pr_version, assign.pr_school, assign.pr_boxletter,\n" +
            "                assign.pr_itemnumber, assign.pr_subitemnumber, assign.subject,\n" +
            "                typ.name AS short_Description, typ.description AS long_description,\n" +
            "                null AS testid,\n" +
            "                to_char(assign.start_date, 'YYYY-MM-DD') AS start_date,\n" +
            "                'T' || to_char(assign.start_date, 'hh24:mi:ss') AS start_time,\n" +
            "                to_char(assign.end_date + 1, 'YYYY-MM-DD') AS end_date,\n" +
            "                'T' || to_char(assign.end_date, 'hh24:mi:ss') AS end_time, 'Y' AS all_day,\n" +
            "                c.description AS Category,\n" +
            "                c.categoryid, coalesce(c.color,' ') AS color, assign.lesson_number, assign.lesson_number AS\n" +
            "                segmentlesson, 'N' AS editable, COMPLETION_DATE, 'PE' AS type_name, student_assignments_id AS eventid,\n" +
            "                assign.subscription_items_fk, assign.subject_id_fk, assign.segment_id_fk, lock_assignment, 3 AS\n" +
            "                sort_order\n" +
            "           FROM homeschoolhouse.student_assignments assign\n" +
            "           JOIN linc.abadb_appld ap\n" +
            "             ON assign.apref = ap.ap_apref\n" +
            "           JOIN homeschoolhouse.student_assignments_types typ\n" +
            "             ON typ.assignment_type_pk = assign.assignment_type_fk\n" +
            "           JOIN homeschoolhouse.category c\n" +
            "             ON c.accountnumber = 1\n" +
            "            AND upper(c.description) = 'ACADEMICS'\n" +
            "      LEFT JOIN linc.abadb_rchdr rchdr\n" +
            "             ON ap.ap_apref = rchdr.rch_apref\n" +
            "          WHERE assign.student_id = 'STUDENT_ID_DATA'\n" +
            "            AND typ.name IN ('PE ACTIVITY', 'PE SKILLS TEST')\n" +
            "            AND assign.start_date between to_date('START_DATE_DATA', 'YYYYMMDD') and to_date('END_DATE_DATA'||'235959',\n" +
            "                'YYYYMMDDhh24miss')\n" +
            "            AND ap.ap_status != 9\n" +
            "            AND (rchdr.rch_status IS NULL OR rchdr.rch_status != 'C')            \n" +
            "       ORDER BY start_date ASC, short_description ASC, sort_order DESC)";

    String VIDEO_LIBRARY_VIDEOS_SD_DB = "SELECT DISTINCT * FROM TABLE (abashared.permissions_queries.getAvailableSubjects(LoginID, SubscriptionId))";
    String LOGIN_DETAILS_SD_DB = "SELECT * FROM abashared.user_logins WHERE lower(user_name) = lower(USER_NAME)";

}
