package com.kotlin.study.widget.mobike;


import android.graphics.Canvas;
import android.view.View;
import android.view.ViewGroup;

import com.jennifer.andy.simpleeyes.utils.ScreenUtils;
import com.kotlin.study.R;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import java.util.Random;

/**
 * Created by kimi on 2017/7/8 0008.
 * Email: 24750@163.com
 */

public class Mobike {

    public static final String TAG = Mobike.class.getSimpleName();

    private World world;
    private float dt = 1f / 60f;
    private int velocityIterations = 3;//速度迭代
    private int positionIterations = 10;//position迭代次数

//                 摩擦系数       屏幕密度           能量损失率        屏幕与现实世界的比例
    private float friction = 0.3f,density = 0.5f,restitution = 0.3f,ratio = 50;
    private int width,height;
    private boolean enable = true;
    private final Random random = new Random();

    private ViewGroup mViewgroup;

    public Mobike(ViewGroup viewgroup) {
        this.mViewgroup = viewgroup;
        density = viewgroup.getContext().getResources().getDisplayMetrics().density;
    }

    public void onSizeChanged(int width,int height){
        this.width = width;
        this.height = height;
    }

    public void onDraw(Canvas canvas) {
        if(!enable){
            return;
        }
        //dt 更新引擎的间隔时间
        //velocityIterations 计算速度
        //positionIterations 迭代的次数
        world.step(dt,velocityIterations,positionIterations);
        int childCount = mViewgroup.getChildCount();
        for(int i = 0; i < childCount; i++){
            View view = mViewgroup.getChildAt(i);
            Body body = (Body) view.getTag(R.id.mobike_body_tag);
            if(body != null){
                //从view中获取绑定的刚体，取出参数，开始更新view
                view.setX(metersToPixels(body.getPosition().x) - view.getWidth() / 2);
                view.setY(metersToPixels(body.getPosition().y) - view.getHeight() / 2);
                view.setRotation(radiansToDegrees(body.getAngle() % 360));
            }
        }
        mViewgroup.invalidate();
    }

    public void onLayout(boolean changed) {
        createWorld(changed);
    }

    public void onStart(){
        setEnable(true);
    }

    public void onStop(){
        setEnable(false);
    }

    public void update(){
        world = null;
        onLayout(true);
    }

    private void createWorld(boolean changed) {

        //jbox2d中world称为世界，这里创建一个世界
        if(world == null){
            //创建边界，注意边界为static静态的，当物体触碰到边界，停止模拟该物体
//            world = new World(new Vec2(0f, 10.0f));
            world = new World(new Vec2(0.0f, 0.0f));
            createTopAndBottomBounds();
            createLeftAndRightBounds();
        }
        int childCount = mViewgroup.getChildCount();
        for(int i = 0; i < childCount; i++){
            View view = mViewgroup.getChildAt(i);
            Body body = (Body) view.getTag(R.id.mobike_body_tag);
            if(body == null || changed){
                createBody(world,view,i);
            }
        }
    }

//    首次加入的view 需要先创建物体进行
    private void createBody(World world, View view, int position) {
        boolean isBig;
        //创建刚体描述，因为刚体需要随重力运动，这里type设置为DYNAMIC
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = (BodyType.DYNAMIC);

        //设置阻尼 不让角速度影响 物体自旋转
        bodyDef.angularDamping = 1000;

        if(position == 0) {
            //设置初始参数，为view的中心点
            bodyDef.fixedRotation = true;
            isBig = true;
            bodyDef.position.set(pixelsToMeters(view.getX() + view.getWidth() / 2),
                    pixelsToMeters(view.getY() - view.getHeight() / 3));
//                    pixelsToMeters(view.getY() + view.getHeight() / 2));

        }else {
//
            bodyDef.fixedRotation = false;
            isBig = false;
            if(position%2 == 0) {
                bodyDef.position.set(pixelsToMeters(ScreenUtils.getScreenWidth(view.getContext())), pixelsToMeters(view.getY() + view.getHeight() * 2));
            }else {
                bodyDef.position.set(pixelsToMeters(view.getX()), pixelsToMeters(view.getY() + view.getHeight() * 2));
            }
        }

        Shape shape = null;
        Boolean isCircle = (Boolean) view.getTag(R.id.mobike_view_circle_tag);
        if(isCircle != null && isCircle){
            shape = createCircleShape(view);//圆形描述
        }else{
            shape = createPolygonShape(view);//多边形描述
        }
        //初始化物体信息
        //friction  物体摩擦力
        //restitution 物体恢复系数
        //density 物体密度
        FixtureDef fixture = new FixtureDef();
        fixture.shape = shape;

        if(isBig){
            fixture.friction = 0;
            fixture.restitution = 0;
            fixture.density = density*1000;//设置密度最大 防止被其他物体影响位置
//            fixture.density = density;//设置密度最大 防止被其他物体影响位置
        }else {
            fixture.friction = friction;
            fixture.restitution = restitution;
            fixture.density = density;
        }

        //用世界创建出刚体
        Body body = world.createBody(bodyDef);
        body.createFixture(fixture);
        view.setTag(R.id.mobike_body_tag,body);

        view.setTag(R.id.mobike_bodydef_tag,bodyDef);
        //初始化物体的运动行为
//        body.setLinearVelocity(new Vec2(random.nextFloat(),random.nextFloat()));

        //设置角速度 自身旋转
//        body.applyAngularImpulse(0);
//        Vec2 impulse = new Vec2(random.nextInt(1000) - 1000, random.nextInt(1000) - 1000);

        if(isBig){
            body.setLinearVelocity(new Vec2(0,0));
        }else {
            Vec2 impulse = new Vec2(random.nextInt(1000), random.nextInt(1000));
            body.applyLinearImpulse(impulse, body.getPosition());
        }

    }


    private Shape createCircleShape(View view){
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(pixelsToMeters(view.getWidth() / 2));
        return circleShape;
    }

    private Shape createPolygonShape(View view){
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(pixelsToMeters(view.getWidth() / 2),pixelsToMeters(view.getHeight() / 2));
        return polygonShape;
    }

    private void createTopAndBottomBounds() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.STATIC;

        PolygonShape box = new PolygonShape();
        float boxWidth = pixelsToMeters(width);
        float boxHeight =  pixelsToMeters(ratio);
        box.setAsBox(boxWidth, boxHeight);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.3f;
        fixtureDef.restitution = 0.5f;

        bodyDef.position.set(0, -boxHeight);
        Body topBody = world.createBody(bodyDef);
        topBody.createFixture(fixtureDef);

        bodyDef.position.set(0, pixelsToMeters(height)+boxHeight);
        Body bottomBody = world.createBody(bodyDef);
        bottomBody.createFixture(fixtureDef);
    }

    private void createLeftAndRightBounds() {
        float boxWidth = pixelsToMeters(ratio);
        float boxHeight = pixelsToMeters(height);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.STATIC;

        PolygonShape box = new PolygonShape();
        box.setAsBox(boxWidth, boxHeight);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.3f;
        fixtureDef.restitution = 0.5f;

        bodyDef.position.set(-boxWidth, boxHeight);
        Body leftBody = world.createBody(bodyDef);
        leftBody.createFixture(fixtureDef);


        bodyDef.position.set(pixelsToMeters(width) + boxWidth, 0);
        Body rightBody = world.createBody(bodyDef);
        rightBody.createFixture(fixtureDef);
    }

    private float radiansToDegrees(float radians) {
        return radians / 3.14f * 180f;
    }

    private float degreesToRadians(float degrees){
        return (degrees / 180f) * 3.14f;
    }

    public float metersToPixels(float meters) {
        return meters * ratio;
    }

    public float pixelsToMeters(float pixels) {
        return pixels / ratio;
    }

    //弹一下，模拟运动
    public void random() {
        int childCount = mViewgroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            Vec2 impulse = new Vec2(random.nextInt(1000) - 1000, random.nextInt(1000) - 1000);
            View view = mViewgroup.getChildAt(i);
            Body body = (Body) view.getTag(R.id.mobike_body_tag);
            if(body != null){
                body.applyLinearImpulse(impulse, body.getPosition());
            }
        }
    }

    //传感器模拟运动
    public void onSensorChanged(float x,float y) {
        int childCount = mViewgroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            Vec2 impulse = new Vec2(x, y);
            View view = mViewgroup.getChildAt(i);
            Body body = (Body) view.getTag(R.id.mobike_body_tag);
            if(body != null){
                body.applyLinearImpulse(impulse, body.getPosition());
            }
        }
    }


    float DEGTORAD = 0.5f;

    //传感器模拟运动
    public void onBigRotate(float x, float y, double angle) {

//        int childCount = mViewgroup.getChildCount();
//        for (int i = 0; i < childCount; i++) {
//            Vec2 impulse = new Vec2(x, y);
//            View view = mViewgroup.getChildAt(i);
//            Body body = (Body) view.getTag(R.id.mobike_body_tag);
//            BodyDef bodyDef = (BodyDef) view.getTag(R.id.mobike_bodydef_tag);
//
//            if(bodyDef != null) {
//                bodyDef.position.set(pixelsToMeters(x),pixelsToMeters(y));
//            }
//
//            if(body != null){
//                body.applyLinearImpulse(impulse, body.getPosition());
//            }
//
//        }

//        float bodyAngle = body->GetAngle();//取得物体自身的弧度
//        b2Vec2 toTarget = clickedPoint - body->GetPosition();//计算角度差
//        float desiredAngle = atanf( -toTarget.x, toTarget.y );//计算设定的弧度
//        float totalRotation = desiredAngle - bodyAngle;//计算需要旋转的弧度

//        float dt = 1.0f / 60.0f;
//        float nextAngle = bodyAngle + body->GetAngularVelocity() *dt;
//        float totalRotation = desiredAngle - nextAngle;
//        while ( totalRotation < -180 * DEGTORAD ) totalRotation += 360 * DEGTORAD;
//        while ( totalRotation >  180 * DEGTORAD ) totalRotation -= 360 * DEGTORAD;
//        float desiredAngularVelocity = totalRotation / dt;
//        float impulse = body->GetInertia() * desiredAngularVelocity;// disregard time factor
//        body->ApplyAngularImpulse( impulse );

        int childCount = mViewgroup.getChildCount();
        float dt = 1.0f / 60.0f;
        if(childCount > 0){


//            Vec2 impulse = new Vec2(x, y);
            View view = mViewgroup.getChildAt(0);
            Body body = (Body) view.getTag(R.id.mobike_body_tag);
            if(body != null){
//                float bodyAngle = body.getAngle();
//
//                float nextAngle = bodyAngle + body.getAngularVelocity() *dt;
//                float totalRotation = (float) angle - nextAngle;
//
//                while ( totalRotation < -180 * DEGTORAD ) totalRotation += 360 * DEGTORAD;
//                while ( totalRotation >  180 * DEGTORAD ) totalRotation -= 360 * DEGTORAD;
//                float desiredAngularVelocity = totalRotation / dt;
//                float impulse = body.getInertia() * desiredAngularVelocity;// disregard time factor
//                body.applyAngularImpulse( impulse );

//                body.applyLinearImpulse(impulse, body.getPosition());



                    x = x - (body.getPosition().x );
                    y = y - (body.getPosition().y );
                    float c = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
                    float d = c / 10;
                Vec2 v1 = new Vec2(x / d, y / d);
//                body.setLinearVelocity(v1);
                Vec2 v2 = new Vec2(body.getPosition().x, body.getPosition().y);
                body.applyForce(v1, v2);

            }
        }
    }


    public float centerX;
    public float centerY;
    /**
     * 小球按圆周运动
     */
    public void removeByRecycle(int radius, int angle) {
        double offsetx = radius * 1.1 * Math.sin(angle * Math.PI / 180);
        double offsety = radius * 1.1 * (1 - Math.cos(angle * Math.PI / 180));
        angle += 0.5;
        if (angle > 360) {
            angle = 0;
        }
        double offsetx2 = radius * 1.1 * Math.sin(angle * Math.PI / 180);
        double offsety2 = radius * 1.1 * (1 - Math.cos(angle * Math.PI / 180));
        this.centerX += offsetx2 - offsetx;
        this.centerY += offsety2 - offsety;
    }

    public float getFriction() {
        return friction;
    }

    public void setFriction(float friction) {
        if(friction >= 0){
            this.friction = friction;
        }
    }

    public float getDensity() {
        return density;
    }

    public void setDensity(float density) {
        if(density >= 0){
            this.density = density;
        }
    }

    public float getRestitution() {
        return restitution;
    }

    public void setRestitution(float restitution) {
        if(restitution >= 0){
            this.restitution = restitution;
        }
    }

    public float getRatio() {
        return ratio;
    }

    public void setRatio(float ratio) {
        if(ratio >= 0){
            this.ratio = ratio;
        }
    }

    public boolean getEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
        mViewgroup.invalidate();
    }
}
