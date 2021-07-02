package constants;

public @interface DataBaseQueryConstant {
    String STUDENT_GRADE_WITH_SUBJECT_AD_DB = "/*Get student grade with subjects from AD DB*/\n" +
            "          SELECT tboxh.txh_subj, tnp.eno_desc || ' - ' || tboxd.txd_desc AS assessment,\n" +
            "                                                  CASE WHEN assign.grade_percentage = 777.7 OR assign.da_status IN (3, 5, 6) THEN 'Grade Pending' WHEN assign.da_status IN (1) THEN 'In Progress' ELSE to_char(assign.grade_percentage) || '%' END AS grade,\n" +
            "                                                  CASE WHEN assign.grade_percentage = 777.7 OR assign.da_status IN (1, 3, 5, 6) THEN '' ELSE lgrds.lgr_lgrd END AS lettergrade,\n" +
            "                                                  assign.completion_date, assign.da_grading_lockdate, assign.da_enddate\n" +
            "                                         FROM homeschoolhouse.student_assignments assign\n" +
            "                                             JOIN linc.abadb_tboxh tboxh\n" +
            "                                               ON assign.pr_form = tboxh.txh_form\n" +
            "                                              AND assign.pr_version = tboxh.txh_ver\n" +
            "                                              AND assign.pr_school = tboxh.txh_school\n" +
            "                                              AND assign.pr_boxletter = tboxh.txh_boxltr\n" +
            "                                             JOIN linc.abadb_tboxd tboxd\n" +
            "                                               ON tboxh.txh_form = tboxd.form_number\n" +
            "                                              AND tboxh.txh_school = tboxd.school\n" +
            "                                              AND tboxh.txh_boxltr = tboxd.box_letter\n" +
            "                                              AND tboxh.txh_ver = tboxd.version\n" +
            "                                              AND assign.pr_itemnumber = tboxd.txd_itmnbr\n" +
            "                                              AND CASE WHEN assign.pr_subitemnumber IS NULL THEN 0 ELSE assign.pr_subitemnumber END = 0\n" +
            "                                             JOIN linc.abadb_tenop tnp\n" +
            "                                               ON tboxh.txh_subj = tnp.eno_rcard\n" +
            "                                             JOIN linc.abadb_appld appld\n" +
            "                                               ON assign.apref = appld.ap_apref\n" +
            "                                             JOIN linc.abadb_applc applc\n" +
            "                                               ON appld.ap_apref = applc.apc_apref\n" +
            "                                              AND applc.ap_item = tnp.eno_subj\n" +
            "                                            INNER JOIN linc.abadb_grads grads\n" +
            "                                               ON appld.ap_grade = grads.gr_grade\n" +
            "                                              AND appld.ap_beg_dt BETWEEN grads.gr_beg_dt AND grads.gr_end_dt\n" +
            "                                             LEFT OUTER JOIN linc.abadb_lgrds lgrds\n" +
            "                                               ON grads.gr_scale = lgrds.lgr_scale\n" +
            "                                              AND CASE WHEN assign.grade_percentage = 0 THEN 1 ELSE assign.grade_percentage END BETWEEN lgrds.lgr_min_gr AND lgrds.lgr_max_gr\n" +
            "                                            WHERE assign.student_id = 'STUDENT_ID_DATA'\n" +
            "                                              AND appld.ap_end_dt >= trunc(sysdate)\n" +
            "                                              AND assign.da_enddate is not null\n" +
            "                                              AND coalesce(assign.da_grading_lockdate, CASE WHEN da_status NOT IN(7,8) THEN sysdate ELSE assign.da_enddate END) >= sysdate - 'ROW_COUNT_DATA'\n" +
            "                                              AND tboxd.txd_da_testid > 0\n" +
            "                                            UNION\n" +
            "                                           SELECT tboxh.txh_subj, tnp.eno_desc || ' - ' || tboxm.txm_desc AS assessment,\n" +
            "                                                  CASE WHEN assign.grade_percentage = 777.7 OR assign.da_status IN (1, 3, 5, 6) THEN 'Grade Pending' WHEN assign.da_status IN (1) THEN 'In Progress' ELSE to_char(assign.grade_percentage) || '%' END AS grade,\n" +
            "                                                  CASE WHEN assign.grade_percentage = 777.7 OR assign.da_status IN (1, 3, 5, 6) THEN '' ELSE lgrds.lgr_lgrd END AS lettergrade,\n" +
            "                                                  assign.completion_date, assign.da_grading_lockdate, assign.da_enddate\n" +
            "                                             FROM homeschoolhouse.student_assignments assign\n" +
            "                                             JOIN linc.abadb_tboxh tboxh\n" +
            "                                               ON assign.pr_form = tboxh.txh_form\n" +
            "                                              AND assign.pr_version = tboxh.txh_ver\n" +
            "                                              AND assign.pr_school = tboxh.txh_school\n" +
            "                                              AND assign.pr_boxletter = tboxh.txh_boxltr\n" +
            "                                             JOIN linc.abadb_tboxm tboxm\n" +
            "                                               ON tboxh.txh_form = tboxm.form_number\n" +
            "                                              AND tboxh.txh_school = tboxm.school\n" +
            "                                              AND tboxh.txh_boxltr = tboxm.box_letter\n" +
            "                                              AND tboxh.txh_ver = tboxm.version\n" +
            "                                              AND assign.pr_itemnumber = tboxm.txm_itmnbr\n" +
            "                                              AND assign.pr_subitemnumber = tboxm.txm_sub_itmnbr\n" +
            "                                             JOIN linc.abadb_tenop tnp\n" +
            "                                               ON tboxh.txh_subj = tnp.eno_rcard\n" +
            "                                             JOIN linc.abadb_appld appld\n" +
            "                                               ON assign.apref = appld.ap_apref\n" +
            "                                             JOIN linc.abadb_applc applc\n" +
            "                                               ON appld.ap_apref = applc.apc_apref\n" +
            "                                              AND applc.ap_item = tnp.eno_subj\n" +
            "                                            INNER JOIN linc.abadb_grads grads\n" +
            "                                               ON appld.ap_grade = grads.gr_grade\n" +
            "                                              AND appld.ap_beg_dt BETWEEN grads.gr_beg_dt AND grads.gr_end_dt\n" +
            "                                             LEFT OUTER JOIN linc.abadb_lgrds lgrds\n" +
            "                                               ON grads.gr_scale = lgrds.lgr_scale\n" +
            "                                              AND CASE WHEN assign.grade_percentage = 0 THEN 1 ELSE assign.grade_percentage END BETWEEN lgrds.lgr_min_gr AND lgrds.lgr_max_gr\n" +
            "                                            WHERE assign.student_id = 'STUDENT_ID_DATA'\n" +
            "                                              AND appld.ap_end_dt >= trunc(sysdate)\n" +
            "                                              AND assign.da_enddate is not null\n" +
            "                                              AND coalesce(assign.da_grading_lockdate, CASE WHEN da_status NOT IN(7,8) THEN sysdate ELSE assign.da_enddate END) >= sysdate - 'ROW_COUNT_DATA'\n" +
            "                                              AND tboxm.txm_da_testid > 0\n" +
            "                                            ORDER BY completion_date";

    String MY_LESSONS_TODAY_SD_DB = "/*Ge my lessons today from SD DB*/\n" +
            "WITH\n" +
            "    studentAssignments AS (SELECT *\n" +
            "                             FROM homeschoolhouse.student_assignments@abadb assign\n" +
            "                             JOIN homeschoolhouse.student_assignments_types@abadb types\n" +
            "                               ON assign.assignment_type_fk = types.assignment_type_pk\n" +
            "                            WHERE assign.student_id = 'STUDENT_ID_DATA'\n" +
            "                              AND types.name IN('DVD', 'VIDEO')),\n" +
            "    videoAssignments AS (SELECT ap_grade, ap_end_dt, eventID, apref, start_date, end_date, completion_date, lesson_number, lock_assignment, segment_id_fk, subject, subject_id_fk, assignType,\n" +
            "                                short_description, long_description, da_testassignmentid, pr_form, pr_version, pr_school, pr_boxletter, pr_itemnumber, pr_subitemnumber, grade,\n" +
            "                                book_item_number, image_url, ap_credt, teacher, sub_start_date, sortorder, sessionsrequired, requirementsRemaining,\n" +
            "                                CASE\n" +
            "                                    WHEN SUM(requirementsRemaining) OVER (PARTITION BY subject_id_fk, lesson_number) = 0 THEN 'Y'\n" +
            "                                    ELSE 'N'\n" +
            "                                END AS IsRequirementMet,\n" +
            "                                subscription_number_pk, latest_point, restart_at, completed\n" +
            "                           FROM (SELECT ap_grade, ap_end_dt, eventid, apref, start_date, end_date, completion_date, lesson_number, lock_assignment, segment_id_fk, subject, subject_id_fk, assignType,\n" +
            "                                        short_description, long_description, da_testassignmentid, pr_form, pr_version, pr_school, pr_boxletter, pr_itemnumber, pr_subitemnumber, grade,\n" +
            "                                        book_item_number, image_url, ap_credt, teacher, sub_start_date, sortorder,\n" +
            "                                        sessionsrequired, sessionoptioncount, min(sessionRemaining) OVER (PARTITION BY subject_id_fk, supergroup, lesson_number) AS requirementsRemaining,\n" +
            "                                        subscription_number_pk, latest_point, restart_at, completed\n" +
            "                                   FROM (SELECT ap_grade, ap_end_dt, assign.student_assignments_id AS eventID, assign.apref, assign.start_date, assign.end_date, assign.completion_date, segments.lesson AS lesson_number,\n" +
            "                                                assign.lock_assignment, assign.segment_id_fk, assign.subject, assign.subject_id_fk,\n" +
            "                                                CASE mediatypes.name\n" +
            "                                                    WHEN 'STREAMING' THEN 'LESSON'\n" +
            "                                                    ELSE 'DVD'\n" +
            "                                                END AS assignType,\n" +
            "                                                sessions.sessionname AS short_description, 'Lesson ' || segments.lesson AS long_description, assign.da_testassignmentid,\n" +
            "                                                assign.pr_form, assign.pr_version, assign.pr_school, assign.pr_boxletter, assign.pr_itemnumber, COALESCE(assign.pr_subitemnumber, 0) AS pr_subitemnumber,\n" +
            "                                                subjects.grade, to_number(subjects.book_item_number) AS book_item_number, subjects.image_url, appld.ap_credt,\n" +
            "                                                CASE\n" +
            "                                                    WHEN NVL(title.ttitl_nbr, 0) IN (3, 9, 10, 20, 21) THEN NVL(title.ttitledesc,'') || ' ' || NVL(teacher.teacher_last_name,'')\n" +
            "                                                    ELSE NVL(title.ttitledesc,'') || '. ' || NVL(teacher.teacher_last_name,'')\n" +
            "                                                END AS teacher, subs.start_date AS sub_start_date, sessions.sortorder, COALESCE(sessions.supergroup, sessions.sessiontype) AS supergroup, segments.sessionsrequired, segments.sessionoptioncount,\n" +
            "                                                CASE\n" +
            "                                                    WHEN assign.completion_date IS NOT NULL THEN 0\n" +
            "                                                    ELSE 1\n" +
            "                                                END AS sessionRemaining, subs.subscription_number_pk, COALESCE(uitems.latest_point, 0) AS latest_point, COALESCE(uitems.restart_at, 0) AS restart_at,\n" +
            "                                                CASE\n" +
            "                                                    WHEN uitems.completed IS NULL THEN\n" +
            "                                                        CASE\n" +
            "                                                            WHEN assign.completion_date IS NOT NULL THEN 'Y'\n" +
            "                                                            ELSE 'N'\n" +
            "                                                        END\n" +
            "                                                    ELSE COALESCE(uitems.completed, 'N')\n" +
            "                                                END AS completed\n" +
            "                                           FROM studentAssignments assign\n" +
            "                                           JOIN abadb.appld\n" +
            "                                             ON assign.apref = appld.ap_apref\n" +
            "                                           JOIN abashared.vlessonsegmentrequirements segments\n" +
            "                                             ON assign.segment_id_fk = segments.segmentid\n" +
            "                                           JOIN abashared.vsessiontypes sessions\n" +
            "                                             ON segments.sessiontypeid = sessions.sessiontypeid\n" +
            "                                           JOIN abashared.vsubjects subjects\n" +
            "                                             ON subjects.subjectid = assign.subject_id_fk\n" +
            "                                           -- streaming only? use JOIN\n" +
            "                                           -- with DVDs? use LEFT JOIN\n" +
            "                                           JOIN abashared.streaming_subscriptions subs\n" +
            "                                             ON assign.apref = subs.application_number\n" +
            "                                           -- streaming only? use JOIN\n" +
            "                                           -- with DVDs? use LEFT JOIN                                    \n" +
            "                                           JOIN abashared.streaming_subscriptions_items subitems\n" +
            "                                             ON subs.subscription_number_pk = subitems.subscription_number_fk\n" +
            "                                            AND subitems.subject_id_fk = assign.subject_id_fk\n" +
            "                                      LEFT JOIN abashared.streaming_users_items uitems\n" +
            "                                             ON uitems.subscription_items_fk = subitems.subscription_items_pk\n" +
            "                                            AND uitems.segment_id_fk = segments.segmentid\n" +
            "                                      LEFT JOIN abashared.teacher_subjects teacher_sbj\n" +
            "                                             ON teacher_sbj.subject_id_fk = segments.subjectid\n" +
            "                                      LEFT JOIN abashared.teachers teacher\n" +
            "                                             ON teacher.teacher_pk = teacher_sbj.teacher_pk_fk\n" +
            "                                           JOIN abadb.ttitl title\n" +
            "                                             ON title.ttitl_nbr = teacher.teacher_title\n" +
            "                                      LEFT JOIN abashared.unfinished_rchdr urchdr\n" +
            "                                             ON appld.ap_apref = urchdr.rch_apref\n" +
            "                                           JOIN enrollment.media_format_types@abadb mediatypes\n" +
            "                                             ON mediatypes.media_format_type_id = appld.media_format_type_id\n" +
            "                                          WHERE (subs.subscription_status_fk = 1\n" +
            "                                                    OR subs.subscription_status_fk IS NULL\n" +
            "                                                    OR mediatypes.name != 'STREAMING')\n" +
            "                                            AND (sessions.subject_group = subitems.subject_group\n" +
            "                                                    OR subitems.subject_group IS NULL\n" +
            "                                                    OR sessions.subject_group IS NULL\n" +
            "                                                    OR uitems.completed = 'Y')\n" +
            "                                            AND (TRUNC(SYSDATE)  BETWEEN COALESCE(subs.start_date, to_date(appld.ap_beg_dt, 'yyyymmdd') - 14, trunc(sysdate)) AND COALESCE(subs.end_date, appld.ap_end_dt, trunc(sysdate))\n" +
            "                                                    OR (mediatypes.name != 'STREAMING' AND (TRUNC(SYSDATE) BETWEEN to_date(appld.ap_beg_dt, 'yyyymmdd') - 14  AND COALESCE(appld.ap_end_dt, trunc(sysdate)) ) ) )\n" +
            "                                            AND (segments.lesson BETWEEN subitems.start_lesson_number and subitems.end_lesson_number\n" +
            "                                                    OR subs.subscription_status_fk IS NULL\n" +
            "                                                    OR mediatypes.name != 'STREAMING')\n" +
            "                                            AND (urchdr.rch_status IN ('A', 'I')\n" +
            "                                                    OR appld.ap_credt != 'Y')\n" +
            "                                            AND (sessions.pen_key = appld.pen_key OR sessions.pen_key IS NULL)\n" +
            "                                            AND appld.ap_status = 8\n" +
            "                                        )\n" +
            "                                )\n" +
            "                        ),\n" +
            "    nextLessonsMinLesson AS (SELECT DISTINCT assign.ap_end_dt, assign.ap_grade, assign.subject_id_fk, assign.apref, MIN(assign.lesson_number) OVER(PARTITION BY assign.subject_id_fk) AS nextLesson, MIN(assign.lesson_number) OVER(PARTITION BY assign.apref) AS minLesson\n" +
            "                               FROM videoAssignments assign\n" +
            "                              WHERE assign.completion_date IS NULL\n" +
            "                                AND assign.IsRequirementMet = 'N'\n" +
            "                           GROUP BY assign.ap_grade, assign.ap_end_dt, assign.subject_id_fk, assign.apref, assign.lesson_number),\n" +
            "    lastCompletedLessons AS (SELECT DISTINCT assign.ap_grade, assign.ap_end_dt, assign.subject_id_fk, MAX(assign.lesson_number) OVER(PARTITION BY assign.subject_id_fk) AS lastCompletedLesson\n" +
            "                               FROM videoAssignments assign\n" +
            "                              WHERE assign.completion_date IS NOT NULL\n" +
            "                                AND assign.IsRequirementMet = 'Y'\n" +
            "                           GROUP BY assign.ap_grade, assign.ap_end_dt, assign.subject_id_fk, assign.lesson_number)\n" +
            "                           \n" +
            "    SELECT vAssign.ap_grade, vAssign.ap_end_dt, vAssign.apref, vAssign.eventID AS eventID, vAssign.start_date, vAssign.end_date, vAssign.short_description, vAssign.long_description, vAssign.completion_date,\n" +
            "           vAssign.assignType, CAST(vAssign.subject_id_fk AS NUMBER(10)) AS subject_id_fk, vAssign.segment_id_fk, vAssign.subject, 0 AS linkitTestID, vAssign.da_testassignmentid, vAssign.book_item_number, vAssign.image_url, vAssign.lock_assignment,\n" +
            "           vAssign.pr_form, vAssign.pr_version, vAssign.pr_school, vAssign.pr_boxletter, vAssign.pr_itemnumber, vAssign.pr_subitemnumber, CAST(vAssign.sortorder AS NUMBER(3)) AS sortorder, vAssign.lesson_number,\n" +
            "           vAssign.subscription_number_pk, vAssign.restart_at, vAssign.latest_point, vAssign.completed, vAssign.teacher, vAssign.ap_credt, 'N' AS lockedlesson,\n" +
            "           vAssign.requirementsRemaining, vAssign.IsRequirementMet\n" +
            "      FROM videoAssignments vAssign\n" +
            " LEFT JOIN lastCompletedLessons lCL\n" +
            "        ON vAssign.subject_id_fk = lCL.subject_id_fk\n" +
            " LEFT JOIN nextLessonsMinLesson nLML\n" +
            "        ON vAssign.subject_id_fk = nLML.subject_id_fk\n" +
            "       AND vAssign.apref = nLML.apref\n" +
            "     WHERE vAssign.lesson_number = CASE\n" +
            "                                       WHEN (vAssign.grade IN(14,15,1,2,3,4,5,6) AND 'N' = ' ') OR 'N' = 'Y' THEN nLmL.minLesson\n" +
            "                                       WHEN (vAssign.grade IN(7,8,9,10,11,12) AND 'N' = ' ') OR 'N' = 'N' THEN nLmL.nextLesson\n" +
            "                                   END\n" +
            "        OR (vAssign.grade IN(7,8,9,10,11,12) AND trunc(vAssign.completion_date) = trunc(sysdate) )\n" +
            "  ORDER BY vAssign.completion_date desc, vAssign.sub_start_date, vAssign.sortorder, vAssign.lesson_number";

    /**
     * Fetching Student's digital assessment details
     * .replaceAll(TableColumn.STUDENT_ID_DATA,studentID)
     */
    String GET_DIGITAL_ONLY_ASSESSMENT_DETAILS_AD_DB = "/*Get digital only assessment details from AD DB*/\n" +
            "WITH\n" +
            "      nextVideoLessons AS (\n" +
            "        SELECT DISTINCT appld.ap_grade, appld.ap_end_dt, assign.apref, assign.subject, min(lessonInfo.lesson) OVER (PARTITION BY assign.subject, assign.apref) AS nextVideoLesson \n" +
            "          FROM homeschoolhouse.student_assignments assign\n" +
            "          JOIN homeschoolhouse.student_assignments_types types\n" +
            "            ON assign.assignment_type_fk = types.assignment_type_pk\n" +
            "          JOIN homeschoolhouse.lesson_information lessonInfo\n" +
            "            ON assign.segment_id_fk = lessonInfo.segmentid\n" +
            "           AND assign.apref = lessonInfo.application_number\n" +
            "           AND assign.subject_id_fk = lessonInfo.subject_id_fk\n" +
            "          JOIN linc.abadb_appld appld\n" +
            "            ON assign.apref = appld.ap_apref\n" +
            "           AND appld.fd_id = 'STUDENT_ID_DATA'\n" +
            "          LEFT JOIN linc.abadb_rchdr rchdr\n" +
            "            ON appld.ap_apref = rchdr.rch_apref\n" +
            "           AND rchdr.rch_id = 'STUDENT_ID_DATA'\n" +
            "         WHERE assign.student_id = 'STUDENT_ID_DATA'\n" +
            "           AND appld.ap_status = 8\n" +
            "           AND trunc(appld.ap_end_dt) >= trunc(sysdate)\n" +
            "           AND assign.completion_date IS NULL\n" +
            "           AND lessonInfo.lesson BETWEEN lessonInfo.start_lesson_number AND lessonInfo.end_lesson_number\n" +
            "           AND types.name = 'VIDEO'\n" +
            "           AND (rchdr.rch_status IN ('A', 'I')\n" +
            "            OR  appld.ap_credt != 'Y')\n" +
            "         GROUP BY appld.ap_grade, appld.ap_end_dt, assign.subject, lessonInfo.lesson, assign.apref\n" +
            "      ),\n" +
            "      studentAssignments AS (\n" +
            "        SELECT appld.ap_grade, appld.ap_end_dt, assign.apref, assign.student_assignments_id AS eventID, assign.start_date, assign.end_date, tenop.eno_desc, tboxd.txd_desc, tboxm.txm_desc, types.description, assign.completion_date, assign.lock_assignment,\n" +
            "               assign.da_enddate,tboxh.txh_type, assign.segment_id_fk, coalesce(to_number(assign.da_testid), tboxd.txd_da_testid, tboxm.txm_da_testid, 0) AS linkitTestID, assign.da_testassignmentid, types.name, tenop.eno_ver, tenop.eno_series,\n" +
            "               tboxh.txh_subj, assign.subject, assign.pr_form, assign.pr_version, assign.pr_school, assign.pr_boxletter, assign.pr_itemnumber, COALESCE(assign.pr_subitemnumber, 0) AS pr_subitemnumber, coalesce(to_number(tboxm.txm_lesson), to_number(tboxd.txd_lesson)) as lesson_number, appld.ap_ograde\n" +
            "          FROM homeschoolhouse.student_assignments assign\n" +
            "          JOIN homeschoolhouse.student_assignments_types types\n" +
            "            ON assign.assignment_type_fk = types.assignment_type_pk\n" +
            "          JOIN linc.abadb_appld appld\n" +
            "            ON assign.apref = appld.ap_apref\n" +
            "           AND appld.fd_id = 'STUDENT_ID_DATA'\n" +
            "          LEFT JOIN linc.abadb_digital_assessments digAsmts\n" +
            "            ON assign.apref = digAsmts.application_number\n" +
            "           AND assign.subject = digAsmts.eno_subj\n" +
            "          JOIN linc.abadb_tenop tenop\n" +
            "            ON assign.subject = tenop.eno_subj\n" +
            "          LEFT JOIN linc.abadb_tboxh tboxh\n" +
            "            ON assign.pr_form = tboxh.txh_form\n" +
            "           AND assign.pr_version = tboxh.txh_ver\n" +
            "           AND assign.pr_school = tboxh.txh_school\n" +
            "           AND assign.pr_boxletter = tboxh.txh_boxltr\n" +
            "          LEFT JOIN linc.abadb_tboxd tboxd\n" +
            "            ON tboxh.txh_form = tboxd.form_number\n" +
            "           AND tboxh.txh_school = tboxd.school\n" +
            "           AND tboxh.txh_boxltr = tboxd.box_letter\n" +
            "           AND tboxh.txh_ver = tboxd.version\n" +
            "           AND assign.pr_itemnumber = tboxd.txd_itmnbr\n" +
            "           AND coalesce(assign.pr_subitemnumber, 0) = 0\n" +
            "          LEFT JOIN linc.abadb_tboxm tboxm\n" +
            "            ON tboxh.txh_form = tboxm.form_number\n" +
            "           AND tboxh.txh_school = tboxm.school\n" +
            "           AND tboxh.txh_boxltr = tboxm.box_letter\n" +
            "           AND tboxh.txh_ver = tboxm.version\n" +
            "           AND assign.pr_itemnumber = tboxm.txm_itmnbr\n" +
            "           AND assign.pr_subitemnumber = tboxm.txm_sub_itmnbr\n" +
            "          LEFT JOIN linc.abadb_rchdr rchdr\n" +
            "            ON appld.ap_apref = rchdr.rch_apref\n" +
            "           AND rchdr.rch_id = 'STUDENT_ID_DATA'\n" +
            "         WHERE assign.student_id = 'STUDENT_ID_DATA'\n" +
            "           AND types.name IN('DIGITAL-ASSESSMENT', 'PE ACTIVITY', 'PE SKILLS TEST')\n" +
            "           AND coalesce(assign.grade_percentage, 0) != 333.3\n" +
            "           AND trunc(sysdate) BETWEEN coalesce(to_date(appld.ap_beg_dt,'yyyymmdd') - 14, digAsmts.start_date) AND coalesce(appld.ap_end_dt, digAsmts.end_date)\n" +
            "           AND coalesce(to_number(tboxm.txm_lesson), to_number(tboxd.txd_lesson)) BETWEEN coalesce(digAsmts.start_lesson, 1) AND coalesce(digAsmts.end_lesson, 180)\n" +
            "           AND appld.ap_status = 8\n" +
            "           AND (rchdr.rch_status IN ('A', 'I')\n" +
            "            OR  appld.ap_credt != 'Y')\n" +
            "           AND ('Y' != 'Y' OR (coalesce(tboxd.txd_da_testid, tboxm.txm_da_testid, 0) > 0 AND ap_ograde = 'Y'))\n" +
            "      ),\n" +
            "      nextLessons AS (\n" +
            "        SELECT DISTINCT assign.ap_grade, assign.ap_end_dt, assign.txh_subj, MIN(to_number(assign.lesson_number)) OVER (PARTITION BY assign.txh_subj) AS nextLesson\n" +
            "          FROM studentAssignments assign\n" +
            "         WHERE assign.completion_date IS NULL\n" +
            "         GROUP BY assign.ap_grade, assign.ap_end_dt, assign.txh_subj, assign.lesson_number\n" +
            "      ),\n" +
            "      currentPELesson AS (\n" +
            "        SELECT assign.ap_grade, assign.ap_end_dt, coalesce(assign.txh_subj, assign.subject) AS subject, assign.lesson_number AS currentLesson\n" +
            "          FROM studentAssignments assign\n" +
            "         WHERE trunc(assign.start_date) = trunc(sysdate)\n" +
            "           AND assign.name IN ('PE ACTIVITY', 'PE SKILLS TEST')\n" +
            "      ),\n" +
            "      streamSubjects AS (\n" +
            "        SELECT subj.subjectid, subj.subjectname, subj.book_item_number, subj.image_url, subj.duration,\n" +
            "               subj.seriesno, subj.seriesversion, subj.rcg_subj,\n" +
            "               sTypes.supergroup, sTypes.sessionname, sTypes.sortorder\n" +
            "          FROM homeschoolhouse.vsubjects subj\n" +
            "          LEFT JOIN homeschoolhouse.vsessiontypes sTypes\n" +
            "            ON subj.subjectid = sTypes.subjectid\n" +
            "         WHERE (sTypes.sessiontypeid = (SELECT max(sessiontypeid)\n" +
            "                                          FROM homeschoolhouse.vsessiontypes sTypes2\n" +
            "                                         WHERE sTypes2.subjectid = subj.subjectid\n" +
            "                                           AND coalesce(sTypes2.bonus_content, 'N') != 'Y')\n" +
            "            OR sTypes.sessiontypeid is null)\n" +
            "      )\n" +
            "\n" +
            " \n" +
            "\n" +
            "      SELECT assign.ap_grade, assign.ap_end_dt, assign.apref, assign.eventID, assign.start_date, assign.end_date, coalesce(CASE WHEN subj.supergroup IS NOT NULL then subj.subjectname ELSE coalesce(subj.sessionname, subj.subjectname) END, assign.eno_desc) AS short_description, coalesce(assign.txd_desc, assign.txm_desc, assign.description) AS long_description, assign.completion_date, assign.lock_assignment,\n" +
            "             box.description AS assignType, assign.segment_id_fk, linkitTestID, assign.da_testassignmentid, to_number(subj.book_item_number) AS book_item_number, subj.image_url,\n" +
            "             assign.subject, assign.pr_form, assign.pr_version, assign.pr_school, assign.pr_boxletter, assign.pr_itemnumber, coalesce(assign.pr_subitemnumber, 0) AS pr_subitemnumber,\n" +
            "             cast(coalesce(subj.sortorder, 99) AS NUMBER(3)) AS sortorder, assign.lesson_number, cast(coalesce(subj.subjectid, -1) AS NUMBER(10)) AS subject_id_fk,\n" +
            "             CASE\n" +
            "               WHEN nVL.nextVideoLesson > subj.duration THEN nVL.nextVideoLesson - subj.duration\n" +
            "               ELSE COALESCE(nVL.nextVideoLesson, 170)\n" +
            "             END AS nextVideoLesson, assign.ap_ograde\n" +
            "        FROM studentAssignments assign\n" +
            "        LEFT JOIN progress_report.pr_box_types box\n" +
            "          ON assign.txh_type = box.box_type\n" +
            "        LEFT JOIN streamSubjects subj\n" +
            "          ON assign.eno_series = subj.seriesno\n" +
            "         AND assign.eno_ver = subj.seriesversion\n" +
            "         AND assign.txh_subj = subj.rcg_subj\n" +
            "        LEFT JOIN nextLessons nLmL\n" +
            "          ON assign.txh_subj = nLmL.txh_subj\n" +
            "        LEFT JOIN nextVideoLessons nVL\n" +
            "          ON assign.subject = nVL.subject\n" +
            "        LEFT JOIN currentPELesson cPEL\n" +
            "          ON coalesce(assign.txh_subj, assign.subject) = cPEL.subject\n" +
            "       WHERE (assign.lesson_number = CASE\n" +
            "                                       WHEN coalesce(trim(assign.txh_type), 'PE') = 'PE' THEN cPEL.currentLesson\n" +
            "                                       ELSE nLmL.nextLesson\n" +
            "                                     END\n" +
            "          OR (trunc(coalesce(assign.completion_date, assign.da_enddate)) = trunc(sysdate)\n" +
            "         AND 'Y' != 'Y'))\n" +
            "         AND assign.completion_date IS NULL\n" +
            "         order by sortorder asc";


    /**
     * Fetching Student's digital assessment details
     * .replaceAll(TableColumn.STUDENT_ID_DATA,studentID)
     */
    String DIGITAL_ONLY_ASSESSMENT_DETAILS_AD_DB = "/*Get digital only assessment details from AD DB*/\n" +
            "SELECT ap_grade,\n" +
            "       apref,\n" +
            "       eventid,\n" +
            "       short_description AS SUBJECT,\n" +
            "       long_description AS LESSON,\n" +
            "       mediaformatname,\n" +
            "       nextVideoLesson,\n" +
            "       lesson_number,\n" +
            "       lock_assignment,\n" +
            "       CASE\n" +
            "           WHEN lock_assignment = 'Y' THEN 'Y'\n" +
            "           WHEN TO_NUMBER(nextVideoLesson) >= TO_NUMBER(lesson_number) THEN 'N'\n" +
            "           ELSE 'Y'\n" +
            "       END AS locked\n" +
            "  FROM (\n" +
            "\n" +
            "WITH\n" +
            "    nextVideoLessons AS (\n" +
            "        SELECT DISTINCT appld.ap_grade, appld.ap_end_dt, assign.apref, assign.subject, min(lessonInfo.lesson) OVER \n" +
            "                        (PARTITION BY assign.subject, assign.apref) AS nextVideoLesson\n" +
            "                   FROM homeschoolhouse.student_assignments assign\n" +
            "                   JOIN homeschoolhouse.student_assignments_types types\n" +
            "                     ON assign.assignment_type_fk = types.assignment_type_pk\n" +
            "                   JOIN homeschoolhouse.lesson_information lessonInfo\n" +
            "                     ON assign.segment_id_fk = lessonInfo.segmentid\n" +
            "                    AND assign.apref = lessonInfo.application_number\n" +
            "                    AND assign.subject_id_fk = lessonInfo.subject_id_fk\n" +
            "                   JOIN linc.abadb_appld appld\n" +
            "                     ON assign.apref = appld.ap_apref\n" +
            "              LEFT JOIN linc.abadb_rchdr rchdr\n" +
            "                     ON appld.ap_apref = rchdr.rch_apref\n" +
            "                  WHERE assign.student_id = 'STUDENT_ID_DATA'\n" +
            "                    AND appld.ap_status = 8\n" +
            "                    AND trunc(appld.ap_end_dt) >= trunc(sysdate)\n" +
            "                    AND assign.completion_date IS NULL\n" +
            "                    AND lessonInfo.lesson BETWEEN lessonInfo.start_lesson_number AND lessonInfo.end_lesson_number\n" +
            "                    AND types.name = 'VIDEO'\n" +
            "                    AND (rchdr.rch_status IN ('A', 'I')\n" +
            "                     OR appld.ap_credt != 'Y')\n" +
            "               GROUP BY appld.ap_grade, appld.ap_end_dt, assign.subject, lessonInfo.lesson, assign.apref\n" +
            "              ),\n" +
            "              studentAssignments AS (\n" +
            "              SELECT appld.ap_grade, appld.ap_end_dt, assign.apref, assign.student_assignments_id AS eventID, \n" +
            "                     assign.start_date, assign.end_date, tenop.eno_desc, tboxd.txd_desc, tboxm.txm_desc, \n" +
            "                     types.description, assign.completion_date, assign.lock_assignment, \n" +
            "                     assign.da_enddate,tboxh.txh_type, assign.segment_id_fk, coalesce(to_number(assign.da_testid), \n" +
            "                     tboxd.txd_da_testid, tboxm.txm_da_testid, 0) AS linkitTestID, assign.da_testassignmentid, \n" +
            "                     types.name, tenop.eno_ver, tenop.eno_series, tboxh.txh_subj, assign.subject, assign.pr_form, \n" +
            "                     assign.pr_version, assign.pr_school, assign.pr_boxletter, assign.pr_itemnumber, \n" +
            "                     COALESCE(assign.pr_subitemnumber, 0) AS pr_subitemnumber, coalesce(to_number(tboxm.txm_lesson), \n" +
            "                     to_number(tboxd.txd_lesson)) as lesson_number, appld.ap_ograde\n" +
            "                FROM homeschoolhouse.student_assignments assign\n" +
            "                JOIN homeschoolhouse.student_assignments_types types\n" +
            "                  ON assign.assignment_type_fk = types.assignment_type_pk\n" +
            "                JOIN linc.abadb_appld appld\n" +
            "                  ON assign.apref = appld.ap_apref\n" +
            "           LEFT JOIN linc.abadb_digital_assessments digAsmts\n" +
            "                  ON assign.apref = digAsmts.application_number\n" +
            "                 AND assign.subject = digAsmts.eno_subj\n" +
            "                JOIN linc.abadb_tenop tenop\n" +
            "                  ON assign.subject = tenop.eno_subj\n" +
            "           LEFT JOIN linc.abadb_tboxh tboxh\n" +
            "                  ON assign.pr_form = tboxh.txh_form\n" +
            "                 AND assign.pr_version = tboxh.txh_ver\n" +
            "                 AND assign.pr_school = tboxh.txh_school\n" +
            "                 AND assign.pr_boxletter = tboxh.txh_boxltr\n" +
            "           LEFT JOIN linc.abadb_tboxd tboxd\n" +
            "                  ON tboxh.txh_form = tboxd.form_number\n" +
            "                 AND tboxh.txh_school = tboxd.school\n" +
            "                 AND tboxh.txh_boxltr = tboxd.box_letter\n" +
            "                 AND tboxh.txh_ver = tboxd.version\n" +
            "                 AND assign.pr_itemnumber = tboxd.txd_itmnbr\n" +
            "                 AND coalesce(assign.pr_subitemnumber, 0) = 0\n" +
            "           LEFT JOIN linc.abadb_tboxm tboxm\n" +
            "                  ON tboxh.txh_form = tboxm.form_number\n" +
            "                 AND tboxh.txh_school = tboxm.school\n" +
            "                 AND tboxh.txh_boxltr = tboxm.box_letter\n" +
            "                 AND tboxh.txh_ver = tboxm.version\n" +
            "                 AND assign.pr_itemnumber = tboxm.txm_itmnbr\n" +
            "                 AND assign.pr_subitemnumber = tboxm.txm_sub_itmnbr\n" +
            "           LEFT JOIN linc.abadb_rchdr rchdr\n" +
            "                  ON appld.ap_apref = rchdr.rch_apref\n" +
            "               WHERE assign.student_id = 'STUDENT_ID_DATA'\n" +
            "                 AND types.name IN('DIGITAL-ASSESSMENT', 'PE ACTIVITY', 'PE SKILLS TEST')\n" +
            "                 AND coalesce(assign.grade_percentage, 0) != 333.3\n" +
            "                 AND trunc(sysdate) BETWEEN coalesce(to_date(appld.ap_beg_dt,'yyyymmdd') - 14, digAsmts.start_date) \n" +
            "                     AND coalesce(appld.ap_end_dt, digAsmts.end_date)\n" +
            "                 AND coalesce(to_number(tboxm.txm_lesson), to_number(tboxd.txd_lesson)) BETWEEN \n" +
            "                     coalesce(digAsmts.start_lesson, 1) AND coalesce(digAsmts.end_lesson, 180)\n" +
            "                 AND appld.ap_status = 8\n" +
            "                 AND (rchdr.rch_status IN ('A', 'I')\n" +
            "                  OR appld.ap_credt != 'Y')\n" +
            "                 AND coalesce(tboxd.txd_da_testid, tboxm.txm_da_testid, 0) > 0 AND ap_ograde = 'Y'),\n" +
            "              nextLessons AS (\n" +
            "              SELECT DISTINCT assign.ap_grade, assign.ap_end_dt, assign.txh_subj, MIN(to_number(assign.lesson_number)) \n" +
            "                     OVER (PARTITION BY assign.txh_subj) AS nextLesson\n" +
            "                FROM studentAssignments assign\n" +
            "               WHERE assign.completion_date IS NULL\n" +
            "            GROUP BY assign.ap_grade, assign.ap_end_dt, assign.txh_subj, assign.lesson_number),\n" +
            "              currentPELesson AS (\n" +
            "              SELECT assign.ap_grade, assign.ap_end_dt, coalesce(assign.txh_subj, assign.subject) AS subject, \n" +
            "                     assign.lesson_number AS currentLesson\n" +
            "                FROM studentAssignments assign\n" +
            "               WHERE trunc(assign.start_date) = trunc(sysdate)\n" +
            "                 AND assign.name IN ('PE ACTIVITY', 'PE SKILLS TEST')),\n" +
            "              streamSubjects AS (\n" +
            "              SELECT subj.subjectid, subj.subjectname, subj.book_item_number, subj.image_url, subj.duration,\n" +
            "                     subj.seriesno, subj.seriesversion, subj.rcg_subj,\n" +
            "                     sTypes.supergroup, sTypes.sessionname, sTypes.sortorder\n" +
            "                FROM homeschoolhouse.vsubjects subj\n" +
            "           LEFT JOIN homeschoolhouse.vsessiontypes sTypes\n" +
            "                  ON subj.subjectid = sTypes.subjectid\n" +
            "               WHERE (sTypes.sessiontypeid = (SELECT max(sessiontypeid)\n" +
            "                                                FROM homeschoolhouse.vsessiontypes sTypes2\n" +
            "                                               WHERE sTypes2.subjectid = subj.subjectid\n" +
            "                                                 AND coalesce(sTypes2.bonus_content, 'N') != 'Y')\n" +
            "                  OR sTypes.sessiontypeid is null))\n" +
            "\n" +
            "              SELECT assign.ap_grade, assign.ap_end_dt, assign.apref, assign.eventID, assign.start_date, \n" +
            "                     assign.end_date, coalesce(CASE WHEN subj.supergroup IS NOT NULL then subj.subjectname ELSE \n" +
            "                     coalesce(subj.sessionname, subj.subjectname) END, assign.eno_desc) AS short_description, \n" +
            "                     coalesce(assign.txd_desc, assign.txm_desc, assign.description) AS long_description, \n" +
            "                     assign.completion_date, box.description AS assignType, assign.segment_id_fk, linkitTestID, \n" +
            "                     assign.da_testassignmentid, to_number(subj.book_item_number) AS book_item_number, subj.image_url,\n" +
            "                     assign.subject, assign.pr_form, assign.pr_version, assign.pr_school, assign.pr_boxletter, \n" +
            "                     assign.pr_itemnumber, coalesce(assign.pr_subitemnumber, 0) AS pr_subitemnumber,\n" +
            "                     cast(coalesce(subj.sortorder, 99) AS NUMBER(3)) AS sortorder, assign.lesson_number, \n" +
            "                     cast(coalesce(subj.subjectid, -1) AS NUMBER(10)) AS subject_id_fk,\n" +
            "                     CASE\n" +
            "                         WHEN nVL.nextVideoLesson > subj.duration THEN nVL.nextVideoLesson - subj.duration\n" +
            "                         ELSE COALESCE(nVL.nextVideoLesson, 170)\n" +
            "                     END AS nextVideoLesson, assign.ap_ograde,\n" +
            "                     mediatypes.name AS mediaformatname, assign.lock_assignment\n" +
            "                FROM studentAssignments assign\n" +
            "           LEFT JOIN progress_report.pr_box_types box\n" +
            "                  ON assign.txh_type = box.box_type\n" +
            "           LEFT JOIN streamSubjects subj\n" +
            "                  ON assign.eno_series = subj.seriesno\n" +
            "                 AND assign.eno_ver = subj.seriesversion\n" +
            "                 AND assign.txh_subj = subj.rcg_subj\n" +
            "           LEFT JOIN nextLessons nLmL\n" +
            "                  ON assign.txh_subj = nLmL.txh_subj\n" +
            "           LEFT JOIN nextVideoLessons nVL\n" +
            "                  ON assign.subject = nVL.subject\n" +
            "           LEFT JOIN currentPELesson cPEL\n" +
            "                  ON coalesce(assign.txh_subj, assign.subject) = cPEL.subject\n" +
            "                JOIN linc.abadb_applc applc\n" +
            "                  ON applc.apc_apref = assign.apref\n" +
            "                 AND applc.ap_item = assign.subject\n" +
            "                JOIN enrollment.media_format_types mediatypes\n" +
            "                  ON mediatypes.media_format_type_id = applc.media_format_type_id\n" +
            "               WHERE (assign.lesson_number = CASE\n" +
            "                                                 WHEN coalesce(trim(assign.txh_type), 'PE') = 'PE' THEN \n" +
            "                                                      cPEL.currentLesson\n" +
            "                                                 ELSE nLmL.nextLesson\n" +
            "                                             END\n" +
            "                  OR (trunc(coalesce(assign.completion_date, assign.da_enddate)) = trunc(sysdate)\n" +
            "                 AND 'Y' != 'Y'))\n" +
            "                 AND assign.completion_date IS NULL\n" +
            "            ORDER BY sortorder ASC)";

    //ORDER BY start_date ASC, eventid ASC) or ORDER BY start_date ASC, our_sort ASC, short_description ASC, long_description ASC)
    String STUDENT_CALENDER_EVENTS_AD_DB = "/*Get student calendar events from AD DB*/\n" +
            "SELECT eventID AS EVENT_ID,\n" +
            "            student_id AS STUDENT_ID,\n" +
            "            apref AS APPLICATION_ID,\n" +
            "            start_date AS START_DATE,\n" +
            "            long_description AS LONG_DESCRIPTION,\n" +
            "            short_description AS Subject,\n" +
            "        CASE\n" +
            "            WHEN LOWER(type_name) = 'video' THEN 'Video'\n" +
            "            WHEN LOWER(type_name) = 'dvd' THEN 'DVD'\n" +
            "            WHEN LOWER(type_name) = 'holiday' THEN ''\n" +
            "            WHEN LOWER(type_name) = 'accountnormal' THEN type_name\n" +
            "            WHEN SUBSTR(LOWER(type_name), 0, 13) = 'studentnormal' THEN 'studentnormal'\n" +
            "            WHEN LOWER(type_name) = 'tboxitems' THEN\n" +
            "                CASE            \n" +
            "                    WHEN INSTR(LOWER(long_description), 'test') > 0 THEN 'Test'\n" +
            "                    WHEN INSTR(LOWER(long_description), 'exam') > 0 THEN 'Exam'\n" +
            "                    WHEN INSTR(LOWER(long_description), 'quiz') > 0 THEN 'Quiz'\n" +
            "                    ELSE 'Lesson'\n" +
            "                END\n" +
            "            ELSE ''\n" +
            "        END AS Description\n" +
            "   FROM (SELECT assign.student_id, assign.apref, assign.assignmentid, coalesce(assign.da_status, 0) AS da_status,\n" +
            "                coalesce(assign.pr_form, 0) AS pr_form, coalesce(assign.pr_version, ' ') AS pr_version, \n" +
            "                coalesce(assign.pr_school, ' ') AS pr_school, coalesce(assign.pr_boxletter, ' ') AS pr_boxletter,\n" +
            "                coalesce(assign.pr_itemnumber, 0) AS pr_itemnumber, coalesce(assign.pr_subitemnumber, 0) AS \n" +
            "                pr_subitemnumber, coalesce(a.subject, ' ') AS subject, a.short_description, a.long_description,\n" +
            "                0 AS testid,\n" +
            "                to_char(start_date, 'YYYY-MM-DD') AS start_date,\n" +
            "                'T' || to_char(start_date, 'hh24:mi:ss') AS start_time,\n" +
            "                CASE WHEN assign.all_day = 'Y' THEN to_char(end_date + 1, 'YYYY-MM-DD') \n" +
            "                     ELSE to_char(end_date, 'YYYY-MM-DD') \n" +
            "                END AS end_date,\n" +
            "                'T' || to_char(end_date, 'hh24:mi:ss') AS end_time, assign.all_day, c.description AS category,\n" +
            "                c.categoryid,  coalesce(c.color,' ') AS color, a.lesson_number, a.lesson_number AS segmentlesson, 'Y' \n" +
            "                AS editable, completion_date, 'STUDENTNORMAL ' || coalesce(typ.name, '') AS type_name, \n" +
            "                student_assignments_id AS eventid, assign.subscription_items_fk, assign.subject_id_fk, \n" +
            "                assign.segment_id_fk, lock_assignment, 3 AS sort_order, 2 AS our_sort\n" +
            "           FROM homeschoolhouse.student_assignments assign\n" +
            "      LEFT JOIN homeschoolhouse.student_assignments_types typ\n" +
            "             ON typ.assignment_type_pk = assign.assignment_type_fk\n" +
            "           JOIN homeschoolhouse.assignments a\n" +
            "             ON assign.assignmentid = a.assignmentid\n" +
            "           JOIN homeschoolhouse.category c\n" +
            "             ON a.categoryid = c.categoryid\n" +
            "          WHERE assign.student_id = 'STUDENT_ID_DATA'\n" +
            "            AND start_date between to_date('START_DATE_DATA', 'YYYYMMDD') and to_date('END_DATE_DATA'||'235959', \n" +
            "                'YYYYMMDDhh24miss')\n" +
            "            AND c.categoryid != 3\n" +
            "          UNION\n" +
            "         SELECT to_number('STUDENT_ID_DATA') AS student_id, 0 AS apref, -1 AS assignmentid, 0 AS da_status,\n" +
            "                0 AS pr_form, ' ' AS pr_version, ' ' AS pr_school, ' ' AS pr_boxletter,\n" +
            "                0 AS pr_itemnumber, 0 AS pr_subitemnumber,\n" +
            "                ' ' AS subject, (SELECT Utilities.Format.ProperName(holiday) FROM dual) AS short_description, 'Enjoy \n" +
            "                the ' || (SELECT Utilities.Format.ProperName(holiday) FROM dual) || ' holiday!' AS long_description,\n" +
            "                0 AS testid,\n" +
            "                to_char(to_date(ho_beg_dt, 'YYYYMMDD'), 'YYYY-MM-DD') AS start_date,\n" +
            "                null AS start_time,\n" +
            "                to_char(to_date(ho_end_dt, 'YYYYMMDD') + 1, 'YYYY-MM-DD') AS end_date,\n" +
            "                null AS end_time,\n" +
            "                'Y' AS all_day, c.description AS category,\n" +
            "                c.categoryid,  coalesce(c.color,' ') AS color, 0 AS lesson_number, 0 AS segmentlesson, 'N' AS \n" +
            "                editable, null AS completion_date, 'HOLIDAY' AS type_name, 0 AS eventid,\n" +
            "                0 AS subscription_items_fk, 0 AS subject_id_fk, 0 AS segment_id_fk, ' ' AS lock_assignment, 4 AS \n" +
            "                sort_order, 1 AS our_sort\n" +
            "           FROM linc.abadb_holid\n" +
            "           JOIN homeschoolhouse.category c\n" +
            "             ON accountnumber in ('ACCOUNT_NUMBER_DATA', 1)\n" +
            "          WHERE description = 'Holiday'\n" +
            "            AND to_date(ho_beg_dt, 'YYYYMMDD') between to_date('START_DATE_DATA', 'YYYYMMDD') and \n" +
            "                to_date('END_DATE_DATA'||'235959', 'YYYYMMDDhh24miss')\n" +
            "          UNION\n" +
            "         SELECT assign.student_id, assign.apref, coalesce(assign.assignmentid,0), coalesce(assign.da_status, 0) AS \n" +
            "                da_status, coalesce(assign.pr_form, 0) AS pr_form, assign.pr_version, assign.pr_school, \n" +
            "                assign.pr_boxletter, assign.pr_itemnumber, coalesce(assign.pr_subitemnumber, 0) AS pr_subitemnumber, \n" +
            "                assign.subject,\n" +
            "                CASE WHEN tnp.eno_grade IN (1,2,3,4,5,6,14,15) THEN tsubj.description \n" +
            "                     ELSE tnp.eno_desc \n" +
            "                END AS short_description, \n" +
            "                replace(tboxd.txd_desc, '\"\"', '\\\"\"') || ' (Day ' || assign.lesson_number || ')' AS long_description,\n" +
            "                CASE WHEN ap_ograde = 'Y' THEN to_number(tboxd.txd_da_testid) ELSE 0 END AS testid,\n" +
            "                to_char(start_date, 'YYYY-MM-DD') AS start_date,\n" +
            "                'T' || to_char(start_date, 'hh24:mi:ss') AS start_time,\n" +
            "                to_char(end_date + 1, 'YYYY-MM-DD') AS end_date,\n" +
            "                'T' || to_char(end_date, 'hh24:mi:ss') AS end_time, 'Y' AS all_day,\n" +
            "                 c.description AS category,\n" +
            "                c.categoryid,  coalesce(c.color,' ') AS color, assign.lesson_number, assign.lesson_number AS \n" +
            "                segmentlesson, 'N' AS editable, completion_date, 'TBOXITEMS' AS type_name, student_assignments_id AS \n" +
            "                eventid, assign.subscription_items_fk, assign.subject_id_fk, assign.segment_id_fk, lock_assignment, 2 \n" +
            "                AS sort_order, 2 AS our_sort\n" +
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
            "            AND start_date between to_date('START_DATE_DATA', 'YYYYMMDD') and to_date('END_DATE_DATA'||'235959', \n" +
            "                'YYYYMMDDhh24miss')\n" +
            "            AND ap.ap_status != 9\n" +
            "            AND (rchdr.rch_status IS NULL OR rchdr.rch_status != 'C')\n" +
            "          UNION\n" +
            "         SELECT assign.student_id, assign.apref, coalesce(assign.assignmentid,0) AS assignmentid, \n" +
            "                coalesce(assign.da_status, 0) AS da_status, coalesce(assign.pr_form, 0) AS pr_form, assign.pr_version, \n" +
            "                assign.pr_school, assign.pr_boxletter, assign.pr_itemnumber, assign.pr_subitemnumber, assign.subject,\n" +
            "                CASE WHEN tnp.eno_grade IN (1,2,3,4,5,6,14,15) THEN tsubj.description ELSE tnp.eno_desc END AS \n" +
            "                short_description, replace(tboxm.txm_desc, '\"\"', '\\\"\"') || ' (Day ' || tboxm.txm_lesson || ')' AS \n" +
            "                long_description,\n" +
            "                CASE WHEN ap_ograde = 'Y' THEN to_number(tboxm.txm_da_testid) ELSE 0 END AS testid,\n" +
            "                to_char(start_date, 'YYYY-MM-DD') AS start_date,\n" +
            "                'T' || to_char(start_date, 'hh24:mi:ss') AS start_time,\n" +
            "                to_char(end_date + 1, 'YYYY-MM-DD') AS end_date,\n" +
            "                'T' || to_char(end_date, 'hh24:mi:ss') AS end_time, 'Y' AS all_day,\n" +
            "                c.description AS category,\n" +
            "                c.categoryid,  coalesce(c.color,' ') AS color, assign.lesson_number, assign.lesson_number AS \n" +
            "                segmentlesson, 'N' AS editable, completion_date, 'TBOXITEMS' AS type_name, student_assignments_id AS \n" +
            "                eventid, assign.subscription_items_fk, assign.subject_id_fk, assign.segment_id_fk, lock_assignment, 2 \n" +
            "                AS sort_order, 2 AS our_sort\n" +
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
            "            AND start_date between to_date('START_DATE_DATA', 'YYYYMMDD') and to_date('END_DATE_DATA'||'235959', \n" +
            "                'YYYYMMDDhh24miss')\n" +
            "            AND ap.ap_status != 9\n" +
            "            AND (rchdr.rch_status IS NULL OR rchdr.rch_status != 'C')\n" +
            "          UNION\n" +
            "         SELECT assign.student_id, assign.apref, coalesce(assign.assignmentid,0) AS assignmentid, \n" +
            "                coalesce(assign.da_status, 0) AS da_status, coalesce(assign.pr_form, 0) AS pr_form, assign.pr_version, \n" +
            "                assign.pr_school, assign.pr_boxletter, assign.pr_itemnumber, assign.pr_subitemnumber, assign.subject,\n" +
            "                subj.subjectname AS short_Description,\n" +
            "                 'Video Lesson ' || segments.lesson || CASE WHEN TRIM(subj.subjectname) = TRIM(stype.sessionname) THEN \n" +
            "                '' ELSE ' - ' || coalesce(stype.sessionname, '') END AS long_description,\n" +
            "                null AS testid,\n" +
            "                to_char(assign.start_date, 'YYYY-MM-DD') AS start_date,\n" +
            "                'T' || to_char(assign.start_date, 'hh24:mi:ss') AS start_time,\n" +
            "                CASE WHEN coalesce(assign.all_day,'Y') = 'Y' THEN to_char(assign.end_date + 1, 'YYYY-MM-DD') ELSE \n" +
            "                     to_char(assign.end_date, 'YYYY-MM-DD') END AS end_date,\n" +
            "                'T' || to_char(assign.end_date, 'hh24:mi:ss') AS end_time, coalesce(assign.all_day,'Y') AS all_day, \n" +
            "                c.description AS category,\n" +
            "                c.categoryid,  coalesce(c.color,' ') AS color, assign.lesson_number, segments.lesson AS segmentlesson, \n" +
            "                'N' AS editable, completion_date, typ.name AS type_name, student_assignments_id AS eventid,\n" +
            "                assign.subscription_items_fk, assign.subject_id_fk, assign.segment_id_fk, lock_assignment, 1 AS \n" +
            "                sort_order, 2 AS our_sort\n" +
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
            "            AND assign.start_date between to_date('START_DATE_DATA', 'YYYYMMDD') and to_date('END_DATE_DATA'||'235959', \n" +
            "                'YYYYMMDDhh24miss')\n" +
            "            AND ap.ap_status != 9\n" +
            "            AND (rchdr.rch_status IS NULL OR rchdr.rch_status != 'C')\n" +
            "            AND (stype.pen_key = ap.pen_key OR stype.pen_key IS NULL)\n" +
            "          UNION\n" +
            "         SELECT assign.student_id, assign.apref, coalesce(assign.assignmentid,0) AS assignmentid, \n" +
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
            "                c.categoryid, coalesce(c.color,' ') AS color, assign.lesson_number, assign.lesson_number AS \n" +
            "                segmentlesson, 'N' AS editable, COMPLETION_DATE, 'PE' AS type_name, student_assignments_id AS eventid,\n" +
            "                assign.subscription_items_fk, assign.subject_id_fk, assign.segment_id_fk, lock_assignment, 3 AS \n" +
            "                sort_order, 2 AS our_sort\n" +
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
            "            AND assign.start_date between to_date('START_DATE_DATA', 'YYYYMMDD') and to_date('END_DATE_DATA'||'235959', \n" +
            "                'YYYYMMDDhh24miss')\n" +
            "            AND ap.ap_status != 9\n" +
            "            AND (rchdr.rch_status IS NULL OR rchdr.rch_status != 'C')\n" +
            "       ORDER BY start_date ASC, our_sort ASC, short_description ASC, long_description ASC)";

    String VIDEO_LIBRARY_VIDEOS_SD_DB = "/*Get video library video details from SD DB*/\n" +
            "SELECT DISTINCT lessons.segment_id, lessons.lesson_number, lessons.subject_name, lessons.completed,\n" +
            "                  lessons.subscription_items, coalesce(lessons.teacher, 'NN') AS teacher, lessons.restart_at,\n" +
            "                  lessons.latest_point, lessons.super_group, lessons.session_name,\n" +
            "                  subjects.subject_id, lessons.video_hls, lessons.video_f4v, lessons.video_webm,\n" +
            "                  lessons.bonus_content, lessons.subject_group,\n" +
            "                  CASE\n" +
            "                    WHEN (SELECT count(*)\n" +
            "                            FROM abashared.vsessiontypes innerSessionType\n" +
            "                            JOIN abashared.vlessonsegments segments\n" +
            "                              ON innerSessionType.subjectid = segments.subjectid\n" +
            "                             AND innerSessionType.sessiontypeid = segments.sessiontypeid\n" +
            "                           WHERE innerSessionType.subjectid = subjects.subject_id\n" +
            "                             AND segments.lesson = lessons.lesson_number\n" +
            "                             AND innerSessionType.bonus_content = 'Y') > 0 THEN 'Y'\n" +
            "                    ELSE 'N'\n" +
            "                  END AS hasBonus\n" +
            "             FROM TABLE (abashared.permissions_queries.getAvailableLessons(p_LoginID => 'LOGIN_ID_DATA', p_SubjectID => 'SUBJECT_ID_DATA', p_SubscriptionNumber => 'SUBSCRIPTION_NUMBER_DATA')) lessons\n" +
            "             JOIN TABLE (abashared.permissions_queries.getAvailableSubjects(p_LoginID => 'LOGIN_ID_DATA', p_SubscriptionNumber => 'SUBSCRIPTION_NUMBER_DATA')) subjects\n" +
            "               ON lessons.subject_name = subjects.subject_name\n" +
            "            WHERE lessons.bonus_content IS NULL\n" +
            "            ORDER BY lessons.lesson_number, lessons.super_group NULLS FIRST";

    String LOGIN_DETAILS_SD_DB = "/* To get the Student Login details from SD DB*/\n" +
            "SELECT DISPLAY_NAME, LOGIN_ID_PK AS LOGIN_ID, ACCOUNT_NUMBER, STUDENT_ID, USER_NAME FROM abashared.user_logins WHERE lower(user_name) = lower('STUDENT_ID_DATA')";

    String GET_SUBSCRIPTION_NUMBER_SD_DB = "/* To get the Subscription number from SD DB*/ \n" +
            "SELECT DISTINCT subscription_number AS SUBSCRIPTION_NUMBER\n" +
            "  FROM abashared.permissions_queries.GetSubscriptionsByLogin('LOGIN_ID_DATA')";

    String GET_APPLICATION_NUMBER_SD_DB = "/* To get the Application number from SD DB*/ \n" +
            "SELECT subs.application_number AS APPLICATION_NUMBER\n" +
            "FROM ABASHARED.streaming_subscriptions subs\n" +
            "WHERE subs.subscription_number_pk = 'SUBSCRIPTION_NUMBER_DATA'";

    String STUDENT_SUBJECT_DETAILS_SD_DB = "/* To fetch the student subject details  from SD DB*/\n" +
            "select distinct * from TABLE (abashared.permissions_queries.getAvailableSubjects('LOGIN_ID_DATA','SUBSCRIPTION_NUMBER_DATA'))";

    String MARK_VIDEO_LESSON_AS_COMPLETED_SD_DB =
            //parameters - SUBSCRIPTION_NUMBER_DATA,SUBSCRIPTION_ITEM_DATA,LOGIN_ID_DATA,SEGMENT_ID_FK_DATA,'USER_ID_DATA'
            "/* To update the video as completed ON SD DB*/\n" +
            "{CALL ABASHARED.VIDEO_STREAMING.FORCE_COMPLETE_VIDEO(?,?,?,?,?)}";

    String MARK_ALL_VIDEO_LESSONS_AS_COMPLETED_AD_DB = "/* To mark all video lessons as completed*/\n" +
            "BEGIN\n" +
            " \n" +
            "  for r_SubscriptionItem IN (SELECT subs.subscription_number_pk, sitems.subscription_items_pk,\n" +
            "                                    sitems.start_lesson_number, sitems.end_lesson_number\n" +
            "                               FROM ABASHARED.streaming_subscriptions subs\n" +
            "                               LEFT JOIN ABASHARED.streaming_subscriptions_items sitems\n" +
            "                                 ON sitems.subscription_number_fk = subs.subscription_number_pk\n" +
            "                              WHERE subs.subscription_number_pk = (SELECT max(subscriptions.subscription_number) AS subscription_number\n" +
            "                                                                     FROM TABLE(abashared.permissions_queries.GetSubscriptionsByLogin(p_LoginID => 393266)) subscriptions\n" +
            "                                                                    WHERE subscriptions.type_name = 'HOMESCHOOL'\n" +
            "                                                                      AND subscriptions.status_name = 'ACTIVE')\n" +
            "                            )\n" +
            "  LOOP\n" +
            "    abashared.video_streaming.Bulk_Clear_Or_Complete(\n" +
            "      p_Subscription_Number => r_SubscriptionItem.subscription_number_pk,\n" +
            "      p_Subscription_Items  => r_SubscriptionItem.subscription_items_pk,\n" +
            "      p_Login_ID            => 393266,\n" +
            "      p_Start_Lesson        => r_SubscriptionItem.start_lesson_number,\n" +
            "      p_End_Lesson          => r_SubscriptionItem.end_lesson_number,\n" +
            "     p_agent               => 'RCG-Script',\n" +
            "      p_clear_or_complete   => 'complete');\n" +
            "  END LOOP;\n" +
            "END;";

    String SET_ALL_VIDEO_COMPLETED_SP_SD_DB = "/*Marking all video completed SP*/ \n" +
            "{CALL abashared.RCG_Complete_All_Videos_For_Student(p_LoginID => ?)}";

    String MARK_ASSESSMENT_RELATED_VIDEO_COMPLETED_SP_SD_DB = "/*Marking assessment related all video completed SP*/ \n" +
            "{CALL abashared.RCG_Complete_All_Videos_For_Student(p_LoginID => ?,p_SubjectID => ?,p_EndLesson => ?)}";

    String CLEAR_ASSESSMENT_PROCESS_MARK_VIDEO_NOT_VIEWED_SP_SD_DB = "/*Clear assessment process (marking video lessons not viewed ) SP*/ \n" +
            "CALL abashared.RCG_Complete_All_Videos_For_Student(p_LoginID   => ?, p_SubjectID => ?,p_StartLesson => ?,p_EndLesson => ?,p_ClearProgress => 'Y');";

    String MY_TO_LIST_LESSONS_SD_DB = "/* MY TO-Do list lessons data*/ \n" +
            "WITH\n" +
            "               studentAssignments AS (\n" +
            "                 SELECT *\n" +
            "                   FROM homeschoolhouse.student_assignments@abadb assign\n" +
            "                   JOIN homeschoolhouse.student_assignments_types@abadb types\n" +
            "                     ON assign.assignment_type_fk = types.assignment_type_pk\n" +
            "                  WHERE assign.student_id = 'STUDENT_ID_DATA'\n" +
            "                    AND types.name IN('DVD', 'VIDEO')\n" +
            "               ),\n" +
            "               videoAssignments AS (\n" +
            "                 SELECT\n" +
            "                        ap_grade, ap_end_dt, eventID, apref, start_date, end_date, completion_date, lesson_number, lock_assignment, segment_id_fk, subject, subject_id_fk, assignType,\n" +
            "                        short_description, long_description, da_testassignmentid, pr_form, pr_version, pr_school, pr_boxletter, pr_itemnumber, pr_subitemnumber, grade,\n" +
            "                        book_item_number, image_url, ap_credt, teacher, sub_start_date, sortorder, sessionsrequired, requirementsRemaining,\n" +
            "                        CASE\n" +
            "                          WHEN SUM(requirementsRemaining) OVER (PARTITION BY subject_id_fk, lesson_number) = 0 THEN 'Y'\n" +
            "                          ELSE 'N'\n" +
            "                        END AS IsRequirementMet,\n" +
            "                        subscription_number_pk, latest_point, restart_at, completed\n" +
            "                   FROM (\n" +
            "                         SELECT ap_grade, ap_end_dt, eventid, apref, start_date, end_date, completion_date, lesson_number, lock_assignment, segment_id_fk, subject, subject_id_fk, assignType,\n" +
            "                                short_description, long_description, da_testassignmentid, pr_form, pr_version, pr_school, pr_boxletter, pr_itemnumber, pr_subitemnumber, grade,\n" +
            "                                book_item_number, image_url, ap_credt, teacher, sub_start_date, sortorder,\n" +
            "                                sessionsrequired, sessionoptioncount, min(sessionRemaining) OVER (PARTITION BY subject_id_fk, supergroup, lesson_number) AS requirementsRemaining,\n" +
            "                                subscription_number_pk, latest_point, restart_at, completed\n" +
            "                           FROM (\n" +
            "                                 SELECT ap_grade, ap_end_dt, assign.student_assignments_id AS eventID, assign.apref, assign.start_date, assign.end_date, assign.completion_date, segments.lesson AS lesson_number,\n" +
            "                                        assign.lock_assignment, assign.segment_id_fk, assign.subject, assign.subject_id_fk, CASE mediatypes.name\n" +
            "                                                                                                                              WHEN 'STREAMING' THEN 'LESSON'\n" +
            "                                                                                                                               ELSE 'DVD'\n" +
            "                                                                                                                            END AS assignType,\n" +
            "                                        sessions.sessionname AS short_description, 'Lesson ' || segments.lesson AS long_description, assign.da_testassignmentid,\n" +
            "                                        assign.pr_form, assign.pr_version, assign.pr_school, assign.pr_boxletter, assign.pr_itemnumber, COALESCE(assign.pr_subitemnumber, 0) AS pr_subitemnumber,\n" +
            "                                        subjects.grade, to_number(subjects.book_item_number) AS book_item_number, subjects.image_url, appld.ap_credt,\n" +
            "                                        CASE\n" +
            "                                           WHEN NVL(title.ttitl_nbr, 0) IN (3, 9, 10, 20, 21) THEN NVL(title.ttitledesc,'') || ' ' || NVL(teacher.teacher_last_name,'')\n" +
            "                                           ELSE NVL(title.ttitledesc,'') || '. ' || NVL(teacher.teacher_last_name,'')\n" +
            "                                        END AS teacher, subs.start_date AS sub_start_date, sessions.sortorder, COALESCE(sessions.supergroup, sessions.sessiontype) AS supergroup, segments.sessionsrequired, segments.sessionoptioncount,\n" +
            "                                        CASE\n" +
            "                                          WHEN assign.completion_date IS NOT NULL THEN 0\n" +
            "                                          ELSE 1\n" +
            "                                        END AS sessionRemaining, subs.subscription_number_pk, COALESCE(uitems.latest_point, 0) AS latest_point, COALESCE(uitems.restart_at, 0) AS restart_at,\n" +
            "                                          CASE WHEN uitems.completed IS NULL THEN\n" +
            "                                                 CASE\n" +
            "                                                   WHEN assign.completion_date IS NOT NULL THEN 'Y'\n" +
            "                                                   ELSE 'N'\n" +
            "                                                 END\n" +
            "                                               ELSE COALESCE(uitems.completed, 'N')\n" +
            "                                          END AS completed\n" +
            "                                   FROM studentAssignments assign\n" +
            "                                   JOIN abadb.appld\n" +
            "                                     ON assign.apref = appld.ap_apref\n" +
            "                                   JOIN abashared.vlessonsegmentrequirements segments\n" +
            "                                     ON assign.segment_id_fk = segments.segmentid\n" +
            "                                   JOIN abashared.vsessiontypes sessions\n" +
            "                                     ON segments.sessiontypeid = sessions.sessiontypeid\n" +
            "                                   JOIN abashared.vsubjects subjects\n" +
            "                                     ON subjects.subjectid = assign.subject_id_fk\n" +
            "                                     Left Join\n" +
            "                                     abashared.streaming_subscriptions subs\n" +
            "                                     ON assign.apref = subs.application_number\n" +
            "                                   Left Join\n" +
            "                                   abashared.streaming_subscriptions_items subitems\n" +
            "                                     ON subs.subscription_number_pk = subitems.subscription_number_fk\n" +
            "                                    AND subitems.subject_id_fk = assign.subject_id_fk\n" +
            "                                   LEFT JOIN abashared.streaming_users_items uitems\n" +
            "                                     ON uitems.subscription_items_fk = subitems.subscription_items_pk\n" +
            "                                    AND uitems.segment_id_fk = segments.segmentid\n" +
            "                                   LEFT JOIN abashared.teacher_subjects teacher_sbj\n" +
            "                                     ON teacher_sbj.subject_id_fk = segments.subjectid\n" +
            "                                   LEFT JOIN abashared.teachers teacher\n" +
            "                                     ON teacher.teacher_pk = teacher_sbj.teacher_pk_fk\n" +
            "                                   JOIN abadb.ttitl title\n" +
            "                                     ON title.ttitl_nbr = teacher.teacher_title\n" +
            "                                   LEFT JOIN abashared.unfinished_rchdr urchdr\n" +
            "                                     ON appld.ap_apref = urchdr.rch_apref\n" +
            "                                   JOIN enrollment.media_format_types@abadb mediatypes\n" +
            "                                     ON mediatypes.media_format_type_id = appld.media_format_type_id\n" +
            "                                  WHERE (subs.subscription_status_fk = 1\n" +
            "                                     OR subs.subscription_status_fk IS NULL\n" +
            "                                     OR mediatypes.name != 'STREAMING'\n" +
            "                                     )\n" +
            "                                    AND (sessions.subject_group = subitems.subject_group\n" +
            "                                         OR subitems.subject_group IS NULL\n" +
            "                                         OR sessions.subject_group IS NULL\n" +
            "                                         OR uitems.completed = 'Y'\n" +
            "                                        )\n" +
            "                                    AND (TRUNC(SYSDATE)  BETWEEN COALESCE(subs.start_date, to_date(appld.ap_beg_dt, 'yyyymmdd') - 14, trunc(sysdate)) AND COALESCE(subs.end_date, appld.ap_end_dt, trunc(sysdate))\n" +
            "                                         OR (mediatypes.name != 'STREAMING' AND (TRUNC(SYSDATE) BETWEEN to_date(appld.ap_beg_dt, 'yyyymmdd') - 14  AND COALESCE(appld.ap_end_dt, trunc(sysdate)) ) )\n" +
            "                                        )\n" +
            "                                    AND (segments.lesson BETWEEN subitems.start_lesson_number and subitems.end_lesson_number\n" +
            "                                     OR subs.subscription_status_fk IS NULL\n" +
            "                                     OR mediatypes.name != 'STREAMING')\n" +
            "                                    AND (urchdr.rch_status IN ('A', 'I')\n" +
            "                                     OR appld.ap_credt != 'Y')\n" +
            "                                    AND (sessions.pen_key = appld.pen_key OR sessions.pen_key IS NULL)\n" +
            "                                    AND appld.ap_status = 8\n" +
            "                                )\n" +
            "                        )\n" +
            "               ),\n" +
            "               nextLessonsMinLesson AS (\n" +
            "                 SELECT DISTINCT assign.ap_end_dt, assign.ap_grade, assign.subject_id_fk, assign.apref, MIN(assign.lesson_number) OVER(PARTITION BY assign.subject_id_fk) AS nextLesson, MIN(assign.lesson_number) OVER(PARTITION BY assign.apref) AS minLesson\n" +
            "                   FROM videoAssignments assign\n" +
            "                  WHERE assign.completion_date IS NULL\n" +
            "                    AND assign.IsRequirementMet = 'N'\n" +
            "                  GROUP BY assign.ap_grade, assign.ap_end_dt, assign.subject_id_fk, assign.apref, assign.lesson_number\n" +
            "               ),\n" +
            "               lastCompletedLessons AS (\n" +
            "                 SELECT DISTINCT assign.ap_grade, assign.ap_end_dt, assign.subject_id_fk, MAX(assign.lesson_number) OVER(PARTITION BY assign.subject_id_fk) AS lastCompletedLesson\n" +
            "                   FROM videoAssignments assign\n" +
            "                  WHERE assign.completion_date IS NOT NULL\n" +
            "                    AND assign.IsRequirementMet = 'Y'\n" +
            "                  GROUP BY assign.ap_grade, assign.ap_end_dt, assign.subject_id_fk, assign.lesson_number\n" +
            "               )\n" +
            "               SELECT vAssign.ap_grade, vAssign.ap_end_dt, vAssign.apref, vAssign.eventID AS eventID, vAssign.start_date, vAssign.end_date, vAssign.short_description, vAssign.long_description, vAssign.completion_date,\n" +
            "                      vAssign.assignType, CAST(vAssign.subject_id_fk AS NUMBER(10)) AS subject_id_fk, vAssign.segment_id_fk, vAssign.subject, 0 AS linkitTestID, vAssign.da_testassignmentid, vAssign.book_item_number, vAssign.image_url, vAssign.lock_assignment,\n" +
            "                      vAssign.pr_form, vAssign.pr_version, vAssign.pr_school, vAssign.pr_boxletter, vAssign.pr_itemnumber, vAssign.pr_subitemnumber, CAST(vAssign.sortorder AS NUMBER(3)) AS sortorder, vAssign.lesson_number,\n" +
            "                      vAssign.subscription_number_pk, vAssign.restart_at, vAssign.latest_point, vAssign.completed, vAssign.teacher, vAssign.ap_credt, 'N' AS lockedlesson,\n" +
            "                      vAssign.requirementsRemaining, vAssign.IsRequirementMet\n" +
            "                 FROM videoAssignments vAssign\n" +
            "                 LEFT JOIN lastCompletedLessons lCL\n" +
            "                   ON vAssign.subject_id_fk = lCL.subject_id_fk\n" +
            "                 LEFT JOIN nextLessonsMinLesson nLML\n" +
            "                   ON vAssign.subject_id_fk = nLML.subject_id_fk\n" +
            "                  AND vAssign.apref = nLML.apref\n" +
            "                WHERE vAssign.lesson_number = CASE\n" +
            "                                                WHEN (vAssign.grade IN(14,15,1,2,3,4,5,6)\n" +
            "                                                 AND  ' ' = ' ')\n" +
            "                                                  OR  ' ' = 'Y' THEN nLmL.minLesson\n" +
            "                                                WHEN (vAssign.grade IN(7,8,9,10,11,12)\n" +
            "                                                 AND  ' ' = ' ')\n" +
            "                                                  OR  ' ' = 'N' THEN nLmL.nextLesson\n" +
            "                                              END\n" +
            "                   OR (vAssign.grade IN(7,8,9,10,11,12)\n" +
            "                       AND trunc(vAssign.completion_date) = trunc(sysdate)\n" +
            "                      )\n" +
            "                ORDER BY vAssign.completion_date desc, vAssign.sub_start_date, vAssign.sortorder, vAssign.lesson_number";

    String MY_TO_DO_LIST_ASSIGNMENTS_AD_DB = "/* My TO-DO List assignment data AD DB*/\n" +
            "WITH\n" +
            "nextVideoLessons AS (\n" +
            "SELECT DISTINCT appld.ap_grade, appld.ap_end_dt, assign.apref, assign.subject, min(lessonInfo.lesson) OVER (PARTITION BY assign.subject, assign.apref) AS nextVideoLesson\n" +
            "FROM homeschoolhouse.student_assignments assign\n" +
            "JOIN homeschoolhouse.student_assignments_types types\n" +
            "ON assign.assignment_type_fk = types.assignment_type_pk\n" +
            "JOIN homeschoolhouse.lesson_information lessonInfo\n" +
            "ON assign.segment_id_fk = lessonInfo.segmentid\n" +
            "AND assign.apref = lessonInfo.application_number\n" +
            "AND assign.subject_id_fk = lessonInfo.subject_id_fk\n" +
            "JOIN linc.abadb_appld appld\n" +
            "ON assign.apref = appld.ap_apref\n" +
            "LEFT JOIN linc.abadb_rchdr rchdr\n" +
            "ON appld.ap_apref = rchdr.rch_apref\n" +
            "WHERE assign.student_id = 'STUDENT_ID_DATA'\n" +
            "AND appld.ap_status = 8\n" +
            "AND trunc(appld.ap_end_dt) >= trunc(sysdate)\n" +
            "AND assign.completion_date IS NULL\n" +
            "AND lessonInfo.lesson BETWEEN lessonInfo.start_lesson_number AND lessonInfo.end_lesson_number\n" +
            "AND types.name = 'VIDEO'\n" +
            "AND (rchdr.rch_status IN ('A', 'I')\n" +
            "OR appld.ap_credt != 'Y')\n" +
            "GROUP BY appld.ap_grade, appld.ap_end_dt, assign.subject, lessonInfo.lesson, assign.apref\n" +
            "),\n" +
            "studentAssignments AS (\n" +
            "SELECT appld.ap_grade, appld.ap_end_dt, assign.apref, assign.student_assignments_id AS eventID, assign.start_date, assign.end_date, tenop.eno_desc, tboxd.txd_desc, tboxm.txm_desc, types.description, assign.completion_date, assign.lock_assignment,\n" +
            "assign.da_enddate,tboxh.txh_type, assign.segment_id_fk, coalesce(to_number(assign.da_testid), tboxd.txd_da_testid, tboxm.txm_da_testid, 0) AS linkitTestID, assign.da_testassignmentid, types.name, tenop.eno_ver, tenop.eno_series,\n" +
            "tboxh.txh_subj, assign.subject, assign.pr_form, assign.pr_version, assign.pr_school, assign.pr_boxletter, assign.pr_itemnumber, COALESCE(assign.pr_subitemnumber, 0) AS pr_subitemnumber, coalesce(to_number(tboxm.txm_lesson), to_number(tboxd.txd_lesson)) as lesson_number, appld.ap_ograde\n" +
            "FROM homeschoolhouse.student_assignments assign\n" +
            "JOIN homeschoolhouse.student_assignments_types types\n" +
            "ON assign.assignment_type_fk = types.assignment_type_pk\n" +
            "JOIN linc.abadb_appld appld\n" +
            "ON assign.apref = appld.ap_apref\n" +
            "LEFT JOIN linc.abadb_digital_assessments digAsmts\n" +
            "ON assign.apref = digAsmts.application_number\n" +
            "AND assign.subject = digAsmts.eno_subj\n" +
            "JOIN linc.abadb_tenop tenop\n" +
            "ON assign.subject = tenop.eno_subj\n" +
            "LEFT JOIN linc.abadb_tboxh tboxh\n" +
            "ON assign.pr_form = tboxh.txh_form\n" +
            "AND assign.pr_version = tboxh.txh_ver\n" +
            "AND assign.pr_school = tboxh.txh_school\n" +
            "AND assign.pr_boxletter = tboxh.txh_boxltr\n" +
            "LEFT JOIN linc.abadb_tboxd tboxd\n" +
            "ON tboxh.txh_form = tboxd.form_number\n" +
            "AND tboxh.txh_school = tboxd.school\n" +
            "AND tboxh.txh_boxltr = tboxd.box_letter\n" +
            "AND tboxh.txh_ver = tboxd.version\n" +
            "AND assign.pr_itemnumber = tboxd.txd_itmnbr\n" +
            "AND coalesce(assign.pr_subitemnumber, 0) = 0\n" +
            "LEFT JOIN linc.abadb_tboxm tboxm\n" +
            "ON tboxh.txh_form = tboxm.form_number\n" +
            "AND tboxh.txh_school = tboxm.school\n" +
            "AND tboxh.txh_boxltr = tboxm.box_letter\n" +
            "AND tboxh.txh_ver = tboxm.version\n" +
            "AND assign.pr_itemnumber = tboxm.txm_itmnbr\n" +
            "AND assign.pr_subitemnumber = tboxm.txm_sub_itmnbr\n" +
            "LEFT JOIN linc.abadb_rchdr rchdr\n" +
            "ON appld.ap_apref = rchdr.rch_apref\n" +
            "WHERE assign.student_id = 'STUDENT_ID_DATA'\n" +
            "AND types.name IN('DIGITAL-ASSESSMENT', 'PE ACTIVITY', 'PE SKILLS TEST')\n" +
            "AND coalesce(assign.grade_percentage, 0) != 333.3\n" +
            "AND trunc(sysdate) BETWEEN coalesce(to_date(appld.ap_beg_dt,'yyyymmdd') - 14, digAsmts.start_date) AND coalesce(appld.ap_end_dt, digAsmts.end_date)\n" +
            "AND coalesce(to_number(tboxm.txm_lesson), to_number(tboxd.txd_lesson)) BETWEEN coalesce(digAsmts.start_lesson, 1) AND coalesce(digAsmts.end_lesson, 180)\n" +
            "AND appld.ap_status = 8\n" +
            "AND (rchdr.rch_status IN ('A', 'I')\n" +
            "OR appld.ap_credt != 'Y')\n" +
            "--AND coalesce(tboxd.txd_da_testid, tboxm.txm_da_testid, 0) > 0 AND ap_ograde = 'Y'\n" +
            "),\n" +
            "nextLessons AS (\n" +
            "SELECT DISTINCT assign.ap_grade, assign.ap_end_dt, assign.txh_subj, MIN(to_number(assign.lesson_number)) OVER (PARTITION BY assign.txh_subj) AS nextLesson\n" +
            "FROM studentAssignments assign\n" +
            "WHERE assign.completion_date IS NULL\n" +
            "GROUP BY assign.ap_grade, assign.ap_end_dt, assign.txh_subj, assign.lesson_number),\n" +
            "currentPELesson AS (\n" +
            "SELECT assign.ap_grade, assign.ap_end_dt, coalesce(assign.txh_subj, assign.subject) AS subject, assign.lesson_number AS currentLesson\n" +
            "FROM studentAssignments assign\n" +
            "WHERE trunc(assign.start_date) = trunc(sysdate)\n" +
            "AND assign.name IN ('PE ACTIVITY', 'PE SKILLS TEST')),\n" +
            "streamSubjects AS (\n" +
            "SELECT subj.subjectid, subj.subjectname, subj.book_item_number, subj.image_url, subj.duration,\n" +
            "subj.seriesno, subj.seriesversion, subj.rcg_subj,\n" +
            "sTypes.supergroup, sTypes.sessionname, sTypes.sortorder\n" +
            "FROM homeschoolhouse.vsubjects subj\n" +
            "LEFT JOIN homeschoolhouse.vsessiontypes sTypes\n" +
            "ON subj.subjectid = sTypes.subjectid\n" +
            "WHERE (sTypes.sessiontypeid = (SELECT max(sessiontypeid)\n" +
            "FROM homeschoolhouse.vsessiontypes sTypes2\n" +
            "WHERE sTypes2.subjectid = subj.subjectid\n" +
            "AND coalesce(sTypes2.bonus_content, 'N') != 'Y')\n" +
            "OR sTypes.sessiontypeid is null))\n" +
            "SELECT assign.ap_grade, assign.ap_end_dt, assign.apref, assign.eventID, assign.start_date, assign.end_date, coalesce(CASE WHEN subj.supergroup IS NOT NULL then subj.subjectname ELSE coalesce(subj.sessionname, subj.subjectname) END, assign.eno_desc) AS short_description, coalesce(assign.txd_desc, assign.txm_desc, assign.description) AS long_description, assign.completion_date, assign.lock_assignment,\n" +
            "box.description AS assignType, assign.segment_id_fk, linkitTestID, assign.da_testassignmentid, to_number(subj.book_item_number) AS book_item_number, subj.image_url,\n" +
            "assign.subject, assign.pr_form, assign.pr_version, assign.pr_school, assign.pr_boxletter, assign.pr_itemnumber, coalesce(assign.pr_subitemnumber, 0) AS pr_subitemnumber,\n" +
            "cast(coalesce(subj.sortorder, 99) AS NUMBER(3)) AS sortorder, assign.lesson_number, cast(coalesce(subj.subjectid, -1) AS NUMBER(10)) AS subject_id_fk,\n" +
            "CASE\n" +
            "WHEN nVL.nextVideoLesson > subj.duration THEN nVL.nextVideoLesson - subj.duration\n" +
            "ELSE COALESCE(nVL.nextVideoLesson, 170)\n" +
            "END AS nextVideoLesson, assign.ap_ograde\n" +
            "FROM studentAssignments assign\n" +
            "LEFT JOIN progress_report.pr_box_types box\n" +
            "ON assign.txh_type = box.box_type\n" +
            "LEFT JOIN streamSubjects subj\n" +
            "ON assign.eno_series = subj.seriesno\n" +
            "AND assign.eno_ver = subj.seriesversion\n" +
            "AND assign.txh_subj = subj.rcg_subj\n" +
            "LEFT JOIN nextLessons nLmL\n" +
            "ON assign.txh_subj = nLmL.txh_subj\n" +
            "LEFT JOIN nextVideoLessons nVL\n" +
            "ON assign.subject = nVL.subject\n" +
            "LEFT JOIN currentPELesson cPEL\n" +
            "ON coalesce(assign.txh_subj, assign.subject) = cPEL.subject\n" +
            "WHERE (assign.lesson_number = CASE\n" +
            "WHEN coalesce(trim(assign.txh_type), 'PE') = 'PE' THEN cPEL.currentLesson\n" +
            "ELSE nLmL.nextLesson\n" +
            "END\n" +
            "OR (trunc(coalesce(assign.completion_date, assign.da_enddate)) = trunc(sysdate)\n" +
            "AND 'Y' != 'Y'))\n" +
            "AND assign.completion_date IS NULL";
    String GET_LAST_VIEWED_LESSON_DATA_SD_DB = "/* Last viewed lesson data SD DB*/\n" +
            "WITH\t\n" +
            "UserPermissions AS (\t\n" +
            "SELECT *\t\n" +
            "FROM abashared.permissions_queries.GetUserPermissions(p_LoginID => 'LOGIN_ID_DATA')\t\n" +
            "),\t\n" +
            "MainTable AS (\t\n" +
            "SELECT ui.subscription_items_fk, ui.last_viewed,\t\n" +
            "row_number() OVER (PARTITION BY vs.subjectid ORDER BY ui.last_viewed DESC) AS rnk,\t\n" +
            "ui.lesson, ls.segmentid AS segment_id_fk, si.subject_id_fk, si.subscription_number_fk, vs.subjectname,\t\n" +
            "(SELECT coalesce(min(lessonnumber), 0)\t\n" +
            "FROM UserPermissions permissions\t\n" +
            "WHERE permissions.subjectid = si.subject_id_fk\t\n" +
            "AND permissions.lessonnumber > ui.lesson) AS nextlesson,\t\n" +
            "(SELECT coalesce(max(lessonnumber), 0)\t\n" +
            "FROM UserPermissions permissions\t\n" +
            "WHERE permissions.subjectid = si.subject_id_fk\t\n" +
            "AND permissions.lessonnumber < ui.lesson) AS prevlesson\t\n" +
            "FROM abashared.streaming_users_items ui\t\n" +
            "JOIN abashared.streaming_subscriptions_items si\t\n" +
            "ON ui.subscription_items_fk = si.subscription_items_pk\t\n" +
            "JOIN abashared.streaming_subscriptions ss\t\n" +
            "ON ss.subscription_number_pk = si.subscription_number_fk\t\n" +
            "JOIN abashared.vsubjects vs\t\n" +
            "ON vs.subjectid = si.subject_id_fk\t\n" +
            "JOIN abashared.vlessonsegments ls\t\n" +
            "ON ls.subjectid = vs.subjectid\t\n" +
            "AND ls.lesson = ui.lesson\t\n" +
            "AND ls.sessiontypeid = ui.session_type_id_fk\t\n" +
            "WHERE ui.login_id_fk = 'LOGIN_ID_DATA'\t\n" +
            "AND ss.subscription_type_fk IN (SELECT subscription_type_pk\t\n" +
            "FROM ABASHARED.streaming_subscriptions_types\t\n" +
            "WHERE NAME IN ('LESSON-ON-DEMAND', 'SUPPLEMENTAL-SCHOOL', 'MASTER-SCHOOL'))\t\n" +
            "),\t\n" +
            "FilteredTable AS (\t\n" +
            "SELECT MainTable.subscription_items_fk, MainTable.last_viewed, MainTable.lesson, MainTable.segment_id_fk, MainTable.subject_id_fk,\t\n" +
            "MainTable.subscription_number_fk, MainTable.subjectname, MainTable.nextlesson, MainTable.prevlesson\t\n" +
            "FROM MainTable\t\n" +
            "WHERE MainTable.rnk = 1\t\n" +
            "AND MainTable.last_viewed IS NOT NULL\t\n" +
            "),\t\n" +
            "SubjectLessonDetails AS (\t\n" +
            "SELECT DISTINCT FilteredTable.subscription_items_fk, FilteredTable.last_viewed, FilteredTable.lesson, FilteredTable.segment_id_fk,\t\n" +
            "FilteredTable.subject_id_fk, FilteredTable.subscription_number_fk, FilteredTable.subjectname AS short_description,\t\n" +
            "'Lesson ' || FilteredTable.lesson AS long_description, FilteredTable.nextlesson, FilteredTable.prevlesson,\t\n" +
            "(SELECT CASE\t\n" +
            "WHEN count(*) > 1 THEN 'Y'\t\n" +
            "ELSE 'N'\t\n" +
            "END\t\n" +
            "FROM abashared.vsubjects s\t\n" +
            "JOIN abashared.vlessonsegments vl\t\n" +
            "ON s.subjectid = vl.subjectid\t\n" +
            "WHERE s.subjectid = FilteredTable.subject_id_fk\t\n" +
            "AND vl.lesson = FilteredTable.lesson) AS hasmorelesson,\t\n" +
            "(SELECT CASE\t\n" +
            "WHEN count(*) > 1 THEN 'Y'\t\n" +
            "ELSE 'N'\t\n" +
            "END\t\n" +
            "FROM abashared.vsubjects s\t\n" +
            "JOIN abashared.vlessonsegments vl\t\n" +
            "ON s.subjectid = vl.subjectid\t\n" +
            "WHERE s.subjectid = subject_id_fk\t\n" +
            "AND vl.lesson = nextlesson) AS nextmorelesson,\t\n" +
            "(SELECT CASE\t\n" +
            "WHEN count(*) > 1 THEN 'Y'\t\n" +
            "ELSE 'N'\t\n" +
            "END\t\n" +
            "FROM abashared.vsubjects s\t\n" +
            "JOIN abashared.vlessonsegments vl\t\n" +
            "ON s.subjectid = vl.subjectid\t\n" +
            "WHERE s.subjectid = subject_id_fk\t\n" +
            "AND vl.lesson = prevlesson) AS prevmorelesson\t\n" +
            "FROM FilteredTable\t\n" +
            ")\t\n" +
            "SELECT subscription_items_fk, last_viewed, lesson, hasmorelesson, segment_id_fk, subject_id_fk, subscription_number_fk, short_description, long_description, nextlesson, nextmorelesson,\t\n" +
            "(SELECT CASE\t\n" +
            "WHEN nextmorelesson = 'Y' THEN 0\t\n" +
            "ELSE (SELECT coalesce(segmentid, 0)\t\n" +
            "FROM abashared.vlessonsegments\t\n" +
            "WHERE lesson = nextlesson\t\n" +
            "AND subjectid = subject_id_fk)\t\n" +
            "END\t\n" +
            "FROM dual) AS nextsegment,\t\n" +
            "prevlesson,prevmorelesson,\t\n" +
            "(SELECT CASE\t\n" +
            "WHEN prevmorelesson = 'Y' THEN 0\t\n" +
            "ELSE (SELECT coalesce(segmentid, 0)\t\n" +
            "FROM abashared.vlessonsegments\t\n" +
            "WHERE lesson = prevlesson\t\n" +
            "AND subjectid = subject_id_fk)\t\n" +
            "END\t\n" +
            "FROM dual) AS prevsegment\t\n" +
            "FROM SubjectLessonDetails\t\n" +
            "ORDER BY last_viewed DESC";

    String GET_PROGRESS_REPORT_SUBJECT_LIST_AD_DB = "/* Progress report subject list AD DB*/\n" +
            "SELECT DISTINCT p.st_id, b.txh_subj, t.eno_subj AS SUBJECT_CODE, t.eno_desc AS SUBJECT_NAME, b.txh_school AS SCHOOL, t.eno_sem, a.ap_sem, a.ap_item,\t\n" +
            "                                            ((b.txh_form - MOD(b.txh_form, 10)) / 10) AS FORM, b.txh_ver AS VERSION,\t\n" +
            "                                            MAX(DECODE(CASE WHEN t.eno_sem = 2 AND a.ap_sem = 1 THEN MOD(b.txh_form, 10) \t\n" +
            "                                                            WHEN t.eno_sem = 1 AND a.ap_sem = 2 THEN MOD(b.txh_form, 10) \t\n" +
            "                                                            ELSE MOD(b.txh_form, 10)\t\n" +
            "                                                        END, 1, CASE WHEN t.eno_sem = 1 AND a.ap_sem = 2 THEN NULL\t\n" +
            "                                                                     ELSE 1\t\n" +
            "                                                                 END, \t\n" +
            "                                                             3, CASE WHEN t.eno_sem = 2 AND a.ap_sem = 1 THEN 3\t\n" +
            "                                                                     ELSE NULL\t\n" +
            "                                                                 END, NULL)) AS grd_prd1,\t\n" +
            "                                            MAX(DECODE(CASE WHEN t.eno_sem = 2 AND a.ap_sem = 1 THEN MOD(b.txh_form, 10)\t\n" +
            "                                                            WHEN t.eno_sem = 1 AND a.ap_sem = 2 THEN MOD(b.txh_form, 10)\t\n" +
            "                                                            ELSE MOD(b.txh_form, 10)\t\n" +
            "                                                        END, 2, CASE WHEN t.eno_sem = 1 AND a.ap_sem = 2 THEN NULL\t\n" +
            "                                                                     ELSE 2\t\n" +
            "                                                                 END, \t\n" +
            "                                                             4, CASE WHEN t.eno_sem = 2 AND a.ap_sem = 1 THEN 4\t\n" +
            "                                                                     ELSE NULL\t\n" +
            "                                                                 END, NULL)) AS grd_prd2,\t\n" +
            "                                            MAX(DECODE(CASE WHEN t.eno_sem = 2 AND a.ap_sem = 1 THEN MOD(b.txh_form, 10)\t\n" +
            "                                                            WHEN t.eno_sem = 1 AND a.ap_sem = 2 THEN MOD(b.txh_form, 10)\t\n" +
            "                                                            ELSE MOD(b.txh_form, 10)\t\n" +
            "                                                        END, 3, CASE WHEN t.eno_sem = 2 AND a.ap_sem = 1 THEN NULL\t\n" +
            "                                                                     ELSE 3\t\n" +
            "                                                                 END, \t\n" +
            "                                                             1, CASE WHEN t.eno_sem = 1 AND a.ap_sem = 2 THEN 1\t\n" +
            "                                                                     ELSE NULL\t\n" +
            "                                                                 END, NULL)) AS grd_prd3,\t\n" +
            "                                            MAX(DECODE(CASE WHEN t.eno_sem = 2 AND a.ap_sem = 1 THEN MOD(b.txh_form, 10)\t\n" +
            "                                                            WHEN t.eno_sem = 1 AND a.ap_sem = 2 THEN MOD(b.txh_form, 10)\t\n" +
            "                                                            ELSE MOD(b.txh_form, 10)\t\n" +
            "                                                        END, 4, CASE WHEN t.eno_sem = 2 AND a.ap_sem = 1 THEN NULL\t\n" +
            "                                                                     ELSE 4\t\n" +
            "                                                                 END, \t\n" +
            "                                                             2, CASE WHEN t.eno_sem = 1 AND a.ap_sem = 2 THEN 2\t\n" +
            "                                                                     ELSE NULL\t\n" +
            "                                                                 END, NULL)) AS grd_prd4,\t\n" +
            "                                            MAX(DECODE(MOD(b.txh_form, 10), 5, 5, NULL)) AS grd_prd5,\t\n" +
            "                                            MAX(DECODE(MOD(b.txh_form, 10), 6, 6, NULL)) AS grd_prd6,\t\n" +
            "                                            abadb.Courses.LookUpCourseSBN(t.eno_subj) as book_item_number, abadb.Courses.LookUpCourseImageURL(t.eno_subj) as image_url\t\n" +
            "                                       FROM linc.abadb_tboxh b\t\n" +
            "                                      INNER JOIN linc.abadb_prst p\t\n" +
            "                                         ON b.txh_form = p.st_form\t\n" +
            "                                        AND b.txh_ver = p.st_version\t\n" +
            "                                        AND b.txh_school = p.st_school\t\n" +
            "                                      INNER JOIN linc.abadb_tenop t\t\n" +
            "                                         ON t.eno_rcard = b.txh_subj\t\n" +
            "                                      INNER JOIN linc.abadb_applc a\t\n" +
            "                                         ON t.eno_subj = a.ap_item\t\n" +
            "                                        AND a.apc_apref = p.st_apref\t\n" +
            "                                      INNER JOIN linc.abadb_appld appld\t\n" +
            "                                         ON apc_apref = appld.ap_apref\t\n" +
            "                                       LEFT JOIN linc.abadb_digital_assessments assessments\t\n" +
            "                                         ON assessments.application_number = p.st_apref\t\n" +
            "                                        AND assessments.eno_subj = t.eno_subj\t\n" +
            "                                      WHERE p.st_apref = 'APPLICATION_NUMBER_DATA'\t\n" +
            "                                        AND fd_host_no = 'ACCOUNT_NUMBER_DATA'\t\n" +
            "                                        AND b.txh_boxltr = 'A'\t\n" +
            "                                      GROUP BY p.st_id, b.txh_subj, t.eno_subj, t.eno_desc, b.txh_school, t.eno_sem, a.ap_sem, a.ap_item,\t\n" +
            "                                            ((b.txh_form - MOD(b.txh_form, 10)) / 10), b.txh_ver,\t\n" +
            "                                            CASE WHEN eno_sem = 2 THEN 2 ELSE 0 END\t\n" +
            "                                      ORDER BY b.txh_subj\t";

    String GET_PROGRESS_REPORT_TABLE_SECTION_LIST_AD_DB = "/* Progress report table's section list AD DB*/\n" +
            "SELECT distinct tboxh.txh_type AS REPORT_TABLE_SECTION_TYPE, box.description AS REPORT_TABLE_SECTION_NAME, box.display_order\t\n" +
            "                                                 FROM linc.abadb_tboxh tboxh\t\n" +
            "                                                 JOIN progress_report.pr_box_types box\t\n" +
            "                                                   ON tboxh.txh_type = box.box_type\t\n" +
            "                                                WHERE tboxh.txh_form = 'FORM_DATA'\t\n" +
            "                                                  AND tboxh.txh_ver = 'VERSION_DATA'\t\n" +
            "                                                  AND tboxh.txh_school = 'SCHOOL_DATA'\t\n" +
            "                                                  AND box.on_dashboard_pr = 'Y'\t\n" +
            "                                                ORDER BY box.display_order";

    String GET_PROGRESS_REPORT_SECTION_TABLE_LIST_AD_DB = "/* Progress report section's table list AD DB*/\n" +
            "SELECT tboxh.txh_boxltr, CASE WHEN (txh_subj LIKE '%EN%'\t\n" +
            "                                                                                       AND tboxh.txh_type IN ('T', 'E') \t\n" +
            "                                                                                       AND (SELECT ap_ograde FROM linc.abadb_appld WHERE ap_apref = 'APPLICATION_NUMBER_DATA') != 'Y') THEN tboxh.txh_desc || ' (Contains - grammar and literature)' \t\n" +
            "                                                                                    ELSE tboxh.txh_desc END AS TABLE_NAME,\t\n" +
            "                                                               tboxh.txh_type, tboxh.exception_message\t\n" +
            "                                                          FROM linc.abadb_tboxh tboxh\t\n" +
            "                                                          JOIN progress_report.pr_box_types box\t\n" +
            "                                                            ON tboxh.txh_type = box.box_type                                                          \t\n" +
            "                                                         WHERE tboxh.txh_form = 'FORM_DATA'\t\n" +
            "                                                           AND tboxh.txh_ver = 'VERSION_DATA'\t\n" +
            "                                                           AND tboxh.txh_type = 'REPORT_TABLE_SECTION_TYPE_DATA'\t\n" +
            "                                                           AND tboxh.txh_school = 'SCHOOL_DATA'\t\n" +
            "                                                         ORDER BY box.display_order";

//    String GET_PROGRESS_REPORT_TABLE_ROWS_AD_DB = "/* Progress report section's table rows AD DB*/\n" +
//            "SELECT tboxd.txd_lesson AS lesson, tboxd.txd_type AS ITEM_TYPE, tboxd.txd_onlinepr AS hasCustomEval,\t\n" +
//            "             tboxd.txd_hosten AS hostEntered, tboxd.txd_itmnbr AS ITEM_NUMBER,\t\n" +
//            "             replace(tboxd.txd_desc, ';', ';<br />') AS ITEM_DESCRIPTION,\t\n" +
//            "             coalesce(prbxd.pxd_status, assign.da_status) AS itemStatus, tboxh.txh_boxltr, tboxh.txh_type,\t\n" +
//            "             coalesce(to_char(prbxd.pxd_grade, '999.9'), to_char(assign.grade_percentage, '999.9'), '0') AS itemGrade, assign.da_testid,\t\n" +
//            "             prbxd.pxd_aflag AS AFlag, coalesce(assign.da_testid, to_char(tboxd.txd_da_testid)) AS testID,\t\n" +
//            "             tboxd.txd_da_testname AS testName, assign.da_testassignmentid, assign.da_enddate AS submittedDate,\t\n" +
//            "             prbxh.pxh_s_type, appld.fd_id AS pxh_id, assign.lock_assignment, appld.ap_ograde AS digitalAssessment, prbxd.start_filling_grade\t\n" +
//            "        FROM linc.abadb_appld appld\t\n" +
//            "        LEFT JOIN linc.abadb_prbxh prbxh\t\n" +
//            "          ON appld.ap_apref = prbxh.pxh_apref\t\n" +
//            "        LEFT JOIN linc.abadb_prbxd prbxd\t\n" +
//            "          ON appld.ap_apref = prbxd.application_number\t\n" +
//            "         AND prbxh.pxh_form = prbxd.form_number\t\n" +
//            "         AND prbxh.pxh_school = prbxd.school\t\n" +
//            "         AND prbxh.pxh_boxltr = prbxd.box_letter\t\n" +
//            "         AND prbxh.pxh_ver = prbxd.version\t\n" +
//            "        JOIN linc.abadb_tboxh tboxh\t\n" +
//            "          ON prbxh.pxh_form = tboxh.txh_form\t\n" +
//            "         AND prbxh.pxh_school = tboxh.txh_school\t\n" +
//            "         AND prbxh.pxh_boxltr = tboxh.txh_boxltr\t\n" +
//            "         AND prbxh.pxh_ver = tboxh.txh_ver\t\n" +
//            "        JOIN linc.abadb_tboxd tboxd\t\n" +
//            "          ON tboxh.txh_form = tboxd.form_number\t\n" +
//            "         AND tboxh.txh_school = tboxd.school\t\n" +
//            "         AND tboxh.txh_boxltr = tboxd.box_letter\t\n" +
//            "         AND tboxh.txh_ver = tboxd.version\t\n" +
//            "         AND prbxd.pxd_itmnbr = tboxd.txd_itmnbr\t\n" +
//            "        LEFT JOIN homeschoolhouse.student_assignments assign\t\n" +
//            "          ON appld.ap_apref = assign.apref\t\n" +
//            "         AND assign.apref = 'APPLICATION_NUMBER_DATA'\t\n" +
//            "         AND tboxh.txh_form = assign.pr_form\t\n" +
//            "         AND tboxh.txh_school = assign.pr_school\t\n" +
//            "         AND tboxh.txh_boxltr = assign.pr_boxletter\t\n" +
//            "         AND tboxh.txh_ver = assign.pr_version\t\n" +
//            "         AND tboxd.txd_itmnbr = assign.pr_itemnumber\t\n" +
//            "         AND coalesce(assign.pr_subitemnumber, 0) = 0\t\n" +
//            "       WHERE appld.ap_apref = 'APPLICATION_NUMBER_DATA'\t\n" +
//            "         AND prbxh.pxh_form = 'FORM_DATA'\t\n" +
//            "         AND prbxh.pxh_school = 'SCHOOL_DATA'\t\n" +
//            "         AND prbxh.pxh_boxltr = 'TXH_BOXLTR_DATA'\t\n" +
//            "         AND prbxh.pxh_ver = 'VERSION_DATA'\t\n" +
//            "         AND (tboxd.txd_type != 'I'\t\n" +
//            "          OR  (tboxd.txd_type = 'I'\t\n" +
//            "         AND   prbxd.pxd_itmnbr IS NOT NULL))\t\n" +
//            "         AND (tboxh.txh_type != 'V'\t\n" +
//            "          OR  coalesce(tboxd.txd_da_testid, 0) > 0 )\t\n" +
//            "       ORDER BY prbxd.pxd_itmnbr";

    String GET_PROGRESS_REPORT_TABLE_ROWS_AD_DB = "/* Progress report section's table rows AD DB*/\n" +
            "SELECT case when Enrollment.Enrollment_Queries.HasClassWithDigitalAssessments(p_ApplicationNumber => 'APPLICATION_NUMBER_DATA') = 'Y'\n" +
            "THEN\n" +
            " case when (tboxd.txd_hosten = 'G'\n" +
            " AND coalesce(to_char(prbxd.pxd_grade, '999.9'), to_char(assign.grade_percentage, '999.9'), '0') not in (777.7,999.9,222.2,333.3) ) THEN 'True'\n" +
            " Else 'False' End\n" +
            "ELSE\n" +
            " Case\n" +
            "    when  (tboxd.txd_hosten = 'G' or (tboxh.txh_type in ('T','E') and tboxd.txd_hosten = ' '))\n" +
            "        AND coalesce(to_char(prbxd.pxd_grade, '999.9'), to_char(assign.grade_percentage, '999.9'), '0') not in (777.7,999.9,222.2,333.3)\n" +
            "        AND prbxd.start_filling_grade = 'N'\n" +
            "        AND assign.da_testid is NULL\n" +
            "        AND tboxh.txh_type = 'T' THEN 'True'\n" +
            "     when tboxd.txd_hosten not in (' ','B','C')\n" +
            "        AND coalesce(to_char(prbxd.pxd_grade, '999.9'), to_char(assign.grade_percentage, '999.9'), '0') not in (777.7,999.9,222.2,333.3)\n" +
            "        AND coalesce(prbxd.pxd_status, assign.da_status) in ('7','8') THEN 'True'\n" +
            "     ELSE\n" +
            "     'False' End\n" +
            "END as SHOW_GRADE_TEXT_BOX\n" +
            "\n" +
            "\n" +
            ",pxh_form,tboxd.txd_lesson AS lesson, tboxd.txd_type AS ITEM_TYPE, tboxd.txd_onlinepr AS hasCustomEval,\n" +
            "             tboxd.txd_hosten AS hostEntered, tboxd.txd_itmnbr AS ITEM_NUMBER,\n" +
            "             replace(tboxd.txd_desc, ';', ';<br />') AS ITEM_DESCRIPTION,\n" +
            "             coalesce(prbxd.pxd_status, assign.da_status) AS itemStatus, tboxh.txh_boxltr, tboxh.txh_type,\n" +
            "             coalesce(to_char(prbxd.pxd_grade, '999.9'), to_char(assign.grade_percentage, '999.9'), '0') AS ITEM_GRADE, assign.da_testid,\n" +
            "             prbxd.pxd_aflag AS AFlag, coalesce(assign.da_testid, to_char(tboxd.txd_da_testid)) AS TEST_ID,\n" +
            "             tboxd.txd_da_testname AS testName, assign.da_testassignmentid, assign.da_enddate AS submittedDate,\n" +
            "             prbxh.pxh_s_type, appld.fd_id AS pxh_id, assign.lock_assignment, appld.ap_ograde AS digitalAssessment, prbxd.start_filling_grade\n" +
            "        FROM linc.abadb_appld appld\n" +
            "        LEFT JOIN linc.abadb_prbxh prbxh\n" +
            "          ON appld.ap_apref = prbxh.pxh_apref\n" +
            "        LEFT JOIN linc.abadb_prbxd prbxd\n" +
            "          ON appld.ap_apref = prbxd.application_number\n" +
            "         AND prbxh.pxh_form = prbxd.form_number\n" +
            "         AND prbxh.pxh_school = prbxd.school\n" +
            "         AND prbxh.pxh_boxltr = prbxd.box_letter\n" +
            "         AND prbxh.pxh_ver = prbxd.version\n" +
            "        JOIN linc.abadb_tboxh tboxh\n" +
            "          ON prbxh.pxh_form = tboxh.txh_form\n" +
            "         AND prbxh.pxh_school = tboxh.txh_school\n" +
            "         AND prbxh.pxh_boxltr = tboxh.txh_boxltr\n" +
            "         AND prbxh.pxh_ver = tboxh.txh_ver\n" +
            "        JOIN linc.abadb_tboxd tboxd\n" +
            "          ON tboxh.txh_form = tboxd.form_number\n" +
            "         AND tboxh.txh_school = tboxd.school\n" +
            "         AND tboxh.txh_boxltr = tboxd.box_letter\n" +
            "         AND tboxh.txh_ver = tboxd.version\n" +
            "         AND prbxd.pxd_itmnbr = tboxd.txd_itmnbr\n" +
            "        LEFT JOIN homeschoolhouse.student_assignments assign\n" +
            "          ON appld.ap_apref = assign.apref\n" +
            "         AND assign.apref = 'APPLICATION_NUMBER_DATA'\n" +
            "         AND tboxh.txh_form = assign.pr_form\n" +
            "         AND tboxh.txh_school = assign.pr_school\n" +
            "         AND tboxh.txh_boxltr = assign.pr_boxletter\n" +
            "         AND tboxh.txh_ver = assign.pr_version\n" +
            "         AND tboxd.txd_itmnbr = assign.pr_itemnumber\n" +
            "         AND coalesce(assign.pr_subitemnumber, 0) = 0\n" +
            "       WHERE appld.ap_apref = 'APPLICATION_NUMBER_DATA'\n" +
            "         AND prbxh.pxh_form = 'FORM_DATA'\n" +
            "         AND prbxh.pxh_school = 'SCHOOL_DATA'\n" +
            "         AND prbxh.pxh_boxltr = 'TXH_BOXLTR_DATA'\n" +
            "         AND prbxh.pxh_ver = 'VERSION_DATA'\n" +
            "         AND (tboxd.txd_type != 'I'\n" +
            "          OR  (tboxd.txd_type = 'I'\n" +
            "         AND   prbxd.pxd_itmnbr IS NOT NULL))\n" +
            "         AND (tboxh.txh_type != 'V'\n" +
            "          OR  coalesce(tboxd.txd_da_testid, 0) > 0 )\n" +
            "       ORDER BY prbxd.pxd_itmnbr";

//    String GET_PROGRESS_REPORT_ADDITIONAL_TABLE_PARENT_ROWS = "/* Progress report section's table additional rows AD DB*/\n" +
//            "SELECT tboxm.txm_lesson AS lesson, tboxm.txm_type AS itemType, 'N' AS hasCustomEval,\t\n" +
//            "             tboxm.txm_hosten AS hostEntered, tboxm.txm_itmnbr AS itemNumber, tboxm.txm_sub_itmnbr,\t\n" +
//            "             replace(tboxm.txm_desc, ';', ';<br />') AS itemDescription,\t\n" +
//            "             coalesce(prbxm.pxm_status, stdntAssign.da_status) AS itemStatus, tboxm.box_letter, tboxh.txh_type,\t\n" +
//            "             coalesce(to_char(prbxm.pxm_grade, '999.9'), to_char(stdntAssign.grade_percentage, '999.9'), '0') AS itemGrade, stdntAssign.da_testid,\t\n" +
//            "             prbxm.pxm_aflag AS AFlag, coalesce(stdntAssign.da_testid, to_char(tboxm.txm_da_testid)) AS testID,\t\n" +
//            "             tboxm.txm_da_testname AS testName, stdntAssign.da_testassignmentid, stdntAssign.da_enddate AS submittedDate, prbxm.pxm_lock AS lock_assignment, appld.ap_ograde AS digitalAssessment, prbxm.start_filling_grade\t\n" +
//            "        FROM linc.abadb_prbxm prbxm\t\n" +
//            "        JOIN linc.abadb_appld appld\t\n" +
//            "          ON prbxm.application_number = appld.ap_apref\t\n" +
//            "        JOIN linc.abadb_tboxh tboxh\t\n" +
//            "          ON tboxh.txh_form = prbxm.form_number\t\n" +
//            "         AND tboxh.txh_ver = prbxm.version\t\n" +
//            "         AND tboxh.txh_school = prbxm.school\t\n" +
//            "         AND tboxh.txh_boxltr = prbxm.box_letter\t\n" +
//            "        JOIN linc.abadb_tboxm tboxm\t\n" +
//            "          ON tboxm.form_number = prbxm.form_number\t\n" +
//            "         AND tboxm.version = prbxm.version\t\n" +
//            "         AND tboxm.school = prbxm.school\t\n" +
//            "         AND tboxm.box_letter = prbxm.box_letter\t\n" +
//            "         AND tboxm.txm_itmnbr = prbxm.pxm_itmnbr\t\n" +
//            "         AND tboxm.txm_sub_itmnbr = prbxm.pxm_sub_itmnbr\t\n" +
//            "        LEFT JOIN homeschoolhouse.student_assignments stdntAssign\t\n" +
//            "          ON stdntAssign.apref = prbxm.application_number\t\n" +
//            "         AND stdntAssign.pr_form = prbxm.form_number\t\n" +
//            "         AND stdntAssign.pr_version = prbxm.version\t\n" +
//            "         AND stdntAssign.pr_school = prbxm.school\t\n" +
//            "         AND stdntAssign.pr_boxLetter = prbxm.box_letter\t\n" +
//            "         AND stdntAssign.pr_itemNumber = prbxm.pxm_itmnbr\t\n" +
//            "         AND stdntAssign.pr_subItemNumber = prbxm.pxm_sub_itmnbr\t\n" +
//            "       WHERE prbxm.application_number = 'APPLICATION_NUMBER_DATA'\t\n" +
//            "         AND prbxm.form_number = 'FORM_DATA'\t\n" +
//            "         AND prbxm.version = 'VERSION_DATA'\t\n" +
//            "         AND prbxm.school = 'SCHOOL_DATA'\t\n" +
//            "         AND prbxm.box_letter = 'TXH_BOXLTR_DATA'\t\n" +
//            "         AND prbxm.pxm_itmnbr = 'ITEM_NUMBER_DATA'\t\n" +
//            "       ORDER BY prbxm.pxm_sub_itmnbr";

    String GET_PROGRESS_REPORT_ADDITIONAL_TABLE_PARENT_ROWS = "/* Progress report section's table additional rows AD DB*/\n" +
            "SELECT\n" +
            "case when Enrollment.Enrollment_Queries.HasClassWithDigitalAssessments(p_ApplicationNumber => 'APPLICATION_NUMBER_DATA') = 'Y'\n" +
            "THEN\n" +
            " case when (tboxm.txm_hosten = 'G'\n" +
            " AND coalesce(to_char(prbxm.pxm_grade, '999.9'), to_char(stdntAssign.grade_percentage, '999.9'), '0') not in (777.7,999.9,222.2,333.3) ) THEN 'True'\n" +
            " Else 'False' End\n" +
            "ELSE\n" +
            " Case\n" +
            "    when  (tboxm.txm_hosten = 'G' or (tboxh.txh_type in ('T','E') and tboxm.txm_hosten = ' '))\n" +
            "        AND coalesce(to_char(prbxm.pxm_grade, '999.9'), to_char(stdntAssign.grade_percentage, '999.9'), '0') not in (777.7,999.9,222.2,333.3)\n" +
            "        AND prbxm.start_filling_grade = 'N'\n" +
            "        AND stdntAssign.da_testid is NULL\n" +
            "        AND tboxh.txh_type = 'T' THEN 'True'\n" +
            "     when tboxm.txm_hosten not in (' ','B','C')\n" +
            "        AND coalesce(to_char(prbxm.pxm_grade, '999.9'), to_char(stdntAssign.grade_percentage, '999.9'), '0') not in (777.7,999.9,222.2,333.3)\n" +
            "        AND coalesce(prbxm.pxm_status, stdntAssign.da_status) in ('7','8') THEN 'True'\n" +
            "     ELSE\n" +
            "     'False' End\n" +
            "END as SHOW_GRADE_TEXT_BOX ,\n" +
            "tboxm.txm_lesson AS lesson, tboxm.txm_type AS ITEM_TYPE, 'N' AS hasCustomEval,\n" +
            "             tboxm.txm_hosten AS hostEntered, tboxm.txm_itmnbr AS ITEM_NUMBER, tboxm.txm_sub_itmnbr,\n" +
            "             replace(tboxm.txm_desc, ';', ';<br />') AS ITEM_DESCRIPTION,\n" +
            "             coalesce(prbxm.pxm_status, stdntAssign.da_status) AS ITEM_STATUS, tboxm.box_letter, tboxh.txh_type,\n" +
            "             coalesce(to_char(prbxm.pxm_grade, '999.9'), to_char(stdntAssign.grade_percentage, '999.9'), '0') AS ITEM_GRADE, stdntAssign.da_testid,\n" +
            "             prbxm.pxm_aflag AS AFlag, coalesce(stdntAssign.da_testid, to_char(tboxm.txm_da_testid)) AS TEST_ID,\n" +
            "             tboxm.txm_da_testname AS testName, stdntAssign.da_testassignmentid, stdntAssign.da_enddate AS submittedDate, prbxm.pxm_lock AS lock_assignment, appld.ap_ograde AS digitalAssessment, prbxm.start_filling_grade\n" +
            "        FROM linc.abadb_prbxm prbxm\n" +
            "        JOIN linc.abadb_appld appld\n" +
            "          ON prbxm.application_number = appld.ap_apref\n" +
            "        JOIN linc.abadb_tboxh tboxh\n" +
            "          ON tboxh.txh_form = prbxm.form_number\n" +
            "         AND tboxh.txh_ver = prbxm.version\n" +
            "         AND tboxh.txh_school = prbxm.school\n" +
            "         AND tboxh.txh_boxltr = prbxm.box_letter\n" +
            "        JOIN linc.abadb_tboxm tboxm\n" +
            "          ON tboxm.form_number = prbxm.form_number\n" +
            "         AND tboxm.version = prbxm.version\n" +
            "         AND tboxm.school = prbxm.school\n" +
            "         AND tboxm.box_letter = prbxm.box_letter\n" +
            "         AND tboxm.txm_itmnbr = prbxm.pxm_itmnbr\n" +
            "         AND tboxm.txm_sub_itmnbr = prbxm.pxm_sub_itmnbr\n" +
            "        LEFT JOIN homeschoolhouse.student_assignments stdntAssign\n" +
            "          ON stdntAssign.apref = prbxm.application_number\n" +
            "         AND stdntAssign.pr_form = prbxm.form_number\n" +
            "         AND stdntAssign.pr_version = prbxm.version\n" +
            "         AND stdntAssign.pr_school = prbxm.school\n" +
            "         AND stdntAssign.pr_boxLetter = prbxm.box_letter\n" +
            "         AND stdntAssign.pr_itemNumber = prbxm.pxm_itmnbr\n" +
            "         AND stdntAssign.pr_subItemNumber = prbxm.pxm_sub_itmnbr\n" +
            "       WHERE prbxm.application_number = 'APPLICATION_NUMBER_DATA'\n" +
            "         AND prbxm.form_number = 'FORM_DATA'\n" +
            "         AND prbxm.version = 'VERSION_DATA'\n" +
            "         AND prbxm.school = 'SCHOOL_DATA'\n" +
            "         AND prbxm.box_letter = 'TXH_BOXLTR_DATA'\n" +
            "         AND prbxm.pxm_itmnbr = 'ITEM_NUMBER_DATA'\n" +
            "       ORDER BY prbxm.pxm_sub_itmnbr";
}
