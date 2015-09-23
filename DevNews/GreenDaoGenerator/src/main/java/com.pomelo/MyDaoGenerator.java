package com.pomelo;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * 用来为GreenDao框架生成Dao文件
 */
public class MyDaoGenerator {

    //辅助文件生成的相对路径
    public static final String DAO_PATH = "../app/src/main/java-gen";
    //辅助文件的包名
    public static final String PACKAGE_NAME = "com.pomelo.greendao";
    //数据库的版本号
    public static final int DATA_VERSION_CODE = 1;

    public static void main(String[] args) throws Exception {

        Schema schema = new Schema(DATA_VERSION_CODE, PACKAGE_NAME);
        addCache(schema, "AndroidCache");
        addCache(schema, "iOSCache");
        addCache(schema, "JavaCache");
        addCache(schema, "PythonCache");
        addCache(schema, "PictureCache");
        addCache(schema, "VideoCache");
        //生成Dao文件路径
        new DaoGenerator().generateAll(schema, DAO_PATH);

    }

    /**
     * 添加不同的缓存表
     * @param schema
     * @param tableName
     */
    private static void addCache(Schema schema, String tableName) {

        Entity entity = schema.addEntity(tableName);

        //主键id自增长
        entity.addIdProperty().primaryKey().autoincrement();
        //请求结果
        entity.addStringProperty("result");
        //页数
        entity.addIntProperty("page");
        //插入时间，暂时无用
        entity.addLongProperty("time");

    }
}
