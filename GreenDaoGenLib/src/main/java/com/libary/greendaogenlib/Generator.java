package com.libary.greendaogenlib;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Property;
import org.greenrobot.greendao.generator.Schema;
import org.greenrobot.greendao.generator.ToMany;

public class Generator {
    private static Schema schema;

    public static void main(String[] args) throws Exception {

        schema = new Schema(Config.VERSION, Config.DEFAULT_PACKAGE);

        addEntity();

        new DaoGenerator().generateAll(schema, Config.OUTDIR);

    }

    private static void addEntity()
    {
        Entity account = schema.addEntity("Account");
        Entity moment = schema.addEntity("Moment");

        /* Account */
        account.addStringProperty("id").primaryKey().getProperty();  //添加ID, 主键
        account.addStringProperty("name").notNull();  //添加String类型的name,不能为空
        account.addStringProperty("avatarlink");  //添加String类型的Link


        /* Moment */
        moment.addStringProperty("id").primaryKey().getProperty();
        moment.addIntProperty("type").notNull();
        moment.addStringProperty("text");
        moment.addDateProperty("time").notNull();
    }

}
