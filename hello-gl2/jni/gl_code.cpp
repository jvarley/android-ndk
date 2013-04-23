
#include <jni.h>
#include <android/log.h>

#include <stdio.h>
#include <stdlib.h>
#include <math.h>

#include "missilecommand/missilecommand.h"

#define  LOG_TAG    "libgl2jni"
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)

Missilecommand *mc;


#ifdef __cplusplus
extern "C" {
#endif
    JNIEXPORT void JNICALL Java_com_android_gl2jni_GL2JNILib_step(JNIEnv * env, jobject obj);
    JNIEXPORT void JNICALL Java_com_android_gl2jni_GL2JNILib_fire(JNIEnv * env, jobject obj,jint x, jint y);
    JNIEXPORT jintArray JNICALL Java_com_android_gl2jni_GL2JNILib_getSpaceManPos(JNIEnv * env, jobject obj);
    JNIEXPORT void JNICALL Java_com_android_gl2jni_GL2JNILib_addBody(JNIEnv * env, jobject obj,jint x, jint y, jint r, jint m);
    JNIEXPORT void JNICALL Java_com_android_gl2jni_GL2JNILib_addEndMoon(JNIEnv * env, jobject obj,jint x, jint y, jint r, jint m);
    JNIEXPORT void JNICALL Java_com_android_gl2jni_GL2JNILib_addSpaceMan(JNIEnv * env, jobject obj,jint x, jint y, jint r, jint m);
    JNIEXPORT void JNICALL Java_com_android_gl2jni_GL2JNILib_reset(JNIEnv * env, jobject obj);
#ifdef __cplusplus
};
#endif


JNIEXPORT void JNICALL Java_com_android_gl2jni_GL2JNILib_fire(JNIEnv * env, jobject obj,jint x, jint y)
{
    mc->fire(x,y);
    
}

JNIEXPORT void JNICALL Java_com_android_gl2jni_GL2JNILib_reset(JNIEnv * env, jobject obj)
{
    
    mc = new Missilecommand();
    mc->reset();
    
}

JNIEXPORT void JNICALL Java_com_android_gl2jni_GL2JNILib_step(JNIEnv * env, jobject obj)
{
    mc->updateGame();
}

JNIEXPORT jintArray JNICALL Java_com_android_gl2jni_GL2JNILib_getSpaceManPos(JNIEnv * env, jobject obj)
{
    int size = 2;
    jintArray result;
    result = (env)->NewIntArray( size);
    if (result == NULL) {
        return NULL; /* out of memory error thrown */
    }
    int i;
    // fill a temp structure to use to populate the java int array
    jint fill[256];
    //for (i = 0; i < size; i++) {
    //    fill[i] = mc.getBody(j)->p.x; // put whatever logic you want to populate the values here.
    //}
    fill[0] = mc->getSpaceman()->p.x;
    fill[1] = mc->getSpaceman()->p.y;
    // move from the temp structure to the java structure
    (env)->SetIntArrayRegion( result, 0, size, fill);
    return result;

}

JNIEXPORT void JNICALL Java_com_android_gl2jni_GL2JNILib_addBody(JNIEnv * env, jobject obj,jint x, jint y, jint r, jint m)
{
    mc->addBody(x,y,r,m);
}

JNIEXPORT void JNICALL Java_com_android_gl2jni_GL2JNILib_addEndMoon(JNIEnv * env, jobject obj,jint x, jint y, jint r, jint m)
{
    mc->addEndMoon(x,y,r,m);
}

JNIEXPORT void JNICALL Java_com_android_gl2jni_GL2JNILib_addSpaceMan(JNIEnv * env, jobject obj,jint x, jint y, jint r, jint m)
{
    mc->addSpaceMan(x,y,r,m);
}
