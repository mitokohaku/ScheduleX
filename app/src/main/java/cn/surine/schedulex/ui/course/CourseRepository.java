package cn.surine.schedulex.ui.course;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import cn.surine.schedulex.base.controller.AbstractSingleTon;
import cn.surine.schedulex.base.controller.BaseRepository;
import cn.surine.schedulex.data.entity.Course;
import cn.surine.schedulex.data.entity.CourseList;
import cn.surine.schedulex.data.network.Loader;
import io.reactivex.Flowable;

/**
 * Intro：
 *
 * @author sunliwei
 * @date 2020-01-19 22:51
 */
public class CourseRepository extends BaseRepository {

    public static AbstractSingleTon<CourseRepository> abt = new AbstractSingleTon<CourseRepository>() {
        @Override
        protected CourseRepository newObj(Bundle bundle) {
            return new CourseRepository();
        }
    };


    Flowable<CourseList> getCourseByNet(int week) {
        return Loader.getInstance().getService().getSchedule(week).compose(schedulerHelper());
    }

    void saveCourseByDb(List<Course> courseList) {
        for (Course course : courseList) {
            //如果已插入course id 的数据，则失败
             appDatabase.courseDao().insert(course);
        }
    }

    List<Course> queryCourseByWeek(int week, int scheduleId) {
        List<Course> list = appDatabase.courseDao().getByScheduleId(scheduleId);
        List<Course> handleList = new ArrayList<>();
        for (Course course : list) {
            try {
                if (course.classWeek.charAt(week - 1) == '1') {
                    handleList.add(course);
                }
            } catch (Exception ignored) {
            }
        }
        return handleList;
    }


    void clearCourseByDb(long scheduleId) {
        appDatabase.courseDao().delete(scheduleId);
    }


    public void deleteCourseByScheduleId(long scheduleId) {
        appDatabase.courseDao().delete(scheduleId);
    }

    public long insert(Course course) {
        return appDatabase.courseDao().insert(course);
    }


    public void insert(Course... course) {
        appDatabase.courseDao().insert(course);
    }


    public Course getCourseById(String id) {
        return appDatabase.courseDao().getByCourseId(id);
    }

    public void update(Course course) {
        appDatabase.courseDao().update(course);
    }

    public void deleteByCourseId(String id) {
        appDatabase.courseDao().deleteByCourseId(id);
    }

    public List<Course> getCourseByScheduleId(int scheduleId) {
        return appDatabase.courseDao().getByScheduleId(scheduleId);
    }


    public List<Course> getTodayCourseListByScheduleId(int day, int curWeek, int roomId) {
        List<Course> courseList = appDatabase.courseDao().getTodayCourse(day, roomId);
        List<Course> handleList = new ArrayList<>();
        for (int i = 0; i < courseList.size(); i++) {
            if(courseList.get(i).classWeek.charAt(curWeek - 1) == '1'){
                handleList.add(courseList.get(i));
            }
        }
        return handleList;
    }
}
