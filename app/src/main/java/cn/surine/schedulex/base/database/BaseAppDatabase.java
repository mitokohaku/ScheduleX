package cn.surine.schedulex.base.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import cn.surine.schedulex.base.Constants;
import cn.surine.schedulex.data.dao.CourseDao;
import cn.surine.schedulex.data.dao.ScheduleDao;
import cn.surine.schedulex.data.entity.Course;
import cn.surine.schedulex.data.entity.Schedule;

/**
 * Intro：
 *
 * @author sunliwei
 * @date 2020-01-16 20:53
 */
@Database(entities = {Course.class, Schedule.class}, version = 2)
public abstract class BaseAppDatabase extends RoomDatabase {
    private volatile static BaseAppDatabase instance;

    public static BaseAppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (BaseAppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(), BaseAppDatabase.class, Constants.DB_NAME)
                            .allowMainThreadQueries()  //TODO：slw 主线程访问，不安全
                            .addMigrations(mg_1_2)
                            .build();
                }
            }
        }
        return instance;
    }


    static final Migration mg_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE schedule ADD COLUMN isShowWeekend INTEGER NOT NULL DEFAULT 0 ");
            database.execSQL("ALTER TABLE schedule ADD COLUMN alphaForCourseItem INTEGER NOT NULL DEFAULT 10 ");
        }
    };


    /**
     * 获取课表数据DAO
     */
    public abstract CourseDao courseDao();


    public abstract ScheduleDao scheduleDao();


}
